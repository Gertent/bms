package com.rmd.bms.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.rmd.bms.bean.UserRequestLog;
import com.rmd.bms.entity.User;
import com.rmd.bms.utils.AsyncExcutor;
import com.rmd.bms.utils.MonitorUtil;
import com.rmd.bms.utils.PropertiesUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * 用户请求日志过滤器
 *
 * @author : liu
 * @Date : 2017/4/7
 */
public class UserRequestLogFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    // 消息队列用户的固定参数
    final static String userLogGroup;
    final static String hostName;
    final static String topic;
    final static String tag;
    final static DefaultMQProducer producer;

    static {
        try {
            userLogGroup = PropertiesUtil.getStringByKey("rocketmq.userLogGroup", "sysconfig.properties");
            hostName = PropertiesUtil.getStringByKey("rocketmq.namesrvAddr", "sysconfig.properties");
            topic = PropertiesUtil.getStringByKey("rocketmq.topic", "sysconfig.properties");
            tag = PropertiesUtil.getStringByKey("rocketmq.tag", "sysconfig.properties");
            producer = new DefaultMQProducer(userLogGroup);
            producer.setNamesrvAddr(hostName);
            producer.start();
        } catch (Exception e) {
            throw new RuntimeException("初始化消息队列参数异常" + e.getMessage());
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest) request;
        // 封装日志信息
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        if(user==null){
        	user = new User();
        }
        String reqIp = MonitorUtil.getRemoteHost(req);
        String reqUrl = req.getRequestURI();
        Map paramMap = req.getParameterMap();
        String param = JSON.toJSONString(paramMap);
        String reqMethod = req.getMethod();
        String reqBrowser = req.getHeader("user-agent");
        String appNo = user.getAppNumber();
        final UserRequestLog log = new UserRequestLog(UUID.randomUUID().toString(), user.getLoginname(), reqIp, reqUrl, param, reqMethod, null, reqBrowser, appNo, new Date(), null);
        if (!(req.getRequestURI().endsWith(".jsp") || req.getRequestURI().endsWith(".css") || req.getRequestURI().endsWith(".js") || req.getRequestURI().endsWith(".png") || req.getRequestURI().endsWith(".gif"))) {
            try {
                // 异步执行日志行为
                AsyncExcutor.excute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // 将日志放入mq中
                            try {
                            	 System.out.println("========================log:" + JSON.toJSONString(log));
                                // 封装消息，参数：主题，标签，数据内容。发送消息
                                Message msg = new Message(topic, tag, JSON.toJSONString(log).getBytes());
                                SendResult result = producer.send(msg);
                                logger.info("*********************** id:" + result.getMsgId() + " result:" + result.getSendStatus());
                            } catch (Exception e) {
                                logger.error("添加消息异常", e);
                            } 
                        } catch (Exception e) {
                            logger.error("异步操作用户请求信息异常", e);
                        }
                    }
                });
            } catch (Exception e) {
                logger.error("获取用户请求信息异常", e);
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        producer.shutdown();
    }
}

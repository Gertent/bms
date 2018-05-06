package com.rmd.bms.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerOrderly;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.rmd.bms.bean.UserRequestLog;
import com.rmd.bms.service.OperationLogService;
import com.rmd.bms.service.UserService;
import com.rmd.bms.utils.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Date;
import java.util.List;

public class UserLogListener implements ServletContextListener {
    public static final Logger logger = Logger.getLogger(UserLogListener.class);

    private OperationLogService operationLogService = null;

    private OperationLogService getOperationLogService() {
        if (operationLogService == null) {
            WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
            this.operationLogService = (OperationLogService) wac.getBean("operationLogService");
        }
        return operationLogService;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(PropertiesUtil.getStringByKey("rocketmq.userLogGroup", "sysconfig.properties"));
        consumer.setNamesrvAddr(PropertiesUtil.getStringByKey("rocketmq.namesrvAddr", "sysconfig.properties"));
        /**
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        String topic = PropertiesUtil.getStringByKey("rocketmq.topic", "sysconfig.properties");
        String tag = PropertiesUtil.getStringByKey("rocketmq.tag", "sysconfig.properties");
        try {
            consumer.subscribe(topic, tag);
            consumer.registerMessageListener(new MessageListenerOrderly() {
                @Override
                public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                    logger.info(Thread.currentThread().getName() + " Receive New Messages: ");
                    try {
                        for (MessageExt msg : msgs) {
                            String str = new String(msg.getBody());
                            if (StringUtils.isBlank(str)) {
                                break;
                            }
                            logger.info("========================receive msg at" + new Date() + ":" + str);
                            // 消费消息
                            UserRequestLog log = JSON.parseObject(str, UserRequestLog.class);
                            getOperationLogService().addElasticSearchUserLog(log);
                        }
                    } catch (Exception e) {
                        logger.error("操作消息异常", e);
                    }
                    return ConsumeOrderlyStatus.SUCCESS;
                }
            });
            consumer.start();
        } catch (MQClientException e1) {
            logger.error("启动消息异常", e1);
        }
        logger.info("用户日志消费已启动");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}

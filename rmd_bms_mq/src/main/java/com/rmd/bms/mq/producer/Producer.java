package com.rmd.bms.mq.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.client.producer.SendStatus;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.rmd.bms.bean.MessageWraper;
import com.rmd.bms.service.MQSupportService;

/**
 * MQ消息生产者包装类
 * @author zuoguodong
 */
public class Producer {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private DefaultMQProducer defaultMQProducer;
    private String producerGroup;
    private String namesrvAddr;
    private String instanceName = String.valueOf(System.currentTimeMillis());
    private MQSupportService supportService;
    
    /**
     * Spring bean init-method
     */
    public void init() throws MQClientException {
        // 初始化
        defaultMQProducer = new DefaultMQProducer(producerGroup);
        defaultMQProducer.setNamesrvAddr(namesrvAddr);
        defaultMQProducer.setInstanceName(instanceName);

        defaultMQProducer.start();
    }
    
    public boolean sendMessage(MessageWraper message){
    	Message msg = new Message();
    	msg.setTopic(message.getTopic());
    	msg.setTags(message.getTags());
    	msg.setKeys(message.getKeys());
    	msg.setBody(JSONObject.toJSONString(message).getBytes());
    	SendResult sendResult = null;
    	try {
    		sendResult = defaultMQProducer.send(msg);
		} catch (Exception e) {
			logger.error("MQ 发送消息出错",e);
		}finally{
			//此日志用于消息对比，查询消息是否丢失，不要随便改变格式
	        logger.info("direction:output,messageId:" + (sendResult==null?"":sendResult.getMsgId()) + "," + message.toString());
		}
    	// 当消息发送失败时如何处理
        if (sendResult == null || sendResult.getSendStatus() != SendStatus.SEND_OK) {
        	//如果只推一次，失败了不再处理 返回false，业务来做来相应的处理
        	if(message.getProducerfailTryTimes()==0){
        		return false;
        	}
        	if(supportService != null){
        		supportService.saveProducerFailMessage(message);
        		return false;
        	}
        }
        return true;
    }
    
    

    /**
     * Spring bean destroy-method
     */
    public void destroy() {
        defaultMQProducer.shutdown();
    }

    public DefaultMQProducer getDefaultMQProducer() {
        return defaultMQProducer;
    }

    public void setProducerGroup(String producerGroup) {
        this.producerGroup = producerGroup;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public void setSupportService(MQSupportService supportService) {
		this.supportService = supportService;
	}
    
}

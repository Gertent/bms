package com.rmd.bms.mq.consumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;
import com.rmd.bms.bean.MessageWraper;
import com.rmd.bms.mq.service.MessageConsumerService;
import com.rmd.bms.service.MQSupportService;

/**
 * MQ 消息处理者包装类
 * 需要注入tag对应的处理业务类
 * @author zuoguodong
 */
public class Consumer {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//topic和tag的对应关系，用于通过tag找对应的service来处理业务
	private Map<String,Map<String,MessageConsumerService>> topicTagServiceMap = new HashMap<String,Map<String,MessageConsumerService>>();
	
	private DefaultMQPushConsumer defaultMQPushConsumer;
    private String namesrvAddr;
    private String consumerGroup;
    private String instance = String.valueOf(System.currentTimeMillis());
    private MQSupportService supportService;
    
    public void init() {
        try{
	        // 一个应用创建一个Consumer，由应用来维护此对象，可以设置为全局对象或者单例<br>
	        // 注意：ConsumerGroupName需要由应用来保证唯一
	        defaultMQPushConsumer = new DefaultMQPushConsumer(consumerGroup);
	        defaultMQPushConsumer.setNamesrvAddr(namesrvAddr);
	        defaultMQPushConsumer.setInstanceName(instance);
	
	        //订阅指定MyTopic下tags
	        List<String> topicList = this.getTopic();
	        for(String topic : topicList){
	        	defaultMQPushConsumer.subscribe(topic,this.getTagsByTopic(topic));
	        }
	       
	        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
	        // 如果非第一次启动，那么按照上次消费的位置继续消费
	        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
	
	        // 设置为集群消费(区别于广播消费)
	        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
	
	        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
	
	            // 默认msgs里只有一条消息，可以通过设置consumeMessageBatchMaxSize参数来批量接收消息
	            @Override
	            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
	            	boolean result = false;
	            	MessageWraper message = null;
	                for (MessageExt msg : msgs) {
	                    message = JSONObject.parseObject(new String(msg.getBody()),MessageWraper.class);
	                    //此日志用于消息对比，查询消息是否丢失，不要随便改变格式
	                    logger.info("direction:input,messageId:" + msg.getMsgId() + "," + message.toString());
	                    try{
	                    	result = getService(msg.getTopic(),msg.getTags()).messageExecute(message);
	                    }catch (Exception e){
	                    	logger.error("消息处理出错",e);
	                    }
	                }
	                // 如果没有return success ，consumer会重新消费该消息，直到return success
	                if(result){
	                	return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	                }else{
	                	if(message != null && supportService != null){
	                		int failTimes = supportService.saveConsumerFailMessage(message);
	                		if(failTimes >= message.getConsumerfailTryTimes()){
	                			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
	                		}
	                	}
	                	return ConsumeConcurrentlyStatus.RECONSUME_LATER;
	                }
	            }
	        });
	        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
	        defaultMQPushConsumer.start();
        }catch(Exception e){
        	logger.error("MQ 客户端出错",e);
        }
    }

    /**
     * Spring bean destroy-method
     */
    public void destroy() {
        defaultMQPushConsumer.shutdown();
    }

    private List<String> getTopic(){
    	List<String> topicList = new ArrayList<String>();
    	Set<String> key = topicTagServiceMap.keySet();
    	Iterator<String> it = key.iterator();
    	while(it.hasNext()){
    		topicList.add(it.next());
    	}
    	return topicList;
    }
    
    private String getTagsByTopic(String topic){ 
    	StringBuffer sb = new StringBuffer();
    	Map<String,MessageConsumerService> map = topicTagServiceMap.get(topic);
    	Set<String> key = map.keySet();
    	Iterator<String> it = key.iterator();
    	while(it.hasNext()){
    		if(sb.length()!=0){
    			sb.append("||");
    		}
    		sb.append(it.next());
    	}
    	return sb.toString();
    }
    
    private MessageConsumerService getService(String topic,String tags){
    	Map<String,MessageConsumerService> map = topicTagServiceMap.get(topic);
    	MessageConsumerService service = map.get(tags);
    	if(service == null){
    		throw new RuntimeException("没有找到"+tags+"对应的service");
    	}
    	return service;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }

	public void setInstance(String instance) {
		this.instance = instance;
	}
    
	public void setTopicTagServiceMap(Map<String, Map<String, MessageConsumerService>> topicTagServiceMap) {
		this.topicTagServiceMap = topicTagServiceMap;
	}

	public void setSupportService(MQSupportService supportService) {
		this.supportService = supportService;
	}
	
}

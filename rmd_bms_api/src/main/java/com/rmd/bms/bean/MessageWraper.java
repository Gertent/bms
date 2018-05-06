package com.rmd.bms.bean;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * MQ中传输的消息包装类
 * 平台中使的MQ消息统一使用此包装类
 * 此消息在原有MQ中message上加入了业务相关属性
 * @author zuoguodng
 */
public class MessageWraper implements Serializable{
	private static final long serialVersionUID = -4136250175027107376L;
	private static final IdWorker idWorker = new IdWorker();
	//应用名称
	private String appName = "";
	//业务名称
	private String businessName = "";
	//队列
	private String topic = "";
	//标签
	private String tags = "";
	//key
	private String keys = "";
	//消息体Json
	private String body;
	//处理失败重试次数 默认失败重试3次
	private int consumerfailTryTimes = 3;
	//发送失败重试次数 默认失败重试0次
	private int producerfailTryTimes = 0;
	//消息id 包装消息产生时产生此ID 数据对比时用
	private long messageWraperId;
	
	public MessageWraper(){
		
	}
	
	public MessageWraper(String appName,String businessName,String topic,String tags,String body){
		messageWraperId = idWorker.nextId();
		this.appName = appName;
		this.businessName = businessName;
		this.topic = topic;
		this.tags = tags;
		this.body = body;
	} 
	
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getKeys() {
		return keys;
	}
	public void setKeys(String keys) {
		this.keys = keys;
	}
	public String getBody(){
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public int getConsumerfailTryTimes() {
		return consumerfailTryTimes;
	}
	public void setConsumerfailTryTimes(int consumerfailTryTimes) {
		this.consumerfailTryTimes = consumerfailTryTimes;
	}
	public int getProducerfailTryTimes() {
		return producerfailTryTimes;
	}
	public void setProducerfailTryTimes(int producerfailTryTimes) {
		this.producerfailTryTimes = producerfailTryTimes;
	}
	public long getMessageWraperId() {
		return messageWraperId;
	}
	public void setMessageWraperId(long messageWraperId) {
		this.messageWraperId = messageWraperId;
	}

	public String toString(){
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		StringBuffer sb = new StringBuffer();
		sb.append("messageWraperId:").append(messageWraperId).append(",");
		sb.append("time:").append(dateFormat.format(new Date())).append(",");
		sb.append("application:").append(appName).append(",");
		sb.append("business:").append(businessName).append(",");
		sb.append("topic:").append(topic).append(",");
		sb.append("tags:").append(tags).append(",");
		sb.append("keys:").append(keys).append(",");
		sb.append("body:").append(body).append(";");
		return sb.toString();
	}
}

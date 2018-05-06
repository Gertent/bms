package com.rmd.bms.service;

import com.rmd.bms.bean.MessageWraper;

/**
 * mq 支持处理类 主要用于错误信息处理
 * @author zuoguodong
 */
public interface MQSupportService {
	
	/**
	 * 保存mq客户端处理失败的消息 
	 * @param id 消息的ID
	 * @param message 消息体
	 * @return 已经是第几次保存 
	 */
	int saveConsumerFailMessage(MessageWraper message);
	
	/**
	 * 保存mq发送处理失败的消息 
	 * @param id 消息的ID
	 * @param message 消息体
	 * @return 已经是第几次保存 
	 */
	int saveProducerFailMessage(MessageWraper message);
	
}

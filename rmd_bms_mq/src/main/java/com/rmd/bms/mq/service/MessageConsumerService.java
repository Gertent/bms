package com.rmd.bms.mq.service;

import com.rmd.bms.bean.MessageWraper;

/**
 * 消息处理接口
 * 所有使用此客户端，进行消息消费的业务类必须实现此接口
 * @author zuoguodong
 */
public interface MessageConsumerService {
	
	/**
	 * 消息处理实方法
	 * @param messageWraper 消息实体，同发送的消息，包括业务信息
	 * @return 如果消息处理成功返回true，处理失败返回false
	 */
	public boolean messageExecute(MessageWraper messageWraper);
	
}

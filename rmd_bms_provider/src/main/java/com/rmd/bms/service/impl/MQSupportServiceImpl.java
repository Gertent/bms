package com.rmd.bms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmd.bms.bean.MessageWraper;
import com.rmd.bms.dao.MQMessageMapper;
import com.rmd.bms.entity.MQMessage;
import com.rmd.bms.service.MQSupportService;

@Service("MQSupportService")
public class MQSupportServiceImpl implements MQSupportService {

	@Autowired
	private MQMessageMapper mqMessageMapper;
	
	@Override
	public int saveConsumerFailMessage(MessageWraper message) {
		MQMessage mqMessage = this.initMQMessage(message);
		mqMessage.setDirection("input");
		return this.saveOrUpdateMessage(mqMessage);
	}

	@Override
	public int saveProducerFailMessage(MessageWraper message) {
		MQMessage mqMessage = this.initMQMessage(message);
		mqMessage.setDirection("output");
		return this.saveOrUpdateMessage(mqMessage);
	}
	
	private int saveOrUpdateMessage(MQMessage mqMessage){
		MQMessage msg = mqMessageMapper.selectByPrimaryKey(mqMessage.getMessageWraperId());
		if(msg == null){
			if(mqMessage.getDirection().equals("input")){
				mqMessage.setConsumerTryTimes(1);
			}
			if(mqMessage.getDirection().equals("output")){
				mqMessage.setProducerTryTimes(1);
			}
			mqMessageMapper.insert(mqMessage);
			return 1;
		}else{
			int tryTimes = 0;
			if(msg.getDirection().equals("input")){
				tryTimes = msg.getConsumerTryTimes();
				tryTimes++;
				msg.setConsumerTryTimes(tryTimes);
			}
			if(msg.getDirection().equals("output")){
				tryTimes = msg.getProducerTryTimes();
				tryTimes++;
				msg.setProducerTryTimes(tryTimes);
			}
			mqMessageMapper.updateByPrimaryKey(msg);
			return tryTimes;
		}
	}
	
	private MQMessage initMQMessage(MessageWraper message){
		MQMessage mqMessage = new MQMessage();
		mqMessage.setAppName(message.getAppName());
		mqMessage.setBody(message.getBody());
		mqMessage.setBusinessName(message.getBusinessName());
		mqMessage.setConsumerFailTryTimes(message.getConsumerfailTryTimes());
		mqMessage.setKeystr(message.getKeys());
		mqMessage.setMessageWraperId(message.getMessageWraperId());
		mqMessage.setProducerFailTryTimes(message.getProducerfailTryTimes());
		mqMessage.setTags(message.getTags());
		mqMessage.setTopic(message.getTopic());
		return mqMessage;
	}

}

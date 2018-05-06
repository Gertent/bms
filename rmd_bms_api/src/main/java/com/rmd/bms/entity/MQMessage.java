package com.rmd.bms.entity;

public class MQMessage {
    private Long messageWraperId;

    private String direction;

    private String appName;

    private String businessName;

    private String topic;

    private String tags;

    private String keystr;

    private Integer consumerFailTryTimes;

    private Integer producerFailTryTimes;

    private Integer consumerTryTimes;

    private Integer producerTryTimes;

    private String body;

    public Long getMessageWraperId() {
        return messageWraperId;
    }

    public void setMessageWraperId(Long messageWraperId) {
        this.messageWraperId = messageWraperId;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction == null ? null : direction.trim();
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName == null ? null : businessName.trim();
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic == null ? null : topic.trim();
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags == null ? null : tags.trim();
    }

    public String getKeystr() {
        return keystr;
    }

    public void setKeystr(String keystr) {
        this.keystr = keystr == null ? null : keystr.trim();
    }

    public Integer getConsumerFailTryTimes() {
        return consumerFailTryTimes;
    }

    public void setConsumerFailTryTimes(Integer consumerFailTryTimes) {
        this.consumerFailTryTimes = consumerFailTryTimes;
    }

    public Integer getProducerFailTryTimes() {
        return producerFailTryTimes;
    }

    public void setProducerFailTryTimes(Integer producerFailTryTimes) {
        this.producerFailTryTimes = producerFailTryTimes;
    }

    public Integer getConsumerTryTimes() {
        return consumerTryTimes;
    }

    public void setConsumerTryTimes(Integer consumerTryTimes) {
        this.consumerTryTimes = consumerTryTimes;
    }

    public Integer getProducerTryTimes() {
        return producerTryTimes;
    }

    public void setProducerTryTimes(Integer producerTryTimes) {
        this.producerTryTimes = producerTryTimes;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body == null ? null : body.trim();
    }
}
package com.rmd.bms.entity;

import java.util.ArrayList;
import java.util.List;

public class MQMessageExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MQMessageExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andMessageWraperIdIsNull() {
            addCriterion("message_wraper_id is null");
            return (Criteria) this;
        }

        public Criteria andMessageWraperIdIsNotNull() {
            addCriterion("message_wraper_id is not null");
            return (Criteria) this;
        }

        public Criteria andMessageWraperIdEqualTo(Long value) {
            addCriterion("message_wraper_id =", value, "messageWraperId");
            return (Criteria) this;
        }

        public Criteria andMessageWraperIdNotEqualTo(Long value) {
            addCriterion("message_wraper_id <>", value, "messageWraperId");
            return (Criteria) this;
        }

        public Criteria andMessageWraperIdGreaterThan(Long value) {
            addCriterion("message_wraper_id >", value, "messageWraperId");
            return (Criteria) this;
        }

        public Criteria andMessageWraperIdGreaterThanOrEqualTo(Long value) {
            addCriterion("message_wraper_id >=", value, "messageWraperId");
            return (Criteria) this;
        }

        public Criteria andMessageWraperIdLessThan(Long value) {
            addCriterion("message_wraper_id <", value, "messageWraperId");
            return (Criteria) this;
        }

        public Criteria andMessageWraperIdLessThanOrEqualTo(Long value) {
            addCriterion("message_wraper_id <=", value, "messageWraperId");
            return (Criteria) this;
        }

        public Criteria andMessageWraperIdIn(List<Long> values) {
            addCriterion("message_wraper_id in", values, "messageWraperId");
            return (Criteria) this;
        }

        public Criteria andMessageWraperIdNotIn(List<Long> values) {
            addCriterion("message_wraper_id not in", values, "messageWraperId");
            return (Criteria) this;
        }

        public Criteria andMessageWraperIdBetween(Long value1, Long value2) {
            addCriterion("message_wraper_id between", value1, value2, "messageWraperId");
            return (Criteria) this;
        }

        public Criteria andMessageWraperIdNotBetween(Long value1, Long value2) {
            addCriterion("message_wraper_id not between", value1, value2, "messageWraperId");
            return (Criteria) this;
        }

        public Criteria andDirectionIsNull() {
            addCriterion("direction is null");
            return (Criteria) this;
        }

        public Criteria andDirectionIsNotNull() {
            addCriterion("direction is not null");
            return (Criteria) this;
        }

        public Criteria andDirectionEqualTo(String value) {
            addCriterion("direction =", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionNotEqualTo(String value) {
            addCriterion("direction <>", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionGreaterThan(String value) {
            addCriterion("direction >", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionGreaterThanOrEqualTo(String value) {
            addCriterion("direction >=", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionLessThan(String value) {
            addCriterion("direction <", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionLessThanOrEqualTo(String value) {
            addCriterion("direction <=", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionLike(String value) {
            addCriterion("direction like", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionNotLike(String value) {
            addCriterion("direction not like", value, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionIn(List<String> values) {
            addCriterion("direction in", values, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionNotIn(List<String> values) {
            addCriterion("direction not in", values, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionBetween(String value1, String value2) {
            addCriterion("direction between", value1, value2, "direction");
            return (Criteria) this;
        }

        public Criteria andDirectionNotBetween(String value1, String value2) {
            addCriterion("direction not between", value1, value2, "direction");
            return (Criteria) this;
        }

        public Criteria andAppNameIsNull() {
            addCriterion("app_name is null");
            return (Criteria) this;
        }

        public Criteria andAppNameIsNotNull() {
            addCriterion("app_name is not null");
            return (Criteria) this;
        }

        public Criteria andAppNameEqualTo(String value) {
            addCriterion("app_name =", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotEqualTo(String value) {
            addCriterion("app_name <>", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameGreaterThan(String value) {
            addCriterion("app_name >", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameGreaterThanOrEqualTo(String value) {
            addCriterion("app_name >=", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameLessThan(String value) {
            addCriterion("app_name <", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameLessThanOrEqualTo(String value) {
            addCriterion("app_name <=", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameLike(String value) {
            addCriterion("app_name like", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotLike(String value) {
            addCriterion("app_name not like", value, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameIn(List<String> values) {
            addCriterion("app_name in", values, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotIn(List<String> values) {
            addCriterion("app_name not in", values, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameBetween(String value1, String value2) {
            addCriterion("app_name between", value1, value2, "appName");
            return (Criteria) this;
        }

        public Criteria andAppNameNotBetween(String value1, String value2) {
            addCriterion("app_name not between", value1, value2, "appName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameIsNull() {
            addCriterion("business_name is null");
            return (Criteria) this;
        }

        public Criteria andBusinessNameIsNotNull() {
            addCriterion("business_name is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessNameEqualTo(String value) {
            addCriterion("business_name =", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameNotEqualTo(String value) {
            addCriterion("business_name <>", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameGreaterThan(String value) {
            addCriterion("business_name >", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameGreaterThanOrEqualTo(String value) {
            addCriterion("business_name >=", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameLessThan(String value) {
            addCriterion("business_name <", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameLessThanOrEqualTo(String value) {
            addCriterion("business_name <=", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameLike(String value) {
            addCriterion("business_name like", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameNotLike(String value) {
            addCriterion("business_name not like", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameIn(List<String> values) {
            addCriterion("business_name in", values, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameNotIn(List<String> values) {
            addCriterion("business_name not in", values, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameBetween(String value1, String value2) {
            addCriterion("business_name between", value1, value2, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameNotBetween(String value1, String value2) {
            addCriterion("business_name not between", value1, value2, "businessName");
            return (Criteria) this;
        }

        public Criteria andTopicIsNull() {
            addCriterion("topic is null");
            return (Criteria) this;
        }

        public Criteria andTopicIsNotNull() {
            addCriterion("topic is not null");
            return (Criteria) this;
        }

        public Criteria andTopicEqualTo(String value) {
            addCriterion("topic =", value, "topic");
            return (Criteria) this;
        }

        public Criteria andTopicNotEqualTo(String value) {
            addCriterion("topic <>", value, "topic");
            return (Criteria) this;
        }

        public Criteria andTopicGreaterThan(String value) {
            addCriterion("topic >", value, "topic");
            return (Criteria) this;
        }

        public Criteria andTopicGreaterThanOrEqualTo(String value) {
            addCriterion("topic >=", value, "topic");
            return (Criteria) this;
        }

        public Criteria andTopicLessThan(String value) {
            addCriterion("topic <", value, "topic");
            return (Criteria) this;
        }

        public Criteria andTopicLessThanOrEqualTo(String value) {
            addCriterion("topic <=", value, "topic");
            return (Criteria) this;
        }

        public Criteria andTopicLike(String value) {
            addCriterion("topic like", value, "topic");
            return (Criteria) this;
        }

        public Criteria andTopicNotLike(String value) {
            addCriterion("topic not like", value, "topic");
            return (Criteria) this;
        }

        public Criteria andTopicIn(List<String> values) {
            addCriterion("topic in", values, "topic");
            return (Criteria) this;
        }

        public Criteria andTopicNotIn(List<String> values) {
            addCriterion("topic not in", values, "topic");
            return (Criteria) this;
        }

        public Criteria andTopicBetween(String value1, String value2) {
            addCriterion("topic between", value1, value2, "topic");
            return (Criteria) this;
        }

        public Criteria andTopicNotBetween(String value1, String value2) {
            addCriterion("topic not between", value1, value2, "topic");
            return (Criteria) this;
        }

        public Criteria andTagsIsNull() {
            addCriterion("tags is null");
            return (Criteria) this;
        }

        public Criteria andTagsIsNotNull() {
            addCriterion("tags is not null");
            return (Criteria) this;
        }

        public Criteria andTagsEqualTo(String value) {
            addCriterion("tags =", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsNotEqualTo(String value) {
            addCriterion("tags <>", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsGreaterThan(String value) {
            addCriterion("tags >", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsGreaterThanOrEqualTo(String value) {
            addCriterion("tags >=", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsLessThan(String value) {
            addCriterion("tags <", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsLessThanOrEqualTo(String value) {
            addCriterion("tags <=", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsLike(String value) {
            addCriterion("tags like", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsNotLike(String value) {
            addCriterion("tags not like", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsIn(List<String> values) {
            addCriterion("tags in", values, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsNotIn(List<String> values) {
            addCriterion("tags not in", values, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsBetween(String value1, String value2) {
            addCriterion("tags between", value1, value2, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsNotBetween(String value1, String value2) {
            addCriterion("tags not between", value1, value2, "tags");
            return (Criteria) this;
        }

        public Criteria andKeystrIsNull() {
            addCriterion("keystr is null");
            return (Criteria) this;
        }

        public Criteria andKeystrIsNotNull() {
            addCriterion("keystr is not null");
            return (Criteria) this;
        }

        public Criteria andKeystrEqualTo(String value) {
            addCriterion("keystr =", value, "keystr");
            return (Criteria) this;
        }

        public Criteria andKeystrNotEqualTo(String value) {
            addCriterion("keystr <>", value, "keystr");
            return (Criteria) this;
        }

        public Criteria andKeystrGreaterThan(String value) {
            addCriterion("keystr >", value, "keystr");
            return (Criteria) this;
        }

        public Criteria andKeystrGreaterThanOrEqualTo(String value) {
            addCriterion("keystr >=", value, "keystr");
            return (Criteria) this;
        }

        public Criteria andKeystrLessThan(String value) {
            addCriterion("keystr <", value, "keystr");
            return (Criteria) this;
        }

        public Criteria andKeystrLessThanOrEqualTo(String value) {
            addCriterion("keystr <=", value, "keystr");
            return (Criteria) this;
        }

        public Criteria andKeystrLike(String value) {
            addCriterion("keystr like", value, "keystr");
            return (Criteria) this;
        }

        public Criteria andKeystrNotLike(String value) {
            addCriterion("keystr not like", value, "keystr");
            return (Criteria) this;
        }

        public Criteria andKeystrIn(List<String> values) {
            addCriterion("keystr in", values, "keystr");
            return (Criteria) this;
        }

        public Criteria andKeystrNotIn(List<String> values) {
            addCriterion("keystr not in", values, "keystr");
            return (Criteria) this;
        }

        public Criteria andKeystrBetween(String value1, String value2) {
            addCriterion("keystr between", value1, value2, "keystr");
            return (Criteria) this;
        }

        public Criteria andKeystrNotBetween(String value1, String value2) {
            addCriterion("keystr not between", value1, value2, "keystr");
            return (Criteria) this;
        }

        public Criteria andConsumerFailTryTimesIsNull() {
            addCriterion("consumer_fail_try_times is null");
            return (Criteria) this;
        }

        public Criteria andConsumerFailTryTimesIsNotNull() {
            addCriterion("consumer_fail_try_times is not null");
            return (Criteria) this;
        }

        public Criteria andConsumerFailTryTimesEqualTo(Integer value) {
            addCriterion("consumer_fail_try_times =", value, "consumerFailTryTimes");
            return (Criteria) this;
        }

        public Criteria andConsumerFailTryTimesNotEqualTo(Integer value) {
            addCriterion("consumer_fail_try_times <>", value, "consumerFailTryTimes");
            return (Criteria) this;
        }

        public Criteria andConsumerFailTryTimesGreaterThan(Integer value) {
            addCriterion("consumer_fail_try_times >", value, "consumerFailTryTimes");
            return (Criteria) this;
        }

        public Criteria andConsumerFailTryTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("consumer_fail_try_times >=", value, "consumerFailTryTimes");
            return (Criteria) this;
        }

        public Criteria andConsumerFailTryTimesLessThan(Integer value) {
            addCriterion("consumer_fail_try_times <", value, "consumerFailTryTimes");
            return (Criteria) this;
        }

        public Criteria andConsumerFailTryTimesLessThanOrEqualTo(Integer value) {
            addCriterion("consumer_fail_try_times <=", value, "consumerFailTryTimes");
            return (Criteria) this;
        }

        public Criteria andConsumerFailTryTimesIn(List<Integer> values) {
            addCriterion("consumer_fail_try_times in", values, "consumerFailTryTimes");
            return (Criteria) this;
        }

        public Criteria andConsumerFailTryTimesNotIn(List<Integer> values) {
            addCriterion("consumer_fail_try_times not in", values, "consumerFailTryTimes");
            return (Criteria) this;
        }

        public Criteria andConsumerFailTryTimesBetween(Integer value1, Integer value2) {
            addCriterion("consumer_fail_try_times between", value1, value2, "consumerFailTryTimes");
            return (Criteria) this;
        }

        public Criteria andConsumerFailTryTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("consumer_fail_try_times not between", value1, value2, "consumerFailTryTimes");
            return (Criteria) this;
        }

        public Criteria andProducerFailTryTimesIsNull() {
            addCriterion("producer_fail_try_times is null");
            return (Criteria) this;
        }

        public Criteria andProducerFailTryTimesIsNotNull() {
            addCriterion("producer_fail_try_times is not null");
            return (Criteria) this;
        }

        public Criteria andProducerFailTryTimesEqualTo(Integer value) {
            addCriterion("producer_fail_try_times =", value, "producerFailTryTimes");
            return (Criteria) this;
        }

        public Criteria andProducerFailTryTimesNotEqualTo(Integer value) {
            addCriterion("producer_fail_try_times <>", value, "producerFailTryTimes");
            return (Criteria) this;
        }

        public Criteria andProducerFailTryTimesGreaterThan(Integer value) {
            addCriterion("producer_fail_try_times >", value, "producerFailTryTimes");
            return (Criteria) this;
        }

        public Criteria andProducerFailTryTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("producer_fail_try_times >=", value, "producerFailTryTimes");
            return (Criteria) this;
        }

        public Criteria andProducerFailTryTimesLessThan(Integer value) {
            addCriterion("producer_fail_try_times <", value, "producerFailTryTimes");
            return (Criteria) this;
        }

        public Criteria andProducerFailTryTimesLessThanOrEqualTo(Integer value) {
            addCriterion("producer_fail_try_times <=", value, "producerFailTryTimes");
            return (Criteria) this;
        }

        public Criteria andProducerFailTryTimesIn(List<Integer> values) {
            addCriterion("producer_fail_try_times in", values, "producerFailTryTimes");
            return (Criteria) this;
        }

        public Criteria andProducerFailTryTimesNotIn(List<Integer> values) {
            addCriterion("producer_fail_try_times not in", values, "producerFailTryTimes");
            return (Criteria) this;
        }

        public Criteria andProducerFailTryTimesBetween(Integer value1, Integer value2) {
            addCriterion("producer_fail_try_times between", value1, value2, "producerFailTryTimes");
            return (Criteria) this;
        }

        public Criteria andProducerFailTryTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("producer_fail_try_times not between", value1, value2, "producerFailTryTimes");
            return (Criteria) this;
        }

        public Criteria andConsumerTryTimesIsNull() {
            addCriterion("consumer_try_times is null");
            return (Criteria) this;
        }

        public Criteria andConsumerTryTimesIsNotNull() {
            addCriterion("consumer_try_times is not null");
            return (Criteria) this;
        }

        public Criteria andConsumerTryTimesEqualTo(Integer value) {
            addCriterion("consumer_try_times =", value, "consumerTryTimes");
            return (Criteria) this;
        }

        public Criteria andConsumerTryTimesNotEqualTo(Integer value) {
            addCriterion("consumer_try_times <>", value, "consumerTryTimes");
            return (Criteria) this;
        }

        public Criteria andConsumerTryTimesGreaterThan(Integer value) {
            addCriterion("consumer_try_times >", value, "consumerTryTimes");
            return (Criteria) this;
        }

        public Criteria andConsumerTryTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("consumer_try_times >=", value, "consumerTryTimes");
            return (Criteria) this;
        }

        public Criteria andConsumerTryTimesLessThan(Integer value) {
            addCriterion("consumer_try_times <", value, "consumerTryTimes");
            return (Criteria) this;
        }

        public Criteria andConsumerTryTimesLessThanOrEqualTo(Integer value) {
            addCriterion("consumer_try_times <=", value, "consumerTryTimes");
            return (Criteria) this;
        }

        public Criteria andConsumerTryTimesIn(List<Integer> values) {
            addCriterion("consumer_try_times in", values, "consumerTryTimes");
            return (Criteria) this;
        }

        public Criteria andConsumerTryTimesNotIn(List<Integer> values) {
            addCriterion("consumer_try_times not in", values, "consumerTryTimes");
            return (Criteria) this;
        }

        public Criteria andConsumerTryTimesBetween(Integer value1, Integer value2) {
            addCriterion("consumer_try_times between", value1, value2, "consumerTryTimes");
            return (Criteria) this;
        }

        public Criteria andConsumerTryTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("consumer_try_times not between", value1, value2, "consumerTryTimes");
            return (Criteria) this;
        }

        public Criteria andProducerTryTimesIsNull() {
            addCriterion("producer_try_times is null");
            return (Criteria) this;
        }

        public Criteria andProducerTryTimesIsNotNull() {
            addCriterion("producer_try_times is not null");
            return (Criteria) this;
        }

        public Criteria andProducerTryTimesEqualTo(Integer value) {
            addCriterion("producer_try_times =", value, "producerTryTimes");
            return (Criteria) this;
        }

        public Criteria andProducerTryTimesNotEqualTo(Integer value) {
            addCriterion("producer_try_times <>", value, "producerTryTimes");
            return (Criteria) this;
        }

        public Criteria andProducerTryTimesGreaterThan(Integer value) {
            addCriterion("producer_try_times >", value, "producerTryTimes");
            return (Criteria) this;
        }

        public Criteria andProducerTryTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("producer_try_times >=", value, "producerTryTimes");
            return (Criteria) this;
        }

        public Criteria andProducerTryTimesLessThan(Integer value) {
            addCriterion("producer_try_times <", value, "producerTryTimes");
            return (Criteria) this;
        }

        public Criteria andProducerTryTimesLessThanOrEqualTo(Integer value) {
            addCriterion("producer_try_times <=", value, "producerTryTimes");
            return (Criteria) this;
        }

        public Criteria andProducerTryTimesIn(List<Integer> values) {
            addCriterion("producer_try_times in", values, "producerTryTimes");
            return (Criteria) this;
        }

        public Criteria andProducerTryTimesNotIn(List<Integer> values) {
            addCriterion("producer_try_times not in", values, "producerTryTimes");
            return (Criteria) this;
        }

        public Criteria andProducerTryTimesBetween(Integer value1, Integer value2) {
            addCriterion("producer_try_times between", value1, value2, "producerTryTimes");
            return (Criteria) this;
        }

        public Criteria andProducerTryTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("producer_try_times not between", value1, value2, "producerTryTimes");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}
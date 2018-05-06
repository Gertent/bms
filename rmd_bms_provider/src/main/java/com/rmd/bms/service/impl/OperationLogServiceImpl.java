package com.rmd.bms.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.rmd.bms.bean.UserRequestLog;
import com.rmd.bms.dao.OperationLogMapper;
import com.rmd.bms.dao.WebApplicationMapper;
import com.rmd.bms.entity.OperationLog;
import com.rmd.bms.entity.User;
import com.rmd.bms.entity.WebApplication;
import com.rmd.bms.entity.WebApplicationExample;
import com.rmd.bms.entity.common.PageBean;
import com.rmd.bms.service.OperationLogService;
import com.rmd.bms.util.DateUtil;
import com.rmd.bms.util.ElasticSearchCondition;
import com.rmd.bms.util.ElasticSearchUtil;

@Service("operationLogService")
public class OperationLogServiceImpl implements OperationLogService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	// 用户在es中的索引
	private static final String ES_USER_LOG_INDEX = "log";
	// 用户在es中order索引下的的类型
	private static final String ES_USER_LOG_INDEX_TYPE = "user_log";
	
	@Autowired
	private WebApplicationMapper webApplicationMapper;

	@Override
	public PageBean<Map<String, Object>> getOperationList(Integer page, Integer rows, Map<String, Object> whereParams) {
		PageBean<Map<String, Object>> pageBean = new PageBean<Map<String, Object>>();
		ElasticSearchUtil.QueryBuilder queryBuilder = ElasticSearchUtil.getQueryBuilder(ES_USER_LOG_INDEX, ES_USER_LOG_INDEX_TYPE);
		queryBuilder.setPage((page-1)*rows, rows);
		ElasticSearchCondition condition = new ElasticSearchCondition();
		if(null != whereParams.get("systemId") && !"".equals(whereParams.get("systemId"))){
			condition.add("appNo", whereParams.get("systemId").toString());
		}
		if(null != whereParams.get("loginname") && !"".equals(whereParams.get("loginname"))){
			condition.add("userName", whereParams.get("loginname").toString());
		}
		if(null != whereParams.get("operation") && !"".equals(whereParams.get("operation"))){
			condition.like("reqUrl", whereParams.get("operation").toString());
		}
		if(null != whereParams.get("starttime") && !"".equals(whereParams.get("starttime"))){
			condition.from("reqDate", DateUtil.strToLong(whereParams.get("starttime").toString()));
		}
		if(null != whereParams.get("endtime") && !"".equals(whereParams.get("endtime"))){
			condition.to("reqDate", DateUtil.strToLong(whereParams.get("endtime").toString()));
		}
		Map<String, Object> map = queryBuilder.execute(condition);
		if(null != map.get("total")){
			pageBean.setTotal(Long.valueOf(String.valueOf(map.get("total"))));
		}
		pageBean.setList((List<Map<String, Object>>)map.get("data"));
		return pageBean;
	}
	
	@Override
	public void addElasticSearchUserLog(UserRequestLog log) {
		if (log == null) {
			return;
		}
		try {
			WebApplicationExample example = new WebApplicationExample();
			WebApplicationExample.Criteria c = example.createCriteria();
			c.andAppNoEqualTo(log.getAppNo());
			List<WebApplication> list = webApplicationMapper.selectByExample(example);
			if(list.size()>0){
				log.setAppName(list.get(0).getAppName());
			}
			System.out.println("====================" + JSON.toJSONString(log));
			ElasticSearchUtil.addDocument(JSON.parseObject(JSON.toJSONString(log)), ES_USER_LOG_INDEX, ES_USER_LOG_INDEX_TYPE, log.getId());
		} catch (Exception e) {
			logger.error("添加用户日志异常", e);
		}
	}

}

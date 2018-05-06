package com.rmd.bms.service;

import java.util.Map;

import com.rmd.bms.bean.UserRequestLog;
import com.rmd.bms.entity.common.PageBean;

/**
 * 操作日志类
 * @author Administrator
 *
 */
public interface OperationLogService {

	/**
	 * 查询日志列表
	 * @param page
	 * @param rows
	 * @param whereParams
	 * @return
	 */
	PageBean<Map<String, Object>> getOperationList(Integer page, Integer rows,
			Map<String, Object> whereParams);
	
	/**
	 * 添加日志信息
	 * @param log
	 */
	void addElasticSearchUserLog(UserRequestLog log);
}

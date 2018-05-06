package com.rmd.bms.service;

import java.util.Map;

import com.rmd.bms.entity.WebApplication;
import com.rmd.bms.entity.common.PageBean;

public interface WebApplicationService {

	PageBean<WebApplication> getWebApplicationList(Integer page, Integer rows, Map<String, Object> whereParams);

	int updateSort(int sort, int id);

	int deleteWebApplicationById(String ids);

	int insertWebApplication(WebApplication webApplication);

	WebApplication selectByPrimaryKey(Integer id);

	int editWebApplication(WebApplication webApplication);

	/**
	 * 通过系统唯一标识来查询系统
	 * @param appNo 系统唯一标识
	 * @return 与标识对应的系统
	 */
	WebApplication selectByAppNo(String appNo);
	
	/**
	 * 检查系统是否已经注册
	 * @param clientId
	 * @param secret
	 * @return
	 */
	public boolean checkClient(String clientId,String secret);
	/**根据用户 查找此用户对应的所有系统和角色*/
	WebApplication getSystemRoleById(Integer userid);

	WebApplication selectByUserId(Integer userid);


}

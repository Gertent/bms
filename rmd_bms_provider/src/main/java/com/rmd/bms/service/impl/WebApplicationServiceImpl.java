package com.rmd.bms.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.rmd.bms.dao.RoleMapper;
import com.rmd.bms.dao.WebApplicationMapper;
import com.rmd.bms.entity.Role;
import com.rmd.bms.entity.WebApplication;
import com.rmd.bms.entity.WebApplicationExample;
import com.rmd.bms.entity.common.PageBean;
import com.rmd.bms.service.WebApplicationService;

@Service("webApplicationService")
public class WebApplicationServiceImpl implements WebApplicationService{

	@Autowired
	private WebApplicationMapper webApplicationMapper;
	
	@Autowired
	private RoleMapper roleMapper;

	
	@Override
	public PageBean<WebApplication> getWebApplicationList(Integer page,
			Integer rows, Map<String, Object> whereParams) {
		 PageHelper.startPage(page, rows);
		return new PageBean<WebApplication>(webApplicationMapper.selectByAllList(whereParams));
	}


	@Override
	public int updateSort(int sort, int id) {
		WebApplication webApplication = new WebApplication();
		webApplication.setId(id);
		webApplication.setSortNum(sort);
		return webApplicationMapper.updateByPrimaryKeySelective(webApplication);
	}


	@Override
	public int deleteWebApplicationById(String ids) {
		int result = 0;
		String[] arr = ids.split(",");
		for (String id : arr) {
			/**判断系统下是否有角色*/
			Role role = new Role();
			role.setSystemId(Integer.valueOf(id));
			int RoleCountResult = roleMapper.selectByRolename(role);
			if(RoleCountResult >0){
				return 2;
			}
			int webApplicationResult = webApplicationMapper.deleteByPrimaryKey(Integer.valueOf(id));
			if(webApplicationResult >0){
				result++;
			}
		}
		if(result >0){
			return 1;
		}
		return 0;
	}


	@Override
	public int insertWebApplication(WebApplication webApplication) {
		WebApplication webApplicationValidate= webApplicationMapper.selectByValidate(webApplication);
		if(webApplicationValidate.getAppNameCount() >0){
			return 2;
		}
		if(webApplicationValidate.getAppNoCount() >0){
			return 3;
		}
		if(webApplicationValidate.getAppUrlCount() >0){
			return 4;
		}
		if(webApplicationValidate.getIdentifyCount() > 0){
			return 5;
		}
		webApplication.setCreatedate(new Date());
		webApplication.setSortNum(0);
		UUID uuid = UUID.randomUUID();
		webApplication.setSecret(uuid.toString());
		int insertSelective = webApplicationMapper.insertSelective(webApplication);
		if(insertSelective >0){
			return 1;
		}
		return 0;
	}


	@Override
	public WebApplication selectByPrimaryKey(Integer id) {
		return webApplicationMapper.selectByPrimaryKey(id);
	}


	@Override
	public int editWebApplication(WebApplication webApplication) {
		WebApplication webApplicationValidate= webApplicationMapper.selectByValidate(webApplication);
		if(webApplicationValidate.getAppNameCount() >0){
			return 2;
		}
		if(webApplicationValidate.getAppNoCount() >0){
			return 3;
		}
		if(webApplicationValidate.getAppUrlCount() >0){
			return 4;
		}
		if(webApplicationValidate.getIdentifyCount() > 0){
			return 5;
		}
		int insertSelective = webApplicationMapper.updateByPrimaryKeySelective(webApplication);
		if(insertSelective >0){
			return 1;
		}
		return 0;
	}


	@Override
	public WebApplication selectByAppNo(String appNo) {
		WebApplicationExample example = new WebApplicationExample();
		WebApplicationExample.Criteria c = example.createCriteria();
		c.andAppNoEqualTo(appNo);
		List<WebApplication> result = webApplicationMapper.selectByExample(example);
		if(result.isEmpty()){
			return null;
		}
		return result.get(0);
	}
	
	@Override
	public boolean checkClient(String clientId, String secret) {
		WebApplicationExample example = new WebApplicationExample();
		WebApplicationExample.Criteria c = example.createCriteria();
		c.andAppNoEqualTo(clientId).andSecretEqualTo(secret);
		List<WebApplication> list = webApplicationMapper.selectByExample(example);
		if(list==null || list.isEmpty()){
			return false;
		}
		return true;
	}


	@Override
	public WebApplication getSystemRoleById(Integer userid) {
		List<WebApplication> webApplicationList = webApplicationMapper.selectByUserId(userid);
		WebApplication webApp = new WebApplication();
		String string = new String();
		for (WebApplication webApplication : webApplicationList) {
			String appName = webApplication.getAppName();
			String rolename = webApplication.getRolename();
			string +="  "+ appName+";"+rolename;
		}
		webApp.setRolename(string);
		return webApp;
	}


	@Override
	public WebApplication selectByUserId(Integer userid) {
		return webApplicationMapper.selectByUserId(userid).get(0);
	}
}

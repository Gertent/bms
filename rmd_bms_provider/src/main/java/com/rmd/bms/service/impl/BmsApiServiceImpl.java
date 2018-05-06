package com.rmd.bms.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.StringUtil;
import com.rmd.bms.constant.Constant;
import com.rmd.bms.dao.ApplicationMenuMapper;
import com.rmd.bms.dao.DepartmentMapper;
import com.rmd.bms.dao.MenuOperationMapper;
import com.rmd.bms.dao.RoleMapper;
import com.rmd.bms.dao.RoleOperationMapper;
import com.rmd.bms.dao.UserMapper;
import com.rmd.bms.dao.WebApplicationMapper;
import com.rmd.bms.entity.ApplicationMenu;
import com.rmd.bms.entity.Department;
import com.rmd.bms.entity.MenuOperation;
import com.rmd.bms.entity.Role;
import com.rmd.bms.entity.RoleOperation;
import com.rmd.bms.entity.User;
import com.rmd.bms.entity.WebApplication;
import com.rmd.bms.entity.Vo.ApplicationMenuVo;
import com.rmd.bms.entity.Vo.DepartmentVo;
import com.rmd.bms.entity.Vo.UserRoleVo;
import com.rmd.bms.entity.Vo.UserVo;
import com.rmd.bms.service.BmsApiService;
import com.rmd.bms.util.MD5;
import com.rmd.commons.servutils.Notification;
import com.rmd.commons.servutils.NotificationBuilder;
import com.rmd.commons.servutils.Notifications;
@Service("bmsApiService")
public class BmsApiServiceImpl implements BmsApiService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BmsApiServiceImpl.class);
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private ApplicationMenuMapper applicationMenuMapper;
	
	@Autowired
	private WebApplicationMapper webApplicationMapper;
	
	@Autowired
	private RoleOperationMapper roleOperationMapper;
	
	@Autowired
	private DepartmentMapper departmentMapper;
	
	@Autowired
	private MenuOperationMapper menuOperationMapper;
	
	
	
	/**
	  * @Description:返回此系统下的此用户可以使用的菜单
	  * @author:yuyang
	  * 2017年4月18日 
	  */

	@Override
	public Notification<UserVo> selectCodeByUser(String name, String password, String appNo) {
		LOGGER.debug("selectCodeByUser ", name,password,appNo);
	    Notification<UserVo> userInfo = NotificationBuilder.buildOne(Notifications.OK);
		
        UserVo userVo = new UserVo();
        //查询用户
		User user = new User();
		Map<String, Object> map = new HashMap<String, Object>();
		List<ApplicationMenuVo> ApplicationMenuVoList = new ArrayList<ApplicationMenuVo>();
		List<MenuOperation> menuOperationInfoList = new ArrayList<MenuOperation>();
		try {
			user.setName(name);
			user.setPassword(MD5.encodeMD5(password));
			user.setStatus(Constant.STATUS_ONE);
			User resultUser = userMapper.selectByAll(user);
			if(resultUser != null){
				//根据用户查询角色
				map.put("userid", resultUser.getId());
				map.put("appNo", appNo);
				List<Role> roleList = roleMapper.selectByAllList(map);
				if(null != roleList && roleList.size()>0){
					for (Role role : roleList) {
						List<ApplicationMenu> applicationMenuList = applicationMenuMapper.selectMenuInfoListByRoleid(role.getId());
						if(null!= applicationMenuList){
							for (ApplicationMenu applicationMenu : applicationMenuList) {
								ApplicationMenuVo applicationMenuVo = new ApplicationMenuVo();
								applicationMenuVo.setId(applicationMenu.getId());
								applicationMenuVo.setMenuCode(applicationMenu.getMenuCode());
								ApplicationMenuVoList.add(applicationMenuVo);
							}
						}
						Map<String, Object> whereParams = new HashMap<String, Object>();
						whereParams.put("roleId", role.getId());
						List<MenuOperation> menuOperationList = menuOperationMapper.selectByAllList(whereParams);
						if(null != menuOperationList){
							for (MenuOperation menuOperation : menuOperationList) {
								menuOperation.setId(menuOperation.getId());
								menuOperation.setOperationName(menuOperation.getOperationName());
								menuOperation.setOperationCode(menuOperation.getOperationCode());
								menuOperationInfoList.add(menuOperation);
							}
						}
					}
				}
			}
			userVo.setId(resultUser.getId());
			userVo.setJobNum(resultUser.getJobNum());
			userVo.setLoginname(resultUser.getLoginname());
			userVo.setApplicationMenuVo(ApplicationMenuVoList);
			userVo.setMenuOperation(menuOperationInfoList);
			userInfo.setResponseData(userVo);
		} catch (Exception e) {
			userInfo.setNotifCode(Notifications.EXCEPTION.getNotifCode());
			LOGGER.error("selectCodeByUser：登陆接口异常！",e);
		}
		return userInfo;
	}

	/**
	  * @Description:返回此系统下拥有此菜单权限的所用用户
	  * @author:yuyang
	  * 2017年4月19日 
	  */
	@Override
	public Notification<List<UserVo>> selectCodeByUserIdandSystem(String menuCode,String appNo
			) {
		 LOGGER.debug("selectCodeByUserIdandSystem", menuCode);
	    Notification<List<UserVo>> userVoInfoList = NotificationBuilder.buildOne(Notifications.OK);
	    
		Map<String, Object> map = new HashMap<String, Object>();
		RoleOperation roleOperation = new RoleOperation();
		ApplicationMenu applicationMenu = new ApplicationMenu();
		List<UserVo> userVoList = new ArrayList<UserVo>();
		try {
			map.put("appNo", appNo);
			/**系统编号查询系统*/
			WebApplication webApplication = webApplicationMapper.selectByAllList(map).get(0);
			if(webApplication !=null){
				/**系统和菜单编码 查询角色*/
				applicationMenu.setSystemId(webApplication.getId());
				applicationMenu.setMenuCode(menuCode);
				ApplicationMenu application = applicationMenuMapper.selectByAll(applicationMenu);
				if(application !=null){
					/**查询菜单对应的角色*/
					roleOperation.setMenuId(application.getId());
					List<RoleOperation> roleOperationList = roleOperationMapper.selectByAll(roleOperation);
					if(roleOperationList.size() > 0){
						/**角色查询用户*/
						for (RoleOperation roleOpera : roleOperationList) {
							Map<String, Object> whereParams = new HashMap<String, Object>();
							whereParams.put("roleId", roleOpera.getRoleId());
							whereParams.put("status", Constant.STATUS_ONE);
							List<User> selectByAllList = userMapper.selectByAllList(whereParams);
							UserVo userVo = new UserVo();
							for (User user : selectByAllList) {
								userVo.setId(user.getId());
								userVo.setLoginname(user.getLoginname());
							}
							userVoList.add(userVo);
						}
					}
				}
			}
			if(null !=userVoList && !userVoList.isEmpty()){
				userVoInfoList.setResponseData(userVoList);
			}else{
				userVoInfoList.setNotifCode(Notifications.NOTFOUND.getNotifCode());
			}
		} catch (Exception e) {
			userVoInfoList.setNotifCode(Notifications.EXCEPTION.getNotifCode());
			LOGGER.error("selectCodeByUser：wms登陆接口异常！",e);
		}
		return userVoInfoList;
	}
	
	/**
	  * @Description:根据用户id 根据用户id获取用户信息
	  * @author:yuyang
	  * 2017年4月19日 
	  */
	@Override
	public Notification<User> selectUserByUserid(Integer id) {
		 LOGGER.debug("selectLoginnameByUserid param:{}", id);
		 Notification<User> userVoInfo = NotificationBuilder.buildOne(Notifications.OK);
		 if (id == null) {
				userVoInfo.setNotifCode(Notifications.PARAMETER_ERROR.getNotifCode());
				return userVoInfo;
			}
		 try {
			 if(null != id){
				 User user = new User();
				 user.setId(id);
				 user.setStatus(Constant.STATUS_ONE);
				 User resultUser = userMapper.selectByAll(user);
				 LOGGER.debug("selectUserByName selectByAll result:{}", resultUser == null ? "null" : JSON.toJSONString(resultUser));
				 userVoInfo.setResponseData(resultUser);
			 }
		} catch (Exception e) {
			userVoInfo.setNotifCode(Notifications.EXCEPTION.getNotifCode());
			LOGGER.error("selectLoginnameByUserid 根据用户id查询用户名称异常！",e);
		}
		return userVoInfo;
	}
	/**
	  * @Description:  根据用户名称或工号 返回用户信息
	  * @author:yuyang
	  * 2017年4月19日 
	  */
	@Override
	public Notification<User> selectUserByName(String name) {
		LOGGER.debug("selectUserByName param:{}", name);
		Notification<User> userVoInfo = NotificationBuilder.buildOne(Notifications.OK);
		if (StringUtils.isEmpty(name)) {
			userVoInfo.setNotifCode(Notifications.PARAMETER_ERROR.getNotifCode());
			return userVoInfo;
		}
		User user = new User();
		try {
			user.setName(name);
			user.setStatus(Constant.STATUS_ONE);
			User resultUser = userMapper.selectByAll(user);
			LOGGER.debug("selectUserByName selectByAll result:{}", resultUser == null ? "null" : JSON.toJSONString(resultUser));
			userVoInfo.setResponseData(resultUser);
		} catch (Exception e) {
			userVoInfo.setNotifCode(Notifications.EXCEPTION.getNotifCode());
			LOGGER.error("selectUserByName 根据用户名称和工号查询用户异常！", e);
		}
		LOGGER.debug("selectUserByName result:{}", userVoInfo == null ? "null" : JSON.toJSONString(userVoInfo));
		return userVoInfo;
	}
	
	/**
	  * @Description: 此系统下的所有用户 并带有角色
	  * @author:yuyang
	  * 2017年4月19日 
	  */
	@Override
	public Notification<List<UserRoleVo>> selectUserandRoleBySystemId(String appNo) {
		 LOGGER.debug("selectUserandRoleBySystemId");
		Notification<List<UserRoleVo>> userVoRoleInfoList = NotificationBuilder.buildOne(Notifications.OK);
		List<UserRoleVo> userRoleVoList = new ArrayList<UserRoleVo>();
		try {
			Map<String, Object> whereParams = new HashMap<String, Object>();
			whereParams.put("appNo", appNo);
			List<Role> selectByAllList = roleMapper.selectByAllList(whereParams);
			for (Role role : selectByAllList) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("roleId", role.getId());
				params.put("status", Constant.STATUS_ONE);
				List<User> userList = userMapper.selectByAllList(params);
				for (User user : userList) {
					UserRoleVo userRoleVo = new UserRoleVo();
					userRoleVo.setId(user.getId());
					userRoleVo.setLoginname(user.getLoginname());
					userRoleVo.setPassword(user.getPassword());
					userRoleVo.setRealname(user.getRealname());
					userRoleVo.setTel(user.getTel());
					userRoleVo.setJobNum(user.getJobNum());
					userRoleVo.setMobile(user.getMobile());
					userRoleVo.setPosition(user.getPosition());
					userRoleVo.setEmail(user.getEmail());
					userRoleVo.setNote(user.getNote());
					/**部门*/
					List<String> list = new ArrayList<String>();
					Department department = departmentMapper.selectByPrimaryKey(user.getDeptid());
					if(department !=null){
						list.add(department.getDeptname());
						deptnameHierarchy(list,department.getId());
						Collections.reverse(list);
						String join = org.apache.commons.lang3.StringUtils.join(list.toArray(),",");
						String[] split = join.split(",");
						String deptNames = new String();
						for (String string : split) {
							deptNames+=string+"/";
						}
						deptNames=deptNames.substring(0, deptNames.length()-1);
						userRoleVo.setDeptNames(deptNames);
					}else{
						userRoleVo.setDeptNames("");
					}
					
					/**角色*/
					List<WebApplication> webApplicationList = webApplicationMapper.selectByUserId(user.getId());
					String string = new String();
					for (WebApplication webApplication : webApplicationList) {
						String appName = webApplication.getAppName();
						String rolename = webApplication.getRolename();
						string +="  "+ appName+";"+rolename;
					}
					userRoleVo.setRolename(string);
					userRoleVoList.add(userRoleVo);
				}
			}
			userVoRoleInfoList.setResponseData(userRoleVoList);
			System.out.println("--------"+JSONArray.fromObject(userVoRoleInfoList).toString());
		} catch (Exception e) {
			userVoRoleInfoList.setNotifCode(Notifications.EXCEPTION.getNotifCode());
			LOGGER.error("selectUserandRoleBySystemId 根据用户名称或工号返回用户并带有角色异常！",e);
		}
		
		return userVoRoleInfoList;
	}
	private void deptnameHierarchy(List<String> list , Integer id){
			Department department = departmentMapper.selectDepartNamesById(id);
			if(department !=null){
				list.add(department.getDeptname());
				deptnameHierarchy(list,department.getId());
			}else{
				return ;
			}
	}

	/**
	  * @Description: 根据用户名称或工号 获取用户id
	  * @author:yuyang
	  * 2017年4月19日 
	  */
	@Override
	public Integer selectUserIdByName(String name) {
		User  resultUser= null;
		if(StringUtil.isNotEmpty(name)){
			User user = new User();
			user.setName(name);
			user.setStatus(Constant.STATUS_ONE);
			resultUser = userMapper.selectByAll(user);
		}
		return resultUser.getId();
	}
	/**
	  * @Description: 根据部门id 获取部门名称
	  * @author:yuyang
	  * 2017年4月19日 
	  */
	@Override
	public Notification<DepartmentVo> selectDepartmentByDepartid(Integer id){
		LOGGER.debug("selectDepartmentByUserid");
		Notification<DepartmentVo> departmentInfo = NotificationBuilder.buildOne(Notifications.OK);
		try {
			DepartmentVo departmentVo = new DepartmentVo();
			if(null != id ){
				Department department = departmentMapper.selectByPrimaryKey(id);
				departmentVo.setId(department.getId());
				departmentVo.setDeptname(department.getDeptname());
			}
			departmentInfo.setResponseData(departmentVo);
		} catch (Exception e) {
			departmentInfo.setNotifCode(Notifications.EXCEPTION.getNotifCode());
			LOGGER.error("selectDepartmentByUserid 根据用户id 获取部门名称异常！",e);
		}
		return departmentInfo;
	}
	/**
	  * @Description: 根据按钮  获取用户信息
	  * @author:yuyang
	  * 2017年4月28日 
	  */
	@Override
	public Notification<List<UserRoleVo>> selectUserByOperationCode(String appNo,
			String operationCode) {
		LOGGER.debug("selectUserByOperationCode");
		Notification<List<UserRoleVo>> userVoRoleInfoList = NotificationBuilder.buildOne(Notifications.OK);
		try {
			List<UserRoleVo> UserRoleVoList =new ArrayList<UserRoleVo>();
			/**根据按钮查询角色*/
			Map<String, Object> whereParams = new HashMap<String, Object>(); 
			whereParams.put("operationCode", operationCode);
			whereParams.put("appNo", appNo);
			List<Role> roleList = roleMapper.selectByoperationWhere(whereParams);
			/**获取可用的用户*/
			for (Role role : roleList) {
				Map<String, Object> whereParam =  new HashMap<String, Object>();
				whereParam.put("status", Constant.STATUS_ONE);
				whereParam.put("roleId",role.getId());
				List<User> userList = userMapper.selectByAllList(whereParam);
				for (User user : userList) {
					UserRoleVo userRoleVo = new UserRoleVo();
					userRoleVo.setId(user.getId());
					userRoleVo.setLoginname(user.getLoginname());
					userRoleVo.setJobNum(user.getJobNum());
					userRoleVo.setDeptName(user.getDeptName());
					UserRoleVoList.add(userRoleVo);
				}
			}
			if(null !=UserRoleVoList && !UserRoleVoList.isEmpty()){
				userVoRoleInfoList.setResponseData(UserRoleVoList);
			}else{
				userVoRoleInfoList.setNotifCode(Notifications.NOTFOUND.getNotifCode());
			}
		} catch (Exception e) {
			userVoRoleInfoList.setNotifCode(Notifications.EXCEPTION.getNotifCode());
			LOGGER.error("selectUserByOperationCode：操作按钮code 查询所有用户接口异常！",e);
		}
		
		return userVoRoleInfoList;
	}

	/**
	  * @Description:根据用户id修改密码
	  * @author:yuyang
	  * 2017年5月9日
	  */
	@Override
	public Notification<Boolean> updatePasswordByUserId(Integer id, String password) {
		LOGGER.debug("selectUserByName param:{}", id,password);
		Notification<Boolean> noti = NotificationBuilder.buildOne(Notifications.OK);
		try {
			if(id == null && StringUtils.isEmpty(password)){
				return NotificationBuilder.buildOne(Notifications.PARAMETER_ERROR);
			}
			User user = new User();
			user.setId(id);
			user.setPassword(MD5.encodeMD5(password));
			int result = userMapper.updateByPrimaryKeySelective(user);
			if(result > 0){
				noti.setResponseData(true);
			} else {
				noti.setResponseData(false);
			}
		} catch (Exception e) {
			noti.setResponseData(false);
			LOGGER.error("updatePasswordByUserId：操作按钮code 查询所有用户接口异常！",e);
		}
		return noti;
	}

	/**
	  * @Description:根据 账号信息/修改密码 名称 查询url
	  * @author:yuyang
	  * 2017年5月9日
	  */
	@Override
	public Notification<ApplicationMenuVo> selectMenuUrlByMessagesName(
			String appNo, String messagesName) {
		LOGGER.debug("selectMenuUrlByMessagesName param:{}", appNo,messagesName);
		Notification<ApplicationMenuVo> applicationMenuInfo = NotificationBuilder.buildOne(Notifications.OK);
		try {
			if(StringUtils.isEmpty(appNo) || StringUtils.isEmpty(messagesName)){
				return applicationMenuInfo;
			}
			WebApplication webApplication = new WebApplication();
			webApplication.setAppNo(appNo);
			WebApplication application = webApplicationMapper.selectByAll(webApplication);
			if("账号信息".equals(messagesName) || "修改密码".equals(messagesName) ){
				ApplicationMenu applicationMenu = new ApplicationMenu();
				applicationMenu.setSystemId(application.getId());
				applicationMenu.setMenuName(messagesName); 
				ApplicationMenu menu = applicationMenuMapper.selectByAll(applicationMenu);
				LOGGER.debug("selectMenuUrlByMessagesName selectByAll result:{}", menu == null ? "null" : JSON.toJSONString(menu));
				ApplicationMenuVo applicationMenuVo = new ApplicationMenuVo();
				applicationMenuVo.setId(menu.getId());
				applicationMenuVo.setMenuUrl(menu.getMenuUrl());
				applicationMenuInfo.setResponseData(applicationMenuVo);
			}else{
				return applicationMenuInfo;
			}
		} catch (Exception e) {
			applicationMenuInfo.setNotifCode(Notifications.EXCEPTION.getNotifCode());
			LOGGER.error("selectMenuUrlByMessagesName 根据 账号信息/修改密码 名称 查询url！", e);
		}
		return applicationMenuInfo;
	}
}

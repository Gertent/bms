package com.rmd.bms.service;

import java.util.List;

import com.rmd.bms.entity.User;
import com.rmd.bms.entity.Vo.ApplicationMenuVo;
import com.rmd.bms.entity.Vo.DepartmentVo;
import com.rmd.bms.entity.Vo.UserRoleVo;
import com.rmd.bms.entity.Vo.UserVo;
import com.rmd.commons.servutils.Notification;

public interface BmsApiService {

	/**
	  * @Description:返回此系统下的用户可以使用的菜单
	  * @author:yuyang
	  * 2017年4月18日 
	  */
	Notification<UserVo> selectCodeByUser(String name, String password, String appNo);
	
	/**
	  * @Description:返回此系统下拥有此菜单权限的所用用户
	  * @author:yuyang
	  * 2017年4月19日 
	  */
	Notification<List<UserVo>> selectCodeByUserIdandSystem(String menuCode,String appNo);
	
	/**
	  * @Description:根据用户id 根据用户id获取用户信息
	  * @author:yuyang
	  * 2017年4月19日 
	  */
	Notification<User> selectUserByUserid(Integer id);
	
	/**
	  * @Description:  根据用户名称或工号 返回用户信息
	  * @author:yuyang
	  * 2017年4月19日 
	  */
	Notification<User> selectUserByName(String name);
	
	/**
	  * @Description: 此系统下的所有用户 并带有角色
	  * @author:yuyang
	  * 2017年4月19日 
	  */
	Notification<List<UserRoleVo>> selectUserandRoleBySystemId(String appNo);
	
	/**
	  * @Description: 根据用户名称或工号 获取用户id
	  * @author:yuyang
	  * 2017年4月19日 
	  */
	Integer selectUserIdByName(String name);
	
	/**
	  * @Description: 根据部门id 获取部门名称
	  * @author:yuyang
	  * 2017年4月19日 
	  */
	Notification<DepartmentVo> selectDepartmentByDepartid(Integer id);
	
	/**
	  * @Description: 根据按钮  获取用户信息
	  * @author:yuyang
	  * 2017年4月28日 
	  */
	Notification<List<UserRoleVo>> selectUserByOperationCode(String appNo, String operationCode);
	/**
	  * @Description: 根据用户id 修改密码
	  * @author:yuyang
	  * 2017年4月28日 
	  * @return 
	  */
	Notification<Boolean> updatePasswordByUserId(Integer id,String password);
	  
	
	/**
	  * @Description: 根据系统标识 和 名称（账号信息/修改密码 获取url）
	  * @author:yuyang
	  * 2017年4月28日 
	  * @return 
	  */
	Notification<ApplicationMenuVo> selectMenuUrlByMessagesName(String appNo, String messagesName);
}

package com.rmd.bms.service;

import com.rmd.bms.entity.Role;
import com.rmd.bms.entity.User;
import com.rmd.bms.entity.WebApplication;
import com.rmd.bms.entity.common.PageBean;

import java.util.List;

public interface UserService {

	List<Role> getRoleList();

	PageBean<User> getUserList(Integer page, Integer rows, User user);

	int insertUser(Integer userid, User user);

	User selectByPrimaryKey(Integer id);

	int deleteUserById(String ids);

	List<WebApplication> getWebApplicationList();

	int updateAllocationRole(Integer userid, String id, String roleIds, Integer systemId);
	/**修改密码*/
	int updateByPassword(Integer id, String password, String newPassword,
			String confirmPassword);

	int updateStatus(Integer userid, String ids, String status);

	int updateByPrimaryKeySelective(Integer userid, User user);
	
	/**
	 * 跟据登录名称查询用户
	 * @param loginName
	 * @return
	 */
	public User findUserByLoginName(String loginName);

	int updateByAccountInfomation(User user);

}

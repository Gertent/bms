package com.rmd.bms.service;

import com.rmd.bms.entity.Role;
import com.rmd.bms.entity.User;
import com.rmd.bms.entity.Vo.RoleVo;
import com.rmd.bms.entity.WebApplication;
import com.rmd.bms.entity.common.PageBean;

import java.util.List;
import java.util.Map;

public interface RoleService {

	PageBean<Role> getRoleList(Integer page, Integer rows,
			Map<String, Object> whereParams);

	int updateStatus(Integer userid, String ids, String status);

	int deleteRoleById(String ids);

	/**
	 * 保存用户角色
	 * @param user
	 * @param role
	 * @return
	 */
	int insertRole(User user, Role role);

	Role selectByPrimaryKey(Integer id);

	/**
	 * 修改用户角色
	 * @param role
	 * @param user
	 * @return
	 */
	int updateRole(RoleVo role, User user);

	List<WebApplication> getWebApplicationList();

	List<Role> selectBySystemId(String systemId);

	Map<String, Object> getRoleListBySystemId(String systemId, String userid);

	List<Role> selectByAllList(Map<String, Object> userAndSystemMap);


}

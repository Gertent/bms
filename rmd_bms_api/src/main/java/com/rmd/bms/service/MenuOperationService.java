package com.rmd.bms.service;

import java.util.List;
import java.util.Map;

/**
 * 菜单下的操作业务接口
 * @author zuoguodong
 */
public interface MenuOperationService {
	
	/**
	 * 跟据用户名获取操作code
	 * @param userId 用户id
	 * @param systemId 系统id
	 * @return
	 */
	List<String> selectOperationCodeList(Integer userId,Integer systemId);
	
	/**
	 * 通过角色ID获取菜单及操作ID
	 * @param roleId
	 * @return 返回的数据结构为  List<"菜单ID:操作ID">
	 */
	 List<Map<String,String>> getMenuAndOperationByRoleId(String roleId);
}

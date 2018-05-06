package com.rmd.bms.service;

import java.util.List;

import com.rmd.bms.bean.JsonTreeData;
import com.rmd.bms.entity.ApplicationMenu;

public interface ApplicationMenuService {

	/**
	 * 跟据系统ID获取当前系统下的操作菜单及操作项
	 * @param systemId 系统ID
	 * @return 树状列表节点集合
	 */
	List<JsonTreeData> selectMenuInfoListById(Integer systemId);

	List<ApplicationMenu> selectMenuInfoListByRoleid(Integer roleId);
	
	/**
	 * 根据用户ID和系统ID查询菜单
	 * @param userId 用户ID
	 * @param SystemId 系统ID
	 * @return 菜单列表
	 */
	List<ApplicationMenu> selectMenuList(Integer userId,Integer systemId);

}

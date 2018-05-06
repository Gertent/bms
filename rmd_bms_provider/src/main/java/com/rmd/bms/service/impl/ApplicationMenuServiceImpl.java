package com.rmd.bms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmd.bms.bean.JsonTreeData;
import com.rmd.bms.dao.ApplicationMenuMapper;
import com.rmd.bms.dao.MenuOperationMapper;
import com.rmd.bms.dao.WebApplicationMapper;
import com.rmd.bms.entity.ApplicationMenu;
import com.rmd.bms.entity.ApplicationMenuExample;
import com.rmd.bms.entity.MenuOperation;
import com.rmd.bms.entity.MenuOperationExample;
import com.rmd.bms.entity.WebApplication;
import com.rmd.bms.service.ApplicationMenuService;

@Service("applicationMenuService")
public class ApplicationMenuServiceImpl implements ApplicationMenuService{

	@Autowired
	private ApplicationMenuMapper applicationMenuMapper;
	@Autowired
	private WebApplicationMapper webApplicationMapper;
	@Autowired
	private MenuOperationMapper menuOperationMapper;
	
	@Override
	public List<JsonTreeData> selectMenuInfoListById(Integer systemId) {
		//查询系统
		WebApplication webApplication = webApplicationMapper.selectByPrimaryKey(systemId);
		//查询系统下的菜单
		ApplicationMenuExample applicationMenuExample = new ApplicationMenuExample();
		ApplicationMenuExample.Criteria menuCriteria = applicationMenuExample.createCriteria();
		menuCriteria.andSystemIdEqualTo(systemId);
		List<ApplicationMenu> menuList = applicationMenuMapper.selectByExample(applicationMenuExample);
		//查询菜单下的操作
		MenuOperationExample menuOperationExample = new MenuOperationExample();
		MenuOperationExample.Criteria c = menuOperationExample.createCriteria();
		c.andSystemIdEqualTo(systemId);
		List<MenuOperation> operationlist = menuOperationMapper.selectByExample(menuOperationExample);
		//数据组装
		//keyMenuMap 将菜单缓存起来，为以后添加子菜单时使用
		Map<Integer,JsonTreeData> keyMenuMap = new HashMap<Integer,JsonTreeData>();
		List<JsonTreeData> treeData = new ArrayList<JsonTreeData>();
		JsonTreeData applicationData = new JsonTreeData();
		applicationData.setPid(0);
		applicationData.setId(webApplication.getId());
		applicationData.setText(webApplication.getAppName());
		applicationData.getAttributes().put("nodeType", "system");
		treeData.add(applicationData);
		List<JsonTreeData> tempMenuData = new ArrayList<JsonTreeData>();
		for(ApplicationMenu menu : menuList){
			JsonTreeData menuData = new JsonTreeData();
			if(menu.getLevel()==1){
				menuData.setPid(applicationData.getId());
			}else{
				menuData.setPid(menu.getParentid());
			}
			menuData.setId(menu.getId());
			menuData.setText(menu.getMenuName());
			menuData.getAttributes().put("nodeType", "menu");
			tempMenuData.add(menuData);
			keyMenuMap.put(menu.getId(),menuData);
		}
		//组织菜单本身的树型结构
		for(JsonTreeData menu : tempMenuData){
			JsonTreeData jmenu = keyMenuMap.get(menu.getPid());
			if(jmenu != null){
				jmenu.getChildren().add(menu);
			}else{
				applicationData.getChildren().add(menu);
			}
		}
		
		for(MenuOperation opertion : operationlist){
			JsonTreeData operationData = new JsonTreeData();
			operationData.setPid(opertion.getMenuId());
			operationData.setId(opertion.getId());
			operationData.setText(opertion.getOperationName());
			operationData.getAttributes().put("nodeType", "operation");
			JsonTreeData menu = keyMenuMap.get(opertion.getMenuId());
			if(menu != null){
				menu.getChildren().add(operationData);
			}
		}
		return treeData;
	}

	@Override
	public List<ApplicationMenu> selectMenuInfoListByRoleid(Integer roleId) {
		return applicationMenuMapper.selectMenuInfoListByRoleid(roleId);
	}

	@Override
	public List<ApplicationMenu> selectMenuList(Integer userId, Integer systemId) {
		List<ApplicationMenu> selectMenuInfoList = applicationMenuMapper.selectMenuInfoList(userId, systemId);
		return selectMenuInfoList;
	}

}

package com.rmd.bms.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rmd.bms.dao.MenuOperationMapper;
import com.rmd.bms.dao.RoleOperationMapper;
import com.rmd.bms.entity.RoleOperation;
import com.rmd.bms.entity.RoleOperationExample;
import com.rmd.bms.service.MenuOperationService;

@Service("menuOperationService")
public class MenuOperationServiceImpl implements MenuOperationService {

	@Autowired
	private MenuOperationMapper menuOperationMapper;
	@Autowired
	private RoleOperationMapper roleOperationMapper;
	
	@Override
	public List<String> selectOperationCodeList(Integer userId, Integer systemId) {
		return menuOperationMapper.selectOperationCodeList(userId, systemId);
	}

	@Override
	public List<Map<String,String>> getMenuAndOperationByRoleId(String roleId) {
		List<Map<String,String>> menuAndOperationList = new ArrayList<Map<String,String>>();
		RoleOperationExample example = new RoleOperationExample();
		RoleOperationExample.Criteria c = example.createCriteria();
		c.andRoleIdEqualTo(Integer.valueOf(roleId));
		List<RoleOperation> list = roleOperationMapper.selectByExample(example);
		for(RoleOperation roleOperation : list){
			Map<String,String> map = new HashMap<String,String>();
			map.put("menuId", String.valueOf(roleOperation.getMenuId()));
			map.put("operationId", (roleOperation.getOperationId()==null?"":roleOperation.getOperationId()).toString());
			menuAndOperationList.add(map);
		}
		return menuAndOperationList;
	}

}

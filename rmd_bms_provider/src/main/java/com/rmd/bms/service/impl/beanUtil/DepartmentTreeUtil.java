package com.rmd.bms.service.impl.beanUtil;

import com.rmd.bms.entity.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentTreeUtil {
	public final static List<Department> getfatherNode(List<Department> treeDataList) {
		List<Department> newTreeDataList = new ArrayList<Department>();
		for (Department jsonTreeData : treeDataList) {
			if (jsonTreeData.getParentId() == 0) {
				List<Department> list = getChildrenNode(jsonTreeData.getId(), treeDataList);
				if (list != null && !list.isEmpty()) {
					jsonTreeData.setChildren(list);
					jsonTreeData.setState("closed");

					int userNum = jsonTreeData.getUserNum();
					userNum += getChildrenUserNum(list);
					jsonTreeData.setUserNum(userNum);
				}
				newTreeDataList.add(jsonTreeData);
			}
		}
		return newTreeDataList;
	}
	
	private final static List<Department> getChildrenNode(Integer pid, List<Department> treeDataList) {
		List<Department> newTreeDataList = new ArrayList<Department>();
		for (Department jsonTreeData : treeDataList) {
			if (jsonTreeData.getParentId() == 0){
				continue;
			}
			if (jsonTreeData.getParentId().equals(pid)) {
				List<Department> list = getChildrenNode(jsonTreeData.getId(), treeDataList);
				if (list != null && !list.isEmpty()) {
					jsonTreeData.setChildren(list);
					jsonTreeData.setState("closed");

					int userNum = jsonTreeData.getUserNum();
					userNum += getUserNum(list);
					jsonTreeData.setUserNum(userNum);
				}
				newTreeDataList.add(jsonTreeData);
			}
		}
		return newTreeDataList;
	}

	private static int getChildrenUserNum(List<Department> list) {
		int userNum = 0;
		for (Department department : list) {
			userNum += department.getUserNum();
		}
		return userNum;
	}

	private static int getUserNum(List<Department> list) {
		int userNum = 0;
		if (list == null || list.isEmpty()) {
			return userNum;
		}
		for (Department department : list) {
			userNum = department.getUserNum();
			userNum += getUserNum(department.getChildren());
		}
		return userNum;
	}
}

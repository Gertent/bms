package com.rmd.bms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rmd.bms.dao.DepartmentMapper;
import com.rmd.bms.dao.UserMapper;
import com.rmd.bms.entity.Department;
import com.rmd.bms.service.DepartmentService;
import com.rmd.bms.service.impl.beanUtil.DepartmentTreeUtil;

@Service("departmentService")
public class DepartmentServiceImpl implements DepartmentService{

	@Autowired
	private DepartmentMapper departmentMapper;
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public List<Department> getDepartmentList(Map<String, Object> whereParams) {
		List<Department> departmentAllList = departmentMapper.selectByAllList(whereParams);
		// 如果搜索条件不为空，则不显示成树结构
		if (!whereParams.isEmpty()) {
		    return departmentAllList;
		}
		List<Department> treeDataList = new ArrayList<Department>();
		for (Department department : departmentAllList) {
			Department treeData = new Department();
			treeData.setId(department.getId());
			treeData.setParentId(department.getParentId());
			treeData.setDeptname(department.getDeptname());
            treeData.setUserNum(department.getUserNum());
            treeData.setSortNum(department.getSortNum());
			treeDataList.add(treeData);
		}
		List<Department> newTreeDataList = DepartmentTreeUtil.getfatherNode(treeDataList);
		return newTreeDataList;
	}
	
	@Override
	public int updateSort(Integer userid,int id, int sort) {
		Department department = new Department();
		department.setId(id);
		department.setSortNum(sort);
		department.setUpdateuser(userid);
		department.setUpdatetime(new Date());
		return departmentMapper.updateByPrimaryKeySelective(department);
	}

	@Override
	@Transactional
	public int deleteDepartmentById(String ids) {
		String[] arr = ids.split(",");
		List<Department> deptList = null;
		try {
			for (String id : arr) {
				/**选中的部门下如果有子部门先将子部门删掉*/
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("parentId", id);
				deptList = departmentMapper.selectByAllList(map);
				if(deptList != null && deptList.size() > 0){
					return 2;
				}	
			}
			for (String id : arr) {
				/**删除部门*/
				departmentMapper.deleteByPrimaryKey(Integer.valueOf(id));
			}
			
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return 1;
	}
	/**删除部门调用方法*/
//	private int deleteHierarchy(Integer id){
//		Department depart = new Department();
//		depart.setId(id);
//		Department department = departmentMapper.selectByAll(depart);
//		if(department !=null){
//			departmentMapper.deleteByParentId(Integer.valueOf(id));
//			deleteHierarchy(department.getId());
//		}
//		return 0;
//}

	@Override
	public int insertDepartment(Integer userid,Department department) {
		if(department.getParentId() == null){
			department.setParentId(0);
		}
		int resultDeptnameCount = departmentMapper.selectByDeptname(department);
		if(resultDeptnameCount > 0){
			return 2;
		}
		
		department.setCreatetime(new Date());
		department.setCreateuser(userid);
		department.setSortNum(0);
		department.setStatus(1);
		int insertSelective = departmentMapper.insertSelective(department);
		if(insertSelective >0){
			return 1;
		}
		return 0;
	}

	@Override
	public Department selectByPrimaryKey(Integer id) {
		
		return departmentMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Integer userid, Department department) {
		int resultName = departmentMapper.selectByDeptname(department);
		if(resultName >0){
			return 2;
		}
		department.setUpdatetime(new Date());
		department.setUpdateuser(userid);
		department.setStatus(1);
		int updateResult = departmentMapper.updateByPrimaryKeySelective(department);
		if(updateResult >0){
			return 1;
		}
		return 0;
	}

	@Override
	public List<Department> selectByAllList(Map<String, Object> params) {
		return departmentMapper.selectByAllList(params);
	}

	@Override
	public Department selectDepartNamesById(Integer id) {
		return departmentMapper.selectDepartNamesById(id);
	}
}

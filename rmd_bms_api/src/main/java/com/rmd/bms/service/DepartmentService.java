package com.rmd.bms.service;

import java.util.List;
import java.util.Map;

import com.rmd.bms.entity.Department;

public interface DepartmentService {

	int updateSort(Integer userid,int id,int sort);

	List<Department> getDepartmentList(Map<String, Object> whereParams);

	int deleteDepartmentById(String ids);

	int insertDepartment(Integer userid, Department department);

	Department selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Integer userid, Department department);

	List<Department> selectByAllList(Map<String, Object> params);
	/**根据id查询部门的所有层级*/
	Department selectDepartNamesById(Integer id);

}

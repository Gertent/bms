package com.rmd.bms.dao;

import com.rmd.bms.entity.Department;
import com.rmd.bms.entity.DepartmentExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface DepartmentMapper {
    long countByExample(DepartmentExample example);

    int deleteByExample(DepartmentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Department record);

    int insertSelective(Department record);

    List<Department> selectByExample(DepartmentExample example);
    
    List<Department> selectByAllList(Map<String, Object> whereParams);

    Department selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Department record, @Param("example") DepartmentExample example);

    int updateByExample(@Param("record") Department record, @Param("example") DepartmentExample example);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);
    /**修改排序*/
	int updateSort();
	/**验证部门名称重复*/
	int selectByDeptname(Department department);
	/**根据id查询部门的所有层级*/
	Department selectDepartNamesById(Integer id);

	Department selectByAll(Department depart);

	int deleteByParentId(Integer id);

}
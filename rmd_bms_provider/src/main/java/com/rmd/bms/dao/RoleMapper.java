package com.rmd.bms.dao;

import com.rmd.bms.entity.Role;
import com.rmd.bms.entity.RoleExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface RoleMapper {
    long countByExample(RoleExample example);

    int deleteByExample(RoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);
    
    List<Role> selectByAllList(Map<String, Object> map);

    Role selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByExample(@Param("record") Role record, @Param("example") RoleExample example);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    /**角色名称验重*/
	int selectByRolename(Role role);

	List<Role> selectByOperationId(Integer operationId);

	List<Role> selectByoperationWhere(Map<String, Object> whereParams);

}
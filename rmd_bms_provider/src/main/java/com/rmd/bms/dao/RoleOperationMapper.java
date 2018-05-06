package com.rmd.bms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.rmd.bms.entity.RoleOperation;
import com.rmd.bms.entity.RoleOperationExample;

public interface RoleOperationMapper {
    long countByExample(RoleOperationExample example);

    int deleteByExample(RoleOperationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RoleOperation record);

    int insertSelective(RoleOperation record);

    List<RoleOperation> selectByExample(RoleOperationExample example);

    RoleOperation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RoleOperation record, @Param("example") RoleOperationExample example);

    int updateByExample(@Param("record") RoleOperation record, @Param("example") RoleOperationExample example);

    int updateByPrimaryKeySelective(RoleOperation record);

    int updateByPrimaryKey(RoleOperation record);
    /**根据角色id 删除与菜单关联表数据*/
	int deleteByRoleId(Integer roleId);
	/**多条件查询一条数据*/
	List<RoleOperation> selectByAll(RoleOperation roleOperation);

}
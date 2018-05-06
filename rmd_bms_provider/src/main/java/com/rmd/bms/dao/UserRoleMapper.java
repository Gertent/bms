package com.rmd.bms.dao;

import com.rmd.bms.entity.UserRole;
import com.rmd.bms.entity.UserRoleExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface UserRoleMapper {
    long countByExample(UserRoleExample example);

    int deleteByExample(UserRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserRole record);

    int insertSelective(UserRole record);

    List<UserRole> selectByExample(UserRoleExample example);

    UserRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserRole record, @Param("example") UserRoleExample example);

    int updateByExample(@Param("record") UserRole record, @Param("example") UserRoleExample example);

    int updateByPrimaryKeySelective(UserRole record);

    int updateByPrimaryKey(UserRole record);
    /**根据用户删除关系数据*/
	int deleteByUserId(Integer userId);
	/**根据角色删除关系数据*/
	int deleteByRoleId(Integer roleId);
	/**根据用户 和角色 删除关系数据     分配权限*/
	int deleteByAll(UserRole userRole);
}
package com.rmd.bms.dao;

import com.rmd.bms.entity.User;
import com.rmd.bms.entity.UserExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);
    
    List<User> selectByAllList(Map<String, Object> whereParams);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    /**验证账号重复*/
	int selectByLoginname(User user);
	/**查询最大的工号*/
	int selectByJob_num();
	/**根据部门查询员工的数量*/
	int selectByDeptId(@Param("deptId")Integer deptId);
	/**多个添加查询用户*/
	User selectByAll(User user);

	
}
package com.rmd.bms.dao;

import com.rmd.bms.entity.ApplicationMenu;
import com.rmd.bms.entity.ApplicationMenuExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ApplicationMenuMapper {
    long countByExample(ApplicationMenuExample example);

    int deleteByExample(ApplicationMenuExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ApplicationMenu record);

    int insertSelective(ApplicationMenu record);

    List<ApplicationMenu> selectByExample(ApplicationMenuExample example);

    ApplicationMenu selectByPrimaryKey(Integer id);
    /**多个条件查询对象*/
    ApplicationMenu selectByAll(ApplicationMenu applicationMenu);

    int updateByExampleSelective(@Param("record") ApplicationMenu record, @Param("example") ApplicationMenuExample example);

    int updateByExample(@Param("record") ApplicationMenu record, @Param("example") ApplicationMenuExample example);

    int updateByPrimaryKeySelective(ApplicationMenu record);

    int updateByPrimaryKey(ApplicationMenu record);

	List<ApplicationMenu> selectMenuInfoListById(Integer parentid);
	/**根据角色id获取菜单信息*/
	List<ApplicationMenu> selectMenuInfoListByRoleid(Integer roleId);
	
	List<ApplicationMenu> selectMenuInfoList(@Param("userId") Integer userId,@Param("systemId")Integer systemId);
	
	
	
}
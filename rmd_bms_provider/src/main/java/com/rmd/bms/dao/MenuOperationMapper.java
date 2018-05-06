package com.rmd.bms.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.rmd.bms.entity.MenuOperation;
import com.rmd.bms.entity.MenuOperationExample;

public interface MenuOperationMapper {
    long countByExample(MenuOperationExample example);

    int deleteByExample(MenuOperationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MenuOperation record);

    int insertSelective(MenuOperation record);

    List<MenuOperation> selectByExample(MenuOperationExample example);

    MenuOperation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MenuOperation record, @Param("example") MenuOperationExample example);

    int updateByExample(@Param("record") MenuOperation record, @Param("example") MenuOperationExample example);

    int updateByPrimaryKeySelective(MenuOperation record);

    int updateByPrimaryKey(MenuOperation record);
    
    List<String> selectOperationCodeList(@Param("userId") Integer userId,@Param("systemId")Integer systemId);
    /**多个条件查询对象*/
	MenuOperation selectByAll(MenuOperation menuOperation);

	List<MenuOperation> selectByAllList(Map<String, Object> whereParams);

}
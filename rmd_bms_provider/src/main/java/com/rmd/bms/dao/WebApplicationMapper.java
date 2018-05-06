package com.rmd.bms.dao;

import com.rmd.bms.entity.WebApplication;
import com.rmd.bms.entity.WebApplicationExample;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface WebApplicationMapper {
    long countByExample(WebApplicationExample example);

    int deleteByExample(WebApplicationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(WebApplication record);

    int insertSelective(WebApplication record);

    List<WebApplication> selectByExample(WebApplicationExample example);

    List<WebApplication> selectByAllList(Map<String, Object> map);
    
    WebApplication selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") WebApplication record, @Param("example") WebApplicationExample example);

    int updateByExample(@Param("record") WebApplication record, @Param("example") WebApplicationExample example);

    int updateByPrimaryKeySelective(WebApplication record);

    int updateByPrimaryKey(WebApplication record);
    /**验证系统重复
     * @param userid */
	WebApplication selectByValidate(WebApplication webApplication);
	/**判断系统下是否有员工*/
	int selectUserCountById(Integer id);

	List<WebApplication> selectByUserId(Integer userid);
	/**多条件查询对象*/
	WebApplication selectByAll(WebApplication webApplication);

	
}
package com.rmd.bms.dao;

import com.rmd.bms.bean.AreaDeliveryInfo;
import com.rmd.bms.entity.AreaDelivery;
import com.rmd.bms.entity.AreaDeliveryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AreaDeliveryMapper {
    long countByExample(AreaDeliveryExample example);

    int deleteByExample(AreaDeliveryExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AreaDelivery record);

    int insertSelective(AreaDelivery record);

    List<AreaDelivery> selectByExample(AreaDeliveryExample example);

    AreaDelivery selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AreaDelivery record, @Param("example") AreaDeliveryExample example);

    int updateByExample(@Param("record") AreaDelivery record, @Param("example") AreaDeliveryExample example);

    int updateByPrimaryKeySelective(AreaDelivery record);

    int updateByPrimaryKey(AreaDelivery record);

    /**
     * 根据参数查询区划信息列表
     * @param params
     * @return
     */
    List<AreaDeliveryInfo> selectInfoByParams(AreaDeliveryInfo params);
}
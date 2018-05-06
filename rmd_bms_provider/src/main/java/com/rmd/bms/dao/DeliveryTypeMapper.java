package com.rmd.bms.dao;

import com.rmd.bms.entity.DeliveryType;
import com.rmd.bms.entity.DeliveryTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DeliveryTypeMapper {
    long countByExample(DeliveryTypeExample example);

    int deleteByExample(DeliveryTypeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DeliveryType record);

    int insertSelective(DeliveryType record);

    List<DeliveryType> selectByExample(DeliveryTypeExample example);

    DeliveryType selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DeliveryType record, @Param("example") DeliveryTypeExample example);

    int updateByExample(@Param("record") DeliveryType record, @Param("example") DeliveryTypeExample example);

    int updateByPrimaryKeySelective(DeliveryType record);

    int updateByPrimaryKey(DeliveryType record);
}
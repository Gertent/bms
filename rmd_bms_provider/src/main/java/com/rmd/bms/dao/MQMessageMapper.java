package com.rmd.bms.dao;

import com.rmd.bms.entity.MQMessage;
import com.rmd.bms.entity.MQMessageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MQMessageMapper {
    long countByExample(MQMessageExample example);

    int deleteByExample(MQMessageExample example);

    int deleteByPrimaryKey(Long messageWraperId);

    int insert(MQMessage record);

    int insertSelective(MQMessage record);

    List<MQMessage> selectByExampleWithBLOBs(MQMessageExample example);

    List<MQMessage> selectByExample(MQMessageExample example);

    MQMessage selectByPrimaryKey(Long messageWraperId);

    int updateByExampleSelective(@Param("record") MQMessage record, @Param("example") MQMessageExample example);

    int updateByExampleWithBLOBs(@Param("record") MQMessage record, @Param("example") MQMessageExample example);

    int updateByExample(@Param("record") MQMessage record, @Param("example") MQMessageExample example);

    int updateByPrimaryKeySelective(MQMessage record);

    int updateByPrimaryKeyWithBLOBs(MQMessage record);

    int updateByPrimaryKey(MQMessage record);
}
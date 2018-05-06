package com.rmd.bms.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rmd.bms.bean.AreaDeliveryInfo;
import com.rmd.bms.dao.AreaDeliveryMapper;
import com.rmd.bms.dao.DeliveryTypeMapper;
import com.rmd.bms.entity.AreaDelivery;
import com.rmd.bms.entity.AreaDeliveryExample;
import com.rmd.bms.entity.AreaDeliveryExample.Criteria;
import com.rmd.bms.entity.DeliveryType;
import com.rmd.bms.service.AreaDeliveryService;

/**
 * 区划管理业务接口实现
 * @author 陈赟 
 * @date 2017年4月1日
 */
@Service("areaDeliveryService")
public class AreaDeliveryServiceImpl implements AreaDeliveryService {

    @Autowired
    private AreaDeliveryMapper areaDeliveryMapper;
    
    @Autowired
    private DeliveryTypeMapper deliveryTypeMapper;

    @Override
    public List<AreaDeliveryInfo> listAreaDeliveryInfo(AreaDeliveryInfo params) {
        List<AreaDeliveryInfo> list = areaDeliveryMapper.selectInfoByParams(params);
        for (AreaDeliveryInfo info : list) {
            if (info.getAreaCode().endsWith("00")) {
                info.setState("closed");
            }
        }
        return list;
    }

    @Override
    public List<DeliveryType> listDeliveryType() {
        return deliveryTypeMapper.selectByExample(null);
    }

    @Override
    @Transactional
    public boolean saveAreaDeliveryInfo(AreaDeliveryInfo params) {
        List<Integer> values = new ArrayList<Integer>();
        String[] areaIds = params.getAreaIds().split(",");
        for (String areaId : areaIds) {
            values.add(Integer.parseInt(areaId));
        }
        AreaDeliveryExample example = new AreaDeliveryExample();
        Criteria criteria = example.createCriteria();
        criteria.andAreaIdIn(values);
        areaDeliveryMapper.deleteByExample(example);
        
        if (StringUtils.isNotEmpty(params.getDeliveryId())) {
            String[] deliveryIds = params.getDeliveryId().split(",");
            for (String areaId : areaIds) {
                for (String deliveryId : deliveryIds) {
                    AreaDelivery record = new AreaDelivery();
                    record.setAreaId(Integer.parseInt(areaId));
                    record.setDeliveryId(Integer.valueOf(deliveryId));
                    record.setCreatetime(new Date());
                    areaDeliveryMapper.insertSelective(record);
                }
            }
        }
        return true;
    }

}

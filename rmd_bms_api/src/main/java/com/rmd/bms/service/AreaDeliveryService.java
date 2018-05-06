package com.rmd.bms.service;

import java.util.List;

import com.rmd.bms.bean.AreaDeliveryInfo;
import com.rmd.bms.entity.DeliveryType;

/**
 * 区划管理业务接口
 * @author 陈赟 
 * @date 2017年4月1日
 */
public interface AreaDeliveryService {

    /**
     * 获取区划信息列表
     * @param params
     * @return
     */
    List<AreaDeliveryInfo> listAreaDeliveryInfo(AreaDeliveryInfo params);
    
    /**
     * 获取配送方式列表
     * @return
     */
    List<DeliveryType> listDeliveryType();
    
    /**
     * 保存配送方式
     * @param params
     * @return
     */
    boolean saveAreaDeliveryInfo(AreaDeliveryInfo params);
}

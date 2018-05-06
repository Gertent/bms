package com.rmd.bms.service;

import com.rmd.bms.bean.AreaInfo;
import com.rmd.bms.entity.SysArea;
import com.rmd.commons.servutils.Notification;

import java.util.List;

/**
 * 区域
 * 
 * @author 陈赟
 * @date 2017年4月6日
 */
public interface AreaService {
    /**
     * 获取区域列表
     * 若parentCode为空，则查出第一级区域 否则，查出下级区域
     * 
     * @param parentCode
     * @return
     */
    Notification<List<SysArea>> listAreaInfo(String parentCode);
    
    /**
     * 根据编码获取单个区域信息
     * 
     * @param areaCode
     * @return
     */
    Notification<SysArea> getAreaInfoByAreaCode(String areaCode);

    /**
     * 根据ID获取单个区域信息
     * 
     * @param areaId
     * @return
     */
    Notification<SysArea> getAreaInfoByAreaId(Integer areaId);
    
    /**
     * 获取所有区域列表
     * @return
     */
    List<AreaInfo> listAllAreaInfo();
    
    /**
     * 获取区域的最后修改时间
     * @return
     */
    long getLastUpdateTime();

    /**
     * 区域信息推送到redis中
     */
	void pushArea2Redis();
}

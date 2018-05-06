package com.rmd.bms.service.impl;

import com.alibaba.fastjson.JSON;
import com.rmd.bms.bean.AreaInfo;
import com.rmd.bms.constant.Constant;
import com.rmd.bms.constant.ConstantRedis;
import com.rmd.bms.dao.SysAreaMapper;
import com.rmd.bms.entity.SysArea;
import com.rmd.bms.entity.SysAreaExample;
import com.rmd.bms.entity.SysAreaExample.Criteria;
import com.rmd.bms.entity.Vo.SysAreaVo;
import com.rmd.bms.service.AreaService;
import com.rmd.commons.servutils.Notification;
import com.rmd.commons.servutils.NotificationBuilder;
import com.rmd.commons.servutils.Notifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("areaService")
public class AreaServiceImpl implements AreaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AreaServiceImpl.class);

    @Autowired
    private SysAreaMapper sysAreaMapper;

    @Autowired
    private AreaRedisService areaRedisService;

    @Override
    public Notification<List<SysArea>> listAreaInfo(String parentCode) {
        LOGGER.debug("listAreaInfo params:parentCode={}", parentCode);
        Notification<List<SysArea>> areaInfoList = NotificationBuilder.buildOne(Notifications.OK);

        // 若parentCode为空，则默认查第一级区域，第一级区域的编码是：86
        if (StringUtils.isEmpty(parentCode)) {
            parentCode = Constant.PARENT_AREA_CODE;
        }

        try {
            SysAreaExample example = new SysAreaExample();
            Criteria criteria = example.createCriteria();
            criteria.andParentCodeEqualTo(parentCode);
            List<SysArea> list = sysAreaMapper.selectByExample(example);

            if (list != null && !list.isEmpty()) {
                areaInfoList.setResponseData(list);
            } else {
                areaInfoList.setNotifCode(Notifications.NOTFOUND.getNotifCode());
            }
        } catch (Exception e) {
            areaInfoList.setNotifCode(Notifications.EXCEPTION.getNotifCode());
            LOGGER.error("listAreaInfo error:", e);
        }

        LOGGER.debug("listAreaInfo result:{}", JSON.toJSONString(areaInfoList));
        return areaInfoList;
    }

    @Override
    public Notification<SysArea> getAreaInfoByAreaCode(String areaCode) {
        LOGGER.debug("getAreaInfoByAreaCode params:areaCode={}", areaCode);
        Notification<SysArea> areaInfo = NotificationBuilder.buildOne(Notifications.OK);

        if (StringUtils.isEmpty(areaCode)) {
            areaInfo.setNotifCode(Notifications.PARAMETER_ERROR.getNotifCode());
            return areaInfo;
        }

        try {
            SysAreaExample example = new SysAreaExample();
            Criteria criteria = example.createCriteria();
            criteria.andAreaCodeEqualTo(areaCode);
            List<SysArea> list = sysAreaMapper.selectByExample(example);

            if (list != null && !list.isEmpty()) {
                areaInfo.setResponseData(list.get(0));
            } else {
                areaInfo.setNotifCode(Notifications.NOTFOUND.getNotifCode());
            }
        } catch (Exception e) {
            areaInfo.setNotifCode(Notifications.EXCEPTION.getNotifCode());
            LOGGER.error("getAreaInfoByAreaCode error:", e);
        }
        return areaInfo;
    }

    @Override
    public Notification<SysArea> getAreaInfoByAreaId(Integer areaId) {
        LOGGER.debug("getAreaInfoByAreaId params:areaId={}", areaId);
        Notification<SysArea> areaInfo = NotificationBuilder.buildOne(Notifications.OK);

        if (areaId == null) {
            areaInfo.setNotifCode(Notifications.PARAMETER_ERROR.getNotifCode());
            return areaInfo;
        }

        try {
            SysArea info = sysAreaMapper.selectByPrimaryKey(areaId);

            if (info != null) {
                areaInfo.setResponseData(info);
            } else {
                areaInfo.setNotifCode(Notifications.NOTFOUND.getNotifCode());
            }
        } catch (Exception e) {
            areaInfo.setNotifCode(Notifications.EXCEPTION.getNotifCode());
            LOGGER.error("getAreaInfoByAreaId error:", e);
        }
        return areaInfo;
    }

    @Override
    public List<AreaInfo> listAllAreaInfo() {
        SysAreaExample example = new SysAreaExample();
        List<SysArea> list = sysAreaMapper.selectByExample(example);

        List<AreaInfo> areaList = new ArrayList<AreaInfo>();
        for (SysArea area : list) {
            AreaInfo areaInfo = new AreaInfo();
            areaInfo.setAreaCode(area.getAreaCode());
            areaInfo.setAreaName(area.getAreaName());
            areaInfo.setParentCode(area.getParentCode());
            areaList.add(areaInfo);
        }
        return this.getChildrenList(Constant.PARENT_AREA_CODE, areaList);
    }

    /**
     * 获取子级区域
     *
     * @param parentCode
     * @param list
     * @return
     */
    private List<AreaInfo> getChildrenList(String parentCode, List<AreaInfo> list) {
        List<AreaInfo> areaList = new ArrayList<AreaInfo>();
        for (AreaInfo area : list) {
            if (area.getParentCode().equals(parentCode)) {
                AreaInfo areaInfo = new AreaInfo();
                areaInfo.setAreaCode(area.getAreaCode());
                areaInfo.setAreaName(area.getAreaName());

                List<AreaInfo> childrenList = getChildrenList(area.getAreaCode(), list);
                areaInfo.setChildren(childrenList);
                areaList.add(areaInfo);
            }
        }
        if (areaList.isEmpty()) {
            return null;
        }
        return areaList;
    }

    @Override
    public long getLastUpdateTime() {
        Date lastUpdateTime = sysAreaMapper.selectLastUpdateTime();
        return lastUpdateTime.getTime();
    }

    @Override
    public void pushArea2Redis() {
        List<SysArea> areaAllList = sysAreaMapper.selectByAll();
        areaRedisService.addAllAreas(ConstantRedis.AREA_KEY_CODE, areaAllList);

        List<String> provinceList = new ArrayList<String>();
        List<String> cityList = new ArrayList<String>();
        List<String> countyList = new ArrayList<String>();
        for(SysArea area : areaAllList) {
            SysAreaVo vo = new SysAreaVo();
            vo.setAreaName(area.getAreaName());
            vo.setAreaCode(area.getAreaCode());
            vo.setParentCode(area.getParentCode());
            String areaStr = JSON.toJSONString(vo);
            if(area.getParentCode().equals(Constant.PARENT_AREA_CODE)) {
                provinceList.add(areaStr);
            } else if (area.getParentCode().endsWith(Constant.CITY_AREA_CODE_SUFFIX)) {
                cityList.add(areaStr);
            } else {
                countyList.add(areaStr);
            }
        }

        areaRedisService.addAreas(ConstantRedis.AREA_KEY_PROVINCE, provinceList);
        areaRedisService.addAreas(ConstantRedis.AREA_KEY_CITY, cityList);
        areaRedisService.addAreas(ConstantRedis.AREA_KEY_COUNTY, countyList);
    }

}

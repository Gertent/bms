package com.rmd.bms.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 区划管理展示信息
 * 包含区域、配送方式、是否启用
 * @author 陈赟 
 * @date 2017年4月1日
 */
public class AreaDeliveryInfo implements Serializable {

    private static final long serialVersionUID = 7159482643737808037L;
    
    private Integer areaId;

    private String areaCode;

    private String areaName;

    private String parentCode;
    
    private String deliveryId;

    private Integer status;
    
    private String type;
    
    private String state;
    
    private String areaIds;

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(String areaIds) {
        this.areaIds = areaIds;
    }

}

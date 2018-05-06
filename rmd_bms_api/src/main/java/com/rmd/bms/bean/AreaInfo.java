package com.rmd.bms.bean;

import java.io.Serializable;
import java.util.List;

public class AreaInfo implements Serializable {

    private static final long serialVersionUID = -6899732283356820234L;
    private String areaCode;
    private String areaName;
    private String parentCode;
    private List<AreaInfo> children;

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

    public List<AreaInfo> getChildren() {
        return children;
    }

    public void setChildren(List<AreaInfo> children) {
        this.children = children;
    }
}

package com.rmd.bms.entity.Vo;

import java.io.Serializable;

public class SysAreaVo implements Serializable{

	
	private static final long serialVersionUID = 8615098327117482916L;

	private Integer id;
	
	private String areaCode;
	
	private String areaName;
	
	private String parentCode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
	
}

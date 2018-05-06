package com.rmd.bms.entity.Vo;

import java.io.Serializable;

public class WebApplicationVo implements Serializable{

	private static final long serialVersionUID = 8038149449092569070L;

	private Integer id;
	
	private String appName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
	
}

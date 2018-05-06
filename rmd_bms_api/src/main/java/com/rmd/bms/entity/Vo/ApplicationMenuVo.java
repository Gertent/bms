package com.rmd.bms.entity.Vo;

import java.io.Serializable;

public class ApplicationMenuVo implements Serializable{

	private static final long serialVersionUID = -3672740298678472379L;
	
	private Integer id;
	private String menuCode;
	private String menuUrl;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
}

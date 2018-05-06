package com.rmd.bms.entity.Vo;

import java.io.Serializable;

public class DepartmentVo implements Serializable{

	private static final long serialVersionUID = -2407691292466714110L;

	private Integer id;
	
	private String deptname;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDeptname() {
		return deptname;
	}

	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	
	
}

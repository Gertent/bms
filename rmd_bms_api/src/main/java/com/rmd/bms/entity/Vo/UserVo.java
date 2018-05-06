package com.rmd.bms.entity.Vo;

import java.io.Serializable;
import java.util.List;

import com.rmd.bms.entity.MenuOperation;

public class UserVo implements Serializable{

	private static final long serialVersionUID = 1595478950074866275L;

	private Integer id;

    private String loginname;
    
    private String jobNum;

    private List<ApplicationMenuVo> ApplicationMenuVo;
    
    private List<MenuOperation> MenuOperation;
	

	public Integer getId() {
		return id;
	}

	public String getJobNum() {
		return jobNum;
	}

	public void setJobNum(String jobNum) {
		this.jobNum = jobNum;
	}

	public List<ApplicationMenuVo> getApplicationMenuVo() {
		return ApplicationMenuVo;
	}

	public void setApplicationMenuVo(List<ApplicationMenuVo> applicationMenuVo) {
		ApplicationMenuVo = applicationMenuVo;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public List<MenuOperation> getMenuOperation() {
		return MenuOperation;
	}

	public void setMenuOperation(List<MenuOperation> menuOperation) {
		MenuOperation = menuOperation;
	}
	
	
}

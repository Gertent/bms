package com.rmd.bms.entity.common;

import java.io.Serializable;

/**
 * 实体基类，包括分页参数
 * @author Administrator
 */
public class BaseEntity implements Serializable{
	
    private static final long serialVersionUID = 6803944837605114723L;
	
	private int pageNum;    // 第几页
	private int pageSize;   // 每页记录数
    private int startNum;   // 起始行
    
	public int getPageNum() {
		return pageNum;
	}
	
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public int getStartNum() {
		startNum = (pageNum-1) * pageSize;
		return startNum;
	}

}

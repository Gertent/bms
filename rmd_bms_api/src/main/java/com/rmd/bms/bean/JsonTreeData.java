package com.rmd.bms.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonTreeData implements Serializable {
	
	private static final long serialVersionUID = -9101801255630381760L;
	
	private Integer id;//id
	private Integer pid;//父id
	private String text;//文本名称
	private String state;//json 'open','closed'
	private String checked;//是否选中
	private Map<String,String> attributes = new HashMap<String,String>();
	private List<JsonTreeData> children = new ArrayList<JsonTreeData>();
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public List<JsonTreeData> getChildren() {
		return children;
	}
	public void setChildren(List<JsonTreeData> children) {
		this.children = children;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public Map<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
}

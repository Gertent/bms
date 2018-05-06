package com.rmd.bms.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * 需要记录的请求日志实体
 * @author : liu
 * Date : 2017/4/7
 */
public class UserRequestLog implements Serializable {

    private String id;
    private String userName;
    private String reqIp;
    private String reqUrl;
    private String param;
    private String reqMethod;
    private String reqSystem;
    private String reqBrowser;
    private String appNo;
    private Date reqDate;
    private String remark;
    private String appName;

    public UserRequestLog() {
    }
    
    
    
    public UserRequestLog(String id, String userName, String reqIp, String reqUrl, String param, String reqMethod,
			String reqSystem, String reqBrowser, String appNo, Date reqDate, String remark) {
		super();
		this.id = id;
		this.userName = userName;
		this.reqIp = reqIp;
		this.reqUrl = reqUrl;
		this.param = param;
		this.reqMethod = reqMethod;
		this.reqSystem = reqSystem;
		this.reqBrowser = reqBrowser;
		this.appNo = appNo;
		this.reqDate = reqDate;
		this.remark = remark;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getReqIp() {
        return reqIp;
    }

    public void setReqIp(String reqIp) {
        this.reqIp = reqIp;
    }

    public String getReqUrl() {
        return reqUrl;
    }

    public void setReqUrl(String reqUrl) {
        this.reqUrl = reqUrl;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getReqMethod() {
        return reqMethod;
    }

    public void setReqMethod(String reqMethod) {
        this.reqMethod = reqMethod;
    }

    public String getReqSystem() {
        return reqSystem;
    }

    public void setReqSystem(String reqSystem) {
        this.reqSystem = reqSystem;
    }

    public String getReqBrowser() {
        return reqBrowser;
    }

    public void setReqBrowser(String reqBrowser) {
        this.reqBrowser = reqBrowser;
    }

    public String getAppNo() {
        return appNo;
    }

    public void setAppNo(String appNo) {
        this.appNo = appNo;
    }

    public Date getReqDate() {
        return reqDate;
    }

    public void setReqDate(Date reqDate) {
        this.reqDate = reqDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
    
}

package com.rmd.bms.entity;

import java.io.Serializable;
import java.util.Date;

public class WebApplication implements Serializable{
    
	private static final long serialVersionUID = -4967193073079538755L;

	private Integer id;

    private String appNo;

    private String identify;

    private String appName;

    private String appUrl;

    private Date createdate;

    private Integer sortNum;

    private String applicationImg;

    private String secret;
    
    private Integer appNameCount;//系统名称数
    
    private Integer identifyCount;//标识数
    
    private Integer appNoCount;//编号数
    
    private Integer appUrlCount;//域名 网址 数
    
    private String rolename;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppNo() {
        return appNo;
    }

    public void setAppNo(String appNo) {
        this.appNo = appNo == null ? null : appNo.trim();
    }

    public String getIdentify() {
        return identify;
    }

    public void setIdentify(String identify) {
        this.identify = identify == null ? null : identify.trim();
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl == null ? null : appUrl.trim();
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public String getApplicationImg() {
        return applicationImg;
    }

    public void setApplicationImg(String applicationImg) {
        this.applicationImg = applicationImg == null ? null : applicationImg.trim();
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret == null ? null : secret.trim();
    }

	public Integer getAppNameCount() {
		return appNameCount;
	}

	public void setAppNameCount(Integer appNameCount) {
		this.appNameCount = appNameCount;
	}

	public Integer getIdentifyCount() {
		return identifyCount;
	}

	public void setIdentifyCount(Integer identifyCount) {
		this.identifyCount = identifyCount;
	}

	public Integer getAppNoCount() {
		return appNoCount;
	}

	public void setAppNoCount(Integer appNoCount) {
		this.appNoCount = appNoCount;
	}

	public Integer getAppUrlCount() {
		return appUrlCount;
	}

	public void setAppUrlCount(Integer appUrlCount) {
		this.appUrlCount = appUrlCount;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
}
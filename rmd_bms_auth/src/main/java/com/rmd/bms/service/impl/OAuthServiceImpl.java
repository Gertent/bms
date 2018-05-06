package com.rmd.bms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.rmd.bms.entity.User;
import com.rmd.bms.service.OAuthService;

@Service("oAuthService")
public class OAuthServiceImpl implements OAuthService {
	
	private Cache cache;
	
	@Autowired
    public OAuthServiceImpl(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("code-cache");
    }

	@Override
	public boolean checkAuthCode(String authCode) {
		return cache.get(authCode) != null;
	}

	@Override
	public String getUsernameByAuthCode(String authCode) {
		return (String)cache.get(authCode).get();
	}

	@Override
	public void addAuthSession(String authCode, String sessionId) {
		cache.put(authCode, sessionId);
	}

	@Override
	public void addSessionUser(String sessionId, User user) {
		cache.put(sessionId, user);
	}

	@Override
	public User getUserByAuthCode(String authCode) {
		String sessionId = (String)cache.get(authCode).get();
		return (User)cache.get(sessionId).get();
	}

	@Override
	public boolean checkSession(String sessionId) {
		return cache.get(sessionId) != null;
	}

	@Override
	public String getSessionIdByAuthCode(String authCode) {
		return (String)cache.get(authCode).get();
	}

	@Override
	public void addSessionAppNo(String session, String appNo) {
		List<String> appNoList;
		if(cache.get("app_use_" + session) == null){
			appNoList = new ArrayList<String>();
		}else{
			appNoList = (List<String>) cache.get("app_use_" + session).get();
		}
		appNoList.add(appNo);
		cache.put("app_use_" + session, appNoList);
	}

	@Override
	public List<String> getAppNoListBySession(String session) {
		if(cache.get("app_use_" + session)!=null){
			return (List<String>) cache.get("app_use_" + session).get();
		}
		return null;
	}

	@Override
	public User getUserBySession(String sessionId) {
		return (User)cache.get(sessionId).get();
	}

	@Override
	public void addSessionAppNoList(String session, List<String> appNoList) {
		cache.put("app_use_" + session, appNoList);
	}
	
}

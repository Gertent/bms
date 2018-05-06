package com.rmd.bms.service;

import java.util.List;

import com.rmd.bms.entity.User;

/**
 * 授权服务 主要用于缓存
 * @author zuoguodong
 *
 */
public interface OAuthService {

    /**
     * 校验近授权码是否存在
     * @param authCode
     * @return
     */
    public boolean checkAuthCode(String authCode);
    
    /**
     * 跟据授权码获取用户名
     * @param accessToken
     * @return
     */
    public String getUsernameByAuthCode(String authCode);
    
    /**
     * 缓存authcode和session
     * @param authCode
     * @param sessionId
     * @return
     */
    public void addAuthSession(String authCode,String sessionId);
    
    /**
     * 通过authCode获取session
     * @param authCode
     * @return
     */
    public String getSessionIdByAuthCode(String authCode);
    
    /**
     * 保存session和user的对应关系
     * @param sessionId
     * @param user
     * @return
     */
    public void addSessionUser(String sessionId,User user);
    
    /**
     * 通过session获取当前用户
     * @param sessionId
     * @return
     */
    public User getUserBySession(String sessionId);
    
    /**
     * 检测session是否存在
     * @param sessionId
     * @return
     */
    public boolean checkSession(String sessionId);
    
    /**
     * 通过authcode获取用户信息
     * @param authCode
     * @return
     */
    public User getUserByAuthCode(String authCode);
    
    /**
     * 记录一个session登录的系统编号，将来用于退出
     * @param session
     * @param appNo
     */
    public void addSessionAppNo(String session,String appNo);
    
    /**
     * 保存session和登录系统的对应关系
     * @param session
     * @param appNoList
     */
    public void addSessionAppNoList(String session,List<String> appNoList);
    
    /**
     * 根据session获取登录过的系统
     * @param session
     * @return
     */
    public List<String> getAppNoListBySession(String session);
}

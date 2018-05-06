package com.rmd.bms.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rmd.bms.entity.User;
import com.rmd.bms.service.OAuthService;
import com.rmd.bms.service.UserService;
import com.rmd.bms.service.WebApplicationService;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 校验token获取用户信息
 * @author Administrator
 */
@Controller
public class AccessTokenController {

	@Autowired
	private WebApplicationService clientService;
	@Autowired
	private OAuthService oAuthService;
	
    @RequestMapping("/accessToken")
    @ResponseBody
    public Map<String,Object> token(HttpServletRequest request){
    	Map<String,Object> result = new HashMap<String,Object>();
    	String clientId = request.getParameter("clientId");
    	String secret = request.getParameter("clientSecret");
    	String authCode = request.getParameter("code");
    	if(StringUtils.isEmpty(clientId)){
    		result.put("error", "系统唯一标识不能为空");
    		return result;
    	}
    	if(StringUtils.isEmpty(secret)){
    		result.put("error", "系统加密标识不能为空");
    		return result;
    	}
    	if(StringUtils.isEmpty(authCode)){
    		result.put("error", "系统授权码不能为空");
    		return result;
    	}
        if(!clientService.checkClient(clientId, secret)){
        	result.put("error", "系统唯一标识或系统加密标识不正确");
        	return result;
        }
        if(!oAuthService.checkAuthCode(authCode)){
        	result.put("error", "系统授权码不正确");
    		return result;
        }
        User user = oAuthService.getUserByAuthCode(authCode);
        String sessionId = oAuthService.getSessionIdByAuthCode(authCode);
        if(null==user.getAppNumber()||"".equals(user.getAppNumber())){
        	user.setAppNumber(clientId);
        	oAuthService.addSessionUser(sessionId, user);
        }
        oAuthService.addSessionAppNo(sessionId, clientId);
        result.put("user", user);
        return result;
      
    }

}

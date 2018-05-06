package com.rmd.bms.auth.filter;

import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.rmd.bms.entity.User;
import com.rmd.bms.service.OAuthService;
import com.rmd.bms.service.WebApplicationService;

public class OAuth2LogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter {
	private static final Logger log = LoggerFactory.getLogger(OAuth2LogoutFilter.class);
	
	@Autowired
	private OAuthService oAuthService;
	@Autowired
	private WebApplicationService clientService;
	
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		try {
			Subject subject = getSubject(request, response);
			String sessionId = subject.getSession().getId().toString();
			List<String> list = oAuthService.getAppNoListBySession(sessionId);
			//如果当前没有系统登录,退出单点登录的主用户，跳转到登录页面
			if(list == null || list.size()==0){
				User user = oAuthService.getUserBySession(subject.getSession().getId().toString());
				String clientUrl = clientService.selectByAppNo(user.getAppNumber()).getAppUrl();
				String rediectUrl = "/authorize?redirect_uri="+clientUrl;
				subject.logout();
				issueRedirect(request, response, rediectUrl);
			}else{
				//否则先通知其它登录过的系统退出
				this.logout(request,response);
			}
		} catch (Exception ise) {
			log.debug("Encountered session exception during logout.  This can generally safely be ignored.", ise);
		}
		return false;
	}
	
	private void logout(ServletRequest request, ServletResponse response) throws Exception{
		Subject subject = getSubject(request, response);
		User user = oAuthService.getUserBySession(subject.getSession().getId().toString());
		String sessionId = subject.getSession().getId().toString();
		List<String> list = oAuthService.getAppNoListBySession(sessionId);
		if(list != null && list.size()>0){
			Iterator<String> it = list.iterator();
			while(it.hasNext()){
				String appNo = it.next();
				//从缓存当中删除当前系统
				it.remove();
				oAuthService.addSessionAppNoList(sessionId, list);
				String url = clientService.selectByAppNo(appNo).getAppUrl();
				url += "/logout";
				issueRedirect(request, response, url);
				break;
			}
			
		}
	}
}

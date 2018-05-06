package com.rmd.bms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rmd.bms.entity.ApplicationMenu;
import com.rmd.bms.entity.User;
import com.rmd.bms.entity.WebApplication;
import com.rmd.bms.service.ApplicationMenuService;
import com.rmd.bms.service.WebApplicationService;

/**
 * 加载主页信息
 * @author zuoguodong
 */
@Controller
public class IndexController {

	@Autowired
	private WebApplicationService webApplicationService;
	@Autowired
	private ApplicationMenuService applicationMenuService;
	
	/**
	 * 初始化主页信息
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/index")
	public ModelAndView index(Model model, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("index");
		Subject subject = SecurityUtils.getSubject(); 
		User user = (User) subject.getPrincipal();
		modelAndView.addObject("user",user);
		WebApplication app = webApplicationService.selectByAppNo(user.getAppNumber());
		user.setSystemId(app.getId());
		List<ApplicationMenu> modlist = applicationMenuService.selectMenuList(user.getId(), app.getId());
		modelAndView.addObject("modlist", modlist);
		return modelAndView;
	}
	
	@RequestMapping(value = "/error")
	public ModelAndView error(Model model, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("error");
		return modelAndView;
	}
}

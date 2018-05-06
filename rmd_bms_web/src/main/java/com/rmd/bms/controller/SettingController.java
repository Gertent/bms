package com.rmd.bms.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.StringUtil;
import com.rmd.bms.controller.base.AbstractAjaxController;
import com.rmd.bms.entity.Department;
import com.rmd.bms.entity.User;
import com.rmd.bms.entity.WebApplication;
import com.rmd.bms.entity.common.PageBean;
import com.rmd.bms.service.DepartmentService;
import com.rmd.bms.service.OperationLogService;
import com.rmd.bms.service.RoleService;
import com.rmd.bms.service.UserService;
import com.rmd.bms.service.WebApplicationService;
import com.rmd.bms.util.MD5;
import com.rmd.bms.util.web.WebMessageCode;

@Controller
@RequestMapping("/settings")
public class SettingController extends AbstractAjaxController{
	
	//日志管理器
	private static Logger logger = Logger.getLogger(DepartmentController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private OperationLogService operationService;
	
	@Autowired
	private WebApplicationService webApplicationService;
	
	@Autowired
	private DepartmentService departMentService;
	
	/**
	  * @Description:账户信息页
	  * @author:yuyang
	  * @param request
	  * @param response
	  * @param id
	  * @return
	  * 2017年4月5日 
	  */
	@RequestMapping(value="/informationsize")
	public ModelAndView getInformationize(HttpServletRequest request, HttpServletResponse response
								 ){
		ModelAndView model = new ModelAndView("settings/informationsize");
		try {
			Integer userid = ((User) SecurityUtils.getSubject().getPrincipal()).getId();
			User user =userService.selectByPrimaryKey(Integer.valueOf(userid));
			user.setPassword(MD5.encodeMD5(user.getPassword()));
			WebApplication webApplication = webApplicationService.getSystemRoleById(userid);
			
			user.setRoleName(webApplication.getRolename());
			//所有部门
			List<String> list = new ArrayList<String>();
			
			Department department = departMentService.selectByPrimaryKey(user.getDeptid());
			if(department !=null){
				list.add(department.getDeptname());
				deptnameHierarchy(list,department.getId());
				Collections.reverse(list);
				String join = org.apache.commons.lang3.StringUtils.join(list.toArray(),",");
				String[] split = join.split(",");
				String deptNames = new String();
				for (String string : split) {
					deptNames+=string+"/";
				}
				deptNames=deptNames.substring(0, deptNames.length()-1);
				user.setDeptNames(deptNames);
			}else{
				user.setDeptNames("");
			}
			model.addObject("user", user);
		} catch (Exception e) {
			logger.error("informationsize：账户信息查询异常 !",e);
			e.printStackTrace();
		}
		return model;
	}
	/**
	  * @Description:查询部门的所有层级
	  * @author:yuyang
	  * @param request
	  * @param response
	  * @param ids
	  * @param status
	  * 2017年4月23日 
	  */
	private void deptnameHierarchy(List<String> list , Integer id){
			Department department = departMentService.selectDepartNamesById(id);
			if(department !=null){
				list.add(department.getDeptname());
				deptnameHierarchy(list,department.getId());
			}else{
				return ;
			}
	}
	
	
	/**
	  * @Description:账户信息
	  * @author:yuyang
	  * @param request
	  * @param response
	  * @param id
	  * @param mobile
	  * @param email
	  * 2017年4月12日 
	  */
	@RequestMapping(value="/updateInformation")
	@ResponseBody
	public void updateInformation(HttpServletRequest request, HttpServletResponse response
								,@RequestParam("id") String id
								,@RequestParam("mobile") String mobile
								,@RequestParam("email") String email
								,@RequestParam("tel") String tel){
		User user = new User();
		user.setId(Integer.valueOf(id));
		user.setMobile(mobile);
		user.setEmail(email);
		user.setTel(tel);
		int result = userService.updateByAccountInfomation(user);
		if(result ==1){
			writeOutWebMessage(WebMessageCode.OPERATE_SUCESS, request, response);
		}else{
			writeOutWebMessage(WebMessageCode.OPERATE_FAILED, request, response);
		}
	}
	/**
	  * @Description:修改密码页
	  * @author:yuyang
	  * @param request
	  * @param response
	  * @param id
	  * @return
	  * 2017年4月5日 
	  */
	@RequestMapping(value="/updatePassPage")
	public ModelAndView updatePassPage(HttpServletRequest request, HttpServletResponse response
				){
		ModelAndView model = new ModelAndView("settings/changePassword");
		try {
			Integer userid = ((User) SecurityUtils.getSubject().getPrincipal()).getId();
			User user =userService.selectByPrimaryKey(Integer.valueOf(userid));
			model.addObject("user", user);
		} catch (Exception e) {
		}
		return model;
	}
	/**
	  * @Description:修改密码
	  * @author:yuyang
	  * @param request
	  * @param response
	  * @param id
	  * @return
	  * 2017年4月5日 
	  */
	@RequestMapping(value="/updatePass")
	@ResponseBody
	public String updatePass(HttpServletRequest request, HttpServletResponse response
								  ,@RequestParam("id") String id
								  ,@RequestParam("password") String password
								  ,@RequestParam("newPassword") String newPassword
								  ,@RequestParam("confirmPassword") String confirmPassword
							   ){
		try {
			Integer userid = ((User) SecurityUtils.getSubject().getPrincipal()).getId();
			int result = userService.updateByPassword(userid,password,newPassword,confirmPassword);
			if(result == 3){
				return "confiremFalse";
			}
			if(result == 2){
				return "passwordFalse";
			}
			if(result ==1){
				return "true";
			}
		} catch (Exception e) {
			logger.error("updatePass：修改密码异常！",e);
			e.printStackTrace();
		}
		return "false";
	}
	
	/**
	  * @Description:操作日志页
	  * @author:yuyang
	  * @param request
	  * @param response
	  * @param id
	  * @return
	  * 2017年4月5日 
	  */
	@RequestMapping(value="/operationLogPage")
	public ModelAndView operationLog(HttpServletRequest request, HttpServletResponse response
										){
		ModelAndView model = new ModelAndView("settings/operationLog");
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		WebApplication webApplication = webApplicationService.selectByAppNo(user.getAppNumber());
		//查询系统表
		List<WebApplication> webApplicationList = new ArrayList<WebApplication>();
		if(webApplication.getIdentify().equals("bms")){
			webApplicationList = roleService.getWebApplicationList();
		}else{
			webApplicationList.add(webApplication);
		}
		model.addObject("webApplicationList", webApplicationList);
		return model;
	}
	
	
	/**
	  * @Description:操作日志列表
	  * @author:yuyang
	  * @param request
	  * @param response
	  * @param loginname
	  * @param operationtime
	  * @param operation
	  * @return
	  * 2017年4月5日 
	  */
	@RequestMapping(value="/operationLog")
	@ResponseBody
	public Map<String, Object> operationLog(HttpServletRequest request, HttpServletResponse response
								  ,@RequestParam(value = "page",required=false) Integer page
								  ,@RequestParam(value = "rows",required=false) Integer rows
								  ,@RequestParam(value = "loginname",required=false) String loginname
								  ,@RequestParam(value = "starttime", required=false) String starttime
								  ,@RequestParam(value = "endtime", required=false) String endtime
								  ,@RequestParam(value = "operation",required=false) String operation
								  ,@RequestParam(value = "systemId",required=false) String systemId
								   ){
		Map<String, Object> map = new HashMap<String, Object>();
		try { 
			Map<String, Object> whereParams = new HashMap<String, Object>();
			if(StringUtil.isNotEmpty(systemId)){
				whereParams.put("systemId", systemId);
			}else{
				User user = (User) SecurityUtils.getSubject().getPrincipal();
				WebApplication webApplication = webApplicationService.selectByAppNo(user.getAppNumber());
				if(!"bms".equals(webApplication.getIdentify())){
					whereParams.put("systemId", webApplication.getAppNo());
				}
			}
			if(!StringUtil.isEmpty(loginname)){
				whereParams.put("loginname", loginname);
			}
			if(!StringUtil.isEmpty(starttime)){
				whereParams.put("starttime", starttime);
			}
			if(!StringUtil.isEmpty(endtime)){
				whereParams.put("endtime", endtime);
			}
			if(!StringUtil.isEmpty(operation)){
				whereParams.put("operation", operation);
			}
			PageBean<Map<String, Object>> pageBean = operationService.getOperationList(page, rows, whereParams);
			map.put("total", pageBean.getTotal());
			map.put("rows", pageBean.getList());
			JSONObject json = JSONObject.fromObject(map);
			map.put("operationList",json);
		} catch (Exception e) {
			logger.error("updatePass：修改密码异常！",e);
			e.printStackTrace();
		}
		return map;
	}
	
}

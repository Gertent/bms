package com.rmd.bms.controller;

import com.github.pagehelper.StringUtil;
import com.rmd.bms.bean.JsonTreeData;
import com.rmd.bms.controller.base.AbstractAjaxController;
import com.rmd.bms.entity.Role;
import com.rmd.bms.entity.User;
import com.rmd.bms.entity.Vo.RoleVo;
import com.rmd.bms.entity.WebApplication;
import com.rmd.bms.entity.common.PageBean;
import com.rmd.bms.service.ApplicationMenuService;
import com.rmd.bms.service.MenuOperationService;
import com.rmd.bms.service.RoleService;
import com.rmd.bms.service.WebApplicationService;
import com.rmd.bms.util.web.WebMessageCode;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController extends AbstractAjaxController{
	//日志管理器
	private static Logger logger = Logger.getLogger(DepartmentController.class);
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private WebApplicationService webApplicationService;
	
	@Autowired
	private ApplicationMenuService applicationMenuService;
	
	@Autowired
	private MenuOperationService menuOperationService;
	
	@RequestMapping("/rolePage")
	public ModelAndView departmentPage(HttpServletRequest request, HttpServletResponse response){
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		WebApplication webApplication = webApplicationService.selectByAppNo(user.getAppNumber());
		//查询系统表
		List<WebApplication> webApplicationList = new ArrayList<WebApplication>();
		if(webApplication.getIdentify().equals("bms")){
			webApplicationList = roleService.getWebApplicationList();
		}else{
			webApplicationList.add(webApplication);
		}
		ModelAndView model = new ModelAndView("role/roleManage");
		model.addObject("webApplicationList", webApplicationList);
		return model;
	}
	
	/**
	  * @Description:角色列表
	  * @author:yuyang
	  * @param request
	  * @param response
	  * @return
	  * 2017年3月29日 
	  */
	@RequestMapping("/listRole")
	@ResponseBody
	public Map<String, Object> listRole(HttpServletRequest request,HttpServletResponse response
										,@RequestParam(value = "page",required=false) Integer page
										,@RequestParam(value = "rows",required=false) Integer rows
										,@RequestParam(value = "systemId",required=false) String systemId
										,@RequestParam(value = "rolename",required=false) String rolename
										,@RequestParam(value = "status",required=false) String status){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			Map<String, Object> whereParams = new HashMap<String, Object>();
			if(StringUtil.isNotEmpty(systemId) || "".equals(systemId)){
				whereParams.put("systemId", systemId);
			}else{
				User user = (User) SecurityUtils.getSubject().getPrincipal();
				WebApplication webApplication = webApplicationService.selectByAppNo(user.getAppNumber());
				whereParams.put("systemId", webApplication.getId());
			}
			if(StringUtil.isNotEmpty(rolename)){
				whereParams.put("rolename", rolename);
			}
			if(StringUtil.isNotEmpty(status)){
				whereParams.put("status", Integer.valueOf(status));
			}
			PageBean<Role> pageBean = roleService.getRoleList(page, rows, whereParams);
			List<Role> rolelist = pageBean.getList();
			map.put("total", pageBean.getTotal());
			map.put("rows", rolelist);
			JSONObject json = JSONObject.fromObject(map);
			map.put("rolelist",json);
		} catch (Exception e) {
			logger.error("listRole：角色列表查询异常！",e);
			e.printStackTrace();
		}
		return map;
	}
	/**
	  * @Description:锁定 解锁
	  * @author:yuyang
	  * @param request
	  * @param response
	  * @param ids
	  * @param status
	  * 2017年3月29日 
	  */
	@RequestMapping("/updateLockStatusById")
	@ResponseBody
	public void updateLockStatusById(HttpServletRequest request,HttpServletResponse response
										,@RequestParam(value = "ids",required=false) String ids
										,@RequestParam(value = "status",required=false) String status){
		try {
			Integer userid = ((User) SecurityUtils.getSubject().getPrincipal()).getId();
			int result = roleService.updateStatus(userid,ids,status);
			if(result >0){
				writeOutWebMessage(WebMessageCode.OPERATE_SUCESS, request, response);
			}else{
				writeOutWebMessage(WebMessageCode.OPERATE_FAILED, request, response);
			}
		} catch (Exception e) {
			logger.error("updateLockStatusById：员工修改状态异常！",e);
			e.printStackTrace();
		}
	}
	
	/**
	  * @Description:删除角色
	  * @author:yuyang
	  * @param request
	  * @param response
	  * @param ids
	  * 2017年3月29日 
	 * @return 
	  */
	@RequestMapping("/deleteroleById")
	@ResponseBody
	public String deleteroleById(HttpServletRequest request,HttpServletResponse response
										,@RequestParam(value = "ids",required=false) String ids){
		try {
			int result = roleService.deleteRoleById(ids);
			if(result >0){
				return "true";
			}
		} catch (Exception e) {
			logger.error("deleteUserById：删除员工异常！",e);
			e.printStackTrace();
		}
		return "false";
	}
	
	/**
	  * @Description:添加角色弹出框
	  * @author:yuyang
	  * @param request
	  * @param response
	  * @return
	  * 2017年3月29日 
	  */
	@RequestMapping("/roleAddPage")
	@ResponseBody
	public ModelAndView roleAddPage(HttpServletRequest request,HttpServletResponse response){
		ModelAndView model = new ModelAndView("role/roleAdd");
		try {
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
		} catch (Exception e) {
			logger.error("roleAddPage：添加角色弹出框！",e);
			e.printStackTrace();
		}
		return model;
	}
	
	
	/**
	  * @Description: 菜单树形操作
	  * @author:yuyang
	  * @param request
	  * @param response
	  * @param m
	  * @return
	  * 2017年3月31日 
	  */
	@RequestMapping("/getTreeEnumInfo")
	@ResponseBody
	public List<JsonTreeData> getTreeEnumInfo(HttpServletRequest request,HttpServletResponse response){
		String systemId = request.getParameter("systemId");
		//获取菜单list
		List<JsonTreeData> treeDataList = applicationMenuService.selectMenuInfoListById(Integer.valueOf(systemId));
		return treeDataList;
	}
	/**
	  * @Description:添加角色
	  * @author:yuyang
	  * @param request
	  * @param response
	  * @param role
	  * @return
	  * 2017年3月29日 
	  */
	@RequestMapping("/roleAdd")
	@ResponseBody
	public String roleAdd(HttpServletRequest request,HttpServletResponse response
								,@ModelAttribute("role") Role role
								){
		try {
			User user = (User) SecurityUtils.getSubject().getPrincipal();
			int result = roleService.insertRole(user,role);
			if(result == 2){
				return "rolenameFalse";
			}
			if(result == 1){
				return "true";
			}
			
		} catch (Exception e) {
			logger.error("roleAdd：添加角色异常！",e);
			e.printStackTrace();
		}
		return "false";
	}
	
	/**
	  * @Description:编辑弹出框
	  * @author:yuyang
	  * @param request
	  * @param response
	  * @param id
	  * @return
	  * 2017年3月29日 
	  */
	@RequestMapping("/roleEditPage")
	@ResponseBody
	public ModelAndView roleEditPage(HttpServletRequest request,HttpServletResponse response
									,@RequestParam(value = "id",required=false) String id){
		ModelAndView model = new ModelAndView("role/roleEdit");
		//角色信息
		Role role = roleService.selectByPrimaryKey(Integer.valueOf(id));
		//系统信息
		WebApplication webApplication = webApplicationService.selectByPrimaryKey(role.getSystemId());
		//菜单信息
		List<Map<String,String>> menuList = menuOperationService.getMenuAndOperationByRoleId(id);
		Map<String,List<Map<String,String>>> map = new HashMap<String,List<Map<String,String>>>();
		map.put("menuList", menuList);
		model.addObject("role", role);
		model.addObject("menuList",JSONObject.fromObject(map));
		model.addObject("webApplication", webApplication);
		return model;
	}
	
	/**
	  * @Description:编辑角色
	  * @author:yuyang
	  * @param request
	  * @param response
	  * @param role
	  * @return
	  * 2017年3月29日 
	  */
	@RequestMapping("/roleEdit")
	@ResponseBody
	public String roleEdit(HttpServletRequest request, HttpServletResponse response
								, RoleVo role
								){
		try {
			User user = (User) SecurityUtils.getSubject().getPrincipal();
			int result = roleService.updateRole(role,user);
			if(result == 2){
				return "rolenameFalse";
			}
			if(result == 1){
				return "true";
			}
		} catch (Exception e) {
			logger.error("userAdd：角色修改异常！",e);
			e.printStackTrace();
		}
		return "false";
	}
	
	/**
	  * @Description:分配角色 ----根据userid 和系统id获取角色数据
	  * @author:yuyang
	  * @param request
	  * @param response
	  * @param systemId
	  * @param id
	  * @return
	  * 2017年4月7日 
	  */
	@RequestMapping("/getRoleListBySystemId")
	@ResponseBody
	public Map<String, Object> getRoleListBySystemId(HttpServletRequest request,HttpServletResponse response
				,@RequestParam("systemId") String systemId
				,@RequestParam("userid") String userid){
		Map<String, Object> map = roleService.getRoleListBySystemId(systemId,userid);
		return map;
	}
}

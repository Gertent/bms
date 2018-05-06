package com.rmd.bms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.StringUtil;
import com.rmd.bms.controller.base.AbstractAjaxController;
import com.rmd.bms.entity.WebApplication;
import com.rmd.bms.entity.common.PageBean;
import com.rmd.bms.service.WebApplicationService;
import com.rmd.bms.util.web.WebMessageCode;

@Controller
@RequestMapping("/webApplication")
public class WebApplicationController extends AbstractAjaxController{
	
	//日志管理器
	private static Logger logger = Logger.getLogger(DepartmentController.class);
	
	@Autowired
	private WebApplicationService webApplicationService;
	
	/**
	  * @Description:跳转系统页面
	  * @author:yuyang
	  * @param request
	  * @param response
	  * @return
	  * 2017年3月30日 
	  */
	@RequestMapping("/webApplicationPage")
	public ModelAndView webApplicationPage(HttpServletRequest request, HttpServletResponse response){
		ModelAndView model = new ModelAndView("webApplication/webApplicationManage");
		return model;
	}
	
	/**
	  * @Description:系统列表
	  * @author:yuyang
	  * @return
	  * 2017年3月29日 
	  */
	@RequestMapping("/getWebApplicationList")
	@ResponseBody
	public Map<String, Object> getWebApplicationList(HttpServletRequest request,HttpServletResponse response
									,@RequestParam(value = "page",required=false) Integer page
									,@RequestParam(value = "rows",required=false) Integer rows
									,@RequestParam(value = "appName",required=false) String appName
									,@RequestParam(value = "identify",required=false) String identify
									,@RequestParam(value = "appNo",required=false) String appNo
									){
		
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Map<String, Object> whereParams = new HashMap<String, Object>();
			if(StringUtil.isNotEmpty(appName)){
				whereParams.put("appName", appName);
			}
			if(StringUtil.isNotEmpty(identify)){
				whereParams.put("identify", identify);
			}
			if(StringUtil.isNotEmpty(appNo)){
				whereParams.put("appNo", appNo);
			}
			PageBean<WebApplication> pageBean = webApplicationService.getWebApplicationList(page, rows, whereParams);
			List<WebApplication> webApplicationList = pageBean.getList();
			map.put("total", pageBean.getTotal());
			map.put("rows", webApplicationList);
			JSONObject json = JSONObject.fromObject(map);
			map.put("webApplicationList",json);
		} catch (Exception e) {
			logger.error("listWebApplication：系统列表查询异常！",e);
			e.printStackTrace();
		}
		return map;
	}
	
	
	/**
	  * @Description:修改排序
	  * @author:yuyang
	  * @param request
	  * @param response
	  * 2017年3月30日 
	  */
	@RequestMapping("/categorySort")
	@ResponseBody
	public void categorySort(HttpServletRequest request, HttpServletResponse response){
		try {
			String sort = request.getParameter("index");
			String id = request.getParameter("id");
			int result = webApplicationService.updateSort(Integer.parseInt(sort),Integer.parseInt(id));
			if(result > 0){
				writeOutWebMessage(WebMessageCode.OPERATE_SUCESS, request, response);
			}else{
				writeOutWebMessage(WebMessageCode.OPERATE_FAILED, request, response);
			}
		} catch (Exception e) {
			logger.error("ListWarehouse：部门修改排序异常！",e);
			e.printStackTrace();
		}
	}
	
	/**
	  * @Description:删除系统
	  * @author:yuyang
	  * @param request
	  * @param response
	  * @param ids
	  * 2017年3月30日 
	 * @return 
	  */
	@RequestMapping("/deleteWebApplicationById")
	@ResponseBody
	public String deleteWebApplicationById(HttpServletRequest request,HttpServletResponse response
										,@RequestParam(value = "ids",required=false) String ids){
		try {
			int result = webApplicationService.deleteWebApplicationById(ids);
			if(result ==2){
				return "userCountFalse";
			}
			if(result == 1){
				return "true";
			}
			
		} catch (Exception e) {
			logger.error("deleteWebApplicationById：删除系统异常！",e);
			e.printStackTrace();
		}
		return "false";
	}

	
	/**
	  * @Description:添加系统弹出框
	  * @author:yuyang
	  * @param request
	  * @param response
	  * @return
	  * 2017年3月30日 
	  */
	@RequestMapping("/webApplicationAddPage")
	@ResponseBody
	public ModelAndView webApplicationAddPage(HttpServletRequest request, HttpServletResponse response){
		ModelAndView model = new ModelAndView("webApplication/webApplicationAdd");
		return model;
	}
	
	
	
	/**
	  * @Description:添加系统
	  * @author:yuyang
	  * @param request
	  * @param response
	  * @return
	  * 2017年3月30日 
	  */
	@RequestMapping("/webApplicationAdd")
	@ResponseBody
	public String webApplicationAdd(HttpServletRequest request,HttpServletResponse response
								,@ModelAttribute("webApplication") WebApplication webApplication){
		try {
			int result = webApplicationService.insertWebApplication(webApplication);
			if(result == 5){
				return "IdentifyFalse";
			}
			if(result == 4){
				return "appUrlFalse";
			}
			if(result == 3){
				return "appNoFalse";
			}
			if(result == 2){
				return "appNameFalse";
			}
			if(result == 1){
				return "true";
			}
			
		} catch (Exception e) {
			logger.error("webApplicationAdd：添加系统异常！",e);
			e.printStackTrace();
		}
		return "false";
	}
	
	/**
	  * @Description:编辑系统弹出框
	  * @author:yuyang
	  * @param request
	  * @param response
	  * @param id
	  * @return
	  * 2017年3月30日 
	  */
	@RequestMapping("/webApplicationEditPage")
	@ResponseBody
	public ModelAndView webApplicationAddPage(HttpServletRequest request, HttpServletResponse response
												,@RequestParam(value = "id",required=false) String id){
		ModelAndView model = new ModelAndView("webApplication/webApplicationEdit");
		WebApplication webApplication = webApplicationService.selectByPrimaryKey(Integer.valueOf(id));
		model.addObject("webApplication", webApplication);
		return model;
	}
	
	/**
	  * @Description:编辑系统
	  * @author:yuyang
	  * @param request
	  * @param response
	  * @param webApplication
	  * @return
	  * 2017年3月30日 
	  */
	@RequestMapping("/webApplicationEdit")
	@ResponseBody
	public String webApplicationEdit(HttpServletRequest request,HttpServletResponse response
								,@ModelAttribute("webApplication") WebApplication webApplication){
		try {
			int result = webApplicationService.editWebApplication(webApplication);
			if(result == 5){
				return "IdentifyFalse";
			}
			if(result == 4){
				return "appUrlFalse";
			}
			if(result == 3){
				return "appNoFalse";
			}
			if(result == 2){
				return "appNameFalse";
			}
			if(result == 1){
				return "true";
			}
			
		} catch (Exception e) {
			logger.error("webApplicationAdd：编辑系统异常！",e);
			e.printStackTrace();
		}
		return "false";
	}
	
}

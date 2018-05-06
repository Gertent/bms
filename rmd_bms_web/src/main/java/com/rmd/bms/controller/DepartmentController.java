package com.rmd.bms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.StringUtil;
import com.rmd.bms.controller.base.AbstractAjaxController;
import com.rmd.bms.entity.Department;
import com.rmd.bms.entity.User;
import com.rmd.bms.service.DepartmentService;
import com.rmd.bms.util.web.WebMessageCode;

@Controller
@RequestMapping("/department")
public class DepartmentController extends AbstractAjaxController{
	//日志管理器
	private static Logger logger = Logger.getLogger(DepartmentController.class);
	@Autowired
	private DepartmentService departMentService;
	
	@RequestMapping("/departmentPage")
	public ModelAndView departmentPage(HttpServletRequest request, HttpServletResponse response){
		ModelAndView model = new ModelAndView("department/departmentManage");
		return model;
	}
	
	/**
	  * @Description:部门列表查询
	  * @author:yuyang
	  * @param request
	  * @param response
	  * @param page
	  * @param rows
	  * @param deptname
	 * @return 
	  * @return
	  * 2017年3月28日 
	  */
	@RequestMapping("/listDepartment")
	@ResponseBody
	public  List<Department> listDepartment(HttpServletRequest request,HttpServletResponse response
											){
		List<Department> epartmentList = new ArrayList<Department>();
		try {
			Map<String, Object> whereParams = new HashMap<String, Object>();
			epartmentList = departMentService.getDepartmentList(whereParams);
		} catch (Exception e) {
			logger.error("ListWarehouse：部门列表查询异常！",e);
			e.printStackTrace();
		}
		return epartmentList;
	}
	
	/**
	  * @Description:搜索
	  * @author:yuyang
	  * @param request
	  * @param response
	  * @param deptname
	  * @return
	  * 2017年4月12日 
	  */
	@RequestMapping("/searchDepartment")
    @ResponseBody
    public List<Department> searchDepartment(HttpServletRequest request,HttpServletResponse response
                                            ,@RequestParam(value = "deptname",required=false) String deptname){
        List<Department> epartmentList = new ArrayList<Department>();
        try {
            Map<String, Object> whereParams = new HashMap<String, Object>();
            if(StringUtil.isNotEmpty(deptname)){
                whereParams.put("deptname", deptname);
            }
            epartmentList = departMentService.getDepartmentList(whereParams);
        } catch (Exception e) {
            logger.error("ListWarehouse：部门列表查询异常！",e);
            e.printStackTrace();
        }
        return epartmentList;
    }
	
	/**
	  * @Description:修改排序
	  * @author:yuyang
	  * @param request
	  * @param response
	  * 2017年3月28日 
	  */
	@RequestMapping("/categorySort")
	@ResponseBody
	public void categorySort(HttpServletRequest request, HttpServletResponse response){
		try {
			Integer userid = ((User) SecurityUtils.getSubject().getPrincipal()).getId();
			String sort = request.getParameter("index");
			String id = request.getParameter("id");
			int result = departMentService.updateSort(userid,Integer.parseInt(id),Integer.parseInt(sort));
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
	  * @Description:删除部门
	  * @author:yuyang
	  * @param request
	  * @param response
	  * @param ids
	  * 2017年3月29日 
	 * @return 
	  */
	@RequestMapping("/deleteDepartmentById")
	@ResponseBody
	public String deleteDepartmentById(HttpServletRequest request, HttpServletResponse response
								,@RequestParam(value = "ids",required=false) String ids){
		try {
			int result = departMentService.deleteDepartmentById(ids);	
			if(result == 2){
				return "deleteDeptFalse";
			}
			if(result == 1){
				return "true";
			}
		} catch (Exception e) {
			logger.error("deleteDepartment：删除部门异常！",e);
			e.printStackTrace();
		}
		return "false";
	}
	
	/**
	  * @Description:添加部门弹出框
	  * @author:yuyang
	  * @param request
	  * @param response
	  * @return
	  * 2017年3月29日 
	  */
	@RequestMapping("/departmentAddPage")
	@ResponseBody
	public ModelAndView departmentAddPage(HttpServletRequest request, HttpServletResponse response){
		ModelAndView model = new ModelAndView("department/departmentAdd");
		Map<String, Object> whereParams = new HashMap<String, Object>();
		List<Department> epartmentList = departMentService.getDepartmentList(whereParams);
		model.addObject("epartmentList", epartmentList);
		return model;
	}
	
	
	/**
	  * @Description:添加部门
	  * @author:yuyang
	  * @param request
	  * @param response
	  * @param ids
	  * 2017年3月29日 
	 * @return 
	  */
	@RequestMapping("/addDepartment")
	@ResponseBody
	public String addDepartment(HttpServletRequest request, HttpServletResponse response
								,@ModelAttribute("department") Department department){
		try {
			Integer userid = ((User) SecurityUtils.getSubject().getPrincipal()).getId();
			int result = departMentService.insertDepartment(userid,department);	
			if(result == 2){
				return "deptnameFalse";
			}
			if(result == 1){
				return "true";
			}
		} catch (Exception e) {
			logger.error("deleteDepartment：删除部门异常！",e);
			e.printStackTrace();
		}
		return "false";
	}
	
	/**
	  * @Description:编辑部门弹出框
	  * @author:yuyang
	  * @param request
	  * @param response
	  * @return
	  * 2017年3月29日 
	  */
	@RequestMapping("/departmentEditPage")
	@ResponseBody
	public ModelAndView departmentEditPage(HttpServletRequest request, HttpServletResponse response
											,@RequestParam(value = "id",required=false) String id){
		ModelAndView model = new ModelAndView("department/departmentEdit");
		Map<String, Object> whereParams = new HashMap<String, Object>();
		Department departMent = departMentService.selectByPrimaryKey(Integer.valueOf(id));
		List<Department> epartmentList = departMentService.getDepartmentList(whereParams);
		model.addObject("epartmentList", epartmentList);
		model.addObject("departMent", departMent);
		return model;
	}
	
	/**
	  * @Description:编辑部门
	  * @author:yuyang
	  * @param request
	  * @param response
	  * @param department
	  * @return
	  * 2017年3月29日 
	  */
	@RequestMapping("/editDepartment")
	@ResponseBody
	public String editDepartment(HttpServletRequest request, HttpServletResponse response
								,@ModelAttribute("department") Department department){
		try {
			Integer userid = ((User) SecurityUtils.getSubject().getPrincipal()).getId();
			int result = departMentService.updateByPrimaryKeySelective(userid,department);	
			if(result == 2){
				return "deptnameFalse";
			}
			if(result == 1){
				return "true";
			}
		} catch (Exception e) {
			logger.error("deleteDepartment：删除部门异常！",e);
			e.printStackTrace();
		}
		return "false";
	}
}

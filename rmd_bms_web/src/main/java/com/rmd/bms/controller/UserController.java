package com.rmd.bms.controller;

import com.rmd.bms.controller.base.AbstractAjaxController;
import com.rmd.bms.entity.Department;
import com.rmd.bms.entity.Role;
import com.rmd.bms.entity.User;
import com.rmd.bms.entity.WebApplication;
import com.rmd.bms.entity.common.PageBean;
import com.rmd.bms.service.DepartmentService;
import com.rmd.bms.service.RoleService;
import com.rmd.bms.service.UserService;
import com.rmd.bms.service.WebApplicationService;
import com.rmd.bms.util.web.WebMessageCode;
import net.sf.json.JSONArray;
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
@RequestMapping("/user")
public class UserController extends AbstractAjaxController {

    //日志管理器
    private static Logger logger = Logger.getLogger(DepartmentController.class);

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentService departMentService;

    @Autowired
    private WebApplicationService webApplicationService;


    @RequestMapping("/userPage")
    public ModelAndView departmentPage(HttpServletRequest request, HttpServletResponse response) {
        //查询角色表
        List<Role> roleList = userService.getRoleList();
        ModelAndView model = new ModelAndView("user/userManage");
        model.addObject("roleList", roleList);
        return model;
    }

    /**
     * @return 2017年3月29日
     * @Description:员工列表
     * @author:yuyang
     */
    @RequestMapping("/getUserList")
    @ResponseBody
    public Map<String, Object> listUser(HttpServletRequest request, HttpServletResponse response
            , @RequestParam(value = "page", required = false) Integer page
            , @RequestParam(value = "rows", required = false) Integer rows
            , @ModelAttribute User params) {

        Map<String, Object> map = new HashMap<String, Object>();
        try {
            PageBean<User> pageBean = userService.getUserList(page, rows, params);
            map.put("total", pageBean.getTotal());
            map.put("rows", pageBean.getList());
            JSONObject json = JSONObject.fromObject(map);
            map.put("userList", json);
        } catch (Exception e) {
            logger.error("listUser：员工列表查询异常！", e);
            e.printStackTrace();
        }
        return map;
    }


    /**
     * @param request
     * @param response
     * @param ids
     * @param status   2017年3月29日
     * @Description:锁定 正常
     * @author:yuyang
     */
    @RequestMapping("/updateLockStatusById")
    @ResponseBody
    public void updateLockStatusById(HttpServletRequest request, HttpServletResponse response
            , @RequestParam(value = "ids", required = false) String ids
            , @RequestParam(value = "status", required = false) String status) {
        try {
            Integer userid = ((User) SecurityUtils.getSubject().getPrincipal()).getId();
            int result = userService.updateStatus(userid, ids, status);
            if (result > 0) {
                writeOutWebMessage(WebMessageCode.OPERATE_SUCESS, request, response);
            } else {
                writeOutWebMessage(WebMessageCode.OPERATE_FAILED, request, response);
            }
        } catch (Exception e) {
            logger.error("updateLockStatusById：员工修改状态异常！", e);
            e.printStackTrace();
        }
    }

    /**
     * @param request
     * @param response
     * @param ids      2017年3月29日
     * @Description:删除员工
     * @author:yuyang
     */
    @RequestMapping("/deleteUserById")
    @ResponseBody
    public void deleteUserById(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "ids", required = false) String ids) {
        try {
            int result = userService.deleteUserById(ids);
            if (result > 0) {
                writeOutWebMessage(WebMessageCode.OPERATE_SUCESS, request, response);
            } else {
                writeOutWebMessage(WebMessageCode.OPERATE_FAILED, request, response);
            }
        } catch (Exception e) {
            logger.error("deleteUserById：删除员工异常！", e);
            e.printStackTrace();
        }
    }


    /**
     * @param request
     * @param response
     * @return 2017年3月29日
     * @Description:添加员工弹出框
     * @author:yuyang
     */
    @RequestMapping("/userAddPage")
    @ResponseBody
    public ModelAndView userAddPage(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView model = new ModelAndView("user/userAdd");
        Map<String, Object> whereParams = new HashMap<String, Object>();
        List<Department> departmentList = departMentService.getDepartmentList(whereParams);
        model.addObject("departmentList", departmentList);
        return model;
    }

    /**
     * @param request
     * @param response
     * @param user
     * @return 2017年3月29日
     * @Description:添加员工
     * @author:yuyang
     */
    @RequestMapping("/addUser")
    @ResponseBody
    public String addUser(HttpServletRequest request, HttpServletResponse response, User user) {
        try {
            Integer userid = ((User) SecurityUtils.getSubject().getPrincipal()).getId();
            int result = userService.insertUser(userid, user);
            if (result == 2) {
                return "loginnameFalse";
            }
            if (result == 1) {
                return "true";
            }

        } catch (Exception e) {
            logger.error("userAdd：添加员工异常！", e);
            e.printStackTrace();
        }
        return "false";
    }

    /**
     * @param request
     * @param response
     * @param id
     * @return 2017年3月29日
     * @Description:编辑员工弹出框
     * @author:yuyang
     */
    @RequestMapping("/userEditPage")
    @ResponseBody
    public ModelAndView userEditPage(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "id", required = false) String id) {
        ModelAndView model = new ModelAndView("user/userEdit");
        Map<String, Object> whereParams = new HashMap<String, Object>();
        User user = userService.selectByPrimaryKey(Integer.valueOf(id));
        List<Department> departmentList = departMentService.getDepartmentList(whereParams);
        model.addObject("user", user);
        model.addObject("departmentList", departmentList);
        return model;
    }

    /**
     * @param request
     * @param response
     * @param user
     * @return 2017年3月29日
     * @Description:编辑员工
     * @author:yuyang
     */
    @RequestMapping("/editUser")
    @ResponseBody
    public String editUser(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("user") User user) {
        try {
            Integer userid = ((User) SecurityUtils.getSubject().getPrincipal()).getId();
            int result = userService.updateByPrimaryKeySelective(userid, user);
            if (result == 2) {
                return "loginnameFalse";
            }
            if (result == 1) {
                return "true";
            }
        } catch (Exception e) {
            logger.error("userAdd：编辑员工异常！", e);
            e.printStackTrace();
        }
        return "false";
    }

    /**
     * @param request
     * @param response
     * @return 2017年4月1日
     * @Description:分配角色弹出框
     * @author:yuyang
     */
    @RequestMapping("/allocatePage")
    @ResponseBody
    public ModelAndView allocatePage(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "id", required = false) String id) {
        ModelAndView model = new ModelAndView("user/userAllocatRoles");
        /**如果用户所在系统为bms则显示全部，如果为其他系统则显示其他系统下的角色*/
        User u = ((User) SecurityUtils.getSubject().getPrincipal());
        WebApplication webApplication = webApplicationService.selectByAppNo(u.getAppNumber());
        List<WebApplication> webApplicationList = new ArrayList<WebApplication>();
        if (webApplication.getIdentify().equals("bms")) {
            webApplicationList = userService.getWebApplicationList();
        } else {
            webApplicationList.add(webApplication);
        }

        User user = userService.selectByPrimaryKey(Integer.valueOf(id));
        model.addObject("user", user);
        model.addObject("webApplicationList", webApplicationList);
        model.addObject("webApplication", webApplication);
        return model;
    }


//	@RequestMapping("/getSystemId")
//	@ResponseBody
//	public Map<String, Object> getSystemId(HttpServletRequest request,HttpServletResponse response
//									,@RequestParam("id") String id){
//		List<WebApplication> webApplicationList = new ArrayList<WebApplication>();
//		Map<String, Object> UserAndSystemMap = new HashMap<String, Object>();
//		Map<String, Object> allMap = new HashMap<String, Object>();
//		WebApplication webApplication = new WebApplication();
//		List<Role> roleList = new ArrayList<Role>();
//		User u = ((User) SecurityUtils.getSubject().getPrincipal());
//		if("010".equals(u.getAppNumber())){
//			 webApplicationList = userService.getWebApplicationList();
//		}
//		//系统
//		webApplication = webApplicationService.selectByAppNo(u.getAppNumber());
//		//系统对应的角色
//		roleList = roleService.selectBySystemId(String.valueOf(webApplication.getId()));
//		//回显角色
//		 UserAndSystemMap.put("status", 1);
//		 UserAndSystemMap.put("systemId", webApplication.getId());
//		 UserAndSystemMap.put("userid", id);
//		 List<Role>  roleBySysIdandUserList= roleService.selectByAllList(UserAndSystemMap);
//		 allMap.put("webApplicationList", webApplicationList);
//		 allMap.put("webApplication", webApplication);
//		 allMap.put("roleList", roleList);
//		 allMap.put("roleBySysIdandUserList", roleBySysIdandUserList);
//		 
//		 
//		 return allMap;
//	}
//	


    /**
     * @param request
     * @param response
     * @param id
     * @return 2017年4月1日
     * @Description:分配角色 ----根据系统id查询角色
     * @author:yuyang
     */
    @RequestMapping("/getRolesBySystemId")
    public ModelAndView getRolesBySystemId(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "systemId", required = false) String systemId) {
        ModelAndView model = new ModelAndView("user/userAllocatRoles");
        List<Role> roleList = roleService.selectBySystemId(systemId);
        JSONArray roleJson = JSONArray.fromObject(roleList);
        model.addObject("roleJson", roleJson);
        return model;
    }

    /**
     * @param request
     * @param response
     * @param user
     * @param roleIds
     * @Description:员工分配角色
     * @author:yuyang
     */
    @RequestMapping("/allocationRole")
    @ResponseBody
    public void allocationRole(HttpServletRequest request, HttpServletResponse response
            , @RequestParam("id") String id
            , @RequestParam("roleIds") String roleIds
            , @RequestParam("systemId") String systemId
    ) {
        Integer userid = ((User) SecurityUtils.getSubject().getPrincipal()).getId();
        int result = userService.updateAllocationRole(userid, id, roleIds, Integer.valueOf(systemId));
        if (result == 1) {
            writeOutWebMessage(WebMessageCode.OPERATE_SUCESS, request, response);
        } else {
            writeOutWebMessage(WebMessageCode.OPERATE_FAILED, request, response);
        }
    }
}

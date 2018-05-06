package com.rmd.bms.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.rmd.bms.dao.RoleMapper;
import com.rmd.bms.dao.UserMapper;
import com.rmd.bms.dao.UserRoleMapper;
import com.rmd.bms.dao.WebApplicationMapper;
import com.rmd.bms.entity.*;
import com.rmd.bms.entity.common.PageBean;
import com.rmd.bms.service.DepartmentService;
import com.rmd.bms.service.UserService;
import com.rmd.bms.service.WebApplicationService;
import com.rmd.bms.util.MD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("userService")
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private WebApplicationMapper webApplicationMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private DepartmentService departMentService;

    @Autowired
    private WebApplicationService webApplicationService;

    @Override
    public List<Role> getRoleList() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Role> selectByAllList = roleMapper.selectByAllList(map);
        return selectByAllList;
    }

    @Override
    public PageBean<User> getUserList(Integer page, Integer rows, User params) {
        Map<String, Object> whereParams = new HashMap<String, Object>();
        if (StringUtil.isNotEmpty(params.getLoginname())) {
            whereParams.put("loginname", params.getLoginname());
        }
        if (StringUtil.isNotEmpty(params.getRealname())) {
            whereParams.put("realname", params.getRealname());
        }
        if (StringUtil.isNotEmpty(params.getJobNum())) {
            whereParams.put("jobNum", params.getJobNum());
        }
        if (params.getDeptid() != null) {
            List<Department> departmentList = departMentService.getDepartmentList(new HashMap<String, Object>());

            if (departmentList != null && !departmentList.isEmpty()) {
                String deptids = "";
                for (Department dept : departmentList) {
                    if (dept.getId().equals(params.getDeptid())) {
                        deptids = getDeptids(dept.getChildren());
                        break;
                    }
                }
                if (deptids.length() > 0) {
                    whereParams.put("deptids", deptids + params.getDeptid());
                } else {
                    whereParams.put("deptid", params.getDeptid());
                }
            } else {
                whereParams.put("deptid", params.getDeptid());
            }

        }
        if (params.getRoleId() != null) {
            whereParams.put("roleId", params.getRoleId());
        }
        if (params.getStatus() != null) {
            whereParams.put("status", params.getStatus());
        }
        PageHelper.startPage(page, rows);
        List<User> userList = userMapper.selectByAllList(whereParams);
        PageBean<User> pageBean = new PageBean<User>(userList);
        List<User> usersList = pageBean.getList();
        for (User user : usersList) {
            //所有角色
            WebApplication webApplication = webApplicationService.getSystemRoleById(user.getId());
            if (webApplication != null) {
                user.setRoleName(webApplication.getRolename());
            }
            //所有部门
            List<String> list = new ArrayList<String>();

            Department department = departMentService.selectByPrimaryKey(user.getDeptid());
            if (department != null) {
                list.add(department.getDeptname());
                deptnameHierarchy(list, department.getId());
                Collections.reverse(list);
                String join = org.apache.commons.lang3.StringUtils.join(list.toArray(), ",");
                String[] split = join.split(",");
                String deptNames = new String();
                for (String string : split) {
                    deptNames += string + "/";
                }
                deptNames = deptNames.substring(0, deptNames.length() - 1);
                user.setDeptNames(deptNames);
            } else {
                user.setDeptNames("");
            }

        }
        return pageBean;
    }

    /**
     * 按部门查询时，列出所有后代部门ID
     *
     * @param list
     * @return
     */
    private String getDeptids(List<Department> list) {
        String deptids = "";
        for (Department dept : list) {
            deptids += dept.getId() + ",";
            if (dept.getChildren() != null && !dept.getChildren().isEmpty()) {
                deptids += getDeptids(dept.getChildren());
            }
        }
        return deptids;
    }


    /**
     * @param request
     * @param response
     * @param ids
     * @param status   2017年4月23日
     * @Description:查询部门的所有层级
     * @author:yuyang
     */
    private void deptnameHierarchy(List<String> list, Integer id) {
        Department department = departMentService.selectDepartNamesById(id);
        if (department != null) {
            list.add(department.getDeptname());
            deptnameHierarchy(list, department.getId());
        } else {
            return;
        }
    }

    @Override
    public int updateStatus(Integer userid, String ids, String status) {
        int result = 0;
        User user = new User();
        String[] arr = ids.split(",");
        for (String id : arr) {
            user.setId(Integer.valueOf(id));
            user.setStatus(Integer.valueOf(status));
            user.setUpdateuser(userid);
            user.setUpdatetime(new Date());
            int upsultResult = userMapper.updateByPrimaryKeySelective(user);
            if (upsultResult > 0) {
                result++;
            }
        }
        if (result > 0) {
            return 1;
        }
        return 0;
    }

    @Override
    public int insertUser(Integer userid, User user) {
        int resultloginnameCount = userMapper.selectByLoginname(user);
        if (resultloginnameCount > 0) {
            return 2;
        }
        int jobNum = userMapper.selectByJob_num();
        jobNum = jobNum + 1;
        String newJobNum = String.format("%05d", jobNum);
        user.setJobNum(newJobNum);
        user.setPassword(MD5.encodeMD5(user.getPassword()));
        user.setStatus(1);
        user.setCreatetime(new Date());
        user.setCreateuser(userid);
        int resultInsert = userMapper.insertSelective(user);
        if (resultInsert > 0) {
            return 1;
        }
        return 0;
    }

    @Override
    public User selectByPrimaryKey(Integer id) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", id);
        User user = userMapper.selectByAllList(map).get(0);
        return user;
    }

    @Override
    public int updateByPrimaryKeySelective(Integer userid, User user) {
        int resultloginnameCount = userMapper.selectByLoginname(user);
        if (resultloginnameCount > 0) {
            return 2;
        }
        user.setStatus(1);
        user.setPassword(MD5.encodeMD5(user.getPassword()));
        user.setUpdatetime(new Date());
        user.setUpdateuser(userid);
        int resultInsert = userMapper.updateByPrimaryKeySelective(user);
        if (resultInsert > 0) {
            return 1;
        }
        return 0;
    }

    @Override
    public int deleteUserById(String ids) {
        try {
            String[] arr = ids.split(",");
            for (String id : arr) {
                User user = new User();
                user.setId(Integer.valueOf(id));
                user.setStatus(2);
                userMapper.updateByPrimaryKeySelective(user);
                /**删除员工和角色得关系表数据*/
                userRoleMapper.deleteByUserId(Integer.valueOf(id));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return 1;
    }

    @Override
    public List<WebApplication> getWebApplicationList() {
        Map<String, Object> map = new HashMap<String, Object>();
        return webApplicationMapper.selectByAllList(map);
    }

    @Override
    public int updateAllocationRole(Integer userid, String id, String roleIds, Integer systemId) {
        int result = 0;
        UserRole userRole = new UserRole();
        String[] arrs = roleIds.split(",");
        userRole.setUserId(Integer.valueOf(id));
        userRole.setSystemId(systemId);
        int resultDelete = userRoleMapper.deleteByAll(userRole);
        if (resultDelete > 0) {
            result++;
        }
        if (!"".equals(roleIds)) {
            for (String roleId : arrs) {
                /**员工分配角色关联表*/
                userRole.setUserId(Integer.valueOf(id));
                userRole.setRoleId(Integer.valueOf(roleId));
                userRole.setCreateuser(userid);
                userRole.setCreatetime(new Date());
                int resultUserRoleInsert = userRoleMapper.insertSelective(userRole);
                if (resultUserRoleInsert > 0) {
                    result++;
                }
            }
        }
        if (result > 0) {
            return 1;
        }
        return 0;
    }

    @Override
    public User findUserByLoginName(String loginName) {
        UserExample userExample = new UserExample();
        UserExample.Criteria c = userExample.createCriteria();
        c.andLoginnameEqualTo(loginName);
        List<Integer> status = new ArrayList<Integer>();
        status.add(0);
        status.add(1);
        c.andStatusIn(status);
        List<User> list = userMapper.selectByExample(userExample);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }


    @Override
    public int updateByPassword(Integer userid, String password, String newPassword,
                                String confirmPassword) {
        User user = new User();
        User resultUser = userMapper.selectByPrimaryKey(userid);
        if (!newPassword.equals(confirmPassword)) {
            return 3;
        }
        String pass = MD5.encodeMD5(password);
        if (!pass.equals(resultUser.getPassword())) {
            return 2;
        }
        user.setId(userid);
        user.setPassword(MD5.encodeMD5(newPassword));
        int updateResult = userMapper.updateByPrimaryKeySelective(user);
        if (updateResult > 0) {
            return 1;
        }
        return 0;
    }

    @Override
    public int updateByAccountInfomation(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

}

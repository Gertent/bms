package com.rmd.bms.service.impl;

import com.github.pagehelper.PageHelper;
import com.rmd.bms.constant.Constant;
import com.rmd.bms.dao.RoleMapper;
import com.rmd.bms.dao.RoleOperationMapper;
import com.rmd.bms.dao.UserRoleMapper;
import com.rmd.bms.dao.WebApplicationMapper;
import com.rmd.bms.entity.*;
import com.rmd.bms.entity.Vo.RoleVo;
import com.rmd.bms.entity.common.PageBean;
import com.rmd.bms.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private WebApplicationMapper webApplicationMapper;

    @Autowired
    private RoleOperationMapper roleOperationMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;


    @Override
    public PageBean<Role> getRoleList(Integer page, Integer rows,
                                      Map<String, Object> whereParams) {
        PageHelper.startPage(page, rows);
        return new PageBean<Role>(roleMapper.selectByAllList(whereParams));
    }

    @Override
    public List<WebApplication> getWebApplicationList() {
        Map<String, Object> map = new HashMap<String, Object>();
        return webApplicationMapper.selectByAllList(map);
    }

    @Override
    public int updateStatus(Integer userid, String ids, String status) {
        int result = 0;
        Role role = new Role();
        String[] arr = ids.split(",");
        for (String id : arr) {
            role.setId(Integer.valueOf(id));
            role.setStatus(Integer.valueOf(status));
            role.setUpdateuser(userid);
            role.setUpdatetime(new Date());
            int upsultResult = roleMapper.updateByPrimaryKeySelective(role);
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
    @Transactional
    public int deleteRoleById(String ids) {
        try {
            String[] arr = ids.split(",");
            for (String id : arr) {
                roleMapper.deleteByPrimaryKey(Integer.valueOf(id));
                /**删除角色对应得菜单关系表*/
                roleOperationMapper.deleteByRoleId(Integer.valueOf(id));
                /**删除角色与用户的关系表*/
                userRoleMapper.deleteByRoleId(Integer.valueOf(id));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return 1;
    }

    @Override
    @Transactional
    public int insertRole(User user, Role role) {
        int resultInsertRoleAndOperation = 0;

        int resultRolenameCount = roleMapper.selectByRolename(role);
        if (resultRolenameCount > 0) {
            return 2;
        }
        role.setCreatetime(new Date());
        role.setCreateuser(user.getId());
        role.setSystemId(role.getSystemId());
        role.setStatus(Constant.STATUS_ONE);
        int resultInsert = roleMapper.insertSelective(role);
        /**添加角色和菜单的中间表*/
        String menuIds = role.getMenuIds();
        String[] arrs = menuIds.split(",");
        for (String menuId : arrs) {
            String menuOperation[] = menuId.split(":");
            RoleOperation roleOperation = new RoleOperation();
            roleOperation.setRoleId(role.getId());
            roleOperation.setMenuId(Integer.valueOf(menuOperation[0]));
            if (menuOperation.length > 1) {
                roleOperation.setOperationId(Integer.valueOf(menuOperation[1]));
            }
            roleOperation.setCreatetime(new Date());
            roleOperation.setCreateuser(user.getId());
            resultInsertRoleAndOperation = roleOperationMapper.insertSelective(roleOperation);
        }
        if (resultInsert > 0 && resultInsertRoleAndOperation > 0) {
            return 1;
        }
        return 0;
    }

    @Override
    public Role selectByPrimaryKey(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public int updateRole(RoleVo role, User user) {
        int resultInsertRoleAndOperation = 0;
        // 如果修改了角色名称，则校验角色名称是否重复
        if (!role.getRolename().equals(role.getOldrolename())) {
            RoleExample example = new RoleExample();
            RoleExample.Criteria c = example.createCriteria();
            c.andRolenameEqualTo(role.getRolename()).andIdNotEqualTo(role.getId());
            c.andSystemIdEqualTo(role.getSystemId());
            List<Role> list = roleMapper.selectByExample(example);
            if (list.size() > 0) {
                return 2;
            }
        }
        Role en = new Role();
        en.setId(role.getId());
        en.setRolename(role.getRolename());
        en.setMenuIds(role.getMenuIds());
        en.setUpdateuser(user.getId());
        en.setUpdatetime(new Date());
        int resultInsert = roleMapper.updateByPrimaryKeySelective(en);
        int resultDelete = roleOperationMapper.deleteByRoleId(role.getId());
        String menuIds = role.getMenuIds();
        String[] arrs = menuIds.split(",");
        for (String menuId : arrs) {
            String menuOperation[] = menuId.split(":");
            RoleOperation roleOperation = new RoleOperation();
            roleOperation.setRoleId(role.getId());
            roleOperation.setMenuId(Integer.valueOf(menuOperation[0]));
            if (menuOperation.length > 1) {
                roleOperation.setOperationId(Integer.valueOf(menuOperation[1]));
            }
            roleOperation.setCreatetime(new Date());
            roleOperation.setCreateuser(user.getId());
            resultInsertRoleAndOperation = roleOperationMapper.insertSelective(roleOperation);
        }

        if (resultInsert > 0 && resultDelete > 0 && resultInsertRoleAndOperation > 0) {
            return 1;
        }
        return 0;
    }

    @Override
    public List<Role> selectBySystemId(String systemId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("systemId", Integer.valueOf(systemId));
        map.put("status", Constant.STATUS_ONE);
        return roleMapper.selectByAllList(map);
    }

    @Override
    public Map<String, Object> getRoleListBySystemId(String systemId, String userid) {
        Map<String, Object> allMap = new HashMap<String, Object>();
        Map<String, Object> UserAndSystemMap = new HashMap<String, Object>();
        Map<String, Object> SystemMap = new HashMap<String, Object>();
        List<Role> roleBySysIdList = new ArrayList<Role>();
        //根据系统获取角色
        SystemMap.put("status", Constant.STATUS_ONE);
        SystemMap.put("systemId", Integer.valueOf(systemId));
        roleBySysIdList = roleMapper.selectByAllList(SystemMap);
        //根据系统和员工获取角色
        UserAndSystemMap.put("status", Constant.STATUS_ONE);
        UserAndSystemMap.put("systemId", Integer.valueOf(systemId));
        UserAndSystemMap.put("userid", userid);
        List<Role> roleBySysIdandUserList = roleMapper.selectByAllList(UserAndSystemMap);
        allMap.put("roleBySysIdList", roleBySysIdList);
        allMap.put("roleBySysIdandUserList", roleBySysIdandUserList);
        return allMap;
    }

    @Override
    public List<Role> selectByAllList(Map<String, Object> userAndSystemMap) {
        return roleMapper.selectByAllList(userAndSystemMap);
    }
}

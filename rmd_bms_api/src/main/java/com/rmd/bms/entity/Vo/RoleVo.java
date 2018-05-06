package com.rmd.bms.entity.Vo;

import java.io.Serializable;

/**
 * Created by win7 on 2017/5/4.
 */
public class RoleVo implements Serializable {
    private Integer id;

    private String rolename;

    private Integer systemId;

    private String menuIds;//菜单id 可以添加多个权限

    private String oldrolename;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    public String getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }

    public String getOldrolename() {
        return oldrolename;
    }

    public void setOldrolename(String oldrolename) {
        this.oldrolename = oldrolename;
    }
}

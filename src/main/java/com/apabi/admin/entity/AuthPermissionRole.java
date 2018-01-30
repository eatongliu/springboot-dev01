package com.apabi.admin.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by liuyutong on 2017/12/22.
 */
public class AuthPermissionRole  implements Serializable {
    private Integer id;
    private Integer roleId;
    private Integer premissionId;
    private Date createDate;
    private Date updateDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPremissionId() {
        return premissionId;
    }

    public void setPremissionId(Integer premissionId) {
        this.premissionId = premissionId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @Override
    public String toString() {
        return "AuthPermissionRole{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", premissionId=" + premissionId +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                '}';
    }
}

package com.apabi.admin.entity;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AuthPermission implements Serializable {
    private int id;
    //权限名称
    private String name;

    //权限类型
    private String type;

    //授权链接
    private String url;

    //父节点id
    private Integer pid;

    private String icon;

    private Date createDate;

    private Date updateDate;

    private Integer enabled;

    private List<AuthPermission> subPermission;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public List<AuthPermission> getSubPermission() {
        return subPermission;
    }

    public void setSubPermission(List<AuthPermission> subPermission) {
        this.subPermission = subPermission;
    }

    @Override
    public String toString() {
        return "AuthPermission{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", pid=" + pid +
                ", icon=" + icon +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", enabled=" + enabled +
                ", subPermission=" + subPermission +
                '}';
    }
}

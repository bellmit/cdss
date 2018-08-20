package com.jhmk.cloudentity.cdss.pojo;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author ziyu.zhou
 * @date 2018/7/6 16:06
 */

@Entity
@Table(name = "shiro_user")
public class ShiroUser {
    private String id;
    private Timestamp gmtCreate;
    private long createBy;
    private Timestamp gmtModify;
    private long modifyBy;
    private byte active;
    private Timestamp createTime;
    private String password;
    private String status;
    private String username;

    @Id
    @Column(name = "id", nullable = false, length = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "GMT_CREATE", nullable = false)
    public Timestamp getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Timestamp gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Basic
    @Column(name = "CREATE_BY", nullable = false)
    public long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(long createBy) {
        this.createBy = createBy;
    }

    @Basic
    @Column(name = "GMT_MODIFY", nullable = false)
    public Timestamp getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Timestamp gmtModify) {
        this.gmtModify = gmtModify;
    }

    @Basic
    @Column(name = "MODIFY_BY", nullable = false)
    public long getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(long modifyBy) {
        this.modifyBy = modifyBy;
    }

    @Basic
    @Column(name = "ACTIVE", nullable = false)
    public byte getActive() {
        return active;
    }

    public void setActive(byte active) {
        this.active = active;
    }

    @Basic
    @Column(name = "create_time", nullable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "password", nullable = true, length = 255)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "status", nullable = true, length = 255)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "username", nullable = true, length = 255)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShiroUser shiroUser = (ShiroUser) o;
        return createBy == shiroUser.createBy &&
                modifyBy == shiroUser.modifyBy &&
                active == shiroUser.active &&
                Objects.equals(id, shiroUser.id) &&
                Objects.equals(gmtCreate, shiroUser.gmtCreate) &&
                Objects.equals(gmtModify, shiroUser.gmtModify) &&
                Objects.equals(createTime, shiroUser.createTime) &&
                Objects.equals(password, shiroUser.password) &&
                Objects.equals(status, shiroUser.status) &&
                Objects.equals(username, shiroUser.username);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, gmtCreate, createBy, gmtModify, modifyBy, active, createTime, password, status, username);
    }
}

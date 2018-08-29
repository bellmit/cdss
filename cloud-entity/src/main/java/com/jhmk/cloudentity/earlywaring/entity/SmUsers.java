package com.jhmk.cloudentity.earlywaring.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "sm_users", schema = "jhmk_waring")
public class SmUsers {
    private String userId;
    private String userName;
    private String userDept;
    private String createDate;
    private String userPwd;
    private String roleId;

    @Basic
    @Column(name = "role_id", nullable = true, length = 255)
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Basic
    @Id
    @Column(name = "USER_ID", nullable = true, length = 255)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "USER_NAME", nullable = true, length = 255)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "USER_DEPT", nullable = true, length = 255)
    public String getUserDept() {
        return userDept;
    }

    public void setUserDept(String userDept) {
        this.userDept = userDept;
    }

    @Basic
    @Column(name = "CREATE_DATE", nullable = true, length = 255)
    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Basic
    @Column(name = "USER_PWD", nullable = true, length = 255)
    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }
}

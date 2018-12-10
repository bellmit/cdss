package com.jhmk.cloudentity.earlywaring.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author ziyu.zhou
 * @date 2018/11/30 12:08
 */

@Entity
@Table(name = "sm_users", schema = "jhmk_waring", catalog = "")
public class SmUsers {
    private int id;
    private String userId;
    private String userName;
    private String userLoginName;
    private String pym;
    private String userDept;
    private String createDate;
    private String userPwd;
    private String roleId;


    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
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
    @Column(name = "user_login_name", nullable = true, length = 255)
    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
    }

    @Basic
    @Column(name = "pym", nullable = true, length = 255)
    public String getPym() {
        return pym;
    }

    public void setPym(String pym) {
        this.pym = pym;
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

    @Basic
    @Column(name = "role_id", nullable = true, length = 11)
    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmUsers smUsers = (SmUsers) o;
        return id == smUsers.id &&
                Objects.equals(userId, smUsers.userId) &&
                Objects.equals(userName, smUsers.userName) &&
                Objects.equals(userLoginName, smUsers.userLoginName) &&
                Objects.equals(pym, smUsers.pym) &&
                Objects.equals(userDept, smUsers.userDept) &&
                Objects.equals(createDate, smUsers.createDate) &&
                Objects.equals(userPwd, smUsers.userPwd) &&
                Objects.equals(roleId, smUsers.roleId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, userId, userName, userLoginName, pym, userDept, createDate, userPwd, roleId);
    }
}

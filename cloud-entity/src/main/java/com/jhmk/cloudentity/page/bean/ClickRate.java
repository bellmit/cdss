package com.jhmk.cloudentity.page.bean;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author ziyu.zhou
 * @date 2018/10/23 17:55
 */

@Entity
@Table(name = "sm_click_rate", schema = "jhmk_waring", catalog = "")
public class ClickRate {
    private int id;
    private int count;
    private Date createTime;
    private String doctorId;
    private Timestamp submitTime;
    private String type;
    private String deptCode;
    private String deptName;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "count", nullable = false)
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Basic
    @Column(name = "create_time", nullable = false, length = 255)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "doctor_id", nullable = false, length = 32)
    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    @Basic
    @Column(name = "submit_time", nullable = true)
    public Timestamp getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Timestamp submitTime) {
        this.submitTime = submitTime;
    }

    @Basic
    @Column(name = "type", nullable = false, length = 32)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "dept_code", nullable = false, length = 32)
    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    @Basic
    @Column(name = "dept_name", nullable = true, length = 32)
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClickRate that = (ClickRate) o;
        return id == that.id &&
                count == that.count &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(doctorId, that.doctorId) &&
                Objects.equals(submitTime, that.submitTime) &&
                Objects.equals(type, that.type) &&
                Objects.equals(deptCode, that.deptCode) &&
                Objects.equals(deptName, that.deptName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, count, createTime, doctorId, submitTime, type, deptCode, deptName);
    }
}

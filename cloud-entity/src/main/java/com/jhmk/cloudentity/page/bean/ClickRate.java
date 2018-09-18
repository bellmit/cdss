package com.jhmk.cloudentity.page.bean;

import javax.persistence.*;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.Objects;

/**
 * @author ziyu.zhou
 * @date 2018/8/29 15:42
 */

@Entity
@Table(name = "sm_click_rate", schema = "jhmk_waring")
public class ClickRate {
    private int id;
    private String type;
    private String doctorId;
    private Date createTime;
    private String deptCode;
    private int count;

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
    @Column(name = "type", nullable = false, length = 32)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
    @Column(name = "dept_code", nullable = false, length = 32)
    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    @Basic
    @Column(name = "create_time", nullable = false)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Basic
    @Column(name = "count", nullable = false)
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClickRate that = (ClickRate) o;
        return id == that.id &&
                count == that.count &&
                Objects.equals(type, that.type) &&
                Objects.equals(doctorId, that.doctorId) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, type, doctorId, createTime, count);
    }
}

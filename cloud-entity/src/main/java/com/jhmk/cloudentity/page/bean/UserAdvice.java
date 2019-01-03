package com.jhmk.cloudentity.page.bean;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * @author ziyu.zhou
 * @date 2019/1/2 17:48
 */

@Entity
@Table(name = "user_advice", schema = "jhmk_waring", catalog = "")
public class UserAdvice {
    private int id;
    private String patientId;
    private String visitId;
    private String doctorId;
    private String doctorName;
    private String deptId;
    private String doctorAdvice;
    private Date createDate;

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
    @Column(name = "patientId", nullable = true, length = 30)
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    @Basic
    @Column(name = "visitId", nullable = true, length = 5)
    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    @Basic
    @Column(name = "doctorId", nullable = true, length = 30)
    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    @Basic
    @Column(name = "doctorName", nullable = true, length = 30)
    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    @Basic
    @Column(name = "deptId", nullable = true, length = 30)
    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    @Basic
    @Column(name = "doctorAdvice", nullable = true, length = 255)
    public String getDoctorAdvice() {
        return doctorAdvice;
    }

    public void setDoctorAdvice(String doctorAdvice) {
        this.doctorAdvice = doctorAdvice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAdvice that = (UserAdvice) o;
        return id == that.id &&
                Objects.equals(patientId, that.patientId) &&
                Objects.equals(visitId, that.visitId) &&
                Objects.equals(doctorId, that.doctorId) &&
                Objects.equals(doctorName, that.doctorName) &&
                Objects.equals(deptId, that.deptId) &&
                Objects.equals(doctorAdvice, that.doctorAdvice);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, patientId, visitId, doctorId, doctorName, deptId, doctorAdvice);
    }

    @Basic
    @Column(name = "createDate", nullable = true)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}

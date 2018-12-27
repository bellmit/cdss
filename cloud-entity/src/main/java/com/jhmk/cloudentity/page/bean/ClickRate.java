package com.jhmk.cloudentity.page.bean;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * @author ziyu.zhou
 * @date 2018/11/15 18:17
 */

@Entity
@Table(name = "sm_click_rate", schema = "jhmk_waring", catalog = "")
public class ClickRate {
    private int id;
    private String type;
    private String doctorId;
    private String patientId;
    private String visitId;
    private String deptCode;
    private Date createTime;
    private int count;
    private Timestamp submitTime;
    private String deptName;
    private String diagnosisCode;
    private String diagnosisName;
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
    @Column(name = "patient_id", nullable = true, length = 30)
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    @Basic
    @Column(name = "visit_id", nullable = true, length = 5)
    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }
    @Basic
    @Column(name = "dept_code", nullable = true, length = 32)
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

    @Basic
    @Column(name = "submit_time", nullable = true)
    public Timestamp getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Timestamp submitTime) {
        this.submitTime = submitTime;
    }

    @Basic
    @Column(name = "dept_name", nullable = true, length = 32)
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Basic
    @Column(name = "diagnosis_code", nullable = true, length = 50)
    public String getDiagnosisCode() {
        return diagnosisCode;
    }

    public void setDiagnosisCode(String diagnosisCode) {
        this.diagnosisCode = diagnosisCode;
    }

    @Basic
    @Column(name = "diagnosis_name", nullable = true, length = 50)
    public String getDiagnosisName() {
        return diagnosisName;
    }

    public void setDiagnosisName(String diagnosisName) {
        this.diagnosisName = diagnosisName;
    }

    @Basic
    @Column(name = "create_date", nullable = true)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
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
                Objects.equals(deptCode, that.deptCode) &&
                Objects.equals(createTime, that.createTime) &&
                Objects.equals(submitTime, that.submitTime) &&
                Objects.equals(deptName, that.deptName) &&
                Objects.equals(diagnosisCode, that.diagnosisCode) &&
                Objects.equals(diagnosisName, that.diagnosisName) &&
                Objects.equals(createDate, that.createDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, type, doctorId, deptCode, createTime, count, submitTime, deptName, diagnosisCode, diagnosisName, createDate);
    }
}

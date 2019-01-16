package com.jhmk.cloudentity.earlywaring.entity;

import javax.persistence.*;

@Entity
@Table(name = "diagnosis_req_log")
public class DiagnosisReqLog {
    private Integer id;
    private String pid;
    private String vid;
    private String diagnosisName;
    private String diagnosisTime;
    private String inTime;
    private String pageSource;
    private String doctorId;
    private String doctorName;
    private String deptCode;

    @Override
    public String toString() {
        return "DiagnosisReqLog{" +
                "id=" + id +
                ", pid='" + pid + '\'' +
                ", vid='" + vid + '\'' +
                ", diagnosis_name='" + diagnosisName + '\'' +
                ", diagnosis_time='" + diagnosisTime + '\'' +
                ", in_time='" + inTime + '\'' +
                ", page_source='" + pageSource + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", deptName='" + deptCode + '\'' +
                '}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "pid")
    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @Basic
    @Column(name = "vid")
    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    @Basic
    @Column(name = "diagnosis_name")
    public String getDiagnosisName() {
        return diagnosisName;
    }

    public void setDiagnosisName(String diagnosisName) {
        this.diagnosisName = diagnosisName;
    }

    @Basic
    @Column(name = "diagnosis_time")
    public String getDiagnosisTime() {
        return diagnosisTime;
    }

    public void setDiagnosisTime(String diagnosisTime) {
        this.diagnosisTime = diagnosisTime;
    }

    @Basic
    @Column(name = "in_time")
    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    @Basic
    @Column(name = "page_source")
    public String getPageSource() {
        return pageSource;
    }

    public void setPageSource(String pageSource) {
        this.pageSource = pageSource;
    }

    @Basic
    @Column(name = "doctor_id", nullable = true, length = 50)
    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    @Basic
    @Column(name = "doctor_name", nullable = true, length = 50)
    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    @Basic
    @Column(name = "dept_code", nullable = true, length = 50)
    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }
}

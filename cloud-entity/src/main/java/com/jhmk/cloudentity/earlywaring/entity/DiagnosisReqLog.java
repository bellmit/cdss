package com.jhmk.cloudentity.earlywaring.entity;

import javax.persistence.*;

@Entity
@Table(name = "diagnosis_req_log")
public class DiagnosisReqLog {
    private Integer id;
    private String pid;
    private String vid;
    private String diagnosis_name;
    private String diagnosis_time;
    private String in_time;
    private String page_source;

    @Override
    public String toString() {
        return "DiagnosisReqLog{" +
                "id=" + id +
                ", pid='" + pid + '\'' +
                ", vid='" + vid + '\'' +
                ", diagnosis_name='" + diagnosis_name + '\'' +
                ", diagnosis_time='" + diagnosis_time + '\'' +
                ", in_time='" + in_time + '\'' +
                ", page_source='" + page_source + '\'' +
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
    public String getDiagnosis_name() {
        return diagnosis_name;
    }

    public void setDiagnosis_name(String diagnosis_name) {
        this.diagnosis_name = diagnosis_name;
    }

    @Basic
    @Column(name = "diagnosis_time")
    public String getDiagnosis_time() {
        return diagnosis_time;
    }

    public void setDiagnosis_time(String diagnosis_time) {
        this.diagnosis_time = diagnosis_time;
    }

    @Basic
    @Column(name = "in_time")
    public String getIn_time() {
        return in_time;
    }

    public void setIn_time(String in_time) {
        this.in_time = in_time;
    }

    @Basic
    @Column(name = "page_source")
    public String getPage_source() {
        return page_source;
    }

    public void setPage_source(String page_source) {
        this.page_source = page_source;
    }
}

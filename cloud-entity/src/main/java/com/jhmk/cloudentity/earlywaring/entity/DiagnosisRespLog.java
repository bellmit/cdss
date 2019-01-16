package com.jhmk.cloudentity.earlywaring.entity;

import javax.persistence.*;

@Entity
@Table(name = "diagnosis_resp_log")
public class DiagnosisRespLog {
    private Integer id;
    private Integer reqId;
    private String alias;
    private String haveChild;
    private String score;
    private String diseaseName;
    private String code;
    private String ishr;
    private String in_time;

    @Override
    public String toString() {
        return "DiagnosisRespLog{" +
                "id=" + id +
                ", reqId=" + reqId +
                ", alias='" + alias + '\'' +
                ", haveChild='" + haveChild + '\'' +
                ", score='" + score + '\'' +
                ", diseaseName='" + diseaseName + '\'' +
                ", code='" + code + '\'' +
                ", ishr='" + ishr + '\'' +
                ", in_time='" + in_time + '\'' +
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
    @Column(name = "reqId")
    public Integer getReqId() {
        return reqId;
    }

    public void setReqId(Integer reqId) {
        this.reqId = reqId;
    }

    @Basic
    @Column(name = "alias")
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    @Basic
    @Column(name = "haveChild")
    public String getHaveChild() {
        return haveChild;
    }

    public void setHaveChild(String haveChild) {
        this.haveChild = haveChild;
    }

    @Basic
    @Column(name = "score")
    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Basic
    @Column(name = "diseaseName")
    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "ishr")
    public String getIshr() {
        return ishr;
    }

    public void setIshr(String ishr) {
        this.ishr = ishr;
    }

    @Basic
    @Column(name = "in_time")
    public String getIn_time() {
        return in_time;
    }

    public void setIn_time(String in_time) {
        this.in_time = in_time;
    }
}

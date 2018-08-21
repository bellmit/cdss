package com.jhmk.cloudentity.cdss.pojo;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * @author ziyu.zhou
 * @date 2018/8/6 13:43
 * 检验检查提醒表
 */

@Entity
@Table(name = "jhauth_remind", schema = "jhmk_waring", catalog = "")
public class JhauthRemind {
    private int id;
    private String doctorId;
    private String patiendId;
    private String visitId;
    private String itemName;
    private Date remindTime;
    private String remindStatus;
    private String remindDate;
    private String patientName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "doctor_id", nullable = false, length = 30)
    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    @Basic
    @Column(name = "patiend_id", nullable = true, length = 30)
    public String getPatiendId() {
        return patiendId;
    }

    public void setPatiendId(String patiendId) {
        this.patiendId = patiendId;
    }

    @Basic
    @Column(name = "visit_id", nullable = true, length = 8)
    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    @Basic
    @Column(name = "item_name", nullable = true, length = 255)
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Basic
    @Column(name = "remind_time", nullable = true)
    public Date getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(Date remindTime) {
        this.remindTime = remindTime;
    }

    @Basic
    @Column(name = "remind_status", nullable = true, length = 3)
    public String getRemindStatus() {
        return remindStatus;
    }

    public void setRemindStatus(String remindStatus) {
        this.remindStatus = remindStatus;
    }

    @Transient
    public String getRemindDate() {
        return remindDate;
    }

    public void setRemindDate(String remindDate) {
        this.remindDate = remindDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JhauthRemind that = (JhauthRemind) o;
        return id == that.id &&
                Objects.equals(doctorId, that.doctorId) &&
                Objects.equals(patiendId, that.patiendId) &&
                Objects.equals(visitId, that.visitId) &&
                Objects.equals(itemName, that.itemName) &&
                Objects.equals(remindTime, that.remindTime) &&
                Objects.equals(remindStatus, that.remindStatus);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, doctorId, patiendId, visitId, itemName, remindTime, remindStatus);
    }

    @Basic
    @Column(name = "patient_name", nullable = true, length = 30)
    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
}

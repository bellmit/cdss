package com.jhmk.cloudentity.cdss.pojo;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author ziyu.zhou
 * @date 2018/7/6 17:32
 */

@Entity
@Table(name = "pat_info", schema = "yunemr")
public class PatInfo {
    private int patientId;
    private String patientName;
    private String sex;
    private Integer age;
    private String ageUnit;
    private String phone;
    private String address;
    private String idCard;
    private String work;
    private String linkMan;
    private String linkPhone;
    private String memo;

    @Id
    @Column(name = "patient_id", nullable = false)
    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    @Basic
    @Column(name = "patient_name", nullable = true, length = 255)
    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    @Basic
    @Column(name = "sex", nullable = true, length = 255)
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "age", nullable = true)
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Basic
    @Column(name = "age_unit", nullable = true, length = 255)
    public String getAgeUnit() {
        return ageUnit;
    }

    public void setAgeUnit(String ageUnit) {
        this.ageUnit = ageUnit;
    }

    @Basic
    @Column(name = "phone", nullable = true, length = 255)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "address", nullable = true, length = 255)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "id_card", nullable = true, length = 20)
    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    @Basic
    @Column(name = "work", nullable = true, length = 255)
    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    @Basic
    @Column(name = "link_man", nullable = true, length = 255)
    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    @Basic
    @Column(name = "link_phone", nullable = true, length = 20)
    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    @Basic
    @Column(name = "memo", nullable = true, length = 255)
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatInfo patInfo = (PatInfo) o;
        return patientId == patInfo.patientId &&
                Objects.equals(patientName, patInfo.patientName) &&
                Objects.equals(sex, patInfo.sex) &&
                Objects.equals(age, patInfo.age) &&
                Objects.equals(ageUnit, patInfo.ageUnit) &&
                Objects.equals(phone, patInfo.phone) &&
                Objects.equals(address, patInfo.address) &&
                Objects.equals(idCard, patInfo.idCard) &&
                Objects.equals(work, patInfo.work) &&
                Objects.equals(linkMan, patInfo.linkMan) &&
                Objects.equals(linkPhone, patInfo.linkPhone) &&
                Objects.equals(memo, patInfo.memo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(patientId, patientName, sex, age, ageUnit, phone, address, idCard, work, linkMan, linkPhone, memo);
    }
}

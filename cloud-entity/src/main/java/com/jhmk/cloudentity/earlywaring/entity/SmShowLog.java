package com.jhmk.cloudentity.earlywaring.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author ziyu.zhou
 * @date 2018/9/4 17:57
 */

@Entity
@Table(name = "sm_show_log", schema = "jhmk_waring", catalog = "")
public class SmShowLog {
    private int id;
    private String doctorId;
    private String patientId;
    private String visitId;
    private String date;
    private Integer ruleStatus;
    private String type;
    private String value;
    private String itemName;
    private String orderItemNames;
    private String drugAllergyName;
    private String significance;
    private String stat;
    private String ruleId;
    private String hintContent;
    private String ruleCondition;
    private String classification;
    private String mainIllName;
    private String otherIllName;
    private String otherMap;
    private String sex;
    private Integer smHospitalLogId;

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
    @Column(name = "doctor_id", nullable = true, length = 20)
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
    @Column(name = "visit_id", nullable = true, length = 2)
    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    @Basic
    @Column(name = "date", nullable = true, length = 100)
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Basic
    @Column(name = "rule_status", nullable = true)
    public Integer getRuleStatus() {
        return ruleStatus;
    }

    public void setRuleStatus(Integer ruleStatus) {
        this.ruleStatus = ruleStatus;
    }

    @Basic
    @Column(name = "type", nullable = true, length = 50)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "value", nullable = true, length = 100)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Basic
    @Column(name = "item_name", nullable = true, length = 20)
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Basic
    @Column(name = "order_item_names", nullable = true, length = 100)
    public String getOrderItemNames() {
        return orderItemNames;
    }

    public void setOrderItemNames(String orderItemNames) {
        this.orderItemNames = orderItemNames;
    }

    @Basic
    @Column(name = "drug_allergy_name", nullable = true, length = 100)
    public String getDrugAllergyName() {
        return drugAllergyName;
    }

    public void setDrugAllergyName(String drugAllergyName) {
        this.drugAllergyName = drugAllergyName;
    }

    @Basic
    @Column(name = "significance", nullable = true, length = 100)
    public String getSignificance() {
        return significance;
    }

    public void setSignificance(String significance) {
        this.significance = significance;
    }

    @Basic
    @Column(name = "stat", nullable = true, length = 10)
    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    @Basic
    @Column(name = "rule_id", nullable = true, length = 100)
    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    @Basic
    @Column(name = "hint_content", nullable = true, length = 100)
    public String getHintContent() {
        return hintContent;
    }

    public void setHintContent(String hintContent) {
        this.hintContent = hintContent;
    }

    @Basic
    @Column(name = "rule_condition", nullable = true, length = 255)
    public String getRuleCondition() {
        return ruleCondition;
    }

    public void setRuleCondition(String ruleCondition) {
        this.ruleCondition = ruleCondition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SmShowLog log = (SmShowLog) o;
        return id == log.id &&
                Objects.equals(doctorId, log.doctorId) &&
                Objects.equals(patientId, log.patientId) &&
                Objects.equals(visitId, log.visitId) &&
                Objects.equals(date, log.date) &&
                Objects.equals(ruleStatus, log.ruleStatus) &&
                Objects.equals(type, log.type) &&
                Objects.equals(value, log.value) &&
                Objects.equals(itemName, log.itemName) &&
                Objects.equals(orderItemNames, log.orderItemNames) &&
                Objects.equals(drugAllergyName, log.drugAllergyName) &&
                Objects.equals(significance, log.significance) &&
                Objects.equals(stat, log.stat) &&
                Objects.equals(ruleId, log.ruleId) &&
                Objects.equals(hintContent, log.hintContent) &&
                Objects.equals(ruleCondition, log.ruleCondition);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, doctorId, patientId, visitId, date, ruleStatus, type, value, itemName, orderItemNames, drugAllergyName, significance, stat, ruleId, hintContent, ruleCondition);
    }

    @Basic
    @Column(name = "classification", nullable = true, length = 8)
    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    @Basic
    @Column(name = "main_ill_name", nullable = true, length = 20)
    public String getMainIllName() {
        return mainIllName;
    }

    public void setMainIllName(String mainIllName) {
        this.mainIllName = mainIllName;
    }

    @Basic
    @Column(name = "other_ill_name", nullable = true, length = 20)
    public String getOtherIllName() {
        return otherIllName;
    }

    public void setOtherIllName(String otherIllName) {
        this.otherIllName = otherIllName;
    }

    @Basic
    @Column(name = "other_map", nullable = true, length = 100)
    public String getOtherMap() {
        return otherMap;
    }

    public void setOtherMap(String otherMap) {
        this.otherMap = otherMap;
    }

    @Basic
    @Column(name = "sex", nullable = true, length = 10)
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Basic
    @Column(name = "sm_hospital_log_id", nullable = true)
    public Integer getSmHospitalLogId() {
        return smHospitalLogId;
    }

    public void setSmHospitalLogId(Integer smHospitalLogId) {
        this.smHospitalLogId = smHospitalLogId;
    }
}

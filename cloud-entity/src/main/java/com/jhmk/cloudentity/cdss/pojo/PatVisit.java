package com.jhmk.cloudentity.cdss.pojo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author ziyu.zhou
 * @date 2018/7/6 17:32
 */

@Entity
@Table(name = "pat_visit", schema = "yunemr")
public class PatVisit {
    private long id;
    private String patientName;
    private String sex;
    private Integer age;
    private String ageUnit;
    private String visitDept;
    private Timestamp visitDate;
    private String visitDoctor;
    private String feeName;
    private Integer receivableAccount;
    private Integer collectedAmount;
    private Integer preferentialAmount;
    private String state;
    private String patientId;
    private String creatorName;
    private String creatorId;
    private Date createDateTime;
    private Integer printFlag;
    private String hospitalNo;
    private String phone;
    private String adress;
    private String chiefComplaint;
    private String presentHistory;
    private String personalHistory;
    private String allergies;
    private String famillyHistory;
    private Integer physicalExaminationTemperature;
    private Integer physicalExaminationWeight;
    private Integer physicalExaminationHeartRate;
    private Integer physicalExaminationSystolicPressure;
    private Integer physicalExaminationDiastolicPressure;
    private String physicalExaminationOthers;
    private String diagnosisDesc;
    private String orderName;
    private BigDecimal totalAmount;
    private Integer visitId;
    private String idCard;
    private String linkMan;
    private String linkPhone;
    private String work;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "patient_name", nullable = true, length = 50)
    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
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
    @Column(name = "age", nullable = true)
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Basic
    @Column(name = "age_unit", nullable = true, length = 5)
    public String getAgeUnit() {
        return ageUnit;
    }

    public void setAgeUnit(String ageUnit) {
        this.ageUnit = ageUnit;
    }

    @Basic
    @Column(name = "visit_dept", nullable = true, length = 25)
    public String getVisitDept() {
        return visitDept;
    }

    public void setVisitDept(String visitDept) {
        this.visitDept = visitDept;
    }

    @Basic
    @Column(name = "visit_date", nullable = true)
    public Timestamp getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Timestamp visitDate) {
        this.visitDate = visitDate;
    }

    @Basic
    @Column(name = "visit_doctor", nullable = true, length = 50)
    public String getVisitDoctor() {
        return visitDoctor;
    }

    public void setVisitDoctor(String visitDoctor) {
        this.visitDoctor = visitDoctor;
    }

    @Basic
    @Column(name = "fee_name", nullable = true, length = 20)
    public String getFeeName() {
        return feeName;
    }

    public void setFeeName(String feeName) {
        this.feeName = feeName;
    }

    @Basic
    @Column(name = "receivable_account", nullable = true)
    public Integer getReceivableAccount() {
        return receivableAccount;
    }

    public void setReceivableAccount(Integer receivableAccount) {
        this.receivableAccount = receivableAccount;
    }

    @Basic
    @Column(name = "collected_amount", nullable = true)
    public Integer getCollectedAmount() {
        return collectedAmount;
    }

    public void setCollectedAmount(Integer collectedAmount) {
        this.collectedAmount = collectedAmount;
    }

    @Basic
    @Column(name = "preferential_amount", nullable = true)
    public Integer getPreferentialAmount() {
        return preferentialAmount;
    }

    public void setPreferentialAmount(Integer preferentialAmount) {
        this.preferentialAmount = preferentialAmount;
    }

    @Basic
    @Column(name = "state", nullable = true, length = 255)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Basic
    @Column(name = "patient_id", nullable = true, length = 16)
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    @Basic
    @Column(name = "creator_name", nullable = true, length = 30)
    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    @Basic
    @Column(name = "creator_id", nullable = true, length = 16)
    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    @Basic
    @Column(name = "create_date_time", nullable = true)
    public Date getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(Date createDateTime) {
        this.createDateTime = createDateTime;
    }

    @Basic
    @Column(name = "print_flag", nullable = true)
    public Integer getPrintFlag() {
        return printFlag;
    }

    public void setPrintFlag(Integer printFlag) {
        this.printFlag = printFlag;
    }

    @Basic
    @Column(name = "hospital_no", nullable = true, length = 16)
    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    @Basic
    @Column(name = "phone", nullable = true, length = 20)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "adress", nullable = true, length = 100)
    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    @Basic
    @Column(name = "chief_complaint", nullable = true, length = 100)
    public String getChiefComplaint() {
        return chiefComplaint;
    }

    public void setChiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint;
    }

    @Basic
    @Column(name = "present_history", nullable = true, length = 100)
    public String getPresentHistory() {
        return presentHistory;
    }

    public void setPresentHistory(String presentHistory) {
        this.presentHistory = presentHistory;
    }

    @Basic
    @Column(name = "personal_history", nullable = true, length = 100)
    public String getPersonalHistory() {
        return personalHistory;
    }

    public void setPersonalHistory(String personalHistory) {
        this.personalHistory = personalHistory;
    }

    @Basic
    @Column(name = "allergies", nullable = true, length = 100)
    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    @Basic
    @Column(name = "familly_history", nullable = true, length = 100)
    public String getFamillyHistory() {
        return famillyHistory;
    }

    public void setFamillyHistory(String famillyHistory) {
        this.famillyHistory = famillyHistory;
    }

    @Basic
    @Column(name = "physical_examination_temperature", nullable = true)
    public Integer getPhysicalExaminationTemperature() {
        return physicalExaminationTemperature;
    }

    public void setPhysicalExaminationTemperature(Integer physicalExaminationTemperature) {
        this.physicalExaminationTemperature = physicalExaminationTemperature;
    }

    @Basic
    @Column(name = "physical_examination_weight", nullable = true)
    public Integer getPhysicalExaminationWeight() {
        return physicalExaminationWeight;
    }

    public void setPhysicalExaminationWeight(Integer physicalExaminationWeight) {
        this.physicalExaminationWeight = physicalExaminationWeight;
    }

    @Basic
    @Column(name = "physical_examination_heart_rate", nullable = true)
    public Integer getPhysicalExaminationHeartRate() {
        return physicalExaminationHeartRate;
    }

    public void setPhysicalExaminationHeartRate(Integer physicalExaminationHeartRate) {
        this.physicalExaminationHeartRate = physicalExaminationHeartRate;
    }

    @Basic
    @Column(name = "physical_examination_systolic_pressure", nullable = true)
    public Integer getPhysicalExaminationSystolicPressure() {
        return physicalExaminationSystolicPressure;
    }

    public void setPhysicalExaminationSystolicPressure(Integer physicalExaminationSystolicPressure) {
        this.physicalExaminationSystolicPressure = physicalExaminationSystolicPressure;
    }

    @Basic
    @Column(name = "physical_examination_diastolic_pressure", nullable = true)
    public Integer getPhysicalExaminationDiastolicPressure() {
        return physicalExaminationDiastolicPressure;
    }

    public void setPhysicalExaminationDiastolicPressure(Integer physicalExaminationDiastolicPressure) {
        this.physicalExaminationDiastolicPressure = physicalExaminationDiastolicPressure;
    }

    @Basic
    @Column(name = "physical_examination_others", nullable = true, length = 100)
    public String getPhysicalExaminationOthers() {
        return physicalExaminationOthers;
    }

    public void setPhysicalExaminationOthers(String physicalExaminationOthers) {
        this.physicalExaminationOthers = physicalExaminationOthers;
    }

    @Basic
    @Column(name = "diagnosis_desc", nullable = true, length = 50)
    public String getDiagnosisDesc() {
        return diagnosisDesc;
    }

    public void setDiagnosisDesc(String diagnosisDesc) {
        this.diagnosisDesc = diagnosisDesc;
    }

    @Basic
    @Column(name = "order_name", nullable = true, length = 50)
    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    @Basic
    @Column(name = "total_amount", nullable = true, precision = 2)
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Basic
    @Column(name = "visit_id", nullable = true)
    public Integer getVisitId() {
        return visitId;
    }

    public void setVisitId(Integer visitId) {
        this.visitId = visitId;
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
    @Column(name = "link_man", nullable = true, length = 255)
    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    @Basic
    @Column(name = "link_phone", nullable = true, length = 255)
    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    @Basic
    @Column(name = "work", nullable = true, length = 255)
    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatVisit patVisit = (PatVisit) o;
        return id == patVisit.id &&
                Objects.equals(patientName, patVisit.patientName) &&
                Objects.equals(sex, patVisit.sex) &&
                Objects.equals(age, patVisit.age) &&
                Objects.equals(ageUnit, patVisit.ageUnit) &&
                Objects.equals(visitDept, patVisit.visitDept) &&
                Objects.equals(visitDate, patVisit.visitDate) &&
                Objects.equals(visitDoctor, patVisit.visitDoctor) &&
                Objects.equals(feeName, patVisit.feeName) &&
                Objects.equals(receivableAccount, patVisit.receivableAccount) &&
                Objects.equals(collectedAmount, patVisit.collectedAmount) &&
                Objects.equals(preferentialAmount, patVisit.preferentialAmount) &&
                Objects.equals(state, patVisit.state) &&
                Objects.equals(patientId, patVisit.patientId) &&
                Objects.equals(creatorName, patVisit.creatorName) &&
                Objects.equals(creatorId, patVisit.creatorId) &&
                Objects.equals(createDateTime, patVisit.createDateTime) &&
                Objects.equals(printFlag, patVisit.printFlag) &&
                Objects.equals(hospitalNo, patVisit.hospitalNo) &&
                Objects.equals(phone, patVisit.phone) &&
                Objects.equals(adress, patVisit.adress) &&
                Objects.equals(chiefComplaint, patVisit.chiefComplaint) &&
                Objects.equals(presentHistory, patVisit.presentHistory) &&
                Objects.equals(personalHistory, patVisit.personalHistory) &&
                Objects.equals(allergies, patVisit.allergies) &&
                Objects.equals(famillyHistory, patVisit.famillyHistory) &&
                Objects.equals(physicalExaminationTemperature, patVisit.physicalExaminationTemperature) &&
                Objects.equals(physicalExaminationWeight, patVisit.physicalExaminationWeight) &&
                Objects.equals(physicalExaminationHeartRate, patVisit.physicalExaminationHeartRate) &&
                Objects.equals(physicalExaminationSystolicPressure, patVisit.physicalExaminationSystolicPressure) &&
                Objects.equals(physicalExaminationDiastolicPressure, patVisit.physicalExaminationDiastolicPressure) &&
                Objects.equals(physicalExaminationOthers, patVisit.physicalExaminationOthers) &&
                Objects.equals(diagnosisDesc, patVisit.diagnosisDesc) &&
                Objects.equals(orderName, patVisit.orderName) &&
                Objects.equals(totalAmount, patVisit.totalAmount) &&
                Objects.equals(visitId, patVisit.visitId) &&
                Objects.equals(idCard, patVisit.idCard) &&
                Objects.equals(linkMan, patVisit.linkMan) &&
                Objects.equals(linkPhone, patVisit.linkPhone) &&
                Objects.equals(work, patVisit.work);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, patientName, sex, age, ageUnit, visitDept, visitDate, visitDoctor, feeName, receivableAccount, collectedAmount, preferentialAmount, state, patientId, creatorName, creatorId, createDateTime, printFlag, hospitalNo, phone, adress, chiefComplaint, presentHistory, personalHistory, allergies, famillyHistory, physicalExaminationTemperature, physicalExaminationWeight, physicalExaminationHeartRate, physicalExaminationSystolicPressure, physicalExaminationDiastolicPressure, physicalExaminationOthers, diagnosisDesc, orderName, totalAmount, visitId, idCard, linkMan, linkPhone, work);
    }
}

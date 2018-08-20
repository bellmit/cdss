package com.jhmk.clouddatasource.entity;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;

/**
 * @author ziyu.zhou
 * @date 2018/7/25 9:58
 */

/**
 * 首页诊断
 */

@Entity
@Table(name = "PAT_DIAGNOSIS", schema = "JHEMR")
public class PatDiagnosis {
    private String patientId;
    private long visitId;
    private String diagnosisType;
    private long diagnosisNo;
    private long diagnosisSubNo;
    private String diagnosisDesc;
    private Time diagnosisDate;
    private String diagnosticianId;
    private Time lastModifyDate;
    private String modifierId;
    private Time superSignDate;
    private String superId;
    private String flag;
    private String diagnosisTypeName;
    private String diagnosisCode;
    private String diagnosisClass;
    private String houseman;
    private Boolean confirmedIndicator;
    private Long id;
    private Long parentid;
    private String deptCode;
    private Boolean contagious;
    private String hospitalNo;
    private boolean diagnosisFlag;
    private String diagnosisNote;
    private String diagnosisDescXml;
    private String diagnosisName;
    private String diagnosisNameBefore;
    private String diagnosisNameAfter;
    private Long fileVisitType;
    private String operTreatIndicator1;
    private String operTreatIndicator1Id;
    private String dischargeConditions;
    private String infection;
    private Time parentSignDate;
    private String parentId;
    private Time lastModifyDateTime;
    private Long status;
    private Long asyncflag;

    @Basic
    @Column(name = "PATIENT_ID", nullable = false, length = 16)
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    @Basic
    @Column(name = "VISIT_ID", nullable = false, precision = 0)
    public long getVisitId() {
        return visitId;
    }

    public void setVisitId(long visitId) {
        this.visitId = visitId;
    }

    @Basic
    @Column(name = "DIAGNOSIS_TYPE", nullable = false, length = 1)
    public String getDiagnosisType() {
        return diagnosisType;
    }

    public void setDiagnosisType(String diagnosisType) {
        this.diagnosisType = diagnosisType;
    }

    @Basic
    @Column(name = "DIAGNOSIS_NO", nullable = false, precision = 0)
    public long getDiagnosisNo() {
        return diagnosisNo;
    }

    public void setDiagnosisNo(long diagnosisNo) {
        this.diagnosisNo = diagnosisNo;
    }

    @Basic
    @Column(name = "DIAGNOSIS_SUB_NO", nullable = false, precision = 0)
    public long getDiagnosisSubNo() {
        return diagnosisSubNo;
    }

    public void setDiagnosisSubNo(long diagnosisSubNo) {
        this.diagnosisSubNo = diagnosisSubNo;
    }

    @Basic
    @Column(name = "DIAGNOSIS_DESC", nullable = true, length = 200)
    public String getDiagnosisDesc() {
        return diagnosisDesc;
    }

    public void setDiagnosisDesc(String diagnosisDesc) {
        this.diagnosisDesc = diagnosisDesc;
    }

    @Basic
    @Column(name = "DIAGNOSIS_DATE", nullable = true)
    public Time getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(Time diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    @Basic
    @Column(name = "DIAGNOSTICIAN_ID", nullable = true, length = 16)
    public String getDiagnosticianId() {
        return diagnosticianId;
    }

    public void setDiagnosticianId(String diagnosticianId) {
        this.diagnosticianId = diagnosticianId;
    }

    @Basic
    @Column(name = "LAST_MODIFY_DATE", nullable = true)
    public Time getLastModifyDate() {
        return lastModifyDate;
    }

    public void setLastModifyDate(Time lastModifyDate) {
        this.lastModifyDate = lastModifyDate;
    }

    @Basic
    @Column(name = "MODIFIER_ID", nullable = true, length = 16)
    public String getModifierId() {
        return modifierId;
    }

    public void setModifierId(String modifierId) {
        this.modifierId = modifierId;
    }

    @Basic
    @Column(name = "SUPER_SIGN_DATE", nullable = true)
    public Time getSuperSignDate() {
        return superSignDate;
    }

    public void setSuperSignDate(Time superSignDate) {
        this.superSignDate = superSignDate;
    }

    @Basic
    @Column(name = "SUPER_ID", nullable = true, length = 16)
    public String getSuperId() {
        return superId;
    }

    public void setSuperId(String superId) {
        this.superId = superId;
    }

    @Basic
    @Column(name = "FLAG", nullable = true, length = 1)
    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Basic
    @Column(name = "DIAGNOSIS_TYPE_NAME", nullable = false, length = 20)
    public String getDiagnosisTypeName() {
        return diagnosisTypeName;
    }

    public void setDiagnosisTypeName(String diagnosisTypeName) {
        this.diagnosisTypeName = diagnosisTypeName;
    }

    @Basic
    @Column(name = "DIAGNOSIS_CODE", nullable = true, length = 30)
    public String getDiagnosisCode() {
        return diagnosisCode;
    }

    public void setDiagnosisCode(String diagnosisCode) {
        this.diagnosisCode = diagnosisCode;
    }

    @Basic
    @Column(name = "DIAGNOSIS_CLASS", nullable = true, length = 2)
    public String getDiagnosisClass() {
        return diagnosisClass;
    }

    public void setDiagnosisClass(String diagnosisClass) {
        this.diagnosisClass = diagnosisClass;
    }

    @Basic
    @Column(name = "HOUSEMAN", nullable = true, length = 16)
    public String getHouseman() {
        return houseman;
    }

    public void setHouseman(String houseman) {
        this.houseman = houseman;
    }

    @Basic
    @Column(name = "CONFIRMED_INDICATOR", nullable = true, precision = 0)
    public Boolean getConfirmedIndicator() {
        return confirmedIndicator;
    }

    public void setConfirmedIndicator(Boolean confirmedIndicator) {
        this.confirmedIndicator = confirmedIndicator;
    }

    @Basic
    @Id
    @Column(name = "ID", nullable = true, precision = 0)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "PARENTID", nullable = true, precision = 0)
    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    @Basic
    @Column(name = "DEPT_CODE", nullable = true, length = 16)
    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    @Basic
    @Column(name = "CONTAGIOUS", nullable = true, precision = 0)
    public Boolean getContagious() {
        return contagious;
    }

    public void setContagious(Boolean contagious) {
        this.contagious = contagious;
    }

    @Basic
    @Column(name = "HOSPITAL_NO", nullable = false, length = 16)
    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    @Basic
    @Column(name = "DIAGNOSIS_FLAG", nullable = false, precision = 0)
    public boolean isDiagnosisFlag() {
        return diagnosisFlag;
    }

    public void setDiagnosisFlag(boolean diagnosisFlag) {
        this.diagnosisFlag = diagnosisFlag;
    }

    @Basic
    @Column(name = "DIAGNOSIS_NOTE", nullable = true, length = 30)
    public String getDiagnosisNote() {
        return diagnosisNote;
    }

    public void setDiagnosisNote(String diagnosisNote) {
        this.diagnosisNote = diagnosisNote;
    }

    @Basic
    @Column(name = "DIAGNOSIS_DESC_XML", nullable = true, length = 500)
    public String getDiagnosisDescXml() {
        return diagnosisDescXml;
    }

    public void setDiagnosisDescXml(String diagnosisDescXml) {
        this.diagnosisDescXml = diagnosisDescXml;
    }

    @Basic
    @Column(name = "DIAGNOSIS_NAME", nullable = true, length = 100)
    public String getDiagnosisName() {
        return diagnosisName;
    }

    public void setDiagnosisName(String diagnosisName) {
        this.diagnosisName = diagnosisName;
    }

    @Basic
    @Column(name = "DIAGNOSIS_NAME_BEFORE", nullable = true, length = 100)
    public String getDiagnosisNameBefore() {
        return diagnosisNameBefore;
    }

    public void setDiagnosisNameBefore(String diagnosisNameBefore) {
        this.diagnosisNameBefore = diagnosisNameBefore;
    }

    @Basic
    @Column(name = "DIAGNOSIS_NAME_AFTER", nullable = true, length = 100)
    public String getDiagnosisNameAfter() {
        return diagnosisNameAfter;
    }

    public void setDiagnosisNameAfter(String diagnosisNameAfter) {
        this.diagnosisNameAfter = diagnosisNameAfter;
    }

    @Basic
    @Column(name = "FILE_VISIT_TYPE", nullable = true, precision = 0)
    public Long getFileVisitType() {
        return fileVisitType;
    }

    public void setFileVisitType(Long fileVisitType) {
        this.fileVisitType = fileVisitType;
    }

    @Basic
    @Column(name = "OPER_TREAT_INDICATOR1", nullable = true, length = 16)
    public String getOperTreatIndicator1() {
        return operTreatIndicator1;
    }

    public void setOperTreatIndicator1(String operTreatIndicator1) {
        this.operTreatIndicator1 = operTreatIndicator1;
    }

    @Basic
    @Column(name = "OPER_TREAT_INDICATOR1_ID", nullable = true, length = 16)
    public String getOperTreatIndicator1Id() {
        return operTreatIndicator1Id;
    }

    public void setOperTreatIndicator1Id(String operTreatIndicator1Id) {
        this.operTreatIndicator1Id = operTreatIndicator1Id;
    }

    @Basic
    @Column(name = "DISCHARGE_CONDITIONS", nullable = true, length = 16)
    public String getDischargeConditions() {
        return dischargeConditions;
    }

    public void setDischargeConditions(String dischargeConditions) {
        this.dischargeConditions = dischargeConditions;
    }

    @Basic
    @Column(name = "INFECTION", nullable = true, length = 1)
    public String getInfection() {
        return infection;
    }

    public void setInfection(String infection) {
        this.infection = infection;
    }

    @Basic
    @Column(name = "PARENT_SIGN_DATE", nullable = true)
    public Time getParentSignDate() {
        return parentSignDate;
    }

    public void setParentSignDate(Time parentSignDate) {
        this.parentSignDate = parentSignDate;
    }

    @Basic
    @Column(name = "PARENT_ID", nullable = true, length = 30)
    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Basic
    @Column(name = "LAST_MODIFY_DATE_TIME", nullable = true)
    public Time getLastModifyDateTime() {
        return lastModifyDateTime;
    }

    public void setLastModifyDateTime(Time lastModifyDateTime) {
        this.lastModifyDateTime = lastModifyDateTime;
    }

    @Basic
    @Column(name = "STATUS", nullable = true, precision = 0)
    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    @Basic
    @Column(name = "ASYNCFLAG", nullable = true, precision = 0)
    public Long getAsyncflag() {
        return asyncflag;
    }

    public void setAsyncflag(Long asyncflag) {
        this.asyncflag = asyncflag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatDiagnosis that = (PatDiagnosis) o;
        return visitId == that.visitId &&
                diagnosisNo == that.diagnosisNo &&
                diagnosisSubNo == that.diagnosisSubNo &&
                diagnosisFlag == that.diagnosisFlag &&
                Objects.equals(patientId, that.patientId) &&
                Objects.equals(diagnosisType, that.diagnosisType) &&
                Objects.equals(diagnosisDesc, that.diagnosisDesc) &&
                Objects.equals(diagnosisDate, that.diagnosisDate) &&
                Objects.equals(diagnosticianId, that.diagnosticianId) &&
                Objects.equals(lastModifyDate, that.lastModifyDate) &&
                Objects.equals(modifierId, that.modifierId) &&
                Objects.equals(superSignDate, that.superSignDate) &&
                Objects.equals(superId, that.superId) &&
                Objects.equals(flag, that.flag) &&
                Objects.equals(diagnosisTypeName, that.diagnosisTypeName) &&
                Objects.equals(diagnosisCode, that.diagnosisCode) &&
                Objects.equals(diagnosisClass, that.diagnosisClass) &&
                Objects.equals(houseman, that.houseman) &&
                Objects.equals(confirmedIndicator, that.confirmedIndicator) &&
                Objects.equals(id, that.id) &&
                Objects.equals(parentid, that.parentid) &&
                Objects.equals(deptCode, that.deptCode) &&
                Objects.equals(contagious, that.contagious) &&
                Objects.equals(hospitalNo, that.hospitalNo) &&
                Objects.equals(diagnosisNote, that.diagnosisNote) &&
                Objects.equals(diagnosisDescXml, that.diagnosisDescXml) &&
                Objects.equals(diagnosisName, that.diagnosisName) &&
                Objects.equals(diagnosisNameBefore, that.diagnosisNameBefore) &&
                Objects.equals(diagnosisNameAfter, that.diagnosisNameAfter) &&
                Objects.equals(fileVisitType, that.fileVisitType) &&
                Objects.equals(operTreatIndicator1, that.operTreatIndicator1) &&
                Objects.equals(operTreatIndicator1Id, that.operTreatIndicator1Id) &&
                Objects.equals(dischargeConditions, that.dischargeConditions) &&
                Objects.equals(infection, that.infection) &&
                Objects.equals(parentSignDate, that.parentSignDate) &&
                Objects.equals(parentId, that.parentId) &&
                Objects.equals(lastModifyDateTime, that.lastModifyDateTime) &&
                Objects.equals(status, that.status) &&
                Objects.equals(asyncflag, that.asyncflag);
    }

    @Override
    public int hashCode() {

        return Objects.hash(patientId, visitId, diagnosisType, diagnosisNo, diagnosisSubNo, diagnosisDesc, diagnosisDate, diagnosticianId, lastModifyDate, modifierId, superSignDate, superId, flag, diagnosisTypeName, diagnosisCode, diagnosisClass, houseman, confirmedIndicator, id, parentid, deptCode, contagious, hospitalNo, diagnosisFlag, diagnosisNote, diagnosisDescXml, diagnosisName, diagnosisNameBefore, diagnosisNameAfter, fileVisitType, operTreatIndicator1, operTreatIndicator1Id, dischargeConditions, infection, parentSignDate, parentId, lastModifyDateTime, status, asyncflag);
    }
}

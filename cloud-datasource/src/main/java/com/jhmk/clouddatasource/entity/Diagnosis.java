package com.jhmk.clouddatasource.entity;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;

/**
 * @author ziyu.zhou
 * @date 2018/7/25 11:04
 */

@Entity
public class Diagnosis {
    private String patientId;
    private long visitId;
    private String diagnosisType;
    private long diagnosisNo;
    private String diagnosisDesc;
    private Time diagnosisDate;
    private Long treatDays;
    private String treatResult;
    private Boolean operTreatIndicator;
    private String diagnosisDescPart;
    private String differentiation;
    private String hospitalNo;
    private String admissionConditions;
    private String operTreatIndicator1;
    private String operTreatIndicator1Id;
    private Byte id;
    private String sequelae;
    private String healing;
    private String dischargeConditions;
    private String diagnosisFlag;
    private String diagnosisNoF;
    private String firstAgainDiagnosis;
    private String deptName;
    private String blCheckNo;
    private String diagnosisNoFCode;
    private String diagnosisFlagId;
    private String diagnosisCode;
    private String diagnosisPart;
    private String inHospitalCase;
    private Time lastModifyDateTime;

    @Id
    @Column(name = "PATIENT_ID", nullable = false, length = 16)
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    @Id
    @Column(name = "VISIT_ID", nullable = false, precision = 0)
    public long getVisitId() {
        return visitId;
    }

    public void setVisitId(long visitId) {
        this.visitId = visitId;
    }

    @Id
    @Column(name = "DIAGNOSIS_TYPE", nullable = false, length = 2)
    public String getDiagnosisType() {
        return diagnosisType;
    }

    public void setDiagnosisType(String diagnosisType) {
        this.diagnosisType = diagnosisType;
    }

    @Id
    @Column(name = "DIAGNOSIS_NO", nullable = false, precision = 0)
    public long getDiagnosisNo() {
        return diagnosisNo;
    }

    public void setDiagnosisNo(long diagnosisNo) {
        this.diagnosisNo = diagnosisNo;
    }

    @Basic
    @Column(name = "DIAGNOSIS_DESC", nullable = true, length = 1500)
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
    @Column(name = "TREAT_DAYS", nullable = true, precision = 0)
    public Long getTreatDays() {
        return treatDays;
    }

    public void setTreatDays(Long treatDays) {
        this.treatDays = treatDays;
    }

    @Basic
    @Column(name = "TREAT_RESULT", nullable = true, length = 4)
    public String getTreatResult() {
        return treatResult;
    }

    public void setTreatResult(String treatResult) {
        this.treatResult = treatResult;
    }

    @Basic
    @Column(name = "OPER_TREAT_INDICATOR", nullable = true, precision = 0)
    public Boolean getOperTreatIndicator() {
        return operTreatIndicator;
    }

    public void setOperTreatIndicator(Boolean operTreatIndicator) {
        this.operTreatIndicator = operTreatIndicator;
    }

    @Basic
    @Column(name = "DIAGNOSIS_DESC_PART", nullable = true, length = 200)
    public String getDiagnosisDescPart() {
        return diagnosisDescPart;
    }

    public void setDiagnosisDescPart(String diagnosisDescPart) {
        this.diagnosisDescPart = diagnosisDescPart;
    }

    @Basic
    @Column(name = "DIFFERENTIATION", nullable = true, length = 32)
    public String getDifferentiation() {
        return differentiation;
    }

    public void setDifferentiation(String differentiation) {
        this.differentiation = differentiation;
    }

    @Id
    @Column(name = "HOSPITAL_NO", nullable = false, length = 16)
    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    @Basic
    @Column(name = "ADMISSION_CONDITIONS", nullable = true, length = 36)
    public String getAdmissionConditions() {
        return admissionConditions;
    }

    public void setAdmissionConditions(String admissionConditions) {
        this.admissionConditions = admissionConditions;
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
    @Column(name = "ID", nullable = true, precision = 0)
    public Byte getId() {
        return id;
    }

    public void setId(Byte id) {
        this.id = id;
    }

    @Basic
    @Column(name = "SEQUELAE", nullable = true, length = 16)
    public String getSequelae() {
        return sequelae;
    }

    public void setSequelae(String sequelae) {
        this.sequelae = sequelae;
    }

    @Basic
    @Column(name = "HEALING", nullable = true, length = 20)
    public String getHealing() {
        return healing;
    }

    public void setHealing(String healing) {
        this.healing = healing;
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
    @Column(name = "DIAGNOSIS_FLAG", nullable = true, length = 12)
    public String getDiagnosisFlag() {
        return diagnosisFlag;
    }

    public void setDiagnosisFlag(String diagnosisFlag) {
        this.diagnosisFlag = diagnosisFlag;
    }

    @Basic
    @Column(name = "DIAGNOSIS_NO_F", nullable = true, length = 20)
    public String getDiagnosisNoF() {
        return diagnosisNoF;
    }

    public void setDiagnosisNoF(String diagnosisNoF) {
        this.diagnosisNoF = diagnosisNoF;
    }

    @Basic
    @Column(name = "FIRST_AGAIN_DIAGNOSIS", nullable = true, length = 10)
    public String getFirstAgainDiagnosis() {
        return firstAgainDiagnosis;
    }

    public void setFirstAgainDiagnosis(String firstAgainDiagnosis) {
        this.firstAgainDiagnosis = firstAgainDiagnosis;
    }

    @Basic
    @Column(name = "DEPT_NAME", nullable = true, length = 100)
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Basic
    @Column(name = "BL_CHECK_NO", nullable = true, length = 100)
    public String getBlCheckNo() {
        return blCheckNo;
    }

    public void setBlCheckNo(String blCheckNo) {
        this.blCheckNo = blCheckNo;
    }

    @Basic
    @Column(name = "DIAGNOSIS_NO_F_CODE", nullable = true, length = 20)
    public String getDiagnosisNoFCode() {
        return diagnosisNoFCode;
    }

    public void setDiagnosisNoFCode(String diagnosisNoFCode) {
        this.diagnosisNoFCode = diagnosisNoFCode;
    }

    @Basic
    @Column(name = "DIAGNOSIS_FLAG_ID", nullable = true, length = 20)
    public String getDiagnosisFlagId() {
        return diagnosisFlagId;
    }

    public void setDiagnosisFlagId(String diagnosisFlagId) {
        this.diagnosisFlagId = diagnosisFlagId;
    }

    @Basic
    @Column(name = "DIAGNOSIS_CODE", nullable = true, length = 20)
    public String getDiagnosisCode() {
        return diagnosisCode;
    }

    public void setDiagnosisCode(String diagnosisCode) {
        this.diagnosisCode = diagnosisCode;
    }

    @Basic
    @Column(name = "DIAGNOSIS_PART", nullable = true, length = 20)
    public String getDiagnosisPart() {
        return diagnosisPart;
    }

    public void setDiagnosisPart(String diagnosisPart) {
        this.diagnosisPart = diagnosisPart;
    }

    @Basic
    @Column(name = "IN_HOSPITAL_CASE", nullable = true, length = 100)
    public String getInHospitalCase() {
        return inHospitalCase;
    }

    public void setInHospitalCase(String inHospitalCase) {
        this.inHospitalCase = inHospitalCase;
    }

    @Basic
    @Column(name = "LAST_MODIFY_DATE_TIME", nullable = true)
    public Time getLastModifyDateTime() {
        return lastModifyDateTime;
    }

    public void setLastModifyDateTime(Time lastModifyDateTime) {
        this.lastModifyDateTime = lastModifyDateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Diagnosis diagnosis = (Diagnosis) o;
        return visitId == diagnosis.visitId &&
                diagnosisNo == diagnosis.diagnosisNo &&
                Objects.equals(patientId, diagnosis.patientId) &&
                Objects.equals(diagnosisType, diagnosis.diagnosisType) &&
                Objects.equals(diagnosisDesc, diagnosis.diagnosisDesc) &&
                Objects.equals(diagnosisDate, diagnosis.diagnosisDate) &&
                Objects.equals(treatDays, diagnosis.treatDays) &&
                Objects.equals(treatResult, diagnosis.treatResult) &&
                Objects.equals(operTreatIndicator, diagnosis.operTreatIndicator) &&
                Objects.equals(diagnosisDescPart, diagnosis.diagnosisDescPart) &&
                Objects.equals(differentiation, diagnosis.differentiation) &&
                Objects.equals(hospitalNo, diagnosis.hospitalNo) &&
                Objects.equals(admissionConditions, diagnosis.admissionConditions) &&
                Objects.equals(operTreatIndicator1, diagnosis.operTreatIndicator1) &&
                Objects.equals(operTreatIndicator1Id, diagnosis.operTreatIndicator1Id) &&
                Objects.equals(id, diagnosis.id) &&
                Objects.equals(sequelae, diagnosis.sequelae) &&
                Objects.equals(healing, diagnosis.healing) &&
                Objects.equals(dischargeConditions, diagnosis.dischargeConditions) &&
                Objects.equals(diagnosisFlag, diagnosis.diagnosisFlag) &&
                Objects.equals(diagnosisNoF, diagnosis.diagnosisNoF) &&
                Objects.equals(firstAgainDiagnosis, diagnosis.firstAgainDiagnosis) &&
                Objects.equals(deptName, diagnosis.deptName) &&
                Objects.equals(blCheckNo, diagnosis.blCheckNo) &&
                Objects.equals(diagnosisNoFCode, diagnosis.diagnosisNoFCode) &&
                Objects.equals(diagnosisFlagId, diagnosis.diagnosisFlagId) &&
                Objects.equals(diagnosisCode, diagnosis.diagnosisCode) &&
                Objects.equals(diagnosisPart, diagnosis.diagnosisPart) &&
                Objects.equals(inHospitalCase, diagnosis.inHospitalCase) &&
                Objects.equals(lastModifyDateTime, diagnosis.lastModifyDateTime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(patientId, visitId, diagnosisType, diagnosisNo, diagnosisDesc, diagnosisDate, treatDays, treatResult, operTreatIndicator, diagnosisDescPart, differentiation, hospitalNo, admissionConditions, operTreatIndicator1, operTreatIndicator1Id, id, sequelae, healing, dischargeConditions, diagnosisFlag, diagnosisNoF, firstAgainDiagnosis, deptName, blCheckNo, diagnosisNoFCode, diagnosisFlagId, diagnosisCode, diagnosisPart, inHospitalCase, lastModifyDateTime);
    }
}

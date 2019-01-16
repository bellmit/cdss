package com.jhmk.cloudentity.common;

import java.io.Serializable;

/**
 * @author ziyu.zhou
 * @date 2019/1/16 17:53
 * 患者信息
 *
 */

public class Huanzhexinxi implements Serializable {
    private String doctor_id;
    private String doctor_name;
    private String patient_id;
    private String visit_id;
    private String pageSource;
    private String inp_no;
    private String warnSource;
    private String pat_info_sex_name;
    private String pat_info_age_value;
    private String pat_info_age_value_unit;
    private String pat_info_marital_status_name;
    private String pat_info_occupation_name;
    private String pat_info_pregnancy_status;
    private String pat_visit_dept_admission_to_name;
    private String pat_visit_dept_admission_to_code;
    private String drug_allergy_name;
    private String emr_code;

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(String visit_id) {
        this.visit_id = visit_id;
    }

    public String getPageSource() {
        return pageSource;
    }

    public void setPageSource(String pageSource) {
        this.pageSource = pageSource;
    }

    public String getInp_no() {
        return inp_no;
    }

    public void setInp_no(String inp_no) {
        this.inp_no = inp_no;
    }

    public String getWarnSource() {
        return warnSource;
    }

    public void setWarnSource(String warnSource) {
        this.warnSource = warnSource;
    }

    public String getPat_info_sex_name() {
        return pat_info_sex_name;
    }

    public void setPat_info_sex_name(String pat_info_sex_name) {
        this.pat_info_sex_name = pat_info_sex_name;
    }

    public String getPat_info_age_value() {
        return pat_info_age_value;
    }

    public void setPat_info_age_value(String pat_info_age_value) {
        this.pat_info_age_value = pat_info_age_value;
    }

    public String getPat_info_age_value_unit() {
        return pat_info_age_value_unit;
    }

    public void setPat_info_age_value_unit(String pat_info_age_value_unit) {
        this.pat_info_age_value_unit = pat_info_age_value_unit;
    }

    public String getPat_info_marital_status_name() {
        return pat_info_marital_status_name;
    }

    public void setPat_info_marital_status_name(String pat_info_marital_status_name) {
        this.pat_info_marital_status_name = pat_info_marital_status_name;
    }

    public String getPat_info_occupation_name() {
        return pat_info_occupation_name;
    }

    public void setPat_info_occupation_name(String pat_info_occupation_name) {
        this.pat_info_occupation_name = pat_info_occupation_name;
    }

    public String getPat_info_pregnancy_status() {
        return pat_info_pregnancy_status;
    }

    public void setPat_info_pregnancy_status(String pat_info_pregnancy_status) {
        this.pat_info_pregnancy_status = pat_info_pregnancy_status;
    }

    public String getPat_visit_dept_admission_to_name() {
        return pat_visit_dept_admission_to_name;
    }

    public void setPat_visit_dept_admission_to_name(String pat_visit_dept_admission_to_name) {
        this.pat_visit_dept_admission_to_name = pat_visit_dept_admission_to_name;
    }

    public String getPat_visit_dept_admission_to_code() {
        return pat_visit_dept_admission_to_code;
    }

    public void setPat_visit_dept_admission_to_code(String pat_visit_dept_admission_to_code) {
        this.pat_visit_dept_admission_to_code = pat_visit_dept_admission_to_code;
    }

    public String getDrug_allergy_name() {
        return drug_allergy_name;
    }

    public void setDrug_allergy_name(String drug_allergy_name) {
        this.drug_allergy_name = drug_allergy_name;
    }

    public String getEmr_code() {
        return emr_code;
    }

    public void setEmr_code(String emr_code) {
        this.emr_code = emr_code;
    }
}

package com.jhmk.cloudentity.common;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ziyu.zhou
 * @date 2019/1/16 17:53
 * 患者信息
 *
 */
@Data
public class Huanzhexinxi implements Serializable {
    private String doctor_id;
    private String doctor_name;
    private String patient_id;
    private String patient_name;
    private String visit_id;
    private String pageSource; // 0 :保存病历 1：下诊断 2：打开病例 3：新建病例 6：下医嘱 7：病案首页 8：其他
    private String inp_no;//住院号
    private String warnSource;//住院 /门诊
    private String pat_info_sex_name;
    private String pat_info_age_value;
    private String pat_info_age_value_unit;
    private String pat_info_marital_status_name;//婚否
    private String pat_info_occupation_name;//职业
    private String pat_info_pregnancy_status;//妊娠状态
    private String pat_visit_dept_admission_to_name;//入院科室
    private String pat_visit_dept_admission_to_code;//入院科室编码
    private String drug_allergy_name;//过敏药物
    private String emr_code;
    //入院时间
    private String admission_time;
    //出院时间
    private String discharge_time;
}

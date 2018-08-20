package com.jhmk.cloudentity.cdss.bean;

import java.util.List;

/**
 * @author ziyu.zhou
 * @date 2018/7/31 14:03
 * 获取漏诊疾病 病例
 */

public class Misdiagnosis {
    private String id;
    private String patient_id;
    private String  visit_id;
    //部门 骨科
    private String dept_discharge_from_name;
    private String district_discharge_from_name;
    //既往史描述
    private String src;
    //既往史疾病
    private List<String>hisDiseaseList;
    //出院诊断疾病集合
    private List<String>nowDiseaseList;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getDept_discharge_from_name() {
        return dept_discharge_from_name;
    }

    public void setDept_discharge_from_name(String dept_discharge_from_name) {
        this.dept_discharge_from_name = dept_discharge_from_name;
    }

    public String getDistrict_discharge_from_name() {
        return district_discharge_from_name;
    }

    public void setDistrict_discharge_from_name(String district_discharge_from_name) {
        this.district_discharge_from_name = district_discharge_from_name;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public List<String> getHisDiseaseList() {
        return hisDiseaseList;
    }

    public void setHisDiseaseList(List<String> hisDiseaseList) {
        this.hisDiseaseList = hisDiseaseList;
    }

    public List<String> getNowDiseaseList() {
        return nowDiseaseList;
    }

    public void setNowDiseaseList(List<String> nowDiseaseList) {
        this.nowDiseaseList = nowDiseaseList;
    }


}

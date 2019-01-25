package com.jhmk.cloudservice.cdssPageService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jhmk.cloudentity.common.Huanzhexinxi;
import com.jhmk.cloudentity.earlywaring.entity.rule.*;
import com.jhmk.cloudutil.config.BaseConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * @author ziyu.zhou
 * @date 2019/1/18 11:06
 */
@Service
public class AnalyzeService {

    /**
     * 患者信息
     *
     * @param json 可查询患者信息返回json数据
     * @return 患者信息
     */
    public Huanzhexinxi analyzeJson2Huanzhexinxi(String json) {
        Huanzhexinxi huanzhexinxi = new Huanzhexinxi();
        if (StringUtils.isNotBlank(json)) {
            JSONObject object = JSONObject.parseObject(json);
            JSONArray jsonArray = object.getJSONArray(BaseConstants.BINGANSHOUYE);
            if (!jsonArray.isEmpty()) {
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                Optional.ofNullable(jsonObject.getString("AGE_VALUE")).ifPresent(s -> huanzhexinxi.setPat_info_age_value(s));
                Optional.ofNullable(jsonObject.getString("AGE_VALUE_UNIT")).ifPresent(s -> huanzhexinxi.setPat_info_age_value_unit(s));
                Optional.ofNullable(jsonObject.getString("ATTENDING_DOCTOR_NAME")).ifPresent(s -> huanzhexinxi.setDoctor_name(s));//主治医师
                Optional.ofNullable(jsonObject.getString("SEX")).ifPresent(s -> huanzhexinxi.setPat_info_sex_name(s));
                Optional.ofNullable(jsonObject.getString("DEPT_ADMISSION_TO_NAME")).ifPresent(s -> huanzhexinxi.setPat_visit_dept_admission_to_name(s));//入院科室
                Optional.ofNullable(jsonObject.getString("DEPT_ADMISSION_TO_CODE")).ifPresent(s -> huanzhexinxi.setPat_visit_dept_admission_to_code(s));//入院科室
                Optional.ofNullable(jsonObject.getString("MARITAL_STATUS")).ifPresent(s -> huanzhexinxi.setPat_info_marital_status_name(s));//入院科室
                Optional.ofNullable(jsonObject.getString("INP_NO")).ifPresent(s -> huanzhexinxi.setInp_no(s));//入院科室
                Optional.ofNullable(jsonObject.getString("ADMISSION_TIME")).ifPresent(s -> huanzhexinxi.setAdmission_time(s));//入院科室
                Optional.ofNullable(jsonObject.getString("NAME")).ifPresent(s -> huanzhexinxi.setPatient_name(s));//病人姓名
                Optional.ofNullable(jsonObject.getString("PATIENT_ID")).ifPresent(s -> huanzhexinxi.setPatient_id(s));
                Optional.ofNullable(jsonObject.getString("VISIT_ID")).ifPresent(s -> huanzhexinxi.setVisit_id(s));
                Optional.ofNullable(jsonObject.getString("OCCUPATION_NAME")).ifPresent(s -> huanzhexinxi.setPat_info_occupation_name(s));//职业
                Optional.ofNullable(jsonObject.getString("DRUG_ALLERGY_NAME")).ifPresent(s -> huanzhexinxi.setDrug_allergy_name(s));//过敏药物
            }
        }
        return huanzhexinxi;

    }

//    public Ruyuanjilu analyzeJson2Ruyuanjilu(String json) {
//        Ruyuanjilu ruyuanjilu = new Ruyuanjilu();
//        if (StringUtils.isNotBlank(json)) {
//            JSONObject object = JSONObject.parseObject(json);
//            JSONArray jsonArray = object.getJSONArray(BaseConstants.RUYUANJILU);
//            if (!jsonArray.isEmpty()) {
//                JSONObject jsonObject = jsonArray.getJSONObject(0);
//                Optional.ofNullable(jsonObject.getString("AGE_VALUE")).ifPresent(s -> huanzhexinxi.setPat_info_age_value(s));
//                Optional.ofNullable(jsonObject.getString("AGE_VALUE_UNIT")).ifPresent(s -> huanzhexinxi.setPat_info_age_value_unit(s));
//                Optional.ofNullable(jsonObject.getString("ATTENDING_DOCTOR_NAME")).ifPresent(s -> huanzhexinxi.setDoctor_name(s));//主治医师
//                Optional.ofNullable(jsonObject.getString("SEX")).ifPresent(s -> huanzhexinxi.setPat_info_sex_name(s));
//                Optional.ofNullable(jsonObject.getString("DEPT_ADMISSION_TO_NAME")).ifPresent(s -> huanzhexinxi.setPat_visit_dept_admission_to_name(s));//入院科室
//                Optional.ofNullable(jsonObject.getString("DEPT_ADMISSION_TO_CODE")).ifPresent(s -> huanzhexinxi.setPat_visit_dept_admission_to_code(s));//入院科室
//                Optional.ofNullable(jsonObject.getString("MARITAL_STATUS")).ifPresent(s -> huanzhexinxi.setPat_info_marital_status_name(s));//入院科室
//                Optional.ofNullable(jsonObject.getString("INP_NO")).ifPresent(s -> huanzhexinxi.setInp_no(s));//入院科室
//                Optional.ofNullable(jsonObject.getString("ADMISSION_TIME")).ifPresent(s -> huanzhexinxi.setAdmission_time(s));//入院科室
//                Optional.ofNullable(jsonObject.getString("NAME")).ifPresent(s -> huanzhexinxi.setPatient_name(s));//病人姓名
//                Optional.ofNullable(jsonObject.getString("PATIENT_ID")).ifPresent(s -> huanzhexinxi.setPatient_id(s));
//                Optional.ofNullable(jsonObject.getString("VISIT_ID")).ifPresent(s -> huanzhexinxi.setVisit_id(s));
//                Optional.ofNullable(jsonObject.getString("OCCUPATION_NAME")).ifPresent(s -> huanzhexinxi.setPat_info_occupation_name(s));//职业
//                Optional.ofNullable(jsonObject.getString("DRUG_ALLERGY_NAME")).ifPresent(s -> huanzhexinxi.setDrug_allergy_name(s));//过敏药物
//            }
//        }
//        return huanzhexinxi;
//
//    }

    /**
     * 病历诊断
     *
     * @param json
     * @return
     */
    public List<Binglizhenduan> analyzeJson2Binglizhenduan(String json) {
        List<Binglizhenduan> beanList = new ArrayList<>();
        if (StringUtils.isNotBlank(json)) {
            JSONObject object = JSONObject.parseObject(json);
            JSONArray jsonArray = object.getJSONArray(BaseConstants.BINGLIZHENDUAN);
            Iterator<Object> iterator = jsonArray.iterator();
            while (iterator.hasNext()) {
                Binglizhenduan bean = new Binglizhenduan();
                JSONObject next = (JSONObject) iterator.next();
//                Optional.ofNullable(next.getString("DIAGNOSIS_TIME"))
//                System.out.println(next);
            }
        }
        return beanList;

    }

    /**
     * 病历诊断
     *
     * @param json
     * @return
     */
    public List<Jianyanbaogao> analyzeJson2Jianyanbaogao(String json) {
        List<Jianyanbaogao> beanList = new ArrayList<>();
        if (StringUtils.isNotBlank(json)) {
            JSONObject object = JSONObject.parseObject(json);
            Object o = object.get(BaseConstants.JIANYANBAOGAO);
            beanList = JSONObject.parseObject(o.toString(), new TypeReference<List<Jianyanbaogao>>() {
            });
        }
        return beanList;

    }

    public List<Jianchabaogao> analyzeJson2Jianchabaogao(String json) {
        List<Jianchabaogao> beanList = new ArrayList<>();
        if (StringUtils.isNotBlank(json)) {
            JSONObject object = JSONObject.parseObject(json);
            Object o = object.get(BaseConstants.JIANCHABAOGAO);
            beanList = JSONObject.parseObject(o.toString(), new TypeReference<List<Jianchabaogao>>() {
            });
            /*JSONArray jsonArray = object.getJSONArray(BaseConstants.JIANCHABAOGAO);
            Iterator<Object> iterator = jsonArray.iterator();
            while (iterator.hasNext()) {
                Binglizhenduan bean = new Binglizhenduan();
                JSONObject next = (JSONObject) iterator.next();
//                Optional.ofNullable(next.getString("DIAGNOSIS_TIME"))
//                System.out.println(next);
            }*/
        }
        return beanList;

    }


}

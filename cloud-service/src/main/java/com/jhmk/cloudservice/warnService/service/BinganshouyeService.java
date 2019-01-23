package com.jhmk.cloudservice.warnService.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jhmk.cloudentity.common.Huanzhexinxi;
import com.jhmk.cloudentity.earlywaring.entity.repository.service.BinganshouyeRepService;
import com.jhmk.cloudentity.earlywaring.entity.rule.Binganshouye;
import com.jhmk.cloudentity.earlywaring.entity.rule.Jianyanbaogao;
import com.jhmk.cloudentity.earlywaring.entity.rule.Rule;
import com.jhmk.cloudutil.config.BaseConstants;
import com.jhmk.cloudutil.util.DbConnectionUtil;
import com.jhmk.cloudutil.util.SocketUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author ziyu.zhou
 * @date 2018/7/30 13:57
 */

@Service
public class BinganshouyeService {
    @Autowired
    DbConnectionUtil dbConnectionUtil;
    @Autowired
    SocketUtil socketUtil;
    @Autowired
    BinganshouyeRepService binganshouyeRepService;
    @Value("${socket.ip}")
    private String ip;
    @Value("${socket.port2}")
    private Integer port2;

    /**
     * 添加、覆盖规则基本信息 如果存在 就执行先删除 在添加操作
     *
     * @param rule
     */
    @Transactional
    public void saveAndFlush(Rule rule) {
        String patient_id = rule.getPatient_id();
        String visit_id = rule.getVisit_id();
        Binganshouye binganshouye = rule.getBinganshouye();
        Binganshouye byPatient_idAndVisit_id = binganshouyeRepService.findByPatient_idAndVisit_id(patient_id, visit_id);
        if (byPatient_idAndVisit_id == null) {
            binganshouyeRepService.save(binganshouye);
        }
    }


    /**
     * 嘉和socket服务获取数据
     *
     * @return
     */
    public Binganshouye getBinganshouyeFromETL(String patientId, String visitId) {
        String returnData = socketUtil.getReturnData(ip, port2, patientId, visitId, null, BaseConstants.BINGANSHOUYE);
        Binganshouye binganshouye = analyzeJson2Binganshouye(returnData);
        return binganshouye;

    }

    public static void main(String[] args) {
        String s = "{\"binganshouye\":[{\"NATION_NAME\":\"中国\",\"DRUG_ALLERGY_NAME\":\"\",\"HEALTH_CARD_NO\":\"\",\"MAILING_ADDRESS_NAME\":\"内蒙古自治区巴彦淖尔市临海区利民东街园丁巷10栋6号\",\"ADMISSION_CLASS_NAME\":\"门诊\",\"EMPLOYER_POSTCODE\":\"000000\",\"AGAIN_IN_PLAN\":\"无\",\"INSURANCE_TYPE_NAME\":\"\",\"DEPT_DISCHARGE_FROM_NAME\":\"肝胆外二病房\",\"CHARGE_TYPE\":\"外地公疗\",\"VIP_INDICATOR\":\"\",\"FIRST_LEVEL_NURS_DAYS\":\"\",\"EMER_TREAT_TIMES\":\"\",\"PATHOLOGY_NO\":\"\",\"WORKING_STATUS\":\"\",\"TUMOR_STAGE_M\":\"\",\"TUMOR_STAGE_N\":\"\",\"FOLLOW_INDICATOR\":\"随诊\",\"AGE_VALUE\":\"48\",\"LAST_VISIT_DATE\":\"\",\"IDENTITY\":\"\",\"INFUSION_REACT_TIMES\":\"\",\"PARENT_DOCTOR_NAME\":\"丘辉\",\"PATHOLOGY_TYPE\":\"\",\"INSURANCE_NO\":\"\",\"TOTAL_PAYMENTS\":\"\",\"PHONE_NUMBER\":\"\",\"NEXT_OF_KIN\":\"杨建丽\",\"BLOOD_TYPE_NAME\":\"B\",\"NEXT_OF_KIN_ZIPCODE\":\"\",\"DISCHARGE_CLASS_NAME\":\"\",\"CATALOG_DATE\":\"2012-10-17 00:00:00\",\"POSTCODE\":\"150000\",\"ICU_DAYS\":\"\",\"SEX\":\"男\",\"MR_VALUE\":\"\",\"REQUEST_DOCTOR_NAME\":\"吴建辉\",\"ICU_FIRST_IN_TIME\":\"\",\"THIRD_LEVEL_NURS_DAYS\":\"\",\"ICU_FIRST_OUT_TIME\":\"\",\"FOLLOW_INTERVAL_UNIT\":\"\",\"ID_CARD_NO\":\"152801196509250670\",\"BABY_ADMIN_WEIGHT\":\"\",\"ADM_CONDITION_NAME\":\"\",\"DRUG_ALLERGY_INDICATOR\":\"无\",\"TUMOR_STAGE_T\":\"\",\"CRITICAL_COND_DAYS\":\"\",\"DISTRICT_ADMISSION_TO_NAME\":\"肝胆外二科\",\"ARMED_SERVICES\":\"\",\"STAT_FLAG_CODE\":\"\",\"ADL_DISCHARGE\":\"100\",\"BUSINESS_PHONE_PHONE\":\"13654788715\",\"TUMOR_STAGE_IS_UNKNOWN\":\"是\",\"IDENTITY_NAME\":\"\",\"MR_QUALITY\":\"\",\"VISIT_TYPE_NAME\":\"全公费\",\"DEPT_ADMISSION_TO_NAME\":\"肝胆外二病房\",\"OPERATOR\":\"\",\"ATTENDING_DOCTOR_NAME\":\"丘辉\",\"BLOOD_TRAN_TIMES\":\"\",\"PRIMARY_NURSE_NAME\":\"\",\"SERVICE_SYSTEM_INDICATOR\":\"\",\"BEFORE_COMA_TIME\":\"\",\"IS_YZ\":\"\",\"SECOND_LEVEL_NURS_DAYS\":\"\",\"DIAGNOSIS_TIME\":\"\",\"FAMILY_ADDR_PROVINCE_NAME\":\"内蒙古自治区\",\"MARITAL_STATUS\":\"已婚\",\"RH_BLOOD_NAME\":\"阳\",\"PATIENT_IDVISIT\":\"0000969000\",\"FOLLOW_INTERVAL_VALUE\":\"\",\"SUPER_DOCTOR_NAME\":\"郝纯毅\",\"NATIONALITY_NAME\":\"汉族\",\"BED_LABEL\":\"\",\"ORIGIN_DEPT_NAME\":\"\",\"DISCHARGE_TIME\":\"2012-10-11 14:57:04\",\"IN_COMA_TIME\":\"\",\"TOP_UNIT\":\"\",\"SPEC_LEVEL_NURS_DAYS\":\"\",\"AUTOPSY_INDICATOR\":\"\",\"CONSULTING_DATE\":\"\",\"INP_DOCTOR_NAME\":\"丘辉\",\"BABY_BIRTH_WEIGHT\":\"\",\"DECUBITAL_ULCER_TIMES\":\"\",\"ESC_EMER_TIMES\":\"\",\"OCCUPATION_NAME\":\"其他\",\"NAME\":\"张颢\",\"CREATE_DATE\":\"\",\"DISTRICT_DISCHARGE_FROM_NAME\":\"肝胆外二科\",\"BLOOD_TRAN_REACT_TIMES\":\"\",\"VISIT_ID\":\"1\",\"ADMITTED_BY\":\"\",\"INP_NO\":\"696900\",\"CATALOGER\":\"王彦晨\",\"PATIENT_ID\":\"0000969000\",\"DEPT_DISCHARGE_FROM_CODE\":\"300301\",\"CONSULTING_DOCTOR\":\"\",\"BABY_AGE\":\"\",\"BIRTH_ADDRESS\":\"内蒙古自治区\",\"IN_HOSPITAL_DAYS\":\"10\",\"ADMISSION_CAUSE\":\"\",\"BLOOD_TRAN_VOL_VALUE\":\"\",\"FURTHER_DOCTOR_NAME\":\"\",\"AGE_VALUE_UNIT\":\"岁\",\"NAME_PHONETIC\":\"\",\"WARD_ADMISSION_TO_NAME\":\"\",\"EMPLOYER_ADDRESS\":\"内蒙古巴彦淖尔是电业局\",\"AGAIN_IN_PURPOSE\":\"\",\"DATE_OF_BIRTH\":\"1964-09-25 00:00:00\",\"NEXT_OF_KIN_ADDR\":\"内蒙古\",\"ADMISSION_TIME\":\"2012-10-01 13:05:11\",\"TUMOR_STAGE_OTHER\":\"\",\"RELATIONSHIP_NAME\":\"配偶\",\"PRACTICAL_DOCTOR_NAME\":\"\",\"SENIOR_DOCTOR_NAME\":\"郝纯毅\",\"ADL_ADMISSION\":\"100\",\"ADVERSE_REACTION_DRUGS_NAME\":\"\",\"TOTAL_COSTS\":\"20717.65\",\"ARCHIVING_DATE\":\"2012-10-17 00:00:00\",\"NEXT_OF_KIN_PHONE\":\"13327123860\",\"CASE_NO\":\"696900\",\"DEPT_ADMISSION_TO_CODE\":\"300301\",\"WARD_DISCHARGE_FROM_NAME\":\"肝胆外二科\",\"IS_MALIGNANT\":\"是\",\"SERIOUS_COND_DAYS\":\"\",\"DEPT_DIRECTOR_NAME\":\"郝纯毅\",\"DUTY\":\"\",\"CCU_DAYS\":\"\",\"UNIT_IN_CONTRACT\":\"\"}]}";
        Binganshouye binganshouye = analyzeJson2Binganshouye(s);
        System.out.println(binganshouye);
    }

    /**
     * 解析json为病案首页
     *
     * @param json
     * @return
     */
    public static Binganshouye analyzeJson2Binganshouye(String json) {
        Binganshouye huanzhexinxi = new Binganshouye();
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






    /**
     * 获取广安门病案首页
     *
     * @param patientId
     * @param visitId
     * @return
     */
    public Rule getGamBinganshouyeFromView(String patientId, String visitId) {
        Map<String, String> param = new HashMap<>();
        Connection conn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        Rule rule = new Rule();
        Binganshouye binganshouye = new Binganshouye();
        rule.setPatient_id(patientId);
        rule.setVisit_id(visitId);
        try {
            conn = dbConnectionUtil.openTempGamConnectionDBForBaogao();
            //HkT:<GB<： 在数据库中代表入院记录 caseid：=patient_id  admincount:入院次数
            cstmt = conn.prepareCall(" select * from  v_cdss_pat_visit where patient_id='" + patientId + "' and VISIT_ID='" + visitId + "'");
            rs = cstmt.executeQuery();// 执行
            while (rs.next()) {
                Optional.ofNullable(rs.getString("INP_NO")).ifPresent(s -> {
                    try {
                        binganshouye.setInp_no(new String(s.getBytes("iso-8859-1"), "GBK"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });
                Optional.ofNullable(rs.getString("WARNSOURCE")).ifPresent(s -> {
                    try {
                        rule.setWarnSource(new String(s.getBytes("iso-8859-1"), "GBK"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });
                Optional.ofNullable(rs.getString("doctor_id")).ifPresent(s -> {
                    try {
                        rule.setDoctor_id(new String(s.getBytes("iso-8859-1"), "GBK"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });
                Optional.ofNullable(rs.getString("doctor_name")).ifPresent(s -> {
                    try {
                        rule.setDoctor_name(new String(s.getBytes("iso-8859-1"), "GBK"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });
                rule.setPageSource("6");//下医嘱
                Optional.ofNullable(rs.getString("bed_no")).ifPresent(s -> {
                    try {
                        binganshouye.setBed_no(new String(s.getBytes("iso-8859-1"), "GBK"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });

                Optional.ofNullable(rs.getString("name")).ifPresent(s -> {
                    try {
                        binganshouye.setName(new String(s.getBytes("iso-8859-1"), "GBK"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });

                Optional.ofNullable(rs.getString("pat_info_sex_name")).ifPresent(s -> {
                    try {
                        binganshouye.setPat_info_sex_name(new String(s.getBytes("iso-8859-1"), "GBK"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });
                Optional.ofNullable(rs.getString("pat_info_age_value")).ifPresent(s -> {
                    try {
                        binganshouye.setPat_info_age_value(new String(s.getBytes("iso-8859-1"), "GBK"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });
                Optional.ofNullable(rs.getString("pat_info_age_value_unit")).ifPresent(s -> {
                    try {
                        binganshouye.setPat_info_age_value_unit(new String(s.getBytes("iso-8859-1"), "GBK"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });
                Optional.ofNullable(rs.getString("pat_info_marital_status_name")).ifPresent(s -> {
                    try {
                        binganshouye.setPat_info_marital_status_name(new String(s.getBytes("iso-8859-1"), "GBK"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });

                Optional.ofNullable(rs.getString("pat_info_occupation_name")).ifPresent(s -> {
                    try {
                        binganshouye.setPat_info_occupation_name(new String(s.getBytes("iso-8859-1"), "GBK"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });
                Optional.ofNullable(rs.getString("dept_admission_to_name")).ifPresent(s -> {
                    try {
                        binganshouye.setPat_visit_dept_admission_to_name(new String(s.getBytes("iso-8859-1"), "GBK"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });
                Optional.ofNullable(rs.getString("dept_admission_to_code")).ifPresent(s -> {
                    try {
                        binganshouye.setPat_visit_dept_admission_to_code(new String(s.getBytes("iso-8859-1"), "GBK"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });
                Optional.ofNullable(rs.getString("dept_discharge_from_name")).ifPresent(s -> {
                    try {
                        binganshouye.setPat_visit_dept_discharge_from_name(new String(s.getBytes("iso-8859-1"), "GBK"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });
                Optional.ofNullable(rs.getString("dept_discharge_from_code")).ifPresent(s -> {
                    try {
                        binganshouye.setPat_visit_dept_discharge_from_code(new String(s.getBytes("iso-8859-1"), "GBK"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });

                rule.setBinganshouye(binganshouye);
                return rule;
            }
        } catch (SQLException ex2) {
            ex2.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            dbConnectionUtil.closeConnectionDB(conn, cstmt, rs);
        }
        return rule;
    }


    /**
     * 获取上个月出院的患者pid+vid
     *
     * @return
     */
    public Set<String> getLastMonthOutHospital() {
        Set<String> set = new HashSet<>();
        Connection conn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        try {
            conn = dbConnectionUtil.openGamConnectionDBForBaogao();
            //HkT:<GB<： 在数据库中代表入院记录 caseid：=patient_id  admincount:入院次数
            cstmt = conn.prepareCall(" select * from  v_cdss_pat_visit_out ");
            rs = cstmt.executeQuery();// 执行
            while (rs.next()) {
                String patientId = rs.getString("patient_id");
                String visitId = rs.getString("visit_id");
                if (StringUtils.isNotBlank(patientId) && StringUtils.isNotBlank(visitId)) {
                    set.add(patientId + "," + visitId);
                }
                return set;
            }
        } catch (SQLException ex2) {
            ex2.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            dbConnectionUtil.closeConnectionDB(conn, cstmt, rs);
        }
        return set;
    }

    /**
     * 获取在院数据
     *
     * @return
     */
    public Set<String> getHospitalPidAndVid() {
        Set<String> set = new HashSet<>();
        Connection conn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        try {
            conn = dbConnectionUtil.openGamConnectionDBForBaogao();
            //HkT:<GB<： 在数据库中代表入院记录 caseid：=patient_id  admincount:入院次数
            cstmt = conn.prepareCall(" select * from  v_cdss_pat_visit ");
            rs = cstmt.executeQuery();// 执行
            while (rs.next()) {
                String patientId = rs.getString("patient_id");
                String visitId = rs.getString("visit_id");
                if (StringUtils.isNotBlank(patientId) && StringUtils.isNotBlank(visitId)) {
                    set.add(patientId + "," + visitId);
                }
            }
        } catch (SQLException ex2) {
            ex2.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            dbConnectionUtil.closeConnectionDB(conn, cstmt, rs);
        }
        return set;
    }
}

package com.jhmk.cloudservice.warnService.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jhmk.cloudentity.earlywaring.entity.repository.service.RuyuanjiluRepService;
import com.jhmk.cloudentity.earlywaring.entity.rule.*;
import com.jhmk.cloudutil.config.UrlConstants;
import com.jhmk.cloudutil.config.UrlConstants;
import com.jhmk.cloudutil.config.UrlPropertiesConfig;
import com.jhmk.cloudutil.util.DbConnectionUtil;
import com.jhmk.cloudutil.util.MapUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author ziyu.zhou
 * @date 2018/7/30 14:18
 */

@Service
public class RuyuanjiluService {
    private static final Logger logger = LoggerFactory.getLogger(RuyuanjiluService.class);

    @Autowired
    DbConnectionUtil dbConnectionUtil;
    @Autowired
    UrlPropertiesConfig urlPropertiesConfig;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    RuyuanjiluRepService ruyuanjiluRepService;

    @Transactional
    public void saveAndFlush(Rule rule) {
        String patient_id = rule.getPatient_id();
        String visit_id = rule.getVisit_id();
        Ruyuanjilu ruyuanjilu = rule.getRuyuanjilu();
        List<Ruyuanjilu> allByPatientIdAndVisitId = ruyuanjiluRepService.findAllByPatientIdAndVisitId(patient_id, visit_id);
        if (allByPatientIdAndVisitId != null && allByPatientIdAndVisitId.size() > 0) {
            Ruyuanjilu ruyuanjilu1 = allByPatientIdAndVisitId.get(0);
            if (ruyuanjilu.equals(ruyuanjilu1)){
            }else {
                ruyuanjiluRepService.deleteByPatient_idAndVisit_id(patient_id, visit_id);
                ruyuanjiluRepService.save(ruyuanjilu);
            }
        }else {
            ruyuanjiluRepService.save(ruyuanjilu);
        }
    }


    /**
     * 广安门 获取视图的入院记录
     *
     * @param inpNo
     * @param visitId
     */
    public String getGamRuyuanjiluFromView(String inpNo, String visitId) {
        Map<String, String> param = new HashMap<>();
        Connection conn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        try {
            conn = dbConnectionUtil.openGamConnectionDBForRyjl();
            //HkT:<GB<： 在数据库中代表入院记录 caseid：=patient_id  admincount:入院次数
//            JuG0P!=a#术前小结
//            HkT:<GB<#入院记录
//            FdK|5%>]#其它单据
//            3vT:<GB<#出院记录
//            2!3L#病程
//            JuG0LVB[#术前讨论
//            JVJu<GB<#手术记录
//            JVJuM,RbJi#手术同意书
//            Ju:s2!3L#术后病程
//            cstmt = conn.prepareCall(" select * from CIOTEST.Emr_jiahe where caseid='" + inpNo + "' and admincount='" + visitId + "'and EMR_TYPE='HkT:<GB<'");
            cstmt = conn.prepareCall(" select * from CIOTEST.Emr_jiahe where caseid='" + inpNo + "' and admincount='" + visitId + "'and EMR_TYPE='入院记录'");
            rs = cstmt.executeQuery();// 执行
            while (rs.next()) {
                String sharetxtcase = rs.getString("SHARETXTCASE");
                param.put("key", "入院记录广安门医院");
                param.put("data", sharetxtcase);

                //亚飞分词接口
                String returnData = restTemplate.postForObject(urlPropertiesConfig.getParticipleurl() + UrlConstants.participle, JSONObject.toJSON(param), String.class);
                System.out.println(JSONObject.toJSONString(param));
                JSONObject resultObj = JSONObject.parseObject(returnData);
                String value = resultObj.getString("value");

//                JSONObject jsonObject = JSONObject.parseObject(value);
//                String marital_and_reproductive_history = jsonObject.getString("marital_and_reproductive_history");
//                ruyuanjiluBean.setMenstrual_and_obstetrical_histories(marital_and_reproductive_history);
                return value;
            }
        } catch (SQLException ex2) {
            ex2.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            dbConnectionUtil.closeConnectionDB(conn, cstmt, rs);
        }
        return null;
    }


    /**
     * 解析分词后数据
     *
     * @param value
     */
    public Ruyuanjilu analyzeGamRuyuanjiluFromView(String value) {
        Ruyuanjilu ruyuanjiluBean = null;
        try {

            ruyuanjiluBean = JSON.parseObject(value, new TypeReference<Ruyuanjilu>() {
            });
        } catch (Exception e) {
            logger.info("入院记录分词解析问题：{}，错误原因{}", value, e.getCause());
        }
        return ruyuanjiluBean;
    }

    public List<Binglizhenduan> analyzeGamBinglizhenduanFromView(String value) {
        JSONObject jsonObject = JSONObject.parseObject(value);
        String diagnosis_name = jsonObject.getString("diagnosis_name");
        List<Binglizhenduan> binglizhenduan = null;
        if (StringUtils.isNotBlank(diagnosis_name)) {
            try {

                binglizhenduan = JSON.parseObject(diagnosis_name, new TypeReference<List<Binglizhenduan>>() {
                });
            } catch (Exception e) {
                logger.info("入院记录诊断分词解析问题：{}，错误原因{}", value, e.getCause());
            }
        }
        return binglizhenduan;

    }

    public List<Map<String, String>> analyzeGamRuyuanjiluMapFromView(String value) {
        Ruyuanjilu ruyuanjiluBean = JSON.parseObject(value, new TypeReference<Ruyuanjilu>() {
        });
        List<Map<String, String>> mapList = new ArrayList<>();
        Map<String, String> map = null;

        Optional<String> chief_complaint = Optional.ofNullable(ruyuanjiluBean.getChief_complaint());
        if (chief_complaint.isPresent()) {
            map = new HashMap<>();
            map.put("key", "主诉");
            map.put("value", chief_complaint.get());
            mapList.add(map);
        }
        Optional<String> history_of_present_illness = Optional.ofNullable(ruyuanjiluBean.getHistory_of_present_illness());
        if (history_of_present_illness.isPresent()) {
            map = new HashMap<>();
            map.put("key", "现病史");
            map.put("value", history_of_present_illness.get());
            mapList.add(map);
        }
        Optional<String> history_of_past_illness = Optional.ofNullable(ruyuanjiluBean.getHistory_of_past_illness());
        if (history_of_past_illness.isPresent()) {
            map = new HashMap<>();
            map.put("key", "既往史");
            map.put("value", history_of_past_illness.get());
            mapList.add(map);
        }
        Optional<String> family_member_diseases = Optional.ofNullable(ruyuanjiluBean.getHistory_of_family_member_diseases());
        if (family_member_diseases.isPresent()) {
            map = new HashMap<>();
            map.put("key", "家族史");
            map.put("value", family_member_diseases.get());
            mapList.add(map);
        }
        Optional<String> social_history = Optional.ofNullable(ruyuanjiluBean.getSocial_history());
        if (social_history.isPresent()) {
            map = new HashMap<>();
            map.put("key", "个人史");
            map.put("value", social_history.get());
            mapList.add(map);
        }
        Optional<String> menstrual_and_obstetrical_histories = Optional.ofNullable(ruyuanjiluBean.getMenstrual_and_obstetrical_histories());
        if (menstrual_and_obstetrical_histories.isPresent()) {
            map = new HashMap<>();
            map.put("key", "婚育史");
            map.put("value", menstrual_and_obstetrical_histories.get());
            mapList.add(map);
        }
        Optional<String> auxiliary_examination = Optional.ofNullable(ruyuanjiluBean.getAuxiliary_examination());
        if (auxiliary_examination.isPresent()) {
            map = new HashMap<>();
            map.put("key", "辅助检查");
            map.put("value", auxiliary_examination.get());
            mapList.add(map);
        }
        Optional<String> physical_examination = Optional.ofNullable(ruyuanjiluBean.getPhysical_examination());
        if (physical_examination.isPresent()) {
            map = new HashMap<>();
            map.put("key", "专科检查");
            map.put("value", physical_examination.get());
            mapList.add(map);
        }
        return mapList;
    }


    /**
     * @param ruyaunjiluHtml 入院记录的htmk
     * @param form           厂商
     * @return 分词后的结果
     */
    public String getParticipleStringResult(String ruyaunjiluHtml, String form) {
        Object parse = JSONObject.parse(ruyaunjiluHtml);
        String returnData = restTemplate.postForObject(urlPropertiesConfig.getParticipleurl() + UrlConstants.segwordment, JSONObject.toJSON(parse), String.class);
        JSONObject object = JSONObject.parseObject(returnData);
        return JSONObject.toJSONString(object);
    }


    /**
     * 解析分词后的数据为 实体
     *
     * @param ruyaunjilurRsult
     * @param form
     * @return
     */
    public Ruyuanjilu analyzeParticipleResult2Ruyuanjilu(String ruyaunjilurRsult, String form) {
        Ruyuanjilu ruyuanjilu = new Ruyuanjilu();
        if (StringUtils.isNotBlank(ruyaunjilurRsult)) {
            JSONObject jsonObject = JSONObject.parseObject(ruyaunjilurRsult);
            JSONObject resultStatus = jsonObject.getJSONObject("result_status");
            if ("success".equals(resultStatus.getString("result_code"))) {
                JSONObject result = jsonObject.getJSONObject("result");
                JSONObject ruyuanjiluJObj = result.getJSONObject("ruyuanjilu");
                Optional.ofNullable(ruyuanjiluJObj.getJSONObject("chief_complaint")).ifPresent(s -> ruyuanjilu.setChief_complaint(s.getString("src")));
                Optional.ofNullable(ruyuanjiluJObj.getJSONObject("physical_examination")).ifPresent(s -> ruyuanjilu.setPhysical_examination(s.getString("src")));
                Optional.ofNullable(ruyuanjiluJObj.getJSONObject("social_history")).ifPresent(s -> ruyuanjilu.setSocial_history(s.getString("src")));
                Optional.ofNullable(ruyuanjiluJObj.getJSONObject("history_of_family_member_diseases")).ifPresent(s -> ruyuanjilu.setHistory_of_family_member_diseases(s.getString("src")));
                Optional.ofNullable(ruyuanjiluJObj.getJSONObject("history_of_present_illness")).ifPresent(s -> ruyuanjilu.setHistory_of_present_illness(s.getString("src")));
                Optional.ofNullable(ruyuanjiluJObj.getJSONObject("menstrual_and_obstetrical_histories")).ifPresent(s -> ruyuanjilu.setMenstrual_and_obstetrical_histories(s.getString("src")));
                Optional.ofNullable(ruyuanjiluJObj.getJSONObject("history_of_past_illness")).ifPresent(s -> ruyuanjilu.setHistory_of_past_illness(s.getString("src")));
            }
        }
        return ruyuanjilu;
    }


}

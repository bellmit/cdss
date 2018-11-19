package com.jhmk.cloudservice.warnService.service;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.jhmk.cloudentity.earlywaring.entity.rule.Jianchabaogao;
import com.jhmk.cloudentity.earlywaring.entity.rule.Jianyanbaogao;
import com.jhmk.cloudentity.earlywaring.entity.rule.Rule;
import com.jhmk.cloudservice.warnService.webservice.AnalysisXmlService;
import com.jhmk.cloudservice.warnService.webservice.CdrService;
import com.jhmk.cloudutil.config.BaseConstants;
import com.jhmk.cloudutil.util.DbConnectionUtil;
import com.jhmk.cloudutil.util.MapUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * @author ziyu.zhou
 * @date 2018/11/8 14:53
 * 检查报告service
 */

@Component
public class JianchabaogaoService {
    @Autowired
    CdrService cdrService;
    @Autowired
    AnalysisXmlService analysisXmlService;

    /**
     * 从视图获取检查报告（广安门）
     *
     * @param patientId
     * @param visitId
     * @return
     */
    public List<Jianchabaogao> getJianchabaogaoBypatientIdAndVisitId(String patientId, String visitId) {
        List<Jianchabaogao> jianchabaogaoList = new LinkedList<>();
        Connection conn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        try {
            conn = DbConnectionUtil.openConnectionDB();

//            cstmt = conn.prepareCall(" select * from v_cdss_exam_report");
            cstmt = conn.prepareCall("select * from v_cdss_exam_report WHERE patient_id=? and visit_id=?");
            cstmt.setString(1, patientId);
            cstmt.setString(2, visitId);
            rs = cstmt.executeQuery();// 执行
//            List<Company> companyList = new ArrayList<Company>();
            while (rs.next()) {

                Jianchabaogao jianchabaogao = new Jianchabaogao();
                Optional.ofNullable(rs.getString("exam_item_name")).ifPresent(s -> {
                    try {
                        jianchabaogao.setExam_item_name(new String(s.getBytes("iso-8859-1"), "GBK"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });
                Optional.ofNullable(rs.getString("exam_time")).ifPresent(s -> {
                    try {
                        jianchabaogao.setExam_time(new String(s.getBytes("iso-8859-1"), "GBK"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });
                Optional.ofNullable(rs.getString("exam_diag")).ifPresent(s -> {
                    try {
                        jianchabaogao.setExam_diag(new String(s.getBytes("iso-8859-1"), "GBK"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });
                Optional.ofNullable(rs.getString("exam_feature")).ifPresent(s -> {
                    try {
                        jianchabaogao.setExam_feature(new String(s.getBytes("iso-8859-1"), "GBK"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });
                jianchabaogaoList.add(jianchabaogao);
            }
        } catch (SQLException ex2) {
            ex2.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            DbConnectionUtil.closeConnectionDB(conn, cstmt, rs);
        }
        return jianchabaogaoList;
    }


    /**
     * （北医三院）
     * 从数据中心获取检查数据
     */
    public List<Jianchabaogao> getJianchabaogaoFromCdr(Rule rule) {
        //基础map 放相同数据
        Map<String, String> baseParams = new HashMap<>();
        baseParams.put("oid", BaseConstants.OID);
        baseParams.put("patient_id", rule.getPatient_id());
        baseParams.put("visit_id", rule.getVisit_id());
        Map<String, String> params = new HashMap<>();
        params.put("ws_code", BaseConstants.JHHDRWS004A);
        params.putAll(baseParams);
        //获取入出转xml
        String hospitalDate = cdrService.getDataByCDR(params, null);

        Map<String, String> hospitalDateMap = analysisXmlService.getHospitalDate(hospitalDate);

        //入院时间
        String admission_time = hospitalDateMap.get("admission_time");
        //出院时间
        String discharge_time = hospitalDateMap.get("discharge_time");

        /**
         * 根据入院出院时间  获取时间段内的检验检查报告
         */
        List<Map<String, String>> listConditions = new LinkedList<>();
        if (StringUtils.isNotBlank(admission_time)) {
            Map<String, String> conditionParams = new HashMap<>();
            conditionParams.put("elemName", "REPORT_TIME");
            conditionParams.put("value", admission_time);
//            conditionParams.put("operator", ">=");
            conditionParams.put("operator", "&gt;=");
            listConditions.add(conditionParams);
            rule.setAdmission_time(admission_time);
        }
        if (StringUtils.isNotBlank(discharge_time)) {
            Map<String, String> conditionParams = new HashMap<>();
            conditionParams.put("elemName", "REPORT_TIME");
            conditionParams.put("value", discharge_time);
            conditionParams.put("operator", "&lt;=");
            listConditions.add(conditionParams);
            rule.setDischarge_time(discharge_time);
        }
        /**
         *检查数据
         */
        params.put("ws_code", BaseConstants.JHHDRWS005);
        String jianchaXML = cdrService.getDataByCDR(params, listConditions);
        List<Map<String, String>> jianchabaogao = analysisXmlService.analysisXml2Jianchabaogao(jianchaXML);
        List<Jianchabaogao> jianchabaogaoList = new LinkedList<>();
        for (Map<String, String> map : jianchabaogao) {
            Jianchabaogao jiancha = MapUtil.map2Bean(map, Jianchabaogao.class);
            jianchabaogaoList.add(jiancha);
        }
        return jianchabaogaoList;
    }

    public static void main(String[] args) {
//        List<Jianchabaogao> jianyanbaogaoBypatientIdAndVisitId = getJianchabaogaoBypatientIdAndVisitId("115608460", "2");
//        System.out.println(JSONObject.toJSONString(jianyanbaogaoBypatientIdAndVisitId));
    }

}
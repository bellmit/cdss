package com.jhmk.cloudservice.warnService.service;

import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudentity.common.JiaheRuleBean;
import com.jhmk.cloudentity.earlywaring.entity.rule.Binganshouye;
import com.jhmk.cloudentity.earlywaring.entity.rule.Jianchabaogao;
import com.jhmk.cloudentity.earlywaring.entity.rule.Jianyanbaogao;
import com.jhmk.cloudentity.earlywaring.entity.rule.Rule;
import com.jhmk.cloudentity.earlywaring.webservice.JianyanbaogaoForAuxiliary;
import com.jhmk.cloudentity.earlywaring.webservice.OriginalJianyanbaogao;
import com.jhmk.cloudservice.cdssPageService.AnalyzeService;
import com.jhmk.cloudservice.webservice.AnalysisXmlService;
import com.jhmk.cloudservice.webservice.CdrService;
import com.jhmk.cloudservice.webservice.SocketClientUtil;
import com.jhmk.cloudutil.config.BaseConstants;
import com.jhmk.cloudutil.util.DateFormatUtil;
import com.jhmk.cloudutil.util.DbConnectionUtil;
import com.jhmk.cloudutil.util.SocketUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ziyu.zhou
 * @date 2018/11/8 14:53
 * 检查报告service
 */

@Component
public class JianyanbaogaoService {
    Logger logger = LoggerFactory.getLogger(JianyanbaogaoService.class);

    @Autowired
    AnalysisXmlService analysisXmlService;
    @Autowired
    CdrService cdrService;
    @Autowired
    AnalyzeService analyzeService;
    @Autowired
    SocketUtil socketUtil;
    @Autowired
    DbConnectionUtil dbConnectionUtil;



    public List<Jianyanbaogao> getJianyanbaogao(Rule rule, String hospitalName) {
        String patientId = rule.getPatient_id();
        String visitId = rule.getVisit_id();
        String inpNo = rule.getInp_no();
        List<Jianyanbaogao> jianyanbaogaoList = null;
        if ("bysy".equals(hospitalName)) {//北医三院
            jianyanbaogaoList = getJianyanbaogaoFromBysyCDR(patientId, visitId);
        } else if ("gam".equals(hospitalName)) {//广安门
            jianyanbaogaoList = getJianyanbaogaoBypatientIdAndVisitId(patientId, visitId);
        } else if ("xzey".equals(hospitalName)) {//徐州二院
            jianyanbaogaoList = getXZEYJianyanbaogaoBypatientIdAndVisitId(patientId, visitId);
        } else if ("gyey".equals(hospitalName)) {//广医二院
            getJianyanbaogaoFromGyeyCdr(inpNo, patientId);
        }
        return jianyanbaogaoList;
    }


/*    public List<Jianyanbaogao> getJianyanbaogao(Rule rule) {
        String patientId = rule.getPatient_id();
        String visitId = rule.getVisit_id();
        String inpNo = rule.getInp_no();
        String admission_time = rule.getAdmission_time();
        String returnData = getReturnData(ip, port, patientId, visitId, admission_time, BaseConstants.JIANYANBAOGAO);
        List<Jianyanbaogao> jianyanbaogaoList = analyzeService.analyzeJson2Jianyanbaogao(returnData);
        return jianyanbaogaoList;
    }*/

    //获取嘉和服务检验报告
    public List<Jianyanbaogao> getJianyanbaogao(JiaheRuleBean rule,String ip,int port) {
        Binganshouye binganshouye = rule.getBinganshouye();
        String patientId = binganshouye.getPatient_id();
        String visitId = binganshouye.getVisit_id();
        String admission_time = binganshouye.getAdmission_time();
        String returnData = socketUtil.getReturnData(ip, port, patientId, visitId, admission_time, BaseConstants.JIANYANBAOGAO);
        List<Jianyanbaogao> jianyanbaogaoList = analyzeService.analyzeJson2Jianyanbaogao(returnData);
        return jianyanbaogaoList;
    }


    /**
     * 广安门 视图
     *
     * @param patientId
     * @param visitId
     * @return
     */
    public List<Jianyanbaogao> getJianyanbaogaoBypatientIdAndVisitId(String patientId, String visitId) {
        /*SocketClientUtil util = new SocketClientUtil(ip, port);
        util.sendData(patientId, visitId, "jianyanbaogao");
        String jianyanbaogao = util.getData();
        List<Jianyanbaogao> jianyanbaogaoList = analyzeService.analyzeJson2Jianyanbaogao(jianyanbaogao);*/
        List<Jianyanbaogao> jianyanbaogaoList = new LinkedList<>();
        Connection conn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        try {
            conn = dbConnectionUtil.openGamConnectionDBForBaogao();

//            cstmt = conn.prepareCall(" select * from v_cdss_exam_report");
            cstmt = conn.prepareCall("select * from v_cdss_lab_report WHERE patient_id=? and visit_id=?");
            cstmt.setString(1, patientId);
            cstmt.setString(2, visitId);
            rs = cstmt.executeQuery();// 执行
//            List<Company> companyList = new ArrayList<Company>();
            while (rs.next()) {

                Jianyanbaogao jianyanbaogao = new Jianyanbaogao();
                Optional.ofNullable(rs.getString("lab_item_name")).ifPresent(s -> {
                    try {
                        jianyanbaogao.setLab_item_name(new String(s.getBytes("iso-8859-1"), "GBK"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });
                Optional.ofNullable(rs.getString("report_time")).ifPresent(s -> {
                    try {
                        jianyanbaogao.setReport_time(new String(s.getBytes("iso-8859-1"), "GBK"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });
                Optional.ofNullable(rs.getString("lab_qual_result")).ifPresent(s -> {
                    try {
                        jianyanbaogao.setLab_qual_result(new String(s.getBytes("iso-8859-1"), "GBK"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });
                Optional.ofNullable(rs.getString("result_status_code")).ifPresent(s -> {
                    try {
                        jianyanbaogao.setResult_status_code(new String(s.getBytes("iso-8859-1"), "GBK"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });
                Optional.ofNullable(rs.getString("reference_range")).ifPresent(s -> {
                    try {
                        jianyanbaogao.setReference_range(new String(s.getBytes("iso-8859-1"), "GBK"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });
                Optional.ofNullable(rs.getString("specimen")).ifPresent(s -> {
                    try {
                        jianyanbaogao.setSpecimen(new String(s.getBytes("iso-8859-1"), "GBK"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });
                Optional.ofNullable(rs.getString("lab_result_value")).ifPresent(s -> {
                    try {
                        jianyanbaogao.setLab_result_value(new String(s.getBytes("iso-8859-1"), "GBK"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });
                Optional.ofNullable(rs.getString("lab_result_value_unit")).ifPresent(s -> {
                    try {
                        jianyanbaogao.setLab_result_value_unit(new String(s.getBytes("iso-8859-1"), "GBK"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });
                Optional.ofNullable(rs.getString("lab_sub_item_name")).ifPresent(s -> {
                    try {
                        jianyanbaogao.setLab_sub_item_name(new String(s.getBytes("iso-8859-1"), "GBK"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                });
                jianyanbaogaoList.add(jianyanbaogao);
            }
        } catch (SQLException ex2) {
            ex2.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            dbConnectionUtil.closeConnectionDB(conn, cstmt, rs);
        }

        //检验报告获取最近时间的
        Map<String, Optional<Jianyanbaogao>> collect = jianyanbaogaoList.stream().collect(Collectors.groupingBy(Jianyanbaogao::getIwantData, Collectors.maxBy((o1, o2) -> DateFormatUtil.parseDateBySdf(o1.getReport_time(), DateFormatUtil.DATETIME_PATTERN_SS).compareTo(DateFormatUtil.parseDateBySdf(o2.getReport_time(), DateFormatUtil.DATETIME_PATTERN_SS)))));
        List<Jianyanbaogao> resultList = new ArrayList<>();
        for (Map.Entry<String, Optional<Jianyanbaogao>> entry : collect.entrySet()) {
            Jianyanbaogao student = entry.getValue().get();
            resultList.add(student);
        }
        return resultList;
    }


    public List<Jianyanbaogao> getDistinctJianyanbaogao(List<Jianyanbaogao> jianyanbaogaoList) {
        //检验报告获取最近时间的
        Map<String, Optional<Jianyanbaogao>> collect = jianyanbaogaoList.stream().collect(Collectors.groupingBy(Jianyanbaogao::getIwantData, Collectors.maxBy((o1, o2) -> DateFormatUtil.parseDateBySdf(o1.getReport_time(), DateFormatUtil.DATETIME_PATTERN_SS).compareTo(DateFormatUtil.parseDateBySdf(o2.getReport_time(), DateFormatUtil.DATETIME_PATTERN_SS)))));
        List<Jianyanbaogao> resultList = new ArrayList<>();
        for (Map.Entry<String, Optional<Jianyanbaogao>> entry : collect.entrySet()) {
            Jianyanbaogao student = entry.getValue().get();
            resultList.add(student);
        }
        return resultList;
    }

    /**
     * 徐州二院 检验报告
     *
     * @param patientId
     * @param visitId
     * @return
     */
    public List<Jianyanbaogao> getXZEYJianyanbaogaoBypatientIdAndVisitId(String patientId, String visitId) {
        List<OriginalJianyanbaogao> originalJianyanbaogaoList = new LinkedList<>();
        Connection conn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        try {
            conn = dbConnectionUtil.openConnectionDBForProduct();

            cstmt = conn.prepareCall("select * from jhcdr_lab_report WHERE patient_id= ? and visit_id=?");
            cstmt.setString(1, patientId);
            cstmt.setString(2, visitId);
            rs = cstmt.executeQuery();// 执行
            //查询检验报告主表的数据
            while (rs.next()) {
                OriginalJianyanbaogao originalJianyanbaogao = new OriginalJianyanbaogao();
                //住院号
                Optional.ofNullable(rs.getString("report_date_time")).ifPresent(s -> {
                    originalJianyanbaogao.setReport_time(s);
                });
                //样本名称
                Optional.ofNullable(rs.getString("specimen")).ifPresent(s -> {
                    originalJianyanbaogao.setSpecimen(s);
                });
                //医嘱名称
                Optional.ofNullable(rs.getString("order_text")).ifPresent(s -> {
                    originalJianyanbaogao.setLab_item_name(s);
                });
                //申请号（预检验报告细项关联的主表字段）
                Optional.ofNullable(rs.getString("lab_apply_no")).ifPresent(s -> {
                    originalJianyanbaogao.setReport_no(s);
                });
                //查询检验报告明细表
                cstmt = conn.prepareCall("select * from jhcdr_lab_report_items WHERE lab_apply_no=? ");
                cstmt.setString(1, originalJianyanbaogao.getReport_no());
                rs = cstmt.executeQuery();// 执行

                List<JianyanbaogaoForAuxiliary> jianyanbaogaoForAuxiliaryList = new ArrayList<>();
                while (rs.next()) {
                    JianyanbaogaoForAuxiliary jianyanbaogaoForAuxiliary = new JianyanbaogaoForAuxiliary();
                    //检验项目名称
                    Optional.ofNullable(rs.getString("lab_item_sname")).ifPresent(s -> {
                        jianyanbaogaoForAuxiliary.setName(s);
                    });
                    //定性结果
                    /*Optional.ofNullable(rs.getString("specimen")).ifPresent(s -> {
                        jianyanbaogaoForAuxiliary.setSpecimen(s);
                    });*/
                    //检验定量结果值
                    Optional.ofNullable(rs.getString("result")).ifPresent(s -> {
                        jianyanbaogaoForAuxiliary.setLab_result(s);
                    });
                    //检验定量结果单位
                    Optional.ofNullable(rs.getString("units")).ifPresent(s -> {
                        jianyanbaogaoForAuxiliary.setUnit(s);
                    });
                    //检验定量结果变化 正常：N    偏高：H   偏低：L
                    Optional.ofNullable(rs.getString("status")).ifPresent(s -> {
                        jianyanbaogaoForAuxiliary.setResult_status_code(s);
                    });
                    //参考区间
                    Optional.ofNullable(rs.getString("result_range")).ifPresent(s -> {
                        jianyanbaogaoForAuxiliary.setReference_range(s);
                    });
                    //和检验主表关系字段
                    Optional.ofNullable(rs.getString("lab_apply_no")).ifPresent(s -> {
                        jianyanbaogaoForAuxiliary.setReport_no(s);
                    });
                    jianyanbaogaoForAuxiliaryList.add(jianyanbaogaoForAuxiliary);
                }
                originalJianyanbaogao.setLabTestItems(jianyanbaogaoForAuxiliaryList);
                originalJianyanbaogaoList.add(originalJianyanbaogao);
            }
        } catch (SQLException ex2) {
            ex2.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            dbConnectionUtil.closeConnectionDB(conn, cstmt, rs);
        }

        //检验报告获取最近时间的
        List<Jianyanbaogao> jianyanbaogaoList = analysisXmlService.analysisOriginalJianyanbaogao2Jianyanbaogao(originalJianyanbaogaoList);
        return jianyanbaogaoList;
    }

    /**
     * 3院数据中心
     */
    public List<Jianyanbaogao> getJianyanbaogaoFromBysyCDR(String patientId, String visitId) {
        Map<String, String> baseParams = new HashMap<>();
        baseParams.put("oid", BaseConstants.OID);
        baseParams.put("patient_id", patientId);
        baseParams.put("visit_id", visitId);
        Map<String, String> params = new HashMap<>();
        params.put("ws_code", BaseConstants.JHHDRWS004A);
        params.putAll(baseParams);
        //获取入出转xml
        String hospitalDate = cdrService.getDataByCDR(params, null);
        //获取入院时间 出院时间
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
        }
        if (StringUtils.isNotBlank(discharge_time)) {
            Map<String, String> conditionParams = new HashMap<>();
            conditionParams.put("elemName", "REPORT_TIME");
            conditionParams.put("value", discharge_time);
            conditionParams.put("operator", "&lt;=");
            listConditions.add(conditionParams);
        }
        //检验数据
//        params.put("ws_code", BaseConstants.JHHDRWS004A);
//        params.put("ws_code", "JHHDRWS006B");
        //检验数据（主表）
        params.put("ws_code", BaseConstants.JHHDRWS006A);
        String jianyanzhubiao = cdrService.getDataByCDR(params, listConditions);
        if (StringUtils.isEmpty(jianyanzhubiao)) {
            return null;
        }
        //检验数据明细
        params.put("ws_code", BaseConstants.JHHDRWS006B);
        String jybgzbMX = cdrService.getDataByCDR(params, listConditions);
        if (StringUtils.isEmpty(jianyanzhubiao)) {
            return null;
        }
        //获取检验报告原始数据
        List<JianyanbaogaoForAuxiliary> jianyanbaogaoForAuxiliaries = analysisXmlService.analysisXml2JianyanbaogaoMX(jybgzbMX);
        List<OriginalJianyanbaogao> originalJianyanbaogaos = analysisXmlService.analysisXml2Jianyanbaogao(jianyanzhubiao, jianyanbaogaoForAuxiliaries);
        List<Jianyanbaogao> jianyanbaogaos = analysisXmlService.analysisOriginalJianyanbaogao2Jianyanbaogao(originalJianyanbaogaos);
        return jianyanbaogaos;

    }

    /**
     * 广安门数据中心  将错就错
     *
     * @param inpNo     作为patientId
     * @param patientId 作为visitId
     * @return
     */
    public List<Jianyanbaogao> getJianyanbaogaoFromGyeyCdr(String inpNo, String patientId) {
        //基础map 放相同数据
        Map<String, String> params = new HashMap<>();
        params.put("oid", BaseConstants.OID);
        /**
         *  广医二院  检查报告
         *  patient_id 是 inp_no
         *  visit_id 是 patient_id
         */
        params.put("patient_id", inpNo);
        params.put("visit_id", patientId);
        params.put("ws_code", BaseConstants.JHHDRWS006A);
        String jianyanzhubiao = cdrService.getDataByCDR(params, null);
        //检验数据明细
        params.put("ws_code", BaseConstants.JHHDRWS006B);
        String jybgzbMX = cdrService.getDataByCDR(params, null);
        //获取检验报告原始数据
        if (StringUtils.isNotBlank(jybgzbMX)) {
            List<JianyanbaogaoForAuxiliary> jianyanbaogaoForAuxiliaries = analysisXmlService.analysisXml2JianyanbaogaoMX(jybgzbMX);
            List<OriginalJianyanbaogao> originalJianyanbaogaos = analysisXmlService.analysisXml2Jianyanbaogao(jianyanzhubiao, jianyanbaogaoForAuxiliaries);
            List<Jianyanbaogao> jianyanbaogaos = analysisXmlService.analysisOriginalJianyanbaogao2Jianyanbaogao(originalJianyanbaogaos);
            return jianyanbaogaos;
        } else {
            return null;
        }
    }

    /**
     * 徐州二院
     *
     * @param patientId
     * @param visitId
     * @return
     */
    public List<Jianyanbaogao> getXzeyJianyanbaogaoBypatientIdAndVisitId(String patientId, String visitId) {
        List<Jianyanbaogao> jianyanbaogaoList = new LinkedList<>();
        Connection conn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        try {
            conn = dbConnectionUtil.openGamConnectionDBForBaogao();

//            cstmt = conn.prepareCall(" select * from v_cdss_exam_report");
            cstmt = conn.prepareCall("select * from jhcdr_lab_report WHERE patient_id=? and visit_id=?");
            cstmt.setString(1, patientId);
            cstmt.setString(2, visitId);
            rs = cstmt.executeQuery();// 执行
//            List<Company> companyList = new ArrayList<Company>();
            while (rs.next()) {
                Jianyanbaogao jianyanbaogao = new Jianyanbaogao();
                Optional.ofNullable(rs.getString("tnp_no")).ifPresent(s -> {
                    jianyanbaogao.setReport_no(s);
                });
                Optional.ofNullable(rs.getString("specimen")).ifPresent(s -> {
                    jianyanbaogao.setSpecimen(s);
                });
                Optional.ofNullable(rs.getString("specimen")).ifPresent(s -> {
                    jianyanbaogao.setSpecimen(s);
                });
                jianyanbaogaoList.add(jianyanbaogao);
            }
        } catch (SQLException ex2) {
            ex2.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            dbConnectionUtil.closeConnectionDB(conn, cstmt, rs);
        }
        //检验报告获取最近时间的
        Map<String, Optional<Jianyanbaogao>> collect = jianyanbaogaoList.stream().collect(Collectors.groupingBy(Jianyanbaogao::getIwantData, Collectors.maxBy((o1, o2) -> DateFormatUtil.parseDateBySdf(o1.getReport_time(), DateFormatUtil.DATETIME_PATTERN_SS).compareTo(DateFormatUtil.parseDateBySdf(o2.getReport_time(), DateFormatUtil.DATETIME_PATTERN_SS)))));
        List<Jianyanbaogao> resultList = new ArrayList<>();
        for (Map.Entry<String, Optional<Jianyanbaogao>> entry : collect.entrySet()) {
            Jianyanbaogao student = entry.getValue().get();
            resultList.add(student);
        }
        return resultList;
    }

    public static void main(String[] args) {
        JianyanbaogaoService jianyanbaogaoService = new JianyanbaogaoService();
        List<Jianyanbaogao> jianyanbaogaoBypatientIdAndVisitId = jianyanbaogaoService.getJianyanbaogaoBypatientIdAndVisitId("115608460", "2");
        System.out.println(JSONObject.toJSONString(jianyanbaogaoBypatientIdAndVisitId));
    }
}

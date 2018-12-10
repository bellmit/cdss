package com.jhmk.cloudservice.warnService.service;

import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudentity.earlywaring.entity.rule.Jianchabaogao;
import com.jhmk.cloudentity.earlywaring.entity.rule.Jianyanbaogao;
import com.jhmk.cloudutil.util.DateFormatUtil;
import com.jhmk.cloudutil.util.DbConnectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
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
    @Autowired
    DbConnectionUtil dbConnectionUtil;
    public List<Jianyanbaogao> getJianyanbaogaoBypatientIdAndVisitId(String patientId, String visitId) {
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

    public static void main(String[] args) {
        JianyanbaogaoService jianyanbaogaoService = new JianyanbaogaoService();
        List<Jianyanbaogao> jianyanbaogaoBypatientIdAndVisitId = jianyanbaogaoService.getJianyanbaogaoBypatientIdAndVisitId("115608460", "2");
        System.out.println(JSONObject.toJSONString(jianyanbaogaoBypatientIdAndVisitId));
    }
}

package com.jhmk.cloudservice.warnService.service;


import com.jhmk.cloudentity.earlywaring.entity.repository.service.BinganshouyeRepService;
import com.jhmk.cloudentity.earlywaring.entity.rule.Binganshouye;
import com.jhmk.cloudentity.earlywaring.entity.rule.Rule;
import com.jhmk.cloudutil.util.DbConnectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    BinganshouyeRepService binganshouyeRepService;

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

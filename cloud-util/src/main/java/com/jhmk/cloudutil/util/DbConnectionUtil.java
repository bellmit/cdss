package com.jhmk.cloudutil.util;

import ch.qos.logback.core.db.dialect.DBUtil;
import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudutil.config.BaseConstants;
import com.jhmk.cloudutil.config.OracleConstants;

import java.sql.*;
import java.util.LinkedList;
import java.util.Optional;

/**
 * mysql  视图链接工具类
 *
 * @author ziyu.zhou
 * @date 2018/11/6 11:32
 */

public class DbConnectionUtil {

//    private final static String driver = "oracle.jdbc.driver.OracleDriver";
//    private final static String url = "jdbc:oracle:thin:@172.16.254.102:1521/ORCL";
//    private final static String account = "lcjczc";
//    private final static String password = "gam_cdss";


    public static Connection openConnectionDB(String driver, String url, String account, String password) {
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, account, password);
        } catch (SQLException ex2) {
            ex2.printStackTrace();
        } catch (Exception ex2) {
            ex2.printStackTrace();
        } finally {
            return conn;
        }
    }

    /**
     * 广安门检验检查报告视图
     * @return
     */
    public static Connection openGamConnectionDBForBaogao() {
        Connection conn = null;
        try {
            Class.forName(OracleConstants.DRIVER);
            conn = DriverManager.getConnection(OracleConstants.GAM_BAOGAOURL, OracleConstants.GAM_BAOGAOUSERNAME, OracleConstants.GAM_BAOGAOPASSWORD);
        } catch (SQLException ex2) {
            ex2.printStackTrace();
        } catch (Exception ex2) {
            ex2.printStackTrace();
        } finally {
            return conn;
        }
    }
    /**
     * 广安门入院记录视图
     * @return
     */
    public static Connection openGamConnectionDBForRyjl() {
        Connection conn = null;
        try {
            Class.forName(OracleConstants.DRIVER);
            conn = DriverManager.getConnection(OracleConstants.GAM_RYJLURL, OracleConstants.GAM_RYJLUSERNAME, OracleConstants.GAM_RYJLPASSWORD);
        } catch (SQLException ex2) {
            ex2.printStackTrace();
        } catch (Exception ex2) {
            ex2.printStackTrace();
        } finally {
            return conn;
        }
    }


    // 关闭连接
    public static void closeConnectionDB(Connection conn, CallableStatement stat, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                rs = null;
            }
        }
        if (stat != null) {
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                stat = null;


            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                conn = null;
            }
        }
    }

    public static void main(String[] args) {
//        List<Jianchabaogao> jianchabaogaoList = new LinkedList<>();


        Connection conn = null;
        CallableStatement cstmt = null;
        ResultSet rs = null;
        try {
            conn = DbConnectionUtil.openGamConnectionDBForRyjl();
            cstmt = conn.prepareCall(" select * from CIOTEST.Emr_jiahe where caseid='082839' and admincount='12'and FRAMEWORKCODE='1'");
//            cstmt = conn.prepareCall(" select * from CIOTEST.Emr_jiahe where caseid='000205' and admincount='3'and FRAMEWORKCODE='1'");
//            cstmt = conn.prepareCall("SELECT * FROM    select * from v_cdss_exam_report WHERE patient_id=? and visit_id=?");
//            cstmt.setString(1, "723993");
//            cstmt.setString(2, "1");
            rs = cstmt.executeQuery();// 执行
//            List<Company> companyList = new ArrayList<Company>();
            while (rs.next()) {
                String sharetxtcase = rs.getString("SHARETXTCASE");
                int indexOf = sharetxtcase.indexOf("入");
                String substring = sharetxtcase.substring(indexOf);
//                String s = sharetxtcase.replaceAll("\\r\\n", "\\\\r\\\\n");
                String s = substring.replaceAll("\\\\","\\\\\\\\");
                System.out.println(s);
                System.out.println(JSONObject.toJSONString(sharetxtcase));


            }
        } catch (SQLException ex2) {
            ex2.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            DbConnectionUtil.closeConnectionDB(conn, cstmt, rs);
        }
//        System.out.println(jianchabaogaoList);
    }

}

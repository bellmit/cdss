package com.jhmk.cloudutil.util;

import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudutil.config.BaseConstants;

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

    private final static String driver = "oracle.jdbc.driver.OracleDriver";
    private final static String url = "jdbc:oracle:thin:@172.16.254.6:1521/ORCL";
    private final static String account = "emr_analysis";
    private final static String password = "gam_jiahe";


    public static Connection openConnectionDB() {
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
            conn = DbConnectionUtil.openConnectionDB();

            cstmt = conn.prepareCall(" select * from CIOTEST.Emr_jiahe where caseid='082839' and admincount='12'and FRAMEWORKCODE='1'");
//            cstmt = conn.prepareCall("SELECT * FROM    select * from v_cdss_exam_report WHERE patient_id=? and visit_id=?");
//            cstmt.setString(1, "723993");
//            cstmt.setString(2, "1");
            rs = cstmt.executeQuery();// 执行
//            List<Company> companyList = new ArrayList<Company>();
            while (rs.next()) {
                String sharetxtcase = rs.getString("SHARETXTCASE");
                System.out.println(sharetxtcase);
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

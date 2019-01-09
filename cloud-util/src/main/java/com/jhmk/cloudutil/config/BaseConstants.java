package com.jhmk.cloudutil.config;

import org.springframework.stereotype.Component;


@Component
public class BaseConstants {

    public static final String USER_STATUS = "0";//用户状态 0-正常 1-异常
    public static final String CURRENT_ROLE_ID = "currentRoleId";
    public static final String CURRENT_ROLE_RANGE = "range";
    public static final String USER_ID = "userId";
    public static final String DEPT_ID = "deptId";
    public static final String DEPT_NAME = "deptName";
    public static final String TOKEN = "token";
    public static final String SYMPOL = "[]";


    //参数状态：1-启用 0- 未启用
    public static final String PARAMTER_STATUS_1 = "1";//参数启用
    public static final String PARAMTER_STATUS_0 = "0";//参数未启用


    public static final String ALARM_CODE1 = "住院";//住院预警
    public static final String ALARM_CODE2 = "门诊";//住院预警


    public static final String OK = "200";// 状态码 200 成功
    public static final String SUCCESS = "success";
    public static final String FALSE = "false";




    /**
     * OID账号，用于数据中心权限通行证
     */
    public static final String OID = "1.2.156.112636.1.2.46";
    /**
     * 数据中心webservice 服务接口
     */
    public static final String JHHDRWS001 = "JHHDRWS001";//患者信息
    public static final String JHHDRWS004A = "JHHDRWS004A";//入出转
    public static final String JHHDRWS005 = "JHHDRWS005";//检查数据
    public static final String JHHDRWS006A = "JHHDRWS006A";//检验数据（主表）
    public static final String JHHDRWS006B = "JHHDRWS006B";//检验结果明细
    public static final String JHHDRWS007A = "JHHDRWS007A";//门诊就诊
    public static final String JHHDRWS007C = "JHHDRWS007C";//门诊诊断
    public static final String JHHDRWS012A = "JHHDRWS012A";//住院医嘱
    public static final String JHHDRWS017 = "JHHDRWS017";//体征测量
    public static final String JHHDRWS020C = "JHHDRWS020C";//首页诊断
    public static final String JHHDRWS020A = "JHHDRWS020A";//病案首页
    public static final String JHHDRWS021D = "JHHDRWS021D";//病历诊断
    public static final String JHHDRWS021A = "JHHDRWS021A";//病历文书
    public static final String JHHDRWS021B = "JHHDRWS021B";//病历章节（反结构化）
    public static final String JHHDRWS029 = "JHHDRWS029";//规整后数据，包括主诉、现病史、既往史、过敏史等数据
    public static final String JHHDRWS031 = "JHHDRWS031";//重症ICU生命体征

    final static public String LTE = "小于等于";
    final static public String GTE = "大于等于";
    final static public String NEQ = "不等于";
    final static public String NCT = "不包含";
    final static public String LT = "小于";
    final static public String GT = "大于";
    final static public String EQ = "等于";
    final static public String CT = "包含";



}

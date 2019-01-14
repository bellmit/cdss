package com.jhmk.cloudutil.config;

/**
 * 获取鉴别诊断疾病名
 *
 * @author ziyu.zhou
 * @date 2018/12/19 14:07
 */

public class UrlConstants {
    //    获取鉴别诊断疾病名
    public static final String getDifferentialDiagnosisName = "/med/disease/diosProperty.json";

    //getVariableList获取变量列表
    public static final String getVariableList = "/med/cindecision/getVariableList.json";
    public static final String addrule = "/med/cindecision/addrule.json";
    public static final String findallnotsubmitrule = "/med/cindecision/findallnotsubmitrule.json";
    public static final String findallsubmitrule = "/med/cindecision/findallsubmitrule.json";
    public static final String findallrule = "/med/cindecision/findallrule.json";
    public static final String findrulebycondition = "/med/cindecision/findrulebycondition.json";
    public static final String submitrule = "/med/cindecision/submitrule.json";
    public static final String matchrule = "/med/cindecision/matchrule.json";
    public static final String updaterule = "/med/cindecision/updaterule.json";
    public static final String getruleforid = "/med/cindecision/getruleforid.json";//查询单条规则

    //改变预警等级
    public static final String changewarninglevel = "/med/cindecision/changewarninglevel.json";
    //审核规则
    public static final String examinerule = "/med/cindecision/examinerule.json";
    //是否运行规则
    public static final String isrunruler = "/med/cindecision/isrunruler.json";
    //专科标识
    public static final String changeIdentification = "/med/cindecision/changeIdentification.json";
    public static final String deleterule = "/med/cindecision/deleterule.json";

    public static final String statisticsrulercount = "/med/cindecision/statisticsrulercount.json";// 统计规则数目
    public static final String groupbyclassification = "/med/cindecision/groupbyclassification.json"; // 分组统计规则分类得数据
    public static final String groupbywarninglevel = "/med/cindecision/groupbywarninglevel.json"; // 分组统计预警等级数据
    public static final String groupbyidentification = "/med/cindecision/groupbyidentification.json";//  分组统计专科标识数据
    public static final String groupbycreatetime = "/med/cindecision/groupbycreatetime.json";// 传入 startDate  endDate 两个参数  按时间分组统计规则
    public static final String getWikiInfoByExpress = "/med/cdss/getWikiInfoByExpress.json";
    public static final String Emrdiseaseinfo = "med/machinelearn/Emrdiseaseinfo.json";//推荐诊断


    //医问道接口
    //3.高级检索值域变量（post）
    public static final String getfieldbyid = "/med/kninterface/getfieldbyid.json";
    //高级检索单位变量（post）
    public static final String getunitsbyid = "/med/kninterface/getunitsbyid.json";
    //5.高级检索根据关键字检索 拼音or汉字（post）
    public static final String searchbyvariablename = "/med/searchbyvariablename.json";
    //接口：获取字段的特殊类型
    public static final String getSpecialTypeByField = "/med/getSpecialTypeByField.json";
    // 获取最新列表
    public static final String getVariableListNew = "/med/getVariableListNew.json";
    //获取诊疗提醒
    public static final String getTipList = "/med/cdss/getTipList.json";
    public static final String getLocalJybg = "/test/rule/labWarnById";


    //获取疾病同义词
    public static final String getSamilarWord = "/med/cdss/getSamilarWord.json";

    //获取疾病子节点
    public static final String getDiseaseChildrenList = "/med/cdss/getDiseaseChildrenList.json";
    //获取疾病父节点
    public static final String getParentList = "/med/cdss/getParentList.json";

    public static final String participle = "/extractchapter/ruyuanjilu.json";
    public static final String updatechildelement = "/med/cindecision/updatechildelement.json";//  更新childElement 接口  需要提供两个参数  _id  (规则ID)  childElement 具体信息
    public static final String findallstandardrules = "/med/cindecision/findallstandardrules.json";//  查询所有得标准规则信息    需要提供page pageSize两个参数
    public static final String findallchildrules = "/med/cindecision/findallchildrules.json";//  查询规则下所有得子类规则信息


    //知识库接口

    //  获取一个词的同义词列表
    public static final String sameWord = "/med/cdss/sameWord.json";
    //获取一个词的标准名称
    public static final String standardFromAlias = "/med/cdss/standardFromAlias.json";
    public static final String getAllChildDisease = "/med/cdss/getAllChildDisease.json";



}

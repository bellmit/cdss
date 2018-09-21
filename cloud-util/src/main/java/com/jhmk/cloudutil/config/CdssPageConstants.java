package com.jhmk.cloudutil.config;

/**
 * @author ziyu.zhou
 * @date 2018/8/23 9:59
 */

/**
 * cdss小界面配置
 */
public class CdssPageConstants {

    private static final String HEADERURL = "http://192.168.132.7:8010/med";

    /**
     * 获取药品变化趋势
     */
    public static final String GETPLANSTAT = HEADERURL + "/disease/getPlanStat.json";

    /**
     * 诊疗计划
     */
//    public static final String TREATPLAN = "http://192.168.8.23:8098/med/advanced/query/treatPlan.json";
    public static final String TREATPLAN = "http://192.168.132.13:8800/med/advanced/query/treatPlan.json";


}

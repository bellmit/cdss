package com.jhmk.cloudentity.page.bean;

/**
 * @author ziyu.zhou
 * @date 2018/8/23 18:47
 */

import java.util.Map;

/**
 * 医嘱用药变化趋势实体类
 */
public class DrugTendency {
    private String name;
    //时间  数量
    private Map<String, Integer> drugMap;
    //药品成分名 时间 数量
    private Map<String, Map<String,Integer>> drugCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Integer> getDrugMap() {
        return drugMap;
    }

    public void setDrugMap(Map<String, Integer> drugMap) {
        this.drugMap = drugMap;
    }

    public Map<String, Map<String, Integer>> getDrugCount() {
        return drugCount;
    }

    public void setDrugCount(Map<String, Map<String, Integer>> drugCount) {
        this.drugCount = drugCount;
    }
}

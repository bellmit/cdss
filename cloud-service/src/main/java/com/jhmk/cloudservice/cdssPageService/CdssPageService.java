package com.jhmk.cloudservice.cdssPageService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudentity.cdss.page.DrugTendency;
import com.jhmk.cloudutil.config.CdssPageConstants;
import com.jhmk.cloudutil.util.MapUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @author ziyu.zhou
 * @date 2018/8/23 10:12
 */
@Service
public class CdssPageService {
    Logger logger = LoggerFactory.getLogger(CdssPageService.class);
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CdssPageService cdssPageService;
    @Autowired
    CommonService commonService;

    /**
     * 获取变化趋势
     *
     * @param map
     * @return
     */
    public JSONObject getPlanStat(String map) {
        JSONObject jsonObject = null;
        Object s = JSONObject.parseObject(map);
        try {
            String result = restTemplate.postForObject(CdssPageConstants.GETPLANSTAT, s, String.class);
            String tempStr1 = result.replaceAll("\\\\", "");
            String tempStr2 = tempStr1.substring(1, tempStr1.length() - 1);
            jsonObject = JSONObject.parseObject(tempStr2);
        } catch (Exception e) {
            logger.info("获取医嘱用药变化趋势失败{},返回结果为{}", e.getMessage());
        } finally {
        }
        return jsonObject;
    }

    /**
     * 过同义词表
     *
     * @param strs
     * @return
     */
    public Map<String, Map<String, Integer>> transform2StandardName1(String strs) {
        JSONObject jsonObject = JSONObject.parseObject(strs);
        Set<String> strings = jsonObject.keySet();
        List<String> drudList = commonService.getDrudList();
        Map<String, Map<String, Integer>> resultMap = new HashMap<>();
        //分时间 遍历
        for (String keyName : strings) {
            JSONObject o = (JSONObject) jsonObject.get(keyName);
            //药品名集合
            Set<String> nameList = o.keySet();
            Map<String, Integer> grudMap = new HashMap<>();
            //分药品名遍历
            for (String grudName : nameList) {
                Integer count = o.getInteger(grudName);
                //药品同义词表
                String drugStandardName = commonService.getDrugStandardName(grudName, drudList);
                //保存到map中
                if (grudMap.containsKey(drugStandardName)) {
                    grudMap.put(drugStandardName, grudMap.get(drugStandardName) + count);
                } else {
                    grudMap.put(drugStandardName, count);
                }
            }
            resultMap.put(keyName, grudMap);
        }
        return resultMap;
    }

    /**
     * 只要括号里边的 标准名 没有括号 则直接用
     *
     * @param strs
     * @return
     */
    public Map<String, Map<String, Integer>> transform2StandardName(String strs) {
        JSONObject jsonObject = JSONObject.parseObject(strs);
        Set<String> strings = jsonObject.keySet();
        Map<String, Map<String, Integer>> resultMap = new HashMap<>();
        //分时间 遍历
        for (String keyName : strings) {
            JSONObject o = (JSONObject) jsonObject.get(keyName);
            //药品名集合
            Set<String> nameList = o.keySet();
            Map<String, Integer> grudMap = new HashMap<>();
            //分药品名遍历
            for (String grudName : nameList) {
                Integer count = o.getInteger(grudName);
                //药品同义词表
                String drugStandardName = getStandardName(grudName);
                //保存到map中
                if (grudMap.containsKey(drugStandardName)) {
                    grudMap.put(drugStandardName, grudMap.get(drugStandardName) + count);
                } else {
                    grudMap.put(drugStandardName, count);
                }
            }
            resultMap.put(keyName, grudMap);
        }
        return resultMap;
    }

    public String getStandardName(String name) {
        String sName = "";
        if (name.contains("(") && name.contains(")")) {
            sName = name.substring(name.indexOf("(") + 1, name.lastIndexOf(")"));
        } else {
            sName = name;
        }
        return sName;
    }

    /**
     * 根据一级药品名获取下一级药品名+子集药物名
     *
     * @param name
     * @return
     */
    public Map<String, Set<String>> getNextLevelNamesAndValue(String name) {
        List<DrugNode> drugNodes = ReadFile.creatChildMedicineTree();
        Set<String> childNameList = ReadFile.getChildNameList(name, drugNodes);
        Map<String, Set<String>> resultMap = new HashMap<>();
        for (String name1Level : childNameList) {
            Set<String> allChildNameList = ReadFile.getAllChildNameList(name1Level, drugNodes);
            resultMap.put(name1Level, allChildNameList);
        }
        return resultMap;
    }


    /**
     * 获取二级变化趋势
     *
     * @param
     * @return
     */
    public List<DrugTendency> getSecondLevelCount(Map<String, Map<String, Integer>> stringMapMap, Map<String, Set<String>> nextLevelNamesAndValue) {
        List<DrugTendency> list = new LinkedList<>();
        // y药品
        for (Map.Entry<String, Set<String>> levelDrugName : nextLevelNamesAndValue.entrySet()) {
            String drugName = levelDrugName.getKey();
            Set<String> drugValueList = levelDrugName.getValue();
            DrugTendency tendency = new DrugTendency();
            tendency.setName(drugName);
            Map<String, Integer> drugMap = new HashMap<>();
            Map<String, Map<String, Integer>> drugCountMap = new HashMap<>();
            for (Map.Entry<String, Map<String, Integer>> entry : stringMapMap.entrySet()) {
                //时间
                String time = entry.getKey();
                //疾病名 ：数量
                Map<String, Integer> drugAndCount = entry.getValue();
                for (Map.Entry<String, Integer> childEntry : drugAndCount.entrySet()) {
                    //药品成分名
                    String childEntryKey = childEntry.getKey();
                    //数量
                    Integer childEntryValue = childEntry.getValue();
                    if (drugValueList.contains(childEntryKey)) {
                        if (drugMap.containsKey(time)) {
                            drugMap.put(time, drugMap.get(time) + childEntryValue);
                        } else {
                            drugMap.put(time, childEntryValue);
                        }
                        if (drugCountMap.containsKey(childEntryKey)) {
                            Map<String, Integer> map = drugCountMap.get(childEntryKey);
                            map.put(time, childEntryValue);
                            drugCountMap.put(childEntryKey, map);
                        } else {
                            Map<String, Integer> tempMap = new HashMap<>();
                            tempMap.put(time, childEntryValue);
                            drugCountMap.put(childEntryKey, tempMap);
                        }

                    } else {
                        drugMap.put(time, drugMap.get(time) == null ? 0 : drugMap.get(time));
                    }

                }
            }
            tendency.setDrugMap(drugMap);
            tendency.setDrugCount(drugCountMap);

            list.add(tendency);
        }

        return list;
    }



}

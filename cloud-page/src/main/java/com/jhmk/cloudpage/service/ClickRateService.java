package com.jhmk.cloudpage.service;

import com.jhmk.cloudentity.page.bean.ClickRate;
import com.jhmk.cloudentity.page.bean.repository.service.ClickRateRepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ziyu.zhou
 * @date 2018/8/29 16:47
 */
@Service
public class ClickRateService {
    @Autowired
    ClickRateRepService clickRateRepService;

    //分隔符
    private static final String SPLITSYMPOL = "&&&";
    public static Map<String, Integer> clickRateMap;
    public static LinkedList<ClickRate> clickRateList;

    /**
     * 初始化一个map
     *
     * @return
     */

    public static Map<String, Integer> initMaps() {
        clickRateMap = new ConcurrentHashMap<>();
        return clickRateMap;
    }

    public static LinkedList<ClickRate> initList() {
        clickRateList = new LinkedList<>();
        return clickRateList;
    }

    /**
     * 将数据保存到map中
     *
     * @param clickRate
     */
    public static void addDate2Map(ClickRate clickRate) {
        if (clickRateMap == null) {
            initMaps();
        }
        // 封装用户统计的request，并且用hash算法分布到不同的队列当中
        String doctorId = clickRate.getDoctorId();
        String type = clickRate.getType();
        java.util.Date createTime = clickRate.getCreateTime();
        String key = doctorId + SPLITSYMPOL + type + SPLITSYMPOL + createTime;
        if (clickRateMap.containsKey(key)) {
            Integer integer = clickRateMap.get(key);
            clickRateMap.put(key, integer + 1);
        } else {
            clickRateMap.put(key, 1);
        }
        System.out.println("添加点击事件成功");
    }

    public static void addDate2List(ClickRate clickRate) {
        if (clickRateList == null) {
            initList();
        }
        // 封装用户统计的request，并且用hash算法分布到不同的队列当中
        clickRateList.add(clickRate);
        System.out.println("添加点击事件成功");
    }

    public Map<String, Integer> initChildMap() {
        Map<String, Integer> initMap = new HashMap<>();
        List<String> distinctType = clickRateRepService.getDistinctType();
        for (String type : distinctType) {
            initMap.put(type, 0);

        }
        return initMap;
    }


}

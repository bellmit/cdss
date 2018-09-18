package com.jhmk.cloudpage.service;

import com.jhmk.cloudentity.page.bean.ClickRate;
import com.jhmk.cloudutil.model.WebPage;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ziyu.zhou
 * @date 2018/8/29 16:47
 */
public class ClickRateService {

    //分隔符
    private static final String SPLITSYMPOL = "&&&";
    public static Map<String, Integer> clickRateMap;

    /**
     * 初始化一个map
     * @return
     */

    public static Map<String, Integer> initMaps() {
        clickRateMap = new ConcurrentHashMap<>();
        return clickRateMap;
    }

    /**
     * 将数据保存到map中
     *
     * @param clickRate
     */
    public static void addDate2Map(ClickRate clickRate) {
        if (clickRateMap==null){
            initMaps();
        }
        // 封装用户统计的request，并且用hash算法分布到不同的队列当中
        String doctorId = clickRate.getDoctorId();
        String type = clickRate.getType();
        Date createTime = clickRate.getCreateTime();
        String key = doctorId + SPLITSYMPOL + type + SPLITSYMPOL + createTime;
        if (clickRateMap.containsKey(key)) {
            Integer integer = clickRateMap.get(key);
            clickRateMap.put(key, integer + 1);
        } else {
            clickRateMap.put(key, 1);
        }
        System.out.println("添加点击事件成功");
    }


}

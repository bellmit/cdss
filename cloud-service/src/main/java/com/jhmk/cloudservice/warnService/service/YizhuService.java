package com.jhmk.cloudservice.warnService.service;


import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudentity.earlywaring.entity.repository.service.YizhuRepService;
import com.jhmk.cloudentity.earlywaring.entity.rule.*;
import com.jhmk.cloudutil.util.MapUtil;
import com.jhmk.cloudutil.util.ReflexUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ziyu.zhou
 * @date 2018/7/30 14:18
 */

@Service
public class YizhuService {
    private static final Logger logger = LoggerFactory.getLogger(YizhuService.class);
    @Autowired
    YizhuRepService yizhuRepService;
    @Transactional
    public void saveAndFlush(Rule rule) {
        String patient_id = rule.getPatient_id();
        String visit_id = rule.getVisit_id();
        List<Yizhu> yizhu = rule.getYizhu();
        if (yizhu != null && yizhu.size() > 0) {
            yizhuRepService.deleteByPatient_idAndVisit_id(patient_id, visit_id);
            try {
                logger.info("医嘱数据为：{}", JSONObject.toJSONString(yizhu));
                for (Yizhu yizhu1:yizhu) {
                    yizhu1.setPatient_id(patient_id);
                    yizhu1.setVisit_id(visit_id);
                    yizhuRepService.save(yizhu1);
                }
            } catch (Exception e) {
                logger.info("医嘱数据为：{}", JSONObject.toJSONString(yizhu));
            }
        }
    }

    public List<Yizhu> analyzeJson2Yizhu(JSONObject jo) {
        List<Yizhu> beanList = new ArrayList<>();
        String patient_id = jo.getString("patient_id");
        String visit_id = jo.getString("visit_id");
        List<Map<String, String>> yizhus = (List<Map<String, String>>) jo.get("yizhu");
        for (Map<String, String> m : yizhus) {
            m.put("patient_id", patient_id);
            m.put("visit_id", visit_id);
            Yizhu bean = MapUtil.map2Bean(m, Yizhu.class);
            beanList.add(bean);
        }
        return beanList;
    }
}

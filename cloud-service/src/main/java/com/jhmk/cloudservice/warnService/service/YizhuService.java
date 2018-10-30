package com.jhmk.cloudservice.warnService.service;


import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudentity.earlywaring.entity.repository.service.YizhuRepService;
import com.jhmk.cloudentity.earlywaring.entity.rule.*;
import com.jhmk.cloudutil.util.MapUtil;
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
    @Autowired
    YizhuRepService yizhuRepService;

    @Transactional
    public void saveAndFlush(Rule rule) {
        String patient_id = rule.getPatient_id();
        String visit_id = rule.getVisit_id();
        List<Yizhu> allByPatientIdAndVisitId = yizhuRepService.findLessThanVisit_id(patient_id, visit_id);


        if (allByPatientIdAndVisitId != null && allByPatientIdAndVisitId.size() > 0) {
            yizhuRepService.delete(allByPatientIdAndVisitId);
        }
        List<Yizhu> yizhu = rule.getYizhu();
        if (yizhu != null && yizhu.size() > 0) {
            yizhuRepService.save(yizhu);
        }
    }

    public List<Yizhu> analyzeJson2Yizhu(JSONObject jo) {
        List<Yizhu> beanList = new ArrayList<>();
        List<Map<String, String>> yizhus = (List<Map<String, String>>) jo.get("yizhu");
        for (Map<String, String> m : yizhus) {
            m.put("patient_id", jo.getString("patient_id"));
            m.put("visit_id", jo.getString("visit_id"));
            Yizhu bean = MapUtil.map2Bean(m, Yizhu.class);
            beanList.add(bean);
        }
        return beanList;
    }
}

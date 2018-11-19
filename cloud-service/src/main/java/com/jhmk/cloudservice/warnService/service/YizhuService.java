package com.jhmk.cloudservice.warnService.service;


import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudentity.earlywaring.entity.repository.service.YizhuRepService;
import com.jhmk.cloudentity.earlywaring.entity.rule.*;
import com.jhmk.cloudservice.warnService.webservice.AnalysisXmlService;
import com.jhmk.cloudservice.warnService.webservice.CdrService;
import com.jhmk.cloudutil.config.BaseConstants;
import com.jhmk.cloudutil.config.OrderStatusConstants;
import com.jhmk.cloudutil.util.MapUtil;
import com.jhmk.cloudutil.util.ReflexUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author ziyu.zhou
 * @date 2018/7/30 14:18
 */

@Service
public class YizhuService {
    private static final Logger logger = LoggerFactory.getLogger(YizhuService.class);
    @Autowired
    CdrService cdrService;
    @Autowired
    AnalysisXmlService analysisXmlService;
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
                logger.debug("医嘱数据为：{}", JSONObject.toJSONString(yizhu));
                for (Yizhu yizhu1 : yizhu) {
                    yizhu1.setPatient_id(patient_id);
                    yizhu1.setVisit_id(visit_id);
                    yizhuRepService.save(yizhu1);
                }
            } catch (Exception e) {
                logger.debug("医嘱数据为：{}", JSONObject.toJSONString(yizhu));
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

    /**
     * 从数据中心获取医嘱（3院）
     *
     * @param rule
     * @return
     */
    public List<Yizhu> getYizhuFromCdr(Rule rule) {

        //基础map 放相同数据
        Map<String, String> baseParams = new HashMap<>();
        baseParams.put("oid", BaseConstants.OID);
        baseParams.put("patient_id", rule.getPatient_id());
        baseParams.put("visit_id", rule.getVisit_id());
        Map<String, String> params = new HashMap<>();
        //获取医嘱
        params.put("ws_code", BaseConstants.JHHDRWS012A);
        params.putAll(baseParams);

        //获取医嘱类型为长期医嘱的 并且ORDER_END_TIME 为null的 表示没有结束
        List<Map<String, String>> listConditions = new LinkedList<>();
        Map<String, String> conditionParams = new HashMap<>();
        conditionParams.put("elemName", "ORDER_PROPERTIES_NAME");
        conditionParams.put("value", "长期");
        conditionParams.put("operator", "=");
        listConditions.add(conditionParams);

        //获取入出转xml
        String yizhuData = cdrService.getDataByCDR(params, listConditions);
        //获取入院时间 出院时间
        List<Yizhu> maps = analysisXmlService.analysisXml2Yizhu(yizhuData);
        return maps;
    }


    /**
     * 广安门根据orderNo唯一标识来更新医嘱
     */
    public void saveOrUpdateYizhuByOrderNo(List<Yizhu> list) {
        for (Yizhu yizhu : list) {
            //医嘱唯一标识
            String order_no = yizhu.getOrder_no();
            if (OrderStatusConstants.stopYizhu.equals(yizhu.getStatus()) || OrderStatusConstants.dropYizhu.equals(yizhu.getStatus())) {
                yizhuRepService.deleteByOrder_no(order_no);
            } else {
                List<Yizhu> allByOrder_no = yizhuRepService.findAllByOrder_no(order_no);
                if (allByOrder_no.size() > 0) {
                    yizhuRepService.deleteByOrder_no(order_no);
                }
                yizhuRepService.save(yizhu);
            }

        }

    }
}

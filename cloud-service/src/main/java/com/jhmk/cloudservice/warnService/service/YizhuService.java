package com.jhmk.cloudservice.warnService.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.jhmk.cloudentity.earlywaring.entity.repository.service.YizhuRepService;
import com.jhmk.cloudentity.earlywaring.entity.rule.*;
import com.jhmk.cloudservice.warnService.webservice.AnalysisXmlService;
import com.jhmk.cloudservice.warnService.webservice.CdrService;
import com.jhmk.cloudutil.config.BaseConstants;
import com.jhmk.cloudutil.config.UrlConstants;
import com.jhmk.cloudutil.config.OrderStatusConstants;
import com.jhmk.cloudutil.util.MapUtil;
import org.apache.commons.lang3.StringUtils;
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


    /**
     * 下医嘱获取医嘱
     * 解析成所需医嘱 并入库
     *
     * @param data
     * @param hospitalName
     * @return
     */
    public List<Yizhu> getYizhu(String data, String hospitalName) {
        List<Yizhu> yizhuList = null;
        JSONObject object = JSONObject.parseObject(data);
        String patientId = object.getString("patient_id");
        String visitId = object.getString("visit_id");
        if ("bysy".equals(hospitalName)) {//北医三院
            yizhuList = analyzeJson2Yizhu(data);
            //更新医嘱
            saveAndFlush(yizhuList, patientId, visitId);
        } else if ("gam".equals(hospitalName)) {//广安门
            //解析医嘱
            List<Yizhu> anaYizhuList = analyzeJson2Yizhu(data);
            //更新最新在执行医嘱
            saveOrUpdateYizhuByOrderNo(anaYizhuList);
            //查询最新在执行医嘱
            yizhuList = yizhuRepService.findAllByPatientIdAndVisitId(patientId, visitId);
        } else if ("xzey".equals(hospitalName)) {//徐州二院
            // TODO: 2019/1/15 未接入此功能
        } else if ("gyey".equals(hospitalName)) {//广医二院
            // TODO: 2019/1/15 未接入此功能
        }
        return yizhuList;
    }

    /**
     * 下诊断获取医嘱
     * 直接从数据库取 无需处理
     *
     * @param map
     * @param hospitalName
     * @return
     */
    public List<Yizhu> getYizhu(Map<String, String> map, String hospitalName) {
        List<Yizhu> yizhuList = null;
        String patientId = map.get("patient_id");
        String visitId = map.get("visit_id");
        if ("bysy".equals(hospitalName)) {//北医三院
            yizhuList = yizhuRepService.findAllByPatientIdAndVisitId(patientId, visitId);
            if (yizhuList == null || yizhuList.size() == 0) {
//            //获取数据中心医嘱
                yizhuList = getYizhuFromCdr(map);
            }
        } else if ("gam".equals(hospitalName)) {//广安门
            yizhuList = yizhuRepService.findAllByPatientIdAndVisitId(patientId, visitId);
        } else if ("xzey".equals(hospitalName)) {//徐州二院
            // TODO: 2019/1/15 未有此功能
//            jianyanbaogaoList = getJianyanbaogaoBypatientIdAndVisitId(patientId, visitId);
        } else if ("gyey".equals(hospitalName)) {//广医二院
            // TODO: 2019/1/15 未有此功能
        }
        return yizhuList;
    }


    @Transactional
    public void saveAndFlush(List<Yizhu> yizhuList, String patientId, String visitId) {
        if (yizhuList != null && yizhuList.size() > 0) {
            yizhuRepService.deleteByPatient_idAndVisit_id(patientId, patientId);
            try {
                yizhuRepService.save(yizhuList);
                logger.info("医嘱保存成功，数量为：{}", yizhuList.size());
            } catch (Exception e) {
                e.printStackTrace();
                logger.debug("医嘱数据为：{}", JSONObject.toJSONString(yizhuList));
            }
        }
    }

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

    /**
     * 将数据解析为医嘱
     *
     * @param data
     * @return
     */
    public List<Yizhu> analyzeJson2Yizhu(String data) {
        JSONObject object = JSONObject.parseObject(data);
        String patientId = object.getString("patient_id");
        String visitId = object.getString("visit_id");

        List<Yizhu> yizhuList = JSONObject.parseObject(data, new TypeReference<List<Yizhu>>() {
        });
        for (Yizhu yizhu : yizhuList) {
            yizhu.setPatient_id(patientId);
            yizhu.setVisit_id(visitId);
        }
        return yizhuList;
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
        if (StringUtils.isEmpty(yizhuData)) {
            return null;
        }
        //获取入院时间 出院时间
        List<Yizhu> maps = analysisXmlService.analysisXml2Yizhu(yizhuData);
        return maps;
    }

    public List<Yizhu> getYizhuFromCdr(Map<String, String> rule) {
        //基础map 放相同数据
        Map<String, String> baseParams = new HashMap<>();
        baseParams.put("oid", BaseConstants.OID);
        baseParams.put("patient_id", rule.get("patient_id"));
        baseParams.put("visit_id", rule.get("visit_id"));
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
        if (StringUtils.isEmpty(yizhuData)) {
            return null;
        }
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

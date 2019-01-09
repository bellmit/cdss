package com.jhmk.cloudservice.cdssPageService;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudentity.earlywaring.entity.SmShowLog;
import com.jhmk.cloudutil.config.BaseConstants;
import com.jhmk.cloudutil.config.UrlConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ziyu.zhou
 * @date 2018/12/21 14:19
 */
@Service
public class SmShowLogService {
    /**
     * 将cdss服务返回的数据解析为showlog
     *
     * @param str      返回数据
     * @param fromData 原始数据
     */
    public List<SmShowLog> getTipList2ShowLogList(String str, String fromData) {
        if (StringUtils.isEmpty(str) || BaseConstants.SYMPOL.equals(str)) {
            return null;
        }
        JSONObject object = JSONObject.parseObject(fromData);
        JSONArray jsonArray = JSONObject.parseArray(str);
        Iterator<Object> iterator = jsonArray.iterator();
        List<SmShowLog> smShowLogList = new ArrayList<>(2);
        SmShowLog smShowLog = null;
        String patientId = object.getString("patient_id");
        String doctorId = object.getString("doctor_id");
        String visitId = object.getString("visit_id");
        while (iterator.hasNext()) {
            JSONObject next = (JSONObject) iterator.next();
            String itemName = next.getString("itemName");
            String type = next.getString("type");
            String stat = next.getString("stat");
            smShowLog = new SmShowLog();
            smShowLog.setItemName(itemName);
            smShowLog.setType(type);
            smShowLog.setStat(stat);
            String data = next.getString("data");
            smShowLog.setDate(data);
            String significance = next.getString("significance");
            if (StringUtils.isNotBlank(itemName) || StringUtils.isNotBlank(significance) || !"{}".equals(significance.trim())) {
                smShowLog.setSignificance(significance);
                String value = next.getString("value");
                smShowLog.setValue(value);
                smShowLog.setRuleStatus(0);
                smShowLog.setPatientId(patientId);
                smShowLog.setDoctorId(doctorId);
                smShowLog.setVisitId(visitId);
                smShowLogList.add(smShowLog);
            }
        }
        return smShowLogList;
    }

    public List<SmShowLog> getTipList2ShowLogList(String str) {
        if (StringUtils.isEmpty(str) || BaseConstants.SYMPOL.equals(str)) {
            return null;
        }
        JSONArray jsonArray = JSONObject.parseArray(str);
        Iterator<Object> iterator = jsonArray.iterator();
        List<SmShowLog> smShowLogList = new ArrayList<>(2);
        SmShowLog smShowLog = null;
        while (iterator.hasNext()) {
            JSONObject next = (JSONObject) iterator.next();
            String itemName = next.getString("itemName");
            String type = next.getString("type");
            String stat = next.getString("stat");
            smShowLog = new SmShowLog();
            smShowLog.setItemName(itemName);
            smShowLog.setType(type);
            smShowLog.setStat(stat);
            String data = next.getString("data");
            smShowLog.setDate(data);
            String significance = next.getString("significance");
            if (StringUtils.isNotBlank(itemName) || StringUtils.isNotBlank(significance) || !"{}".equals(significance.trim())) {
                smShowLog.setSignificance(significance);
                String value = next.getString("value");
                smShowLog.setValue(value);
                smShowLog.setRuleStatus(0);
                smShowLogList.add(smShowLog);
            }
        }
        return smShowLogList;
    }

    /**
     * 只取检验报告集合
     *
     * @param logs
     * @return
     */
    public List<SmShowLog> getLabShowLogList(List<SmShowLog> logs) {
        if (Objects.isNull(logs)) {
            return null;
        }
        List<SmShowLog> collect = logs.stream().filter(log -> "lab".equals(log.getType())).collect(Collectors.toList());
        return collect;
    }

}

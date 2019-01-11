package com.jhmk.cloudpage.controller;

/**
 * @author ziyu.zhou
 * @date 2018/8/23 9:57
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudentity.base.BaseController;
import com.jhmk.cloudentity.earlywaring.entity.SmShowLog;
import com.jhmk.cloudentity.earlywaring.entity.repository.service.SmShowLogRepService;
import com.jhmk.cloudentity.page.bean.DrugTendency;
import com.jhmk.cloudservice.cdssPageService.CdssPageService;
import com.jhmk.cloudutil.config.UrlConstants;
import com.jhmk.cloudutil.config.UrlPropertiesConfig;
import com.jhmk.cloudutil.model.AtResponse;
import com.jhmk.cloudutil.model.ResponseCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * cdss小界面控制层
 */
@Controller
@RequestMapping("/cdss")
public class CdssPageController extends BaseController {
    Logger logger = LoggerFactory.getLogger(CdssPageController.class);


    @Autowired
    RestTemplate restTemplate;
    @Autowired
    UrlPropertiesConfig urlPropertiesConfig;

    @Autowired
    SmShowLogRepService smShowLogRepService;
    @Autowired
    CdssPageService cdssPageService;


    /**
     * 用药变化趋势
     *
     * @param response
     * @param map
     */
    @PostMapping("/getPlanStat")
    public void getPlanStat(HttpServletResponse response, @RequestBody(required = false) String map) {
        System.out.println("接受数据为：==========" + map);
        AtResponse resp = new AtResponse();
        JSONObject jsonObject = JSONObject.parseObject(map);
        String drugName = jsonObject.getString("drugName");
        JSONObject planStat = cdssPageService.getPlanStat(map);
        System.out.println("解析数据为：" + drugName);
        String result = planStat.getString("result");
        //转换为标准名
        Map<String, Map<String, Integer>> stringMapMap = cdssPageService.transform2StandardName(result);
        Map<String, Set<String>> nextLevelNamesAndValue = cdssPageService.getNextLevelNamesAndValue(drugName);
        List<DrugTendency> secondLevelCount = cdssPageService.getSecondLevelCount(stringMapMap, nextLevelNamesAndValue);
        resp.setData(secondLevelCount);
        resp.setResponseCode(ResponseCode.OK);
        wirte(response, resp);
    }

    /**
     * 诊疗计划
     *
     * @param response
     * @param map
     */
    @PostMapping("/getTreatPlan")
    public void getTreatPlan(HttpServletResponse response, @RequestBody(required = false) String map) {
        JSONObject jsonObject = JSONObject.parseObject(map);
        String idList = jsonObject.getString("idList");
        List<String> list = Arrays.asList(idList.split(","));
        String treatPlan = cdssPageService.getTreatPlan(list);
        wirte(response, treatPlan);
    }

    /**
     * 获取鉴别诊断疾病名 前10个
     *
     * @param response
     * @param map
     */

    @PostMapping("/getDifferentialDiagnosisName")
    public void getDifferentialDiagnosisName(HttpServletResponse response, @RequestBody(required = false) String map) {
        AtResponse resp = new AtResponse(System.currentTimeMillis());
        List<String> resultList = new ArrayList<>(10);
        Object parse = JSONObject.parse(map);
        String s = null;
        try {

            s = restTemplate.postForObject(urlPropertiesConfig.getCdssurl() + UrlConstants.getDifferentialDiagnosisName, parse, String.class);
        } catch (Exception e) {
            logger.info("调用{}失败，原因为：{}", UrlConstants.getDifferentialDiagnosisName, e.getCause());
            resp.setResponseCode(ResponseCode.INERERROR);
        }
        if (StringUtils.isNotBlank(s)) {
            JSONArray jsonArray = JSONObject.parseArray(s);
            List<Object> objects = null;
            if (jsonArray.size() > 10) {
                objects = jsonArray.subList(0, 10);
            } else {
                objects = jsonArray.subList(0, jsonArray.size());
            }
            Iterator<Object> iterator = objects.iterator();
            while (iterator.hasNext()) {
                Map<String, String> next = (Map) iterator.next();
                resultList.add(next.get("disease_obj_differential_diagnosis_differential_diagnosis_differential_diagnosis_name"));
            }
            resp.setResponseCode(ResponseCode.OK);
            resp.setData(resultList);

        }
        wirte(response, resp);
    }

    /**
     * 检验报告解读
     *
     * @param response
     * @param map
     */
    @PostMapping("/interpretLab")
    public void interpretLab (HttpServletResponse response, @RequestBody String map) {
        AtResponse resp = new AtResponse(System.currentTimeMillis());
        JSONObject object = JSONObject.parseObject(map);
        String patientId = object.getString("patientId");
        String visitId = object.getString("visitId");
        String doctorId = object.getString("doctorId");
        List<SmShowLog> lab = smShowLogRepService.findAllByDoctorIdAndPatientIdAndVisitIdAndType(doctorId, patientId, visitId, "lab");
        resp.setData(lab);
        resp.setResponseCode(ResponseCode.OK);
        wirte(response, resp);
    }

}

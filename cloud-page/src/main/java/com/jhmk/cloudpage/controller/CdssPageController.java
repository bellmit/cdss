package com.jhmk.cloudpage.controller;

/**
 * @author ziyu.zhou
 * @date 2018/8/23 9:57
 */

import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudentity.base.BaseController;
import com.jhmk.cloudentity.page.bean.DrugTendency;
import com.jhmk.cloudservice.cdssPageService.CdssPageService;
import com.jhmk.cloudutil.config.CdssPageConstants;
import com.jhmk.cloudutil.model.AtResponse;
import com.jhmk.cloudutil.model.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * cdss小界面控制层
 */
@Controller
@RequestMapping("/page/cdss")
public class CdssPageController extends BaseController {
    Logger logger = LoggerFactory.getLogger(CdssPageController.class);


    @Autowired
    RestTemplate restTemplate;
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
        AtResponse resp = new AtResponse();
        JSONObject jsonObject = JSONObject.parseObject(map);
        String idList = jsonObject.getString("idList");
        List<String> list = Arrays.asList(idList.split(","));
        String treatPlan = cdssPageService.getTreatPlan(list);
        wirte(response, treatPlan);
    }


}

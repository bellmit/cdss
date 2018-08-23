package com.jhmk.cloudpage.controller;

/**
 * @author ziyu.zhou
 * @date 2018/8/23 9:57
 */

import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudentity.base.BaseController;
import com.jhmk.cloudentity.cdss.pojo.repository.service.SysDiseasesRepService;
import com.jhmk.cloudpage.service.CdssRunRuleService;
import com.jhmk.cloudpage.service.CdssService;
import com.jhmk.cloudpage.service.TestService;
import com.jhmk.cloudservice.cdssPageService.CdssPageService;
import com.jhmk.cloudutil.config.CdssConstans;
import com.jhmk.cloudutil.config.CdssPageConstants;
import com.jhmk.cloudutil.model.AtResponse;
import com.jhmk.cloudutil.model.ResponseCode;
import com.netflix.discovery.converters.Auto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
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
    SysDiseasesRepService sysDiseasesRepository;
    @Autowired

    CdssService cdssService;
    @Autowired
    TestService testService;
    @Autowired
    CdssRunRuleService cdssRunRuleService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CdssPageService cdssPageService;


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
        Map<String, Map<String, Integer>> secondLevelCount = cdssPageService.getSecondLevelCount(stringMapMap, nextLevelNamesAndValue);
        Map<String, Map<String, Map<String, Integer>>> durgCount = cdssPageService.getDurgCount(stringMapMap, nextLevelNamesAndValue);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("count", secondLevelCount);
        resultMap.put("durg", durgCount);
        System.out.println(JSONObject.toJSONString(secondLevelCount));
        resp.setData(resultMap);
        resp.setResponseCode(ResponseCode.OK);
        wirte(response, resp);
    }


}

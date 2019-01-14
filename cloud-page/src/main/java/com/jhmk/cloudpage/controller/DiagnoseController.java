package com.jhmk.cloudpage.controller;

import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudentity.base.BaseController;
import com.jhmk.cloudservice.warnService.service.RuleService;
import com.jhmk.cloudutil.config.UrlConstants;
import com.jhmk.cloudutil.config.UrlPropertiesConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;

/**
 * @author ziyu.zhou
 * @date 2019/1/14 17:08
 * exe 诊断列表及功能
 */

@Controller
@RequestMapping("diagnose")
public class DiagnoseController extends BaseController {

    Logger logger = LoggerFactory.getLogger(DiagnoseController.class);
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    UrlPropertiesConfig urlPropertiesConfig;
    @Autowired

    RuleService ruleService;

    /**
     * 推荐诊断
     *
     * @param response
     * @param map
     */
    @PostMapping("/emrdiseaseinfo")
    public void emrdiseaseinfo(HttpServletResponse response, @RequestBody String map) {
        Object parse = JSONObject.parse(map);
        String s = restTemplate.postForObject(urlPropertiesConfig.getCdssurl() + UrlConstants.Emrdiseaseinfo, parse, String.class);
        wirte(response, s);
    }
}

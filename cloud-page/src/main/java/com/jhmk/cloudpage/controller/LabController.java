package com.jhmk.cloudpage.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudentity.base.BaseController;
import com.jhmk.cloudentity.base.BaseEntityController;
import com.jhmk.cloudentity.earlywaring.entity.SmHospitalLog;
import com.jhmk.cloudentity.earlywaring.entity.SmShowLog;
import com.jhmk.cloudentity.page.bean.ClickRate;
import com.jhmk.cloudservice.cdssPageService.SmShowLogService;
import com.jhmk.cloudutil.config.BaseConstants;
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
import java.util.Iterator;
import java.util.List;

/**
 * @author ziyu.zhou
 * @date 2018/12/21 11:41
 * 检验报告
 */
@Controller
@RequestMapping("/lab")
public class LabController extends BaseController {
    Logger logger = LoggerFactory.getLogger(LabController.class);

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    UrlPropertiesConfig urlPropertiesConfig;
    @Autowired
    SmShowLogService smShowLogService;

    /**
     * 根据检验报告作出提醒信息
     *
     * @param response
     * @param map
     */
    @PostMapping("/warnByLab")
    public void warnByLab(HttpServletResponse response, @RequestBody String map) {
        Object object = JSONObject.parse(map);
        AtResponse resp = new AtResponse(System.currentTimeMillis());
        String result = restTemplate.postForObject(urlPropertiesConfig.getCdssurl() + BaseConstants.getTipList, object, String.class);
        List<SmShowLog> tipList2ShowLogList = smShowLogService.getTipList2ShowLogList(result);
        List<SmShowLog> labShowLogList = smShowLogService.getLabShowLogList(tipList2ShowLogList);
        resp.setData(labShowLogList);
        resp.setResponseCode(ResponseCode.OK);
        wirte(response, resp);

    }

    @PostMapping("/labWarnById")
    public void labWarnById(HttpServletResponse response, @RequestBody String map) {
        Object object = JSONObject.parse(map);
        AtResponse resp = new AtResponse(System.currentTimeMillis());
//        String result = restTemplate.postForObject(urlPropertiesConfig.getCdssurl() + BaseConstants.getTipList, object, String.class);
//        String result = restTemplate.postForObject("http://localhost:7088"+ BaseConstants.getLocalJybg, object, String.class);
        String result = restTemplate.postForObject("http://192.168.8.20:7088"+ BaseConstants.getLocalJybg, object, String.class);
        List<SmShowLog> tipList2ShowLogList = smShowLogService.getTipList2ShowLogList(result);
        List<SmShowLog> labShowLogList = smShowLogService.getLabShowLogList(tipList2ShowLogList);
        resp.setData(labShowLogList);
        resp.setResponseCode(ResponseCode.OK);
        wirte(response, resp);

    }
}

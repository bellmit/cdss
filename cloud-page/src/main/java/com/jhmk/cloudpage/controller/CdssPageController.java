package com.jhmk.cloudpage.controller;

/**
 * @author ziyu.zhou
 * @date 2018/8/23 9:57
 */

import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudentity.base.BaseController;
import com.jhmk.cloudentity.cdss.page.DrugTendency;
import com.jhmk.cloudentity.earlywaring.entity.*;
import com.jhmk.cloudentity.page.bean.ClickRate;
import com.jhmk.cloudentity.page.service.ClickRateRepService;
import com.jhmk.cloudservice.cdssPageService.CdssPageService;
import com.jhmk.cloudutil.model.AtResponse;
import com.jhmk.cloudutil.model.ResponseCode;
import com.jhmk.cloudutil.util.DateFormatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
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



}

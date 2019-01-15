package com.jhmk.cloudpage.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudentity.base.BaseController;
import com.jhmk.cloudentity.earlywaring.entity.rule.Rule;
import com.jhmk.cloudservice.warnService.service.RuleService;
import com.jhmk.cloudutil.model.AtResponse;
import com.jhmk.cloudutil.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author ziyu.zhou
 * @date 2019/1/11 13:38
 * 提醒控制层
 */

@Controller
@RequestMapping("/warn")
public class WarnController extends BaseController {
    @Value("hospital")
    private String hospital;
    Logger logger = LoggerFactory.getLogger(WarnController.class);
    @Autowired
    RuleService ruleService;

    @PostMapping("/ruleMatch")
    public void ruleMatch(HttpServletResponse response, @RequestBody String map) {
        AtResponse resp = null;
        JSONObject object = JSON.parseObject(map);
//        logger.info("接受到的初始数据{}", JSONObject.toJSONString(object));
//        //页面来源 入院记录： 0 :保存病历 1：下诊断 2：打开病例 3：新建病例 6：下医嘱 7：病案首页 8：其他
//        String pageSource = object.getString("pageSource");//页面来源
//        logger.info("页面来源：{}", pageSource);
//        if (StringUtils.isEmpty(pageSource) || "test".equals(pageSource)) {
//            resp = ruleMatchService.ruleMatch(map);
//        } else if ("6".equals(pageSource)) {//医嘱 为6 其他做下诊断处理
//            resp = ruleMatchService.ruleMatchByDoctorAdvice(map);
//        } else {
//            resp = ruleMatchService.ruleMatchByDiagnose(map);
//        }


        //1. 拼接规则  基本信息 检验检查 医嘱

        Map<String, String> parse = (Map) JSONObject.parse(map);
        String s = ruleService.anaRule(parse);
        //解析一诉五史
        JSONObject jsonObject = JSONObject.parseObject(s);
        Rule rule = Rule.fill(jsonObject);


        //2. 规则匹配
        logger.info("下诊断规则匹配json串：{}", JSONObject.toJSONString(rule));
        String data = ruleService.ruleMatchByRuleBase(rule);
        // 2.1 将匹配结果入库
        if (StringUtils.isNotEmpty(data)) {
            ruleService.add2LogTableNew(data, rule);
        }

        //3. 既往史匹配
        //3.1 匹配结果入库

        //4. 返回信息


        wirte(response, resp);
    }

}

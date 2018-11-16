package com.jhmk.warn.matchController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudentity.base.BaseEntityController;
import com.jhmk.cloudentity.earlywaring.entity.SmHospitalLog;
import com.jhmk.cloudentity.earlywaring.entity.SmShowLog;
import com.jhmk.cloudentity.earlywaring.entity.UserModel;
import com.jhmk.cloudentity.earlywaring.entity.rule.Rule;
import com.jhmk.cloudservice.warnService.service.RuleService;
import com.jhmk.cloudservice.warnService.webservice.AnalysisXmlService;
import com.jhmk.cloudutil.model.AtResponse;
import com.jhmk.cloudutil.util.StringUtil;
import com.jhmk.warn.controller.RuleMatchController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author ziyu.zhou
 * @date 2018/11/6 11:30
 */
@Controller
@RequestMapping("/warn/gam")
public class GamController extends BaseEntityController<SmHospitalLog> {
    private static final Logger logger = LoggerFactory.getLogger(RuleMatchController.class);


    @Autowired
    RuleService ruleService;
    @Autowired
    AnalysisXmlService analysisXmlService;

    @PostMapping("/ruleMatch")
    public void ruleMatch(HttpServletResponse response, @RequestBody String map) {
        AtResponse resp = new AtResponse();
        List<SmShowLog> logList = null;
        Map<String, String> paramMap = (Map) JSON.parse(map);
        //解析规则 一诉五史 检验报告等
        String s = ruleService.anaRule(paramMap);
        String s2 = StringUtil.stringTransform(s);
        JSONObject parse = JSONObject.parseObject(s2);
        Rule rule = Rule.fill(parse);
        System.out.println(JSONObject.toJSONString(rule));
        String data = "";
        try {
            //规则匹配
            data = ruleService.ruleMatchGetResp(rule);
            logger.info("规则匹配返回结果为：{}", data);
        } catch (Exception e) {
            logger.info("规则匹配失败:{},请求数据为：{}", e.getMessage(), map);
        }
        if (StringUtils.isNotBlank(data)) {
            //获取保存信息 返回前台显示
            ruleService.add2LogTable(data, rule);
        }
        logList = ruleService.add2ShowLog(rule, data, map);
        logger.info("提示信息结果为：{}", JSONObject.toJSONString(logList));
        resp.setData(logList);
        wirte(response, resp);
        ruleService.saveRule2Database(rule);
    }

}

package com.jhmk.warn.controller;

import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudentity.base.BaseController;
import com.jhmk.cloudentity.base.BaseEntityController;
import com.jhmk.cloudentity.earlywaring.entity.UserModel;
import com.jhmk.cloudentity.earlywaring.entity.rule.Rule;
import com.jhmk.cloudentity.earlywaring.entity.rule.Yizhu;
import com.jhmk.cloudservice.warnService.service.RuleService;
import com.jhmk.cloudutil.model.AtResponse;
import com.jhmk.cloudutil.model.ResponseCode;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author ziyu.zhou
 * @date 2018/9/21 14:36
 */
@Controller
@RequestMapping("warn/temp")
public class TempController  extends BaseController {
    @Autowired
    RuleService ruleService;
    @PostMapping("/getCdrData")
    @ResponseBody
    public void getCdrData(HttpServletResponse response, @RequestBody String map) {
        Map<String, String> parse = (Map) JSONObject.parse(map);

        String s = ruleService.anaRule(parse);
        //解析一诉五史
        JSONObject jsonObject = JSONObject.parseObject(s);
        Rule rule = Rule.fill(jsonObject);
        //获取 拼接检验检查报告
        rule = ruleService.getbaogao(rule);
        List<Yizhu> yizhu = ruleService.getYizhu(rule);
        parse.put("jianyanbaogao",JSONObject.toJSONString(rule.getJianchabaogao()));
        parse.put("jianchabaogao",JSONObject.toJSONString(rule.getJianchabaogao()));
        parse.put("yizhu",JSONObject.toJSONString(yizhu));
        wirte(response,parse);
    }
}

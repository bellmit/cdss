package com.jhmk.warn.controller;

import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudentity.base.BaseController;
import com.jhmk.cloudentity.base.BaseEntityController;
import com.jhmk.cloudentity.earlywaring.entity.UserModel;
import com.jhmk.cloudentity.earlywaring.entity.rule.Jianchabaogao;
import com.jhmk.cloudentity.earlywaring.entity.rule.Jianyanbaogao;
import com.jhmk.cloudentity.earlywaring.entity.rule.Rule;
import com.jhmk.cloudentity.earlywaring.entity.rule.Yizhu;
import com.jhmk.cloudservice.warnService.service.RuleService;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 数据中心接口测试
 *
 * @author ziyu.zhou
 * @date 2018/9/21 14:36
 */
@Controller
@RequestMapping("warn/temp")
public class TempController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(CmsController.class);

    @Autowired
    RuleService ruleService;

    @PostMapping("/getCdrData")
    @ResponseBody
    public void getCdrData(HttpServletResponse response, @RequestBody String map) {
        Map<String, String> parse = (Map) JSONObject.parse(map);
        Map<String, Object> result = (Map) JSONObject.parse(map);

        String s = ruleService.anaRule(parse);
        //解析一诉五史
        JSONObject jsonObject = JSONObject.parseObject(s);
        Rule rule = Rule.fill(jsonObject);
        //获取 拼接检验检查报告
        rule = ruleService.getbaogao(rule);
        List<Yizhu> yizhu = ruleService.getYizhu(rule);
        result.put("jianyanbaogao", rule.getOriginalJianyanbaogaos());
        List<Jianchabaogao> jianchabaogao = rule.getJianchabaogao();
        if (jianchabaogao != null) {
            result.put("jianchabaogao", JSONObject.toJSON(rule.getJianchabaogao()));
        }
        result.put("yizhu", yizhu);
        wirte(response, result);
    }

    @PostMapping("/getJianyanbaogaoStr")
    @ResponseBody
    public void getJianyanbaogaoStr(HttpServletResponse response, @RequestBody String map) {
        JSONObject jsonObject = JSONObject.parseObject(map);
        AtResponse resp = new AtResponse();
        if (Objects.nonNull(jsonObject)) {
            String patient_id = jsonObject.getString("patient_id");
            String visit_id = jsonObject.getString("visit_id");
            Rule rule = ruleService.getRuleFromDatabase(patient_id, visit_id);
            //获取 拼接检验检查报告
            List<Jianyanbaogao> jianchabaogaoStr = ruleService.getJianyanbaogaoStr(rule);
            resp.setData(jianchabaogaoStr);
            resp.setResponseCode(ResponseCode.OK);
            wirte(response, resp);
        } else {
            logger.info("医嘱规则匹配传递信息为{}" + map);
        }
    }


    @PostMapping("/getJianchabaogaoStr")
    @ResponseBody
    public void getJianchabaogaoStr(HttpServletResponse response, @RequestBody String map) {
        JSONObject jsonObject = JSONObject.parseObject(map);
        if (Objects.nonNull(jsonObject)) {
            String patient_id = jsonObject.getString("patient_id");
            String visit_id = jsonObject.getString("visit_id");
            Rule rule = ruleService.getRuleFromDatabase(patient_id, visit_id);
            //获取 拼接检验检查报告
            List<Jianchabaogao> jianchabaogaoStr = ruleService.getJianchabaogaoStr(rule);
            wirte(response, jianchabaogaoStr);
        } else {
            logger.info("医嘱规则匹配传递信息为{}" + map);
        }
    }
}

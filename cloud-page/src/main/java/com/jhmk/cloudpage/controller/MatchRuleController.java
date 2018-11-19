package com.jhmk.cloudpage.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudentity.base.BaseEntityController;
import com.jhmk.cloudentity.earlywaring.entity.SmShowLog;
import com.jhmk.cloudentity.earlywaring.entity.repository.service.SmShowLogRepService;
import com.jhmk.cloudentity.earlywaring.entity.repository.service.SmUsersRepService;
import com.jhmk.cloudentity.earlywaring.entity.repository.service.YizhuRepService;
import com.jhmk.cloudentity.earlywaring.entity.rule.Jianchabaogao;
import com.jhmk.cloudentity.earlywaring.entity.rule.Jianyanbaogao;
import com.jhmk.cloudentity.earlywaring.entity.rule.Rule;
import com.jhmk.cloudentity.earlywaring.entity.rule.Yizhu;
import com.jhmk.cloudservice.warnService.service.*;
import com.jhmk.cloudservice.warnService.webservice.AnalysisXmlService;
import com.jhmk.cloudservice.warnService.webservice.CdrService;
import com.jhmk.cloudutil.config.UrlConfig;
import com.jhmk.cloudutil.model.AtResponse;
import com.jhmk.cloudutil.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author ziyu.zhou
 * @date 2018/11/16 16:48
 */
@Controller
@RequestMapping("/page/match")
public class MatchRuleController extends BaseEntityController<Object> {
    private static final Logger logger = LoggerFactory.getLogger(MatchRuleController.class);


    @Autowired
    YizhuService yizhuService;
    @Autowired
    RuleService ruleService;
    @Autowired
    AnalysisXmlService analysisXmlService;

    @Autowired
    RuleMatchService ruleMatchService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    JianchabaogaoService jianchabaogaoService;
    @Autowired
    JianyanbaogaoService jianyanbaogaoService;
    @Autowired
    SmShowLogRepService smShowLogRepService;
    @Autowired
    SmUsersRepService smUsersRepService;
    @Autowired
    HosptailLogService hosptailLogService;
    @Autowired
    UserModelService userModelService;
    @Autowired
    UrlConfig urlConfig;

    @Autowired
    YizhuRepService yizhuRepService;
    @Autowired
    CdrService cdrService;

    @Autowired
    ResolveRuleService resolveRuleService;

    /**
     * 广安门规则匹配
     *
     * @param response
     * @param map
     */
    @PostMapping("/ruleMatchForGAM")
    public void ruleMatchResult(HttpServletResponse response, @RequestBody String map) {
        AtResponse resp = null;
        map = StringUtil.stringTransform(map);
        JSONObject jsonObject = JSONObject.parseObject(map);
        String patient_id = jsonObject.getString("patient_id");
        String visit_id = jsonObject.getString("visit_id");
        logger.info("接受到的初始数据{}", map);
        //页面来源 入院记录： 0 :保存病历 1：下诊断 2：打开病例 3：新建病例 6：下医嘱 7：病案首页 8：其他
        String pageSource = jsonObject.getString("pageSource");//页面来源
        logger.info("页面来源：{}", pageSource);
        Rule rule = null;
        if (StringUtils.isEmpty(pageSource) || "test".equals(pageSource)) {
            Map<String, String> paramMap = (Map) JSON.parse(map);
            //解析规则 一诉五史 检验报告等
            String s = ruleService.anaRule(paramMap);
            JSONObject parse = JSONObject.parseObject(s);
            rule = Rule.fill(parse);
            logger.info("规则匹配数据为：{}", JSONObject.toJSONString(rule));
        } else if ("6".equals(pageSource)) {//医嘱 为6 其他做下诊断处理
            List<Yizhu> yizhus = yizhuService.analyzeJson2Yizhu(jsonObject);
            yizhuService.saveOrUpdateYizhuByOrderNo(yizhus);
            //获取正在执行医嘱
            List<Yizhu> nowYizhuList = yizhuRepService.findAllByPatientIdAndVisitId(patient_id, visit_id);
            rule.setYizhu(nowYizhuList);
            //获取 拼接检验检查报告
            List<Jianyanbaogao> jianyanbaogaoList = jianyanbaogaoService.getJianyanbaogaoBypatientIdAndVisitId(patient_id, visit_id);
            rule.setJianyanbaogao(jianyanbaogaoList);
            List<Jianchabaogao> jianchabaogaoList = jianchabaogaoService.getJianchabaogaoBypatientIdAndVisitId(patient_id, visit_id);
            rule.setJianchabaogao(jianchabaogaoList);
            //todo 获取一诉五史 诊断 走视图 亚飞解析
            logger.info("下医嘱规则匹配数据为：{}", JSONObject.toJSONString(rule));
            yizhuService.saveAndFlush(rule);
        } else {
            Map<String, String> parse = (Map) JSONObject.parse(map);
            String s = ruleService.anaRule(parse);
            //解析一诉五史
            JSONObject temp = JSONObject.parseObject(s);
            rule = Rule.fill(temp);
            List<Jianyanbaogao> jianyanbaogaoList = jianyanbaogaoService.getJianyanbaogaoBypatientIdAndVisitId(patient_id, visit_id);
            rule.setJianyanbaogao(jianyanbaogaoList);
            List<Jianchabaogao> jianchabaogaoList = jianchabaogaoService.getJianchabaogaoBypatientIdAndVisitId(patient_id, visit_id);
            rule.setJianchabaogao(jianchabaogaoList);
            List<Yizhu> yizhu = yizhuRepService.findAllByPatientIdAndVisitId(patient_id, visit_id);
            rule.setYizhu(yizhu);
            logger.info("下诊断规则匹配数据为：{}", JSONObject.toJSONString(rule));
            ruleService.saveRule2Database(rule);
        }
        //规则匹配返回结果
        String matchRuleData = ruleMatchService.matchRule(rule);
        //将匹配到规则数据保存到日志库
        ruleService.add2LogTable(matchRuleData, rule);
        //将触发规则保存到提醒库 并返回提醒数据
        ruleService.getTipList2ShowLog(rule, map);
        List<SmShowLog> logList = ruleService.add2ShowLog(rule, map);
        resp.setData(logList);
        wirte(response, resp);
    }

}

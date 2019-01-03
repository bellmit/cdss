package com.jhmk.cloudservice.warnService.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudentity.earlywaring.entity.SmShowLog;
import com.jhmk.cloudentity.earlywaring.entity.SmUsers;
import com.jhmk.cloudentity.earlywaring.entity.repository.service.SmUsersRepService;
import com.jhmk.cloudentity.earlywaring.entity.repository.service.YizhuRepService;
import com.jhmk.cloudentity.earlywaring.entity.rule.*;
import com.jhmk.cloudutil.model.AtResponse;
import com.jhmk.cloudutil.model.ResponseCode;
import com.jhmk.cloudutil.util.MapUtil;
import com.jhmk.cloudutil.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author ziyu.zhou
 * @date 2018/10/25 20:30
 */
@Service
public class RuleMatchService {

    private static final Logger logger = LoggerFactory.getLogger(RuleMatchService.class);

    @Autowired
    SmUsersRepService smUsersRepService;
    @Autowired
    JianchabaogaoService jianchabaogaoService;
    @Autowired
    JianyanbaogaoService jianyanbaogaoService;
    @Autowired
    YizhuService yizhuService;
    @Autowired
    YizhuRepService yizhuRepService;
    @Autowired
    RuleService ruleService;

    /**
     * 下诊断规则匹配
     *
     * @param map
     * @return
     */
    public AtResponse ruleMatchByDiagnose(String map) {
        AtResponse resp = new AtResponse();
        try {
            Map<String, String> parse = (Map) JSONObject.parse(map);
            String s = ruleService.anaRule(parse);
            //解析一诉五史
            JSONObject jsonObject = JSONObject.parseObject(s);
            Rule rule = Rule.fill(jsonObject);
            String patient_id = rule.getPatient_id();
            String visit_id = rule.getVisit_id();
            //获取 拼接检验检查报告
            rule = ruleService.getbaogao(rule);
            //从数据库获取 如果数据可没有 从数据中心获取
            List<Yizhu> yizhu = yizhuRepService.findAllByPatientIdAndVisitId(patient_id, visit_id);
            if (yizhu == null || yizhu.size() == 0) {
//            //获取数据中心医嘱
                yizhu = yizhuService.getYizhuFromCdr(rule);

            }
            rule.setYizhu(yizhu);
            String data = "";
            //规则匹配
            logger.info("下诊断规则匹配json串：{}", JSONObject.toJSONString(rule));
            data = ruleService.ruleMatchGetResp(rule);
            if (StringUtils.isNotBlank(data)) {
                ruleService.add2LogTable(data, rule);
            }
            List<SmShowLog> logList = ruleService.add2ShowLog(rule, data, map);
            resp.setData(logList);
            resp.setResponseCode(ResponseCode.OK);
            //一诉五史信息入库
            ruleService.saveRule2Database(rule);
        } catch (ClassCastException e) {
            resp.setResponseCode(ResponseCode.INERERROR);
            e.printStackTrace();
            logger.info("类型转换失败，{}，原始数据为：{}", e.getMessage(), map);
        } catch (Exception e) {
            resp.setResponseCode(ResponseCode.INERERROR);
            e.printStackTrace();
            logger.info("ruleMatchByDiagnose方法报错：{}，原始数据为：{}", e.getMessage(), map);
        }

        return resp;
    }

    public AtResponse ruleMatchByDoctorAdvice(String map) {
        AtResponse resp = new AtResponse();
        JSONObject jsonObject = JSONObject.parseObject(map);
        List<Yizhu> yizhus = yizhuService.analyzeJson2Yizhu(jsonObject);
        if (Objects.nonNull(jsonObject)) {
            String patient_id = jsonObject.getString("patient_id");
            String visit_id = jsonObject.getString("visit_id");
            String doctor_id = jsonObject.getString("doctor_id");
            Rule ruleBean = ruleService.getDiagnoseFromDatabase(patient_id, visit_id);
            ruleBean.setDoctor_id(doctor_id);
            //获取 拼接检验检查报告
            ruleBean = ruleService.getbaogao(ruleBean);
            //获取 拼接检验检查报告
//            List<Jianyanbaogao> jianyanbaogaoList = jianyanbaogaoService.getJianyanbaogaoBypatientIdAndVisitId(patient_id, visit_id);
//            ruleBean.setJianyanbaogao(jianyanbaogaoList);
//            List<Jianchabaogao> jianchabaogaoList = jianchabaogaoService.getJianchabaogaoBypatientIdAndVisitId(patient_id, visit_id);
//            ruleBean.setJianchabaogao(jianchabaogaoList);
            ruleBean.setYizhu(yizhus);
            String data = "";
            try {
                //规则匹配
                logger.info("下医嘱规则匹配json串：{}", JSONObject.toJSONString(ruleBean));
                data = ruleService.ruleMatchGetResp(ruleBean);
            } catch (Exception e) {
                logger.info("规则匹配失败:{}" + e.getMessage());
            }
            if (StringUtils.isNotBlank(data)) {
                ruleService.add2LogTable(data, ruleBean);
            }
            List<SmShowLog> logList = ruleService.add2ShowLog(ruleBean, data, map);
            resp.setResponseCode(ResponseCode.OK);
            resp.setData(logList);
            yizhuService.saveAndFlush(ruleBean);
        } else {
            resp.setResponseCode(ResponseCode.INERERROR);
            logger.info("医嘱规则匹配传递信息为{}" + map);
        }
        return resp;

    }

    public AtResponse ruleMatch(String map) {
        AtResponse resp = new AtResponse();
        List<SmShowLog> logList = null;
        Map<String, String> paramMap = (Map) JSON.parse(map);
        //解析规则 一诉五史 检验报告等
        String s = ruleService.anaRule(paramMap);
        String s2 = StringUtil.stringTransform(s);
        JSONObject parse = JSONObject.parseObject(s2);
        Rule rule = Rule.fill(parse);
        String data = "";
        try {
            //规则匹配
            logger.info("测试规则匹配json串：{}", JSONObject.toJSONString(rule));
            data = ruleService.ruleMatchGetResp(rule);
            logger.info("规则匹配返回结果为：{}", data);
            resp.setResponseCode(ResponseCode.OK);

        } catch (Exception e) {
            resp.setResponseCode(ResponseCode.INERERROR);
            logger.info("规则匹配失败:{},请求数据为：{}", e.getMessage(), map);
        }
        if (StringUtils.isNotBlank(data)) {
            //获取保存信息 返回前台显示
            ruleService.add2LogTable(data, rule);
            //todo  删除触发规则保存到sm_show_log表中，改为从sm_hospital表获取数据
        }
        logList = ruleService.add2ShowLog(rule, data, map);
        resp.setData(logList);
        return resp;
    }

    public AtResponse ruleMatchTest(String map) {
        AtResponse resp = new AtResponse();
        List<SmShowLog> logList = null;
        Map<String, String> paramMap = (Map) JSON.parse(map);
        //解析规则 一诉五史 检验报告等
//        String s = ruleService.anaRule(paramMap);
        String s2 = StringUtil.stringTransform(JSONObject.toJSONString(paramMap));
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
            String doctor_name = rule.getDoctor_name();
            if (StringUtils.isNotBlank(doctor_name)) {

                List<SmUsers> byUserName = smUsersRepService.findByUserName(doctor_name);
                if (byUserName != null && byUserName.size() > 0) {
                    rule.setDoctor_id(byUserName.get(0).getUserId());
                }
            }
            //获取保存信息 返回前台显示
            ruleService.add2LogTable(data, rule);
            //todo  删除触发规则保存到sm_show_log表中，改为从sm_hospital表获取数据
        }
        logList = ruleService.add2ShowLog(rule, data, map);
        logger.info("提示信息结果为：{}", JSONObject.toJSONString(logList));
        resp.setData(logList);
        return resp;
    }

    /**
     * 规则匹配
     *
     * @param rule
     * @return 匹配规则返回结果数据
     */
    public String matchRule(Rule rule) {
        System.out.println(JSONObject.toJSONString(rule));
        String data = "";
        try {
            data = ruleService.ruleMatchGetResp(rule);
            logger.info("规则匹配返回结果为：{}", data);
        } catch (Exception e) {
            logger.info("规则匹配失败:{},请求数据为：{}", e.getMessage(), JSONObject.toJSONString(rule));
        }
        return data;
    }

}

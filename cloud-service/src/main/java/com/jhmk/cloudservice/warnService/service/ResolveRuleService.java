package com.jhmk.cloudservice.warnService.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudentity.earlywaring.entity.UserDataModelMapping;
import com.jhmk.cloudentity.earlywaring.entity.UserModel;
import com.jhmk.cloudentity.earlywaring.entity.repository.service.UserDataModelMappingRepService;
import com.jhmk.cloudentity.earlywaring.entity.repository.service.UserModelRepService;
import com.jhmk.cloudentity.earlywaring.entity.rule.AnalyzeBean;
import com.jhmk.cloudentity.earlywaring.entity.rule.FormatRule;
import com.jhmk.cloudentity.earlywaring.entity.rule.StandardRule;
import com.jhmk.cloudutil.config.BaseConstants;
import com.jhmk.cloudutil.util.MapUtil;
import com.jhmk.cloudutil.util.StringUtil;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ziyu.zhou
 * @date 2018/8/2 10:37
 */


/**
 * 解析规则
 */
@Service
public class ResolveRuleService {
    @Autowired
    UserDataModelMappingRepService userDataModelMappingRepService;
    @Autowired
    UserModelRepService userModelRepService;

    private static final String symbol = "[]";

    /**
     * 获取cdss规则解析
     * 用于标准规则 获取提示释义等信息
     */
    public FormatRule getStandardRule(String ruleStr) {
        JSONObject jObject = JSON.parseObject(ruleStr);
        Object result = jObject.get("result");
        FormatRule cdssRuleBean = null;
        if (ObjectUtils.anyNotNull(result) && !symbol.equals(result)) {
            Map resultMap = (Map) result;
            cdssRuleBean = MapUtil.map2Bean(resultMap, FormatRule.class);
        }
        return cdssRuleBean;
    }


    //获取大并列 条件信息
    public void anaOldRule(String str) {
        List<List<StandardRule>> basicStandardRule = getBasicStandardRule(str);
        String string = JSONObject.toJSONString(basicStandardRule);
        System.out.println(string);
    }


    public List<List<StandardRule>> getBasicStandardRule(String ruleStr) {
        List<List<StandardRule>> resultList = new LinkedList<>();
        //切分大并列
        String[] ands = ruleStr.split("\\)and\\(");
        for (String str : ands) {
            List<StandardRule> oneRule = getOneRule(str);
            resultList.add(oneRule);
        }
        return resultList;
    }

    //切分小并列 并生成bean的list
    private List<StandardRule> getOneRule(String str) {
        List<StandardRule> list = new LinkedList<>();
        String s = str.replaceAll("\\(", "").replaceAll("\\)", "");
        String[] ands = s.split("and");
        for (String oneStr : ands) {
            StandardRule rule = anaSyspol(oneStr);
            list.add(rule);
        }
        return list;
    }


    //获取rule bean 判断符号 获取关系
    private StandardRule anaSyspol(String str) {
        if (str.contains(BaseConstants.NCT)) {
            return getRule(str, BaseConstants.NCT);
        } else if (str.contains(BaseConstants.CT)) {
            return getRule(str, BaseConstants.CT);

        } else if (str.contains(BaseConstants.GTE)) {
            return getRule(str, BaseConstants.GTE);

        } else if (str.contains(BaseConstants.LTE)) {
            return getRule(str, BaseConstants.LTE);

        } else if (str.contains(BaseConstants.GT)) {
            return getRule(str, BaseConstants.GT);

        } else if (str.contains(BaseConstants.LT)) {
            return getRule(str, BaseConstants.LT);

        } else if (str.contains(BaseConstants.NEQ)) {
            return getRule(str, BaseConstants.NEQ);

        } else if (str.contains(BaseConstants.EQ)) {
            return getRule(str, BaseConstants.EQ);
        } else {
            System.out.println("解析错误");
            return null;
        }
    }


    /**
     * 根据条件 解析添加规则时 原始条件
     *
     * @param str
     * @return
     */
    public List<List<AnalyzeBean>> getOldRuleConditionList(String str) {
        List<List<AnalyzeBean>> resultList = new LinkedList<>();
        String delOutBracketStr = delOutBracket(str);
        List<String> list = anaBigJuxtaposition(delOutBracketStr);
        for (String condition : list) {
            List<AnalyzeBean> listAnalyzeBeanRule = getListAnalyzeBeanRule(condition);
            resultList.add(listAnalyzeBeanRule);
        }
        return resultList;
    }

    /**
     * 解析大并列
     *
     * @param bigStr ((1and2)and(3and4)and(5and6))
     * @return 1and2
     * 3and4
     * 5and6
     */
    public List<String> anaBigJuxtaposition(String bigStr) {
        String s = delOutBracket(bigStr);
        List<String> list = new LinkedList<>();
        String patt = "(?<=\\().*?(?=\\))";
        Pattern pt = Pattern.compile(patt);
        Matcher m1 = pt.matcher(s);
        while (m1.find()) {
            list.add(m1.group());
        }
        return list;
    }


    /**
     * 删除最外层括号
     *
     * @param s
     * @return
     */
    private String delOutBracket(String s) {
        String s1 = s.replaceAll("\\(\\(", "(").replaceAll("\\)\\)", ")");

        return s1;
    }

    /**
     * 将条件解析为添加时候 的信息
     * smallStr (住院病历诊断_诊断名称等于急性肺栓塞and住院病历诊断_诊断序号等于1)
     *
     * @return {"unit":"","flag":"1","field":"住院病历诊断_诊断名称","values":"急性肺栓塞","id":"54","type":"4","exp":"等于"},{"unit":"岁","flag":"1","field":"病案首页_基本信息_年龄","values":"2","id":"56","type":"0","exp":"等于"}
     */
    public List<AnalyzeBean> getListAnalyzeBeanRule(String smallStr) {
        List<AnalyzeBean> list = new LinkedList<>();
        String[] ands = smallStr.split("and");
        for (String cond : ands) {
            AnalyzeBean analyzeBean = anaSyspol2AnalyzeBean(cond);
            list.add(analyzeBean);
        }
        return list;
    }


    private AnalyzeBean anaSyspol2AnalyzeBean(String str) {
        if (str.contains(BaseConstants.NCT)) {
            return getAnalyzeBeanByCondition(str, BaseConstants.NCT);
        } else if (str.contains(BaseConstants.CT)) {
            return getAnalyzeBeanByCondition(str, BaseConstants.CT);

        } else if (str.contains(BaseConstants.GTE)) {
            return getAnalyzeBeanByCondition(str, BaseConstants.GTE);

        } else if (str.contains(BaseConstants.LTE)) {
            return getAnalyzeBeanByCondition(str, BaseConstants.LTE);

        } else if (str.contains(BaseConstants.GT)) {
            return getAnalyzeBeanByCondition(str, BaseConstants.GT);

        } else if (str.contains(BaseConstants.LT)) {
            return getAnalyzeBeanByCondition(str, BaseConstants.LT);

        } else if (str.contains(BaseConstants.NEQ)) {
            return getAnalyzeBeanByCondition(str, BaseConstants.NEQ);

        } else if (str.contains(BaseConstants.EQ)) {
            return getAnalyzeBeanByCondition(str, BaseConstants.EQ);
        } else {
            System.out.println("解析错误");
            return null;
        }
    }

    //生成单个bean
    private StandardRule getRule(String str, String sympol) {
        StandardRule rule = new StandardRule();
        String[] split = str.split(sympol);
        if (split.length == 2) {
            rule.setName(split[0]);
            rule.setSympol(sympol);
            rule.setStatus("0");
            String[] allSplit = split[1].split("/");
            rule.setStandardValue(allSplit[0]);
            List<String> clildName = new LinkedList<>();
            for (int i = 0; i < allSplit.length; i++) {
                if (i == 0) {
                    continue;
                }
                clildName.add(allSplit[i]);
            }
            rule.setChildValues(clildName);
            rule.setAllValues(Arrays.asList(split[1].split("/")));
        }
        return rule;
    }

    /**
     * @param str    条件信息 ex: 年龄等于22
     * @param sympol 符号 ex: 大于
     * @return
     */
    private AnalyzeBean getAnalyzeBeanByCondition(String str, String sympol) {
        AnalyzeBean rule = new AnalyzeBean();
        String[] split = str.split(sympol);
        if (split.length == 2) {
            //映射后字段 住院病历诊断_诊断名称
            String field = split[0];
            String value = split[1];
            UserDataModelMapping byUmNamePath = userDataModelMappingRepService.findByDmNamePath(field).get(0);
            //映射前字段名 临床诊断_诊断_诊断名称
            String oldField = byUmNamePath.getUmNamePath();
            String lastName = oldField.substring(oldField.lastIndexOf("_") + 1);
            List<UserModel> userModelList = userModelRepService.findByUmName(lastName);
            if (Objects.nonNull(userModelList)) {
                String umType = userModelList.get(0).getUmType();
                rule.setType(umType);
            }
            rule.setId(String.valueOf(byUmNamePath.getUdmmId()));
            rule.setFlag("1");
            if (ifNeedSplitValue(value)) {
                Map<String, String> map = splitValue(value);
                rule.setValues(map.get("value"));
                rule.setUnit(map.get("unit"));
            } else {
                rule.setValues(value);
            }
//            病案首页_基本信息_年龄

            rule.setField(oldField);
            rule.setExp(sympol);
        }
        return rule;
    }

    /**
     * 切分12岁 这种问题
     *
     * @return
     */
    public boolean ifNeedSplitValue(String value) {
        if (value.length() < 2) {
            return false;
        }

        if (value.lastIndexOf("小时") == value.length() - 2 && StringUtil.isInteger(value.substring(0, value.length() - 2))) {
            return true;
        } else if (value.lastIndexOf("天") == value.length() - 1 && StringUtil.isInteger(value.substring(0, value.length() - 1))) {
            return true;

        } else if (value.lastIndexOf("月") == value.length() - 1 && StringUtil.isInteger(value.substring(0, value.length() - 1))) {
            return true;

        } else if (value.lastIndexOf("日") == value.length() - 1 && StringUtil.isInteger(value.substring(0, value.length() - 1))) {
            return true;

        } else if (value.lastIndexOf("年") == value.length() - 1 && StringUtil.isInteger(value.substring(0, value.length() - 1))) {
            return true;

        } else if (value.lastIndexOf("岁") == value.length() - 1 && StringUtil.isInteger(value.substring(0, value.length() - 1))) {
            return true;
        }
        return false;
    }

    //切分数值和单位
    public static Map<String, String> splitValue(String value) {
        Map<String, String> map = new HashMap<>();
        if (value.contains("小时")) {
            map.put("value", value.substring(0, value.length() - 2));
            map.put("unit", value.substring(value.length() - 2));
            map.put(value.substring(0, value.length() - 2), value.substring(value.length() - 2));
        } else if (value.contains("天")) {
            map.put("value", value.substring(0, value.length() - 1));
            map.put("unit", value.substring(value.length() - 1));
        } else if (value.contains("月")) {
            map.put("value", value.substring(0, value.length() - 1));
            map.put("unit", value.substring(value.length() - 1));
        } else if (value.contains("日")) {

        } else if (value.contains("年")) {
            map.put("value", value.substring(0, value.length() - 1));
            map.put("unit", value.substring(value.length() - 1));
        } else if (value.contains("岁")) {
            map.put("value", value.substring(0, value.length() - 1));
            map.put("unit", value.substring(value.length() - 1));
        }
        return map;
    }

    public static void main(String[] args) {
        Map<String, String> map = splitValue("88岁");
    }
}

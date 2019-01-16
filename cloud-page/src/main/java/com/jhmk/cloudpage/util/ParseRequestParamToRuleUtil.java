package com.jhmk.cloudpage.util;

import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudentity.earlywaring.entity.rule.Rule;

public class ParseRequestParamToRuleUtil {
    public static Rule parseRequestParam(String s){
        //解析一诉五史
        JSONObject temp = JSONObject.parseObject(s);
        Rule rule = Rule.fill(temp);
        return rule;
    }
}

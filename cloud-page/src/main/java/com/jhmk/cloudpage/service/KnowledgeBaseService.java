package com.jhmk.cloudpage.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudutil.config.UrlConstants;
import com.jhmk.cloudutil.config.UrlPropertiesConfig;
import com.jhmk.cloudutil.util.DocumentUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ziyu.zhou
 * @date 2019/1/10 13:46
 * 知识库 service
 */
@Service
public class KnowledgeBaseService {
    @Autowired
    UrlPropertiesConfig urlPropertiesConfig;
    @Autowired
    DocumentUtil documentUtil;
    @Autowired
    RestTemplate restTemplate;

    Map<String, String> typeMap = new HashMap<>(4);

    @PostConstruct
    public void initMap() {
        typeMap.put("disease", "疾病");
        typeMap.put("medicine", "药品");
        typeMap.put("exam", "检查");
        typeMap.put("lab_sub", "检验细项");
    }

    public String getResultUrl(String map) {
        JSONObject object = JSONObject.parseObject(map);
        String name = object.getString("name");
        String type = object.getString("type");
        String value = object.getString("value");
        String standardFromAlias = documentUtil.getStandardFromAlias(value);
        String condition = getCondition(name, type, standardFromAlias);
        Object parse = JSONObject.parse(condition);
        String s = restTemplate.postForObject(urlPropertiesConfig.getCdssurl() + UrlConstants.getWikiInfoByExpress, parse, String.class);
        String firsetId = getFirsetId(s);
        if (StringUtils.isNotEmpty(firsetId)) {
            if (typeMap.containsKey(type)) {
                String chinaType = typeMap.get(type);
                String resultUrl = getUrl(standardFromAlias, firsetId, chinaType);
                //网址不识别#  自动转成%23
                return resultUrl.replaceAll("#", "%23");
            }
        }
        String resultUrl = getUrl(standardFromAlias);
        return resultUrl.replaceAll("#", "%23");
    }


    /**
     * http://192.168.8.31:8877/detail.html?keywords=%E8%A1%80%E9%92%BE&thkey=%E8%A1%80%E9%92%BE&id=lab_sub%23%23%23EfrRJzXMRHraDacVzypdtC&type=%E6%A3%80%E9%AA%8C%E7%BB%86%E9%A1%B9
     *
     * @param keywords 关键字
     * @param id       唯一id
     * @param type     类型 检验、检查、疾病
     * @return
     */
    public String getUrl(String keywords, String id, String type) {
        StringBuilder sb = new StringBuilder(urlPropertiesConfig.getKnowbasePageurl() + "/detail.html?keywords=");
        sb.append(keywords)
                .append("&thkey=")
                .append(keywords)
                .append("&id=")
                .append(id)
                .append("&type=")
                .append(type);
        return sb.toString();
    }

    /**
     * 没有查到唯一id  通用地址url
     *
     * @param keywords
     * @return
     */
    public String getUrl(String keywords) {
        StringBuilder sb = new StringBuilder(urlPropertiesConfig.getKnowbasePageurl() + "/detail.html?keywords=");
        sb.append(keywords)
                .append("&filterField=");
        return sb.toString();
    }

    public String getFirsetId(String s) {
        if (StringUtils.isNotBlank(s)) {
            JSONObject objectS = JSONObject.parseObject(s);
            JSONArray result = objectS.getJSONArray("result");
            if (result != null && result.size() > 0) {
                Map<String, String> idMap = (Map) result.get(0);
                String id = idMap.get("id");
                return id;
            } else {
                return null;
            }
        }
        return null;
    }


    public String getCondition(String name, String type, String value) {
        StringBuilder sb = new StringBuilder();
        sb.append("{")
                .append("\"flag\":\"or\",,")
                .append("\"size\": 100,")
                .append("\"fields\": [{")
                .append(" \"name\": \"" + name + "\",")
                .append(" \"type\": \"" + type + "\",")
                .append("\"value\": \"" + value + "\"")
                .append("}")
                .append("],")
                .append("\"resultField\":[\"reference_obj.literature_name\", \"reference_obj.full_text_indicator\"]}");
        return sb.toString();
    }

}

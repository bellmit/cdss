package com.jhmk.cloudpage.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudentity.base.BaseController;
import com.jhmk.cloudentity.page.bean.ClickRate;
import com.jhmk.cloudpage.demo.Demo;
import com.jhmk.cloudpage.demo.FrontHitEvent;
import com.jhmk.cloudpage.service.KnowledgeBaseService;
import com.jhmk.cloudpage.util.ReadFileService;
import com.jhmk.cloudservice.cdssPageService.ReadFile;
import com.jhmk.cloudutil.config.UrlConstants;
import com.jhmk.cloudutil.config.UrlPropertiesConfig;
import com.jhmk.cloudutil.model.AtResponse;
import com.jhmk.cloudutil.model.ResponseCode;
import com.jhmk.cloudutil.util.DocumentUtil;
import com.jhmk.cloudutil.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author ziyu.zhou
 * @date 2018/8/31 18:54
 */
@Controller
@RequestMapping("/demo")
public class DemoController extends BaseController {
    @Autowired
    KnowledgeBaseService knowledgeBaseService;
    @Autowired
    UrlPropertiesConfig urlPropertiesConfig;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    DocumentUtil documentUtil;
    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping("/test1")
    public AtResponse demo() {
        AtResponse resp = new AtResponse(System.currentTimeMillis());
        resp.setData("www.baidu.com");
        resp.setResponseCode(ResponseCode.OK);
        return resp;
    }

    @RequestMapping("/testtwo")
    @ResponseBody
    public AtResponse demo2() {
        AtResponse resp = new AtResponse(System.currentTimeMillis());
        String s = "1234";
        resp.setData(s);
        return resp;
    }

    @RequestMapping("/test2")
    public void demo2(HttpServletResponse response, @RequestBody String map) {
        FrontHitEvent event = new FrontHitEvent(map);
        applicationContext.publishEvent(event);
        System.out.println("事件发布成功");
    }

    /**
     * 获取标准名 和icd
     */
    @GetMapping("/getStandardNameAndICD")
    public void getStandardNameAndICD() {
        List<String> list = ReadFileService.readSourceList("temp");
        Set<Demo> resultList = new HashSet<>();
        for (String illName : list) {
            boolean flag = false;
            Demo demo = new Demo();
            demo.setIllName(illName);
            String standardFromAlias = documentUtil.getStandardFromAlias(illName);
            demo.setStandardName(standardFromAlias);
            String condition = "{\"flag\":\"and\",\"size\":1,\"fields\":[{\"name\":\"disease\",\"type\":\"disease\",\"value\":\"" + standardFromAlias + "\"}],\"resultField\":[\"disease_obj.disease_name\",\"disease_obj.disease_code\"]}";
            Object parse = JSONObject.parse(condition);
            String s = restTemplate.postForObject(urlPropertiesConfig.getCdssurl() + UrlConstants.getWikiInfoByExpress, parse, String.class);
            if (StringUtils.isNotBlank(s)) {
                JSONObject object = JSONObject.parseObject(s);
                if (Objects.nonNull(object)) {
                    JSONArray result = object.getJSONArray("result");
                    if (result != null && result.size() > 0) {
                        Object o = result.get(0);
                        Map o1 = (Map) o;
                        Object disease_obj = o1.get("disease_obj");
                        if (disease_obj != null) {
                            Map disease_obj1 = (Map) disease_obj;
                            if (disease_obj1 != null) {
                                Object disease_code = disease_obj1.get("disease_code");
                                if (disease_code != null) {
                                    JSONArray jsonArray = JSONObject.parseArray(disease_code.toString());
                                    Iterator<Object> iterator = jsonArray.iterator();
                                    while (iterator.hasNext()) {
                                        Object next = iterator.next();
                                        Map<String, String> nextMap = (Map) next;
                                        String s1 = nextMap.get("disease_code_edition");
                                        if ("北京临床版ICD-10 V6.01".equals(s1)) {
                                            String icd = nextMap.get("disease_code_value");
                                            demo.setIcd(icd);
                                            resultList.add(demo);
                                            flag = true;
                                            continue;
                                        }

                                    }
                                    if (!flag) {
                                        resultList.add(demo);
                                    }
                                } else {
                                    resultList.add(demo);

                                }
                            } else {
                                resultList.add(demo);

                            }

                        } else {
                            resultList.add(demo);
                        }
                    } else {
                        resultList.add(demo);
                    }
                } else {
                    resultList.add(demo);
                }
            } else {
                resultList.add(demo);
            }
        }
        for (Demo demo : resultList) {
            System.out.println(demo.getIllName() + "&" + demo.getStandardName() + "&" + demo.getIcd());
        }
    }
}

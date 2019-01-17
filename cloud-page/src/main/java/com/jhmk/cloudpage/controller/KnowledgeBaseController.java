package com.jhmk.cloudpage.controller;

import com.jhmk.cloudentity.base.BaseController;
import com.jhmk.cloudservice.cdssPageService.KnowledgeBaseService;
import com.jhmk.cloudutil.config.UrlPropertiesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;

/**
 * @author ziyu.zhou
 * @date 2019/1/9 18:39
 * 知识库控制层
 */
@Controller
@RequestMapping("/knowledgeBase")
public class KnowledgeBaseController extends BaseController {
    @Autowired
    KnowledgeBaseService knowledgeBaseService;
    @Autowired
    UrlPropertiesConfig urlPropertiesConfig;
    @Autowired
    RestTemplate restTemplate;

    /**
     * 肿瘤 获取知识库地址
     * @param response
     * @param map
     */

    @PostMapping("/getWikiInfoByExpress")
    public void getWikiInfoByExpress(HttpServletResponse response, @RequestBody String map) {
        String url = knowledgeBaseService.getResultUrl(map);
        System.out.println(url);
        wirte(response, url);
    }


//    wirte(response, s);


}

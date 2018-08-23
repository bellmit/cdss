package com.jhmk.cloudpage.controller;

/**
 * @author ziyu.zhou
 * @date 2018/8/23 9:57
 */

import com.jhmk.cloudentity.base.BaseController;
import com.jhmk.cloudentity.cdss.pojo.repository.service.SysDiseasesRepService;
import com.jhmk.cloudpage.service.CdssRunRuleService;
import com.jhmk.cloudpage.service.CdssService;
import com.jhmk.cloudpage.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

/**
 * cdss小界面控制层
 */
@Controller
@RequestMapping("/page/cdss")
public class CdssPageController extends BaseController {
    Logger logger = LoggerFactory.getLogger(CdssPageController.class);


    @Autowired
    SysDiseasesRepService sysDiseasesRepository;
    @Autowired

    CdssService cdssService;
    @Autowired
    TestService testService;
    @Autowired
    CdssRunRuleService cdssRunRuleService;
    @Autowired
    RestTemplate restTemplate;


    public void test(){

    }
}

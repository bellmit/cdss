package com.jhmk.cloudpage.controller;

import com.jhmk.cloudentity.base.BaseController;
import com.jhmk.cloudentity.page.service.ClickRateRepService;
import com.jhmk.cloudpage.service.SchedulerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ziyu.zhou
 * @date 2018/8/31 14:46
 */
@Controller
@RequestMapping("/page/task")
public class TaskController extends BaseController {
    Logger logger = LoggerFactory.getLogger(ClickRateController.class);

    @Autowired
    SchedulerTask schedulerTask;

    @PostMapping("/addClickCount2DateTable")
    public void addClickCount2DateTable() {

        schedulerTask.addClickCount2DateTable();
        logger.info("记录点击事件完成");
    }

}

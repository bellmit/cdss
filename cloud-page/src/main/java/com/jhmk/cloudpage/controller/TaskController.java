package com.jhmk.cloudpage.controller;

import com.jhmk.cloudentity.base.BaseController;
import com.jhmk.cloudentity.earlywaring.entity.SmDepts;
import com.jhmk.cloudentity.earlywaring.entity.SmUsers;
import com.jhmk.cloudentity.earlywaring.entity.repository.service.SmDeptsRepService;
import com.jhmk.cloudentity.earlywaring.entity.repository.service.SmUsersRepService;
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
@RequestMapping("/task")
public class TaskController extends BaseController {
    Logger logger = LoggerFactory.getLogger(ClickRateController.class);

    @Autowired
    SmUsersRepService smUsersRepService;
    @Autowired
    SmDeptsRepService smDeptsRepService;
    @Autowired
    SchedulerTask schedulerTask;

    @PostMapping("/addClickCount2DateTable")
    public void addClickCount2DateTable() {
        schedulerTask.addClickCount2DateTable();
        logger.info("记录点击事件完成");
    }
//    @PostMapping("/addClickRate2DataTable")
//    public void addClickRate2DataTable() {
//
//        schedulerTask.addClickCount2DateTable();
//        logger.info("记录点击事件完成");
//    }

    @PostMapping("/test")
    public void contextLoads() {
        SmUsers one = smUsersRepService.findOne("10401");
        //科室code码
        if (one != null) {
            String deptCode = one.getUserDept();
            SmDepts firstByDeptCode = smDeptsRepService.findFirstByDeptCode(deptCode);
            String deptName = firstByDeptCode.getDeptName();
            System.out.println(deptName);
        }
    }
}

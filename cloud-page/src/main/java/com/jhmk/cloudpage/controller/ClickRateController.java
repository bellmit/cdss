package com.jhmk.cloudpage.controller;

import com.jhmk.cloudentity.base.BaseController;
import com.jhmk.cloudentity.page.bean.ClickRate;
import com.jhmk.cloudentity.page.service.ClickRateRepService;
import com.jhmk.cloudpage.service.ClickRateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @author ziyu.zhou
 * @date 2018/8/29 16:18
 */
@Controller
@RequestMapping("/page/clickrate")
public class ClickRateController extends BaseController {
    Logger logger = LoggerFactory.getLogger(ClickRateController.class);

    @Autowired
    ClickRateRepService clickRateRepService;

    /**
     * 统计cdss小界面点击量
     */
    @PostMapping("/statisticsClickCount")
    public void statisticsClickCount(HttpServletResponse response, @RequestBody ClickRate clickRate) {
        String type = clickRate.getType();
        String doctorId = clickRate.getDoctorId();
        Date date = new Date();
        java.sql.Date sdate = new java.sql.Date(date.getTime());
        ClickRate oldBean = clickRateRepService.findByDoctorIdAndCreateTimeAndType(doctorId, sdate, type);
        if (oldBean != null) {
            oldBean.setCount(oldBean.getCount() + 1);
            clickRateRepService.save(oldBean);
        } else {
            clickRate.setCount(1);
            clickRateRepService.save(clickRate);
        }
    }


    @PostMapping("/add")
    public void add(HttpServletResponse response, @RequestBody ClickRate clickRate) {
        ClickRateService.addDate2Map(clickRate);
    }
    @PostMapping("/show")
    public void showDate(){
        Map<String, Integer> clickRateMap = ClickRateService.clickRateMap;
        System.out.println(clickRateMap.toString());
    }
}

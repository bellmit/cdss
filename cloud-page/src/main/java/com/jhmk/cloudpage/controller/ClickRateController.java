package com.jhmk.cloudpage.controller;

import com.jhmk.cloudentity.base.BaseController;
import com.jhmk.cloudentity.page.bean.ClickRate;
import com.jhmk.cloudentity.page.service.ClickRateRepService;
import com.jhmk.cloudpage.service.ClickRateService;
import com.jhmk.cloudutil.model.AtResponse;
import com.jhmk.cloudutil.model.ResponseCode;
import com.jhmk.cloudutil.util.DateFormatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
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
        String format = DateFormatUtil.format(date, DateFormatUtil.DATE_PATTERN_S);
        ClickRate oldBean = clickRateRepService.findByDoctorIdAndCreateTimeAndType(doctorId, format, type);
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
        String format = DateFormatUtil.format(new Date(), DateFormatUtil.DATE_PATTERN_S);
        clickRate.setCreateTime(format);
        clickRate.setSubmitTime(new Timestamp(System.currentTimeMillis()));
        ClickRateService.addDate2Map(clickRate);
        AtResponse resp = new AtResponse();
        resp.setResponseCode(ResponseCode.OK);
        wirte(response, resp);
    }

    @PostMapping("/show")
    public void showDate() {

    }
}

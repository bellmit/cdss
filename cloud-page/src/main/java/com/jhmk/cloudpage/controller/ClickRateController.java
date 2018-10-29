package com.jhmk.cloudpage.controller;

import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudentity.base.BaseController;
import com.jhmk.cloudentity.base.BaseEntityController;
import com.jhmk.cloudentity.earlywaring.entity.SmDepts;
import com.jhmk.cloudentity.earlywaring.entity.SmUsers;
import com.jhmk.cloudentity.earlywaring.entity.repository.service.SmDeptsRepService;
import com.jhmk.cloudentity.earlywaring.entity.repository.service.SmUsersRepService;
import com.jhmk.cloudentity.page.bean.ClickRate;
import com.jhmk.cloudentity.page.service.ClickRateRepService;
import com.jhmk.cloudpage.service.ClickRateService;
import com.jhmk.cloudutil.model.AtResponse;
import com.jhmk.cloudutil.model.ResponseCode;
import com.jhmk.cloudutil.util.DateFormatUtil;
import org.apache.commons.lang3.StringUtils;
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
import java.util.*;

/**
 * @author ziyu.zhou
 * @date 2018/8/29 16:18
 */
@Controller
@RequestMapping("/page/clickrate")
public class ClickRateController extends BaseEntityController<ClickRate> {
    Logger logger = LoggerFactory.getLogger(ClickRateController.class);

    @Autowired
    SmDeptsRepService smDeptsRepService;
    @Autowired
    SmUsersRepService smUsersRepService;
    @Autowired
    ClickRateRepService clickRateRepService;


    @PostMapping("/add")
    public void add(HttpServletResponse response, @RequestBody ClickRate clickRate) {
        clickRate.setCreateTime(new java.sql.Date(System.currentTimeMillis()));
        ClickRateService.addDate2Map(clickRate);
        AtResponse resp = new AtResponse();
        resp.setResponseCode(ResponseCode.OK);
        wirte(response, resp);
    }


    @PostMapping("/view")
    public void showDate(HttpServletResponse response, @RequestBody(required = false) String map) {
        List<ClickRate> dataByCondition = null;
        if (StringUtils.isNotBlank(map)) {
            JSONObject jsonObject = JSONObject.parseObject(map);
            Date startTime = jsonObject.getDate("startTime");
            Date endTime = jsonObject.getDate("endTime");
            String deptCode = jsonObject.getString("deptCode");
            Map<String, Object> param = null;
            if (StringUtils.isNotBlank(deptCode)) {
                param = new HashMap<>();
                param.put("deptCode", deptCode);
            }
            dataByCondition = clickRateRepService.getDataByCondition(startTime, endTime, param);
        } else {
            dataByCondition = clickRateRepService.getDataByCondition(null, null, null);
        }
        Map<String, Integer> params = new HashMap<>();
        for (ClickRate clickRate : dataByCondition) {
            String type = clickRate.getType();
            int count = clickRate.getCount();
            if (params.containsKey(type)) {
                params.put(type, params.get(type) + count);
            } else {
                params.put(type, count);
            }
        }
        AtResponse resp = new AtResponse();
        resp.setData(params);
        resp.setResponseCode(ResponseCode.OK);
        wirte(response, resp);
    }

    @PostMapping("/getDeptMap")
    public void getDeptMap(HttpServletResponse response) {
        Map<String, String> result = new HashMap<>();
        AtResponse resp = new AtResponse();
        List<String> distinctDoctorId = clickRateRepService.getDistinctDoctorId();
        for (String doctor_id : distinctDoctorId) {
            SmUsers one = smUsersRepService.findOne(doctor_id);
            if (one != null) {
                String deptId = one.getUserDept();
                if (StringUtils.isNotBlank(deptId)) {
                    SmDepts firstByDeptCode = smDeptsRepService.findFirstByDeptCode(deptId);
                    if (Objects.nonNull(firstByDeptCode)) {
                        result.put(firstByDeptCode.getDeptName(), firstByDeptCode.getDeptCode());
                    }
                }
            }
        }
        resp.setResponseCode(ResponseCode.OK);
        resp.setData(result);
        wirte(response, resp);
    }


    @PostMapping("/getClickRateByCondition")
    public void getAllData(HttpServletResponse response, @RequestBody(required = false) Map<String, Object> map) {
        AtResponse<Map<String, Object>> resp = super.listData(map, clickRateRepService, "createTime");
        resp.setResponseCode(ResponseCode.OK);
        wirte(response, resp);
    }

}

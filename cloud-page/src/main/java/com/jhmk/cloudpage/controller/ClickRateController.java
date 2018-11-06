package com.jhmk.cloudpage.controller;

import com.alibaba.fastjson.JSON;
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
import com.jhmk.cloudutil.util.CompareUtil;
import com.jhmk.cloudutil.util.DateFormatUtil;
import com.jhmk.cloudutil.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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


    /**
     * 条件查询
     *
     * @param response
     * @param params
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public void roleList(HttpServletResponse response, @RequestBody String params) {
        Map<String, Object> parse = (Map) JSON.parse(params);
        AtResponse<Map<String, Object>> resp = super.listData(parse, clickRateRepService, "createTime");
        wirte(response, resp);
    }

    @PostMapping("/view")
    public void showDate(HttpServletResponse response, @RequestBody(required = false) String map) {
        Map<String, Object> resultMap = new HashMap<>();
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
        Map<String, Integer> deptParams = new HashMap<>();
        Map<String, Integer> typeParams = new HashMap<>();
        for (ClickRate clickRate : dataByCondition) {
            String type = clickRate.getType();
            String deptName = clickRate.getDeptName();
            int count = clickRate.getCount();
            if (typeParams.containsKey(type)) {
                typeParams.put(type, typeParams.get(type) + count);
            } else {
                typeParams.put(type, count);
            }
            if (deptParams.containsKey(deptName)) {
                deptParams.put(deptName, deptParams.get(deptName) + count);
            } else {
                deptParams.put(deptName, count);
            }
        }
        Map<String, Integer> map1 = CompareUtil.compareMapValue(typeParams);
        Map<String, Integer> map2 = CompareUtil.compareMapValue(deptParams);
        resultMap.put("dept", map1);
        resultMap.put("type", map2);
        AtResponse resp = new AtResponse();
        resp.setData(resultMap);
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


//    @PostMapping("/getClickRateByCondition")
//    public void getAllData(HttpServletResponse response, @RequestBody(required = false) Map<String, Object> map) {
//        AtResponse<Map<String, Object>> resp = super.listData(map, clickRateRepService, "createTime");
//        resp.setResponseCode(ResponseCode.OK);
//        wirte(response, resp);
//    }



    @PostMapping("/getClickRateByCondition")
    public void getClickRateByCondition(HttpServletResponse response, @RequestBody(required = false) String map) {
        Map<String, Object> result = new HashMap<>();
        List<ClickRate> dataByCondition = null;
        if (StringUtils.isNotBlank(map)) {
            JSONObject jsonObject = JSONObject.parseObject(map);
            Date startTime = jsonObject.getDate("startTime");
            Date endTime = jsonObject.getDate("endTime");
            String deptCode = jsonObject.getString("deptCode");
            int page = jsonObject.getInteger("page");
            int pageSize = jsonObject.getInteger("deptCode");
            Map<String, Object> param = null;
            if (StringUtils.isNotBlank(deptCode)) {
                param = new HashMap<>();
                param.put("deptCode", deptCode);
            }
            dataByCondition = clickRateRepService.getDataByCondition(startTime, endTime, param);
        } else {
            dataByCondition = clickRateRepService.getDataByCondition(null, null, null);
        }
        Map<String, Integer> typeParams = new HashMap<>();
        Map<String, Integer> deptParams = new HashMap<>();
        for (ClickRate clickRate : dataByCondition) {
            String type = clickRate.getType();
            String deptName = clickRate.getDeptName();

            int count = clickRate.getCount();
            if (StringUtils.isNotBlank(type)) {

                if (typeParams.containsKey(type)) {
                    typeParams.put(type, typeParams.get(type) + count);
                } else {
                    typeParams.put(type, count);
                }
            }
            if (StringUtils.isNotBlank(deptName)) {
                if (deptParams.containsKey(deptName)) {
                    deptParams.put(deptName, deptParams.get(deptName) + count);
                } else {
                    deptParams.put(deptName, count);
                }
            }
        }

        List<String> distinctDoctorId = clickRateRepService.getDistinctDoctorId();
        Map<String, Integer> map1 = CompareUtil.compareMapValue(typeParams);
        Map<String, Integer> map2 = CompareUtil.compareMapValue(deptParams);
        result.put("type", map1);
        result.put("dept", map2);
        result.put("count", distinctDoctorId.size());
        AtResponse resp = new AtResponse();
        resp.setData(JSONObject.toJSON(result));
        resp.setResponseCode(ResponseCode.OK);
        wirte(response, resp);
    }

//    @PostMapping("/getClickRateByType")
//    public void getClickRateByType(HttpServletResponse response, @RequestBody(required = false) String map) {
//        List<ClickRate> dataByCondition = null;
//        if (StringUtils.isNotBlank(map)) {
//            JSONObject jsonObject = JSONObject.parseObject(map);
//            Date startTime = jsonObject.getDate("startTime");
//            Date endTime = jsonObject.getDate("endTime");
//            String deptCode = jsonObject.getString("deptCode");
//            Map<String, Object> param = null;
//            if (StringUtils.isNotBlank(deptCode)) {
//                param = new HashMap<>();
//                param.put("deptCode", deptCode);
//            }
//            dataByCondition = clickRateRepService.getDataByCondition(startTime, endTime, param);
//        } else {
//            dataByCondition = clickRateRepService.getDataByCondition(null, null, null);
//        }
//        Map<String, Integer> typeParams = new HashMap<>();
//        for (ClickRate clickRate : dataByCondition) {
//            String type = clickRate.getType();
//            int count = clickRate.getCount();
//            if (typeParams.containsKey(type)) {
//                typeParams.put(type, typeParams.get(type) + count);
//            } else {
//                typeParams.put(type, count);
//            }
//
//        }
//        Map<String, Integer> map1 = CompareUtil.compareMapValue(typeParams);
//        AtResponse resp = new AtResponse();
//        resp.setData(map1);
//        resp.setResponseCode(ResponseCode.OK);
//        wirte(response, resp);
//    }
//
//    @PostMapping("/getClickRateByDept")
//    public void getClickRateByDept(HttpServletResponse response, @RequestBody(required = false) String map) {
//        List<ClickRate> dataByCondition = null;
//        if (StringUtils.isNotBlank(map)) {
//            JSONObject jsonObject = JSONObject.parseObject(map);
//            Date startTime = jsonObject.getDate("startTime");
//            Date endTime = jsonObject.getDate("endTime");
//            String deptCode = jsonObject.getString("deptCode");
//            Map<String, Object> param = null;
//            if (StringUtils.isNotBlank(deptCode)) {
//                param = new HashMap<>();
//                param.put("deptCode", deptCode);
//            }
//            dataByCondition = clickRateRepService.getDataByCondition(startTime, endTime, param);
//        } else {
//            dataByCondition = clickRateRepService.getDataByCondition(null, null, null);
//        }
//        Map<String, Integer> deptParams = new HashMap<>();
//        for (ClickRate clickRate : dataByCondition) {
//            String deptName = clickRate.getDeptName();
//            int count = clickRate.getCount();
//            if (deptParams.containsKey(deptName)) {
//                deptParams.put(deptName, deptParams.get(deptName) + count);
//            } else {
//                deptParams.put(deptName, count);
//            }
//        }
//        Map<String, Integer> map2 = CompareUtil.compareMapValue(deptParams);
//        AtResponse resp = new AtResponse();
//        resp.setData(map2);
//        resp.setResponseCode(ResponseCode.OK);
//        wirte(response, resp);
//    }

}

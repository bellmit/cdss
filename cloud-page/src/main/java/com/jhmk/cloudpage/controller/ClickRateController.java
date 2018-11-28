package com.jhmk.cloudpage.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
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
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
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
    ClickRateService clickRateService;
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


    @PostMapping("/addList")
    public void addList(HttpServletResponse response, @RequestBody ClickRate clickRate) {
        clickRate.setCreateTime(new Date());
        ClickRateService.addDate2List(clickRate);
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
        AtResponse<Map<String, Object>> resp = super.listDataByMap(parse, clickRateRepService, "createTime");
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


    /**
     * 查询科室数量和user数量（总）
     *
     * @param response
     */
    @PostMapping("/getClickRateAllCount")
    public void getClickRateCount(HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        List<String> distinctDoctorId1 = clickRateRepService.getDistinctDoctorId();
        List<String> distinctByDeptCode = clickRateRepService.getDistinctByDeptCode();
        result.put("dept", distinctByDeptCode.size());
        result.put("id", distinctDoctorId1.size());
        AtResponse resp = new AtResponse();
        resp.setData(result);
        resp.setResponseCode(ResponseCode.OK);
        wirte(response, resp);
    }


    /**
     * 获取折线图
     */
    @PostMapping("/getLineByCondition")
    public void getLineByCondition(HttpServletResponse response, @RequestBody(required = false) String map) {
        AtResponse resp = new AtResponse();
        Map<String, Integer> result = new HashMap<>();
        List<ClickRate> dataByCondition = null;
        JSONObject jsonObject = JSONObject.parseObject(map);
        //yyyy-MM-dd 格式
        Date startTime = jsonObject.getDate("startTime");
        Date endTime = jsonObject.getDate("endTime");
        if (StringUtils.isNotBlank(map)) {
            String deptCode = jsonObject.getString("deptCode");
            Map<String, Object> param = null;
            if (StringUtils.isNotBlank(deptCode)) {
                param = new HashMap<>();
                param.put("deptCode", deptCode);
            }
            dataByCondition = clickRateRepService.getDataByCondition(startTime, endTime, param);
        } else {
            dataByCondition = clickRateRepService.getDataByCondition(startTime, endTime, null);
        }
        List<String> betweenStringDate = DateFormatUtil.getBetweenStringDate(startTime, endTime);
        betweenStringDate.forEach(s -> result.put(s, 0));
        for (ClickRate bean : dataByCondition) {
            String format = DateFormatUtil.format(bean.getCreateTime(), DateFormatUtil.DATE_PATTERN_S);
            result.put(format, result.get(format) + bean.getCount());
        }
        ArrayList<Map.Entry<String, Integer>> entries = new ArrayList<>(result.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                Date date1 = DateFormatUtil.parseDateBySdf(o1.getKey(), DateFormatUtil.DATE_PATTERN_S);
                Date date2 = DateFormatUtil.parseDateBySdf(o2.getKey(), DateFormatUtil.DATE_PATTERN_S);
                return date1.compareTo(date2);
            }
        });
        resp.setData(entries);
        resp.setResponseCode(ResponseCode.OK);
        wirte(response, resp);
    }


    /**
     * 横坐标医生 纵坐标科室
     *
     * @param response
     * @param map
     */
    @PostMapping("/getDoctorAndCountByCondition")
    public void getDoctorAndCountByCondition(HttpServletResponse response, @RequestBody(required = false) String map) {
        AtResponse resp = new AtResponse();
        String allCount = "总数";//计算总数
        //第一个key  医生id  第二个map key代表电机类型 value 点击次数
        Map<String, Map<String, Integer>> resultMap = new HashMap<>();
        List<ClickRate> dataByCondition = null;
        JSONObject jsonObject = JSONObject.parseObject(map);
        //yyyy-MM-dd 格式
        Date startTime = jsonObject.getDate("startTime");
        Date endTime = jsonObject.getDate("endTime");
        if (StringUtils.isNotBlank(map)) {
            String deptCode = jsonObject.getString("deptCode");
            Map<String, Object> param = null;
            if (StringUtils.isNotBlank(deptCode)) {
                param = new HashMap<>();
                param.put("deptCode", deptCode);
            }
            dataByCondition = clickRateRepService.getDataByCondition(startTime, endTime, param);
        } else {
            dataByCondition = clickRateRepService.getDataByCondition(startTime, endTime, null);
        }
        for (ClickRate bean : dataByCondition) {
            String doctorId = bean.getDoctorId();

            String type = bean.getType();
            int clickCount = bean.getCount();
            if (resultMap.containsKey(doctorId)) {
                Map<String, Integer> childMap = resultMap.get(doctorId);
                childMap.put(type, childMap.get(type) + clickCount);
                childMap.put(allCount, childMap.get(allCount) + clickCount);
                resultMap.put(doctorId, childMap);
            } else {
                Map<String, Integer> childMap = clickRateService.initChildMap();
                childMap.put(type, clickCount);
                childMap.put(allCount, clickCount);
                resultMap.put(doctorId, childMap);
            }
        }
        ArrayList<Map.Entry<String, Map<String, Integer>>> entries = new ArrayList<>(resultMap.entrySet());
        Collections.sort(entries, new Comparator<Map.Entry<String, Map<String, Integer>>>() {
            @Override
            public int compare(Map.Entry<String, Map<String, Integer>> o1, Map.Entry<String, Map<String, Integer>> o2) {
                Map<String, Integer> value = o1.getValue();
                Integer integer = value.get(allCount);
                Map<String, Integer> value2 = o2.getValue();
                Integer integer2 = value2.get(allCount);
                return integer2.compareTo(integer);
            }
        });

        resp.setData(entries);
        resp.setResponseCode(ResponseCode.OK);
        wirte(response, resp);
    }


    /**
     * 获取柱状图 医嘱点击数量排序
     */
    @PostMapping("/getDoctorCountByCondition")
    public void getDoctorCountByCondition(HttpServletResponse response, @RequestBody(required = false) String map) {
        Map<String, Object> result = new HashMap<>();
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

}

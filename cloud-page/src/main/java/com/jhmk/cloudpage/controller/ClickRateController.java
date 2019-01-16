package com.jhmk.cloudpage.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudentity.base.BaseEntityController;
import com.jhmk.cloudentity.earlywaring.entity.SmDepts;
import com.jhmk.cloudentity.earlywaring.entity.SmUsers;
import com.jhmk.cloudentity.earlywaring.entity.repository.service.SmDeptsRepService;
import com.jhmk.cloudentity.earlywaring.entity.repository.service.SmUsersRepService;
import com.jhmk.cloudentity.page.bean.ClickRate;
import com.jhmk.cloudentity.page.bean.repository.service.ClickRateRepService;
import com.jhmk.cloudpage.service.ClickRateService;
import com.jhmk.cloudutil.config.UrlConstants;
import com.jhmk.cloudutil.config.UrlPropertiesConfig;
import com.jhmk.cloudutil.model.AtResponse;
import com.jhmk.cloudutil.model.ResponseCode;
import com.jhmk.cloudutil.model.WebPage;
import com.jhmk.cloudutil.util.CompareUtil;
import com.jhmk.cloudutil.util.DateFormatUtil;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author ziyu.zhou
 * @date 2018/8/29 16:18
 */
@Controller
@RequestMapping("/clickrate")
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
    @Autowired
    UrlPropertiesConfig urlPropertiesConfig;
    @Autowired
    RestTemplate restTemplate;

    @PostMapping("/test")
    public void test() {
        Map<String, String> param = new HashMap<>();
        param.put("diseaseName", "高血压");
        Object parse1 = JSONObject.toJSON(param);
        String sames = restTemplate.postForObject(urlPropertiesConfig.getCdssurl() + UrlConstants.getSamilarWord, parse1, String.class);
        System.out.println(sames);
    }

    @PostMapping("/add")
    public void add(HttpServletResponse response, @RequestBody ClickRate clickRate) {
        clickRate.setCreateTime(new java.sql.Date(System.currentTimeMillis()));
        ClickRateService.addDate2Map(clickRate);
        AtResponse resp = new AtResponse();
        resp.setResponseCode(ResponseCode.OK);
        wirte(response, resp);
    }

    /**
     * 添加详细医生操作日志信息
     *
     * @param response
     * @param clickRate
     */
    @PostMapping("/addParticularLog")

    @ApiOperation(value = "添加详细医生操作日志信息", notes = "请求demo：{{\"type\":\"\",\"doctorId\":\"10401\",\"patientId\":\"3123\",\"visitId\":\"3\",\"deptCode\":\"3123\",\"deptName\":\"心血管科\",\"diagnosisCode\":\"疾病编码\",\"diagnosisName\":\"高血压\"}}",
            httpMethod = "POST", responseContainer = "Map")
    @ApiResponses({@ApiResponse(code = 200, message = "成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clickRate", value = "请求参数", required = true, paramType = "body")
    })
    public void addParticularLog(HttpServletResponse response, @RequestBody ClickRate clickRate) {
        //点击时间
        clickRate.setCreateTime(new Date());
        clickRateRepService.save(clickRate);
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
     * @param response //     * @param params
     */
    @RequestMapping(value = "/list")
    public void roleList(HttpServletResponse response, @RequestBody String map) {
        AtResponse resp = new AtResponse(System.currentTimeMillis());
        Map<String, Object> parse = (Map) JSON.parse(map);
        List<ClickRate> resultList = new ArrayList<>();
        List<ClickRate> dataByCondition = null;
        int page = 0;
        if (StringUtils.isNotBlank(map)) {
            JSONObject jsonObject = JSONObject.parseObject(map);
            Date startTime = jsonObject.getDate("startTime");
            Date endTime = jsonObject.getDate("endTime");
            String deptCode = jsonObject.getString("deptCode");
            String pageNum = jsonObject.getString(WebPage.PAGE_NUM);
            if (pageNum != null && !"".equals(pageNum.trim())) {
                // Pageable页面从0开始计
                page = new Integer(pageNum) - 1;
            }
            Map<String, Object> param = null;
            if (StringUtils.isNotBlank(deptCode)) {
                param = new HashMap<>();
                param.put("deptCode", deptCode);
            }
            dataByCondition = clickRateRepService.getDataByCondition(startTime, endTime, param);
        } else {
            dataByCondition = clickRateRepService.getDataByCondition(null, null, null);
        }
//        if (data[i].deptName.indexOf('血液') != -1 || data[i].deptName.indexOf('呼吸') != -1 || data[i].deptN
//        ame.indexOf('骨科') != -1 || data[i].deptName.indexOf('耳鼻喉') != -
//                1 || data[i].deptName.indexOf('心血管') != -1 || data[i].deptName.indexOf('普外') != -1)
        Collections.sort(dataByCondition, CompareUtil.createComparator(-1, "createTime"));
        for (ClickRate clickRate : dataByCondition) {
            String deptName = clickRate.getDeptName();
            if (StringUtils.isEmpty(deptName)) {
                continue;
            }
            if (deptName.contains("血液") || deptName.contains("呼吸") || deptName.contains("骨科") || deptName.contains("耳鼻喉") || deptName.contains("心血管") || deptName.contains("普外")) {
                resultList.add(clickRate);
            }
        }
        WebPage webPage = new WebPage();
        int currentPage = page + 1;
        // 当前页
        webPage.setPageNo(currentPage);
        // 总页数
        webPage.setTotalPageNum(resultList.size() % 20 == 0 ? resultList.size() / 20 : resultList.size() / 20 + 1);
        // 总记录数
        webPage.setTotalCount(resultList.size());
        List<ClickRate> clickRates = resultList.subList(page * 20, (page + 1) * 20);
        parse.put(WebPage.WEB_PAGE, webPage);
        parse.put(LIST_DATA, clickRates);
        resp.setResponseCode(ResponseCode.OK);
        resp.setData(parse);
        wirte(response, resp);
    }

    @RequestMapping(value = "/listTest")
    public AtResponse listTest(HttpServletResponse response, @RequestBody String map) {
        AtResponse resp = new AtResponse(System.currentTimeMillis());
        Map<String, Object> parse = (Map) JSON.parse(map);
        List<ClickRate> resultList = new ArrayList<>();
        List<ClickRate> dataByCondition = null;
        int page = 0;
        if (StringUtils.isNotBlank(map)) {
            JSONObject jsonObject = JSONObject.parseObject(map);
            Date startTime = jsonObject.getDate("startTime");
            Date endTime = jsonObject.getDate("endTime");
            String deptCode = jsonObject.getString("deptCode");
            String pageNum = jsonObject.getString(WebPage.PAGE_NUM);
            if (pageNum != null && !"".equals(pageNum.trim())) {
                // Pageable页面从0开始计
                page = new Integer(pageNum) - 1;
            }
            Map<String, Object> param = null;
            if (StringUtils.isNotBlank(deptCode)) {
                param = new HashMap<>();
                param.put("deptCode", deptCode);
            }
            dataByCondition = clickRateRepService.getDataByCondition(startTime, endTime, param);
        } else {
            dataByCondition = clickRateRepService.getDataByCondition(null, null, null);
        }
//        if (data[i].deptName.indexOf('血液') != -1 || data[i].deptName.indexOf('呼吸') != -1 || data[i].deptN
//        ame.indexOf('骨科') != -1 || data[i].deptName.indexOf('耳鼻喉') != -
//                1 || data[i].deptName.indexOf('心血管') != -1 || data[i].deptName.indexOf('普外') != -1)
        Collections.sort(dataByCondition, CompareUtil.createComparator(-1, "createTime"));
        for (ClickRate clickRate : dataByCondition) {
            String deptName = clickRate.getDeptName();
            if (StringUtils.isEmpty(deptName)) {
                continue;
            }
            if (deptName.contains("血液") || deptName.contains("呼吸") || deptName.contains("骨科") || deptName.contains("耳鼻喉") || deptName.contains("心血管") || deptName.contains("普外")) {
                resultList.add(clickRate);
            }
        }
        WebPage webPage = new WebPage();
        int currentPage = page + 1;
        // 当前页
        webPage.setPageNo(currentPage);
        // 总页数
        webPage.setTotalPageNum(resultList.size() % 20 == 0 ? resultList.size() / 20 : resultList.size() / 20 + 1);
        // 总记录数
        webPage.setTotalCount(resultList.size());
        List<ClickRate> clickRates = resultList.subList(page * 20, (page + 1) * 20);
        parse.put(WebPage.WEB_PAGE, webPage);
        parse.put(LIST_DATA, clickRates);
        resp.setResponseCode(ResponseCode.OK);
        resp.setData(parse);
        return resp;
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

    @ApiOperation(value = "按条件查询医生点击信息", notes = "请求demo：{\"startTime\":\"2017-01-01 00:00:00\",\"endTime\":\"2019-01-01 00:00:00\",\"deptCode\":\"1010100\"}",
            httpMethod = "POST", responseContainer = "Map")
    @ApiResponses({@ApiResponse(code = 200, message = "成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clickRate", value = "请求参数", required = false, paramType = "body")
    })
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

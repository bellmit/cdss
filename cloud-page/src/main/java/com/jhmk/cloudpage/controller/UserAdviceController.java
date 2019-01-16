package com.jhmk.cloudpage.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudentity.base.BaseEntityController;
import com.jhmk.cloudentity.earlywaring.entity.SmUsers;
import com.jhmk.cloudentity.page.bean.UserAdvice;
import com.jhmk.cloudentity.page.bean.repository.service.UserAdviceRepService;
import com.jhmk.cloudutil.model.AtResponse;
import com.jhmk.cloudutil.model.ResponseCode;
import com.netflix.discovery.converters.Auto;
import io.swagger.annotations.*;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author ziyu.zhou
 * @date 2019/1/2 18:18
 */
@Controller
@RequestMapping("/userAdvice")
@Api(description = "医生建议功能列表", value = "医生建议控制层")
public class UserAdviceController extends BaseEntityController<UserAdvice> {
    Logger logger = LoggerFactory.getLogger(UserAdviceController.class);

    @Autowired
    UserAdviceRepService userAdviceRepService;

    //分页展示
    @PostMapping(value = "/list")
    @ApiOperation(value = "分页展示", notes = "注意参数:()",
            httpMethod = "POST", responseContainer = "Map")
    @ApiResponses({@ApiResponse(code = 200, message = "成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "map", value = "请求参数", required = true, paramType = "body")
    })
    public void userList(HttpServletResponse response, @RequestBody String map) {
        Map<String, Object> reqParams = (Map) JSON.parse(map);
        AtResponse<Map<String, Object>> resp = listData(reqParams, userAdviceRepService, "createDate");
        Map<String, Object> data = resp.getData();
        List<SmUsers> uu = (List<SmUsers>) data.get(LIST_DATA);
        data.put(LIST_DATA, uu);
        resp.setResponseCode(ResponseCode.OK);
        resp.setData(data);
        wirte(response, resp);
    }


    //添加操作
    @ApiOperation(value = "添加用户建议", notes = "请求demo({\"patientId\":\"123\",\"visitId\":\"3\",\"doctorId\":\"10401\",\"doctorName\":\"张三\",\"deptId\":\" 部门编号\",\"doctorAdvice\":\" 医生建议 \"})",
            httpMethod = "POST", responseContainer = "Map")
    @ApiResponses({@ApiResponse(code = 200, message = "成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "map", value = "请求参数", required = true, paramType = "body")
    })
    @PostMapping(value = "/add")
    public void addUser(HttpServletResponse response, @RequestBody UserAdvice advice) {
        AtResponse<String> resp = new AtResponse<>();
        advice.setCreateDate(new Date());
        UserAdvice save = userAdviceRepService.save(advice);
        if (save != null) {
            resp.setResponseCode(ResponseCode.OK);
            resp.setMessage("添加成功");
        } else {
            logger.info("添加失败：" + save.toString());
            resp.setResponseCode(ResponseCode.ADDRERROR);
            resp.setMessage("添加失败");
        }
        wirte(response, resp);
    }


    @ApiOperation(value = "删除用户建议", notes = "请求demo({\"id\":1})",
            httpMethod = "POST", responseContainer = "Map")
    @ApiResponses({@ApiResponse(code = 200, message = "成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "map", value = "请求参数", required = true, paramType = "body")
    })
    @PostMapping(value = "/delete")
    public void delete(HttpServletResponse response, @RequestBody String map) {
        JSONObject object = JSON.parseObject(map);
        Integer id = object.getInteger("id");
        userAdviceRepService.delete(id);
        String message;
        AtResponse<String> resp = new AtResponse(System.currentTimeMillis());
        message = "删除成功";
        resp.setResponseCode(ResponseCode.OK);
        resp.setData(message);
        wirte(response, resp);
    }

    @ApiOperation(value = "编辑保存用户建议", notes = "请求demo({\"patientId\":\"123\",\"id\":1,\"visitId\":\"3\",\"doctorId\":\"10401\",\"doctorName\":\"张三\",\"deptId\":\" 部门编号\",\"doctorAdvice\":\" 医生建议 \"})",
            httpMethod = "POST", responseContainer = "Map")
    @ApiResponses({@ApiResponse(code = 200, message = "成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "map", value = "请求参数", required = true, paramType = "body")
    })
    @PostMapping(value = "/editor")
    public void editor(HttpServletResponse response, @RequestBody UserAdvice map) {
        AtResponse<String> resp = super.editSave(map, map.getId(), userAdviceRepService);
        wirte(response, resp);
    }


    @ApiOperation(value = "获取用户要编辑的用户建议", notes = "请求demo({\"id\":11})",
            httpMethod = "POST", responseContainer = "Map")
    @ApiResponses({@ApiResponse(code = 200, message = "成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "map", value = "请求参数", required = true, paramType = "body")
    })
    @PostMapping(value = "/view")
    public void view(HttpServletResponse response, @RequestBody String map) {
        JSONObject object = JSONObject.parseObject(map);
        Integer id = object.getInteger("id");
        AtResponse<UserAdvice> resp = super.viewData(id, userAdviceRepService);
        wirte(response, resp);
    }


}

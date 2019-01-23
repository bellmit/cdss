package com.jhmk.warn.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudentity.base.BaseEntityController;
import com.jhmk.cloudentity.earlywaring.entity.SmUsers;
import com.jhmk.cloudentity.earlywaring.entity.repository.service.SmUsersRepService;
import com.jhmk.cloudservice.warnService.service.SmUserService;
import com.jhmk.cloudutil.model.AtResponse;
import com.jhmk.cloudutil.model.ResponseCode;
import io.swagger.annotations.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * 用户管理对外接口
 */
@Controller
@RequestMapping("/users")
public class UsersController extends BaseEntityController<SmUsers> {

    private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    SmUsersRepService smUsersRepService;
    @Autowired
    SmUserService smUserService;


    @ApiOperation(value = "分页条件查询", notes = "请求demo:{\"userDept\":\"1020306\"}",
            httpMethod = "POST", responseContainer = "Map")
    @ApiResponses({@ApiResponse(code = 200, message = "成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "map", value = "请求参数", required = true, paramType = "body")
    })
    @PostMapping(value = "/list")
    public void userList(HttpServletResponse response, @RequestBody String map) {
        Map<String, Object> reqParams = (Map) JSON.parse(map);
        AtResponse<Map<String, Object>> resp = listData(reqParams, smUsersRepService, "userId");
        wirte(response, resp);
    }


    @ApiOperation(value = "添加用户", notes = "请求demo:SmUsers",
            httpMethod = "POST", responseContainer = "Map")
    @ApiResponses({@ApiResponse(code = 200, message = "成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "map", value = "请求参数", required = true, paramType = "body")
    })
    @PostMapping(value = "/add")
    public void addUser(HttpServletResponse response, @RequestBody SmUsers user) {
        AtResponse<String> resp = new AtResponse<>();
        SmUsers one = smUsersRepService.findOne(user.getUserId());
        if (one != null) {
            resp.setResponseCode(ResponseCode.INERERROR);
            resp.setMessage("用户已存在");
        } else {
            SmUsers save = smUsersRepService.save(user);
            if (save != null) {
                resp.setResponseCode(ResponseCode.OK);
                resp.setMessage("用户添加成功");
            } else {
                logger.info("用户添加失败：" + save.toString());
                resp.setResponseCode(ResponseCode.INERERROR);
                resp.setMessage("用户添加失败");
            }
        }
        wirte(response, resp);
    }


    @ApiOperation(value = "查询用户唯一id是否存在", notes = "请求demo:{\"id\":\"213\"}",
            httpMethod = "POST", responseContainer = "Map")
    @ApiResponses({@ApiResponse(code = 200, message = "成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "map", value = "请求参数", required = true, paramType = "body")
    })
    @PostMapping(value = "/checkUserId")
    public void checkUserId(HttpServletResponse response, @RequestBody String map) {
        JSONObject object = JSON.parseObject(map);
        String id = object.getString("id");
        SmUsers user = smUsersRepService.findOne(id);
        String existUser = "Y";
        if (user == null) {
            existUser = "N";
        }
        AtResponse<String> resp = new AtResponse(System.currentTimeMillis());
        resp.setResponseCode(ResponseCode.OK);
        resp.setData(existUser);
        wirte(response, resp);
    }

    @ApiOperation(value = "删除用户", notes = "请求demo:{\"id\":\"213\"}",
            httpMethod = "POST", responseContainer = "Map")
    @ApiResponses({@ApiResponse(code = 200, message = "成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "map", value = "请求参数", required = true, paramType = "body")
    })
    @RequestMapping(value = "/delete")
    public void delete(HttpServletResponse response, @RequestBody String map) {
        JSONObject object = JSON.parseObject(map);
        String id = object.getString("id");
        AtResponse<String> delete = super.delete(id, smUsersRepService);
        wirte(response, delete);
    }

    @ApiOperation(value = "查询编辑用户", notes = "请求demo:{\"id\":\"213\"}",
            httpMethod = "POST", responseContainer = "Map")
    @ApiResponses({@ApiResponse(code = 200, message = "成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "map", value = "请求参数", required = true, paramType = "body")
    })
    @RequestMapping(value = "/view")
    public void view(HttpServletResponse response, @RequestBody String map) {
        JSONObject object = JSON.parseObject(map);
        String id = object.getString("id");
        AtResponse<String> stringAtResponse = super.viewData(id, smUsersRepService);
        wirte(response, stringAtResponse);
    }

    @ApiOperation(value = "编辑用户", notes = "请求demo:user实体类",
            httpMethod = "POST", responseContainer = "Map")
    @ApiResponses({@ApiResponse(code = 200, message = "成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "map", value = "请求参数", required = true, paramType = "body")
    })
    @RequestMapping(value = "/editor")
    public void editor(HttpServletResponse response, @RequestBody SmUsers user) {
        AtResponse resp = new AtResponse(System.currentTimeMillis());
        if (StringUtils.isEmpty(user.getRoleId())) {
            resp.setResponseCode(ResponseCode.INERERROR);
            resp.setMessage("请选择角色！");
        }
        SmUsers updateUser = smUserService.editor(user, user.getRoleId());
        if (updateUser == null) {
            resp.setResponseCode(ResponseCode.INERERROR);
            resp.setMessage("编辑失败,请重新添加！");
        } else {
            resp.setResponseCode(ResponseCode.OK);
            resp.setMessage("编辑成功！");
        }
        wirte(response, resp);
    }

    @ApiOperation(value = "修改用户密码", notes = "请求demo:{\"id\":\"213\",\"jiuPassword\":\"12345\",\"password\":\"123\"} ",
            httpMethod = "POST", responseContainer = "Map")
    @ApiResponses({@ApiResponse(code = 200, message = "成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "map", value = "请求参数", required = true, paramType = "body")
    })
    @PostMapping(value = "/changePassword")
    public void updPwd(HttpServletResponse response, @RequestBody String map) {
        AtResponse<String> resp = new AtResponse(System.currentTimeMillis());
        JSONObject object = JSON.parseObject(map);
        String userId = object.getString("id");
        String jiuPassword = object.getString("jiuPassword");
        String password = object.getString("password");
        SmUsers user = smUsersRepService.findOne(userId);
        if (user.getUserPwd().equals(jiuPassword)) {
            smUsersRepService.setPasswordFor(password, userId);
            resp.setResponseCode(ResponseCode.OK);
            resp.setMessage("修改成功！");
        } else {
            resp.setResponseCode(ResponseCode.INERERROR2);
            resp.setMessage("原密码错误！");
        }
        wirte(response, resp);
    }
}
package com.jhmk.warn.controller;


import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudentity.base.BaseEntityController;
import com.jhmk.cloudentity.earlywaring.entity.SmDepts;
import com.jhmk.cloudentity.earlywaring.entity.SmRole;
import com.jhmk.cloudentity.earlywaring.entity.SmUsers;
import com.jhmk.cloudentity.earlywaring.entity.repository.service.SmDeptsRepService;
import com.jhmk.cloudentity.earlywaring.entity.repository.service.SmRoleRepService;
import com.jhmk.cloudentity.earlywaring.entity.repository.service.SmUsersRepService;
import com.jhmk.cloudutil.config.BaseConstants;
import com.jhmk.cloudutil.config.UrlConstants;
import com.jhmk.cloudutil.model.AtResponse;
import com.jhmk.cloudutil.model.ResponseCode;
import com.jhmk.cloudutil.util.JWTUtil;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@Controller
@Api(description = "登录注销功能", value = "用户登录注销")
public class CmsController extends BaseEntityController<SmUsers> {
    @Autowired
    SmDeptsRepService smDeptsRepService;
    @Autowired
    SmRoleRepService smRoleRepService;
    @Autowired
    SmUsersRepService smUsersRepService;
    private static final Logger logger = LoggerFactory.getLogger(CmsController.class);

    /**
     * 登录功能
     *
     * @param httpServletRequest
     * @param response
     * @param map
     */

    @RequestMapping(value = "/warn/login")
    @ApiOperation(value = "登录", notes = "注意参数:(username,password)",
            httpMethod = "POST", responseContainer = "Map")
    @ApiResponses({@ApiResponse(code = 200, message = "成功")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "map", value = "请求参数", required = true, paramType = "body",
                    examples = @Example(value = {
                            @ExampleProperty(mediaType = "username", value = "ww"),
                            @ExampleProperty(mediaType = "password", value = "12345")
                    }))
    })
    @ResponseBody
    public void loginPost(HttpServletRequest httpServletRequest, HttpServletResponse response, @RequestBody @ApiIgnore String map) {
        logger.info("登录信息：{}", map);
        Map<String, String> param = (Map) JSONObject.parse(map);
        AtResponse resp = new AtResponse<>(System.currentTimeMillis());
        Map<String, Object> data = new HashMap<>();
        if (param.get("username") == null || "".equals(param.get("username")) || "".equals(param.get("password"))) {
            resp.setResponseCode(ResponseCode.INERERROR2);
            resp.setMessage("用户名或密码为空");
            wirte(response, resp);
        } else {
            String userId = param.get("username");
            SmUsers admin = smUsersRepService.findOne(userId);
            String password = param.get("password");
            if (admin != null) {
                if (password.equals(admin.getUserPwd())) {
                    httpServletRequest.getSession().setAttribute(BaseConstants.USER_ID, userId.trim());
                    //todo token 写法
                    String token = null;
                    try {
                        token = JWTUtil.createJWT(userId, password, System.currentTimeMillis());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    httpServletRequest.getSession().setAttribute(BaseConstants.TOKEN, token);
                    String roleId = admin.getRoleId();
                    httpServletRequest.getSession().setAttribute(BaseConstants.CURRENT_ROLE_ID, roleId);
                    SmRole one = smRoleRepService.findOne(roleId);
                    httpServletRequest.getSession().setAttribute(BaseConstants.CURRENT_ROLE_RANGE, one.getRuleRange());


                    if (admin.getUserDept() != null) {
                        httpServletRequest.getSession().setAttribute(BaseConstants.DEPT_ID, admin.getUserDept());
                        SmDepts firstByDeptCode = smDeptsRepService.findFirstByDeptCode(admin.getUserDept());
                        if (firstByDeptCode != null) {
                            httpServletRequest.getSession().setAttribute(BaseConstants.DEPT_NAME, firstByDeptCode.getDeptName());
                        } else {
//                        httpServletRequest.getSession().setAttribute(BaseConstants.DEPT_NAME, firstByDeptCode.getDeptName());
                        }
                    }


                    httpServletRequest.getSession().setAttribute(BaseConstants.TOKEN, token);
                    //设置session超时时间(2小时)
                    httpServletRequest.getSession().setMaxInactiveInterval(2 * 60 * 60);

                    logger.info("登录成功");
                    resp.setMessage("登录成功");
                    resp.setData(admin);
                    resp.setResponseCode(ResponseCode.OK);
                    response.setHeader(BaseConstants.TOKEN, token);
                } else {
                    resp.setResponseCode(ResponseCode.INERERROR2);
                    resp.setMessage("密码错误");

                }
                data.put("status", "200");
            } else {
                resp.setResponseCode(ResponseCode.CUSTOMER_NOT_EXIST);
                resp.setMessage("用户不存在");
            }

            wirte(response, resp);

        }
        logger.info("登录结果信息：{}", JSONObject.toJSONString(resp));

    }

    /**
     * 注销功能
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/warn/loginout", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "退出登录", notes = "无需传参",
            httpMethod = "POST", responseContainer = "Map")
    @ApiResponses({@ApiResponse(code = 200, message = "成功")})
    public void loginout(HttpServletRequest request, HttpServletResponse response) {
        request.removeAttribute(BaseConstants.USER_ID);
        request.removeAttribute(BaseConstants.CURRENT_ROLE_ID);
        request.removeAttribute(BaseConstants.DEPT_ID);
//            request.removeAttribute(BaseConstants.FT_DEPT_ID);
//            request.removeAttribute(BaseConstants.FT_DEPT_NAME);
        request.removeAttribute(BaseConstants.TOKEN);

        logger.info(getUserId() + " logout.");
        AtResponse resp = new AtResponse(System.currentTimeMillis());
        resp.setMessage(BaseConstants.SUCCESS);
        resp.setResponseCode(ResponseCode.OK);
        logger.info(BaseConstants.USER_ID + "退出成功");
        resp.setMessage("退出成功");
        resp.setResponseCode(ResponseCode.OK);
        wirte(response, resp);

    }

}

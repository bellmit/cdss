package com.jhmk.warn.controller;

import com.alibaba.fastjson.JSON;
import com.jhmk.cloudentity.base.BaseEntityController;
import com.jhmk.cloudentity.earlywaring.entity.UserDataModelMapping;
import com.jhmk.cloudentity.earlywaring.entity.UserModel;
import com.jhmk.cloudentity.earlywaring.entity.repository.service.UserDataModelMappingRepService;
import com.jhmk.cloudservice.warnService.service.UserModelService;
import com.jhmk.cloudutil.config.BaseConstants;
import com.jhmk.cloudutil.config.UrlPropertiesConfig;
import com.jhmk.cloudutil.model.AtResponse;
import com.jhmk.cloudutil.model.ResponseCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author zzy
 */


@Controller
@RequestMapping("/warn/userModel")
public class UserModelController extends BaseEntityController<UserModel> {

    public static final Logger logger = LoggerFactory.getLogger(UserModelController.class);
    @Autowired
    UserModelService userModelService;
    @Autowired
    UrlPropertiesConfig urlPropertiesConfig;
    @Autowired
    UserDataModelMappingRepService userDataModelMappingRepService;
    @Autowired
    RestTemplate restTemplate;

    /**
     * 获取分层列表
     */
    @RequestMapping(value = "/getVariableList")
    @ResponseBody
    public void getVariableList(HttpServletResponse response) {
        List<UserModel> variableListNew = userModelService.getVariableList();
        wirte(response, variableListNew);
    }

    /**
     * 值域搜索
     *
     * @param response
     * @param param
     */
    @RequestMapping(value = "/getTypeByField")
    @ResponseBody
    public void getTypeByField(HttpServletResponse response, @RequestBody String param) {
        Map<String, String> map = (Map) JSON.parse(param);
        String s = map.get("variableid");
        UserDataModelMapping mapping = userDataModelMappingRepService.findByUmNamePath(s);
        if (mapping != null) {
            map.put("variableid", mapping.getDmNamePath());
            Object o = JSON.toJSON(map);
            String data = "";
            try {
                data = restTemplate.postForObject(urlPropertiesConfig.getCdssurl() + BaseConstants.getfieldbyid, o, String.class);
            } catch (Exception e) {
                logger.info("调用{}接口失败，错误原因{}，错误信息{}", BaseConstants.getfieldbyid, e.getCause(), e.getMessage());
            } finally {
                wirte(response, data);
            }
        }

    }

    /**
     * 高级检索单位变量
     *
     * @param response
     * @param param
     */
    @RequestMapping(value = "/getunitsbyid")
    @ResponseBody
    public void getunitsbyid(HttpServletResponse response, @RequestBody String param) {
        AtResponse resp = new AtResponse();
        Map<String, String> map = (Map) JSON.parse(param);
        String s = map.get("variableid");
        UserDataModelMapping mapping = userDataModelMappingRepService.findByUmNamePath(s);
        if (mapping != null) {
            map.put("variableid", mapping.getDmNamePath());
            Object o = JSON.toJSON(map);
            String data = "";
            try {
                data = restTemplate.postForObject(urlPropertiesConfig.getCdssurl() + BaseConstants.getunitsbyid, o, String.class);
                resp.setData(data);
                resp.setResponseCode(ResponseCode.OK);
            } catch (Exception e) {
                logger.info("调用{}接口失败，错误原因{}，错误信息{}", BaseConstants.getunitsbyid, e.getCause(), e.getMessage());
                resp.setResponseCode(ResponseCode.INERERROR);
            } finally {
                wirte(response, data);
            }
        }

    }

}




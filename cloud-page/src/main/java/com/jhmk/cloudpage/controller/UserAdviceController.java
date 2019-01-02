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
import java.util.List;
import java.util.Map;

/**
 * @author ziyu.zhou
 * @date 2019/1/2 18:18
 */
@Controller
@RequestMapping("/userAdvice")
public class UserAdviceController extends BaseEntityController<UserAdvice> {
    Logger logger = LoggerFactory.getLogger(UserAdviceController.class);

    @Autowired
    UserAdviceRepService userAdviceRepService;

    //分页展示
    @PostMapping(value = "/list")
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
    @PostMapping(value = "/add")
    public void addUser(HttpServletResponse response, @RequestBody UserAdvice advice) {
        AtResponse<String> resp = new AtResponse<>();

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


    //    //删除
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

    @PostMapping(value = "/editor")
    public void editor(HttpServletResponse response, @RequestBody UserAdvice map) {
        AtResponse<String> resp = super.editSave(map, map.getId(), userAdviceRepService);
        wirte(response, resp);
    }
}

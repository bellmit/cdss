package com.jhmk.warn.controller;

import com.jhmk.cloudentity.base.BaseEntityController;
import com.jhmk.cloudentity.earlywaring.entity.SmEvaluate;
import com.jhmk.cloudentity.earlywaring.entity.repository.service.SmEvaluateRepService;
import com.jhmk.cloudutil.config.BaseConstants;
import com.jhmk.cloudutil.model.AtResponse;
import com.jhmk.cloudutil.model.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/warn/evaluate")
public class EvaluateController extends BaseEntityController<SmEvaluate> {

    @Autowired
    SmEvaluateRepService smEvaluateRepService;

    @RequestMapping("")
    public String list() {
        return "/evaluate/list";
    }


    //分页展示
    @RequestMapping(value = "/listData")
    @ResponseBody
    public AtResponse userList(@RequestParam Map<String, Object> reqParams) {

        AtResponse<Map<String, Object>> resp = listDataByMap(reqParams, smEvaluateRepService, "creataDate");
        Map<String, Object> data = resp.getData();
        Map<String, String> mm = (Map<String, String>) data.get("params");
        List<SmEvaluate> uu = (List<SmEvaluate>) data.get(LIST_DATA);
        data.put(LIST_DATA, uu);
        //2.机构列表
        resp.setResponseCode(ResponseCode.OK);
        resp.setData(data);
        return resp;
    }


    //添加操作
    @RequestMapping(value = "/addEvaluate")
    @ResponseBody
    public AtResponse addEvaluate(@ModelAttribute SmEvaluate evaluate) {
        AtResponse<String> resp = new AtResponse(System.currentTimeMillis());
        String userId = getUserId();
        evaluate.setUserId(userId);
        evaluate.setCreateDate(new Timestamp(System.currentTimeMillis()));
        evaluate.setStatus(BaseConstants.PARAMTER_STATUS_1);
        SmEvaluate evaluate1 = smEvaluateRepService.save(evaluate);
        if (evaluate1 == null) {
            resp.setMessage("添加失败,请重新添加！");
            resp.setResponseCode(ResponseCode.INERERROR);
            return resp;
        } else {
            resp.setMessage("添加成功！");
            resp.setResponseCode(ResponseCode.OK);
            return resp;
        }

    }

    //删除操作
    @RequestMapping(value = "/delEvaluate")
    @ResponseBody
    public AtResponse delEvaluate(@RequestParam(name = "id", required = true) String id) {
        AtResponse<String> resp = new AtResponse(System.currentTimeMillis());
        int index = smEvaluateRepService.setStatus("0", id);
        String message = "";
        if (index != 0) {
            message = "删除成功";
            resp.setResponseCode(ResponseCode.OK);
            resp.setData(message);
            return resp;
        } else {
            message = "删除失败,请重新操作";
            resp.setResponseCode(ResponseCode.INERERROR);
            resp.setData(message);
            return resp;
        }
    }
}

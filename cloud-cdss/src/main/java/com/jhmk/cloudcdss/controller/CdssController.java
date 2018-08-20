package com.jhmk.cloudcdss.controller;

import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudcdss.service.CdssService;
import com.jhmk.cloudentity.base.BaseController;
import com.jhmk.cloudentity.cdss.CdssRuleBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/test/cdss")
public class CdssController extends BaseController {

    @Autowired
    CdssService cdssService;

    /**
     * 随机查询 抽取病例
     *
     * @param response
     */
    @PostMapping("/ranDomSel")
    @ResponseBody
    public void ranDomSel(HttpServletResponse response) {

        //查询所有patientod
        List<String> idList = cdssService.getAllIds();
        int size = idList.size();
        int round = (int) (Math.random() * size);
        String id = idList.get(round);
        //查询  ruyuanjilu 一诉五史
        CdssRuleBean cdssTestBean = cdssService.selruyuanjiluById(id);
        //病案首页
        Map selbinganshouye = cdssService.selBasy(id);
        cdssTestBean.setBinganshouye(selbinganshouye);
        //病例诊断
        List<Map<String, String>> selbinglizhenduan1 = cdssService.selbinglizhenduan(id);
        cdssTestBean.setBinglizhenduan(selbinglizhenduan1);
        //首页诊断
        List<Map<String, String>> syzdList = cdssService.selSyzd(id);
        cdssTestBean.setShouyezhenduan(syzdList);
        List<Map<String, String>> jybgList = cdssService.selJybgList(id);
        cdssTestBean.setJianyanbaogao(jybgList);
        Object o = JSONObject.toJSON(cdssTestBean);
        wirte(response, o);
    }


}

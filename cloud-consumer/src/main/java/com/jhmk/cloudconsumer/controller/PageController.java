package com.jhmk.cloudconsumer.controller;

import com.jhmk.cloudconsumer.feign.PageInterface;
import com.jhmk.cloudentity.base.BaseController;
import com.jhmk.cloudentity.base.BaseEntityController;
import com.jhmk.cloudutil.model.AtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * @author ziyu.zhou
 * @date 2019/1/2 11:40
 */
@Controller
public class PageController extends BaseController {
    @Autowired
    PageInterface pageInterface;

    @RequestMapping("/hello/test1")
    public void index(HttpServletResponse response) {
        AtResponse s = pageInterface.test1();
        wirte(response,s);
    }

    @RequestMapping("/hello/test21")
    public void index2(HttpServletResponse response, @RequestBody String map) {
        AtResponse s = pageInterface.test2(map);
        System.out.println(s);
        wirte(response, s);
    }
}

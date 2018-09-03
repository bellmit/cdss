package com.jhmk.cloudpage.controller;

import com.jhmk.cloudentity.page.bean.ClickRate;
import com.jhmk.cloudpage.demo.FrontHitEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

/**
 * @author ziyu.zhou
 * @date 2018/8/31 18:54
 */
@Controller
@RequestMapping("/page/demo")
public class DemoController {
    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping("/test1")
    public String demo() {
        return "www.baidu.com";
    }

    @RequestMapping("/test2")
    public void demo2(HttpServletResponse response, @RequestBody String map) {
        FrontHitEvent event = new FrontHitEvent(map);
        applicationContext.publishEvent(event);
        System.out.println("事件发布成功");
    }
}

package com.jhmk.cloudpage.demo;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.EventListener;

/**
 * @author ziyu.zhou
 * @date 2018/8/31 19:36
 */
@Component
public class FrontHitListener implements ApplicationListener<FrontHitEvent> {
    @Override
    public void onApplicationEvent(FrontHitEvent frontHitEvent) {
        System.out.println("接收到事件："+frontHitEvent.getClass());
    }
}

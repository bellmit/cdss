package com.jhmk.cloudconsumer.feign;

import com.jhmk.cloudconsumer.hystrix.PageHystrix;
import com.jhmk.cloudutil.model.AtResponse;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ziyu.zhou
 * @date 2019/1/2 11:36
 */
@FeignClient(name = "jhmk-page",fallback = PageHystrix.class)
public interface PageInterface {
    @RequestMapping(value = "/demo/test1")
    AtResponse test1();

    @RequestMapping(value = "/demo/testtwo")
    AtResponse test2(String name);
}

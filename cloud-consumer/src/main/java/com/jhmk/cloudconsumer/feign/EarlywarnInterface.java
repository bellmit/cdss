package com.jhmk.cloudconsumer.feign;

import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @author ziyu.zhou
 * @date 2019/1/2 11:36
 */
@FeignClient(name= "jhmk-earlywarn")
public interface EarlywarnInterface {
}

package com.jhmk.cloudconsumer.hystrix;

import com.jhmk.cloudconsumer.feign.PageInterface;
import com.jhmk.cloudutil.model.AtResponse;
import com.jhmk.cloudutil.model.ResponseCode;
import org.springframework.stereotype.Component;

/**
 * @author ziyu.zhou
 * @date 2019/1/8 10:30
 */
@Component
public class PageHystrix implements PageInterface {
    @Override
    public AtResponse test1() {
        AtResponse resp = new AtResponse(System.currentTimeMillis());
        resp.setResponseCode(ResponseCode.INERERROR);
        resp.setMessage("调用失败");
        return resp;
    }

    @Override
    public AtResponse test2(String name) {
        AtResponse resp = new AtResponse(System.currentTimeMillis());
        resp.setResponseCode(ResponseCode.INERERROR);
        resp.setMessage("调用失败");
        return resp;    }
}

package com.jhmk.cloudutil.util;

import com.alibaba.fastjson.JSON;

public class JsonUtil {
    /**
     * 解析JsonObject数据
     *
     * @param jsonString Json格式字符串
     * @param cls        封装类
     */
    public static <T> T parseObject(String jsonString, Class<T> cls) {
        T t = null;
        try {
            t = JSON.parseObject(jsonString, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }

}

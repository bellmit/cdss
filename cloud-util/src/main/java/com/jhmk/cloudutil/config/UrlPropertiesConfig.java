package com.jhmk.cloudutil.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author ziyu.zhou
 * @date 2018/12/10 15:00
 */
@Component
@ConfigurationProperties()
@PropertySource(value = "classpath:urlConfig.yml")
@Data
public class UrlPropertiesConfig {

    private String cdssurl;
    private String pageurl;
    private String participleurl;
    private String esurl;
    //知识库
    private String knowbaseurl;
}

package com.jhmk.cloudpage.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author ziyu.zhou
 * @date 2019/1/23 18:46
 */

@Configuration
@Data
//@PropertySource("classpath:config/my.prop")
@ConfigurationProperties(prefix = "socket")
public class SocketPropConf {
    private String ip;
    private Integer port;
    private Integer port2;
}

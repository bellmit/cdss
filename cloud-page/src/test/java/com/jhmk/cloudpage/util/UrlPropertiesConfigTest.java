package com.jhmk.cloudpage.util;

import com.jhmk.cloudutil.config.UrlPropertiesConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author ziyu.zhou
 * @date 2018/12/10 15:20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlPropertiesConfigTest {
    @Autowired
    UrlPropertiesConfig urlPropertiesConfig;

    @Test
    public void getCdssurl() {
        String cdssurl = urlPropertiesConfig.getCdssurl();
        String pageurl = urlPropertiesConfig.getPageurl();
        String participleurl = urlPropertiesConfig.getParticipleurl();
        System.out.println(pageurl);
    }
}
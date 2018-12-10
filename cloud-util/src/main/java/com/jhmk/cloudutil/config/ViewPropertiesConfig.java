package com.jhmk.cloudutil.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author ziyu.zhou
 * @date 2018/12/10 15:00
 */
@Component
@ConfigurationProperties()
@PropertySource(value = "classpath:viewConfig.yml")
public class ViewPropertiesConfig {

    private String driver;
    private String gam_temp_baogaourl;
    private String gam_baogaousername;
    private String gam_baogaopassword;
    private String gam_ryjlurl;
    private String gam_ryjlusername;
    private String gam_ryjlpassword;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getGam_temp_baogaourl() {
        return gam_temp_baogaourl;
    }

    public void setGam_temp_baogaourl(String gam_temp_baogaourl) {
        this.gam_temp_baogaourl = gam_temp_baogaourl;
    }

    public String getGam_baogaousername() {
        return gam_baogaousername;
    }

    public void setGam_baogaousername(String gam_baogaousername) {
        this.gam_baogaousername = gam_baogaousername;
    }

    public String getGam_baogaopassword() {
        return gam_baogaopassword;
    }

    public void setGam_baogaopassword(String gam_baogaopassword) {
        this.gam_baogaopassword = gam_baogaopassword;
    }

    public String getGam_ryjlurl() {
        return gam_ryjlurl;
    }

    public void setGam_ryjlurl(String gam_ryjlurl) {
        this.gam_ryjlurl = gam_ryjlurl;
    }

    public String getGam_ryjlusername() {
        return gam_ryjlusername;
    }

    public void setGam_ryjlusername(String gam_ryjlusername) {
        this.gam_ryjlusername = gam_ryjlusername;
    }

    public String getGam_ryjlpassword() {
        return gam_ryjlpassword;
    }

    public void setGam_ryjlpassword(String gam_ryjlpassword) {
        this.gam_ryjlpassword = gam_ryjlpassword;
    }
}

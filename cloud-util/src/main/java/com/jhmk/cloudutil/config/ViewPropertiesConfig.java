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
    private String temp_baogaourl;
    private String baogaousername;
    private String baogaopassword;
    private String ryjlurl;
    private String ryjlusername;
    private String ryjlpassword;
    private String baogaourl;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getTemp_baogaourl() {
        return temp_baogaourl;
    }

    public void setTemp_baogaourl(String temp_baogaourl) {
        this.temp_baogaourl = temp_baogaourl;
    }

    public String getBaogaousername() {
        return baogaousername;
    }

    public void setBaogaousername(String baogaousername) {
        this.baogaousername = baogaousername;
    }

    public String getBaogaopassword() {
        return baogaopassword;
    }

    public void setBaogaopassword(String baogaopassword) {
        this.baogaopassword = baogaopassword;
    }

    public String getRyjlurl() {
        return ryjlurl;
    }

    public void setRyjlurl(String ryjlurl) {
        this.ryjlurl = ryjlurl;
    }

    public String getRyjlusername() {
        return ryjlusername;
    }

    public void setRyjlusername(String ryjlusername) {
        this.ryjlusername = ryjlusername;
    }

    public String getRyjlpassword() {
        return ryjlpassword;
    }

    public void setRyjlpassword(String ryjlpassword) {
        this.ryjlpassword = ryjlpassword;
    }

    public String getBaogaourl() {
        return baogaourl;
    }

    public void setBaogaourl(String baogaourl) {
        this.baogaourl = baogaourl;
    }
}

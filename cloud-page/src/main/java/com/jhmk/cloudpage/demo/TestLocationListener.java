package com.jhmk.cloudpage.demo;

/**
 * @author ziyu.zhou
 * @date 2018/8/31 19:17
 */

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;



public class TestLocationListener implements LocationListener {

    private LocationService ls;

    public TestLocationListener() {
        ls = new LocationService();
        ls.addLocationListener(this);                     //注册监听
        System.out.println("添加监听器完毕");
        try {
            // 调用此方法触发事件,触发的事件就是执行locationEvent(接口的方法)的方法
            ls.addLocation("insert into tb_location (companyId,mobile,longitude,latitude,locationTime) values(2,'444','4','4','2011-03-23 11:03:04')");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void locationEvent(LocationEvent le) {
        // TODO Auto-generated method stub
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleWithFixedDelay(new ListThread(), 0, 2, TimeUnit.SECONDS);
    }

}
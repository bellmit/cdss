package com.jhmk.cloudpage.demo;

/**
 * @author ziyu.zhou
 * @date 2018/8/31 19:18
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;


public class LocationService {
    private Connection conn = null;
    List list = new ArrayList();
    String sql = "select * from tb_location";
    Statement st = null;

    private Vector repository = new Vector();
    private LocationListener ll;

    public LocationService() {
    }

    //注册监听器，如果这里没有使用Vector而是使用ArrayList那么要注意同步问题
    public void addLocationListener(LocationListener ll) {
        repository.addElement(ll);//这步要注意同步问题
    }

    //如果这里没有使用Vector而是使用ArrayList那么要注意同步问题
    public void notifyLocationEvent(LocationEvent event) {
        Enumeration e = repository.elements();//这步要注意同步问题
        while (e.hasMoreElements()) {
            ll = (LocationListener) e.nextElement();
            ll.locationEvent(event);
        }
    }

    //删除监听器，如果这里没有使用Vector而是使用ArrayList那么要注意同步问题
    public void removeLocationListener(LocationListener ll) {
        repository.remove(ll);//这步要注意同步问题
    }


    List<String> tempList = new ArrayList<>();

    public void addLocation(String sql) {
        tempList.add(sql);
    }

    public static void main(String[] args) {
        new TestLocationListener();
    }
}
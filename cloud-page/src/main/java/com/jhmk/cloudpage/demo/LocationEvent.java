package com.jhmk.cloudpage.demo;
import java.util.EventObject;

/**
 * @author ziyu.zhou
 * @date 2018/8/31 19:17
 */


public class LocationEvent extends EventObject {

    private static final long serialVersionUID = 1L;
    private Object obj;

    public LocationEvent(Object source) {
        super(source);
        obj = source;
    }

    @Override
    public Object getSource(){
        return obj;
    }

}
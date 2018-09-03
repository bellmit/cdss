package com.jhmk.cloudpage.demo;

/**
 * @author ziyu.zhou
 * @date 2018/8/31 19:17
 */

import java.util.EventListener;

public interface LocationListener extends EventListener {

    public void locationEvent(LocationEvent le);

}
package com.jhmk.cloudpage.demo;

/**
 * @author ziyu.zhou
 * @date 2018/8/31 19:21
 */

public class ListThread implements Runnable {
    int i = 0;

    @Override
    public void run() {
        System.out.println(i++);
    }
}

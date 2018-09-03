package com.jhmk.cloudpage.demo;

import org.springframework.context.ApplicationEvent;

import java.util.EventObject;

/**
 * @author ziyu.zhou
 * @date 2018/8/31 19:35
 */

public class FrontHitEvent extends ApplicationEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public FrontHitEvent(Object source) {
        super(source);
    }
}

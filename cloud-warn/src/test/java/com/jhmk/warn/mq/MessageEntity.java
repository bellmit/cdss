package com.jhmk.warn.mq;

/**
 * @author ziyu.zhou
 * @date 2018/11/13 17:48
 */

/**
 * 消息实体类
 */
public class MessageEntity {


    /** 消息Id */
    private  String msgId = null;
    /** 消息内容*/
    private  String msg = null;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
package com.jhmk.cloudutil.utilbean;

/**
 * @author ziyu.zhou
 * @date 2018/11/20 17:50
 */

public class XMLBean {
    private String condition; //字段名
    private String sympol; //符号  <>=
    private String value;//值

    public XMLBean() {
    }

    public XMLBean(String condition, String sympol, String value) {
        this.condition = condition;
        this.sympol = sympol;
        this.value = value;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getSympol() {
        return sympol;
    }

    public void setSympol(String sympol) {
        this.sympol = sympol;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

package com.jhmk.cloudentity.earlywaring.entity.rule;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * 添加规则时  用于解析的实体类
 */
public class AnalyzeBean implements Serializable{
    private String id;
    private String unit;
    private String flag;
    private String field;
    private String values;
    private String exp;
    private String name;

    /**
     * 以下字段为接受已有的mongo库的规则 ，解析为原前台传过来的字段，方便前台重新编辑规则信息
     */
    private List<String> value=new LinkedList<>();//等同于values
    private String type;// suerModel 的 类型

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public String getExp() {
        return exp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public List<String> getValue() {
        return value;
    }

    public void setValue(List<String> value) {
        this.value = value;
    }


}

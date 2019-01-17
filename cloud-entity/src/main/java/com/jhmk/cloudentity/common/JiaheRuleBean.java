package com.jhmk.cloudentity.common;

import com.jhmk.cloudentity.earlywaring.entity.rule.Binglizhenduan;

import java.io.Serializable;
import java.util.List;

/**
 * @author ziyu.zhou
 * @date 2019/1/16 17:52
 * 嘉和电子病历传输数据实体类
 */

public class JiaheRuleBean implements Serializable {
    private Huanzhexinxi huanzhexinxi;
    private Wenshuxinxi wenshuxinxi;
    private List<Binglizhenduan> binglizhenduanList;

    public Huanzhexinxi getHuanzhexinxi() {
        return huanzhexinxi;
    }

    public void setHuanzhexinxi(Huanzhexinxi huanzhexinxi) {
        this.huanzhexinxi = huanzhexinxi;
    }

    public Wenshuxinxi getWenshuxinxi() {
        return wenshuxinxi;
    }

    public void setWenshuxinxi(Wenshuxinxi wenshuxinxi) {
        this.wenshuxinxi = wenshuxinxi;
    }

    public List<Binglizhenduan> getBinglizhenduanList() {
        return binglizhenduanList;
    }

    public void setBinglizhenduanList(List<Binglizhenduan> binglizhenduanList) {
        this.binglizhenduanList = binglizhenduanList;
    }
}

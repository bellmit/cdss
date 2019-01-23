package com.jhmk.cloudentity.common;

import com.jhmk.cloudentity.earlywaring.entity.rule.Binganshouye;
import com.jhmk.cloudentity.earlywaring.entity.rule.Binglizhenduan;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ziyu.zhou
 * @date 2019/1/16 17:52
 * 嘉和电子病历传输数据实体类
 */

@Data
public class JiaheRuleBean implements Serializable {
    private Binganshouye binganshouye;
    private Huanzhexinxi huanzhexinxi;
    private Wenshuxinxi wenshuxinxi;
    private List<Binglizhenduan> binglizhenduanList;

}

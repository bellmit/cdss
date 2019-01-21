package com.jhmk.cloudentity.earlywaring.entity.rule;

import lombok.Data;


/**
 * @author ziyu.zhou
 * @date 2018/7/31 10:35
 */


@Data
public class Jianyanbaogao {
    private String lab_item_name;//检验项名称
    private String specimen;
    private String speciman_type_name;
    private String lab_qual_result;//定性结果
    private String lab_result_value;//检验定量结果值
    private String report_time;//检验结果产生时间
    private String report_no;
    private String reference_range;//参考区间
    private String lab_result_value_unit;//参考单位
    private String lab_sub_item_name;//检验细项
    private String result_status_code;//结果编码，和参考范围比较的定性结果，H、L等
    private String result_status_name;//结果描述，和参考范围比较的定性结果，偏高、偏低等

    //做排序用 删除重复项
    public String getIwantData() {
        return this.specimen + this.lab_sub_item_name;
    }
}

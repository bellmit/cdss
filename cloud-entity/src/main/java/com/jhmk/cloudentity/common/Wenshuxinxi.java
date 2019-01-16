package com.jhmk.cloudentity.common;//

/**
 * @author ziyu.zhou
 * @date 2019/1/16 17;//57
 * 文书信息
 */

public class Wenshuxinxi {
    private String file_unique_id;// 文书唯一id,
    private String dept_code;// 文书所属科室,
    private String mr_class_code;// 文书分类,
    private String topic;// 文书名称,
    private String file_flag;// 文书状态,
    private String creator_name;// 文书创建者,
    private String caption_date_time;// 文书创建时间,
    private String last_modify_date_time;// 文书最后修改时间,
    private String mr_content;// 文书内容

    public String getFile_unique_id() {
        return file_unique_id;
    }

    public void setFile_unique_id(String file_unique_id) {
        this.file_unique_id = file_unique_id;
    }

    public String getDept_code() {
        return dept_code;
    }

    public void setDept_code(String dept_code) {
        this.dept_code = dept_code;
    }

    public String getMr_class_code() {
        return mr_class_code;
    }

    public void setMr_class_code(String mr_class_code) {
        this.mr_class_code = mr_class_code;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getFile_flag() {
        return file_flag;
    }

    public void setFile_flag(String file_flag) {
        this.file_flag = file_flag;
    }

    public String getCreator_name() {
        return creator_name;
    }

    public void setCreator_name(String creator_name) {
        this.creator_name = creator_name;
    }

    public String getCaption_date_time() {
        return caption_date_time;
    }

    public void setCaption_date_time(String caption_date_time) {
        this.caption_date_time = caption_date_time;
    }

    public String getLast_modify_date_time() {
        return last_modify_date_time;
    }

    public void setLast_modify_date_time(String last_modify_date_time) {
        this.last_modify_date_time = last_modify_date_time;
    }

    public String getMr_content() {
        return mr_content;
    }

    public void setMr_content(String mr_content) {
        this.mr_content = mr_content;
    }
}

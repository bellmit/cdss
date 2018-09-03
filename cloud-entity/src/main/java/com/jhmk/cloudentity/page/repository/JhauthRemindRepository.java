package com.jhmk.cloudentity.page.repository;

import com.jhmk.cloudentity.page.bean.JhauthRemind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * @author ziyu.zhou
 * @date 2018/7/6 17:32
 */

public interface JhauthRemindRepository extends JpaRepository<JhauthRemind, Integer>, JpaSpecificationExecutor<JhauthRemind> {

    /**
     * @param doctorId     医生id
     * @param patiendId    病人id
     * @param remindStatus 状态  1 提示 2忽略
     * @param remindTime   提醒时间
     * @return
     */
    @Query("select j  from JhauthRemind j where j.doctorId=?1 and j.patiendId=?2  and j.remindStatus=?3 and j.remindTime=?4")
    List<JhauthRemind> findJhauthRemind(String doctorId, String patiendId, String remindStatus, Date remindTime);


    @Query("select j  from JhauthRemind j where j.doctorId=?1 and j.remindStatus=?2 ")
    List<JhauthRemind> findJhauthRemindByDoctorId(String doctorId, String remindStatus);


    /**
     * 查询所有要提示的信息 大于当前时间的
     * @param doctorId
     * @param remindStatus
     * @param remindTime
     * @return
     */
    @Query("select j  from JhauthRemind j where j.doctorId=?1 and j.remindStatus=?2 and j.remindTime>=?3")
    List<JhauthRemind> findAllJhauthRemindByRemindTime(String doctorId, String remindStatus, Date remindTime);


    /**
     * 修改状态
     *
     * @param status
     * @param id
     */

    @Query("update JhauthRemind set remindStatus =?1 where id= ?2")
    void updateStatus(String status, int id);
}

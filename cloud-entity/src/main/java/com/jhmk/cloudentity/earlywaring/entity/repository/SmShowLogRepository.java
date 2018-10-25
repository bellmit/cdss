package com.jhmk.cloudentity.earlywaring.entity.repository;

import com.jhmk.cloudentity.earlywaring.entity.SmHospitalLog;
import com.jhmk.cloudentity.earlywaring.entity.SmShowLog;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface SmShowLogRepository extends PagingAndSortingRepository<SmShowLog, Integer>, JpaSpecificationExecutor<SmShowLog> {

    SmShowLog findFirstByDoctorIdAndPatientIdAndRuleIdAndVisitId(String doctorId, String patientId, String ruleId, String visitId);

    List<SmShowLog> findByDoctorIdAndRuleStatus(String doctorId, int ruleStatus);

    List<SmShowLog> findByDoctorIdAndPatientIdAndVisitIdOrderByDateDesc(String doctorId, String patientId, String visitId);

    @Modifying
    @Transactional
    @Query("update SmShowLog l set l.ruleStatus = ?1 where l.id = ?2")
    int update(int ruleStatus, int id);


    @Modifying
    @Transactional
    @Query("update SmShowLog l set l.ruleStatus = ?1  ,l.smHospitalLogId=?2 ,l.date=?3  where l.id = ?4")
    int updateSmHospitalById(int ruleStatus, int smHospitalLogId, String date, int id);


    @Modifying
    @Transactional
    @Query("update SmShowLog l set l.ruleStatus = ?1 where l.id = ?2")
    int updateNameById(String name, int id);

    SmShowLog findFirstByDoctorIdAndPatientIdAndItemNameAndTypeAndStatAndVisitId(String doctorId, String patientId, String itemName, String type, String stat, String visitId);

    /**
     * 查询推荐检查 此次状态为0的logs数据
     *
     * @param doctorId
     * @param patientId
     * @param visitId
     * @return
     */
    @Query("select  s from SmShowLog s where s.doctorId=?1 and s.patientId=?2 and s.visitId=?3 and s.type='rulematch' and s.ruleStatus=0")
    List<SmShowLog> findExistLogByRuleMatch(String doctorId, String patientId, String visitId);

    @Query("select  s from SmShowLog s where s.doctorId=?1 and s.patientId=?2 and s.visitId=?3 and s.type<>'rulematch' and s.ruleStatus=0")
    List<SmShowLog> findExistLog(String doctorId, String patientId, String visitId);


}

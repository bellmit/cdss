package com.jhmk.cloudentity.earlywaring.entity.repository;

import com.jhmk.cloudentity.earlywaring.entity.SmHospitalLog;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Date;
import java.util.List;

public interface SmHospitalLogRepository extends PagingAndSortingRepository<SmHospitalLog, Integer>, JpaSpecificationExecutor<SmHospitalLog> {


    @Query("select distinct (d.doctorId)from SmHospitalLog d ")
    long getDistinctDoctorIdCount();


    @Override
    long count();

    long countByWarnSource(String warnSource);

    /**
     * 根据部门 触发预警状态 日期 查询
     *
     * @param deptCode
     * @param startTime
     * @param endTime
     * @return
     */
    @Query("select l from  SmHospitalLog l  where l.deptCode=?1 and l.createTime>=?2 and l.createTime<=?3 ")
    List<SmHospitalLog> getAllByDeptAndYear(String deptCode, Date startTime, Date endTime);

    /**
     * 查询部门疾病触发统计
     *
     * @param deptCode
     * @param startTime
     * @param endTime
     * @return
     */
    @Query("select l.diagnosisName,count(1) from  SmHospitalLog l  where l.deptCode=?1  and l.createTime>=?2 and l.createTime<=?3 group by l.diagnosisName ")
// limit 10
    List<Object[]> getCountByDiagnosisNameAndDeptCode(String deptCode, Date startTime, Date endTime);


    /**
     * 统计时间范围内所有触发规则的信息
     *
     * @param startTime
     * @param endTime
     * @return
     */
    @Query("select l from  SmHospitalLog l  where l.createTime>=?1 and l.createTime<=?2 ")
    List<SmHospitalLog> getDeptCountByYear(Date startTime, Date endTime);



    /**
     *  todo 应尽量避免在 where 子句中对字段进行 null 值判断，否则将导致引擎放弃使用索引而进行全表扫描 解决方案 设置默认值 ！=0

     * 应尽量避免在 where 子句中使用!=或<>操作符，否则将引擎放弃使用索引而进行全表扫描。优化器将无法通过索引来确定将要命中的行数,因此需要搜索该表的所有行。
     * @return
     */
    @Query("select distinct (d.deptCode)from SmHospitalLog d where d.deptCode<>null")
    List<String> getCountByDistinctDeptCode();

    @Query("select distinct (d.deptCode)from SmHospitalLog d where d.deptCode<>null and  d.createTime>=?1 and d.createTime<=?2")
    List<String> getCountByDistinctDeptCodeAndDate(Date startTime, Date endTime);


    @Query("select distinct (d.doctorId),d.doctorName from SmHospitalLog d ")
    List<SmHospitalLog> getCountByDistinctDoctorId();


    List<SmHospitalLog> findAllByOrderByCreateTimeDesc(Specification specification);


}

package com.jhmk.cloudentity.cdss.pojo.repository;


import com.jhmk.cloudentity.cdss.pojo.SysHospitalDept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author ziyu.zhou
 * @date 2018/7/6 17:32
 */

public interface SysHospitalDeptRepository extends JpaRepository<SysHospitalDept, String>,JpaSpecificationExecutor<SysHospitalDept> {
    @Query("select s.deptName from SysHospitalDept s where s.inputCode like CONCAT('%',?1,'%')")
    List<String> findAllByInputCode(String deptCode);

    @Query("select s.deptName from SysHospitalDept s where s.deptName like CONCAT('%',?1,'%')and s.inputCode like CONCAT('%',?2,'%')")
    List<String> findAllByDeptNameAndInputCode(String deptName, String deptCode);
}

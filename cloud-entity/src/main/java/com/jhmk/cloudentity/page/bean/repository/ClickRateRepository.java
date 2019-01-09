package com.jhmk.cloudentity.page.bean.repository;

import com.jhmk.cloudentity.page.bean.ClickRate;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ClickRateRepository extends PagingAndSortingRepository<ClickRate, Integer>, JpaSpecificationExecutor<ClickRate> {

    ClickRate findByDoctorIdAndCreateTimeAndType(String doctorId, java.sql.Date createTime, String type);

    /**
     * 查询表中 所有医生id
     * @return
     */
    @Query("select distinct (c.doctorId) from ClickRate c ")
    List<String> getDistinctDoctorId();

    /**
     * 查询表中 所有医生id
     * @return
     */
    @Query("select distinct (c.type) from ClickRate c ")
    List<String> getDistinctType();

    /**
     * 查询表中 所有医生id
     * @return
     */
    @Query("select distinct (c.deptCode) from ClickRate c ")
    List<String> getDistinctByDeptCode();

}

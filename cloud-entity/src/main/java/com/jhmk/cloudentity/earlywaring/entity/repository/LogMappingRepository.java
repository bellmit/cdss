package com.jhmk.cloudentity.earlywaring.entity.repository;

import com.jhmk.cloudentity.earlywaring.entity.rule.LogMapping;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface LogMappingRepository extends PagingAndSortingRepository<LogMapping, Integer>, JpaSpecificationExecutor<LogMapping> {

//    @Query("SELECT m from LogMapping m where m.logId=?1 GROUP BY m.logObj,m.logResult,m.logTime,m.logOrderF,m.logOrderS")
//    List<LogMapping> findAllByLogId(int logId);
}

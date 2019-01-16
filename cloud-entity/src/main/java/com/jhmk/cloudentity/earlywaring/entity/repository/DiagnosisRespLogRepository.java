package com.jhmk.cloudentity.earlywaring.entity.repository;

import com.jhmk.cloudentity.earlywaring.entity.DiagnosisRespLog;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DiagnosisRespLogRepository extends PagingAndSortingRepository<DiagnosisRespLog, Integer>, JpaSpecificationExecutor<DiagnosisRespLog> {

}

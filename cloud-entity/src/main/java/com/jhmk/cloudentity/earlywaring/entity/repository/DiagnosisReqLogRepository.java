package com.jhmk.cloudentity.earlywaring.entity.repository;

import com.jhmk.cloudentity.earlywaring.entity.DiagnosisReqLog;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DiagnosisReqLogRepository extends PagingAndSortingRepository<DiagnosisReqLog, Integer>, JpaSpecificationExecutor<DiagnosisReqLog> {

}

package com.jhmk.cloudentity.earlywaring.entity.repository;

import com.jhmk.cloudentity.earlywaring.entity.SmEvaluate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SmEvaluateRepository extends JpaRepository<SmEvaluate, Integer>, JpaSpecificationExecutor<SmEvaluate> {
    //删除
    @Modifying
    @Query("update SmEvaluate u set u.status = ?1 where u.id = ?2")
    int setStatus(String status, String id);


}

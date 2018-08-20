package com.jhmk.clouddatasource.entity.repository;

import com.jhmk.clouddatasource.entity.PatDiagnosis;
import com.jhmk.clouddatasource.entity.PatMasterIndex;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author ziyu.zhou
 * @date 2018/7/25 10:00
 */

public interface PatMasterIndexRepository extends JpaRepository<PatDiagnosis,String> {
    List<PatMasterIndex> findByPatientId(String patient_id);
}
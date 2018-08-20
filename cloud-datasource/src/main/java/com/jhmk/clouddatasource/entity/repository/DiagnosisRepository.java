package com.jhmk.clouddatasource.entity.repository;

import com.jhmk.clouddatasource.entity.Diagnosis;
import com.jhmk.clouddatasource.entity.PatDiagnosis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author ziyu.zhou
 * @date 2018/7/25 10:00
 */

public interface DiagnosisRepository extends JpaRepository<Diagnosis,String> {
    List<Diagnosis> findAllByPatientIdAndVisitId(String patient_id, Long visit_id);
}

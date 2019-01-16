package com.jhmk.cloudentity.earlywaring.entity.repository.service;

import com.jhmk.cloudentity.base.BaseRepService;
import com.jhmk.cloudentity.earlywaring.entity.DiagnosisRespLog;
import com.jhmk.cloudentity.earlywaring.entity.repository.DiagnosisRespLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DiagnosisRespLogService extends BaseRepService<DiagnosisRespLog, Integer> {
    @Autowired
    DiagnosisRespLogRepository diagnosisRespLogRepository;


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public DiagnosisRespLog save(DiagnosisRespLog respLog) {
        return diagnosisRespLogRepository.save(respLog);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Iterable<DiagnosisRespLog> save(List<DiagnosisRespLog> list) {
        return diagnosisRespLogRepository.save(list);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Integer id) {
        diagnosisRespLogRepository.delete(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(DiagnosisRespLog respLog) {
        diagnosisRespLogRepository.delete(respLog);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(List<DiagnosisRespLog> list) {
        diagnosisRespLogRepository.delete(list);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public DiagnosisRespLog findOne(Integer id) {
        return diagnosisRespLogRepository.findOne(id);
    }


    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Iterable<DiagnosisRespLog> findAll() {
        return diagnosisRespLogRepository.findAll();
    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<DiagnosisRespLog> findAll(Pageable pageable) {
        return diagnosisRespLogRepository.findAll(pageable);
    }

}

package com.jhmk.cloudentity.earlywaring.entity.repository.service;

import com.jhmk.cloudentity.base.BaseRepService;
import com.jhmk.cloudentity.earlywaring.entity.DiagnosisReqLog;
import com.jhmk.cloudentity.earlywaring.entity.repository.DiagnosisReqLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DiagnosisReqLogService extends BaseRepService<DiagnosisReqLog, Integer> {
    @Autowired
    DiagnosisReqLogRepository diagnosisReqLogRepository;


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public DiagnosisReqLog save(DiagnosisReqLog reqLog) {
        return diagnosisReqLogRepository.save(reqLog);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Iterable<DiagnosisReqLog> save(List<DiagnosisReqLog> list) {
        return diagnosisReqLogRepository.save(list);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Integer id) {
        diagnosisReqLogRepository.delete(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(DiagnosisReqLog reqLog) {
        diagnosisReqLogRepository.delete(reqLog);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(List<DiagnosisReqLog> list) {
        diagnosisReqLogRepository.delete(list);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public DiagnosisReqLog findOne(Integer id) {
        return diagnosisReqLogRepository.findOne(id);
    }


    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Iterable<DiagnosisReqLog> findAll() {
        return diagnosisReqLogRepository.findAll();
    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<DiagnosisReqLog> findAll(Pageable pageable) {
        return diagnosisReqLogRepository.findAll(pageable);
    }

}

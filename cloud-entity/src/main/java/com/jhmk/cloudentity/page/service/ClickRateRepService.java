package com.jhmk.cloudentity.page.service;

import com.jhmk.cloudentity.base.BaseRepService;
import com.jhmk.cloudentity.page.bean.ClickRate;
import com.jhmk.cloudentity.page.repository.ClickRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ClickRateRepService extends BaseRepService<ClickRate, Integer> {
    @Autowired
    ClickRateRepository repository;


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ClickRate save(ClickRate basicInfo) {
        return repository.save(basicInfo);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Iterable<ClickRate> save(List<ClickRate> list) {
        return repository.save(list);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(ClickRate ClickRate) {
        repository.delete(ClickRate);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(List<ClickRate> list) {
        repository.delete(list);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public ClickRate findOne(Integer id) {
        return repository.findOne(id);
    }


    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Iterable<ClickRate> findAll() {
        return repository.findAll();
    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<ClickRate> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public ClickRate findByDoctorIdAndCreateTimeAndType(String doctorId, String createTime, String type) {
        return repository.findByDoctorIdAndCreateTimeAndType(doctorId, createTime, type);
    }


}

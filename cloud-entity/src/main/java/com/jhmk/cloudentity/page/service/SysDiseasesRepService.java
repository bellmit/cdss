package com.jhmk.cloudentity.page.service;


import com.jhmk.cloudentity.base.BaseRepService;
import com.jhmk.cloudentity.page.bean.SysDiseases;
import com.jhmk.cloudentity.page.repository.SysDiseasesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysDiseasesRepService extends BaseRepService<SysDiseases, Integer> {
    @Autowired
    SysDiseasesRepository repository;


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public SysDiseases save(SysDiseases binganshouye) {
        return repository.save(binganshouye);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Iterable<SysDiseases> save(List<SysDiseases> list) {
        return repository.save(list);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(SysDiseases SysDiseases) {
        repository.delete(SysDiseases);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(List<SysDiseases> list) {
        repository.delete(list);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public SysDiseases findOne(Integer id) {
        return repository.findOne(id);
    }


    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Iterable<SysDiseases> findAll() {
        return repository.findAll();
    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<SysDiseases> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }


    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<String> findAllByEngDiseases(String enddiseases) {
        return repository.findAllByEngDiseases(enddiseases);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<String> findAllByChinaDiseasesAndEngDiseases(String chinese, String enddiseases) {
        return repository.findAllByChinaDiseasesAndEngDiseases(chinese, enddiseases);
    }
}

package com.jhmk.cloudentity.page.bean.repository.service;

import com.jhmk.cloudentity.base.BaseRepService;
import com.jhmk.cloudentity.page.bean.UserAdvice;
import com.jhmk.cloudentity.page.bean.repository.UserAdviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserAdviceRepService extends BaseRepService<UserAdvice, Integer> {
    @Autowired
    UserAdviceRepository repository;


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserAdvice save(UserAdvice basicInfo) {
        return repository.save(basicInfo);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Iterable<UserAdvice> save(List<UserAdvice> list) {
        return repository.save(list);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(UserAdvice UserAdvice) {
        repository.delete(UserAdvice);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(List<UserAdvice> list) {
        repository.delete(list);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public UserAdvice findOne(Integer id) {
        return repository.findOne(id);
    }


    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Iterable<UserAdvice> findAll() {
        return repository.findAll();
    }


    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<UserAdvice> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    //筛选列表
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<UserAdvice> findAll(Specification specification) {
        return repository.findAll(specification);
    }
}

package com.jhmk.cloudentity.page.bean.repository.service;

import com.jhmk.cloudentity.base.BaseRepService;
import com.jhmk.cloudentity.page.bean.ClickRate;
import com.jhmk.cloudentity.page.bean.repository.ClickRateRepository;
import com.jhmk.cloudutil.model.WebPage;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    //筛选列表
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<ClickRate> findAll(Specification specification) {
        return repository.findAll(specification);
    }


    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public ClickRate findByDoctorIdAndCreateTimeAndType(String doctorId, java.sql.Date createTime, String type) {
        return repository.findByDoctorIdAndCreateTimeAndType(doctorId, createTime, type);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<String> getDistinctDoctorId() {
        return repository.getDistinctDoctorId();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<String> getDistinctType() {
        return repository.getDistinctType();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<String> getDistinctByDeptCode() {
        return repository.getDistinctByDeptCode();
    }

    public List<ClickRate> getDataByCondition(Date start, Date end, Map param) {
        Specification sf = getWhereClause(start, end, param);
        List<ClickRate> all = this.findAll(sf);
        return all;
    }

    public Specification<T> getWhereClause(Date startTime, Date endTime, Map<String, Object> params) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<Predicate>();

                if (startTime != null) {
                    list.add(cb.greaterThanOrEqualTo(root.get("createTime").as(Date.class), startTime));

                }
                if (endTime != null) {
                    list.add(cb.lessThanOrEqualTo(root.get("createTime").as(Date.class), endTime));
                }
//                //拼接传入参数
                if (params != null) {
                    for (String key : params.keySet()) {
                        if (WebPage.PAGE_NUM.equals(key)) {
                            continue;
                        } else {
                            Object value = params.get(key);
                            if (!org.springframework.util.StringUtils.isEmpty(value.toString())) {
                                list.add(cb.equal(root.get(key), value));
                            }
                        }
                    }
                }

                Predicate[] p = new Predicate[list.size()];
                list.toArray(p);
                return cb.and(p);

            }
        };
    }

}

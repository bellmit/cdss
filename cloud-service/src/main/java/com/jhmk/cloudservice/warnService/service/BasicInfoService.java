package com.jhmk.cloudservice.warnService.service;


import com.jhmk.cloudentity.earlywaring.entity.repository.service.BasicInfoRepService;
import com.jhmk.cloudentity.earlywaring.entity.rule.BasicInfo;
import com.jhmk.cloudentity.earlywaring.entity.rule.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ziyu.zhou
 * @date 2018/7/30 13:57
 */

@Service
public class BasicInfoService {
    @Autowired
    BasicInfoRepService basicInfoRepService;

    /**
     * 添加、覆盖规则基本信息 如果存在 就执行先删除 在添加操作
     *
     * @param rule
     */
    @Transactional
    public void saveAndFlush(Rule rule) {
        String patient_id = rule.getPatient_id();
        String visit_id = rule.getVisit_id();
        String doctor_id = rule.getDoctor_id();
        BasicInfo byPatient_idAndVisit_id = basicInfoRepService.findByPatient_idAndVisit_id(patient_id, visit_id);
        if (byPatient_idAndVisit_id == null || (byPatient_idAndVisit_id.getDoctor_id()==null&&doctor_id!=null)) {
            basicInfoRepService.deleteByPatient_idAndVisit_id(patient_id, visit_id);
            String pageSource = rule.getPageSource();
            String warnSource = rule.getWarnSource();
            String doctor_name = rule.getDoctor_name();
            String dept_code = rule.getDept_code();
            //基本信息
            BasicInfo basicInfo = new BasicInfo();
            basicInfo.setPatient_id(patient_id);
            basicInfo.setVisit_id(visit_id);
            basicInfo.setDept_name(dept_code);
            basicInfo.setDoctor_id(doctor_id);
            basicInfo.setPageSource(pageSource);
            basicInfo.setWarnSource(warnSource);
            basicInfo.setDoctor_name(doctor_name);
            basicInfoRepService.save(basicInfo);
        }

    }

}

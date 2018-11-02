package com.jhmk.cloudservice.warnService.service;


import com.jhmk.cloudentity.earlywaring.entity.repository.service.BasicInfoRepService;
import com.jhmk.cloudentity.earlywaring.entity.repository.service.BinganshouyeRepService;
import com.jhmk.cloudentity.earlywaring.entity.rule.BasicInfo;
import com.jhmk.cloudentity.earlywaring.entity.rule.Binganshouye;
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
public class BinganshouyeService {
    @Autowired
    BinganshouyeRepService binganshouyeRepService;

    /**
     * 添加、覆盖规则基本信息 如果存在 就执行先删除 在添加操作
     *
     * @param rule
     */
    @Transactional
    public void saveAndFlush(Rule rule) {
        String patient_id = rule.getPatient_id();
        String visit_id = rule.getVisit_id();
//        List<Binganshouye> lessThanVisit_id = binganshouyeRepService.findLessThanVisit_id(patient_id, visit_id);
//        if (lessThanVisit_id.size() > 0) {
//            binganshouyeRepService.delete(lessThanVisit_id);
//        }
        binganshouyeRepService.deleteByPatient_idAndVisit_id(patient_id, visit_id);

        Binganshouye binganshouye = rule.getBinganshouye();
        binganshouyeRepService.save(binganshouye);
    }

}

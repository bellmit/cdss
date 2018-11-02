package com.jhmk.cloudservice.warnService.service;


import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudentity.earlywaring.entity.repository.service.BinglizhenduanRepService;
import com.jhmk.cloudentity.earlywaring.entity.repository.service.ShouyezhenduanRepService;
import com.jhmk.cloudentity.earlywaring.entity.rule.Binglizhenduan;
import com.jhmk.cloudentity.earlywaring.entity.rule.Rule;
import com.jhmk.cloudentity.earlywaring.entity.rule.Shouyezhenduan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ziyu.zhou
 * @date 2018/7/30 14:06
 */
@Service
public class ZhenduanService {
    private static final Logger logger = LoggerFactory.getLogger(ZhenduanService.class);

    @Autowired
    BinglizhenduanRepService binglizhenduanRepService;
    @Autowired
    ShouyezhenduanRepService shouyezhenduanRepService;

    @Transactional
    public void saveAndFlush(Rule rule) {
        String patient_id = rule.getPatient_id();
        String visit_id = rule.getVisit_id();
        List<Binglizhenduan> binglizhenduan = rule.getBinglizhenduan();
        logger.info("保存病历诊断为:{}", JSONObject.toJSONString(binglizhenduan));
        if (binglizhenduan != null) {
            binglizhenduanRepService.deleteByPatient_idAndVisit_id(patient_id, visit_id);
            binglizhenduanRepService.save(binglizhenduan);
        }
        List<Shouyezhenduan> shouyezhenduan = rule.getShouyezhenduan();
        if (shouyezhenduan != null) {
            shouyezhenduanRepService.deleteByPatient_idAndVisit_id(patient_id, visit_id);
            shouyezhenduanRepService.save(shouyezhenduan);
        }
    }


}

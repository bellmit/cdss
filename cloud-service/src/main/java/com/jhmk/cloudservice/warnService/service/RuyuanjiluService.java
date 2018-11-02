package com.jhmk.cloudservice.warnService.service;


import com.jhmk.cloudentity.earlywaring.entity.repository.service.RuyuanjiluRepService;
import com.jhmk.cloudentity.earlywaring.entity.rule.Rule;
import com.jhmk.cloudentity.earlywaring.entity.rule.Ruyuanjilu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ziyu.zhou
 * @date 2018/7/30 14:18
 */

@Service
public class RuyuanjiluService {
    @Autowired
    RuyuanjiluRepService ruyuanjiluRepService;

    @Transactional
    public void saveAndFlush(Rule rule) {
        String patient_id = rule.getPatient_id();
        String visit_id = rule.getVisit_id();
//        List<Ruyuanjilu> allByPatientIdAndVisitId = ruyuanjiluRepService.findLessThanVisit_id(patient_id, visit_id);


//        if (allByPatientIdAndVisitId != null&&allByPatientIdAndVisitId.size()>0) {
//            ruyuanjiluRepService.delete(allByPatientIdAndVisitId);
//        }
        ruyuanjiluRepService.deleteByPatient_idAndVisit_id(patient_id, visit_id);

        Ruyuanjilu ruyuanjilu = rule.getRuyuanjilu();
        if (ruyuanjilu != null ) {
            ruyuanjiluRepService.save(ruyuanjilu);
        }
    }
}

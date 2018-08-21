package com.jhmk.cloudpage.ruleService;

import com.jhmk.cloudentity.cdss.bean.Misdiagnosis;
import com.jhmk.cloudutil.config.CdssConstans;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.jhmk.cloudpage.config.MongoUtils.getCollection;


/**
 * @author ziyu.zhou
 * @date 2018/7/31 14:24
 */
@Service
public class BasyService {
    MongoCollection<Document> binganshouye = getCollection(CdssConstans.DATASOURCE, CdssConstans.BINGANSHOUYE);

    /**
     * 获取所有骨科信息
     *
     * @return
     */
    public List<Misdiagnosis> getGukeData() {
        List<Misdiagnosis> misdiagnosisList = new LinkedList<>();
        List<Document> countPatientId = Arrays.asList(
                new Document("$project", new Document("patient_id", 1).append("_id", 1).append("visit_id", 1).append("binganshouye", 1))
//                , new Document("$skip", 5000),
//                new Document("$limit", 10000)
        );
        AggregateIterable<Document> output = binganshouye.aggregate(countPatientId);
        for (Document document : output) {
            Misdiagnosis misdiagnosis = new Misdiagnosis();
            if (document == null) {
                continue;
            }
            misdiagnosis.setPatient_id(document.getString("patient_id"));
            misdiagnosis.setVisit_id(document.getString("visit_id"));
            misdiagnosis.setId(document.getString("_id"));
            Document binganshouye = (Document) document.get("binganshouye");
            Document patVisit = (Document) binganshouye.get("pat_visit");

            misdiagnosis.setDept_discharge_from_name(patVisit.getString("dept_admission_to_name"));
            misdiagnosis.setDistrict_discharge_from_name(patVisit.getString("district_discharge_from_name"));
            if (patVisit.getString("dept_admission_to_name").contains("骨科")) {
                misdiagnosisList.add(misdiagnosis);
            }
        }
        return misdiagnosisList;
    }


    public Set<String> getAllDepts() {
        Set<String> names = new HashSet<>();
        List<Document> countPatientId = Arrays.asList(
                new Document("$project", new Document("patient_id", 1).append("_id", 1).append("visit_id", 1).append("binganshouye", 1))
                , new Document("$skip", CdssConstans.BEGINCOUNT),
                new Document("$limit", CdssConstans.ENDCOUNT)
        );
        AggregateIterable<Document> output = binganshouye.aggregate(countPatientId);
        for (Document document : output) {
            if (document == null) {
                continue;
            }
            Document binganshouye = (Document) document.get("binganshouye");
            Document patVisit = (Document) binganshouye.get("pat_visit");

            names.add(patVisit.getString("dept_admission_to_name"));
        }
        return names;
    }

}

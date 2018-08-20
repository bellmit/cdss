package com.jhmk.cloudcdss.ruleService;

import com.jhmk.cloudutil.config.CdssConstans;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.jhmk.cloudutil.util.MongoUtils.getCollection;


/**
 * @author ziyu.zhou
 * @date 2018/7/31 14:19
 */
//首页诊断

@Service
public class SyzdService {
    MongoCollection<Document> shouyezhenduan = getCollection(CdssConstans.DATASOURCE, CdssConstans.SHOUYEZHENDUAN);

    /**
     * 获取首页诊断疾病集合
     * @param id
     * @return
     */
    public List<String> getDiseaseList(String id) {
        List<String> list = new ArrayList<>();

        List<Document> countPatientId = Arrays.asList(
                new Document("$unwind", "$shouyezhenduan"),
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("_id", 1).append("patient_id", 1).append("visit_id", 1).append("shouyezhenduan", 1))
        );
        AggregateIterable<Document> binli = shouyezhenduan.aggregate(countPatientId);
        for (Document document : binli) {
            Document binglizhenduan = (Document) document.get("shouyezhenduan");
            String diagnosis_name = binglizhenduan.getString("diagnosis_name");
            list.add(diagnosis_name);
        }
        return list;
    }

}

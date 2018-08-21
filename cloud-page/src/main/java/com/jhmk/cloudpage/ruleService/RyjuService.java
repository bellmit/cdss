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
 * @date 2018/7/31 13:56
 * r入院记录
 */

@Service
public class RyjuService {
    MongoCollection<Document> binganshouye = getCollection(CdssConstans.DATASOURCE, CdssConstans.BINGANSHOUYE);
    //入院记录
    static MongoCollection<Document> ruyuanjilu = getCollection(CdssConstans.DATASOURCE, CdssConstans.RUYUANJILU);


    /**
     * 根据id获取既往史
     *
     * @param id
     * @return
     */

    public Misdiagnosis getJWSdieases(String id) {
        Misdiagnosis jiwangshi = new Misdiagnosis();
        List<String> jwDiseases = new LinkedList<>();
        List<Document> countPatientId2 = Arrays.asList(
                //过滤数据
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("patient_id", 1).append("visit_id", 1).append("ruyuanjilu.history_of_past_illness", 1)
                )

        );
        AggregateIterable<Document> output = ruyuanjilu.aggregate(countPatientId2);

        for (Document document : output) {

            Document ruyuanjilu = (Document) document.get("ruyuanjilu");
            String patient_id = document.getString("patient_id");
            String visit_id = document.getString("visit_id");

            if (ruyuanjilu == null) {
                continue;
            }

            //既往史
            Document history_of_past_illness = (Document) ruyuanjilu.get("history_of_past_illness");
            if (Objects.nonNull(history_of_past_illness)) {
                String src = history_of_past_illness.getString("src");
                if (Objects.nonNull(history_of_past_illness.get("disease"))) {
                    continue;
                }
                ArrayList<Document> array = history_of_past_illness.get("disease", ArrayList.class);
                if (Objects.nonNull(array)) {

                    for (Document doc : array) {
                        String disease_name = doc.getString("disease_name");
                        jwDiseases.add(disease_name);
                    }
                    jiwangshi.setId(id);
                    jiwangshi.setPatient_id(patient_id);
                    jiwangshi.setVisit_id(visit_id);
                    jiwangshi.setHisDiseaseList(jwDiseases);
                    jiwangshi.setSrc(src);
                }
            }
        }

        return jiwangshi;
    }


}

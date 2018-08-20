package com.jhmk.cloudcdss.ruleService;

import com.jhmk.cloudentity.cdss.bean.Misdiagnosis;
import com.jhmk.cloudutil.config.CdssConstans;
import com.jhmk.cloudutil.util.MongoUtils;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.jhmk.cloudutil.util.MongoUtils.getCollection;


/**
 * @author ziyu.zhou
 * @date 2018/8/7 13:42
 */

@Service
public class MenzhenzhenduanService {
    MongoCollection<Document> binganshouye = getCollection(CdssConstans.DATASOURCE, CdssConstans.BINGANSHOUYE);
    //入院记录
    static MongoCollection<Document> menzhenzhenduan = getCollection(CdssConstans.DATASOURCE, CdssConstans.MENZHENZHENDUAN);
    static MongoCollection<Document> menzhenshuju = getCollection(CdssConstans.DATASOURCE, CdssConstans.MENZHENSHUJU);
    static MongoCollection<Document> mzjzjl = getCollection(CdssConstans.DATASOURCE, CdssConstans.mzjzjl);

    /**
     * 根据id获取既往史
     *
     * @param dept 获取指定科室的id集合
     * @return
     */

    public List<String> get(String dept) {
        MongoCollection<Document> collection = getCollection(CdssConstans.DATASOURCE, CdssConstans.mzjzjl);
//        Pattern pattern = Pattern.compile("^.*耳鼻.*$", Pattern.CASE_INSENSITIVE);
        List<String> idList = new LinkedList<>();
        List<Document> countPatientId2 = Arrays.asList(
                //过滤数据
                new Document("$match", new Document("menzhenjiuzhenjilu.visit_dept_name", dept))
//                , new Document("$skip", 5000),
//                new Document("$limit", 10000)

        );
//        BasicDBObject query = new BasicDBObject();
//        //加入查询条件
//        query.put("menzhenjiuzhenjilu.visit_dept_name", pattern);
//        //按名次升序排序
//        BasicDBObject sort = new BasicDBObject();
//        // 1,表示正序； －1,表示倒序
//        FindIterable<Document> iterable = collection.find(query);
//
//        MongoCursor<Document> iterator = iterable.iterator();
//        while (iterator.hasNext()) {
//            System.out.println(iterator.next());
//        }
        AggregateIterable<Document> output = mzjzjl.aggregate(countPatientId2);

        for (Document document : output) {

            String id = document.getString("_id");
            idList.add(id);
        }
        return idList;
    }

    /**
     * 根据id获取门诊数据
     *
     * @param id
     * @return
     */
    public Misdiagnosis getMenzhenzhenduan(String id) {
        Misdiagnosis jiwangshi = new Misdiagnosis();
        List<String> jwDiseases = new LinkedList<>();
        List<Document> countPatientId2 = Arrays.asList(
                //过滤数据
                new Document("$match", new Document("_id", id)),
                new Document("$unwind", "$menzhenzhenduan")
        );
        AggregateIterable<Document> output = menzhenzhenduan.aggregate(countPatientId2);

        for (Document document : output) {

            Document menzhenzhenduanDoc = (Document) document.get("menzhenzhenduan");
            if (menzhenzhenduanDoc != null) {
                String diagnosis_desc = menzhenzhenduanDoc.getString("diagnosis_desc");
                String diagnosis_name = menzhenzhenduanDoc.getString("diagnosis_name");

            }
        }

        return jiwangshi;
    }

    private final String all = "既有诊断名称又有诊断描述的数量";
    private final String name = "只有诊断名称的数量";
    private final String desc = "只有诊断描述的数量";
    private final String allnot = "两者都没有的数量";
    private final String error = "门诊诊断是空的";

    public Map<String, Integer> getMenzhenzhenduanByIdList(List<String> idList) {
        Map<String, Integer> map = new HashMap<>();
        map.put(all, 0);
        map.put(name, 0);
        map.put(desc, 0);
        map.put(allnot, 0);
        map.put(error, 0);
        Misdiagnosis jiwangshi = new Misdiagnosis();
        List<String> jwDiseases = new LinkedList<>();
        for (String id : idList) {

            List<Document> countPatientId2 = Arrays.asList(
                    //过滤数据
                    new Document("$match", new Document("_id", id)),
                    new Document("$unwind", "$menzhenzhenduan")
            );
            AggregateIterable<Document> output = menzhenzhenduan.aggregate(countPatientId2);

            for (Document document : output) {

                Document menzhenzhenduanDoc = (Document) document.get("menzhenzhenduan");
                if (menzhenzhenduanDoc != null) {
                    String diagnosis_desc = menzhenzhenduanDoc.getString("diagnosis_desc");
                    String diagnosis_name = menzhenzhenduanDoc.getString("diagnosis_name");
                    String diagnosis_num = menzhenzhenduanDoc.getString("diagnosis_num");
                    if ("1".equals(diagnosis_num)) {

                        //既有诊断名称又有诊断描述的数量
                        if (StringUtils.isNotBlank(diagnosis_name) && StringUtils.isNotBlank(diagnosis_desc)) {
                            map.put(all, map.get(all) + 1);

                            //只有诊断名称的数量
                        } else if (StringUtils.isNotBlank(diagnosis_name) && StringUtils.isBlank(diagnosis_desc)) {
                            map.put(name, map.get(name) + 1);

                            //只有诊断描述的数量
                        } else if (StringUtils.isBlank(diagnosis_name) && StringUtils.isNotBlank(diagnosis_desc)) {
                            map.put(desc, map.get(desc) + 1);

                            //两者都没有的数量
                        } else if (StringUtils.isBlank(diagnosis_name) && StringUtils.isBlank(diagnosis_desc)) {
                            map.put(allnot, map.get(allnot) + 1);
                        } else {
                            map.put(error, map.get(error) + 1);

                        }
                    }

                }
            }
        }


        return map;
    }


    public Map<String, Integer> getDiagnosisCountByIdList(List<String> idList) {
        Map<String, Integer> map = new HashMap<>();
        for (String id : idList) {
            List<Document> countPatientId2 = Arrays.asList(
                    //过滤数据
                    new Document("$match", new Document("_id", id)),
                    new Document("$unwind", "$menzhenzhenduan")
            );
            AggregateIterable<Document> output = menzhenzhenduan.aggregate(countPatientId2);
            for (Document document : output) {
                Document menzhenzhenduanDoc = (Document) document.get("menzhenzhenduan");
                if (menzhenzhenduanDoc != null) {
                    String diagnosis_desc = menzhenzhenduanDoc.getString("diagnosis_desc");
                    String diagnosis_name = menzhenzhenduanDoc.getString("diagnosis_name");
                    String diagnosis_num = menzhenzhenduanDoc.getString("diagnosis_num");
                    if ("1".equals(diagnosis_num)) {
                        if (StringUtils.isNotBlank(diagnosis_name)) {
                            if (map.containsKey(diagnosis_name)) {
                                map.put(diagnosis_name, map.get(diagnosis_name) + 1);
                            } else {
                                map.put(diagnosis_name, 1);
                            }
                        } else {
                            if (StringUtils.isNotBlank(diagnosis_desc)) {
                                if (map.containsKey(diagnosis_desc)) {
                                    map.put(diagnosis_desc, map.get(diagnosis_desc) + 1);
                                } else {
                                    map.put(diagnosis_desc, 1);
                                }
                            }
                        }
                    }
                }
            }
        }
        return map;
    }

}

package com.jhmk.cloudpage.service;

import com.jhmk.cloudpage.config.MongoUtils;
import com.jhmk.cloudentity.cdss.bean.MenZhen;
import com.jhmk.cloudutil.config.CdssConstans;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static com.jhmk.cloudpage.config.MongoUtils.getCollection;


/**
 * @author ziyu.zhou
 * @date 2018/7/29 17:24
 */
@Service
public class TestService {

    MongoCollection<Document> binganshouye = getCollection(CdssConstans.DATASOURCE, CdssConstans.BINGANSHOUYE);
    //入院记录
    static MongoCollection<Document> ruyuanjilu = getCollection(CdssConstans.DATASOURCE, CdssConstans.RUYUANJILU);
    static MongoCollection<Document> yizhu = getCollection(CdssConstans.DATASOURCE, CdssConstans.YIZHU);
    //病理诊断 初诊
    MongoCollection<Document> binglizhenduan = getCollection(CdssConstans.DATASOURCE, CdssConstans.BINGLIZHENDUAN);
    MongoCollection<Document> shouyezhenduan = getCollection(CdssConstans.DATASOURCE, CdssConstans.SHOUYEZHENDUAN);
    MongoCollection<Document> jianchabaogao = getCollection(CdssConstans.DATASOURCE, CdssConstans.JCBG);
    MongoCollection<Document> jianyanbaogao = getCollection(CdssConstans.DATASOURCE, CdssConstans.JYBG);
    static MongoCollection<Document> menzhenshuju = getCollection(CdssConstans.DATASOURCE, CdssConstans.MENZHENSHUJU);
    static MongoCollection<Document> menzhenzhenduan = getCollection(CdssConstans.DATASOURCE, CdssConstans.MENZHENZHENDUAN);


    //获取门诊数量
    public int getAllMenZhenData() {

        int count = MongoUtils.getCount(CdssConstans.MENZHENSHUJU);
        return count;
    }

//
//    public static void main(String[] args) {
////        int count = MongoUtils.getCount(CdssConstans.MENZHENSHUJU);
////        System.out.println(count);
//
//        List<MenZhen> menZhenData = getMenZhenData();
//        System.out.println(menZhenData);
//    }


//    public static void main(String[] args) {
////        Map<String, Integer> map = getmapData();
//        write2File();
//    }


    public  void write2File() {
        BufferedWriter bufferedWriter = null;
        File file = new File("/data/1/CDSS/3院门诊数量统计.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
            Map<String, Integer> map = getmapData();
            for (Map.Entry<String,Integer> entry:map.entrySet()) {
                String s = entry.getKey() + "," + entry.getValue();
                System.out.println(s);
                bufferedWriter.write(s);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Map<String, Integer> getmapData() {
        Map<String, Integer> num = new HashMap<>();
//        Map<String, Integer> allNum = new HashMap<>();
//        Map<String, Integer> chuzhenNum = new HashMap<>();
//        Map<String, Integer> fuzhenNum = new HashMap<>();
//        Map<String, Integer> keshiNum = new HashMap<>();
        List<Document> countPatientId = Arrays.asList(
//                new Document("$skip", 0),
//                new Document("$limit", 300)
        );

        int allcount = MongoUtils.getCount(CdssConstans.MENZHENZHENDUAN);
        num.put("allNum", allcount);

        AggregateIterable<Document> menzhen = menzhenshuju.aggregate(countPatientId);
        for (Document document : menzhen) {
            Map<String, String> map = new HashMap<>();
            Document menzhenshuju = (Document) document.get("menzhenshuju");
            String id = document.getString("_id");
            String patient_id = document.getString("patient_id");
            String visit_id = document.getString("visit_id");
            if ("1".equals(visit_id)) {
                num.put("chuzhenNum", (num.get("chuzhenNum") == null ? 0 : num.get("chuzhenNum")) + 1);
            } else {
                num.put("fuzhenNum", (num.get("fuzhenNum") == null ? 0 : num.get("fuzhenNum")) + 1);
            }


            String batchno = document.getString("batchno");
            MenZhen menZhen = new MenZhen();

            if (Objects.nonNull(menzhenshuju)) {
                Object chief_complaint = menzhenshuju.get("chief_complaint");
                if (Objects.nonNull(chief_complaint)) {
                    String src = ((Document) chief_complaint).getString("src");
                    menZhen.setChief_complaint(src);
                    num.put("不为空", (num.get("不为空") == null ? 0 : num.get("不为空")) + 1);

                }
            }
            if (Objects.nonNull(menzhenshuju)) {
                Object pat_visit = menzhenshuju.get("pat_visit");
                if (Objects.nonNull(pat_visit)) {
                    String dept_admission_to_name = ((Document) pat_visit).getString("dept_admission_to_name");
                    menZhen.setDept_code(dept_admission_to_name);
                    if (num.containsKey(dept_admission_to_name)) {
                        num.put(dept_admission_to_name, num.get(dept_admission_to_name) + 1);
                    } else {
                        num.put(dept_admission_to_name, 1);
                    }
                }
            }

        }
        return num;

    }

    public List<MenZhen> getMenZhenData() {
        List<MenZhen> list = new LinkedList<>();

        List<Document> countPatientId = Arrays.asList(
//                new Document("$skip", 0),
//                new Document("$limit", 200)
        );
        AggregateIterable<Document> menzhen = menzhenshuju.aggregate(countPatientId);
        for (Document document : menzhen) {
            Map<String, String> map = new HashMap<>();
            Document menzhenshuju = (Document) document.get("menzhenshuju");
            String id = document.getString("_id");
            String patient_id = document.getString("patient_id");
            String visit_id = document.getString("visit_id");
            String batchno = document.getString("batchno");
            MenZhen menZhen = new MenZhen();

            if (Objects.nonNull(menzhenshuju)) {
                Object chief_complaint = menzhenshuju.get("chief_complaint");
                if (Objects.nonNull(chief_complaint)) {
                    String src = ((Document) chief_complaint).getString("src");
                    menZhen.setChief_complaint(src);
                }
            }
            if (Objects.nonNull(menzhenshuju)) {
                Object pat_visit = menzhenshuju.get("pat_visit");
                if (Objects.nonNull(pat_visit)) {
                    String dept_admission_to_name = ((Document) pat_visit).getString("dept_admission_to_name");
                    menZhen.setDept_code(dept_admission_to_name);
                }
            }
            menZhen.setId(id);
            menZhen.setPatient_id(patient_id);
            menZhen.setVisit_id(visit_id);
            menZhen.setBatchno(batchno);
            String menZhenZhenDuan = getMenZhenZhenDuan(id);
            menZhen.setDiagnosis(menZhenZhenDuan);
            list.add(menZhen);
        }
        return list;
    }

    //获取诊断名
    public String getMenZhenZhenDuan(String id) {

        List<Document> countPatientId = Arrays.asList(
                new Document("$match", new Document("_id", id)),
                new Document("$unwind", "$menzhenzhenduan"),
                new Document("$project", new Document("menzhenzhenduan", 1))
        );
        String name = "";
        AggregateIterable<Document> doc = menzhenzhenduan.aggregate(countPatientId);
        for (Document document : doc) {
            Object menzhenzhenduan = document.get("menzhenzhenduan");
            if (Objects.nonNull(menzhenzhenduan)) {
                String diagnosis_num = ((Document) menzhenzhenduan).getString("diagnosis_num");
                String diagnosis_name = ((Document) menzhenzhenduan).getString("diagnosis_name");
                if ("1".equals(diagnosis_num)) {
                    name = diagnosis_name;
                    continue;
                }
            }

        }

        return name;
    }

}

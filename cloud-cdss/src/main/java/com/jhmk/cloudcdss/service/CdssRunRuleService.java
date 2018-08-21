package com.jhmk.cloudcdss.service;

import com.jhmk.cloudentity.cdss.pojo.CdssRunRuleBean;
import com.jhmk.cloudutil.config.CdssConstans;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.jhmk.cloudutil.util.MongoUtils.getCollection;


/**
 * @author ziyu.zhou
 * @date 2018/7/27 14:47
 * 数据库跑历史规则专用
 */
@Service
public class CdssRunRuleService {

    MongoCollection<Document> binganshouye = getCollection(CdssConstans.DATASOURCE, CdssConstans.BINGANSHOUYE);
    //入院记录
    static MongoCollection<Document> ruyuanjilu = getCollection(CdssConstans.DATASOURCE, CdssConstans.RUYUANJILU);
    static MongoCollection<Document> yizhu = getCollection(CdssConstans.DATASOURCE, CdssConstans.YIZHU);
    //病理诊断 初诊
    MongoCollection<Document> binglizhenduan = getCollection(CdssConstans.DATASOURCE, CdssConstans.BINGLIZHENDUAN);
    MongoCollection<Document> shouyezhenduan = getCollection(CdssConstans.DATASOURCE, CdssConstans.SHOUYEZHENDUAN);
    MongoCollection<Document> jianchabaogao = getCollection(CdssConstans.DATASOURCE, CdssConstans.JCBG);
    MongoCollection<Document> jianyanbaogao = getCollection(CdssConstans.DATASOURCE, CdssConstans.JYBG);

    /**
     * 获取入院记录
     *
     * @param id
     * @return
     * @throws NullPointerException
     */
    public Map<String, String> getRYJL(String id) throws NullPointerException {
        List<Map<String, String>> ruyuanjiluList = new ArrayList<>();
        List<Document> countPatientId2 = Arrays.asList(
                //过滤数据
                new Document("$match", new Document("_id", id)),
//                new Document("$unwind","$diagnosis_name"),
                new Document("$project", new Document("patient_id", 1).append("visit_id", 1).append("ruyuanjilu", 1)
                )

        );
        AggregateIterable<Document> output = ruyuanjilu.aggregate(countPatientId2);

        Map<String, String> ryjlMap = new LinkedHashMap<>();
        for (Document document : output) {

            Document ruyuanjilu = (Document) document.get("ruyuanjilu");
            if (ruyuanjilu == null) {
                continue;
            }
            Document chief_complaint = (Document) ruyuanjilu.get("chief_complaint");
            if (Objects.nonNull(chief_complaint)) {
                //主诉
                ryjlMap.put("chief_complaint", chief_complaint.getString("src"));
            }

            //淋巴结
            //头部
            //现病史
            Document history_of_present_illness = (Document) ruyuanjilu.get("history_of_present_illness");
            if (Objects.nonNull(history_of_present_illness)) {

                ryjlMap.put("history_of_present_illness", history_of_present_illness.getString("src"));
            }
            //既往史
            Document history_of_past_illness = (Document) ruyuanjilu.get("history_of_past_illness");
            if (Objects.nonNull(history_of_past_illness)) {
                ryjlMap.put("history_of_past_illness", history_of_past_illness.getString("src"));
            }
            //个人史
            Document social_history = (Document) ruyuanjilu.get("social_history");
            if (social_history != null) {
                ryjlMap.put("social_history", social_history.getString("src"));
            }
            //婚姻史
            Document menstrual_and_obstetrical_histories = (Document) ruyuanjilu.get("menstrual_and_obstetrical_histories");
            if (menstrual_and_obstetrical_histories != null) {
                ryjlMap.put("menstrual_and_obstetrical_histories", menstrual_and_obstetrical_histories.getString("src"));
            }
            //家族史
            Document history_of_family_member_diseases = (Document) ruyuanjilu.get("history_of_family_member_diseases");
            if (Objects.nonNull(history_of_family_member_diseases)) {
                ryjlMap.put("history_of_family_member_diseases", history_of_family_member_diseases.getString("src"));
            }

            //辅助检查
            Document physical_examination = (Document) ruyuanjilu.get("physical_examination");
            if (Objects.nonNull(physical_examination)) {

                ryjlMap.put("physical_examination", physical_examination.getString("src"));
            }

            //专科查体
            Document auxiliary_examination = (Document) ruyuanjilu.get("auxiliary_examination");
            if (Objects.nonNull(auxiliary_examination)) {
                ryjlMap.put("auxiliary_examination", auxiliary_examination.getString("src"));
            }
        }
        return ryjlMap;
    }

    /**
     * 病案首页
     *
     * @param id
     * @return
     */
    public CdssRunRuleBean getBASY(String id) {
        List<Document> countPatientId = Arrays.asList(

                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("patient_id", 1).append("_id", 1).append("visit_id", 1).append("binganshouye", 1))
        );
        AggregateIterable<Document> output = binganshouye.aggregate(countPatientId);
        Map<String, String> map = new HashMap<>();

        CdssRunRuleBean cdssRuleBean = new CdssRunRuleBean();
        for (Document document : output) {
            if (document == null) {
                continue;
            }
            cdssRuleBean.setPatient_id(document.getString("patient_id"));
            cdssRuleBean.setVisit_id(document.getString("visit_id"));
            cdssRuleBean.setId(document.getString("_id"));
            Document binganshouye = (Document) document.get("binganshouye");
            Document patInfo = (Document) binganshouye.get("pat_info");
            Document patVisit = (Document) binganshouye.get("pat_visit");
            //病案首页
            map.put("pat_info_sex_name", (String) patInfo.get("sex_name"));
            map.put("pat_info_age_value", (String) patVisit.get("age_value"));
            map.put("pat_info_age_value_unit", (String) patVisit.get("age_value_unit"));
            map.put("pat_info_marital_status_name", (String) patVisit.get("marital_status_name"));
            map.put("pat_visit_dept_discharge_from_name", (String) patVisit.get("dept_discharge_from_name"));

            map.put("pat_visit_dept_admission_to_name", (String) patVisit.get("dept_admission_to_name"));
            map.put("pat_visit_dept_admission_to_code", patVisit.getString("dept_admission_to_code"));
            cdssRuleBean.setBinganshouye(map);
            //医生id
            cdssRuleBean.setId(document.getString("_id"));
            cdssRuleBean.setDept_code(patVisit.getString("dept_admission_to_name"));
            cdssRuleBean.setDoctor_name(patVisit.getString("attending_doctor_name"));
            break;
        }
        return cdssRuleBean;
    }


    /**
     * 病例诊断
     *
     * @return
     */
    public List<Map<String, String>> getBLZD(String id) {
        List<Map<String, String>> list = new ArrayList<>();

        List<Document> countPatientId = Arrays.asList(
                new Document("$unwind", "$binglizhenduan"),
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("_id", 1).append("patient_id", 1).append("visit_id", 1).append("binglizhenduan", 1))
        );
        AggregateIterable<Document> binli = binglizhenduan.aggregate(countPatientId);
        for (Document document : binli) {
            Map<String, String> map = new HashMap<>();
            Document binglizhenduan = (Document) document.get("binglizhenduan");
            String diagnosis_num = binglizhenduan.getString("diagnosis_num");
            String diagnosis_name = binglizhenduan.getString("diagnosis_name");
            if (StringUtils.isNotBlank(diagnosis_name)) {
                diagnosis_name = binglizhenduan.getString("diagnosis_desc");
            }
            map.put("diagnosis_name", diagnosis_name);
            map.put("diagnosis_time", binglizhenduan.getString("diagnosis_time"));
            map.put("diagnosis_num", diagnosis_num);
            map.put("diagnosis_type_name", binglizhenduan.getString("diagnosis_type_name"));

            map.put("diagnosis_sub_num", binglizhenduan.getString("diagnosis_sub_num"));


            list.add(map);
        }
        return list;
    }

    /**
     * 医嘱
     *
     * @param id
     * @return
     */
    public List<Map<String, String>> getZY(String id) {
        List<Document> countPatientId2 = Arrays.asList(
                //过滤数据
                new Document("$match", new Document("_id", id)),
                new Document("$unwind", "$yizhu"),
                new Document("$project", new Document("patient_id", 1).append("visit_id", 1).append("yizhu", 1)
                )

        );
        AggregateIterable<Document> output = yizhu.aggregate(countPatientId2);
        List<Map<String, String>> orderList = new LinkedList<>();
        for (Document document : output) {
            Document yizhuDocu = (Document) document.get("yizhu");
            if (yizhuDocu == null) {
                continue;
            }
            Map<String, String> orderMap = new HashMap<>();
            if (yizhuDocu.get("order_item_name") != null) {
                String order_item_name = yizhuDocu.getString("order_item_name");
                orderMap.put("order_item_name", order_item_name);
                String order_begin_time = yizhuDocu.getString("order_begin_time");
                orderMap.put("order_begin_time", order_begin_time);

                String order_end_time = yizhuDocu.getString("order_end_time");
                orderMap.put("order_end_time", order_end_time);

                String frequency_name = yizhuDocu.getString("frequency_name");
                orderMap.put("frequency_name", frequency_name);

                String order_properties_name = yizhuDocu.getString("order_properties_name");
                orderMap.put("order_properties_name", order_properties_name);

                orderList.add(orderMap);
            }

        }
        return orderList;
    }

    /**
     * 检验报告
     *
     * @param id
     * @return
     */

    public List<Map<String, String>> getJYBG(String id) {
        List<Document> countPatientId = Arrays.asList(
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("_id", 1).append("patient_id", 1).append("visit_id", 1).append("jianyanbaogao", 1))
        );
        AggregateIterable<Document> aggregate = jianyanbaogao.aggregate(countPatientId);
        MongoCursor<Document> iterator = aggregate.iterator();
        List<Map<String, String>> jianyan = new LinkedList<>();
        while (iterator.hasNext()) {
            Document next = iterator.next();
            Document jianyanbaogao = next.get("jianyanbaogao", Document.class);

            if (jianyanbaogao.get("lab_report") != null) {
                List<Document> lab_report = jianyanbaogao.get("lab_report", List.class);
                for (Document d : lab_report) {
                    Map<String, String> map = new HashMap<>();
                    // 检验项目名
                    map.put("lab_item_name", d.getString("lab_item_name"));
                    //检验子项名
                    map.put("lab_sub_item_name", d.getString("lab_sub_item_name"));
                    //检验子项值
                    map.put("lab_result_value", d.getString("lab_result_value"));
                    map.put("lab_result_value_unit", d.getString("lab_result_value_unit"));
                    map.put("report_time", d.getString("report_time"));
                    jianyan.add(map);
                }
            }
        }
        return jianyan;

    }

    /**
     * 检查报告
     */

    public List<Map<String, String>> getJCBG(String id) {

        List<Document> countPatientId = Arrays.asList(
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("_id", 1).append("patient_id", 1).append("visit_id", 1).append("jianchabaogao", 1))
        );
        AggregateIterable<Document> aggregate = jianchabaogao.aggregate(countPatientId);
        MongoCursor<Document> iterator = aggregate.iterator();
        List<Map<String, String>> jiancha = new LinkedList<>();
        while (iterator.hasNext()) {
            Document next = iterator.next();
            Document jianchabaogao = next.get("jianchabaogao", Document.class);
            if (Objects.nonNull(jianchabaogao)) {
                if (jianchabaogao.get("exam_report") != null) {
                    Object lab_report1 = jianchabaogao.get("exam_report");

                    List<Document> lab_report = jianchabaogao.get("exam_report", List.class);
                    for (Document d : lab_report) {
                        Map<String, String> map = new HashMap<>();
                        // 检查项目名
                        map.put("exam_item_name", d.getString("exam_item_name"));
                        //检查类别名
                        map.put("exam_class_name", d.getString("exam_class_name"));
                        //检查所见
                        map.put("exam_feature", d.getString("exam_feature"));
                        map.put("exam_diag", d.getString("exam_diag"));
                        map.put("exam_time", d.getString("exam_time"));
//                    map.put("exam_feature_quantization", d.get("exam_feature_quantization")));
//                    map.put("exam_diag_quantization", d.get("exam_diag_quantization")));
//                map.put("检验子项值单位",d.get("").toString());
                        jiancha.add(map);
                    }
                }
            }
        }

        return jiancha;
    }


}

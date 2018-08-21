package com.jhmk.cloudcdss.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudentity.cdss.pojo.CdssRuleBean;
import com.jhmk.cloudutil.config.CdssConstans;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.jhmk.cloudcdss.service.InitService.liiNames;
import static com.jhmk.cloudutil.util.MongoUtils.getCollection;


/**
 * @author ziyu.zhou
 * @date 2018/7/9 10:51
 */


@Service
public class CdssService {

    MongoCollection<Document> binganshouye = getCollection(CdssConstans.DATASOURCE, CdssConstans.BINGANSHOUYE);
    //入院记录
    static MongoCollection<Document> ruyuanjilu = getCollection(CdssConstans.DATASOURCE, CdssConstans.RUYUANJILU);
    static MongoCollection<Document> yizhu = getCollection(CdssConstans.DATASOURCE, CdssConstans.YIZHU);
    //病理诊断 初诊
    MongoCollection<Document> binglizhenduan = getCollection(CdssConstans.DATASOURCE, CdssConstans.BINGLIZHENDUAN);
    MongoCollection<Document> shouyezhenduan = getCollection(CdssConstans.DATASOURCE, CdssConstans.SHOUYEZHENDUAN);
    MongoCollection<Document> jianchabaogao = getCollection(CdssConstans.DATASOURCE, CdssConstans.JCBG);
    MongoCollection<Document> jianyanbaogao = getCollection(CdssConstans.DATASOURCE, CdssConstans.JYBG);


    //查询病案首页
    public CdssRuleBean selBasy(CdssRuleBean cdssRuleBean) {
        List<Document> countPatientId = Arrays.asList(

                new Document("$match", new Document("_id", cdssRuleBean.getId())),
                new Document("$project", new Document("patient_id", 1).append("_id", 1).append("visit_id", 1).append("binganshouye", 1))
        );
        AggregateIterable<Document> output = binganshouye.aggregate(countPatientId);
        Map<String, String> map = new HashMap<>();

        for (Document document : output) {
            if (document == null) {
                continue;
            }
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

    //查询病案首页
    public Map selBasy(String _id) {
        List<Document> countPatientId = Arrays.asList(

                new Document("$match", new Document("_id", _id)),
                new Document("$project", new Document("patient_id", 1).append("_id", 1).append("visit_id", 1).append("binganshouye", 1))
        );
        AggregateIterable<Document> output = binganshouye.aggregate(countPatientId);
        Map<String, String> map = new HashMap<>();

        for (Document document : output) {
            if (document == null) {
                continue;
            }
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

            break;
        }
        return map;
    }

    //获取病理诊断
    public List<Map<String, String>> selbinglizhenduan(String id) {
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

    public List<Map<String, String>> getShouYeZhenDuan(String id) {
        List<Map<String, String>> list = new ArrayList<>();

        List<Document> countPatientId = Arrays.asList(
                new Document("$unwind", "$binglizhenduan"),
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("_id", 1).append("patient_id", 1).append("visit_id", 1).append("binglizhenduan", 1))
        );
        AggregateIterable<Document> binli = shouyezhenduan.aggregate(countPatientId);
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


    //类似于3目运算符
    public static String flagObj(Object str) {
        return (String) Optional.ofNullable(str)
                .orElse("");
    }


    private static String getLevelStr(int level) {
        StringBuffer levelStr = new StringBuffer();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append("\t");
        }
        return levelStr.toString();
    }


    /**
     * 查询所有_id
     *
     * @return
     */
    public List<String> getAllIds() {
        List<String> idList = new LinkedList<>();
        List<Document> output = Arrays.asList(
                new Document("$project", new Document("_id", 1))
        );
        AggregateIterable<Document> aggregate = ruyuanjilu.aggregate(output);
        for (Document document : aggregate) {
            String id = document.get("_id").toString();
            idList.add(id);
        }
        return idList;
    }

    //获取出院主疾病在疾病列表中的id
    public List<CdssRuleBean> getAllIdsByIllName() {

        List<CdssRuleBean> beanList = new LinkedList<>();
        List<Document> countPatientId = Arrays.asList(
                new Document("$unwind", "$shouyezhenduan"),
                new Document("$project", new Document("_id", 1).append("patient_id", 1).append("visit_id", 1).append("shouyezhenduan", 1))
//                , new Document("$skip", CdssConstans.BEGINCOUNT),
//                new Document("$limit", CdssConstans.ENDCOUNT)
//                new Document("$limit", 500)
        );
        AggregateIterable<Document> binli = shouyezhenduan.aggregate(countPatientId);
        for (Document document : binli) {
            Document shoueyezhenduan = (Document) document.get("shouyezhenduan");
            if ("1".equals(shoueyezhenduan.getString("diagnosis_num")) && "3".equals(shoueyezhenduan.getString("diagnosis_type_code"))) {
                CdssRuleBean cdssRuleBean = new CdssRuleBean();
                String diagnosis_name = shoueyezhenduan.getString("diagnosis_name");
                if (liiNames.contains(diagnosis_name)) {

                    cdssRuleBean.setMainIllName(diagnosis_name);
                    cdssRuleBean.setId(document.getString("_id"));
                    beanList.add(cdssRuleBean);
                }

            }

        }
        return beanList;
    }


    //根据id查询入院记录
    public CdssRuleBean selruyuanjiluById(String id) throws NullPointerException {

        CdssRuleBean selbinglizhenduan = new CdssRuleBean();
        List<Map<String, String>> ruyuanjiluList = new ArrayList<>();
        List<Document> countPatientId2 = Arrays.asList(
                //过滤数据
                new Document("$match", new Document("_id", id)),
//                new Document("$unwind","$diagnosis_name"),
                new Document("$project", new Document("patient_id", 1).append("visit_id", 1).append("ruyuanjilu", 1)
                )

        );
        AggregateIterable<Document> output = ruyuanjilu.aggregate(countPatientId2);
        for (Document document : output) {
            selbinglizhenduan.setPatient_id(document.get("patient_id").toString());
            selbinglizhenduan.setVisit_id(document.get("visit_id").toString());
            selbinglizhenduan.setId(document.get("_id").toString());
            Document ruyuanjilu = (Document) document.get("ruyuanjilu");
            if (ruyuanjilu == null) {
                return selbinglizhenduan;
            }

            List<Document> diagnosisName = (List) ruyuanjilu.get("diagnosis_name");
            if (diagnosisName != null) {
                for (Document d : diagnosisName) {
                    if (d.get("diagnosis_num") != null && d.get("diagnosis_num").toString().equals("1")) {
                        selbinglizhenduan.setQuezhen(d.get("diagnosis_name").toString());
                        break;
                    }
                }
            }

            if (Objects.nonNull(ruyuanjilu.get("chief_complaint"))) {
                Document chief_complaint = (Document) ruyuanjilu.get("chief_complaint");
                if (chief_complaint != null) {
                    Map<String, String> mapzs = new LinkedHashMap<>();
                    mapzs.put("key", "主诉");
                    mapzs.put("value", flagObj(chief_complaint.get("src")));
                    //主诉
                    ruyuanjiluList.add(mapzs);
                }
            }
            //现病史
            Document history_of_present_illness = (Document) ruyuanjilu.get("history_of_present_illness");
            if (history_of_present_illness != null) {

                Map<String, String> mapxbs = new LinkedHashMap<>();
                mapxbs.put("key", "现病史");
                mapxbs.put("value", flagObj(history_of_present_illness.get("src")));
                ruyuanjiluList.add(mapxbs);
            }
            //既往史
            Document history_of_past_illness = (Document) ruyuanjilu.get("history_of_past_illness");
            if (history_of_past_illness != null) {
                Map<String, String> jws = new LinkedHashMap<>();
                jws.put("key", "既往史");
                jws.put("value", flagObj(history_of_past_illness.get("src")));
                ruyuanjiluList.add(jws);
            }
            //个人史

            Document social_history = (Document) ruyuanjilu.get("social_history");
            if (social_history != null) {

                Map<String, String> grs = new LinkedHashMap<>();
                grs.put("key", "个人史");
                grs.put("value", flagObj(social_history.get("src")));
                ruyuanjiluList.add(grs);
            }

            //婚姻史
            Document menstrual_and_obstetrical_histories = (Document) ruyuanjilu.get("menstrual_and_obstetrical_histories");
            if (menstrual_and_obstetrical_histories != null) {

                Map<String, String> hys = new LinkedHashMap<>();
                hys.put("key", "婚姻史");
                hys.put("value", flagObj(menstrual_and_obstetrical_histories.get("src")));
                ruyuanjiluList.add(hys);
            }

            //家族史
            Document history_of_family_member_diseases = (Document) ruyuanjilu.get("history_of_family_member_diseases");
            if (history_of_family_member_diseases != null) {
                Map<String, String> jzs = new LinkedHashMap<>();
                jzs.put("key", "家族史");
                jzs.put("value", flagObj(history_of_family_member_diseases.get("src")));
                ruyuanjiluList.add(jzs);
            }
            //辅助检查
            Document physical_examination = (Document) ruyuanjilu.get("physical_examination");
            if (physical_examination != null) {
                Map<String, String> tgjc = new LinkedHashMap<>();
                tgjc.put("key", "体格检查");
                tgjc.put("value", flagObj(physical_examination.get("src")));
                ruyuanjiluList.add(tgjc);
            }
            //专科检查
            Document special_examination = (Document) ruyuanjilu.get("special_examination");
            if (special_examination != null) {
                Map<String, String> zkjc = new LinkedHashMap<>();
                zkjc.put("key", "专科检查");
                zkjc.put("value", flagObj(special_examination.get("src")));
                ruyuanjiluList.add(zkjc);
            }


            //专科查体
            Document auxiliary_examination = (Document) ruyuanjilu.get("auxiliary_examination");
            if (ObjectUtils.anyNotNull(auxiliary_examination)) {
                Map<String, String> fzjc = new LinkedHashMap<>();
                fzjc.put("key", "辅助检查");
                fzjc.put("value", flagObj(auxiliary_examination.get("src")));
                ruyuanjiluList.add(fzjc);
            }
            selbinglizhenduan.setRuyuanjilu(ruyuanjiluList);
        }
        return selbinglizhenduan;
    }


    /**
     * 获取入院记录
     *
     * @param id
     * @return
     * @throws NullPointerException
     */


    /**
     * 获取首页诊断
     *
     * @param id
     * @return
     */

    public List<Map<String, String>> selSyzd(String id) {
        List<Map<String, String>> list = new ArrayList<>();

        List<Document> countPatientId = Arrays.asList(
                new Document("$unwind", "$shouyezhenduan"),
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("_id", 1).append("patient_id", 1).append("visit_id", 1).append("shouyezhenduan", 1))
        );
        AggregateIterable<Document> binli = shouyezhenduan.aggregate(countPatientId);
        for (Document document : binli) {
            Map<String, String> shouyezhenduanMap = new HashMap<>();
            Document shoueyezhenduan = (Document) document.get("shouyezhenduan");

            String diagnosis_name = shoueyezhenduan.getString("diagnosis_name");
            if (StringUtils.isNotBlank(diagnosis_name)) {
                diagnosis_name = shoueyezhenduan.getString("diagnosis_desc");
            }
            shouyezhenduanMap.put("diagnosis_name", diagnosis_name);
            shouyezhenduanMap.put("diagnosis_time", shoueyezhenduan.getString("diagnosis_time"));
            shouyezhenduanMap.put("diagnosis_num", shoueyezhenduan.getString("diagnosis_num"));
            shouyezhenduanMap.put("diagnosis_type_name", shoueyezhenduan.getString("diagnosis_type_name"));
            shouyezhenduanMap.put("diagnosis_type_code", shoueyezhenduan.getString("diagnosis_type_code"));
            list.add(shouyezhenduanMap);
        }
        return list;
    }

    /**
     * 获取检验报告
     *
     * @param id
     * @return
     */
    public List<Map<String, String>> selJybgList(String id) {
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
            if (jianyanbaogao == null) {
                continue;
            }
            if (jianyanbaogao.get("lab_report") != null) {
                List<Document> lab_report = jianyanbaogao.get("lab_report", List.class);
                for (Document d : lab_report) {
                    Map<String, String> map = new HashMap<>();
                    // 检验项目名
                    map.put("lab_item_name", flagObj(d.get("lab_item_name")));
                    //检验子项名
                    map.put("lab_sub_item_name", flagObj(d.get("lab_sub_item_name")));
                    //检验子项值
                    map.put("lab_result_value", flagObj(d.get("lab_result_value")));
                    map.put("lab_result_value_unit", flagObj(d.get("lab_result_value_unit")));
                    jianyan.add(map);
                }
            }
        }
        return jianyan;
    }

    /**
     * 检验报告
     *
     * @param id
     * @return
     */
    public List<Map<String, String>> selJybg(String id) {
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
                    map.put("lab_item_name", flagObj(d.get("lab_item_name")));
                    //检验子项名
                    map.put("lab_sub_item_name", flagObj(d.get("lab_sub_item_name")));
                    //检验子项值
                    map.put("lab_result_value", flagObj(d.get("lab_result_value")));
                    map.put("lab_result_value_unit", flagObj(d.get("lab_result_value_unit")));
                    map.put("report_time", flagObj(d.get("report_time")));
                    jianyan.add(map);
                }
            }
        }
        return jianyan;

    }

    public List<Map<String, List<Map<String, String>>>> getJianYan(String id) {

        List<Document> countPatientId = Arrays.asList(
                new Document("$match", new Document("_id", id)),
                new Document("$project", new Document("_id", 1).append("patient_id", 1).append("visit_id", 1).append("jianyanbaogao", 1))
        );
        AggregateIterable<Document> aggregate = jianyanbaogao.aggregate(countPatientId);
        MongoCursor<Document> iterator = aggregate.iterator();
        List<Map<String, List<Map<String, String>>>> jianyan = new LinkedList<>();
        Map<String, List<Map<String, String>>> map = new HashMap<>();
        while (iterator.hasNext()) {
            Document next = iterator.next();
            Document jianyanbaogao = next.get("jianyanbaogao", Document.class);
            if (jianyanbaogao == null) {
                continue;
            }

            if (jianyanbaogao.get("lab_report") != null) {
                List<Document> lab_report = jianyanbaogao.get("lab_report", List.class);
                for (Document d : lab_report) {
                    // 检验项目名
                    Map<String, String> cMap = new HashMap<>();
                    cMap.put("name", d.getString("lab_sub_item_name"));
                    cMap.put("lab_result", flagObj(d.get("lab_result_value")));
                    cMap.put("report_time", d.getString("report_time"));
                    if (map.containsKey(d.getString("lab_item_name"))) {
                        List<Map<String, String>> lab_item_name = map.get(d.getString("lab_item_name"));
                        lab_item_name.add(cMap);
                        map.put(d.getString("lab_item_name"), lab_item_name);
                    } else {
                        List<Map<String, String>> clist = new ArrayList<>();
                        clist.add(cMap);
                        map.put(d.getString("lab_item_name"), clist);
                    }
                }
                jianyan.add(map);

            }
        }
        return jianyan;

    }


    //检查报告
    public List<Map<String, String>> selJcbgList(String id) {

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
                        map.put("exam_item_name", flagObj(d.get("exam_item_name")));
                        //检查类别名
                        map.put("exam_class_name", flagObj(d.get("exam_class_name")));
                        //检查所见
                        map.put("exam_feature", flagObj(d.get("exam_feature")));
                        map.put("exam_diag", flagObj(d.get("exam_diag")));
                        map.put("exam_time", flagObj(d.get("exam_time")));
//                    map.put("exam_feature_quantization", flagObj(d.get("exam_feature_quantization")));
//                    map.put("exam_diag_quantization", flagObj(d.get("exam_diag_quantization")));
//                map.put("检验子项值单位",d.get("").toString());
                        jiancha.add(map);
                    }
                }
            }
        }

        return jiancha;
    }


    public List<Map<String, String>> selYizhu(String id) {
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

    public String anaRule(String map) {
        Map<String, String> paramMap = (Map) JSON.parse(map);
        Map<String, Object> endparamMap = new HashMap<String, Object>();
        endparamMap.putAll(paramMap);
        for (String key : paramMap.keySet()) {
            if (key.equals("ruyuanjilu")) {
                String ryjl = String.valueOf(paramMap.get("ruyuanjilu"));
                JSONArray jsonArray = JSON.parseArray(ryjl);
                Iterator<Object> it = jsonArray.iterator();
                Map<String, String> ryjlMap = new HashMap<String, String>();
                while (it.hasNext()) {
                    JSONObject ob = (JSONObject) it.next();
                    String ryjlkey = ob.getString("key");
                    String value = ob.getString("value");
                    if (value != null && !value.isEmpty()) {
                        if (ryjlkey.equals("既往史")) {
                            ryjlMap.put("history_of_past_illness", value);
                        } else if (ryjlkey.equals("主诉")) {
                            ryjlMap.put("chief_complaint", value);
                        } else if (ryjlkey.equals("现病史")) {
                            ryjlMap.put("history_of_present_illness", value);
                        } else if (ryjlkey.equals("家族史")) {
                            ryjlMap.put("history_of_family_member_diseases", value);
                        } else if (ryjlkey.equals("婚姻史")) {
                            ryjlMap.put("menstrual_and_obstetrical_histories", value);
                        } else if (ryjlkey.equals("辅助检查")) {
                            ryjlMap.put("auxiliary_examination", value);
                        } else if (ryjlkey.equals("体格检查")) {
                            ryjlMap.put("physical_examination", value);
                        }
                    }
                }
                endparamMap.put("ruyuanjilu", ryjlMap);
            } else if (key.equals("jianyanbaogao")) {
                String jybg = String.valueOf(paramMap.get("jianyanbaogao"));
                JSONArray jsonArray = JSON.parseArray(jybg);
                Iterator<Object> it = jsonArray.iterator();
                List<Map<String, String>> jybgMap = new ArrayList<Map<String, String>>();
                while (it.hasNext()) {
                    Map<String, String> jcbg = new HashMap<String, String>();
                    JSONObject ob = (JSONObject) it.next();
                    if (ob.containsKey("labTestItems")) {
                        Object labTestItems = ob.get("labTestItems");
                        JSONArray sbjsonArray = JSON.parseArray(labTestItems.toString());
                        for (Object object : sbjsonArray) {
                            JSONObject sbobj = (JSONObject) object;
                            if (sbobj.getString("name") != null) {
                                jcbg.put("lab_sub_item_name", sbobj.getString("name"));
                            }
                            if (sbobj.getString("unit") != null) {
                                jcbg.put("lab_result_value_unit", sbobj.getString("unit"));
                            }
                            if (sbobj.getString("lab_result_value") != null) {
                                jcbg.put("lab_result_value", sbobj.getString("lab_result_value"));
                            }
                            if (sbobj.getString("result_status_code") != null) {
                                jcbg.put("result_status_code", sbobj.getString("result_status_code"));
                            }
                        }
                    }
                    jybgMap.add(jcbg);

                }
                endparamMap.put(key, jybgMap);
            }
        }
        return JSONObject.toJSONString(endparamMap);
    }
}



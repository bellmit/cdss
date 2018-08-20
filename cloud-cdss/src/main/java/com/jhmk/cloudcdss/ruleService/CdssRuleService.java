package com.jhmk.cloudcdss.ruleService;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.jhmk.cloudutil.util.MongoUtils.getCollection;


/**
 * @author ziyu.zhou
 * @date 2018/7/31 17:08
 */

public class CdssRuleService {
    static MongoCollection<Document> cdssrule = getCollection("cdss", "decision_rule");


    public static void main(String[] args) {
        write2file();
    }

    public static List<String> getAllRule() {
        List<String> list = new ArrayList<>();

        List<Document> countPatientId = Arrays.asList(
                new Document("$project", new Document("rule_condition", 1).append("is_run", 1))
        );
        AggregateIterable<Document> binli = cdssrule.aggregate(countPatientId);
        for (Document document : binli) {
            String rule_condition = document.getString("rule_condition");
            String is_run = document.getString("is_run");
            if ("是".equals(is_run)) {
                list.add(rule_condition);
            }
        }
        return list;
    }

    public static void write2file() {
        BufferedWriter bw = null;
//        File file = new File("/data/1/CDSS/3院骨科漏诊数据.txt");
        File file = new File("C:/嘉和美康文档/3院测试数据/cdss规则.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            bw = new BufferedWriter(new FileWriter(file));
            List<String> allRule = getAllRule();
            for (String s : allRule) {
                if (s.contains("诊断名称")) {
                    bw.write(s);
                    bw.newLine();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}

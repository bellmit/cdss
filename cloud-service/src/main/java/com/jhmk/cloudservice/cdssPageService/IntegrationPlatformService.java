//package com.jhmk.cloudservice.cdssPageService;
//
//import com.ewell.mq.queue.QueueTools;
//import com.ibm.mq.MQException;
//import com.ibm.mq.MQQueueManager;
//import com.jhmk.cloudentity.earlywaring.entity.rule.Jianchabaogao;
//import com.jhmk.cloudentity.earlywaring.entity.rule.Jianyanbaogao;
//import com.jhmk.cloudentity.earlywaring.entity.rule.Yizhu;
//import com.jhmk.cloudutil.util.XmlUtil;
//import com.jhmk.cloudutil.utilbean.XMLBean;
//import org.dom4j.Document;
//import org.dom4j.DocumentException;
//import org.dom4j.DocumentHelper;
//import org.dom4j.Element;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//import java.util.Optional;
//
///**
// * 集成平台服务
// *
// * @author ziyu.zhou
// * @date 2018/11/27 13:45
// */
//
//
//@Service
//public class IntegrationPlatformService {
//    /**
//     * 广安门
//     *
//     * @param fid        服务名 医嘱：BS35001 检验报告： BS20003  检查报告： BS20001
//     * @param comditions 查询条件
//     */
//    public String getGuanganmenDataFromJcpt(String fid, List<XMLBean> comditions) {
//        QueueTools queueTools = new QueueTools();
//        MQQueueManager queueManager = null;
//        String reqMsg = XmlUtil.appendXML(fid, comditions);
//        String msgId = "";
//        String respMsg = null;
//        try {
//            // 连接MQ，获取队列管理器实例，该实例如果不调用方法进行断开操作可长期保持连接。连接函数会自动读取配置文件标签名为“QMGR.SXX”下的配置信息进行连接。，
//            queueManager = queueTools.connect("QMGR.S60");
//            // 发送请求消息
//            msgId = queueTools.putMsg(queueManager, fid, reqMsg);
//            // 获取响应消息
//            respMsg = queueTools.getMsgById(queueManager, fid, msgId, 3);
//            // 此处为获取响应数据后的业务处理
//        } catch (MQException e) {
//            // 2033表示队列中没有消息
//            if (e.reasonCode == 2033) {
//
//            } else {
//                e.printStackTrace();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            //断开MQ连接
//            if (null != queueManager) {
//                try {
//                    queueManager.disconnect();
//                } catch (MQException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return respMsg;
//    }
//
//    public List<Yizhu> analyzeXmlYizhu(String xmlYizhu) {
//        List<Yizhu> yizhuList = new ArrayList<>();
//        Document dom = null;
//        try {
//            dom = DocumentHelper.parseText(xmlYizhu);
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//        Element root = dom.getRootElement();
//        Element msgInfo = root.element("MsgInfo");
//        List<Element> items = msgInfo.elements();
//        if (Objects.nonNull(items)) {
//            for (int i = 0; i < items.size(); i++) {
//                Yizhu yizhu = new Yizhu();
//                Element element = items.get(i);
//                String text = element.getText();
//                try {
//                    Document msg = DocumentHelper.parseText(text);
//                    Element rootElement = msg.getRootElement();
//                    Element body = rootElement.element("body");
//                    Element row = body.element("row");
//                    Optional.ofNullable(row.element("ORDER_ID")).ifPresent(s -> yizhu.setOrder_no(s.getText()));
//                    Optional.ofNullable(row.element("ORDER_ITEM_TYPE_CODE")).ifPresent(s -> yizhu.setOrder_class_code(s.getText()));//医嘱名称
//                    Optional.ofNullable(row.element("ORDER_ITEM_TYPE_NAME")).ifPresent(s -> yizhu.setOrder_class_name(s.getText()));//医嘱名称
//                    Optional.ofNullable(row.element("ORDER_ITEM_CONTENT")).ifPresent(s -> yizhu.setOrder_item_name(s.getText()));//医嘱名称
//                    Optional.ofNullable(row.element("ORDER_STATUS")).ifPresent(s -> yizhu.setStatus(s.getText()));//医嘱状态
//                    Optional.ofNullable(row.element("ORDER_STATUS")).ifPresent(s -> yizhu.setOrder_status_name(s.getText()));//医嘱状态
//                    Optional.ofNullable(row.element("ORDER_START_TIME")).ifPresent(s -> yizhu.setOrder_begin_time(s.getText()));
//                    Optional.ofNullable(row.element("ORDER_END_TIME")).ifPresent(s -> yizhu.setOrder_end_time(s.getText()));
//                    Optional.ofNullable(row.element("MEDICATION_FREQUENCY")).ifPresent(s -> yizhu.setFrequency_name(s.getText()));
//                    Optional.ofNullable(row.element("MEDICATION_FREQUENCY_UNIT")).ifPresent(s -> yizhu.setFrequency_code(s.getText()));
//                    Optional.ofNullable(row.element("MEDICATION_APPROACH_NAME")).ifPresent(s -> yizhu.setDosage_form(s.getText()));//用药途径名称
//                    Optional.ofNullable(row.element("MEDICATION_DOSAGE_ONCE")).ifPresent(s -> yizhu.setDosage_value(s.getText()));//用药剂量-单次（药品使用次剂量）
//                    Optional.ofNullable(row.element("MEDICATION_DOSAGE_ONCE_UNIT")).ifPresent(s -> yizhu.setDosage_value(s.getText()));//用药剂量-单次单位
//                    Optional.ofNullable(row.element("DRUG_DOSAGE_ALL")).ifPresent(s -> yizhu.setTotal_dosage_value(s.getText()));//药物使用总剂量
//                    Optional.ofNullable(row.element("DRUG_DOSAGE_ALL_UNIT")).ifPresent(s -> yizhu.setTotal_dosage_unit(s.getText()));//药物使用总剂量单位
//                    yizhuList.add(yizhu);
//                } catch (DocumentException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return yizhuList;
//    }
//
//    public List<Jianyanbaogao> analyzeXmlJianyanbaogao(String xmlJianyanbaogao) {
//        List<Jianyanbaogao> jianyanbaogaoList = new ArrayList<>();
//        Document dom = null;
//        try {
//            dom = DocumentHelper.parseText(xmlJianyanbaogao);
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//        Element root = dom.getRootElement();
//        Element msgInfo = root.element("MsgInfo");
//        if (Objects.nonNull(msgInfo)) {
//
//            List<Element> items = msgInfo.elements();
//            if (Objects.nonNull(items)) {
//                for (int i = 0; i < items.size(); i++) {
//                    Jianyanbaogao jianyanbaogao = new Jianyanbaogao();
//                    Element element = items.get(i);
//                    String text = element.getText();
//                    try {
//                        Document msg = DocumentHelper.parseText(text);
//                        Element rootElement = msg.getRootElement();
//                        Element body = rootElement.element("body");
//                        Element row = body.element("row");
//                        Optional.ofNullable(row.element("SPECIMEN")).ifPresent(s -> jianyanbaogao.setSpecimen(s.getText()));
//                        Optional.ofNullable(row.element("REPORT_DATE")).ifPresent(s -> jianyanbaogao.setReport_time(s.getText()));//时间
//                        Optional.ofNullable(row.element("LAB_SUB_ITEM_NAME")).ifPresent(s -> jianyanbaogao.setLab_sub_item_name(s.getText()));//检验细项
//                        Optional.ofNullable(row.element("TEST_ITEM_NAME")).ifPresent(s -> jianyanbaogao.setLab_item_name(s.getText()));//检验项
//                        Optional.ofNullable(row.element("REPORT_NO")).ifPresent(s -> jianyanbaogao.setReport_no(s.getText()));//检验号
//                        Optional.ofNullable(row.element("TEST_QUALITATIVE_RESULT")).ifPresent(s -> jianyanbaogao.setLab_qual_result(s.getText()));//定性结果
//                        Optional.ofNullable(row.element("TEST_RESULT_VALUE")).ifPresent(s -> jianyanbaogao.setLab_result_value(s.getText()));//定量结果
//                        Optional.ofNullable(row.element("TEST_RESULT_VALUE_UNIT")).ifPresent(s -> jianyanbaogao.setLab_result_value_unit(s.getText()));
//                        Optional.ofNullable(row.element("RESULT_STATUS_CODE")).ifPresent(s -> jianyanbaogao.setResult_status_code(s.getText()));
//                        Optional.ofNullable(row.element("REFERENCE_RANGE")).ifPresent(s -> jianyanbaogao.setReference_range(s.getText()));//用药途径名称
//                        jianyanbaogaoList.add(jianyanbaogao);
//                    } catch (DocumentException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//
//        return jianyanbaogaoList;
//    }
//
//    public List<Jianchabaogao> analyzeXmlJianchabaogao(String xmlJianchabaogao) {
//        List<Jianchabaogao> jianchabaogaoList = new ArrayList<>();
//        Document dom = null;
//        try {
//            dom = DocumentHelper.parseText(xmlJianchabaogao);
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//        Element root = dom.getRootElement();
//        Element msgInfo = root.element("MsgInfo");
//        if (Objects.nonNull(msgInfo)) {
//            List<Element> items = msgInfo.elements();
//            if (Objects.nonNull(items)) {
//                for (int i = 0; i < items.size(); i++) {
//                    Jianchabaogao jianchabaogao = new Jianchabaogao();
//                    Element element = items.get(i);
//                    String text = element.getText();
//                    try {
//                        Document msg = DocumentHelper.parseText(text);
//                        Element rootElement = msg.getRootElement();
//                        Element body = rootElement.element("body");
//                        Element row = body.element("row");
//                        Optional.ofNullable(row.element("EXAM_RESULT")).ifPresent(s -> jianchabaogao.setExam_feature(s.getText()));//检查结果
//                        Optional.ofNullable(row.element("REPORT_DATE")).ifPresent(s -> jianchabaogao.setExam_time(s.getText()));//时间
//                        Optional.ofNullable(row.element("REPORT_NAME")).ifPresent(s -> jianchabaogao.setExam_item_name(s.getText()));//检验细项
//                        Optional.ofNullable(row.element("REPORT_NO")).ifPresent(s -> jianchabaogao.setExam_no(s.getText()));//检验项
//                        Optional.ofNullable(row.element("EXAM_DESCR")).ifPresent(s -> jianchabaogao.setExam_feature(s.getText()));//医师所见
//                        jianchabaogaoList.add(jianchabaogao);
//                    } catch (DocumentException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//
//        return jianchabaogaoList;
//    }
//
//    public static void main(String[] args) {
//        String fid = "BS35001";
//        List<XMLBean> list = new ArrayList<>();
//        XMLBean xmlBean1 = new XMLBean("PAT_ID", "=", "101715853");
//        XMLBean xmlBean2 = new XMLBean("INHOSP_NUM", "=", "9");
//        list.add(xmlBean1);
//        list.add(xmlBean2);
////        String guanganmenDataFromJcpt = getGuanganmenDataFromJcpt(fid, list);
////        Document dom = null;
////        try {
////            dom = DocumentHelper.parseText(guanganmenDataFromJcpt);
////        } catch (DocumentException e) {
////            e.printStackTrace();
////        }
////        Element root = dom.getRootElement();
////        Element msgInfo = root.element("MsgInfo");
////        String text = msgInfo.getText();
////        System.out.println(text);
////        List<Element> items = root.elements();
////        if (Objects.nonNull(items)) {
////            Element lab_item_name = element.element("LAB_ITEM_NAME");
////            if (Objects.nonNull(lab_item_name)) {
////                jianyanbaogao.setLab_item_name(lab_item_name.getText());
////            }
////            MsgInfo
//    }
//}
//

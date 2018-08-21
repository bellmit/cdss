package com.jhmk.cloudcdss.service;//package jhmk.clinic.cms.service;
//
///**
// * @author ziyu.zhou
// * @date 2018/7/16 11:45
// */
//
//import jhmk.clinic.core.util.MyThreadPoolManager;
//import jhmk.clinic.entity.cdss.CdssRuleBean;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.Resource;
//import org.springframework.stereotype.Component;
//
//import java.io.*;
//import java.util.*;
//import java.util.concurrent.ExecutorService;
//
///**
// * 获取疾病名
// */
//
//@Component
//public class IllNessService implements InitializingBean {
//    //疾病集合
//    static Set<String> liiNames = new HashSet<>();
//    //病例集合
//    public volatile static List<CdssRuleBean> caseList = new LinkedList<>();
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        readFile2Cache();
//        addCase2cache2();
//    }
//    private void addCase2cache2() {
//        CdssService cdssService = new CdssService();
//        List<CdssRuleBean> idList = cdssService.getAllIdsByIllName();
////        MyThreadPoolManager myThreadPoolManager = MyThreadPoolManager.getsInstance();
//        ExecutorService executorService = MyThreadPoolManager.newBlockingExecutorsUseCallerRun();
//        for (CdssRuleBean cdssRuleBean : idList) {
//            Runnable task = new Runnable() {
//                @Override
//                public void run() {
//                    String id = cdssRuleBean.getId();
//                    //查询  ruyuanjilu 一诉五史
//                    CdssRuleBean cdssTestBean = cdssService.selruyuanjiluById(id);
//                    if (cdssTestBean.getRuyuanjilu() != null) {
//                        cdssTestBean.setMainIllName(cdssRuleBean.getMainIllName());
//                        //病案首页
//                        Map selbinganshouye = cdssService.selBasy(id);
//                        cdssTestBean.setBinganshouye(selbinganshouye);
//                        //病例诊断
//                        List<Map<String, String>> selbinglizhenduan1 = cdssService.selbinglizhenduan(id);
//                        cdssTestBean.setBinglizhenduan(selbinglizhenduan1);
//                        //首页诊断
//                        List<Map<String, String>> syzdList = cdssService.selSyzd(id);
//                        cdssTestBean.setShouyezhenduan(syzdList);
//                        List<Map<String, List<Map<String, String>>>> jianYan = cdssService.getJianYan(id);
//                        if (jianYan.size() > 0) {
//                            cdssTestBean.setJianyanbaogao(jianYan);
//                        }
//                        if (Objects.nonNull(cdssTestBean)) {
//                            caseList.add(cdssTestBean);
//                        }
//                    }
//                }
//
//            };
//            executorService.execute(task);
//        }
//    }
//
//    private void readFile2Cache() {
//        Resource resource = new ClassPathResource("commonDiseases");
//        File file = null;
//        BufferedReader br = null;
//        try {
//            file = resource.getFile();
//            br = new BufferedReader(new FileReader(file));
//            String line = null;
//            while ((line = br.readLine()) != null) {
//                String ill = line.replaceAll("\\(", "（").replaceAll("\\)", "）");
//                liiNames.add(ill);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                br.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void addCase2cache1() {
//        CdssService cdssService = new CdssService();
//        List<CdssRuleBean> idList = cdssService.getAllIdsByIllName();
//        int size = idList.size();
//        MyThreadPoolManager myThreadPoolManager = MyThreadPoolManager.getsInstance();
//
//        List<CdssRuleBean> tempList = null;
//
//        int count = size / 16;
//        Runnable task = null;
//        List<Runnable> tasks = new LinkedList<>();
//        for (int i = 0; i < 16; i++) {
//            if (i == 16 - 1) {
//                tempList = idList.subList(i * count, size);
//            } else {
//                tempList = idList.subList(i * count, count * (i + 1));
//            }
//
//            final List<CdssRuleBean> temList = tempList;
//            task = new Runnable() {
//                @Override
//                public void run() {
//                    for (CdssRuleBean cdssRuleBean : temList) {
//                        String id = cdssRuleBean.getId();
//                        //查询  ruyuanjilu 一诉五史
//                        CdssRuleBean cdssTestBean = cdssService.selruyuanjiluById(id);
//                        if (cdssTestBean.getRuyuanjilu() == null) {
//                            continue;
//                        }
//                        cdssTestBean.setMainIllName(cdssRuleBean.getMainIllName());
//                        //病案首页
//                        Map selbinganshouye = cdssService.selBasy(id);
//                        cdssTestBean.setBinganshouye(selbinganshouye);
//                        //病例诊断
//                        List<Map<String, String>> selbinglizhenduan1 = cdssService.selbinglizhenduan(id);
//                        cdssTestBean.setBinglizhenduan(selbinglizhenduan1);
//                        //首页诊断
//                        List<Map<String, String>> syzdList = cdssService.selSyzd(id);
//                        cdssTestBean.setShouyezhenduan(syzdList);
//                        List<Map<String, List<Map<String, String>>>> jianYan = cdssService.getJianYan(id);
//                        if (jianYan.size() > 0) {
//                            cdssTestBean.setJianyanbaogao(jianYan);
//                        }
//                        if (Objects.nonNull(cdssTestBean)) {
//                            caseList.add(cdssTestBean);
//                        }
//                    }
//                }
//            };
//            myThreadPoolManager.execute(task);
//
//
//        }
////        exec.shutdown();
//    }
//
//
//    private void addCase2cache() {
//        CdssService cdssService = new CdssService();
//        List<CdssRuleBean> idList = cdssService.getAllIdsByIllName();
//
//        for (CdssRuleBean cdssRuleBean : idList) {
//            String id = cdssRuleBean.getId();
//            //查询  ruyuanjilu 一诉五史
//            CdssRuleBean cdssTestBean = cdssService.selruyuanjiluById(id);
//            if (cdssTestBean.getRuyuanjilu() == null) {
//                System.out.println("主键为:" + id);
//                continue;
//            }
//            cdssTestBean.setMainIllName(cdssRuleBean.getMainIllName());
//            //病案首页
//            Map selbinganshouye = cdssService.selBasy(id);
//            cdssTestBean.setBinganshouye(selbinganshouye);
//            //病例诊断
//            List<Map<String, String>> selbinglizhenduan1 = cdssService.selbinglizhenduan(id);
//            cdssTestBean.setBinglizhenduan(selbinglizhenduan1);
//            //首页诊断
//            List<Map<String, String>> syzdList = cdssService.selSyzd(id);
//            cdssTestBean.setShouyezhenduan(syzdList);
//            List<Map<String, List<Map<String, String>>>> jianYan = cdssService.getJianYan(id);
//            cdssTestBean.setJianyanbaogao(jianYan);
//            caseList.add(cdssTestBean);
//        }
//    }
//
//}

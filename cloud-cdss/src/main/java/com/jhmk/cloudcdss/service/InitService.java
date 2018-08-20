package com.jhmk.cloudcdss.service;

import com.jhmk.cloudcdss.ruleService.BasyService;
import com.jhmk.cloudentity.cdss.cdss.CdssRuleBean;
import com.jhmk.cloudutil.util.MyThreadPoolManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;

/**
 * @author ziyu.zhou
 * @date 2018/7/17 16:32
 */
@Service
public class InitService {

    //疾病集合
    static Set<String> liiNames = new HashSet<>();
    //病例集合
    public volatile static List<CdssRuleBean> caseList = new LinkedList<>();


    public volatile static Set<String> diseaseNames = new HashSet<>();

    @PostConstruct
    public void init() throws Exception {
        readFile2Cache();
        addCase2cache();
        addDiseaseName2Cache();
    }

    private void addDiseaseName2Cache() {
        BasyService basyService=new BasyService();
        Set<String> allDepts = basyService.getAllDepts();
        diseaseNames.addAll(allDepts);

    }

    private void addCase2cache2() {
        CdssService cdssService = new CdssService();
        List<CdssRuleBean> idList = cdssService.getAllIdsByIllName();
//        MyThreadPoolManager myThreadPoolManager = MyThreadPoolManager.getsInstance();
        ExecutorService executorService = MyThreadPoolManager.newBlockingExecutorsUseCallerRun();
        for (CdssRuleBean cdssRuleBean : idList) {
            String id = cdssRuleBean.getId();
            CdssRuleBean cdssTestBean = cdssService.selruyuanjiluById(id);
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    //查询  ruyuanjilu 一诉五史
                    if (cdssTestBean.getRuyuanjilu() != null) {
                        cdssTestBean.setMainIllName(cdssRuleBean.getMainIllName());
                        //病案首页
                        Map selbinganshouye = cdssService.selBasy(id);
                        cdssTestBean.setBinganshouye(selbinganshouye);
                        //病例诊断
                        List<Map<String, String>> selbinglizhenduan1 = cdssService.selbinglizhenduan(id);
                        cdssTestBean.setBinglizhenduan(selbinglizhenduan1);
                        //首页诊断
                        List<Map<String, String>> syzdList = cdssService.selSyzd(id);
                        cdssTestBean.setShouyezhenduan(syzdList);
                        List<Map<String, List<Map<String, String>>>> jianYan = cdssService.getJianYan(id);
//                        if (jianYan.size() > 0) {
//                            cdssTestBean.setJianyanbaogao(jianYan);
//                        }
                        if (Objects.nonNull(cdssTestBean)) {
                            caseList.add(cdssTestBean);
                        }
                    }
                }

            };
            executorService.execute(task);
        }
    }

    private void readFile2Cache() {
        Resource resource = new ClassPathResource("commonDiseases");
        File file = null;
        BufferedReader br = null;
        try {
            file = resource.getFile();
            br = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = br.readLine()) != null) {
                String ill = line.replaceAll("\\(", "（").replaceAll("\\)", "）");
                liiNames.add(ill);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    private void addCase2cache() {
        CdssService cdssService = new CdssService();
        List<CdssRuleBean> idList = cdssService.getAllIdsByIllName();

        for (CdssRuleBean cdssRuleBean : idList) {
            String id = cdssRuleBean.getId();
            //查询  ruyuanjilu 一诉五史
            CdssRuleBean cdssTestBean = cdssService.selruyuanjiluById(id);
            if (cdssTestBean.getRuyuanjilu() == null) {
                continue;
            }
            cdssTestBean.setMainIllName(cdssRuleBean.getMainIllName());
            //病案首页
            Map selbinganshouye = cdssService.selBasy(id);
            cdssTestBean.setBinganshouye(selbinganshouye);
            //病例诊断
            List<Map<String, String>> selbinglizhenduan1 = cdssService.selbinglizhenduan(id);
            cdssTestBean.setBinglizhenduan(selbinglizhenduan1);
            //首页诊断
            List<Map<String, String>> syzdList = cdssService.selSyzd(id);
            cdssTestBean.setShouyezhenduan(syzdList);
            List<Map<String, List<Map<String, String>>>> jianYan = cdssService.getJianYan(id);
//            cdssTestBean.setJianyanbaogao(jianYan);
            caseList.add(cdssTestBean);
        }
    }
}

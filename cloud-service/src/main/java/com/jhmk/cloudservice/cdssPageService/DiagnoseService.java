package com.jhmk.cloudservice.cdssPageService;

import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudentity.earlywaring.entity.DiagnosisReqLog;
import com.jhmk.cloudentity.earlywaring.entity.DiagnosisRespLog;
import com.jhmk.cloudentity.earlywaring.entity.repository.service.DiagnosisReqLogService;
import com.jhmk.cloudentity.earlywaring.entity.repository.service.DiagnosisRespLogService;
import com.jhmk.cloudentity.earlywaring.entity.rule.Binglizhenduan;
import com.jhmk.cloudentity.earlywaring.entity.rule.Rule;
import com.jhmk.cloudutil.config.UrlConstants;
import com.jhmk.cloudutil.config.UrlPropertiesConfig;
import com.jhmk.cloudutil.util.DateFormatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @author ziyu.zhou
 * @date 2019/1/16 17:40
 * 诊断服务层
 */
@Service
public class DiagnoseService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UrlPropertiesConfig urlPropertiesConfig;
    @Autowired
    DiagnosisReqLogService diagnosisReqLogService;
    @Autowired
    DiagnosisRespLogService diagnosisRespLogService;


    public String emrdiseaseinfo(Rule rule, Map parse){
        //处理请求数据
        DiagnosisReqLog reqLog = getReqData(rule);
        diagnosisReqLogService.save(reqLog);//保存请求数据
        //请求数据接口
        String s = restTemplate.postForObject(urlPropertiesConfig.getCdssurl() + UrlConstants.Emrdiseaseinfo, parse, String.class);
        //将返回的数据入库
        List<DiagnosisRespLog> respLogList = getRespData(s,reqLog.getId());
        diagnosisRespLogService.save(respLogList);
        return s;
    }

    /**
     * 将请求数据插入实体类中
     */
    private DiagnosisReqLog getReqData(Rule rule){
        String pid = rule.getPatient_id();
        String vid = rule.getVisit_id();
        String doctorId = rule.getDoctor_id();
        String doctorName = rule.getDoctor_name();
        String deptCode = rule.getDept_code();
        //初步诊断（病例主诊断）
        String diagnosis_name = "";
        //下诊断时间
        String diagnosis_time = null;
        List<Binglizhenduan> blzdList = rule.getBinglizhenduan();
        for(Binglizhenduan blzd :blzdList){
            if("初步诊断".equals(blzd.getDiagnosis_type_name()) && "1".equals(blzd.getDiagnosis_num())){
                diagnosis_name = blzd.getDiagnosis_name();
                diagnosis_time = blzd.getDiagnosis_time();
            }
        }
        String page_source = rule.getPageSource();
        DiagnosisReqLog reqLog = new DiagnosisReqLog();
        reqLog.setPid(pid);
        reqLog.setVid(vid);
        reqLog.setDiagnosisName(diagnosis_name);
        reqLog.setDiagnosisTime(diagnosis_time);
        reqLog.setPageSource(page_source);
        reqLog.setDoctorId(doctorId);
        reqLog.setDoctorName(doctorName);
        reqLog.setDeptCode(deptCode);
        reqLog.setInTime(DateFormatUtil.format(new Date(),DateFormatUtil.DATETIME_PATTERN_SS));

        return reqLog;
    }

    /**
     * 将返回数据插入实体类中
     */
    private List<DiagnosisRespLog> getRespData(String str, Integer reqId){
        List<DiagnosisRespLog> respLogList = new ArrayList<>();
        JSONObject resultObj = JSONObject.parseObject(str);
        Set<String> firstLevelKeys = resultObj.keySet();
        for(String firstLevelItem : firstLevelKeys){
            DiagnosisRespLog respLog = new DiagnosisRespLog();
            String alias="";
            String haveChild="";
            String score="";
            String diseaseName="";
            String code="";
            String ishr="";

            JSONObject firstLevelValue = (JSONObject) resultObj.get(firstLevelItem);
            Set<String> secondLevelKeys = firstLevelValue.keySet();
            for(String secondLevelItem : secondLevelKeys){
                if("ishr".equals(secondLevelItem)){
                    ishr = (String) firstLevelValue.get(secondLevelItem);
                }else {
                    JSONObject secondValue = (JSONObject) firstLevelValue.get(secondLevelItem);
                    Set<String> secondLevelKeySet = secondValue.keySet();
                    for (String secondLevelKeySetItem : secondLevelKeySet) {
                        if("alias".equals(secondLevelKeySetItem)){
                            alias = String.valueOf(secondValue.get(secondLevelKeySetItem));
                        }else if("haveChild".equals(secondLevelKeySetItem)){
                            haveChild = (String) secondValue.get(secondLevelKeySetItem);
                        }else if("score".equals(secondLevelKeySetItem)){
                            score = (secondValue.get(secondLevelKeySetItem)).toString();
                        }else if("disease_disease_name".equals(secondLevelKeySetItem)){
                            diseaseName = (String) secondValue.get(secondLevelKeySetItem);
                        }else if("code".equals(secondLevelKeySetItem)){
                            code = (String) secondValue.get(secondLevelKeySetItem);
                        }
                    }
                }
            }
            respLog.setReqId(reqId);
            respLog.setAlias(alias);
            respLog.setHaveChild(haveChild);
            respLog.setScore(score);
            respLog.setDiseaseName(diseaseName);
            respLog.setCode(code);
            respLog.setIshr(ishr);
            respLog.setIn_time(DateFormatUtil.format(new Date(),DateFormatUtil.DATETIME_PATTERN_SS));
            respLogList.add(respLog);
        }
        return respLogList;
    }
}

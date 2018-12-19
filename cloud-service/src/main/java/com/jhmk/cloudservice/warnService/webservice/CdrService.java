package com.jhmk.cloudservice.warnService.webservice;


import com.jhmk.cloudservice.warnService.webservice.service.HdrQueryDataService;
import com.jhmk.cloudservice.warnService.webservice.service.HdrQueryDataWsImpl;
import com.jhmk.cloudutil.config.BaseConstants;
import com.jhmk.cloudutil.util.DateFormatUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author ziyu.zhou
 * @date 2018/7/19 11:16
 */

@Service
public class CdrService {
    private static final Logger logger = LoggerFactory.getLogger(CdrService.class);
    static HdrQueryDataWsImpl hdrQueryDataWsImplPort = null;

    static {
        HdrQueryDataService hdrQueryDataService = new HdrQueryDataService();
        hdrQueryDataWsImplPort = hdrQueryDataService.getHdrQueryDataWsImplPort();
    }

    public static String getXml(Map<String, String> params, List<Map<String, String>> conditions) {
        StringBuffer sb = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        sb.append("<MSG>")
                .append("<HOSPITAL_OID>")
                .append(BaseConstants.OID)
                .append("</HOSPITAL_OID>")
                .append("<PATIENT_ID>")
                .append(params.get("patient_id"))
                .append("</PATIENT_ID>");
        if (StringUtils.isNotBlank(params.get("visit_id"))) {
            sb.append("<VISIT_ID>")
                    .append(params.get("visit_id"))
                    .append("</VISIT_ID>");

        }

        sb.append("<WS_CODE>")
                .append(params.get("ws_code"))
                .append("</WS_CODE>");
        if (Objects.nonNull(conditions)) {
            sb.append("<CONDITION>");
            for (Map<String, String> map : conditions) {
                sb.append("<ELEM NAME=\"" + map.get("elemName") + "\" VALUE=\"" + map.get("value") + "\"  OPERATOR=\"" + map.get("operator") + "\"></ELEM>");
            }
            sb.append("</CONDITION>");
        }

        sb.append("</MSG>");
        return sb.toString();
    }

    /**
     * 调用数据中心接口 并返回数据
     *
     * @return
     */
    public String getDataByCDR(Map<String, String> params, List<Map<String, String>> coditions) {
        String xml = getXml(params, coditions);
        String data = "";
        try {
            data = hdrQueryDataWsImplPort.queryData(xml);
        } catch (Exception e) {
            logger.error("获取数据中心数据失败：{},请求参数为：{}", e.getMessage(),xml);
        }
        return data;
    }


    public static void main(String[] args) {
//        String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><MSG><HOSPITAL_OID>1.2.156.112636.1.2.46</HOSPITAL_OID><PATIENT_ID>001652668700</PATIENT_ID><VISIT_ID>1</VISIT_ID><WS_CODE>JHHDRWS005</WS_CODE><CONDITION><ELEM NAME=\"REPORT_TIME\" VALUE=\"2018-09-16 13:07:57\"  OPERATOR=\"&gt;=\"></ELEM></CONDITION></MSG>";
//        String xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?><MSG><HOSPITAL_OID>1.2.156.112636.1.2.46</HOSPITAL_OID><PATIENT_ID>001652668700</PATIENT_ID><VISIT_ID>1</VISIT_ID><WS_CODE>JHHDRWS005</WS_CODE></MSG>";
        Map<String, String> params = new HashMap<>();
        params.put("oid", BaseConstants.OID);
        params.put("patient_id", "000571764100");
//        params.put("patient_id", "000571764100");
        params.put("visit_id", "1");
//        //检验数据
//        params.put("ws_code", "JHHDRWS005");
        //医嘱
        params.put("ws_code", "JHHDRWS012A");
//        params.put("ws_code", "JHHDRWS006A");
//        params.put("ws_code", "JHHDRWS012A");
////        params.put("elemName", "REPORT_TIME");
////        params.put("value", "2017-08-15 06:40:26");
////        params.put("operator", "=");
        List<Map<String, String>> listConditions = new LinkedList<>();
        Map<String, String> conditionParams = new HashMap<>();
        conditionParams.put("elemName", "ORDER_PROPERTIES_NAME");
        conditionParams.put("value", "长期");
        conditionParams.put("operator", "=");
        listConditions.add(conditionParams);
        String xml = getXml(params, listConditions);
        System.out.println(xml);
        HdrQueryDataService hdrQueryDataService = new HdrQueryDataService();
        HdrQueryDataWsImpl hdrQueryDataWsImplPort = hdrQueryDataService.getHdrQueryDataWsImplPort();
        String data = hdrQueryDataWsImplPort.queryData(xml);
        System.out.println(data);
        AnalysisXmlService analysisXmlService = new AnalysisXmlService();
//        analysisXmlService.analysisXml2Binganshouye(data);
    }

    public List<Map<String, String>> aa() {
        List<Map<String, String>> listConditions = new LinkedList<>();
        Map<String, String> conditionParams = new HashMap<>();
        conditionParams.put("elemName", "ORDER_PROPERTIES_NAME");
        conditionParams.put("value", "长期");
        conditionParams.put("operator", "=");


        return listConditions;
    }
}

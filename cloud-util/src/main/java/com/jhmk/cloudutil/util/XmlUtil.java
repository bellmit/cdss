package com.jhmk.cloudutil.util;


import com.jhmk.cloudutil.utilbean.XMLBean;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author ziyu.zhou
 * @date 2018/11/20 17:55
 */

public class XmlUtil {
    /**
     * @param fid
     * @param params
     * @return
     */
//    public static String appendXML(String fid, List<XMLBean> params) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("<ESBEntry>");
//        sb.append("<AccessControl>");
//        sb.append("<UserName>CDSS</UserName >");
//        sb.append("<Password > 123456 </Password >");
//        sb.append("<SysFlag > 1 </SysFlag >");
//        sb.append(" <Fid > " + fid + " </Fid>");
//        sb.append("</AccessControl >");
//        sb.append("<MessageHeader >");
//        sb.append("<Fid > " + fid + " </Fid>");
//        sb.append("<SourceSysCode > S60 </SourceSysCode>");
//        sb.append("<TargetSysCode > S00 </TargetSysCode>");
//        sb.append("<MsgDate >");
//        sb.append(DateFormatUtil.format(new Date(), DateFormatUtil.DATETIME_PATTERN_SS));
//        sb.append("</MsgDate >");
//        sb.append("</MessageHeader >");
//        sb.append("<RequestOption >");
//        sb.append("<triggerData > 0 </triggerData>");
//        sb.append("<dataAmount > 500 </dataAmount>");
//        sb.append("</RequestOption >");
//        sb.append("<MsgInfo flag = \"1\">");
//        sb.append("<Msg ></Msg >");
//        sb.append("<distinct value = \"0\" />");
//        //todo
//        for (XMLBean bean : params) {
//            sb.append("<query item = \"" + bean.getCondition() + "\" compy = \" " + bean.getSympol() + " \" value = \"'" + bean.getValue() + "'\" splice = \"AND\" />");
//        }
//        sb.append("</MsgInfo>");
//        sb.append("<GroupInfo flag = \"0\">");
//        sb.append("<AS ID = \"\" linkField = \"\"/>");
//        sb.append("</GroupInfo>");
//        sb.append("</ESBEntry>");
//        return sb.toString();
//    }
    public static String appendXML(String fid, List<XMLBean> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<ESBEntry>\n").
                append("        <AccessControl>\n").
                append("          <UserName>CDSS</UserName>\n").
                append("          <Password>123456</Password>\n").
                append("          <SysFlag>1</SysFlag>\n").
                append("          <Fid>" + fid + "</Fid>\n").
                append("        </AccessControl>\n").
                append("        <MessageHeader>\n").
                append("          <Fid>" + fid + "</Fid>\n").
                append("          <SourceSysCode>S60</SourceSysCode>\n").
                append("          <TargetSysCode>S00</TargetSysCode>\n").
                append("          <MsgDate>" + DateFormatUtil.format(new Date(), DateFormatUtil.DATETIME_PATTERN_SS) + "</MsgDate>\n").
                append("        </MessageHeader>\n").
                append("        <RequestOption>\n").
                append("          <triggerData>0</triggerData>\n").
                append("          <dataAmount>500</dataAmount>\n").
                append("        </RequestOption>\n").
                append("        <MsgInfo flag=\"1\" >\n").
                append("          <Msg></Msg>\n").
                append("          <distinct value=\"0\"/>\n");
        for (XMLBean bean : params) {
            sb.append("          <query item=\"" + bean.getCondition() + "\" compy=\" " + bean.getSympol() + " \" value=\" '" + bean.getValue() + "' \" splice=\"AND\"/>\n");
        }
        sb.append("        </MsgInfo>\n").
                append("        <GroupInfo flag=\"0\">\n").
                append("          <AS ID=\"\" linkField=\"\"/>\n").
                append("        </GroupInfo>\n").
                append("</ESBEntry>");
        return sb.toString();
    }
}

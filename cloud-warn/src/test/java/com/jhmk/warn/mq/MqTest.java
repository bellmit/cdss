//package com.jhmk.warn.mq;
//
//import com.ewell.mq.queue.MessageEntity;
//import com.ewell.mq.queue.QueueTools;
//import com.ibm.mq.MQException;
//import com.ibm.mq.MQQueueManager;
//
//import java.io.IOException;
//
///**
// * @author ziyu.zhou
// * @date 2018/11/13 17:08
// */
//
////@RunWith(SpringRunner.class)
////@SpringBootTest
//public class MqTest {
//    /**
//     * 获取连接MQ的队列管理器实例
//     *
//     * @return 队列管理器实例
//     * @throws MQException
//     * @gateKeeper 连接对象在配置文件中的节点名 QMGR.P60
//     */
//
//    public static MQQueueManager connect(String gateKeeper) throws MQException {
//        QueueTools queueTools = new QueueTools();
//        MQQueueManager queueManager = null;
//        queueManager = queueTools.connect(gateKeeper);
//        return queueManager;
//    }
//
//    //    @Test
//    public void connect() throws MQException {
//        QueueTools queueTools = new QueueTools();
//        MQQueueManager queueManager = null;
//        queueManager = queueTools.connect("QMGR.P60");
//        System.out.println(queueManager);
//    }
//
//    public static void main(String[] args) throws Exception {
////        MQQueueManager connect = connect("QMGR.P60");
////        System.out.println(connect);
//
//        QueueTools queueTools = new QueueTools();
//        MQQueueManager queueManager = null;
//        queueManager = queueTools.connect("QMGR.P60");
//
//        String reqMsg = "<ESBEntry>\n" +
//                "        <AccessControl>\n" +
//                "          <UserName>CDSS</UserName>\n" +
//                "          <Password>123456</Password>\n" +
//                "          <SysFlag>1</SysFlag>\n" +
//                "          <Fid>BS35001</Fid>\n" +
//                "        </AccessControl>\n" +
//                "        <MessageHeader>\n" +
//                "          <Fid>BS35001</Fid>\n" +
//                "          <SourceSysCode>S60</SourceSysCode>\n" +
//                "          <TargetSysCode>S00</TargetSysCode>\n" +
//                "          <MsgDate>2018-11-13 16:35:01</MsgDate>\n" +
//                "        </MessageHeader>\n" +
//                "        <RequestOption>\n" +
//                "          <triggerData>0</triggerData>\n" +
//                "          <dataAmount>500</dataAmount>\n" +
//                "        </RequestOption>\n" +
//                "        <MsgInfo flag=\"1\" >\n" +
//                "          <Msg></Msg>\n" +
//                "          <distinct value=\"0\"/>\n" +
//                "          <query item=\"ORDER_ID\" compy=\" = \" value=\" '019259471' \" splice=\"AND\"/>\n" +
//                "          <query item=\"ORDER_ID\" compy=\" = \" value=\" '019259471' \" splice=\"AND\"/>\n" +
//                "        </MsgInfo>\n" +
//                "        <GroupInfo flag=\"0\">\n" +
//                "          <AS ID=\"\" linkField=\"\"/>\n" +
//                "        </GroupInfo>\n" +
//                "</ESBEntry>";
//        String msgId = "";
//        msgId = queueTools.putMsg(queueManager, "BS35001", reqMsg);
//        String respMsg = null;
//        respMsg = queueTools.getMsgById(queueManager, "BS10001", msgId, 3);
//        System.out.println(respMsg);
//    }
//
//
//
//    /**
//     * 断开MQ连接
//     *
//     * @param qmr
//     * @throws MQException
//     */
//    public void disconnectMQ(MQQueueManager qmr) throws MQException {
//        return;
//    }
//
//
//    public String putMsg(MQQueueManager qmr, String fid, String msg) throws MQException, IOException {
//        QueueTools queueTools = new QueueTools();
//        MQQueueManager queueManager = connect("QMGR.P60");
//        String reqMsg = "请求内容";
//        String msgId = "";
//        try {
//            msgId = queueTools.putMsg(queueManager, "BS10001", reqMsg);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return msgId;
//    }
//
//    /**
//     * 根据服务Id将消息发送到相应队列，并返回消息Id
//     *
//     * @param qmr 队列管理器实例
//     * @param fid 服务Id
//     * @param msg 消息内容,不能为null
//     * @return 消息Id
//     * @throws MQException
//     * @throws IOException
//     * @paramsign队列序号，在需要为一个服务配置3个及以上队列是设置，设置为0时两个函数完全相同
//     */
//    public String putMsg(MQQueueManager qmr, String fid, String sign, String msg) throws MQException, IOException {
//        return "";
//    }
//
//    /**
//     * 根据服务Id从对应的队列中获取消息， 方法提供超时等待设置，参数waitInterval为等待时间，单位：秒
//     *
//     * @param qmr          队列管理器实例
//     * @param fid          服务Id
//     * @param waitInterval 超时等待时间，单位：秒
//     * @return 返回包含消息内容和消息Id的对象
//     * @throws MQException
//     * @throws IOException
//     * @paramsign队列序号，在需要为一个服务配置3个及以上队列是设置，设置为1时两个函数完全相同
//     */
//    public MessageEntity getMsg(MQQueueManager qmr, String fid, int waitInterval) throws MQException, IOException {
//        return null;
//    }
//
//    public MessageEntity getMsg(MQQueueManager qmr, String fid, String sign, int waitInterval) throws MQException, IOException {
//        return null;
//    }
//
//    public String getMsgById(MQQueueManager qmr, String fid, String msgid, int waitInterval) throws MQException, IOException {
//        return "";
//    }
//
//    /**
//     * 根据服务Id从相应队列中获取指定消息Id的第一条消息， 方法提供超时等待设置，参数waitInterval为等待时间，单位：秒
//     *
//     * @param qmr          队列管理器实例
//     * @param fid          服务Id
//     * @param msgid        消息Id
//     * @param waitInterval 超时等待时间，单位：秒
//     * @return 消息内容
//     * @throws MQException
//     * @throws IOException
//     * @paramsign队列序号，在需要为一个服务配置3个及以上队列是设置，设置为1时两个函数完全相同
//     */
//    public String getMsgById(MQQueueManager qmr, String fid, String sign, String msgid, int waitInterval) throws MQException, IOException {
//        return "";
//    }
//}

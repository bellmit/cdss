package com.jhmk.cloudutil.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.Socket;

/**
 * @author ziyu.zhou
 * @date 2019/1/23 18:15
 */
@Service
public class SocketUtil {
    Logger logger = LoggerFactory.getLogger(SocketUtil.class);

    /**
     * @param ip
     * @param port
     * @param patientId
     * @param visitId
     * @param admission_time 入院时间
     * @param type
     * @return
     */
    public String getReturnData(String ip, int port, String patientId, String visitId, String admission_time, String type) {

        Socket socket = null; // 从socket中获取输入输出流
        OutputStream os = null;
        PrintWriter pw = null;
        InputStream is = null;
        BufferedReader br = null;
        try {
            socket = new Socket(ip, port);
            //2、获取输出流，向服务器端发送信息
            os = socket.getOutputStream();//字节输出流
            pw = new PrintWriter(os);//将输出流包装成打印流
            pw.write(patientId + "#" + visitId + "#" + admission_time + "##" + type);
            pw.flush();
            socket.shutdownOutput();
            //3、获取输入流，并读取服务器端的响应信息
            is = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            String info = null;
            while ((info = br.readLine()) != null) {
                logger.info("调用检验报告返回结果为：{}", info);
                return info;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //4、关闭资源
            try {
                br.close();
                is.close();
                pw.close();
                os.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

}

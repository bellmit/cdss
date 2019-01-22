package com.jhmk.cloudservice.webservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * socket请求数据
 */
@Slf4j
public class SocketClientUtil {

    Socket socket = null;
    OutputStream os = null;
    PrintWriter pw = null;
    InputStream is = null;
    BufferedReader br = null;

    String info = null;//服务端返回值

    /**
     * 初始化连接
     * @param ip
     * @param port
     */
    public SocketClientUtil(String ip,int port){
        try {
            //获取socket连接
            socket = new Socket(ip,port);
        } catch (UnknownHostException e) {
            log.error("socket未知主机地址连接异常,连接信息：IP为："+ip+",端口为："+port);
            e.printStackTrace();
        } catch (IOException e) {
            log.error("socket IO异常");
            e.printStackTrace();
        }
    }

    /**
     * 根据patientId、visitId、type查询数据
     * @return
     * 传输格式：T001184393#24#2017-01-09 00:00:00#2018-01-20 00:00:00#binganshouye
     * 解析：patient_id#visit_id#开始时间#结束时间#表名称
     * 若某些值不需要传，对应的地方空字符串代替 如：
     * T001184393#24###binganshouy
     */
    public void sendData(String patientId, String visitId,String type){
        if(StringUtils.isEmpty(patientId) || StringUtils.isEmpty(visitId) || StringUtils.isEmpty(type)){
            return ;
        }
        try {
            //获取输出流，向服务器端发送信息
            os = socket.getOutputStream();//字节输出流
            pw = new PrintWriter(os);//将输出流包装成打印流
            //往服务端发送请求数据
            pw.write(patientId + "#" + visitId + "###" + type);
            pw.flush();
            //关闭输出流
            socket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取服务端发送的信息
     * @return
     */
    public String getData(){
        //获取输入流，并读取服务器端的响应信息
        try {
            is = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            //获取服务端返回数据
            while ((info = br.readLine()) != null) {
                log.info("我是客户端，服务器说：" + info);
                return info;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭资源
     */
    public void closeResource(){
        try {
            if(os!=null) {
                os.close();
                os=null;
            }if(is!=null) {
                is.close();
                is=null;
            }if(pw!=null) {
                pw.close();
                pw=null;
            }if(br!=null) {
                br.close();
                br=null;
            }
            if(socket!=null) {
                socket.close();
                socket=null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

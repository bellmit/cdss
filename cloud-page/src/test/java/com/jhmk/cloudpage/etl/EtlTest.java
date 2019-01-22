package com.jhmk.cloudpage.etl;

import com.jhmk.cloudentity.common.Huanzhexinxi;
import com.jhmk.cloudentity.earlywaring.entity.rule.Binglizhenduan;
import com.jhmk.cloudentity.earlywaring.entity.rule.Jianchabaogao;
import com.jhmk.cloudentity.earlywaring.entity.rule.Jianyanbaogao;
import com.jhmk.cloudservice.cdssPageService.AnalyzeService;
import org.junit.Test;

import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * @author ziyu.zhou
 * @date 2019/1/18 14:10
 */

public class EtlTest {
    @Test
    public void testBinganshouye() {
        AnalyzeService analyzeService = new AnalyzeService();
        String binganshouye = getReturnData("0009643012", "6", "binganshouye");
        Huanzhexinxi huanzhexinxi = analyzeService.analyzeJson2Huanzhexinxi(binganshouye);
        System.out.println(huanzhexinxi);
    }

    @Test
    public void testbinglizhenduan() {
        AnalyzeService analyzeService = new AnalyzeService();

        String data = getReturnData("0000969000", "1", "binglizhenduan");
        List<Binglizhenduan> binglizhenduans = analyzeService.analyzeJson2Binglizhenduan(data);

        System.out.println(binglizhenduans);
//        System.out.println(huanzhexinxi);

    }

    @Test
    public void testJianyanbaogao() {
        AnalyzeService analyzeService = new AnalyzeService();

//        String data = getReturnData("192.168.8.42", 6667, "0009530899", "1", "jianyanbaogao");
        String data = getReturnData("192.168.8.42", 6667, "0009583961", "1", "jianyanbaogao");
        List<Jianyanbaogao> beanList = analyzeService.analyzeJson2Jianyanbaogao(data);
//        List<Jianchabaogao> beanList1 = analyzeService.analyzeJson2Jianchabaogao(data);

        System.out.println(beanList);

    }@Test
    public void testJianchabaogao() {
        AnalyzeService analyzeService = new AnalyzeService();

//        String data = getReturnData("192.168.8.42", 6667, "0009530899", "1", "jianyanbaogao");
        String data = getReturnData("192.168.8.42", 6667, "0009583961", "1", "jianchabaogao");
        List<Jianchabaogao> beanList = analyzeService.analyzeJson2Jianchabaogao(data);
//        List<Jianchabaogao> beanList1 = analyzeService.analyzeJson2Jianchabaogao(data);

        System.out.println(beanList);

    }

    private String getReturnData(String patientId, String visitId, String type) {

        Socket socket = null; // 从socket中获取输入输出流
        OutputStream os = null;
        PrintWriter pw = null;
        InputStream is = null;
        BufferedReader br = null;
        try {
            socket = new Socket("192.168.8.42", 6666);
            //2、获取输出流，向服务器端发送信息
            os = socket.getOutputStream();//字节输出流
            pw = new PrintWriter(os);//将输出流包装成打印流
            pw.write(patientId + "#" + visitId + "###" + type);
            pw.flush();
            socket.shutdownOutput();
            //3、获取输入流，并读取服务器端的响应信息
            is = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            String info = null;
            while ((info = br.readLine()) != null) {
                System.out.println("我是客户端，服务器说：" + info);
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

    private String getReturnData(String ip, int port, String patientId, String visitId, String type) {

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
            pw.write(patientId + "#" + visitId + "###" + type);
            pw.flush();
            socket.shutdownOutput();
            //3、获取输入流，并读取服务器端的响应信息
            is = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            String info = null;
            while ((info = br.readLine()) != null) {
                System.out.println("我是客户端，服务器说：" + info);
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

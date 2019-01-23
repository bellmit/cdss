package com.jhmk.cloudpage.controller;

import com.alibaba.fastjson.JSONObject;
import com.jhmk.cloudentity.base.BaseController;
import com.jhmk.cloudentity.common.JiaheRuleBean;
import com.jhmk.cloudentity.earlywaring.entity.rule.Binganshouye;
import com.jhmk.cloudentity.earlywaring.entity.rule.Jianchabaogao;
import com.jhmk.cloudentity.earlywaring.entity.rule.Jianyanbaogao;
import com.jhmk.cloudpage.config.SocketPropConf;
import com.jhmk.cloudservice.cdssPageService.AnalyzeService;
import com.jhmk.cloudservice.cdssPageService.KnowledgeBaseService;
import com.jhmk.cloudservice.warnService.service.BinganshouyeService;
import com.jhmk.cloudservice.warnService.service.JianchabaogaoService;
import com.jhmk.cloudservice.warnService.service.JianyanbaogaoService;
import com.jhmk.cloudutil.config.UrlPropertiesConfig;
import com.jhmk.cloudutil.model.AtResponse;
import com.jhmk.cloudutil.model.ResponseCode;
import com.jhmk.cloudutil.util.DocumentUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.Socket;
import java.util.*;

/**
 * @author ziyu.zhou
 * @date 2018/8/31 18:54
 */
@Controller
@RequestMapping("/demo")
public class DemoController extends BaseController {
    @Autowired
    KnowledgeBaseService knowledgeBaseService;
    @Autowired
    UrlPropertiesConfig urlPropertiesConfig;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    DocumentUtil documentUtil;
    @Autowired
    SocketPropConf socketPropConf;
    @Autowired
    JianchabaogaoService jianchabaogaoService;
    @Autowired
    JianyanbaogaoService jianyanbaogaoService;
    @Autowired
    BinganshouyeService binganshouyeService;
    @Autowired
    private ApplicationContext applicationContext;

    @RequestMapping("/test1")
    public AtResponse demo() {
        AtResponse resp = new AtResponse(System.currentTimeMillis());
        resp.setData("www.baidu.com");
        resp.setResponseCode(ResponseCode.OK);
        return resp;
    }

    @RequestMapping("/testtwo")
    @ResponseBody
    public AtResponse demo2() {
        AtResponse resp = new AtResponse(System.currentTimeMillis());
        String s = "1234";
        resp.setData(s);
        return resp;
    }

    @RequestMapping("/testSocket")
    @ResponseBody
    public void test() throws Exception { // 向服务器发出请求建立连接
        Socket socket = new Socket("192.168.8.42", 6666); // 从socket中获取输入输出流
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter pw = new PrintWriter(outputStream);
//        InputStreamReader isr = new InputStreamReader(inputStream, "GBK");
        OutputStream dos = socket.getOutputStream();
        dos.write("000001460800#2###binganshouye".getBytes());
        pw.println("000001460800#2###binganshouye");
        pw.flush();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String result = br.readLine();
        System.out.println(result);
        inputStream.close();
        outputStream.close();
        socket.close();
    }

    @RequestMapping("/testDocumentUtil")
    @ResponseBody
    public void demo2(HttpServletResponse response, @RequestBody String name) {
        JSONObject object = JSONObject.parseObject(name);
        String name1 = object.getString("name");
        List<String> allChildDisease = documentUtil.getAllChildDisease(name1);
        String standardFromAlias = documentUtil.getStandardFromAlias(name1);
        List<String> medicineFromLevelName = documentUtil.getMedicineFromLevelName(name1);
        System.out.println(medicineFromLevelName);
    }


    public static void main(String[] args) throws IOException {
        AnalyzeService analyzeService = new AnalyzeService();
        //客户端
//1、创建客户端Socket，指定服务器地址和端口
        Socket socket = new Socket("192.168.8.42", 6666); // 从socket中获取输入输出流
//2、获取输出流，向服务器端发送信息
        OutputStream os = socket.getOutputStream();//字节输出流
        PrintWriter pw = new PrintWriter(os);//将输出流包装成打印流
        pw.write("0000969000#1###binganshouye");
        pw.flush();
        socket.shutdownOutput();
//3、获取输入流，并读取服务器端的响应信息
        InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String info = null;
        while ((info = br.readLine()) != null) {
            System.out.println("我是客户端，服务器说：" + info);
        }
//4、关闭资源
        br.close();
        is.close();
        pw.close();
        os.close();
        socket.close();
    }


    @GetMapping("/testsocketPropConf")
    public void testsocketPropConf(HttpServletResponse response) {
        String ip = socketPropConf.getIp();
        Integer port = socketPropConf.getPort();
        wirte(response, ip + port);
    }

    @GetMapping("/getRule")
    public void getRule(HttpServletResponse response) {
        JiaheRuleBean jiaheRuleBean = new JiaheRuleBean();
        Binganshouye binganshouyeFromETL = binganshouyeService.getBinganshouyeFromETL("0000969000", "1");
        jiaheRuleBean.setBinganshouye(binganshouyeFromETL);
        List<Jianyanbaogao> jianyanbaogao = jianyanbaogaoService.getJianyanbaogao(jiaheRuleBean);
        List<Jianyanbaogao> distinctJianyanbaogao = jianyanbaogaoService.getDistinctJianyanbaogao(jianyanbaogao);
        List<Jianchabaogao> jianchabaogao = jianchabaogaoService.getJianchabaogao(jiaheRuleBean);
        wirte(response, "123");
    }
}

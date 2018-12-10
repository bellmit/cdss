//package com.jhmk.warn.mq;
//
//import com.ewell.mq.queue.async.config.ConfigUtil;
//import org.dom4j.Element;
//import org.dom4j.io.SAXReader;
//
//import java.net.URL;
//
///**
// * @author ziyu.zhou
// * @date 2018/11/16 10:37
// */
//
//public class Demo1 {
//    public static void main(String[] args) {
//        SAXReader reader = new SAXReader();
////        this.document = reader.read(this.resolveURL(this.configPath + this.configFile).openStream());
//
//    }
//
//    private ClassLoader getDefaultClassLoader() {
//        ClassLoader cl = null;
//
//        try {
//            cl = Thread.currentThread().getContextClassLoader();
//        } catch (Throwable var4) {
//            ;
//        }
//
//        if (cl == null) {
//            cl = ConfigUtil.class.getClassLoader();
//            if (cl == null) {
//                try {
//                    cl = ClassLoader.getSystemClassLoader();
//                } catch (Throwable var3) {
//                    ;
//                }
//            }
//        }
//
//        return cl;
//    }
//    private URL resolveURL(String path) {
//        ClassLoader cl = this.getDefaultClassLoader();
//        URL url = cl != null ? cl.getResource(path) : ClassLoader.getSystemResource(path);
//        return url != null ? url : ConfigUtil.class.getClassLoader().getResource(path);
//    }
//
//
//}

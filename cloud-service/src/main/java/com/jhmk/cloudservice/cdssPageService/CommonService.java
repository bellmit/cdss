package com.jhmk.cloudservice.cdssPageService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ziyu.zhou
 * @date 2018/8/23 10:33
 */
@Service
public class CommonService {
    Logger logger = LoggerFactory.getLogger(CommonService.class);

    /**
     * 获取药品同义词 集合
     *
     * @return
     */
    public List<String> getDrudList() {
        List<String> list = new ArrayList<>();
        Resource resource = new ClassPathResource("drugSynonym");
        File file = null;
        String s;
        try {
            file = resource.getFile();
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            while ((s = br.readLine()) != null) {
                String line = br.readLine();
                if (line == null || "".equals(line) || "null".equals(line)) {
                    continue;
                }
                list.add(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.debug("获取药品同义词表失败，错误原因{}",e.getCause());
        }
        return list;

    }

    /**
     * 获取标准名
     *
     * @param str 牛黄酸#@#牛胆素
     * @return 牛黄酸
     */
    public String getStandardDrugName(String str) {
        String sName = "";
        if (str.contains("#@#")) {
            String[] split = str.split("#@#");
            sName = split[0];
        } else {
            logger.info("此药品名存在问题：{}", str);
        }
        return sName;
    }


    /**
     * 判断名字是都在同义词表中
     *
     * @param name
     * @param str
     * @return
     */
    public boolean isExist(String name, String str) {
        String[] split = str.split("#@#");
        String sName = split[0];
        String otherNames = split[1];
        String[] otherNameList = otherNames.split("@");
        List<String> list = new LinkedList();
        list.add(sName);
        list.addAll(Arrays.asList(otherNameList));
        if (list.indexOf(name) != -1) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 根据药品名获取标准名 不存在 则返回本身
     *
     * @param name 药品名
     * @param list 同义词集合
     * @return
     */
    public String getDrugStandardName(String name, List<String> list) {
        for (String str : list) {
            if (str.contains(name)) {
                if (isExist(name, str)) {
                    return getStandardDrugName(str);
                }
            }
        }
        return name;
    }
}

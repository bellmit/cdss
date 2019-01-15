package com.jhmk.cloudpage.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author ziyu.zhou
 * @date 2018/8/25 11:03
 */

public class ReadFileService {
    public static List<String> readFile2List(String fileName) {
        List<String> list = new LinkedList<>();
        File file = new File(fileName);
        if (!file.isFile()) {
            return null;
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = br.readLine()) != null) {
                if (line != null && !"".equals(line.trim())) {
                    list.add(line);
                } else {
                    list.add("");
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }


    public static Set<String> readSource(String name) {
        Set<String> liiNames = new LinkedHashSet<>();
        Resource resource = new ClassPathResource(name);
        File file = null;
        BufferedReader br = null;
        try {
            file = resource.getFile();
            br = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = br.readLine()) != null) {
                String ill = line.replaceAll("\\(", "（").replaceAll("\\)", "）");
                liiNames.add(ill);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return liiNames;
    }

    public static List<String> readSourceList(String name) {
        List<String> liiNames = new LinkedList<>();
        Resource resource = new ClassPathResource(name);
        File file = null;
        BufferedReader br = null;
        try {
            file = resource.getFile();
            br = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = br.readLine()) != null) {
                String ill = line.replaceAll("\\(", "（").replaceAll("\\)", "）");
                liiNames.add(ill);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return liiNames;
    }

    public static List<String> readCsv(String name) {
        List<String> liiNames = new LinkedList<>();
        File file = null;
        BufferedReader br = null;
        try {
            file = new File(name);

            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));//这里如果csv文件编码格式是utf-8,改成utf-8即可
            String line = null;
            while ((line = br.readLine()) != null) {
                String ill = line.replaceAll("\\(", "（").replaceAll("\\)", "）");
                liiNames.add(ill);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return liiNames;
    }

    public static void main(String[] args) {
        String fileName = "C:/Users/11075/Desktop/demo1.csv";
        List<String> list = readCsv(fileName);
        System.out.println(list);
    }

}

package com.jhmk.cloudutil.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author ziyu.zhou
 * @date 2018/12/10 15:50
 */

public class FileUtil {
    Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 将字符串写入文件
     *
     * @param data
     * @param fileName
     */
    public static void writeStr2File(String data, String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                FileWriter fw = new FileWriter(file.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(data);
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

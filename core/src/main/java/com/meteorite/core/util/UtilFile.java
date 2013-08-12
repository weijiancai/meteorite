package com.meteorite.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author wei_jc
 * @version 1.0
 */
public class UtilFile {
    public static String readString(File file) throws IOException {
        StringBuilder result = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            result.append(line);
        }
        br.close();
        return  result.toString();
    }

    /**
     * 根据目录路径，创建目录结构
     *
     * @param dir 目录路径
     * @return 返回创建后的目录
     */
    public static File makeDirs(File dir) {
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public static File makeDirs(String dirPath) {
        return makeDirs(new File(dirPath));
    }


    public static File makeDirs(String parentDir, String subDir) {
        return makeDirs(new File(parentDir, subDir));
    }
}

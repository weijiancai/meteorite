package com.meteorite.core.util;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

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

    public static File makeDirs(File parentDir, String subDir) {
        return makeDirs(new File(parentDir, subDir));
    }

    /**
     * 创建文件
     *
     * @param parentDir 父目录
     * @param fileName  文件名
     * @return 返回创建后的文件
     */
    public static File createFile(File parentDir, String fileName) throws IOException {
        File file = new File(parentDir, fileName);
        try {
            if(!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            throw new IOException(file.getAbsolutePath(), e);
        }
        return file;
    }

    public static void createFile(File file, URL url) throws IOException {
        try {
            URLConnection conn = url.openConnection();
            InputStream is = conn.getInputStream();
            FileOutputStream os = new FileOutputStream(file);
            int i;
            while ((i = is.read()) != -1) {
                os.write(i);
            }
            os.close();
            is.close();
        } catch (IOException e) {

        }
    }

    /**
     * 从类路径下创建文件
     *
     * @param path 文件路径
     * @return 返回类路径创建的文件
     * @throws URISyntaxException
     */
    public static File createCPFile(String path) throws URISyntaxException {
        return new File(new File(UtilFile.class.getResource("/").toURI()), path);
    }
}

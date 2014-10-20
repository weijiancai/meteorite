package com.meteorite.core.util;

import com.meteorite.core.datasource.classpath.ClassPathDataSource;
import com.meteorite.core.model.ITreeNode;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * 文件工具类
 *
 * @author wei_jc
 * @since 1.0
 */
public class UFile {
    private static final Logger log = Logger.getLogger(UFile.class);

    public static String readString(File file) throws IOException {
        return readString(file, "UTF-8");
    }

    public static String readString(File file, String charset) throws IOException {
        return readString(new FileInputStream(file), charset);
    }

    public static String readString(InputStream is, String charset) throws IOException {
        StringBuilder result = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, charset));
        String line;
        while ((line = br.readLine()) != null) {
            result.append(line).append("\r\n");
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
            e.printStackTrace();
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
        return new File(new File(UFile.class.getResource("/").toURI()), path);
    }

    /**
     * 将字节数据写入到目标文件
     *
     * @param bytes 字节数据
     * @param target 目标文件
     */
    public static void write(byte[] bytes, File target) throws IOException {
        if (!target.getParentFile().exists()) {
            target.getParentFile().mkdirs();
        }
        BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(target));
        os.write(bytes, 0, bytes.length);
        os.flush();
        os.close();
    }

    /**
     * 将输入流中数据写入到输出流中
     *
     * @param is 输入流
     * @param os 输出流
     */
    public static void write(InputStream is, OutputStream os) throws IOException {
        int i;
        byte[] arrays = new byte[4096];
        while ((i = is.read(arrays)) != -1) {
            os.write(arrays, 0, i);
        }
        os.flush();
    }

    /**
     * 将文本字符串写入文件中
     *
     * @param text 文本字符串
     * @param filePath 文件路径
     */
    public static void write(String text, String filePath) throws IOException {
        write(text.getBytes(), new File(filePath));
    }

    /**
     * 将URL中的内容写入文件中，文件名为url的后缀名
     *
     * @param url URL
     * @param baseDir 基准目录
     * @throws IOException
     */
    public static void write(URL url, File baseDir) throws IOException {
        if (!baseDir.exists()) {
            baseDir.mkdirs();
        }
        String fileName = UString.getLastName(url.getFile(), "/");
        File file = new File(baseDir, fileName);
        if (file.exists()) {
            return;
        }
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        InputStream is = httpConn.getInputStream();
        FileOutputStream fos = new FileOutputStream(file);
        write(is, fos);
        fos.close();
        is.close();
    }

    public static File getClassPathDir() {
        URL url = UFile.class.getClassLoader().getResource("");
        if (url == null) {
            url = UFile.class.getResource("");
        }
        return new File(url.getFile());
    }

    /**
     * 获得文件名的扩展名，如file.txt，返回txt
     *
     * @param fileName 文件名
     * @return 返回扩展名
     */
    public static String getFileExt(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static void copyTreeFromClassPath(String source, final File target) {
        ClassPathDataSource cp = ClassPathDataSource.getInstance();
        try {
            ITreeNode tree = cp.getNavTree(source);
            iteratorTree(tree, new Callback<ITreeNode>() {
                @Override
                public void call(ITreeNode node, Object... obj) throws Exception {
                    InputStream is = UIO.getInputStream("/" + node.getId(), UIO.FROM.CP);
                    File file = new File(target, node.getId());
                    if (node.getChildren().size() > 0) {
                        return;
                    }

                    File parentDir = file.getParentFile();
                    if (!parentDir.exists()) {
                        parentDir.mkdirs();
                    }
                    write(is, new FileOutputStream(file));
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void iteratorTree(ITreeNode node, Callback<ITreeNode> callback) throws Exception {
        callback.call(node);
        List<ITreeNode> children = node.getChildren();
        if (children != null && children.size() > 0) {
            for (ITreeNode treeNode : children) {
                iteratorTree(treeNode, callback);
            }
        }
    }

    /**
     * 获得文件的大小，用字符串表示，例如: 10TB 10GB 10MB 10KB
     *
     * @param file 文件
     * @return 返回字符串表示的文件大小
     */
    public static String getSize(File file) {
        double kb = 1024;
        double mb = 1024 * 1024;
        double gb = 1024 * 1024 * 1024;
        double tb = 1024.0 * 1024 * 1024 * 1024;

        long number = file.length();
        String result;
        if(number > tb) {
            result = String.format("%.2f", number / tb) + " TB";
        } else if(number > gb) {
            result = String.format("%.2f", number / gb) + " GB";
        } else if(number > mb) {
            result = String.format("%.2f", number / mb) + " MB";
        } else {
            result = String.format("%.2f", number / kb) + " KB";
        }

        return result.replace(".00", "");
    }
}

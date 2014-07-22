package com.meteorite.core.datasource.classpath;

import com.meteorite.core.datasource.ResourceItem;
import com.meteorite.core.loader.ILoader;
import com.meteorite.core.model.impl.BaseNavTreeNode;
import com.meteorite.core.util.UString;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类路径加载器
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class ClassPathLoader implements ILoader {
    private static final Logger log = Logger.getLogger(ClassPathLoader.class);
    private static ClassPathLoader loader;

    private BaseNavTreeNode navTree;
    private Map<String, ResourceItem> nodeMap;
    private String baseDir;

    private ClassPathLoader() {
    }

    public static ClassPathLoader getLoader() {
        if (loader == null) {
            loader = new ClassPathLoader();
        }

        return loader;
    }

    @Override
    public void load() throws Exception {
        navTree = new BaseNavTreeNode();
        navTree.setId("/");
        navTree.setName("Root");
        navTree.setDisplayName("Root");
        nodeMap = new HashMap<>();

        URL url = Thread.currentThread().getContextClassLoader().getResource("/");
        if (url == null) {
            url = getClass().getResource("/");
        }
        // 得到协议的名称
        String protocol = url.getProtocol();
        // 如果是以文件的形式保存在服务器上
        if ("file".equals(protocol)) {
            log.info("file类型的扫描");
            // 获取包的物理路径
            String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
            File root = new File(filePath);
            baseDir = root.getAbsolutePath();
            // 以文件的方式扫描整个包下的文件 并添加到集合中
            loadByFile(baseDir, baseDir);
        } else if ("jar".equals(protocol)) {
            // 如果是jar包文件
            // 定义一个JarFile
            log.info("jar类型的扫描");
            baseDir = url.getFile();
            JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
            // 从此jar包 得到一个枚举类
            Enumeration<JarEntry> entries = jar.entries();
            // 同样的进行循环迭代
            while (entries.hasMoreElements()) {
                // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                JarEntry entry = entries.nextElement();
                String name = entry.getName().replace("//", "/");
                System.out.println(name);
                if (nodeMap.containsKey(name)) {
                    continue;
                }

                ClassPathResourceItem node = new ClassPathResourceItem("jar", null);
                node.setId(name);
                node.setName(UString.getLastName(name, "/"));
                node.setDisplayName(node.getName());
                nodeMap.put(name, node);
            }
        }

        // 第二次循环，设置父节点
        for (Map.Entry<String, ResourceItem> entry : nodeMap.entrySet()) {
            String name = entry.getKey();
            BaseNavTreeNode node = entry.getValue();
            if (name.endsWith("/")) {
                name = name.substring(0, name.length() - 1);
            }

            BaseNavTreeNode parent = null;
            int idx = name.lastIndexOf("/");
            if (idx > 0) {
                parent = nodeMap.get(name.substring(0, idx + 1));
            }
            if (parent != null) {
                parent.addChild(node);
            } else {
                navTree.addChild(node);
            }
        }
    }

    private void loadByFile(String basePath, String path) {
        // 获取此包的目录 建立一个File
        File dir = new File(path);
        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        // 如果存在 就获取包下的所有文件 包括目录
        File[] files = dir.listFiles();
        // 循环所有文件
        if (files == null) {
            return;
        }
        for (File file : files) {
            ClassPathResourceItem node = new ClassPathResourceItem("file", basePath);
            node.setName(file.getName());
            node.setDisplayName(node.getName());
            System.out.println(file);

            // 如果是目录 则继续扫描
            if (file.isDirectory()) {
                node.setId(file.getAbsolutePath().substring(basePath.length() + 1).replace("\\", "/") + "/");
                if (!nodeMap.containsKey(node.getId())) {
                    nodeMap.put(node.getId(), node);
                }
                loadByFile(basePath, file.getAbsolutePath());
            } else {
                node.setId(file.getAbsolutePath().substring(basePath.length() + 1).replace("\\", "/"));
                if (!nodeMap.containsKey(node.getId())) {
                    nodeMap.put(node.getId(), node);
                }
            }
        }
    }

    public BaseNavTreeNode getNavTree() {
        return navTree;
    }

    public ResourceItem getResource(String path) {
        return nodeMap.get(path);
    }

    public String getBaseDir() {
        return baseDir;
    }
}

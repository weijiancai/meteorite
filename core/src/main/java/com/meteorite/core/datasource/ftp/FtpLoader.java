package com.meteorite.core.datasource.ftp;

import com.meteorite.core.loader.ILoader;
import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.model.impl.BaseTreeNode;
import com.meteorite.core.observer.Observer;
import com.meteorite.core.util.UString;
import org.apache.commons.net.ftp.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Ftp加载器
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class FtpLoader implements ILoader {
    private static final Logger log = Logger.getLogger(FtpLoader.class);

    private final String ip;
    private final String user;
    private final String password;

    private FTPClient client;
    private FtpResourceItem navTree;
    private Map<String, FtpResourceItem> nodeMap;

    public FtpLoader(String ip, String user, String password) {
        this.ip = ip;
        this.user = user;
        this.password = password;

        client = new FTPClient();
//        client.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out), true));
        client.addProtocolCommandListener(new LogCommandListener());
        FTPClientConfig config = new FTPClientConfig();
        config.setServerTimeZoneId("zh-CN");
        client.configure(config);

        nodeMap = new HashMap<String, FtpResourceItem>();
        navTree = new FtpResourceItem();
        navTree.setId("/");
        navTree.setName("Root");
        navTree.setDisplayName("Root");
        nodeMap.put("/", navTree);
    }

    @Override
    public void load() throws Exception {
        /*nodeMap = new HashMap<>();
        navTree = new FtpResourceItem();
        navTree.setId("/");
        navTree.setName("Root");
        navTree.setDisplayName("Root");
        nodeMap.put("/", navTree);*/

        // 异步获取，不需要一次全部加载完
        /*if (connect()) {
            iterator("/he_gy", null);

            // 第二次循环，设置父节点
            for (Map.Entry<String, FtpResourceItem> entry : nodeMap.entrySet()) {
                String name = entry.getKey();
                FtpResourceItem node = entry.getValue();
                if (name.endsWith("/")) {
                    name = name.substring(0, name.length() - 1);
                }

                FtpResourceItem parent = null;
                int idx = name.lastIndexOf("/");
                if (idx > 0) {
                    parent = nodeMap.get(name.substring(0, idx));
                }
                if (parent != null) {
                    parent.addChild(node);
                } else {
                    navTree.addChild(node);
                }
            }
        }*/

//        client.disconnect();
    }

    private void iterator(String parent, FTPFile ftpFile) throws IOException {
        FtpResourceItem node = new FtpResourceItem();
        node.setId(parent);
        node.setName(UString.getLastName(parent, "/"));
        node.setDisplayName(node.getName());
        if (ftpFile != null) {
            node.setDisplayName(node.getName());
            node.setLastModified(ftpFile.getTimestamp().getTime());
            node.setSize(ftpFile.getSize());
            node.setType(ftpFile.isDirectory() ? "目录" : "文件");
        }
        nodeMap.put(parent, node);

        FTPFile[] files = client.listFiles(parent);
        for (FTPFile file : files) {
            String name = parent + "/" + file.getName();
//            System.out.println(name);
            if (file.isDirectory()) {
                iterator(name, file);
            } else {
                node = new FtpResourceItem();
                node.setId(name);
                node.setName(file.getName());
                node.setDisplayName(node.getName());
                node.setLastModified(file.getTimestamp().getTime());
                node.setSize(file.getSize());
                node.setType(file.isDirectory() ? "目录" : "文件");
                nodeMap.put(name, node);
            }
        }
    }

    public boolean connect() {
        if(client.isConnected() && client.isAvailable()) {
            try {
                String status = client.getStatus();
                System.out.println(status);
                return true;
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                try {
                    client.disconnect();
                } catch (IOException e1) {
                    log.error(e1.getMessage(), e1);
                }
            }
        }

        try {
            client.connect(ip);
            log.info("Connected to " + ip + " on " + client.getLocalPort());
        } catch (IOException e) {
            log.error("Could not connect to server.", e);
            return false;
        }

        try {
            if (!client.login(user, password)) {
                client.logout();
                log.error(String.format("登陆【%s】失败，用户名【%s】！", ip, user));
                return false;
            }
            client.setControlEncoding("GBK");
            client.setFileType(FTP.BINARY_FILE_TYPE);
            client.enterLocalPassiveMode();
        } catch (FTPConnectionClosedException e) {
            log.error("Server closed connection.", e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return true;
    }

    public BaseTreeNode getNavTree() {
        return navTree;
    }

    public BaseTreeNode getTreeNode(String path) {
        BaseTreeNode node = nodeMap.get(path);
        if (node == null) {

        }

        return node;
    }

    public List<ITreeNode> getChildren(String path) throws IOException {
        BaseTreeNode parent = getTreeNode(path);
        if (parent == null) {
            return new ArrayList<ITreeNode>();
        }
        /*if (parent.getChildren().size() > 0) {
            return parent.getChildren();
        }*/

        List<ITreeNode> result = new ArrayList<ITreeNode>();

        if (connect()) {
            FTPFile[] files = client.listFiles(path);
            for (FTPFile file : files) {
                String name = path + "/" + file.getName();
                if ("/".equals(path)) {
                    name = "/" + file.getName();
                }

                FtpResourceItem node = new FtpResourceItem();
                node.setId(name);
                node.setName(file.getName());
                node.setDisplayName(node.getName());
                node.setLastModified(file.getTimestamp().getTime());
                node.setSize(file.getSize());
                node.setType(file.isDirectory() ? "目录" : "文件");
                nodeMap.put(name, node);
                parent.getChildren().add(node);
                result.add(node);
            }
        }

        return result;
    }

    public void write(String path, OutputStream os) throws IOException {
        if (connect()) {
            client.retrieveFile(path, os);
        }
    }

    public void save(String path, InputStream is) throws IOException {
        if(connect()) {
            client.storeFile(path, is);
        }
    }

    public void delete(String path) throws IOException {
        if(connect()) {
            FtpResourceItem item = nodeMap.get(path);

            if ("目录".equals(item.getType())) {
                deleteAll(path);
                client.removeDirectory(path);
            } else {
                client.deleteFile(path);
            }
        }
    }

    /**
     * 删除此目录下的所有文件
     *
     * @param path 目录路径
     * @throws IOException
     */
    public void deleteAll(String path) throws IOException {
        FTPFile[] files = client.listFiles(path);
        for (FTPFile f : files) {
            if (f.isDirectory()) {
                this.deleteAll(path + "/" + f.getName());
                client.removeDirectory(path + "/" + f.getName());
            }
            if (f.isFile()) {
                client.deleteFile(path + "/" + f.getName());
            }
        }
    }

    @Override
    public void registerObserver(Observer observer) {

    }

    @Override
    public void removeObserver(Observer observer) {

    }

    @Override
    public void notifyObserver() {

    }
}
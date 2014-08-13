package com.meteorite.core.datasource.ftp;

import com.meteorite.core.ITree;
import com.meteorite.core.loader.ILoader;
import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.model.impl.BaseNavTreeNode;
import com.meteorite.core.model.impl.BaseTreeNode;
import com.meteorite.core.util.UString;
import org.apache.commons.net.ftp.*;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.*;

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
        client.setControlEncoding("GBK");
        try {
            client.setFileType(FTP.BINARY_FILE_TYPE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        FTPClientConfig config = new FTPClientConfig();
        config.setServerTimeZoneId("zh-CN");
        client.configure(config);

        nodeMap = new HashMap<>();
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
        if(client.isConnected()) {
            try {
                String status = client.getStatus();
                System.out.println(status);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
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
            client.setFileType(FTP.ASCII_FILE_TYPE);
            client.enterLocalPassiveMode();
        } catch (FTPConnectionClosedException e) {
            log.error("Server closed connection.", e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return true;
    }

    public BaseNavTreeNode getNavTree() {
        return navTree;
    }

    public BaseNavTreeNode getTreeNode(String path) {
        BaseNavTreeNode node = nodeMap.get(path);
        if (node == null) {

        }

        return node;
    }

    public List<ITreeNode> getChildren(String path) throws IOException {
        BaseNavTreeNode parent = getTreeNode(path);
        if (parent == null) {
            return new ArrayList<>();
        }
        /*if (parent.getChildren().size() > 0) {
            return parent.getChildren();
        }*/

        List<ITreeNode> result = new ArrayList<>();

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
            client.setControlEncoding("UTF-8");
            client.storeFile(path, is);
        }
    }

    public void delete(String path) throws IOException {
        if(connect()) {
            boolean isSuccess = client.deleteFile(path);
            System.out.println(isSuccess);
        }
        client.disconnect();
    }
}

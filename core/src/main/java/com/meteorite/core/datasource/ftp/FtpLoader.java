package com.meteorite.core.datasource.ftp;

import com.meteorite.core.datasource.ResourceItem;
import com.meteorite.core.loader.ILoader;
import com.meteorite.core.model.impl.BaseNavTreeNode;
import com.meteorite.core.util.UString;
import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
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
    private BaseNavTreeNode navTree;
    private Map<String, BaseNavTreeNode> nodeMap;

    public FtpLoader(String ip, String user, String password) {
        this.ip = ip;
        this.user = user;
        this.password = password;

        client = new FTPClient();
//        client.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out), true));
        client.setControlEncoding("GBK");
    }

    @Override
    public void load() throws Exception {
        navTree = new BaseNavTreeNode();
        navTree.setId("/");
        navTree.setName("Root");
        navTree.setDisplayName("Root");
        nodeMap = new HashMap<>();

        if (connect()) {
            iterator("/he_gy");

            // 第二次循环，设置父节点
            for (Map.Entry<String, BaseNavTreeNode> entry : nodeMap.entrySet()) {
                String name = entry.getKey();
                BaseNavTreeNode node = entry.getValue();
                if (name.endsWith("/")) {
                    name = name.substring(0, name.length() - 1);
                }

                BaseNavTreeNode parent = null;
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
        }

        client.disconnect();
    }

    private void iterator(String parent) throws IOException {
        BaseNavTreeNode node = new BaseNavTreeNode();
        node.setId(parent);
        node.setName(UString.getLastName(parent, "/"));
        node.setDisplayName(node.getName());
        nodeMap.put(parent, node);

        FTPFile[] files = client.listFiles(parent);
        for (FTPFile file : files) {
            String name = parent + "/" + file.getName();
//            System.out.println(name);
            if (file.isDirectory()) {
                iterator(name);
            } else {
                node = new BaseNavTreeNode();
                node.setId(name);
                node.setName(file.getName());
                node.setDisplayName(node.getName());
                nodeMap.put(name, node);
            }
        }
    }

    public boolean connect() {
        if(client.isConnected()) {
            return true;
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
        return nodeMap.get(path);
    }

    public void write(String path, OutputStream os) throws IOException {
        if (connect()) {
            client.retrieveFile(path, os);
        }
    }
}

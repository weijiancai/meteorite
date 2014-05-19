package com.meteorite.core.util;

import org.hsqldb.Server;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wei_jc
 * @version 1.0
 */
public class HSqlDBServer {
    private static HSqlDBServer instance;

    private Server server;
    private Map<String, String> filePathMap = new HashMap<>();

    private HSqlDBServer() {
        server = new Server();
        server.setTrace(true);
    }

    public static HSqlDBServer getInstance() {
        if (instance == null) {
            instance = new HSqlDBServer();
        }

        return instance;
    }

    public void addDbFile(String dbName, String dbFilePath) {
        filePathMap.put(dbName, dbFilePath);
    }

    /**
     * 启动数据库
     */
    public void start() {
        Server.main(new String[]{
                "-help"
        });

        int i = 0;
        for (Map.Entry<String, String> entry : filePathMap.entrySet()) {
            System.out.println(String.format("启动数据库【%s】 : %s", entry.getKey(), entry.getValue()));

            server.setDatabaseName(i, entry.getKey());
            server.setDatabasePath(i, entry.getValue());

            i++;
        }

        server.start();
    }

    /**
     * 停止数据库
     */
    public void stop() {
        server.stop();
    }
}

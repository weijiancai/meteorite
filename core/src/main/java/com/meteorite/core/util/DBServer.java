package com.meteorite.core.util;

import org.hsqldb.Server;

/**
 * @author wei_jc
 * @version 1.0
 */
public class DBServer {
    public static void main(String[] args) {
        Server.main(new String[]{
                "-help"
        });

        Server.main(new String[]{
                "-database.0", "C:/Users/wei_jc/.taobao/sqldb/taobao",
                "-dbname.0", "taobao",
                "-trace", "true"
        });

    }
}

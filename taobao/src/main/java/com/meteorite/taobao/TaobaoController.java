package com.meteorite.taobao;

import com.meteorite.core.util.UtilFile;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 *
 * @author wei_jc
 * @version 1.0
 */
public class TaobaoController implements Initializable {
    @FXML
    private TreeView<String> catTree;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private File dbFile;

    /**
     * 初始化内存数据库
     * 目录结构
     * .taobao
     *     | -- sqldb
     *            | -- taobao
     */
    private void initDB() throws IOException {
        // 获得用户主目录
        String userHomeStr = System.getProperty("user.home");
        File userHome = new File(userHomeStr);
        File tbDir = new File(userHome, ".taobao");
        File dbDir = new File(tbDir, "sqldb");
        if (!dbDir.exists()) {
            dbDir.mkdirs();
        }
        dbFile = new File(dbDir, "taobao");
        if (!dbFile.exists()) {
            dbFile.createNewFile();
        }
    }

    public Connection getConn() throws Exception {
//        String url = "jdbc:hsqldb:file:" + dbFile.getAbsolutePath();
        String url = "jdbc:hsqldb:hsql://localhost/taobao";
        System.out.println(url);
        String userName = "sa";
        String password = "";
        String driverClass = "org.hsqldb.jdbcDriver";
        Class.forName(driverClass);
        return DriverManager.getConnection(url, userName, password);
    }

    public void createCategoryTable() throws Exception {
        // 读取初始化脚本
        File initSqlFile = new File(getClass().getResource("/dbscript/init.sql").toURI());
        String[] sqls = UtilFile.readString(initSqlFile).split(";");
        Connection conn = getConn();
        for (String sql : sqls) {
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        }
    }

    public static void main(String[] args) {
        try {
            TaobaoController controller = new TaobaoController();
            controller.initDB();
            controller.createCategoryTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

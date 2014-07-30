package com.meteorite.fxbase;

import com.meteorite.core.datasource.DataSourceManager;
import com.meteorite.core.datasource.classpath.ClassPathDataSource;
import com.meteorite.core.datasource.db.DBDataSource;
import com.meteorite.core.datasource.db.DatabaseType;
import com.meteorite.core.datasource.ftp.FtpDataSource;
import com.meteorite.core.util.UFile;
import com.meteorite.fxbase.ui.view.MUTree;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;
import javafx.stage.Stage;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class Main extends Application {
    @Override public void start(Stage primaryStage) {
        /*DBDataSource ds = new DBDataSource();
        ds.setName("mysql");
        ds.setDatabaseType(DatabaseType.MYSQL);
        ds.setDriverClass("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/");
        ds.setUsername("root");
        ds.setPassword("7758521");*/

//        ClassPathDataSource ds = ClassPathDataSource.getInstance();
        try {
            FtpDataSource ds = new FtpDataSource("115.29.163.55", "wei_jc", "wjcectong2013#");
            ds.load();
            primaryStage.setScene(new Scene(new MUTree(ds.getNavTree())));
        } catch (Exception e) {
            e.printStackTrace();
        }
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}

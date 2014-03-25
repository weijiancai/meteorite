package com.ectongs.dsconfig;

import cc.csdn.base.db.dataobj.dsconfig.DataStoreConfig;
import cc.csdn.base.util.UtilBase64;
import cc.csdn.base.util.UtilObject;
import com.ectongs.http.HttpAccepter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class TestApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // 全屏显示
        /*Rectangle2D primaryScreenBonds = Screen.getPrimary().getVisualBounds();
        stage.setX(primaryScreenBonds.getMinX());
        stage.setY(primaryScreenBonds.getMinY());
        stage.setWidth(primaryScreenBonds.getWidth());
        stage.setHeight(primaryScreenBonds.getHeight());*/

        BaseHttpService service = new BaseHttpService("flex/DataStoreConfigAction");
        service.addParameter("dsClassName", "Product");
        service.addParameter("isConfigFromDs", "false");
        service.addParameter("isReturnObj", "true");
        HttpAccepter accepter = service.send("66540F58102FE173C81E621907A94BF8");
        String data = accepter.getData();
        DataStoreConfig dsConfig = (DataStoreConfig) UtilObject.byteToObject(UtilBase64.decode(data));


        Parent properties = FXMLLoader.load(getClass().getResource("Properties.fxml"));
        stage.setTitle("Hello World");

        FxBaseGrid grid = new FxBaseGrid(dsConfig);
        grid.addItems(3);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(grid);

        BorderPane root = new BorderPane();
        root.setCenter(scrollPane);
        root.setRight(properties);
        stage.setScene(new Scene(root, 1524, 800));
        stage.show();
    }

    public static void main(String[] args) {
        launch(TestApp.class);
    }
}

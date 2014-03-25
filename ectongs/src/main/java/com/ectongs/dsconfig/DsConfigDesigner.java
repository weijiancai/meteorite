package com.ectongs.dsconfig;

import cc.csdn.base.db.dataobj.dsconfig.DataStoreConfig;
import cc.csdn.base.util.UtilBase64;
import cc.csdn.base.util.UtilObject;
import com.ectongs.http.HttpAccepter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author wei_jc
 * @since 1.00
 */
public class DsConfigDesigner extends Application {

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
        HttpAccepter accepter = service.send("A64F3E83DF79566103A66A14C27EA64D");
        String data = accepter.getData();
        DataStoreConfig dsConfig = (DataStoreConfig) UtilObject.byteToObject(UtilBase64.decode(data));


        FXMLLoader loader = new FXMLLoader(getClass().getResource("Properties.fxml"));
        loader.load();
        VBox propertiesPane = loader.getRoot();
        Properties properties = loader.getController();
//        VBox propertiesPane = FXMLLoader.load(getClass().getResource("Properties.fxml"));
        stage.setTitle("Hello World");

        FxBaseGrid grid = new FxBaseGrid(dsConfig);
        grid.addItems(3);
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPadding(new Insets(10, 10, 10, 10));
        scrollPane.setContent(grid);

        BorderPane root = new BorderPane();
        root.setCenter(scrollPane);
        root.setRight(propertiesPane);
        stage.setScene(new Scene(root, 1524, 800));
        stage.show();
    }

    public static void main(String[] args) {
        launch(DsConfigDesigner.class);
    }
}

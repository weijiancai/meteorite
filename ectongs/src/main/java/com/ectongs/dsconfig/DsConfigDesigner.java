package com.ectongs.dsconfig;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * @author wei_jc
 * @since 1.00
 */
public class DsConfigDesigner extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(new Label("Test")));
        stage.show();
    }

    public static void main(String[] args) {
        launch(DsConfigDesigner.class);
    }
}

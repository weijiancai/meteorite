package com.meteorite.fxbase;

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
        SplitPane splitPane = new SplitPane();
        splitPane.setId("hiddenSplitter");
        Region region1 = new Region();
        region1.getStyleClass().add("rounded");
        Region region2 = new Region();
        region2.getStyleClass().add("rounded");
        Region region3 = new Region();
        region3.getStyleClass().add("rounded");
        splitPane.getItems().addAll(region1, region2, region3);
        splitPane.setDividerPositions(0.33, 0.66);

        primaryStage.setScene(new Scene(splitPane));
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}

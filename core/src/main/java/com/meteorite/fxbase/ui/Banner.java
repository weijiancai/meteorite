package com.meteorite.fxbase.ui;

import com.meteorite.fxbase.BaseApp;
import javafx.event.EventHandler;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * @author weijiancai
 */
public class Banner extends ToolBar {
    private double mouseDragOffsetX = 0;
    private double mouseDragOffsetY = 0;

    public Banner(final Stage stage) {
        this.setId("banner");
        if (!BaseApp.IS_APPLET) {
            // add close min max
            final WindowButtons windowButtons = new WindowButtons(stage);
            Region region = new Region();
            HBox.setHgrow(region, Priority.ALWAYS);
            this.getItems().addAll(region, windowButtons);
            // add window header double clicking
            this.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount() == 2) {
                        windowButtons.toogleMaximized();
                    }
                }
            });
            // add window dragging
            this.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    mouseDragOffsetX = event.getSceneX();
                    mouseDragOffsetY = event.getSceneY();
                }
            });
            this.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (!windowButtons.isMaximized()) {
                        stage.setX(event.getScreenX() - mouseDragOffsetX);
                        stage.setY(event.getScreenY() - mouseDragOffsetY);
                    }
                }
            });
        }
    }
}

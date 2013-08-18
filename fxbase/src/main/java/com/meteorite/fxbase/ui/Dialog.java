package com.meteorite.fxbase.ui;

import com.meteorite.fxbase.BaseApp;
import javafx.event.EventHandler;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

/**
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class Dialog extends BorderPane {
    private FxDesktop desktop;
    private boolean isModal;
    private Pane modalPane;

    public Dialog(FxDesktop desktop, String color, boolean modal) {
        this.desktop = desktop;
        this.isModal = modal;

        this.setStyle("-fx-background-color:" + color);
        this.setManaged(false);
        this.setPrefSize(300, 200);

        if (isModal) {
            modalPane = new Pane();
            modalPane.setStyle("-fx-background-color:#cccccc");
        }
    }

    public Dialog(boolean modal) {
        this.isModal = modal;

        this.setStyle("-fx-background-color:#ff0000");
        this.setPrefSize(300, 200);
        this.setManaged(false);

        if (isModal) {
            modalPane = new Pane();
            modalPane.setStyle("-fx-background-color:#cccccc");
        }

        TopBar topBar = new TopBar(this);
        this.setTop(topBar);
    }

    public void show() {
        if (isModal) {
            modalPane = new Pane();
            modalPane.setStyle("-fx-background-color:#333333");
            desktop.getChildren().add(modalPane);
        }
        desktop.getChildren().add(this);
        this.autosize();
    }

    public void close() {
        if (modalPane != null) {
            desktop.getChildren().remove(modalPane);
        }
        desktop.getChildren().remove(this);
    }

    public boolean isModal() {
        return isModal;
    }

    public void setModal(boolean modal) {
        isModal = modal;
    }

    public Pane getModalPane() {
        return modalPane;
    }

    public void setModalPane(Pane modalPane) {
        this.modalPane = modalPane;
    }

    public FxDesktop getDesktop() {
        return desktop;
    }

    public void setDesktop(FxDesktop desktop) {
        this.desktop = desktop;
    }

    class TopBar extends ToolBar {
        private double mouseDragOffsetX = 0;
        private double mouseDragOffsetY = 0;

        public TopBar(final Dialog dialog) {
            this.setId("banner");

            if (!BaseApp.IS_APPLET) {
                // add close min max
                final WindowButtons windowButtons = new WindowButtons(dialog);
                Region region = new Region();
                HBox.setHgrow(region, Priority.ALWAYS);
                this.getItems().addAll(region, windowButtons);
                // add window dragging
                this.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent event) {
                        mouseDragOffsetX = event.getSceneX() - dialog.getLayoutX();
                        mouseDragOffsetY = event.getSceneY() - dialog.getLayoutY();
                    }
                });
                this.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent event) {
                        if(!windowButtons.isMaximized()) {
                            dialog.layoutXProperty().unbind();
                            dialog.layoutYProperty().unbind();
                            dialog.setLayoutX(event.getSceneX() - mouseDragOffsetX);
                            dialog.setLayoutY(event.getSceneY() - mouseDragOffsetY);
                        }
                    }
                });
            }
        }
    }
}

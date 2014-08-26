package com.ectongs.dsconfig;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class DragTest extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Pane pane = new Pane();
        final Text source = new Text(50, 100, "Drag Me");
        final Text target = new Text(300, 100, "Drop Here");
        pane.getChildren().addAll(source, target);

        source.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Dragboard dragboard = source.startDragAndDrop(TransferMode.ANY);

                ClipboardContent content = new ClipboardContent();
                content.putString(source.getText());
                dragboard.setContent(content);

                mouseEvent.consume();
            }
        });

        target.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                if (dragEvent.getGestureSource() != target && dragEvent.getDragboard().hasString()) {
                    dragEvent.acceptTransferModes(TransferMode.MOVE);
                }

                dragEvent.consume();
            }
        });
        target.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                if (dragEvent.getGestureSource() != target && dragEvent.getDragboard().hasString()) {
                    target.setFill(Color.GREEN);
                }

                dragEvent.consume();
            }
        });
        target.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                target.setFill(Color.BLACK);
            }
        });
        target.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                Dragboard dragboard = dragEvent.getDragboard();
                boolean success = false;
                if (dragboard.hasString()) {
//                    target.setText(dragboard.getString());
                    success = true;
                }

                double sourceX = source.getTranslateX();
                double sourceY = source.getLayoutY();
                double targetX = target.getTranslateX();
                double targetY = target.getLayoutY();

                source.setTranslateX(targetX + 10);
                source.setTranslateY(targetY + 10);
                target.setTranslateX(sourceX);
                target.setTranslateY(sourceY);

                System.out.println(String.format("SourceX = %s, SourceY = %s, TargetX = %s, TargetY = %s", sourceX, sourceY, targetX, targetY));

                dragEvent.setDropCompleted(success);
                dragEvent.consume();
            }
        });
        source.setOnDragDone(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                if (dragEvent.getTransferMode() == TransferMode.MOVE) {
//                    source.setText("a");
                }
                dragEvent.consume();
            }
        });

        /*source.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                source.setTranslateX(source.getTranslateX() + 10);
                source.setTranslateY(source.getTranslateY() + 10);
            }
        });*/

        Scene scene = new Scene(pane, 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(DragTest.class);
    }
}

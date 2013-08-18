package com.meteorite.fxbase.ui.dialog;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class ExceptionDialog extends FXDialog {
    public ExceptionDialog(Stage parent, Throwable throwable) {
        super(DialogResources.getMessage("exception.dialog.title"));

        initModality(Modality.APPLICATION_MODAL);

        // --- initComponents
        VBox contentPanel = new VBox();
        contentPanel.getStyleClass().add("more-info-dialog");

        contentPanel.setPrefSize(800, 600);

        if (throwable != null) {
            BorderPane labelPanel = new BorderPane();

            Label label = new Label(DialogResources.getString("exception.dialog.label"));
            labelPanel.setLeft(label);

            contentPanel.getChildren().add(labelPanel);

            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            throwable.printStackTrace(pw);
            TextArea text = new TextArea(sw.toString());
            text.setEditable(false);
            text.setWrapText(true);
            text.setPrefWidth(60 * 8);
            text.setPrefHeight(20 * 12);

            VBox.setVgrow(text, Priority.ALWAYS);
            contentPanel.getChildren().add(text);
        }

        // --- getBtnPanel
        // This panel contains right-aligned "Close" button.  It should
        // dismiss the dialog and dispose of it.
        HBox btnPanel = new HBox();
        btnPanel.getStyleClass().add("button-panel");

        Button dismissBtn = new Button(DialogResources.getMessage("common.close.btn"));
        dismissBtn.setPrefWidth(80);
        dismissBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                hide();
            }
        });

        dismissBtn.setDefaultButton(true);
        btnPanel.getChildren().add(dismissBtn);
        contentPanel.getChildren().add(btnPanel);
        // --- getBtnPanel

        setContentPane(contentPanel);
        // --- initComponents
    }
}

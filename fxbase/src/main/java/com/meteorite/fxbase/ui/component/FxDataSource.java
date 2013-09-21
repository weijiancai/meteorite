package com.meteorite.fxbase.ui.component;

import com.meteorite.core.db.DataSource;
import com.meteorite.fxbase.ui.IValue;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class FxDataSource extends HBox implements IValue{
    private TextField textField;
    private Button settingButton;
    private Button testButton;
    private Hyperlink settingHyperlink;
    private Hyperlink testHyperlink;

    private DataSource dataSource;

    public FxDataSource() {
        textField = new TextField();
        textField.setEditable(false);
        settingButton = new Button("设置");
        testButton = new Button("测试");
        testButton.setOnAction(new SettingAction());

        settingHyperlink = new Hyperlink("设置");
        testHyperlink = new Hyperlink("测试");

//        this.getChildren().addAll(textField, settingButton, testButton);
        this.getChildren().addAll(textField, settingHyperlink, testHyperlink);

        this.prefWidthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldNum, Number newNum) {
                textField.setPrefWidth(newNum.doubleValue() - 80);
            }
        });
    }

    @Override
    public String[] values() {
        return new String[0];
    }

    @Override
    public String value() {
        return null;
    }

    @Override
    public void setValue(String[] value) {
    }

    @Override
    public void setValue(String value) {
    }

    public class SettingAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {

        }
    }

    public class TestAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {

        }
    }
}

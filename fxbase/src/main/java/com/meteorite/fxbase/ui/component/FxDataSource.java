package com.meteorite.fxbase.ui.component;

import com.meteorite.core.datasource.db.DBDataSource;
import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.meta.MetaManager;
import com.meteorite.core.ui.IView;
import com.meteorite.core.ui.config.ViewConfigFactory;
import com.meteorite.fxbase.BaseApp;
import com.meteorite.fxbase.ui.config.FxFormFieldConfig;
import com.meteorite.fxbase.ui.event.FxLayoutEvent;
import com.meteorite.fxbase.ui.view.FxFormField;
import com.meteorite.fxbase.ui.view.FxPane;
import com.meteorite.fxbase.ui.view.FxViewFactory;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class FxDataSource extends FxFormField {
    private HBox hbox;
    private TextField textField;
    private Button settingButton;
    private Button testButton;
    private Hyperlink settingHyperlink;
    private Hyperlink testHyperlink;

    private DBDataSource dataSource;

    public FxDataSource(FxFormFieldConfig fieldConfig) {
        super(fieldConfig);
        hbox = new HBox();
        textField = new TextField();
        textField.setEditable(false);
        settingButton = new Button("设置");
        testButton = new Button("测试");
        testButton.setOnAction(new SettingAction());

        settingHyperlink = new Hyperlink("设置");
        settingHyperlink.setOnAction(new SettingAction());
        testHyperlink = new Hyperlink("测试");
        testHyperlink.setOnAction(new TestAction());

//        this.getChildren().addAll(textField, settingButton, testButton);
        hbox.getChildren().addAll(textField, settingHyperlink, testHyperlink);
        textField.prefWidthProperty().bind(hbox.prefWidthProperty().subtract(60));
        HBox.setHgrow(textField, Priority.ALWAYS);

        /*if (isDesign) {
            textField.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    textField.fireEvent(new FxLayoutEvent(layoutConfig));
                }
            });
        }*/
        if(fieldConfig.getFormConfig().getColCount() == 1) {
            hbox.setMaxWidth(fieldConfig.getFormConfig().getColWidth());
        }
    }

    @Override
    public void setDisplayStyle(DisplayStyle displayStyle) {
    }

    @Override
    public Node getNode() {
        return hbox;
    }

    @Override
    public void setValue(String... values) {
        textField.setText(values[0]);
    }

    @Override
    public String getValue() {
        return textField.getText().trim();
    }

    @Override
    public String[] getValues() {
        return new String[]{getValue()};
    }

    @Override
    public StringProperty textProperty() {
        return textField.textProperty();
    }

    @Override
    public DoubleProperty widthProperty() {
        return hbox.prefWidthProperty();
    }

    @Override
    public DoubleProperty heightProperty() {
        return hbox.prefHeightProperty();
    }

    @Override
    public void registLayoutEvent() {
        textField.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                textField.fireEvent(new FxLayoutEvent(fieldConfig.getLayoutConfig(), FxDataSource.this));
            }
        });
    }


    public class SettingAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            IView<FxPane> view = FxViewFactory.getView(ViewConfigFactory.createFormConfig(MetaManager.getMeta(DBDataSource.class)));
            MUDialog.showCustomDialog(BaseApp.getInstance().getStage(), "数据源配置", view.layout(), null);
        }
    }

    public class TestAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {

        }
    }
}

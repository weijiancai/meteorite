package com.meteorite.fxbase.ui.component;

import com.meteorite.core.db.DataSource;
import com.meteorite.core.meta.MetaManager;
import com.meteorite.core.ui.ILayoutConfig;
import com.meteorite.core.ui.IView;
import com.meteorite.core.ui.IViewConfig;
import com.meteorite.core.ui.config.ViewConfigFactory;
import com.meteorite.fxbase.BaseApp;
import com.meteorite.fxbase.ui.Dialogs;
import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.dialog.DialogOptions;
import com.meteorite.fxbase.ui.event.FxLayoutEvent;
import com.meteorite.fxbase.ui.view.FxFormView;
import com.meteorite.fxbase.ui.view.FxViewFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class FxDataSource extends HBox implements IValue {
    private TextField textField;
    private Button settingButton;
    private Button testButton;
    private Hyperlink settingHyperlink;
    private Hyperlink testHyperlink;

    private DataSource dataSource;

    public FxDataSource(final ILayoutConfig layoutConfig, boolean isDesign) {
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
        this.getChildren().addAll(textField, settingHyperlink, testHyperlink);
        textField.prefWidthProperty().bind(this.prefWidthProperty().subtract(60));
        HBox.setHgrow(textField, Priority.ALWAYS);

        if (isDesign) {
            textField.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    textField.fireEvent(new FxLayoutEvent(layoutConfig));
                }
            });
        }
    }

    @Override
    public String[] values() {
        return new String[0];
    }

    @Override
    public String value() {
        return textField.getText();
    }

    @Override
    public void setValue(String[] value) {
    }

    @Override
    public void setValue(String value) {
        textField.setText(value);
    }

    public class SettingAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {
            IView<Pane> view = FxViewFactory.getView(ViewConfigFactory.createFormConfig(MetaManager.getMeta(DataSource.class)));
            Dialogs.showCustomDialog(BaseApp.getInstance().getStage(),view.layout(), null, "数据源配置", DialogOptions.OK, null);
        }
    }

    public class TestAction implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent actionEvent) {

        }
    }
}

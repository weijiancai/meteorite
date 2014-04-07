package com.meteorite.fxbase.ui.component.form;

import com.meteorite.core.datasource.db.DBDataSource;
import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.meta.MetaManager;
import com.meteorite.core.ui.IView;
import com.meteorite.core.ui.config.ViewConfigFactory;
import com.meteorite.core.ui.layout.property.FormFieldProperty;
import com.meteorite.fxbase.BaseApp;
import com.meteorite.fxbase.ui.config.FxFormFieldConfig;
import com.meteorite.fxbase.ui.event.FxLayoutEvent;
import com.meteorite.fxbase.ui.view.FxFormField;
import com.meteorite.fxbase.ui.view.FxPane;
import com.meteorite.fxbase.ui.view.FxViewFactory;
import com.meteorite.fxbase.ui.view.MUDialog;
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
 * MetaUI 数据源控件
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MuDataSource extends HBox {
    private TextField textField;
    private Button settingButton;
    private Button testButton;
    private Hyperlink settingHyperlink;
    private Hyperlink testHyperlink;

    private DBDataSource dataSource;

    public MuDataSource(FormFieldProperty fieldConfig) {
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
        setHgrow(textField, Priority.ALWAYS);

        /*if (isDesign) {
            textField.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    textField.fireEvent(new FxLayoutEvent(layoutConfig));
                }
            });
        }*/
        /*if(fieldConfig.getFormConfig().getColCount() == 1) {
            this.setMaxWidth(fieldConfig.getFormConfig().getColWidth());
        }*/
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

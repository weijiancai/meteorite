package com.meteorite.fxbase.ui.component;

import com.meteorite.core.dict.DictCategory;
import com.meteorite.core.dict.DictCode;
import com.meteorite.core.ui.config.layout.FormFieldConfig;
import com.meteorite.fxbase.BaseApp;
import com.meteorite.fxbase.ui.Dialogs;
import com.meteorite.fxbase.ui.dialog.DialogOptions;
import com.meteorite.fxbase.ui.event.FxLayoutEvent;
import com.meteorite.fxbase.ui.view.FxFormField;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

import java.util.List;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class FxComboBox extends FxFormField {
    private DictCategory category;
    private ComboBox<DictCode> comboBox;

    public FxComboBox(FormFieldConfig fieldConfig) {
        super(fieldConfig);

        comboBox = new ComboBox<>();

        this.category = fieldConfig.getDict();
        comboBox.setEditable(true);
        if (category != null) {
            List<DictCode> list = category.getCodeList();
            comboBox.setItems(FXCollections.observableArrayList(list));
            if (list.size() > 0) {
                comboBox.setPromptText(list.get(0).getValue());
            }
        }

        // 查看数据字典上下文菜单
        MenuItem lookDictMenu = new MenuItem("查看数据字典");
        lookDictMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Dialogs.showCustomDialog(BaseApp.getInstance().getStage(), new FxLookDictPane(category), null, category.getName(), DialogOptions.OK, null);
            }
        });
        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getItems().add(lookDictMenu);
        comboBox.setContextMenu(contextMenu);

        comboBox.setPrefWidth(fieldConfig.getWidth());
        this.setValue(fieldConfig.getValue());
    }


    @Override
    public Node getNode() {
        return comboBox;
    }

    @Override
    public void setValue(String... values) {
        if (category != null) {
            for (DictCode code : comboBox.getItems()) {
                if (code.getName().equalsIgnoreCase(values[0])) {
                    comboBox.getSelectionModel().select(code);
                }
            }
        }
    }

    @Override
    public String getValue() {
        DictCode code = comboBox.getValue();
        return code == null ? "" : code.getValue();
    }

    @Override
    public String[] getValues() {
        return new String[]{getValue()};
    }

    @Override
    public StringProperty textProperty() {
        return comboBox.idProperty();
    }

    @Override
    public void registLayoutEvent() {
        comboBox.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    comboBox.fireEvent(new FxLayoutEvent<>(fieldConfig.getLayoutConfig(), this));
                }
            }
        });
    }
}

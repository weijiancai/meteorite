package com.meteorite.fxbase.ui.component.form;

import com.meteorite.core.dict.DictCategory;
import com.meteorite.core.dict.DictCode;
import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.ui.layout.property.FormFieldProperty;
import com.meteorite.core.util.UString;
import com.meteorite.fxbase.BaseApp;
import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.component.FxLookDictPane;
import com.meteorite.fxbase.ui.config.FxFormFieldConfig;
import com.meteorite.fxbase.ui.event.FxLayoutEvent;
import com.meteorite.fxbase.ui.view.FxFormField;
import com.meteorite.fxbase.ui.view.MUDialog;
import javafx.beans.property.DoubleProperty;
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
 * MetaUI 下拉框
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MuComboBox extends ComboBox<DictCode> implements IValue {
    private DictCategory category;

    public MuComboBox(FormFieldProperty fieldConfig) {
        this.category = fieldConfig.getDict();
        this.setEditable(true);
        if (category != null) {
            List<DictCode> list = category.getCodeList();
            this.setItems(FXCollections.observableArrayList(list));
            if (list.size() > 0) {
                this.setPromptText(list.get(0).getDisplayName());
            }
        }

        // 查看数据字典上下文菜单
        MenuItem lookDictMenu = new MenuItem("查看数据字典");
        lookDictMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                MUDialog.showCustomDialog(BaseApp.getInstance().getStage(), category.getName(), new FxLookDictPane(category), null);
            }
        });
        ContextMenu contextMenu = new ContextMenu();
        contextMenu.getItems().add(lookDictMenu);
        this.setContextMenu(contextMenu);

        this.setPrefWidth(fieldConfig.getWidth());
    }

    @Override
    public String[] values() {
        return new String[0];
    }

    @Override
    public String value() {
        return getValue().getName();
    }

    @Override
    public void setValue(String[] value) {

    }

    @Override
    public void setValue(String value) {
        if (category != null) {
            if (category.getId().equals("EnumBoolean")) {
                if (UString.toBoolean(value)) {
                    this.getSelectionModel().select(category.getDictCodeByName("TRUE"));
                } else {
                    this.getSelectionModel().select(category.getDictCodeByName("FALSE"));
                }
            } else {
                for (DictCode code : this.getItems()) {
                    if (code.getName().equalsIgnoreCase(value)) {
                        this.getSelectionModel().select(code);
                    }
                }
            }
        }
    }
}

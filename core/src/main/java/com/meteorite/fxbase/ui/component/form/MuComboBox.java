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
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * MetaUI 下拉框
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MuComboBox extends BaseFormField implements IValue {
    private DictCategory category;
    private ComboBox<DictCode> comboBox;

    public MuComboBox(FormFieldProperty fieldConfig) {
        super(fieldConfig);
        this.isAddQueryMode = false;
        init();
    }

    @Override
    protected void initPrep() {
        this.category = config.getDict();

        comboBox = new ComboBox<DictCode>();
        comboBox.setEditable(true);
        comboBox.setConverter(new DictCodeConverter());
        comboBox.setCellFactory(new Callback<ListView<DictCode>, ListCell<DictCode>>() {
            @Override
            public ListCell<DictCode> call(ListView<DictCode> param) {
                final ListCell<DictCode> cell = new ListCell<DictCode>() {

                    @Override public void updateItem(DictCode item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getDisplayName());
                        }
                        else {
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });
        if (category != null) {
            List<DictCode> list = category.getCodeList();
            comboBox.setItems(FXCollections.observableArrayList(list));
            if (list.size() > 0) {
                comboBox.setPromptText(list.get(0).getDisplayName());
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
        comboBox.setContextMenu(contextMenu);

        comboBox.prefWidthProperty().bind(this.widthProperty());
        comboBox.valueProperty().addListener(new ChangeListener<DictCode>() {
            @Override
            public void changed(ObservableValue<? extends DictCode> observable, DictCode oldValue, DictCode newValue) {
                if (newValue != null) {
                    valueProperty().set(newValue.getName());
                }
            }
        });
    }

    @Override
    protected Node[] getControls() {
        return new Node[]{comboBox};
    }

    @Override
    public String value() {
        if (comboBox.getValue() == null) {
            return null;
        }
        return comboBox.getValue().getName();
    }

    @Override
    public void setValue(String value) {
        super.setValue(value);
        if (category != null) {
            if (category.getId().equals("EnumBoolean")) {
                if (UString.toBoolean(value)) {
                    comboBox.getSelectionModel().select(category.getDictCodeByName("T"));
                } else {
                    comboBox.getSelectionModel().select(category.getDictCodeByName("F"));
                }
            } else {
                for (DictCode code : comboBox.getItems()) {
                    if (code.getName().equalsIgnoreCase(value)) {
                        comboBox.getSelectionModel().select(code);
                    }
                }
            }
        }
        if (value == null) {
            comboBox.getSelectionModel().clearSelection();
        }
    }

    class DictCodeConverter extends StringConverter<DictCode> {

        @Override
        public String toString(DictCode code) {
            if (code == null) {
                return null;
            }
            return code.getDisplayName();
        }

        @Override
        public DictCode fromString(String string) {
            if (UString.isNotEmpty(string)) {
                for (DictCode code : category.getCodeList()) {
                    if (code.getDisplayName().equals(string)) {
                        return code;
                    }
                }
            }
            return null;
        }
    }
}

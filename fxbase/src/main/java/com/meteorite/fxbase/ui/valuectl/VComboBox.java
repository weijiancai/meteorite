package com.meteorite.fxbase.ui.valuectl;

import com.meteorite.core.dict.DictCategory;
import com.meteorite.core.dict.DictCode;
import com.meteorite.core.ui.ILayoutConfig;
import com.meteorite.fxbase.BaseApp;
import com.meteorite.fxbase.ui.Dialogs;
import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.component.FxLookDictPane;
import com.meteorite.fxbase.ui.dialog.DialogOptions;
import com.meteorite.fxbase.ui.event.FxLayoutEvent;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;

import java.util.List;

/**
 * JavaFx下拉框控件
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class VComboBox extends ComboBox<DictCode> implements IValue {
    private DictCategory category;

    public VComboBox(final DictCategory category, final ILayoutConfig layoutConfig, boolean isDesign) {
        super();
        this.category = category;
        this.setEditable(true);
        if (category != null) {
            List<DictCode> list = category.getCodeList();
            this.setItems(FXCollections.observableArrayList(list));
            if (list.size() > 0) {
                this.setPromptText(list.get(0).getValue());
            }
        }

        if (isDesign) {
            this.focusedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (newValue) {
                        VComboBox.this.fireEvent(new FxLayoutEvent(layoutConfig));
                    }
                }
            });
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
        this.setContextMenu(contextMenu);
    }

    @Override
    public String[] values() {
        return new String[]{value()};
    }

    @Override
    public String value() {
        DictCode code = this.getValue();
        return code == null ? "" : code.getValue();
    }

    @Override
    public void setValue(String[] value) {
    }

    @Override
    public void setValue(String value) {
        if (category != null) {
            for (DictCode code : this.getItems()) {
                if (code.getName().equalsIgnoreCase(value)) {
                    this.getSelectionModel().select(code);
                }
            }
        }
    }
}

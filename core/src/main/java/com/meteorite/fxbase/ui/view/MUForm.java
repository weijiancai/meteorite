package com.meteorite.fxbase.ui.view;

import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.datasource.QueryBuilder;
import com.meteorite.core.datasource.db.QueryResult;
import com.meteorite.core.dict.EnumDataStatus;
import com.meteorite.core.dict.FormType;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.observer.BaseSubject;
import com.meteorite.core.observer.Subject;
import com.meteorite.core.ui.ViewManager;
import com.meteorite.core.ui.layout.property.FormProperty;
import com.meteorite.core.util.UDate;
import com.meteorite.core.util.UUIDUtil;
import com.meteorite.fxbase.BaseApp;
import com.meteorite.fxbase.MuEventHandler;
import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.component.form.BaseFormField;
import com.meteorite.fxbase.ui.component.form.ICanQuery;
import com.meteorite.fxbase.ui.design.MUFormDesigner;
import com.meteorite.fxbase.ui.event.FormFieldValueEvent;
import com.meteorite.fxbase.ui.event.data.DataStatusEventData;
import com.meteorite.fxbase.ui.layout.MUFormLayout;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Callback;

import java.sql.SQLException;
import java.util.*;

/**
 * MetaUI Form
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUForm extends BorderPane {
    private FormProperty formConfig;
    private MUFormLayout layout;
    private MUTable table;
    private DataMap data;
    private DataMap newRowData;

    private BooleanProperty isModified = new SimpleBooleanProperty();
    private Map<String, IValue> modifiedValueMap = new HashMap<String, IValue>();
    private boolean isAdd;

    private boolean onlyShowHidden;
    private boolean isDesignMode;

    private Subject<DataStatusEventData> dataStatusSubject = new BaseSubject<DataStatusEventData>();

    public MUForm() {
    }

    public MUForm(FormProperty property) {
        this(property, null);
    }

    public MUForm(FormProperty property, MUTable table) {
        this.table = table;
        initUI(property);
    }

    public void initUI(final FormProperty formConfig) {
        this.formConfig = formConfig;
        layout = new MUFormLayout();
        layout.initUI(formConfig, onlyShowHidden);
        this.setCenter(layout);

        // 监听FormField状态变化
        for (final Map.Entry<String, IValue> entry : getValueMap().entrySet()) {
            final IValue value = entry.getValue();
            value.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    if (oldValue == null || !oldValue.equals(newValue)) {
                        isModified.set(true);
                        modifiedValueMap.put(entry.getKey(), value);
                    }
                    fireEvent(new FormFieldValueEvent((BaseFormField) value, oldValue, newValue));
                }
            });
        }

        if (!isDesignMode) {
            MenuItem designMenu = new MenuItem("设计视图");
            designMenu.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    MUFormDesigner designer = new MUFormDesigner(MUForm.this);
                    MUDialog.showCustomDialog(null, "设计视图", designer, new Callback<Void, Void>() {
                        @Override
                        public Void call(Void param) {
                            initUI(formConfig);
                            return null;
                        }
                    });
                }
            });
            final ContextMenu contextMenu = new ContextMenu();
            contextMenu.getItems().addAll(designMenu);

            this.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    if (event.getButton() == MouseButton.SECONDARY) { // 非设计视图，弹出右键菜单
                        contextMenu.show(MUForm.this, event.getScreenX(), event.getScreenY());
                    } else {
                        contextMenu.hide();
                    }
                }
            });
        }
    }

    public void setValues(DataMap result) {
        this.data = result;
        for (Map.Entry<String, IValue> entry : layout.getValueMap().entrySet()) {
            if (result == null) {
                entry.getValue().setValue(null);
            } else {
                entry.getValue().setValue(result.getString(entry.getKey()));
            }
        }
        // 重置修改状态
        isModified.set(false);
        modifiedValueMap = new HashMap<String, IValue>();
    }

    public void setValue(String name, String value) {
        isAdd = false;
        IValue v = getValueMap().get(name);
        if (v != null) {
            v.setValue(value);
        }
    }

    /**
     * 新增
     */
    public void add(DataMap defaultValues) {
        isAdd = true;
        newRowData = defaultValues;
        if (defaultValues == null) {
            newRowData = new DataMap();
        }
        dataStatusSubject.notifyObserver(new DataStatusEventData(EnumDataStatus.ADD_BEFORE, this));
        for (Map.Entry<String, IValue> entry : layout.getValueMap().entrySet()) {
            String defaultValue = newRowData.getString(entry.getKey());
            if (defaultValue == null) {
                defaultValue = entry.getValue().getDefaultValue();
                if ("GUID()".equals(defaultValue)) {
                    defaultValue = UUIDUtil.getUUID();
                } else if ("SYSDATE()".equals(defaultValue)) {
                    defaultValue = UDate.dateToString(new Date(), "yyyy-MM-dd HH:ss:mm");
                }
            }

            entry.getValue().setValue(defaultValue);
        }
    }

    public void add() {
        add(null);
    }

    public List<ICanQuery> getQueryList() {
        return layout.getQueryList();
    }

    public Map<String, IValue> getValueMap() {
        return layout.getValueMap();
    }

    public IValue getValue(String colName) {
        return getValueMap().get(colName);
    }

    public void setAdd(boolean isAdd) {
        this.isAdd = isAdd;
    }

    public void save() throws Exception {
        if(isAdd) {
            Map<String, Object> map = new HashMap<String, Object>();
            for (IValue value : layout.getValueMap().values()) {
                MetaField field = value.getMetaField();
                map.put(field.getName(), value.value());
            }
            data = formConfig.getMeta().save(map);
            isAdd = false;
            dataStatusSubject.notifyObserver(new DataStatusEventData(EnumDataStatus.ADD_AFTER, this));
        } else {
            formConfig.getMeta().update(modifiedValueMap, data);
            if (table != null) {
                table.getSelectedItem().putAll(modifiedValueMap);
            }
        }
        isModified.set(false);
    }

    public FormProperty getFormConfig() {
        return formConfig;
    }

    public boolean getIsModified() {
        return isModified.get();
    }

    public BooleanProperty isModifiedProperty() {
        return isModified;
    }

    public Subject<DataStatusEventData> getDataStatusSubject() {
        return dataStatusSubject;
    }

    public void setDataStatusSubject(Subject<DataStatusEventData> dataStatusSubject) {
        this.dataStatusSubject = dataStatusSubject;
    }

    public DataMap getData() {
        return data;
    }

    public DataMap getNewRowData() {
        return newRowData;
    }

    public void reset() {
        for (Map.Entry<String, IValue> entry : layout.getValueMap().entrySet()) {
            entry.getValue().setValue("");
        }
    }

    public void setOnlyShowHidden(boolean onlyShowHidden) {
        this.onlyShowHidden = onlyShowHidden;
    }

    public void setDesignMode(boolean isDesignMode) {
        this.isDesignMode = isDesignMode;
    }
}

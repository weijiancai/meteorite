package com.meteorite.fxbase.ui.event;

import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.ui.layout.property.BaseProperty;
import javafx.event.Event;
import javafx.event.EventType;

/**
 * 数据改变事件
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class DataChangeEvent extends Event {
    public static EventType<DataChangeEvent> EVENT_TYPE = new EventType<DataChangeEvent>("DATA_CHANGE_EVENT_TYPE");
    private BaseProperty property;
    private String name;
    private String newValue;
    private DataMap rowData;

    public DataChangeEvent(BaseProperty property, String name, String newValue) {
        super(EVENT_TYPE);

        this.property = property;
        this.name = name;
        this.newValue = newValue;
    }

    public BaseProperty getProperty() {
        return property;
    }

    public String getName() {
        return name;
    }

    public String getNewValue() {
        return newValue;
    }

    public DataMap getRowData() {
        return rowData;
    }

    public void setRowData(DataMap rowData) {
        this.rowData = rowData;
    }
}

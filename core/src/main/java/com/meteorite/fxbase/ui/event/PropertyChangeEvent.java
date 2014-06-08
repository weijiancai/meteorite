package com.meteorite.fxbase.ui.event;

import javafx.event.Event;
import javafx.event.EventType;

/**
 * 属性值改变事件
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class PropertyChangeEvent extends Event {
    public static EventType<PropertyChangeEvent> EVENT_TYPE = new EventType<PropertyChangeEvent>("PROPERTY_CHANGE_EVENT_TYPE");
    private String propName;
    private String propValue;

    public PropertyChangeEvent(String propName, String propValue) {
        super(EVENT_TYPE);

        this.propName = propName;
        this.propValue = propValue;
    }

    public String getPropValue() {
        return propValue;
    }

    public String getPropName() {
        return propName;
    }
}

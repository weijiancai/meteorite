package com.meteorite.fxbase.ui.event;

import com.meteorite.core.ui.ILayoutConfig;
import javafx.event.Event;
import javafx.event.EventType;

/**
 * 布局事件
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class FxLayoutEvent extends Event {
    public static EventType<FxLayoutEvent> EVENT_TYPE = new EventType<>("LAYOUT_EVENT_TYPE");
    private ILayoutConfig layoutConfig;

    public FxLayoutEvent(ILayoutConfig layoutConfig) {
        super(EVENT_TYPE);
        this.layoutConfig = layoutConfig;
    }

    public ILayoutConfig getLayoutConfig() {
        return layoutConfig;
    }
}

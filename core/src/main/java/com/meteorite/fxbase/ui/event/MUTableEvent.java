package com.meteorite.fxbase.ui.event;

import com.meteorite.fxbase.ui.view.MUTable;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventType;

/**
 * MetaUI Table事件
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUTableEvent extends ActionEvent {
    private MUTable table;

    public MUTableEvent(MUTable table) {
        this.table = table;
    }

    public MUTable getTable() {
        return table;
    }
}

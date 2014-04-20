package com.meteorite.fxbase;

import com.meteorite.fxbase.ui.view.MUDialog;
import javafx.event.Event;
import javafx.event.EventHandler;

/**
 * 事件处理
 *
 * @author wei_jc
 * @since 1.0.0
 */
public abstract class MuEventHandler<T extends Event> implements EventHandler<T> {
    @Override
    public void handle(T event) {
        try {
            doHandler(event);
        } catch (Exception e) {
            e.printStackTrace();
            MUDialog.showExceptionDialog(e);
        }
    }

    public abstract void doHandler(T event) throws Exception;
}

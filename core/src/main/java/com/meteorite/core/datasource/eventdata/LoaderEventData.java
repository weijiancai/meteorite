package com.meteorite.core.datasource.eventdata;

import com.meteorite.core.observer.EventData;

/**
 * 数据库加载事件数据
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class LoaderEventData extends EventData {
    private String message;

    public LoaderEventData(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package com.meteorite.fxbase.ui.event.data;

import com.meteorite.core.dict.EnumDataStatus;
import com.meteorite.core.observer.EventData;
import com.meteorite.fxbase.ui.view.MUForm;

/**
 * 数据状态事件数据
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class DataStatusEventData extends EventData {
    private EnumDataStatus dataStatus;
    private MUForm form;

    public DataStatusEventData(EnumDataStatus dataStatus, MUForm form) {
        this.dataStatus = dataStatus;
        this.form = form;
    }

    public EnumDataStatus getDataStatus() {
        return dataStatus;
    }

    public MUForm getForm() {
        return form;
    }
}

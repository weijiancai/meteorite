package com.meteorite.fxbase.ui.design;

import com.meteorite.core.dict.DictCode;
import com.meteorite.core.dict.DictManager;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ChoiceBox;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.editor.AbstractPropertyEditor;

/**
 * 数据字典属性编辑器
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class DictPropertyEditor extends AbstractPropertyEditor<DictCode, ChoiceBox<DictCode>> {

    public DictPropertyEditor(PropertySheet.Item property, ChoiceBox<DictCode> control) {
        super(property, control);
    }

    public DictPropertyEditor(PropertySheet.Item property, ChoiceBox<DictCode> control, boolean readonly) {
        super(property, control, readonly);
    }

    public DictPropertyEditor(PropertySheet.Item param, String dictId) {
        super(param, new ChoiceBox<DictCode>());
        getEditor().getItems().setAll(DictManager.getDict(dictId).getCodeList());
    }

    @Override
    protected ObservableValue<DictCode> getObservableValue() {
        return getEditor().valueProperty();
    }

    @Override
    public void setValue(DictCode dictCode) {
        getEditor().setValue(dictCode);
    }
}

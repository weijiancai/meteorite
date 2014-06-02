package com.meteorite.fxbase.ui;

import com.meteorite.core.meta.model.MetaField;
import javafx.beans.property.StringProperty;

/**
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface IValue {
    String value();

    void setValue(String value);

    StringProperty valueProperty();

    String getName();

    MetaField getMetaField();
}

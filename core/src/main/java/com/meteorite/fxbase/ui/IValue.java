package com.meteorite.fxbase.ui;

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
}

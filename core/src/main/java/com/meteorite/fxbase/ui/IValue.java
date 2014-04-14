package com.meteorite.fxbase.ui;

/**
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface IValue {
    String[] values();

    String value();

    void setValue(String[] value);

    void setValue(String value);
}

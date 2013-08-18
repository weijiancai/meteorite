package com.meteorite.fxbase.ui.dialog;

import javafx.scene.control.Label;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class UITextArea extends Label {
    private double preferred_width = 360;

    /**
     * Creates a new instance of UITextArea
     */
    public UITextArea(String text) {
        setText(text);
        init();
    }

    /**
     * Creates a new instance of UITextArea with specified preferred width.
     * This is used by the dialog UI template.
     */
    public UITextArea(double my_width) {
        preferred_width = my_width;
        init();
    }

    private void init() {
        setPrefWidth(preferred_width);
        setMinSize(USE_PREF_SIZE, USE_PREF_SIZE);
        setWrapText(true);
    }
}

package com.meteorite.fxbase.ui.dialog;

import javafx.scene.image.ImageView;

/**
 *
 * @author wei_jc
 * @version 1.0.0
 */
public enum DialogType {
    ERROR(DialogOptions.OK, "error16.image") {
        @Override public String getDefaultMasthead() { return "错误"; }
    },
    INFORMATION(DialogOptions.OK, "info16.image") {
        @Override public String getDefaultMasthead() { return "消息"; }
    },
    WARNING(DialogOptions.OK, "warning16.image") {
        @Override public String getDefaultMasthead() { return "警告"; }
    },
    CONFIRMATION(DialogOptions.YES_NO_CANCEL, "confirm48.image") {
        @Override public String getDefaultMasthead() { return "选择一个选项"; }
    },
    INPUT(DialogOptions.OK_CANCEL, "confirm48.image") {
        @Override public String getDefaultMasthead() { return "选择一个选项"; }
    },
    CUSTOM(DialogOptions.OK, "info16.image") {
        @Override public String getDefaultMasthead() { return "消息"; }
    };

    private final DialogOptions defaultOptions;
    private final String imageResource;

    DialogType(DialogOptions defaultOptions, String imageResource) {
        this.defaultOptions = defaultOptions;
        this.imageResource = imageResource;
    }

    public ImageView getImage() {
        return DialogResources.getIcon(imageResource);
    }

    public String getDefaultTitle() {
        return getDefaultMasthead();
    }

    public abstract String getDefaultMasthead();

    public DialogOptions getDefaultOptions() {
        return defaultOptions;
    }
}

package com.meteorite.fxbase.ui.dialog;

/**
 * An enumeration used to specify which buttons to show to the user in a
 * dialog.
 *
 * @author wei_jc
 * @version 1.0.0
 */
public enum DialogOptions {
    /**
     * Used to specify that two buttons should be shown, with default labels
     * specified as 'Yes' and 'No'.
     */
    YES_NO,

    /**
     * Used to specify that three buttons should be shown, with default labels
     * specified as 'Yes', 'No', and 'Cancel'.
     */
    YES_NO_CANCEL,

    /**
     * Used to specify that one button should be shown, with the default label
     * specified as 'Ok'.
     */
    OK,

    /**
     * Used to specify that two buttons should be shown, with default labels
     * specified as 'Ok' and 'Cancel'.
     */
    OK_CANCEL
}

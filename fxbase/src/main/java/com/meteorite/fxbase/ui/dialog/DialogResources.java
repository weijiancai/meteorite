package com.meteorite.fxbase.ui.dialog;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class DialogResources {
    // Localization strings.
    private final static ResourceBundle dialogsResourceBundle =
            ResourceBundle.getBundle("com.sun.javafx.scene.control.skin.resources.dialog-resources");

    /**
     * Method to get an internationalized string from the deployment resource.
     */
    public static String getMessage(String key) {
        try {
            return dialogsResourceBundle.getString(key);
        } catch (MissingResourceException ex) {
            // Do not trace this exception, because the key could be
            // an already translated string.
            System.out.println("Failed to get string for key '" + key + "'");
            return key;
        }
    }

    /**
     * Returns a string from the resources
     */
    public static String getString(String key) {
        try {
            return dialogsResourceBundle.getString(key);
        } catch (MissingResourceException mre) {
            // Do not trace this exception, because the key could be
            // an already translated string.
            System.out.println("Failed to get string for key '" + key + "'");
            return key;
        }
    }

    /**
     * Returns a string from a resource, substituting argument 1
     */
    public static String getString(String key, Object... args) {
        return MessageFormat.format(getString(key), args);
    }

    /**
     * Returns an <code>ImageView</code> given an image file name or resource name
     */
    public static ImageView getIcon(final String key) {
        try {
            return AccessController.doPrivileged(
                    new PrivilegedExceptionAction<ImageView>() {
                        @Override
                        public ImageView run() {
                            String resourceName = getString(key);
                            URL url = DialogResources.class.getResource(resourceName);
                            if (url == null) {
                                System.out.println("Can't create ImageView for key '" + key +
                                        "', which has resource name '" + resourceName +
                                        "' and URL 'null'");
                                return null;
                            }
                            return getIcon(url);
                        }
                    });
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static ImageView getIcon(URL url) {
        return new ImageView(new Image(url.toString()));
    }
}

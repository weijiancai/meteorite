package com.meteorite.fxbase.util;

import com.meteorite.core.util.UString;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * JavaFx 图像工具类
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class FUImage {
    public static ImageView getImageView(String iconPath) {
        if (UString.isEmpty(iconPath)) {
            return null;
        }
        return new ImageView(new Image(FUImage.class.getResourceAsStream(iconPath)));
    }
}

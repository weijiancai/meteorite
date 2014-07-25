package com.ectongs.ideaplugin.sql;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

import java.awt.*;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class SqlFormat extends AnAction {
    private SqlFormatDialog dialog = new SqlFormatDialog();

    public void actionPerformed(AnActionEvent e) {
        dialog.setTitle("Sql格式化");
        int width = 800, height = 600;
        dialog.setSize(width, height);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        int screenWidth = dimension.width;                     //获取屏幕的宽
        int screenHeight = dimension.height;                   //获取屏幕的高
        dialog.setLocation(screenWidth/2-width/2, screenHeight/2-height/2);

        dialog.setVisible(true);
    }
}

package com.meteorite.fxbase;

import com.meteorite.core.config.ProjectConfig;
import com.meteorite.core.facade.IFacade;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * JavaFX 主应用程序
 *
 * @author wei_jc
 * @version 1.0.0
 */
public abstract class BaseApplication extends Application {
    private IFacade facade;

    public BaseApplication() {
        facade = getFacade();
    }

    @Override
    public void start(Stage stage) throws Exception {
        ProjectConfig projectConfig = facade.getProjectConfig();
        stage.setTitle(projectConfig.getProjectCnName());

        // 全屏显示
        Rectangle2D primaryScreenBonds = Screen.getPrimary().getVisualBounds();
        stage.setX(primaryScreenBonds.getMinX());
        stage.setY(primaryScreenBonds.getMinY());
        stage.setWidth(primaryScreenBonds.getWidth());
        stage.setHeight(primaryScreenBonds.getHeight());

        stage.show();
    }

    /**
     * 获取Facade
     *
     * @return 返回Facade
     */
    protected abstract IFacade getFacade();
}
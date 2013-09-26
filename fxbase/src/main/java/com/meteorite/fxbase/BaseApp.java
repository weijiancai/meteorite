package com.meteorite.fxbase;

import com.meteorite.core.config.ProjectConfig;
import com.meteorite.core.config.SystemManager;
import com.meteorite.core.facade.IFacade;
import com.meteorite.core.meta.MetaManager;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.ui.IView;
import com.meteorite.core.ui.config.ConfigInit;
import com.meteorite.core.ui.config.ViewConfigFactory;
import com.meteorite.core.util.HSqlDBServer;
import com.meteorite.fxbase.ui.Dialogs;
import com.meteorite.fxbase.ui.FxDesktop;
import com.meteorite.fxbase.ui.calendar.FXCalendar;
import com.meteorite.fxbase.ui.dialog.DialogOptions;
import com.meteorite.fxbase.ui.view.FxViewFactory;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import netscape.javascript.JSObject;

/**
 * JavaFX 主应用程序
 *
 * @author wei_jc
 * @version 1.0.0
 */
public abstract class BaseApp extends Application {
    /** 是否APPLET运行方式 */
    public static boolean IS_APPLET;
    private static BaseApp instance;

    private IFacade facade;

    protected static FxDesktop desktop;
    protected Scene scene;
    private Stage stage;

    public BaseApp() {
        instance = this;
        try {
            JSObject browser = getHostServices().getWebContext();
            IS_APPLET =  browser != null;
        } catch (Exception e) {
            IS_APPLET = false;
        }

        facade = getFacade();
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        final ProjectConfig projectConfig = facade.getProjectConfig();
        stage.setTitle(projectConfig.getDisplayName());

        //  启动数据库
        HSqlDBServer.getInstance().start();

        // 初始化完成
        facade.initAfter();

        // 全屏显示
        Rectangle2D primaryScreenBonds = Screen.getPrimary().getVisualBounds();
        stage.setX(primaryScreenBonds.getMinX());
        stage.setY(primaryScreenBonds.getMinY());
        stage.setWidth(primaryScreenBonds.getWidth());
        stage.setHeight(primaryScreenBonds.getHeight());

        // 检查是否配置了项目
        if (!SystemManager.isConfigured(projectConfig)) {
            IView<Pane> projectConfigView = FxViewFactory.getView(ConfigInit.getProjectConfig());
            Dialogs.showCustomDialog(stage, projectConfigView.layout(), "masthead", "项目信息配置", DialogOptions.OK, new Callback<Void, Void>() {
                @Override
                public Void call(Void aVoid) {
                    try {
                        if (!SystemManager.isConfigured(projectConfig)) {
                            System.exit(0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            });
        } else {
            desktop = new FxDesktop(stage);
            scene = new Scene(desktop);
            setSkin(R.skin.DEFAULT);
            setCalendarStyle();
            // show stage
            stage.setScene(scene);
        }

        setSkin(R.skin.DEFAULT);
        setCalendarStyle();
        // show stage
        stage.setScene(scene);

        stage.show();
    }

    public static FxDesktop getDesktop() {
        return desktop;
    }

    public void setSkin(String skin) {
        scene.getStylesheets().addAll(R.class.getResource("skin/" + skin + "/" + skin + ".css").toExternalForm());
    }

    private void setCalendarStyle() {
        scene.getStylesheets().addAll(FXCalendar.class.getResource("styles/calendar_styles.css").toExternalForm());
    }

    @Override
    public void stop() throws Exception {
        HSqlDBServer.getInstance().stop();
        super.stop();
    }

    /**
     * 获取Facade
     *
     * @return 返回Facade
     */
    protected abstract IFacade getFacade();

    /**
     * 获取BaseApp实例
     *
     * @return 返回BaseApp实例
     */
    public static BaseApp getInstance() {
        return instance;
    }

    /**
     * 获取JavaFx主Stage
     *
     * @return 返回JavaFx主Stage
     */
    public Stage getStage() {
        return stage;
    }
}
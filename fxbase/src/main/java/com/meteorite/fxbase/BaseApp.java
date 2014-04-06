package com.meteorite.fxbase;

import com.meteorite.core.config.ProjectConfig;
import com.meteorite.core.config.SystemManager;
import com.meteorite.core.facade.IFacade;
import com.meteorite.core.ui.IView;
import com.meteorite.core.ui.config.ConfigInit;
import com.meteorite.core.util.HSqlDBServer;
import com.meteorite.fxbase.ui.FxDesktop;
import com.meteorite.fxbase.ui.view.MUDialog;
import com.meteorite.fxbase.ui.view.FxPane;
import com.meteorite.fxbase.ui.view.FxView;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Callback;
import netscape.javascript.JSObject;

/**
 * JavaFX 主应用程序
 *
 * @author wei_jc
 * @since 1.0.0
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

        //  系统初始化
        SystemManager.getInstance().init();

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
//            IView<Pane> projectConfigView = FxViewFactory.getView(ConfigInit.getProjectConfig());
            IView<FxPane> projectConfigView = new FxView(ConfigInit.getProjectConfig());
            MUDialog.showCustomDialog(stage, "项目信息配置", projectConfigView.layout(), new Callback<Void, Void>() {
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
            // show stage
            stage.setScene(scene);
        }

        setSkin(R.skin.DEFAULT);
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
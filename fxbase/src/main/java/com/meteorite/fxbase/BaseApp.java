package com.meteorite.fxbase;

import com.meteorite.core.config.ProjectConfig;
import com.meteorite.core.config.ProjectConfigFactory;
import com.meteorite.core.facade.IFacade;
import com.meteorite.core.meta.MetaManager;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaForm;
import com.meteorite.fxbase.ui.Dialogs;
import com.meteorite.fxbase.ui.FxDesktop;
import com.meteorite.fxbase.ui.FxFormView;
import com.meteorite.fxbase.ui.calendar.FXCalendar;
import com.meteorite.fxbase.ui.dialog.DialogOptions;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    private IFacade facade;

    protected static FxDesktop desktop;
    protected Scene scene;

    public BaseApp() {
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
        ProjectConfig projectConfig = facade.getProjectConfig();
        stage.setTitle(projectConfig.getProjectCnName());

        // 全屏显示
        Rectangle2D primaryScreenBonds = Screen.getPrimary().getVisualBounds();
        stage.setX(primaryScreenBonds.getMinX());
        stage.setY(primaryScreenBonds.getMinY());
        stage.setWidth(primaryScreenBonds.getWidth());
        stage.setHeight(primaryScreenBonds.getHeight());

        // 检查是否配置了项目
        if (!ProjectConfigFactory.isConfigured(projectConfig)) {
            Meta meta = MetaManager.toMeta(projectConfig);
            MetaForm metaForm = MetaManager.toForm(meta);
            FxFormView formView = new FxFormView(metaForm);
//            Dialogs.showInformationDialog(stage, "请配置项目信息！");
            Dialogs.showCustomDialog(stage, formView, "masthead", "项目信息配置", DialogOptions.OK, new Callback<Void, Void>() {
                @Override
                public Void call(Void aVoid) {
                    return null;
                }
            });
        } else {
            scene = new Scene(new Button("测试"));
            setSkin(R.skin.DEFAULT);
            setCalendarStyle();
            // show stage
            stage.setScene(scene);
        }

        scene = new Scene(new Label("测试"));
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

    /**
     * 获取Facade
     *
     * @return 返回Facade
     */
    protected abstract IFacade getFacade();
}
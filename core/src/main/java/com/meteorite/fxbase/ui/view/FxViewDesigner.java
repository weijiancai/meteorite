package com.meteorite.fxbase.ui.view;

import com.meteorite.core.ui.ILayoutConfig;
import com.meteorite.core.ui.IViewConfig;
import com.meteorite.core.ui.layout.LayoutManager;
import com.meteorite.fxbase.ui.component.FxFormPane;
import com.meteorite.fxbase.ui.event.FxLayoutEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

/**
 * JavaFx 视图设计器
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class FxViewDesigner extends BorderPane {
    private IViewConfig viewConfig;
    private FxView view;
    private FxPane pane;

    public FxViewDesigner(IViewConfig config) {
        this.viewConfig = config;

        initUI();
    }

    private void initUI() {
        view = new FxView(viewConfig, true);
        pane = view.layout();
        pane.setStyle("-fx-background-color: #bbbbbb;");
        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pane.fireEvent(new FxLayoutEvent(viewConfig.getLayoutConfig(), pane));
            }
        });
        pane.registLayoutEvent();


        this.setPrefWidth(1000);
        this.setPrefHeight(500);
        // 中间可视化布局
        this.setCenter(pane);
        // 底部布局属性配置
//        setBottom(viewConfig.getLayoutConfig());
        setBottom(new FxLayoutEvent(viewConfig.getLayoutConfig(), pane));

        this.addEventHandler(FxLayoutEvent.EVENT_TYPE, new EventHandler<FxLayoutEvent>() {
            @Override
            public void handle(FxLayoutEvent event) {
                setBottom(event);
            }
        });
    }

    public void setBottom(FxLayoutEvent event) {
        ILayoutConfig config = LayoutManager.createFormLayout(event.getLayoutConfig());
        FxFormPane formPane = new FxFormPane(config, false);
        formPane.registLayoutConfigChangeEvent(event);
        setBottom(formPane);
    }

}

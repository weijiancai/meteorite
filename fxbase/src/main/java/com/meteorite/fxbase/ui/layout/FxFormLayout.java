package com.meteorite.fxbase.ui.layout;

import com.meteorite.core.ui.IActionConfig;
import com.meteorite.core.ui.IViewConfig;
import com.meteorite.core.ui.config.layout.FormConfig;
import com.meteorite.fxbase.ui.component.FxFormPane;
import com.meteorite.fxbase.ui.view.FxPane;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class FxFormLayout {
//    private List<Action> actions;
    private IViewConfig viewConfig;
    private FormConfig formConfig;
    private boolean isDesign;

    public FxFormLayout(IViewConfig viewConfig, boolean isDesign) {
        this.viewConfig = viewConfig;
        this.isDesign = isDesign;
        this.formConfig = new FormConfig(viewConfig.getLayoutConfig());
    }

    private void initUI() {

    }

    /*public List<Action> getActions() {
        return actions;
    }*/

    public BorderPane layout() {
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(getTop());
        borderPane.setCenter(new FxFormPane(viewConfig.getLayoutConfig()));
        return borderPane;
    }

    public HBox getTop() {
        HBox box = new HBox(10);
        box.setPrefHeight(30);
        Region region = new Region();
        box.getChildren().add(region);
        HBox.setHgrow(region, Priority.ALWAYS);
        for (IActionConfig action : viewConfig.getLayoutConfig().getActionConfigs()) {
            Button button = new Button(action.getDisplayName());
            button.setId(action.getName());
            box.getChildren().add(button);
        }
        return box;
    }
}

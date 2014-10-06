package com.meteorite.fxbase.ui.design;

import com.meteorite.core.ui.layout.property.FormProperty;
import com.meteorite.fxbase.MuEventHandler;
import com.meteorite.fxbase.ui.event.FormFieldClickEvent;
import com.meteorite.fxbase.ui.view.MUForm;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * 表单设计器
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUFormDesigner extends BorderPane {
    private MUForm sourceForm;
    private MUForm currentForm;
    private MUForm hiddenForm;
    private FormProperty formConfig;
    private Properties properties;

    public MUFormDesigner(MUForm source) {
        this.sourceForm = source;
        this.formConfig = source.getFormConfig();
        initUI();
    }

    private void initUI() {
        this.setPrefHeight(600);
        // 显示未隐藏的表单
        currentForm = new MUForm();
        currentForm.setDesignMode(true);
        currentForm.initUI(sourceForm.getFormConfig());
        currentForm.addEventHandler(FormFieldClickEvent.EVENT_TYPE, new MuEventHandler<FormFieldClickEvent>() {
            @Override
            public void doHandler(FormFieldClickEvent event) throws Exception {
                properties.setProperties(event.getFormField());
            }
        });
        // 显示隐藏的表单
        hiddenForm = new MUForm();
        hiddenForm.setOnlyShowHidden(true);
        hiddenForm.setDesignMode(true);
        hiddenForm.initUI(formConfig);
        hiddenForm.addEventHandler(FormFieldClickEvent.EVENT_TYPE, new MuEventHandler<FormFieldClickEvent>() {
            @Override
            public void doHandler(FormFieldClickEvent event) throws Exception {
                properties.setProperties(event.getFormField());
            }
        });

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(currentForm);
        borderPane.setBottom(hiddenForm);
        this.setCenter(borderPane);

        // 工具条
        ToolBar toolBar = new ToolBar();
        Button btnRefresh = new Button("刷新");
        btnRefresh.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                currentForm.initUI(formConfig);
                hiddenForm.initUI(formConfig);
            }
        });
        toolBar.getItems().addAll(btnRefresh);
        this.setTop(toolBar);

        // 属性面板
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Properties.fxml"));
            loader.load();
            VBox propertiesPane = loader.getRoot();
            properties = loader.getController();

            this.setRight(propertiesPane);
        } catch (Exception e) {
            throw new RuntimeException("加载属性配置文件失败！", e);
        }
    }
}

package com.meteorite.fxbase.ui.component;

import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.ui.ILayoutConfig;
import com.meteorite.core.ui.config.layout.FormConfig;
import com.meteorite.core.ui.config.layout.FormFieldConfig;
import com.meteorite.fxbase.ui.event.FxLayoutEvent;
import com.meteorite.fxbase.ui.valuectl.VComboBox;
import com.meteorite.fxbase.ui.valuectl.VPasswordField;
import com.meteorite.fxbase.ui.valuectl.VTextArea;
import com.meteorite.fxbase.ui.valuectl.VTextField;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

import java.util.ArrayList;
import java.util.List;

/**
 * 表单面板
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class FxFormPane extends GridPane {
    private FormConfig formConfig;
    private boolean isDesign;

    public FxFormPane(FormConfig formConfig, boolean isDesign) {
        this.formConfig = formConfig;
        this.isDesign = isDesign;
        init();
//        this.setGridLinesVisible(true);
    }

    public void init() {
        this.setHgap(formConfig.getHgap());
        this.setVgap(formConfig.getVgap());

        Label label;
        Region labelGap;
        Node node;
        Region fieldGap;
        int idxRow = 0;
        int idxCol = 0;

        for (FormFieldConfig field : formConfig.getFormFields()) {
            if (!field.isDisplay()) { // 不显示
                continue;
            }
//            columnNameMap.put(field.getMetaField().getName().toLowerCase(), field);
//            fieldMap.put(field.getId(), field);

            node = getValueNode(field);
            // 单行
            if (field.isSingleLine()) {
                idxRow++;
                label = new Label(field.getDisplayName());
                this.add(label, 0, idxRow);

                labelGap = new Region();
                labelGap.setPrefWidth(formConfig.getLabelGap());
                this.add(labelGap, 1, idxRow);

//                fieldNodeMap.put(field.getId(), node);
                this.add(node, 2, idxRow, formConfig.getColCount() * 4 - 2, 1);

                GridPane.setHgrow(node, Priority.ALWAYS);
                idxCol = 0;
                idxRow++;

                continue;
            }

            label = new Label(field.getDisplayName());
            this.add(label, idxCol++, idxRow);

            labelGap = new Region();
            labelGap.setPrefWidth(formConfig.getLabelGap());
            this.add(labelGap, idxCol++, idxRow);

//            fieldNodeMap.put(field.getId(), node);
            this.add(node, idxCol++, idxRow);

            if (formConfig.getColCount() == 1) { // 单列
                idxCol = 0;
                idxRow++;
            } else { // 多列
                if (idxCol == formConfig.getColCount() * 4 - 1) {
                    idxCol = 0;
                    idxRow++;
                } else {
                    fieldGap = new Region();
                    fieldGap.setPrefWidth(formConfig.getFieldGap());
                    this.add(fieldGap, idxCol++, idxRow);
                }
            }
        }
    }

    private Node getValueNode(FormFieldConfig field) {
        Node node;
        DisplayStyle displayStyle = field.getDisplayStyle();
        if (DisplayStyle.TEXT_AREA == displayStyle) {
            VTextArea textArea = new VTextArea(field.getLayoutConfig(), false);
            textArea.setPrefHeight(field.getHeight());
            node = textArea;
        } else if (DisplayStyle.PASSWORD == displayStyle) {
            VPasswordField passwordField = new VPasswordField(field.getLayoutConfig(), false);
            passwordField.setPrefWidth(field.getWidth());
            node = passwordField;
        } else if (DisplayStyle.COMBO_BOX == displayStyle || DisplayStyle.BOOLEAN == displayStyle) {
            VComboBox comboBox = new VComboBox(field.getDict(), field.getLayoutConfig(), false);
            comboBox.setPrefWidth(field.getWidth());
            comboBox.setValue(field.getValue());
            node = comboBox;
        } else if (DisplayStyle.DATA_SOURCE == displayStyle) {
            FxDataSource dataSource = new FxDataSource(field.getLayoutConfig(), isDesign);
            if(formConfig.getColCount() == 1) {
                dataSource.setMaxWidth(formConfig.getColWidth());
            }
            node = dataSource;
        } else {
            if (MetaDataType.DATE == field.getDataType()) {
                /*if ("0".equals(field.getForm().getFormType())) {
                    VDateRangeField dateField = new VDateRangeField();
                    dateField.setPrefWidth(field.getWidth() + 0.0);
                    node = dateField;
                } else {
                    VDateField dateField = new VDateField();
                    dateField.setDateTextWidth(field.getWidth() + 0.0);
                    node = dateField;
                }*/
                node = null;
            } else {
                VTextField textField = new VTextField(field.getLayoutConfig(), isDesign);
                textField.setPrefWidth(field.getWidth());
                textField.setValue(field.getValue());
                node = textField;
            }
        }
        return node;
    }
}

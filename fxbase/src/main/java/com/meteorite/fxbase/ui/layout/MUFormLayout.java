package com.meteorite.fxbase.ui.layout;

import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.ui.layout.property.FormFieldProperty;
import com.meteorite.core.ui.layout.property.FormProperty;
import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.component.form.*;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.HashMap;
import java.util.Map;

/**
 * MetaUI 表单布局器
 *
 * @author wei_jc
 * @since  1.0.0
 */
public class MUFormLayout extends BorderPane {
    private FormProperty formConfig;
    private Map<String, IValue> valueMap = new HashMap<>();

    public MUFormLayout(FormProperty property) {
        this.formConfig = property;
        initUI();
    }

    private void initUI() {
        this.setTop(createTop());
        this.setCenter(createCenter());
    }

    public HBox createTop() {
        HBox box = new HBox(10);
        box.setPrefHeight(30);
        Region region = new Region();
        box.getChildren().add(region);
        HBox.setHgrow(region, Priority.ALWAYS);
        /*for (IActionConfig action : layoutConfig.getActionConfigs()) {
            Button button = new Button(action.getDisplayName());
            button.setId(action.getName());
            box.getChildren().add(button);
        }*/
        return box;
    }

    public GridPane createCenter() {
        GridPane gridPane = new GridPane();
//        gridPane.setGridLinesVisible(true);
        gridPane.setHgap(formConfig.getHgap());
        gridPane.setVgap(formConfig.getVgap());

        Label label;
        Region labelGap;
        Node formField;
        Region fieldGap;
        int idxRow = 0;
        int idxCol = 0;

        for (FormFieldProperty field : formConfig.getFormFields()) {
            if (!field.isDisplay()) { // 不显示
                continue;
            }

            formField = getFormField(field);
            // 单行
            if (field.isSingleLine()) {
                idxRow++;
                label = new javafx.scene.control.Label(field.getDisplayName());
                gridPane.add(label, 0, idxRow);

                labelGap = new Region();
                labelGap.setPrefWidth(formConfig.getLabelGap());
                gridPane.add(labelGap, 1, idxRow);

                gridPane.add(formField, 2, idxRow, formConfig.getColCount() * 4 - 2, 1);

                GridPane.setHgrow(formField, Priority.ALWAYS);
                idxCol = 0;
                idxRow++;

                continue;
            }

            label = new Label(field.getDisplayName());
            gridPane.add(label, idxCol++, idxRow);

            labelGap = new Region();
            labelGap.setPrefWidth(formConfig.getLabelGap());
            gridPane.add(labelGap, idxCol++, idxRow);

            gridPane.add(formField, idxCol++, idxRow);

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
                    gridPane.add(fieldGap, idxCol++, idxRow);
                }
            }
        }

        return gridPane;
    }

    private Node getFormField(FormFieldProperty field) {
        Node node;
        DisplayStyle displayStyle = field.getDisplayStyle();
        if (DisplayStyle.TEXT_AREA == displayStyle) {
            node = new MuTextArea(field);
        } else if (DisplayStyle.PASSWORD == displayStyle) {
            node = new MuPasswordField(field);
        } else if (DisplayStyle.COMBO_BOX == displayStyle || DisplayStyle.BOOLEAN == displayStyle) {
            node = new MuComboBox(field);
        } else if (DisplayStyle.DATA_SOURCE == displayStyle) {
            node = new MuDataSource(field);
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
                node = new MuDate(field);
            } else {
                node = new MuTextField(field);
            }
        }
        valueMap.put(field.getColumnName(), (IValue)node);
        return node;
    }

    public Map<String, IValue> getValueMap() {
        return valueMap;
    }
}

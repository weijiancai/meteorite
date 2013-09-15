package com.meteorite.fxbase.ui.layout;

import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.ui.ConfigConst;
import com.meteorite.core.ui.IActionConfig;
import com.meteorite.core.ui.ILayoutConfig;
import com.meteorite.core.ui.IViewConfig;
import com.meteorite.core.ui.layout.property.FormFieldProperty;
import com.meteorite.core.ui.layout.property.FormProperty;
import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.valuectl.VPasswordField;
import com.meteorite.fxbase.ui.valuectl.VTextArea;
import com.meteorite.fxbase.ui.valuectl.VTextField;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class FxFormLayout implements ConfigConst {
//    private List<Action> actions;
    private IViewConfig viewConfig;

    public FxFormLayout(IViewConfig viewConfig) {
        this.viewConfig = viewConfig;
    }

    private void initUI() {

    }

    /*public List<Action> getActions() {
        return actions;
    }*/

    public BorderPane layout() {
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(getTop());
        borderPane.setCenter(getCenter());
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

    public GridPane getCenter() {
        ILayoutConfig form = viewConfig.getLayoutConfig();
        GridPane formGrid = new GridPane();
        formGrid.setHgap(form.getPropIntValue(FORM_HGAP));
        formGrid.setVgap(form.getPropIntValue(FORM_VGAP));

        Label label;
        Region labelGap;
        IValue node;
        Region fieldGap;
        int idxRow = 0;
        int idxCol = 0;
        for (ILayoutConfig field : form.getChildren()) {
            if (!field.getPropBooleanValue(FORM_FIELD_IS_DISPLAY)) { // 不显示
                continue;
            }
//            columnNameMap.put(field.getMetaField().getName().toLowerCase(), field);
//            fieldMap.put(field.getId(), field);

            node = getValueNode(field);
            // 单行
            if (field.getPropBooleanValue(FORM_FIELD_IS_SINGLE_LINE)) {
                idxRow++;
                formGrid.add(new Label(field.getPropStringValue(FORM_FIELD_DISPLAY_NAME)), 0, idxRow);
                labelGap = new Region();
                labelGap.setPrefWidth(form.getPropIntValue(FORM_LABEL_GAP));
                formGrid.add(labelGap, 1, idxRow);
//                fieldNodeMap.put(field.getId(), node);
                formGrid.add((Node)node, 2, idxRow, form.getPropIntValue(FORM_COL_COUNT) * 4 - 3, 1);
                idxCol = 0;
                idxRow++;

                continue;
            }

            label = new Label(field.getPropStringValue(FORM_FIELD_DISPLAY_NAME));
            formGrid.add(label, idxCol++, idxRow);

            labelGap = new Region();
            labelGap.setPrefWidth(form.getPropIntValue(FORM_LABEL_GAP));
            formGrid.add(labelGap, idxCol++, idxRow);

//            fieldNodeMap.put(field.getId(), node);
            formGrid.add((Node)node, idxCol++, idxRow);

            if (form.getPropIntValue(FORM_COL_COUNT) == 1) {
                idxCol = 0;
                idxRow++;
            } else {
                if (idxCol == form.getPropIntValue(FORM_COL_COUNT) * 4 - 1) {
                    idxCol = 0;
                    idxRow++;
                } else {
                    fieldGap = new Region();
                    fieldGap.setPrefWidth(form.getPropIntValue(FORM_FIELD_GAP));
                    formGrid.add(fieldGap, idxCol++, idxRow);
                }
            }
        }

        return formGrid;
    }

    private IValue getValueNode(ILayoutConfig field) {
        IValue node;
        DisplayStyle displayStyle = DisplayStyle.getStyle(field.getPropStringValue(FORM_FIELD_DISPLAY_STYLE));
        if (DisplayStyle.TEXT_AREA == displayStyle) {
            VTextArea textArea = new VTextArea();
            textArea.setPrefHeight(field.getPropIntValue(FORM_FIELD_HEIGHT));
            node = textArea;
        } else if (DisplayStyle.PASSWORD == displayStyle) {
            VPasswordField passwordField = new VPasswordField();
            passwordField.setPrefWidth(field.getPropIntValue(FORM_FIELD_WIDTH));
            node = passwordField;
        } else if (DisplayStyle.COMBO_BOX == displayStyle) {
            /*VComboBox comboBox = new VComboBox(field.getMetaField().getDictCategory());
            comboBox.setPrefWidth(field.getWidth());
            node = comboBox;*/
            node = null;
        } else {
            if (MetaDataType.DATE == field.getPropDataType(FORM_FIELD_DATA_TYPE)) {
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
                VTextField textField = new VTextField();
                textField.setPrefWidth(field.getPropIntValue(FORM_FIELD_WIDTH));
                node = textField;
            }
        }
        return node;
    }


}

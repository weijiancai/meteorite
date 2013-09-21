package com.meteorite.fxbase.ui.layout;

import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.ui.ConfigConst;
import com.meteorite.core.ui.IActionConfig;
import com.meteorite.core.ui.ILayoutConfig;
import com.meteorite.core.ui.IViewConfig;
import com.meteorite.core.ui.config.layout.FormConfig;
import com.meteorite.core.ui.config.layout.FormFieldConfig;
import com.meteorite.core.ui.layout.property.FormFieldProperty;
import com.meteorite.core.ui.layout.property.FormProperty;
import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.component.FxDataSource;
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
public class FxFormLayout {
//    private List<Action> actions;
    private IViewConfig viewConfig;
    private FormConfig formConfig;

    public FxFormLayout(IViewConfig viewConfig) {
        this.viewConfig = viewConfig;
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
        GridPane formGrid = new GridPane();
        formGrid.setHgap(formConfig.getHgap());
        formGrid.setVgap(formConfig.getVgap());

        Label label;
        Region labelGap;
        Pane node;
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
                formGrid.add(new Label(field.getDisplayName()), 0, idxRow);
                labelGap = new Region();
                labelGap.setPrefWidth(formConfig.getLabelGap());
                formGrid.add(labelGap, 1, idxRow);
//                fieldNodeMap.put(field.getId(), node);
                int prefWidth = formConfig.getColCount() * formConfig.getColWidth() + formConfig.getColCount() * formConfig.getLabelGap() + (formConfig.getColCount() - 1) * formConfig.getFieldGap()
                        + formConfig.getColCount() * formConfig.getHgap();
//                System.out.println(prefWidth);
                System.out.println(formGrid.getPrefWidth());
//                System.out.println(node.getPrefWidth());
//                node.setPrefWidth(prefWidth);
                GridPane.setHgrow(node, Priority.ALWAYS);
                node.prefWidthProperty().bindBidirectional(formGrid.prefWidthProperty());
                formGrid.add(node, 2, idxRow, formConfig.getColCount() * 4 - 2, 1);
                idxCol = 0;
                idxRow++;

                continue;
            }

            label = new Label(field.getDisplayName());
            formGrid.add(label, idxCol++, idxRow);

            labelGap = new Region();
            labelGap.setPrefWidth(formConfig.getLabelGap());
            formGrid.add(labelGap, idxCol++, idxRow);

//            fieldNodeMap.put(field.getId(), node);
            formGrid.add(node, idxCol++, idxRow);

            if (formConfig.getColCount() == 1) {
                idxCol = 0;
                idxRow++;
            } else {
                if (idxCol == formConfig.getColCount() * 4 - 1) {
                    idxCol = 0;
                    idxRow++;
                } else {
                    fieldGap = new Region();
                    fieldGap.setPrefWidth(formConfig.getFieldGap());
                    formGrid.add(fieldGap, idxCol++, idxRow);
                }
            }
        }

        formGrid.layout();

        return formGrid;
    }

    private Pane getValueNode(FormFieldConfig field) {
        Pane node;
        DisplayStyle displayStyle = field.getDisplayStyle();
        if (DisplayStyle.TEXT_AREA == displayStyle) {
            VTextArea textArea = new VTextArea();
            textArea.setPrefHeight(field.getHeight());
            node = textArea;
        } else if (DisplayStyle.PASSWORD == displayStyle) {
            VPasswordField passwordField = new VPasswordField();
            passwordField.setPrefWidth(field.getWidth());
            node = passwordField;
        } else if (DisplayStyle.COMBO_BOX == displayStyle) {
            /*VComboBox comboBox = new VComboBox(field.getMetaField().getDictCategory());
            comboBox.setPrefWidth(field.getWidth());
            node = comboBox;*/
            node = null;
        } else if (DisplayStyle.DATA_SOURCE == displayStyle) {
            FxDataSource dataSource = new FxDataSource();
//            dataSource.setPrefWidth(field.getFormConfig().getColWidth() * 2);
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
                VTextField textField = new VTextField();
                textField.setPrefWidth(field.getWidth());
                node = textField;
            }
        }
        return node;
    }


}

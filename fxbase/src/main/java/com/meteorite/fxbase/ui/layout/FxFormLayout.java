package com.meteorite.fxbase.ui.layout;

import com.meteorite.core.R;
import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.ui.layout.LayoutManager;
import com.meteorite.core.ui.layout.impl.FormLayout;
import com.meteorite.core.ui.layout.property.FormFieldProperty;
import com.meteorite.core.ui.layout.property.FormProperty;
import com.meteorite.core.ui.model.Action;
import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.valuectl.VPasswordField;
import com.meteorite.fxbase.ui.valuectl.VTextArea;
import com.meteorite.fxbase.ui.valuectl.VTextField;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.List;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class FxFormLayout extends FormLayout {
    private FormProperty model;
    private List<Action> actions;

    public FxFormLayout(FormProperty property) {
        this.model = property;
        actions = LayoutManager.getLayout(R.layout.FORM).getActions();

        initUI();
    }

    private void initUI() {

    }

    public List<Action> getActions() {
        return actions;
    }

    @Override
    public BorderPane layout() {
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(getTop());
        borderPane.setCenter(getCenter());
        return borderPane;
    }

    public HBox getTop() {
        HBox box = new HBox(5);
        Region region = new Region();
        box.getChildren().add(region);
        HBox.setHgrow(region, Priority.ALWAYS);
        for (Action action : actions) {
            Button button = new Button(action.getCname());
            button.setId(action.getName());
            box.getChildren().add(button);
        }
        return box;
    }

    public GridPane getCenter() {
        FormProperty form = model;

        GridPane formGrid = new GridPane();
        formGrid.setHgap(form.getHgap());
        formGrid.setVgap(form.getVgap());

        Label label;
        Region labelGap;
        IValue node;
        Region fieldGap;
        int idxRow = 0;
        int idxCol = 0;
        for (FormFieldProperty field : form.getFormFields()) {
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
                labelGap.setPrefWidth(form.getLabelGap());
                formGrid.add(labelGap, 1, idxRow);
//                fieldNodeMap.put(field.getId(), node);
                formGrid.add((Node)node, 2, idxRow, form.getColCount() * 4 - 3, 1);
                idxCol = 0;
                idxRow++;

                continue;
            }

            label = new Label(field.getDisplayName());
            formGrid.add(label, idxCol++, idxRow);

            labelGap = new Region();
            labelGap.setPrefWidth(form.getLabelGap());
            formGrid.add(labelGap, idxCol++, idxRow);

//            fieldNodeMap.put(field.getId(), node);
            formGrid.add((Node)node, idxCol++, idxRow);

            if (form.getColCount() == 1) {
                idxCol = 0;
                idxRow++;
            } else {
                if (idxCol == form.getColCount() * 4 - 1) {
                    idxCol = 0;
                    idxRow++;
                } else {
                    fieldGap = new Region();
                    fieldGap.setPrefWidth(form.getFieldGap());
                    formGrid.add(fieldGap, idxCol++, idxRow);
                }
            }
        }

        return formGrid;
    }

    private IValue getValueNode(FormFieldProperty field) {
        IValue node;
        if (DisplayStyle.TEXT_AREA == field.getDisplayStyle()) {
            VTextArea textArea = new VTextArea();
            textArea.setPrefHeight(field.getHeight());
            node = textArea;
        } else if (DisplayStyle.PASSWORD == field.getDisplayStyle()) {
            VPasswordField passwordField = new VPasswordField();
            passwordField.setPrefWidth(field.getWidth());
            node = passwordField;
        } else if (DisplayStyle.COMBO_BOX == field.getDisplayStyle()) {
            /*VComboBox comboBox = new VComboBox(field.getMetaField().getDictCategory());
            comboBox.setPrefWidth(field.getWidth());
            node = comboBox;*/
            node = null;
        } else {
            if (MetaDataType.DATE == field.getMetaField().getDataType()) {
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

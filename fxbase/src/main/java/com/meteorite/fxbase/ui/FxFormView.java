package com.meteorite.fxbase.ui;

import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.meta.model.MetaForm;
import com.meteorite.core.meta.model.MetaFormField;
import com.meteorite.core.ui.IFormView;
import com.meteorite.fxbase.ui.valuectl.*;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.util.HashMap;
import java.util.Map;

/**
 * JavaFx表单视图
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class FxFormView extends BorderPane implements IFormView {
    private MetaForm metaForm;

    private Map<String, IValue> fieldNodeMap = new HashMap<String, IValue>();
    private Map<String, MetaFormField> fieldMap = new HashMap<String, MetaFormField>();
    private Map<String, MetaFormField> columnNameMap = new HashMap<String, MetaFormField>();

    public FxFormView(MetaForm metaForm) {
        this.metaForm = metaForm;

        initUI();
    }

    @Override
    public void initUI() {
        initFormGridPane();
    }

    private void initFormGridPane() {
        GridPane formGrid = new GridPane();
        formGrid.setHgap(metaForm.getHgap());
        formGrid.setVgap(metaForm.getVgap());

        Label label;
        Region labelGap;
        IValue node;
        Region fieldGap;
        int idxRow = 0;
        int idxCol = 0;
        for (MetaFormField field : metaForm.getFormFields()) {
            if (!field.isDisplay()) { // 不显示
                continue;
            }
            columnNameMap.put(field.getMetaField().getName().toLowerCase(), field);
            fieldMap.put(field.getId(), field);

            node = getValueNode(field);
            // 单行
            if (field.isSingleLine()) {
                idxRow++;
                formGrid.add(new Label(field.getDisplayName()), 0, idxRow);
                labelGap = new Region();
                labelGap.setPrefWidth(metaForm.getLabelGap());
                formGrid.add(labelGap, 1, idxRow);
//                fieldNodeMap.put(field.getId(), node);
                formGrid.add((Node)node, 2, idxRow, metaForm.getColCount() * 4 - 3, 1);
                idxCol = 0;
                idxRow++;

                continue;
            }

            label = new Label(field.getDisplayName());
            formGrid.add(label, idxCol++, idxRow);

            labelGap = new Region();
            labelGap.setPrefWidth(metaForm.getLabelGap());
            formGrid.add(labelGap, idxCol++, idxRow);

//            fieldNodeMap.put(field.getId(), node);
            formGrid.add((Node)node, idxCol++, idxRow);

            if (metaForm.getColCount() == 1) {
                idxCol = 0;
                idxRow++;
            } else {
                if (idxCol == metaForm.getColCount() * 4 - 1) {
                    idxCol = 0;
                    idxRow++;
                } else {
                    fieldGap = new Region();
                    fieldGap.setPrefWidth(metaForm.getFieldGap());
                    formGrid.add(fieldGap, idxCol++, idxRow);
                }
            }
        }

        this.setCenter(formGrid);
    }

    private IValue getValueNode(MetaFormField field) {
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
                if ("0".equals(field.getForm().getFormType())) {
                    VDateRangeField dateField = new VDateRangeField();
                    dateField.setPrefWidth(field.getWidth() + 0.0);
                    node = dateField;
                } else {
                    VDateField dateField = new VDateField();
                    dateField.setDateTextWidth(field.getWidth() + 0.0);
                    node = dateField;
                }
            } else {
                VTextField textField = new VTextField();
                textField.setPrefWidth(field.getWidth());
                node = textField;
            }
        }
        return node;
    }
}

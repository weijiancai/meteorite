package com.meteorite.fxbase.ui.layout;

import com.meteorite.core.dict.FormType;
import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.ui.layout.property.FormFieldProperty;
import com.meteorite.core.ui.layout.property.FormProperty;
import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.component.form.*;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MetaUI 表单布局器
 *
 * @author wei_jc
 * @since  1.0.0
 */
public class MUFormLayout extends BorderPane {
    private FormProperty formConfig;
    private Map<String, IValue> valueMap = new HashMap<String, IValue>();
    private List<ICanQuery> queryList = new ArrayList<ICanQuery>();
    private boolean onlyShowHidden;

    public MUFormLayout(FormProperty property) {
        this.formConfig = property;
        initUI();
    }

    public MUFormLayout() {
    }

    private void initUI() {
//        this.setTop(createTop());
        this.setCenter(createCenter());
        this.setStyle("-fx-padding: 10");
    }

    public void initUI(FormProperty property, boolean onlyShowHidden) {
        this.formConfig = property;
        this.onlyShowHidden = onlyShowHidden;
        this.setCenter(createCenter());
        this.setStyle("-fx-padding: 10");
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
        gridPane.setAlignment(Pos.TOP_LEFT);

//        Label label;
        TextFlow textFlow;
        Region labelGap;
        Node formField;
        Region fieldGap;
        int idxRow = 0;
        int idxCol = 0;

        for (FormFieldProperty field : formConfig.getFormFields()) {
            if (onlyShowHidden) { // 只显示隐藏的
                if (field.isDisplay()) {
                    continue;
                }
            } else if (!field.isDisplay()) { // 不显示
                continue;
            }

            formField = getFormField(field);
            // 查询表单
            if (FormType.QUERY == formConfig.getFormType()) {
                // TextArea不显示
                if ((formField instanceof MuTextArea || field.getMaxLength() > 200)) {
                    continue;
                }
                // 查询表单，查询条件，至显示3行
                if (idxRow > 3) {
                    break;
                }
            }

            // 显示文本
            textFlow = new TextFlow();
            textFlow.getChildren().add(new Text(field.getDisplayName()));
            if (field.isRequire() && formConfig.getFormType() == FormType.EDIT) {
                Text requireText = new Text("*");
                requireText.setFill(Color.RED);
                requireText.setFont(new Font(15));
                requireText.setTextAlignment(TextAlignment.CENTER);
                textFlow.getChildren().add(requireText);
            }

            // 单行
            if (field.isSingleLine()) {
                idxRow++;
//                label = new Label(field.getDisplayName());
                gridPane.add(textFlow, 0, idxRow);

                labelGap = new Region();
                labelGap.setPrefWidth(formConfig.getLabelGap());
                gridPane.add(labelGap, 1, idxRow);

                gridPane.add(formField, 2, idxRow, formConfig.getColCount() * 4 - 2, 1);

                GridPane.setHgrow(formField, Priority.ALWAYS);
                idxCol = 0;
                idxRow++;

                continue;
            }

//            label = new Label(field.getDisplayName());
            gridPane.add(textFlow, idxCol++, idxRow);

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
        } else if (DisplayStyle.PASSWORD == displayStyle || MetaDataType.PASSWORD == field.getDataType()) {
            node = new MuPasswordField(field);
        } else if (DisplayStyle.COMBO_BOX == displayStyle || DisplayStyle.BOOLEAN == displayStyle) {
            node = new MuComboBox(field);
        } else if (DisplayStyle.DATA_SOURCE == displayStyle) {
            node = new MuDataSource(field);
        } else if (DisplayStyle.DATE == displayStyle) {
            if (formConfig.getFormType() == FormType.QUERY) {
                node = new MuDateRange(field);
            } else {
                node = new MuDate(field);
            }
        } else {
            node = new MuTextField(field);
        }

        if (MetaDataType.INTEGER == field.getDataType() && formConfig.getFormType() == FormType.QUERY) {
            node = new MuIntRange(field);
        }

        valueMap.put(field.getName(), (IValue)node);
        queryList.add((ICanQuery) node);

        return node;
    }

    public Map<String, IValue> getValueMap() {
        return valueMap;
    }

    public List<ICanQuery> getQueryList() {
        return queryList;
    }
}

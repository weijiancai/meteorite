package com.meteorite.fxbase.ui.component;

import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.ui.ConfigConst;
import com.meteorite.core.ui.IActionConfig;
import com.meteorite.core.ui.ILayoutConfig;
import com.meteorite.core.util.UNumber;
import com.meteorite.core.util.UString;
import com.meteorite.fxbase.ui.IFormField;
import com.meteorite.fxbase.ui.config.FxFormConfig;
import com.meteorite.fxbase.ui.config.FxFormFieldConfig;
import com.meteorite.fxbase.ui.event.FxLayoutEvent;
import com.meteorite.fxbase.ui.view.FxPane;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 表单面板
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class FxFormPane extends FxPane {
    protected FxFormConfig formConfig;
    protected List<IFormField> formFieldList;

    public FxFormPane(ILayoutConfig layoutConfig, boolean isShowTop) {
        super(layoutConfig, isShowTop);
    }

    public FxFormPane(ILayoutConfig layoutConfig) {
        this(layoutConfig, true);
    }

    public HBox createTop() {
        HBox box = new HBox(10);
        box.setPrefHeight(30);
        Region region = new Region();
        box.getChildren().add(region);
        HBox.setHgrow(region, Priority.ALWAYS);
        for (IActionConfig action : layoutConfig.getActionConfigs()) {
            Button button = new Button(action.getDisplayName());
            button.setId(action.getName());
            box.getChildren().add(button);
        }
        return box;
    }

    public GridPane createCenter() {
        this.formConfig = new FxFormConfig(layoutConfig);
        formFieldList = new ArrayList<>();

        GridPane gridPane = new GridPane();
//        gridPane.setGridLinesVisible(true);
        gridPane.setHgap(formConfig.getHgap());
        gridPane.setVgap(formConfig.getVgap());

        Label label;
        Region labelGap;
        IFormField formField;
        Region fieldGap;
        int idxRow = 0;
        int idxCol = 0;

        for (FxFormFieldConfig field : formConfig.getFxFormFields()) {
            if (!field.isDisplay()) { // 不显示
                continue;
            }

            formField = getFormField(field);
            formFieldList.add(formField);
            // 单行
            if (field.isSingleLine()) {
                idxRow++;
                label = new Label(field.getDisplayName());
                gridPane.add(label, 0, idxRow);

                labelGap = new Region();
                labelGap.setPrefWidth(formConfig.getLabelGap());
                gridPane.add(labelGap, 1, idxRow);

                formField.setLabel(label);
                gridPane.add(formField.getNode(), 2, idxRow, formConfig.getColCount() * 4 - 2, 1);

                GridPane.setHgrow(formField.getNode(), Priority.ALWAYS);
                idxCol = 0;
                idxRow++;

                continue;
            }

            label = new Label(field.getDisplayName());
            gridPane.add(label, idxCol++, idxRow);

            labelGap = new Region();
            labelGap.setPrefWidth(formConfig.getLabelGap());
            gridPane.add(labelGap, idxCol++, idxRow);

            formField.setLabel(label);
            gridPane.add(formField.getNode(), idxCol++, idxRow);

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

    private IFormField getFormField(FxFormFieldConfig field) {
        IFormField node;
        DisplayStyle displayStyle = field.getDisplayStyle();
        if (DisplayStyle.TEXT_AREA == displayStyle) {
            return new FxTextArea(field);
        } else if (DisplayStyle.PASSWORD == displayStyle) {
            return new FxPasswordField(field);
        } else if (DisplayStyle.COMBO_BOX == displayStyle || DisplayStyle.BOOLEAN == displayStyle) {
            return new FxComboBox(field);
        } else if (DisplayStyle.DATA_SOURCE == displayStyle) {
            return new FxDataSource(field);
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
                return new FxTextField(field);
            }
        }
        return node;
    }

    @Override
    public void registLayoutEvent() {
        for (IFormField field : formFieldList) {
            field.registLayoutEvent();
        }
    }

    @Override
    public void registLayoutConfigChangeEvent(FxLayoutEvent layoutEvent) {
        if (layoutEvent.getLayoutSource() instanceof FxFormPane) {
            FxFormPane formPane = (FxFormPane) layoutEvent.getLayoutSource();
        } else if(layoutEvent.getLayoutSource() instanceof IFormField) {
            final IFormField formField = (IFormField) layoutEvent.getLayoutSource();
            for (final IFormField field : formFieldList) {
                field.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        if (ConfigConst.FORM_FIELD_NAME.equals(field.getFormFieldConfig().getName())) {
                            formField.getFormFieldConfig().setName(newValue);
                        } else if (ConfigConst.FORM_FIELD_DISPLAY_NAME.equals(field.getFormFieldConfig().getName())) {
                            formField.getFormFieldConfig().setDisplayName(newValue);
                        } else if (ConfigConst.FORM_FIELD_WIDTH.equals(field.getFormFieldConfig().getName())) {
                            formField.getFormFieldConfig().setWidth(UNumber.toInt(newValue));
                        } else if (ConfigConst.FORM_FIELD_HEIGHT.equals(field.getFormFieldConfig().getName())) {
                            formField.getFormFieldConfig().setHeight(UNumber.toInt(newValue));
                        } else if (ConfigConst.FORM_FIELD_IS_DISPLAY.equals(field.getFormFieldConfig().getName())) {
                            formField.getFormFieldConfig().setDisplay(UString.toBoolean(newValue));
                        } else if (ConfigConst.FORM_FIELD_IS_SINGLE_LINE.equals(field.getFormFieldConfig().getName())) {
                            formField.getFormFieldConfig().setSingleLine(UString.toBoolean(newValue));
                        } else if (ConfigConst.FORM_FIELD_DISPLAY_STYLE.equals(field.getFormFieldConfig().getName())) {
                            formField.getFormFieldConfig().setDisplayStyle(DisplayStyle.getStyle(newValue));
                        } else if (ConfigConst.FORM_FIELD_DATA_TYPE.equals(field.getFormFieldConfig().getName())) {
                            formField.getFormFieldConfig().setDataType(MetaDataType.getDataType(newValue));
                        } else if (ConfigConst.FORM_FIELD_DICT_ID.equals(field.getFormFieldConfig().getName())) {
                            formField.getFormFieldConfig().setDict(newValue);
                        } else if (ConfigConst.FORM_FIELD_SORT_NUM.equals(field.getFormFieldConfig().getName())) {
                            formField.getFormFieldConfig().setSortNum(UNumber.toInt(newValue));
                        }
                    }
                });
            }
        }
    }
}

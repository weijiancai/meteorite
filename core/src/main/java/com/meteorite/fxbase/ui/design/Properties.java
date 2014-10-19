package com.meteorite.fxbase.ui.design;

import com.meteorite.core.dict.DictCategory;
import com.meteorite.core.dict.DictCode;
import com.meteorite.core.dict.DictManager;
import com.meteorite.core.dict.QueryModel;
import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.ui.layout.property.FormFieldProperty;
import com.meteorite.core.ui.model.ViewProperty;
import com.meteorite.core.util.UNumber;
import com.meteorite.fxbase.ui.component.form.BaseFormField;
import com.meteorite.fxbase.ui.view.MUDialog;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class Properties implements Initializable {
    @FXML
    private TextField nameTF;
    @FXML
    private TextField displayNameTF;
    @FXML
    private ChoiceBox<DictCode> displayStyleCB;
    @FXML
    private ChoiceBox<DictCode> typeCB;
    @FXML
    private ChoiceBox<DictCode> dictCB;
    @FXML
    private ChoiceBox<DictCode> queryModeCB;
    @FXML
    private TextField classAttrTF;
    @FXML
    private TextField groupNameTF;
    @FXML
    private CheckBox isGroupOpenCB;
    @FXML
    private TextField defaultValueTF;
    @FXML
    private CheckBox usePreValueCB;
    @FXML
    private CheckBox isDisplayCB;
    @FXML
    private CheckBox isReadonlyCB;
    @FXML
    private CheckBox isRequireCB;
    @FXML
    private CheckBox isSingleLineCB;
    @FXML
    private TextField colSpanTF;
    @FXML
    private TextField sortNumTF;
    @FXML
    private TextField widthTF;
    @FXML
    private TextField heightTF;

    private DictCategory displayStyleCategory = DictManager.getDict(DisplayStyle.class);
    private DictCategory queryModeCategory = DictManager.getDict(QueryModel.class);

    private FormFieldProperty config;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayStyleCB.getItems().setAll(displayStyleCategory.getCodeList());
        queryModeCB.getItems().setAll(queryModeCategory.getCodeList());
        dictCB.getItems().setAll(DictManager.getRoot().getCodeList());


        // 监听控件值变化
        displayNameTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                config.setDisplayName(newValue);
                persist("displayName", newValue);
            }
        });

        displayStyleCB.valueProperty().addListener(new ChangeListener<DictCode>() {

            @Override
            public void changed(ObservableValue<? extends DictCode> observable, DictCode oldValue, DictCode newValue) {
                config.setDisplayStyle(DisplayStyle.getStyle(newValue.getName()));
                persist("displayStyle", newValue.getName());
            }
        });

        dictCB.valueProperty().addListener(new ChangeListener<DictCode>() {

            @Override
            public void changed(ObservableValue<? extends DictCode> observable, DictCode oldValue, DictCode newValue) {
                config.setDict(DictManager.getDict(newValue.getId()));
                persist("dictId", newValue.getId());
            }
        });

        queryModeCB.valueProperty().addListener(new ChangeListener<DictCode>() {
            @Override
            public void changed(ObservableValue<? extends DictCode> observable, DictCode oldValue, DictCode newValue) {
                config.setQueryModel(QueryModel.convert(newValue.getName()));
                persist("queryModel", newValue.getName());
            }
        });

        widthTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                int width = UNumber.toInt(newValue);
                config.setWidth(width);
                persist("width", width + "");
            }
        });

        heightTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                int height = UNumber.toInt(newValue);
                config.setHeight(height);
                persist("height", height + "");
            }
        });

        sortNumTF.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                int sortNum = UNumber.toInt(newValue);
                config.setSortNum(sortNum);
                persist("sortNum", sortNum + "");
            }
        });


        isDisplayCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
                config.setDisplay(newValue);
                persist("isDisplay", newValue ? "T" : "F");
            }
        });

        isSingleLineCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                config.setSingleLine(newValue);
                persist("isSingleLine", newValue ? "T" : "F");
            }
        });

        isRequireCB.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                config.setRequire(newValue);
                persist("require", newValue ? "T" : "F");
            }
        });
    }

    private void persist(String propName, String value) {
        ViewProperty viewProperty = config.getPropertyByName(propName);
        if (viewProperty == null) {
            MUDialog.showInformation(String.format("属性名【%s】不存在！", propName));
            return;
        }
        if ((viewProperty.getValue() == null && value == null) || (viewProperty.getValue() != null && viewProperty.getValue().equals(value)) || (value != null && value.equals(viewProperty.getValue()))) {
            return;
        }
        viewProperty.setValue(value);

        // 保存到数据库
        try {
            viewProperty.persist();
        } catch (Exception e) {
            throw new RuntimeException("保存视图属性失败！", e);
        }
    }

    public void setColId(String colId) {
        nameTF.setText(colId);
    }

    public void setDisplayName(String displayName) {
        displayNameTF.setText(displayName);
    }

    public void setEditStyle(String editStyle) {
        if (editStyle == null) {
            displayStyleCB.getSelectionModel().select(0);
        }
    }

    public void setClassAttr(String classAttr) {
        classAttrTF.setText(classAttr);
    }

    public void setType(String type) {
        if (type == null) {
            displayStyleCB.getSelectionModel().select(0);
        }
    }

    public void setGroupName(String groupName) {
        groupNameTF.setText(groupName);
    }

    public void setIsGroupOpen(boolean isGroupOpen) {
        isGroupOpenCB.setSelected(isGroupOpen);
    }

    public void setDefaultValue(String defaultValue) {
        defaultValueTF.setText(defaultValue);
    }

    public void setUsePreValue(boolean usePreValue) {
        usePreValueCB.setSelected(usePreValue);
    }

    public void setIsDisplay(boolean isDisplay) {
        isDisplayCB.setSelected(isDisplay);
    }

    public void setIsReadonly(boolean isReadonly) {
        isReadonlyCB.setSelected(isReadonly);
    }

    public void setIsRequire(boolean isRequire) {
        isRequireCB.setSelected(isRequire);
    }

    public void setIsSingleLine(boolean isSingleLine) {
        isSingleLineCB.setSelected(isSingleLine);
    }

    public void setColSpan(String colSpan) {
        colSpanTF.setText(colSpan);
    }

    public void setSortNum(String sortNum) {
        sortNumTF.setText(sortNum);
    }

    public void setWidth(Integer width) {
        if (width != null) {
            widthTF.setText(width.toString());
        }
    }

    public void setHeight(Integer height) {
        if (height != null) {
            heightTF.setText(height.toString());
        }
    }

    public void setProperties(BaseFormField formField) {
        config = formField.getConfig();
        nameTF.setText(config.getName());
        displayNameTF.setText(config.getDisplayName());
        isSingleLineCB.setSelected(config.isSingleLine());
        isDisplayCB.setSelected(config.isDisplay());
        isRequireCB.setSelected(config.isRequire());
        widthTF.setText(config.getWidth() + "");
        heightTF.setText(config.getHeight() + "");
        displayStyleCB.setValue(displayStyleCategory.getDictCodeByName(config.getDisplayStyle().name()));
        sortNumTF.setText(config.getSortNum() + "");
        defaultValueTF.setText(config.getDefaultValue());
        if (config.getDict() != null) {
            dictCB.setValue(DictManager.getRoot().getDictCode(config.getDict().getId()));
        } else {
            dictCB.setValue(null);
        }
        if (config.getQueryModel() != null) {
            queryModeCB.setValue(queryModeCategory.getDictCodeByName(config.getQueryModel().name()));
        } else {
            queryModeCB.setValue(null);
        }
    }
}

package com.meteorite.fxbase.ui.component.form;

import com.meteorite.core.dict.FormType;
import com.meteorite.core.dict.QueryModel;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.layout.property.FormFieldProperty;
import com.meteorite.core.util.UString;
import com.meteorite.fxbase.BaseApp;
import com.meteorite.fxbase.MuEventHandler;
import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.component.FxLookDictPane;
import com.meteorite.fxbase.ui.view.MUDialog;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wei_jc
 * @since 1.0.0
 */
public abstract class BaseFormField extends HBox implements IValue, ICanQuery {
    protected FormFieldProperty config;
    protected boolean isAddQueryMode;
    private Hyperlink btnQueryModel;
    private String oldValue;
    private List<Condition> list = new ArrayList<>();
    private StringProperty value = new SimpleStringProperty();

    public BaseFormField(FormFieldProperty property) {
        this.config = property;
        this.isAddQueryMode = (config.getFormProperty().getFormType() == FormType.QUERY);
    }

    protected BaseFormField() {
    }

    protected abstract void initPrep();

    protected void init() {
        initPrep();

        this.setPrefWidth(config.getWidth());
        this.setAlignment(Pos.CENTER_LEFT);
        this.setSpacing(5);
        // 设置默认值
        String value = config.getValue();
        if (!"GUID()".equals(value)) {
            this.setValue(config.getValue());
        }

        initAfter();
    }

    protected void initAfter() {
        this.getChildren().addAll(getControls());

        if(isAddQueryMode) {
            btnQueryModel = new Hyperlink("=");
            btnQueryModel.setBorder(null);
            btnQueryModel.setMinWidth(30);
            btnQueryModel.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String text = btnQueryModel.getText();
                    switch (text) {
                        case "=":  setQueryModel("!="); break;
                        case "!=": setQueryModel(">"); break;
                        case ">": setQueryModel(">="); break;
                        case ">=": setQueryModel("<");  break;
                        case "<": setQueryModel("<="); break;
                        case "<=": setQueryModel("%%"); break;
                        case "%%": setQueryModel("*%"); break;
                        case "*%": setQueryModel("%*"); break;
                        case "%*": setQueryModel("=");
                    }
                }
            });

            // 查看查询模式上下文菜单
            MenuItem equal = new MenuItem("=");
            equal.setOnAction(new MuEventHandler<ActionEvent>() {
                @Override
                public void doHandler(ActionEvent event) throws Exception {
                    setQueryModel("=");
                }
            });
            MenuItem notEqual = new MenuItem("!=");
            notEqual.setOnAction(new MuEventHandler<ActionEvent>() {
                @Override
                public void doHandler(ActionEvent event) throws Exception{
                    setQueryModel("!=");
                }
            });
            MenuItem greater = new MenuItem(">");
            greater.setOnAction(new MuEventHandler<ActionEvent>() {
                @Override
                public void doHandler(ActionEvent event) throws Exception{
                    setQueryModel(">");
                }
            });
            MenuItem greaterEqual = new MenuItem(">=");
            greaterEqual.setOnAction(new MuEventHandler<ActionEvent>() {
                @Override
                public void doHandler(ActionEvent event) throws Exception {
                    setQueryModel(">=");
                }
            });
            MenuItem less = new MenuItem("<");
            less.setOnAction(new MuEventHandler<ActionEvent>() {
                @Override
                public void doHandler(ActionEvent event) throws Exception {
                    setQueryModel("<");
                }
            });
            MenuItem lessEqual = new MenuItem("<=");
            lessEqual.setOnAction(new MuEventHandler<ActionEvent>() {
                @Override
                public void doHandler(ActionEvent event) throws Exception {
                    setQueryModel("<=");
                }
            });
            MenuItem like = new MenuItem("%%");
            like.setOnAction(new MuEventHandler<ActionEvent>() {
                @Override
                public void doHandler(ActionEvent event) throws Exception {
                    setQueryModel("%%");
                }
            });
            MenuItem leftLike = new MenuItem("*%");
            leftLike.setOnAction(new MuEventHandler<ActionEvent>() {
                @Override
                public void doHandler(ActionEvent event) throws Exception {
                    setQueryModel("*%");
                }
            });
            MenuItem rightLike = new MenuItem("%*");
            rightLike.setOnAction(new MuEventHandler<ActionEvent>() {
                @Override
                public void doHandler(ActionEvent event) throws Exception {
                    setQueryModel("%*");
                }
            });
            ContextMenu contextMenu = new ContextMenu();
            contextMenu.getItems().addAll(equal, notEqual, greater, greaterEqual, less, lessEqual, like, leftLike, rightLike);
            btnQueryModel.setContextMenu(contextMenu);
            this.getChildren().add(btnQueryModel);
        }
    }

    private void setQueryModel(String queryModel) {
        btnQueryModel.setText(queryModel);
        switch (queryModel) {
            case "=": config.setQueryModel(QueryModel.EQUAL); break;
            case "!=": config.setQueryModel(QueryModel.NOT_EQUAL); break;
            case ">": config.setQueryModel(QueryModel.GREATER_THAN); break;
            case ">=": config.setQueryModel(QueryModel.GREATER_EQUAL); break;
            case "<": config.setQueryModel(QueryModel.LESS_THAN); break;
            case "<=": config.setQueryModel(QueryModel.LESS_EQUAL); break;
            case "%%": config.setQueryModel(QueryModel.LIKE); break;
            case "*%": config.setQueryModel(QueryModel.LEFT_LIKE); break;
            case "%*": config.setQueryModel(QueryModel.RIGHT_LIKE);
        }
    }

    @Override
    public List<Condition> getConditions() {
        list.clear();
        if (UString.isNotEmpty(value())) {
            list.add(new Condition(config.getColumnName(), config.getQueryModel(), value(), MetaDataType.STRING));
        }

        return list;
    }

    @Override
    public StringProperty valueProperty() {
        return value;
    }

    @Override
    public void setValue(String value) {
        valueProperty().set(value);
    }

    protected abstract Node[] getControls();

    public FormFieldProperty getConfig() {
        return config;
    }

    @Override
    public String getName() {
        return config.getName();
    }

    @Override
    public MetaField getMetaField() {
        return config.getMetaField();
    }

    @Override
    public String getDefaultValue() {
        return config.getValue();
    }
}

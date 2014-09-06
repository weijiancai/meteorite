package com.meteorite.fxbase.ui.component.table.column;

import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.datasource.db.object.DBColumn;
import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.ui.layout.property.TableFieldProperty;
import com.meteorite.core.util.UDate;
import com.meteorite.core.util.UObject;
import com.meteorite.fxbase.ui.component.table.cell.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

import java.util.Date;

/**
 * Base Table Column
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class BaseTableColumn extends TableColumn<DataMap, String> {
    protected TableFieldProperty property;

    public BaseTableColumn(final TableFieldProperty property) {
        this.property = property;
//        this.setText(property.getDisplayName());
//        this.setPrefWidth(property.getWidth());
        this.setMinWidth(60);
        this.textProperty().bindBidirectional(property.displayNameProperty());
        this.prefWidthProperty().bind(property.widthProperty());
        this.visibleProperty().bind(property.displayProperty());
        this.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                property.setWidth(newValue.intValue());
            }
        });

        this.setCellValueFactory(new Callback<CellDataFeatures<DataMap, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DataMap, String> param) {
                Object obj = param.getValue().get(property.getName());
                if (obj instanceof Date) {
                    return new SimpleStringProperty(UDate.dateToString((Date)obj, "yyyy-MM-dd HH:mm:ss"));
                }
                return new SimpleStringProperty(UObject.toString(obj));
            }
        });

        this.setCellFactory(new Callback<TableColumn<DataMap, String>, TableCell<DataMap, String>>() {
            @Override
            public TableCell<DataMap, String> call(TableColumn<DataMap, String> param) {
                return getTableCell(param);
            }
        });


        /*if (MetaDataType.BOOLEAN == property.getDataType()) {
            this.setCellFactory(new Callback<TableColumn<DataMap, String>, TableCell<DataMap, String>>() {
                @Override
                public TableCell<DataMap, String> call(TableColumn<DataMap, String> param) {
                    return new BoolTableCell(param, property);
                }
            });
        } else {
            if(DisplayStyle.COMBO_BOX == property.getDisplayStyle()) {
                this.setCellFactory(new Callback<TableColumn<DataMap, String>, TableCell<DataMap, String>>() {
                    @Override
                    public TableCell<DataMap, String> call(TableColumn<DataMap, String> param) {
                        return new DictTableCell(param, property);
                    }
                });
            }
            else {
                this.setCellFactory(new Callback<TableColumn<DataMap, String>, TableCell<DataMap, String>>() {
                    @Override
                    public TableCell<DataMap, String> call(TableColumn<DataMap, String> param) {
                        return new TextTableCell(param, property);
                    }
                });
            }
        }*/
    }

    private TableCell<DataMap, String> getTableCell(TableColumn<DataMap, String> param) {
        if (MetaDataType.BOOLEAN == property.getDataType()) {
            return new BoolTableCell(param, property);
        }

        if(DisplayStyle.COMBO_BOX == property.getDisplayStyle()) {
            return new DictTableCell(param, property);
        }

        if (property.getMetaField().isFk()) {
            return new HyperlinkTableCell(param, property);
        }

        return new TextTableCell(param, property);
    }

    public TableFieldProperty getProperty() {
        return property;
    }
}

package com.meteorite.fxbase.ui.component.table.cell;

import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.datasource.QueryBuilder;
import com.meteorite.core.datasource.db.QueryResult;
import com.meteorite.core.dict.FormType;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.ViewManager;
import com.meteorite.core.ui.layout.property.FormProperty;
import com.meteorite.core.ui.layout.property.TableFieldProperty;
import com.meteorite.core.util.UString;
import com.meteorite.fxbase.MuEventHandler;
import com.meteorite.fxbase.ui.view.MUDialog;
import com.meteorite.fxbase.ui.view.MUForm;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableColumn;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Callback;

/**
 * 超链接Table Cell
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class HyperlinkTableCell extends BaseTableCell {
    private Hyperlink hyperlink;
    private boolean isInit;
    private Paint originalTextFile;

    public HyperlinkTableCell(final TableColumn<DataMap, String> column, final TableFieldProperty prop) {
        super(column, prop);

        hyperlink = new Hyperlink("");
        this.setGraphic(hyperlink);

        hyperlink.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                Meta meta = prop.getMetaField().getMeta();
                MetaField refField = prop.getMetaField().getRefField();
                Meta refMeta = meta.getRefMeta(prop.getMetaField().getId());
                QueryBuilder queryBuilder = QueryBuilder.create(refMeta).add(refField.getOriginalName(), hyperlink.getText());
                QueryResult<DataMap> queryResult = refMeta.query(queryBuilder);

                FormProperty formProperty = new FormProperty(ViewManager.getViewByName(refMeta.getName() + "FormView"));
                formProperty.setFormType(FormType.READONLY);
                MUForm form = new MUForm(formProperty);
                form.setValues(queryResult.getRows().get(0));
                MUDialog.showCustomDialog(null, refMeta.getDisplayName(), form, new Callback<Void, Void>() {
                    @Override
                    public Void call(Void param) {
                        return null;
                    }
                });
            }
        });
    }

    private void init() {
        this.getTableRow().selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    if (originalTextFile == null) {
                        originalTextFile = hyperlink.getTextFill();
                    }
                    hyperlink.setTextFill(Color.WHITE);
                } else {
                    hyperlink.setTextFill(originalTextFile);
                }
            }
        });
        isInit = true;
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (!isInit) {
            init();
        }
        if (UString.isNotEmpty(item)) {
            hyperlink.setText(item);
            this.setGraphic(hyperlink);
        } else {
            this.setGraphic(null);
        }
    }
}

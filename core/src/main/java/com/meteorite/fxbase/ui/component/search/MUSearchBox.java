package com.meteorite.fxbase.ui.component.search;

import com.meteorite.core.datasource.db.DBManager;
import com.meteorite.core.datasource.db.object.DBObject;
import com.meteorite.core.util.UString;
import com.meteorite.fxbase.MuEventHandler;
import com.meteorite.fxbase.ui.view.MUTabsDesktop;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 搜索框
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUSearchBox extends VBox {
    private TextField textField;
    private final Button clearButton = new Button();
    private final Region innerBackground = new Region();
    private final Region icon = new Region();

    private ListView<DBObject> listView = new ListView<>(FXCollections.<DBObject>observableArrayList());

    public MUSearchBox(final MUTabsDesktop tabsDesktop) {
        textField = new TextField() {
            @Override protected void layoutChildren() {
                super.layoutChildren();
                if (clearButton.getParent() != this) getChildren().add(clearButton);
                if (innerBackground.getParent() != this) getChildren().add(0,innerBackground);
                if (icon.getParent() != this) getChildren().add(icon);
                innerBackground.setLayoutX(0);
                innerBackground.setLayoutY(0);
                innerBackground.resize(getWidth(), getHeight());
                icon.setLayoutX(0);
                icon.setLayoutY(0);
                icon.resize(35,30);
                clearButton.setLayoutX(getWidth()-30);
                clearButton.setLayoutY(0);
                clearButton.resize(30, 30);
            }
        };

        textField.getStyleClass().addAll("search-box");
        textField.setMaxWidth(250);
        icon.getStyleClass().setAll("search-box-icon");
        innerBackground.getStyleClass().setAll("search-box-inner");
        textField.setPromptText("Search");
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                clearButton.setVisible(newValue.length() > 0);
                updateResults(newValue);
            }
        });
        textField.setPrefHeight(30);
        clearButton.getStyleClass().setAll("search-clear-button");
        clearButton.setCursor(Cursor.DEFAULT);
        clearButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent t) {
                textField.setText("");
            }
        });
        clearButton.setVisible(false);
        clearButton.setManaged(false);
        innerBackground.setManaged(false);
        icon.setManaged(false);

        textField.addEventFilter(KeyEvent.ANY, new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent t) {
                if (t.getCode() == KeyCode.DOWN
                        || t.getCode() == KeyCode.UP
                        || t.getCode() == KeyCode.PAGE_DOWN
                        || (t.getCode() == KeyCode.HOME && (t.isControlDown() || t.isMetaDown()))
                        || (t.getCode() == KeyCode.END && (t.isControlDown() || t.isMetaDown()))
                        || t.getCode() == KeyCode.PAGE_UP) {
                    listView.requestFocus();
                    listView.getSelectionModel().select(0);
                    t.consume();
                } else if (t.getCode() == KeyCode.ENTER) {
                    t.consume();
                    if (t.getEventType() == KeyEvent.KEY_PRESSED) {

                    }
                }
            }
        });

        listView.setVisible(false);
        listView.setCellFactory(new Callback<ListView<DBObject>, ListCell<DBObject>>() {
            @Override
            public ListCell<DBObject> call(ListView<DBObject> param) {
                return new DBSearchResultListCell();
            }
        });
        listView.setOnMouseClicked(new MuEventHandler<MouseEvent>() {
            @Override
            public void doHandler(MouseEvent event) throws Exception {
                if (event.getClickCount() == 2) {
                    tabsDesktop.hideSearchBox();
                    tabsDesktop.openTab(listView.getSelectionModel().getSelectedItem());
                }
            }
        });
        listView.setOnKeyPressed(new MuEventHandler<KeyEvent>() {
            @Override
            public void doHandler(KeyEvent event) throws Exception {
                if (event.getCode() == KeyCode.ENTER) {
                    tabsDesktop.hideSearchBox();
                    tabsDesktop.openTab(listView.getSelectionModel().getSelectedItem());
                }
            }
        });
        this.setMinWidth(700);

        this.getChildren().addAll(textField, listView);
    }

    private void updateResults(String searchText) {
        if (UString.isEmpty(searchText)) {
            listView.setVisible(false);
            return;
        }

        listView.getItems().clear();
        List<DBObject> result = new ArrayList<>();
        for (DBObject object : DBManager.getCache().getAllDBObject()) {
            if (object.getName().toLowerCase().contains(searchText.toLowerCase())) {
                result.add(object);
            }
        }
        // 排序
        Collections.sort(result, new Comparator<DBObject>() {
            @Override
            public int compare(DBObject o1, DBObject o2) {
                int i = o1.getObjectType().name().compareTo(o2.getObjectType().name());
                if(i != 0) {
                    return i;
                }
                return o1.getName().compareTo(o2.getName());
            }
        });

        if (result.size() > 0) {
            listView.getItems().addAll(result);
            listView.setVisible(true);
        } else {
            listView.setVisible(false);
        }
    }

    public void reset() {
        textField.setText("");
        listView.setVisible(false);
        textField.requestFocus();
    }

    class DBSearchResultListCell extends ListCell<DBObject> {
        private HBox box;
        private ImageView icon;
        private Text searchPrepText;
        private Text searchText;
        private Text searchAfterText;
        private Label fullNameText;
        private Label commentText;
        private Text typeText;
        private Tooltip fullNameTooltip;
        private Tooltip commentTooltip;

        public DBSearchResultListCell() {
            box = new HBox();
            icon = new ImageView();
            searchPrepText = new Text();
            searchText = new Text();
            searchAfterText = new Text();
            fullNameText = new Label();
            commentText = new Label();
            typeText = new Text();
            fullNameTooltip = new Tooltip();
            commentTooltip = new Tooltip();

            fullNameTooltip.setFont(Font.font(15));
            commentTooltip.setFont(Font.font(15));
            Tooltip.install(fullNameText, fullNameTooltip);
            Tooltip.install(commentText, commentTooltip);

            HBox searchTextBox = new HBox();
            searchTextBox.getChildren().add(searchText);
            searchText.setFill(Color.BLUE);
            searchTextBox.setStyle("-fx-background-color: #ffff00");
            HBox commentBox = new HBox();
            commentBox.getChildren().addAll(fullNameText, commentText);
            commentBox.setMaxWidth(400);
            fullNameText.setTextFill(Color.GREY);
            fullNameText.setFont(Font.font(11));
            commentText.setFont(Font.font(11));

            typeText.setFont(Font.font(null, FontWeight.BOLD, 10));

            Region region = new Region();
            HBox.setHgrow(region, Priority.ALWAYS);
            box.getChildren().addAll(icon, searchPrepText, searchTextBox, searchAfterText, commentBox, region, typeText);
        }

        @Override
        protected void updateItem(DBObject object, boolean empty) {
            super.updateItem(object, empty);
            if (object != null) {
                String searchStr = textField.getText();
                if (object.getIcon() != null) {
                    icon.setImage(new Image(getClass().getResourceAsStream(object.getIcon())));
                }
                int idx = object.getName().toLowerCase().indexOf(searchStr.toLowerCase());
                if (idx > -1) {
                    searchPrepText.setText(" " + object.getName().substring(0, idx));
                    searchText.setText(object.getName().substring(idx, idx + searchStr.length()));
                    searchAfterText.setText(object.getName().substring(idx + searchStr.length()) + " ");
                } else {
                    return;
                }

                fullNameText.setText(String.format("(%s)", object.getFullName()));
                fullNameTooltip.setText(object.getFullName());
                if (UString.isNotEmpty(object.getComment())) {
                    commentText.setText(String.format(" - %s", object.getComment()));
                    commentTooltip.setText(object.getComment());
                }

                if (object.getObjectType() != null) {
                    typeText.setText(object.getObjectType().name());
                }

                this.setGraphic(box);
            } else {
                this.setGraphic(null);
            }
        }
    }
}

package com.meteorite.fxbase.ui.component.tree;

import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.observer.EventData;
import com.meteorite.core.observer.Observer;
import com.meteorite.core.observer.Subject;
import com.meteorite.core.util.UString;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.lang.ref.WeakReference;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class BaseTreeCell extends TreeCell<ITreeNode> {
    private HBox hbox;
    private Label mainLabel;
    private Label presentableLabel;
    private ContextMenu contextMenu = new ContextMenu();

    private WeakReference<TreeItem<ITreeNode>> treeItemRef;

    private InvalidationListener treeItemGraphicListener = new InvalidationListener() {
        @Override
        public void invalidated(Observable observable) {
            updateDisplay(getItem(), isEmpty());
        }
    };

    private WeakInvalidationListener weakTreeItemGraphicListener = new WeakInvalidationListener(treeItemGraphicListener);

    public BaseTreeCell() {
        InvalidationListener treeItemListener = new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                TreeItem<ITreeNode> oldTreeItem = treeItemRef == null ? null : treeItemRef.get();
                if (oldTreeItem != null) {
                    oldTreeItem.graphicProperty().removeListener(weakTreeItemGraphicListener);
                }

                TreeItem<ITreeNode> newTreeItem = getTreeItem();
                if (newTreeItem != null) {
                    newTreeItem.graphicProperty().addListener(weakTreeItemGraphicListener);
                    treeItemRef = new WeakReference<TreeItem<ITreeNode>>(newTreeItem);
                }
            }
        };
        WeakInvalidationListener weakTreeItemListener = new WeakInvalidationListener(treeItemListener);
        treeItemProperty().addListener(weakTreeItemListener);
        if (getTreeItem() != null) {
            getTreeItem().graphicProperty().addListener(weakTreeItemGraphicListener);
        }
    }

    public void addContextMenu(MenuItem item) {
        contextMenu.getItems().add(item);
    }

    @Override
    protected void updateItem(ITreeNode item, boolean empty) {
        super.updateItem(item, empty);
        updateDisplay(item, empty);
    }

    void updateDisplay(ITreeNode item, boolean empty) {
        if (item == null || empty) {
            hbox = null;
            mainLabel = null;
            setText(null);
            setGraphic(null);
        } else {
            if (hbox == null) {
                hbox = new HBox(3);
                hbox.setAlignment(Pos.CENTER_LEFT);
                mainLabel = new Label();
                presentableLabel = new Label();
                presentableLabel.setFont(Font.font(11));
                presentableLabel.setTextFill(Color.DARKRED);
                presentableLabel.setAlignment(Pos.BOTTOM_LEFT);
                hbox.getChildren().addAll(mainLabel, presentableLabel);

                setGraphic(hbox);
                // 注册监听
                Subject<EventData> presentableTextSubject = item.getPresentableTextSubject();
                if (presentableTextSubject != null) {
                    presentableTextSubject.registerObserver(new Observer<EventData>() {
                        @Override
                        public void update(EventData data) {
                            presentableLabel.setText(data.getStrData());
                        }
                    });
                }
            }
            mainLabel.setText(item.getDisplayName());
            presentableLabel.setText(item.getPresentableText());
            String iconPath = item.getIcon();
            if (UString.isNotEmpty(iconPath)) {
                Node node = new ImageView(new Image(getClass().getResourceAsStream(iconPath)));
                mainLabel.setGraphic(node);
            }
        }
    }
}

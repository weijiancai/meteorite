package com.metaui.dbtool;

import com.meteorite.core.ITree;
import com.meteorite.core.datasource.ResourceTreeAdapter;
import com.meteorite.core.datasource.ResourceType;
import com.meteorite.core.datasource.VirtualResource;
import com.meteorite.core.datasource.db.DBIcons;
import com.meteorite.core.datasource.db.object.DBObject;
import com.meteorite.core.datasource.db.object.enums.DBObjectType;
import com.meteorite.core.model.ITreeNode;
import com.meteorite.fxbase.MuEventHandler;
import com.meteorite.fxbase.ui.component.tree.BaseTreeCell;
import com.meteorite.fxbase.ui.component.tree.MUTreeItem;
import com.meteorite.fxbase.ui.view.MUTree;
import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库导航树单元
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class DBNavTreeCell extends BaseTreeCell {
    public DBNavTreeCell() {

    }

    @Override
    public void updateItem(ITreeNode item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            /*DBObject dbObj = (DBObject) item;
            if (dbObj.getObjectType() == DBObjectType.VIEW) {
                MenuItem menuItem = new MenuItem("新增视图");
                addContextMenu(menuItem);
            }*/
            ResourceTreeAdapter adapter = (ResourceTreeAdapter) item;
            VirtualResource resource = adapter.getResource();
            ResourceType type = resource.getResourceType();

            if (DBIcons.DBO_VIEWS.equals(type.getIcon())) {
                MenuItem menuItem = new MenuItem("新增视图");
                addContextMenu(menuItem);
            } else if (DBIcons.DBO_TABLES.equals(type.getIcon())) {
                MenuItem menuItem = new MenuItem("刷新");
                menuItem.setOnAction(new MuEventHandler<ActionEvent>() {
                    @Override
                    public void doHandler(ActionEvent event) throws Exception {
                        List<ITreeNode> list = getItem().getChildren();
                        List<TreeItem<ITreeNode>> treeItems = new ArrayList<TreeItem<ITreeNode>>();
                        for (ITreeNode node : list) {
                            treeItems.add(new MUTreeItem((MUTree)getTreeView(), node));
                        }
                        getTreeItem().getChildren().setAll(treeItems);
                    }
                });
                addContextMenu(menuItem);
            }
        }
    }
}

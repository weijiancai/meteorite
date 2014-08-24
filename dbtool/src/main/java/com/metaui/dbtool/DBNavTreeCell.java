package com.metaui.dbtool;

import com.meteorite.core.datasource.ResourceTreeAdapter;
import com.meteorite.core.datasource.ResourceType;
import com.meteorite.core.datasource.VirtualResource;
import com.meteorite.core.datasource.db.object.DBObject;
import com.meteorite.core.datasource.db.object.enums.DBObjectType;
import com.meteorite.core.model.ITreeNode;
import com.meteorite.fxbase.ui.component.tree.BaseTreeCell;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;

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

            if (type.getName().equals(DBObjectType.VIEW.name())) {
                MenuItem menuItem = new MenuItem("新增视图");
                addContextMenu(menuItem);
            }

        }
    }
}

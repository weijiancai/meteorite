package com.meteorite.fxbase.ui.win;

import com.meteorite.core.meta.MetaManager;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.project.NavMenu;
import com.meteorite.core.project.NavMenuTreeNode;
import com.meteorite.core.project.ProjectDefine;
import com.meteorite.fxbase.ui.IValue;
import javafx.scene.control.TreeItem;

import java.util.Map;

/**
 * 导航树菜单
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUNavMenuWin extends MUTreeTableWin {
    private ProjectDefine project;
    private NavMenuTreeNode rootNavMenu;

    public MUNavMenuWin(ProjectDefine project) {
        this.project = project;
        rootNavMenu = new NavMenuTreeNode(project.getRootNavMenu());

        initUI();
    }

    @Override
    public ITreeNode getRootTreeNode() {
        return rootNavMenu;
    }

    @Override
    public Meta getMainMeta() {
        return MetaManager.getMeta("NavMenu");
    }

    @Override
    public Meta getItemMeta() {
        return null;
    }

    @Override
    public String getParentIdColName() {
        return "pic";
    }

    @Override
    public String getItemFkColName() {
        return null;
    }

    @Override
    public String getItemFkDbName() {
        return null;
    }

    @Override
    public TreeItem<ITreeNode> createNewTreeNode(Map<String, IValue> valueMap) {
        NavMenu menu = new NavMenu();
        menu.setId(valueMap.get("id").value());
        menu.setName(valueMap.get("name").value());
        menu.setDisplayName(valueMap.get("displayName").value());
        NavMenuTreeNode node = new NavMenuTreeNode(menu);
        return new TreeItem<ITreeNode>(node);
    }
}

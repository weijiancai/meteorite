package com.meteorite.fxbase.ui.win;

import com.meteorite.core.dict.DictCategory;
import com.meteorite.core.dict.DictManager;
import com.meteorite.core.dict.DictTreeNode;
import com.meteorite.core.meta.MetaManager;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.model.impl.BaseTreeNode;
import com.meteorite.core.util.UString;
import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.component.tree.MUTreeItem;
import javafx.scene.control.TreeItem;

import java.util.Map;

/**
 * 数据字典窗口
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUDictWin extends MUTreeTableWin {
    private DictTreeNode root;

    public MUDictWin() {
        root = new DictTreeNode(DictManager.getRoot());
        initUI();
        setSelectItem(1);
    }

    @Override
    public ITreeNode getRootTreeNode() {
        return root;
    }

    @Override
    public Meta getMainMeta() {
        return MetaManager.getMeta("Category");
    }

    @Override
    public Meta getItemMeta() {
        return MetaManager.getMeta("Code");
    }

    @Override
    public String getParentIdColName() {
        return "pid";
    }

    @Override
    public String getItemFkColName() {
        return "categoryId";
    }

    @Override
    public String getItemFkDbName() {
        return "category_id";
    }

    @Override
    public ITreeNode createNewTreeNode(Map<String, IValue> valueMap) {
        DictCategory category = new DictCategory();
        category.setId(valueMap.get("id").value());
        category.setName(valueMap.get("name").value());
        category.setSystem(UString.toBoolean(valueMap.get("isSystem").value()));
        return new DictTreeNode(category);
    }
}

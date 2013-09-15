package com.meteorite.core.model;

import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.model.impl.BaseTreeModel;
import com.meteorite.core.model.impl.BaseTreeNode;

import java.util.HashMap;
import java.util.Map;

/**
 * 模型管理器
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class ModelManager {
    private static ModelManager instance;
    private Map<String, ITreeModel> treeModelMap = new HashMap<String, ITreeModel>();

    private ModelManager() {
        loadTreeModel();
    }

    /**
     * 加载树形模型数据
     */
    private void loadTreeModel() {

    }

    public static ModelManager getInstance() {
        if (instance == null) {
            instance = new ModelManager();
        }
        return instance;
    }

    public ITreeModel getTreeModel(String modelName) {
        return treeModelMap.get(modelName);
    }

    public void addTreeModel(Meta meta) {
        BaseTreeModel treeModel = new BaseTreeModel();
        treeModel.setName(meta.getName() + "Tree");

        BaseTreeNode root = new BaseTreeNode();

        treeModel.setRoot(root);
    }
}

package com.meteorite.fxbase.ui.win;

import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.model.impl.BaseTreeNode;
import com.meteorite.core.project.ProjectDefine;
import com.meteorite.core.project.ProjectManager;
import com.meteorite.core.ui.ViewManager;
import com.meteorite.core.ui.model.View;
import com.meteorite.fxbase.ui.view.MUTable;
import com.meteorite.fxbase.ui.view.MuCrud;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;

/**
 * 项目管理窗口
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUProjectWin extends BorderPane {
    private MuCrud projectCrud;
    private Button btnDesign;
    private TreeItem<ITreeNode> projectTree;

    public MUProjectWin() {
        initUI();
    }

    private void initUI() {
        this.setCenter(new MUTable(ViewManager.getViewByName("ProjectDefineCrudView")));
        // 初始化项目树
        initProjectTree();
    }

    private void initProjectTree() {
        BaseTreeNode projectNode = new BaseTreeNode("项目管理");
        projectNode.setId("Project");
        projectNode.setView(View.createNodeView(this));

        projectTree = new TreeItem<ITreeNode>(projectNode);

        for (ProjectDefine project : ProjectManager.getProjects()) {
            TreeItem<ITreeNode> node = new TreeItem<ITreeNode>(new BaseTreeNode(project.getDisplayName()));
            BaseTreeNode navTree = new BaseTreeNode("导航菜单");
            navTree.setId(project.getName() + "_navMenu");
            navTree.setPresentableText(project.getDisplayName() + " - 导航菜单");
            View view = new View();
            view.setNode(new MUNavMenuWin(project));
            navTree.setView(view);
            TreeItem<ITreeNode> navTreeNode = new TreeItem<ITreeNode>(navTree);
            node.getChildren().add(navTreeNode);
            projectTree.getChildren().add(node);
        }
    }

    public TreeItem<ITreeNode> getProjectTree() {
        return projectTree;
    }
}

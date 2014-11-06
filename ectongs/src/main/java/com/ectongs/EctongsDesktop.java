package com.ectongs;

import com.ectongs.view.DsConfigView;
import com.meteorite.core.datasource.DataSource;
import com.meteorite.core.datasource.DataSourceManager;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.model.impl.BaseTreeNode;
import com.meteorite.core.observer.Observer;
import com.meteorite.core.ui.ViewManager;
import com.meteorite.core.ui.model.View;
import com.meteorite.core.util.UString;
import com.meteorite.fxbase.ui.component.tree.MUTreeItem;
import com.meteorite.fxbase.ui.view.MUTabsDesktop;
import com.meteorite.fxbase.ui.view.MuCrud;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.control.Tab;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 易诚通桌面
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class EctongsDesktop extends MUTabsDesktop {
    private static final Logger log = Logger.getLogger(EctongsDesktop.class);


    public EctongsDesktop() {

    }

    @Override
    public void initAfter() {
        BaseTreeNode dsConfigTreeNode = new BaseTreeNode("DS配置");
        dsConfigTreeNode.setId("DsConfigSetting");

        View dsConfigView = new View();
        dsConfigView.setNode(new DsConfigView());
        dsConfigTreeNode.setView(dsConfigView);

        tree.getRoot().getChildren().add(new MUTreeItem(dsConfigTreeNode));
    }

    /*public void openTab(ITreeNode node) {
        if (node == null) {
            return;
        }

        // 展开数节点
        tree.expandTo(node);

        // 打开视图
        View view = node.getView();
        if (view != null) {
            String text = node.getName();
            Tab tab = tabCache.get(node.getId());
            if (tab == null) {
                tab = new Tab(text);
                tab.setId(node.getId());
                String displayName = node.getDisplayName();
                if (UString.isEmpty(displayName)) {
                    displayName = node.getName();
                }
                tab.setText(displayName);
                tab.setContent(new MuCrud(view));

                tabPane.getTabs().add(tab);
                tabCache.put(node.getId(), tab);
            }
            tabPane.getSelectionModel().select(tab);
        }
    }*/

    @Override
    protected List<Tab> getGenCodeTabs(Meta meta) {
        List<Tab> result =  super.getGenCodeTabs(meta);

        //

        return result;
    }
}

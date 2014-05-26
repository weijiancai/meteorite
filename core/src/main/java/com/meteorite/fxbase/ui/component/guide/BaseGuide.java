package com.meteorite.fxbase.ui.component.guide;

import com.meteorite.fxbase.ui.component.BasePane;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * MetaUI 向导控件
 *
 * @author wei_jc
 * @since 1.0.0
 */
public abstract class BaseGuide extends BasePane {
    private Label labelTitle;
    private Button btnPrev; // 前一页
    private Button btnNext; // 下一页
    private Button btnFinish; // 完成
    private Button btnCancel; // 取消

    private StackPane stackPane;
    private List<GuideModel> modelList;
    private int curPage;

    /*protected BaseGuide(List<GuideModel> modelList) {
        this.modelList = modelList;
    }*/

    @Override
    public void initUI() {
        super.initUI();
        modelList = getModelList();
        curPage = 0;

        // 顶部标题
        labelTitle = new Label(modelList.get(0).getTitle());
        this.setTop(labelTitle);

        // 中央面板
        stackPane = new StackPane();

        for (int i = modelList.size() - 1; i >= 0; i--){
            GuideModel model = modelList.get(i);
            stackPane.getChildren().add(model.getContent());
        }
        this.setCenter(stackPane);

        // 前一页
        btnPrev = new Button("< 前一页");
        btnPrev.disableProperty().bind(Bindings.createBooleanBinding(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return curPage == 0;
            }
        }));
        // 下一页
        btnNext = new Button("下一页 >");
        btnNext.disableProperty().bind(Bindings.createBooleanBinding(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return curPage < modelList.size() - 1;
            }
        }));
        // 完成
        btnFinish = new Button("完成");
        btnFinish.disableProperty().bind(Bindings.createBooleanBinding(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return curPage < modelList.size() - 1;
            }
        }));
        // 取消
        btnCancel = new Button("取消");

        // 底部按钮
        HBox bottomBox = new HBox(15);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.getChildren().addAll(btnPrev, btnNext, btnFinish, btnCancel);
        this.setBottom(bottomBox);
    }

    public abstract List<GuideModel> getModelList();
}

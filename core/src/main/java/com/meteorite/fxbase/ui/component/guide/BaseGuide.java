package com.meteorite.fxbase.ui.component.guide;

import com.meteorite.fxbase.MuEventHandler;
import com.meteorite.fxbase.ui.ICanInput;
import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.component.BasePane;
import com.meteorite.fxbase.ui.component.pane.MUStackPane;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private MUStackPane stackPane;
    private List<GuideModel> modelList;
    private int curPage;

    /*protected BaseGuide(List<GuideModel> modelList) {
        this.modelList = modelList;
    }*/

    @Override
    public void initUI() {
        super.initUI();
        modelList = getModelList();
        curPage = 1;

        // 顶部标题
        labelTitle = new Label(modelList.get(0).getTitle());
        this.setTop(labelTitle);

        // 中央面板
        stackPane = new MUStackPane();

        for (GuideModel model : modelList) {
            stackPane.add(model.getContent());

        }
        this.setCenter(stackPane);

        // 前一页
        btnPrev = new Button("< 前一页");
        /*btnPrev.disableProperty().bind(Bindings.createBooleanBinding(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return curPage == 0;
            }
        }));*/
        btnPrev.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                nextPage(--curPage);
            }
        });
        // 下一页
        btnNext = new Button("下一页 >");
        /*btnNext.disableProperty().bind(Bindings.createBooleanBinding(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return curPage <= modelList.size() - 1;
            }
        }));*/
        btnNext.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                nextPage(++curPage);
            }
        });
        // 完成
        btnFinish = new Button("完成");
        /*btnFinish.disableProperty().bind(Bindings.createBooleanBinding(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return curPage < modelList.size() - 1;
            }
        }));*/
        btnFinish.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                doFinish(getValueMap());
            }
        });
        // 取消
        btnCancel = new Button("取消");

        // 底部按钮
        HBox bottomBox = new HBox(15);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(5));
        bottomBox.getChildren().addAll(btnPrev, btnNext, btnFinish, btnCancel);
        this.setBottom(bottomBox);

        nextPage(curPage);
    }

    private void nextPage(int page) {
        if(page < 1) {
            page = 1;
        }
        if(page > modelList.size()) {
            page = modelList.size();
        }
        curPage = page;

        if(modelList.size() == 1) {
            btnPrev.setDisable(true);
            btnNext.setDisable(true);
            btnFinish.setDisable(false);

            return;
        }

        if(page <= 1) {
            btnPrev.setDisable(true);
            btnNext.setDisable(false);
            btnFinish.setDisable(false);
        } else if(page >= modelList.size()) {
            btnPrev.setDisable(false);
            btnNext.setDisable(true);
            btnFinish.setDisable(false);
        } else {
            btnPrev.setDisable(false);
            btnNext.setDisable(false);
            btnFinish.setDisable(false);
        }

        stackPane.show(page - 1);
        labelTitle.setText(modelList.get(page - 1).getTitle());
    }

    public abstract List<GuideModel> getModelList();

    public abstract void doFinish(Map<String, String> param) throws FileNotFoundException, SQLException;

    public Map<String, String> getValueMap() {
        Map<String, String> result = new HashMap<>();
        for (GuideModel model : modelList) {
            ICanInput value = (ICanInput) model.getContent();
            result.put(value.getName(), value.getValueString());
        }

        return result;
    }
}

package com.meteorite.fxbase.ui.component.guide;

import javafx.scene.Node;

/**
 * 向导模型类
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class GuideModel {
    private String title;
    private Node content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Node getContent() {
        return content;
    }

    public void setContent(Node content) {
        this.content = content;
    }
}

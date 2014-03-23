package com.meteorite.core.ui.model;

import com.meteorite.core.meta.model.Meta;

import java.util.List;

/**
 * 视图布局
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class ViewLayout {
    /** 视图布局ID */
    private String id;

    private View view;
    private Layout layout;
    private Meta meta;
    private List<ViewConfig> configs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<ViewConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(List<ViewConfig> configs) {
        this.configs = configs;
    }
}

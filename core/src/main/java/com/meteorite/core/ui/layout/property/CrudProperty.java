package com.meteorite.core.ui.layout.property;

import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.ui.ViewManager;
import com.meteorite.core.ui.layout.LayoutManager;
import com.meteorite.core.ui.layout.PropertyNames;
import com.meteorite.core.ui.model.View;
import com.meteorite.core.ui.model.ViewProperty;
import com.meteorite.core.util.UUIDUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class CrudProperty implements PropertyNames {
    private View formView;
    private View tableView;
    private View queryView;
    private Meta meta;

    public CrudProperty(View view) {
        formView = ViewManager.getViewById(view.getStringProperty(CRUD.FORM));
        tableView = ViewManager.getViewById(view.getStringProperty(CRUD.TABLE));
        queryView = ViewManager.getViewById(view.getStringProperty(CRUD.QUERY));
        meta = view.getMeta();
    }

    public View getFormView() {
        return formView;
    }

    public void setFormView(View formView) {
        this.formView = formView;
    }

    public View getTableView() {
        return tableView;
    }

    public void setTableView(View tableView) {
        this.tableView = tableView;
    }

    public View getQueryView() {
        return queryView;
    }

    public void setQueryView(View queryView) {
        this.queryView = queryView;
    }

    public Meta getMeta() {
        return meta;
    }

    public static View createCrudView(Meta meta, View formView, View tableView, View queryView) {
        View view = new View();
        view.setId(UUIDUtil.getUUID());
        view.setName(meta.getName() + "CrudView");
        view.setDisplayName(meta.getDisplayName() + "CRUD视图");
        view.setValid(true);
        view.setInputDate(new Date());
        view.setSortNum(0);

        List<ViewProperty> viewProperties = new ArrayList<>();

        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(CRUD.FORM), formView.getId()));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(CRUD.TABLE), tableView.getId()));
        viewProperties.add(new ViewProperty(view, LayoutManager.getLayoutPropById(CRUD.QUERY), queryView.getId()));

        view.setViewProperties(viewProperties);

        return view;
    }
}

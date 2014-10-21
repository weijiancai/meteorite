package com.meteorite.core.ui.model;

import com.meteorite.core.datasource.DataSourceManager;
import com.meteorite.core.datasource.db.util.JdbcTemplate;
import com.meteorite.core.datasource.persist.MetaPDBFactory;
import com.meteorite.core.meta.model.MetaField;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.HashMap;
import java.util.Map;

/**
 * 视图属性
 *
 * @author wei_jc
 * @since 1.0.0
 */
@XmlRootElement
public class ViewProperty {
    /** 视图属性ID */
    private String id;
    /** 视图ID */
    private String viewId;
    /** 布局属性ID */
    private String layoutPropId;
    /** 元字段ID */
    private String metaFieldId;
    /** 属性值 */
    private String value;

    private View view;
    private MetaField field;
    private LayoutProperty property;

    public ViewProperty() {
    }

    public ViewProperty(View view, LayoutProperty property, String value) {
        this.value = value;
        this.view = view;
        this.property = property;
        this.viewId = view.getId();
        this.layoutPropId = property.getId();
    }

    public ViewProperty(View view, LayoutProperty property, MetaField field, String value) {
        this.value = value;
        this.view = view;
        this.field = field;
        this.property = property;
        this.viewId = view.getId();
        this.layoutPropId = property.getId();
        this.metaFieldId = field.getId();
    }

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute
    public String getViewId() {
        return viewId;
    }

    public void setViewId(String viewId) {
        this.viewId = viewId;
    }

    @XmlAttribute
    public String getLayoutPropId() {
        return layoutPropId;
    }

    public void setLayoutPropId(String layoutPropId) {
        this.layoutPropId = layoutPropId;
    }

    @XmlAttribute
    public String getMetaFieldId() {
        return metaFieldId;
    }

    public void setMetaFieldId(String metaFieldId) {
        this.metaFieldId = metaFieldId;
    }

    @XmlAttribute
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @XmlTransient
    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    @XmlTransient
    public MetaField getField() {
        return field;
    }

    public void setField(MetaField field) {
        this.field = field;
    }

    @XmlTransient
    public LayoutProperty getProperty() {
        return property;
    }

    public void setProperty(LayoutProperty property) {
        this.property = property;
    }

    public void update() throws Exception {
        JdbcTemplate template = new JdbcTemplate();
        try {
            Map<String, Object> valueMap = new HashMap<String, Object>();
            valueMap.put("value", getValue());

            Map<String, Object> conditionMap = new HashMap<String, Object>();
            conditionMap.put("id", getId());

            template.update(valueMap, conditionMap, "mu_view_prop");
            template.commit();
        } finally {
            template.close();
        }
    }

    public void save() throws Exception {
        JdbcTemplate.save(DataSourceManager.getSysDataSource(), MetaPDBFactory.getViewProperty(this));
    }
}

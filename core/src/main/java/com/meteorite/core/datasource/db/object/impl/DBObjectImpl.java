package com.meteorite.core.datasource.db.object.impl;

import com.alibaba.fastjson.annotation.JSONField;
import com.meteorite.core.datasource.db.DBDataSource;
import com.meteorite.core.datasource.db.DBObjCache;
import com.meteorite.core.datasource.db.DBResource;
import com.meteorite.core.datasource.db.object.DBLoader;
import com.meteorite.core.datasource.db.object.DBObject;
import com.meteorite.core.datasource.db.object.enums.DBObjectType;
import com.meteorite.core.datasource.db.object.DBSchema;
import com.meteorite.core.datasource.db.object.DBDataset;
import com.meteorite.core.datasource.db.util.JdbcTemplate;
import com.meteorite.core.meta.MetaManager;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.ui.ViewManager;
import com.meteorite.core.ui.model.View;
import com.meteorite.core.util.UString;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class DBObjectImpl implements DBObject {
    private String name;
    private String comment;
    private DBSchema schema;
    private DBObjectType objectType;
    private ITreeNode parent;
    private List<ITreeNode> children;
    private DBDataSource dataSource;

    private String icon;
    private String presentableText = "";

    public DBObjectImpl() {}

    public DBObjectImpl(String name, String comment, List<ITreeNode> children) {
        setName(name);
        this.comment = comment;
        setChildren(children);
    }

    @Override
    public DBDataSource getDataSource() {
        return dataSource;
    }

    @Override @XmlAttribute
    @JSONField(name = "displayName")
    public String getName() {
        return name;
    }

    @Override
    public String getDisplayName() {
        return comment;
    }

    @Override
    public int getSortNum() {
        return 0;
    }

    @Override
    public String getFullName() {
        if ("ROOT".equals(name)) {
            return "";
        }
        if (objectType == DBObjectType.DATABASE) {
            return "[" + name + "] ";
        }

        if (getParent() != null) {
            if(objectType == DBObjectType.NONE) {
                return ((DBObjectImpl)getParent()).getFullName();
            }

            String fullName = ((DBObjectImpl)getParent()).getFullName();
            if (UString.isNotEmpty(fullName)) {
                if (((DBObjectImpl) getParent()).getObjectType() == DBObjectType.DATABASE) {
                    return fullName + name;
                }
                return fullName + "." + name;
            }
        }
        return name;
    }

    @Override @XmlElement
    public String getComment() {
        return comment;
    }

    @Override
    public DBObjectType getObjectType() {
        if (objectType == null) {
            objectType = DBObjectType.NONE;
        }
        return objectType;
    }

    @Override
    @XmlTransient
    public DBSchema getSchema() {
        return schema;
    }

    @Override
    @XmlTransient
    public List<ITreeNode> getChildren() {
        if (children == null) {
            children = new ArrayList<ITreeNode>();
        }
        return children;
    }

    @Override
    public String getIcon() {
        return UString.isEmpty(icon) ? objectType == null ? "" : objectType.getIcon() : icon;
    }

    @Override
    public String getPresentableText() {
        return presentableText;
    }

    @Override
    public boolean isVirtual() {
        return false;
    }

    @Override
    public String getId() {
        return getFullName();
    }

    @Override
    public String getPid() {
        if (parent == null) {
            return "";
        }
        return parent.getId();
    }

    @Override
    public ITreeNode getParent() {
        return parent;
    }

    @Override
    public void setParent(ITreeNode parent) {
        this.parent = parent;
    }

    public void setName(String name) {
        this.name = name;
        // 放入缓存
        DBObjCache.getInstance().addDbObject(getFullName(), this);
    }

    public void setPresentableText(String presentableText) {
        this.presentableText = presentableText;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setSchema(DBSchema schema) {
        this.schema = schema;
    }

    public void setObjectType(DBObjectType objectType) {
        this.objectType = objectType;
    }

    public void setChildren(List<ITreeNode> children) {
        this.children = children;
        if (children != null) {
            for (ITreeNode node : children) {
                node.setParent(this);
            }
        }
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setDataSource(DBDataSource dataSource) throws Exception {
        this.dataSource = dataSource;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (UString.isEmpty(presentableText) && objectType != null) {
            switch (objectType) {
                case SCHEMA: {
                    sb.append(name.toLowerCase());
                    break;
                }
                case TABLE:
                case VIEW:
                case CONSTRAINT:
                case COLUMN: {
                    sb.append(name.toLowerCase());
                    if (UString.isNotEmpty(comment) && !getSchema().getName().equalsIgnoreCase("information_schema")) {
                        sb.append(" - ").append(comment);
                    }
                    break;
                }
                default: {
                    sb.append(name);
                }
            }
        } else {
            sb.append(name).append(presentableText);
        }

        return sb.toString();
    }

    @Override
    public View getView() {
        if(objectType == DBObjectType.TABLE || objectType == DBObjectType.VIEW) {
            View view = ViewManager.getViewByName(UString.tableNameToClassName(name) + "CrudView");
            if (view == null) {
                JdbcTemplate template = new JdbcTemplate();
                try {
                    Meta meta = MetaManager.initMetaFromResource(template, new DBResource(this.dataSource, this));
                    // 创建视图
                    ViewManager.createViews(meta, template);
                    template.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    template.close();
                }

                view = ViewManager.getViewByName(UString.tableNameToClassName(name) + "CrudView");
            }

            return view;
        }
        return null;
    }
}

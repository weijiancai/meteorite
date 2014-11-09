package com.meteorite.core.datasource.db.object.impl;

import com.alibaba.fastjson.annotation.JSONField;
import com.meteorite.core.datasource.db.DBDataSource;
import com.meteorite.core.datasource.db.DBIcons;
import com.meteorite.core.datasource.db.DBObjCache;
import com.meteorite.core.datasource.db.DBResource;
import com.meteorite.core.datasource.db.object.*;
import com.meteorite.core.datasource.db.object.enums.DBObjectType;
import com.meteorite.core.datasource.db.util.JdbcTemplate;
import com.meteorite.core.meta.MetaManager;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.observer.BaseSubject;
import com.meteorite.core.observer.EventData;
import com.meteorite.core.observer.Subject;
import com.meteorite.core.ui.ViewManager;
import com.meteorite.core.ui.model.View;
import com.meteorite.core.util.UString;
import org.apache.log4j.Logger;

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
    private static final Logger log = Logger.getLogger(DBObjectImpl.class);

    private String name;
    private String comment;
    private DBSchema schema;
    private DBObjectType objectType;
    private ITreeNode parent;
    private List<ITreeNode> children;
    private DBDataSource dataSource;

    private String icon;
    private String presentableText = "";
    private Subject<EventData> presentableTextSubject = new BaseSubject<EventData>();

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
        return name;
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
        if (children == null || children.size() == 0) {
            if (children == null) {
                children = new ArrayList<ITreeNode>();
            }

            try {
                loadChildren();
            } catch (Exception e) {
                log.error("加载数据库子节点失败！", e);
            }
        }
        return children;
    }

    private void loadChildren() throws Exception {
        DBObjectType dbType = getObjectType();
        if (DBObjectType.DATABASE == dbType) {
            DBObjectList dbSchemas = new DBObjectList("Schemas", DBIcons.DBO_SCHEMAS, DBObjectType.SCHEMAS);
            dbSchemas.setDataSource(getDataSource());
            dbSchemas.setParent(this);

            DBObjectList dbUsers = new DBObjectList("Users", DBIcons.DBO_USERS, DBObjectType.USERS);
            dbUsers.setDataSource(getDataSource());
            dbUsers.setParent(this);

            DBObjectList dbPrivileges = new DBObjectList("Privileges", DBIcons.DBO_PRIVILEGES, DBObjectType.PRIVILEGES);
            dbPrivileges.setDataSource(getDataSource());
            dbPrivileges.setParent(this);

            DBObjectList dbCharsets = new DBObjectList("Charset", null, DBObjectType.CHARSETS);
            dbCharsets.setDataSource(getDataSource());
            dbCharsets.setParent(this);

            List<ITreeNode> list = new ArrayList<ITreeNode>();
            list.add(dbSchemas);
            list.add(dbUsers);
            list.add(dbPrivileges);
            list.add(dbCharsets);

            children.addAll(list);
        } else if (DBObjectType.SCHEMAS == dbType) {
            List<DBSchema> schemas = getDataSource().getDbConnection().getLoader().loadSchemas();
            for (DBSchema dbSchema : schemas) {
                DBSchemaImpl schema = (DBSchemaImpl) dbSchema;
                // 加载表
                DBObjectList tables = new DBObjectList("Tables", DBIcons.DBO_TABLES, DBObjectType.TABLES);
                tables.setSchema(schema);
                // 加载视图
                DBObjectList views = new DBObjectList("Views", DBIcons.DBO_VIEWS, DBObjectType.VIEWS);
                views.setSchema(schema);
                // 加载索引
                DBObjectList indexes = new DBObjectList("Indexes", DBIcons.DBO_INDEXES, DBObjectType.INDEXES);
                indexes.setSchema(schema);
                // 加载触发器
                DBObjectList triggers = new DBObjectList("Triggers", DBIcons.DBO_TRIGGERS, DBObjectType.TRIGGERS);
                triggers.setSchema(schema);
                // 加载存储过程
                DBObjectList procedures = new DBObjectList("Procedures", DBIcons.DBO_PROCEDURES, DBObjectType.PROCEDURES);
                procedures.setSchema(schema);
                // 加载函数
                DBObjectList functions = new DBObjectList("Functions", DBIcons.DBO_FUNCTIONS, DBObjectType.FUNCTIONS);
                functions.setSchema(schema);

                List<ITreeNode> list = new ArrayList<ITreeNode>();
                list.add(tables);
                list.add(views);
                list.add(indexes);
                list.add(triggers);
                list.add(procedures);
                list.add(functions);

                schema.setChildren(list);
            }
            children.addAll(schemas);
        } else if (DBObjectType.USERS == dbType) {
            List<DBUser> users = getDataSource().getDbConnection().getLoader().loadUsers();
            children.addAll(users);
        } else if (DBObjectType.PRIVILEGES == dbType) {
            List<DBObject> privileges = getDataSource().getDbConnection().getLoader().loadPrivileges();
            children.addAll(privileges);
        } else if (DBObjectType.CHARSETS == dbType) {
            List<DBObject> charsets = getDataSource().getDbConnection().getLoader().loadCharsets();
            children.addAll(charsets);
        } else if (DBObjectType.TABLES == dbType) {
            List<DBTable> tables = getDataSource().getDbConnection().getLoader().loadTables(schema);
            children.addAll(tables);
        } else if (DBObjectType.VIEWS == dbType) {
            List<DBView> views = getDataSource().getDbConnection().getLoader().loadViews(schema);
            children.addAll(views);
        } else if (DBObjectType.INDEXES == dbType) {
            List<DBIndex> indexes = getDataSource().getDbConnection().getLoader().loadIndexes(schema);
            children.addAll(indexes);
        } else if (DBObjectType.TRIGGERS == dbType) {
            List<DBTrigger> triggers = getDataSource().getDbConnection().getLoader().loadTriggers(schema);
            children.addAll(triggers);
        } else if (DBObjectType.PROCEDURES == dbType) {
            List<DBProcedure> procedures = getDataSource().getDbConnection().getLoader().loadProcedures(schema);
            children.addAll(procedures);
        } else if (DBObjectType.FUNCTIONS == dbType) {
            List<DBFunction> functions = getDataSource().getDbConnection().getLoader().loadFunctions(schema);
            children.addAll(functions);
        } else if (DBObjectType.COLUMNS == dbType) {
            List<DBColumn> columns = getDataSource().getDbConnection().getLoader().loadColumns((DBTable) getParent());
            children.addAll(columns);
        } else if (DBObjectType.CONSTRAINTS == dbType) {
            List<DBConstraint> constraints = getDataSource().getDbConnection().getLoader().loadConstraint((DBTable) getParent());
            children.addAll(constraints);
        }
    }

    @Override
    public String getIcon() {
        return UString.isEmpty(icon) ? objectType == null ? "" : objectType.getIcon() : icon;
    }

    @Override
    public String getPresentableText() {
        if (UString.isNotEmpty(presentableText)) {
            return presentableText;
        }

        if (UString.isEmpty(comment)) {
            return "";
        }
        if (objectType != null) {
            switch (objectType) {
                case TABLE:
                case VIEW:
                case CONSTRAINT:
                case COLUMN: {
                    presentableText = " - " + comment;
                }

            }
        }
        return presentableText;
    }

    @Override
    public boolean isVirtual() {
        return false;
    }

    @Override
    public Subject<EventData> getPresentableTextSubject() {
        return presentableTextSubject;
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
        if (!this.presentableText.equals(presentableText)) {
            EventData eventData = new EventData();
            eventData.setStrData(presentableText);
            presentableTextSubject.notifyObserver(eventData);
        }
        this.presentableText = presentableText;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setSchema(DBSchema schema) {
        this.schema = schema;
        this.dataSource = schema.getDataSource();
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

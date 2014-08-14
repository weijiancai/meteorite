package com.meteorite.core.datasource.db.object.impl;

import com.meteorite.core.datasource.db.object.DBArgument;
import com.meteorite.core.datasource.db.object.DBMethod;
import com.meteorite.core.datasource.db.object.DBObject;
import com.meteorite.core.datasource.db.object.enums.DBMethodType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 方法实现类
 *
 * @author wei_jc
 * @since 1.0.0
 */
public abstract class DBMethodImpl extends DBObjectImpl implements DBMethod {
    private List<DBArgument> arguments;
    private String content;
    private Map<String, DBArgument> argumentMap = new HashMap<String, DBArgument>();

    @Override
    public List<DBArgument> getArguments() {
        return arguments;
    }

    @Override
    public DBArgument getArgument(String name) {
        return argumentMap.get(name);
    }

    @Override
    public String getContent() {
        return content;
    }

    public void setArguments(List<DBArgument> arguments) {
        this.arguments = arguments;
        argumentMap.clear();
        for (DBArgument argument : arguments) {
            argumentMap.put(argument.getName(), argument);
        }
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getFullName() {
        return getSchema().getFullName() + "." + getName();
    }
}

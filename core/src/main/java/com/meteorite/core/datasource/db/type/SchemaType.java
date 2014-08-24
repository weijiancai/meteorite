package com.meteorite.core.datasource.db.type;

import com.meteorite.core.datasource.ResourceType;
import com.meteorite.core.datasource.db.object.enums.DBObjectType;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class SchemaType implements ResourceType {
    @Override
    public String getName() {
        return "DB_SCHEMA";
    }

    @Override
    public String getDescription() {
        return "数据库Schema";
    }

    @Override
    public String getDefaultExtension() {
        return null;
    }

    @Override
    public String getIcon() {
        return DBObjectType.SCHEMA.getIcon();
    }

    @Override
    public boolean isBinary() {
        return false;
    }
}

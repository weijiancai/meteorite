package com.meteorite.core.db.object.loader;

import com.meteorite.core.db.object.DBConnection;

/**
 * MySql加载器
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class HsqldbLoader extends BaseDBLoader {

    public HsqldbLoader(DBConnection conn) throws Exception {
        super(conn);
    }

    @Override
    protected String getSchemaSql() {
        return "select\n" +
                "                SCHEMA_NAME,\n" +
                "                'N' as IS_PUBLIC,\n" +
                "                if(lower(SCHEMA_NAME)='information_schema', 'Y', 'N') as IS_SYSTEM\n" +
                "            from INFORMATION_SCHEMA.SCHEMATA\n" +
                "            order by SCHEMA_NAME asc";
    }

    @Override
    protected String getTableSql() {
        return "select\n" +
                "                TABLE_NAME,\n" +
                "                TABLE_COMMENT,\n" +
                "                'N' as IS_TEMPORARY\n" +
                "            from  INFORMATION_SCHEMA.TABLES\n" +
                "            where\n" +
                "                TABLE_SCHEMA = '%s' and\n" +
                "                TABLE_TYPE = 'BASE TABLE'\n" +
                "            order by TABLE_NAME asc";
    }
}

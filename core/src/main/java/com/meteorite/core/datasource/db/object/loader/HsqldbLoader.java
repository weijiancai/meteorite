package com.meteorite.core.datasource.db.object.loader;

import com.meteorite.core.datasource.db.object.DBConnection;

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
                "                (case when SCHEMA_NAME='INFORMATION_SCHEMA' THEN 'T' ELSE 'F' END) as IS_SYSTEM\n" +
                "            from INFORMATION_SCHEMA.SCHEMATA\n" +
                "            order by SCHEMA_NAME asc";
    }

    @Override
    protected String getTableSql() {
        return "select\n" +
                "                TABLE_NAME,\n" +
                "                (select comment from INFORMATION_SCHEMA.system_comments where object_schema='%1$s' AND OBJECT_NAME=TABLE_NAME AND (OBJECT_TYPE='BASE TABLE' OR OBJECT_TYPE='TABLE')) as TABLE_COMMENT,\n" +
                "                'N' as IS_TEMPORARY\n" +
                "            from  INFORMATION_SCHEMA.TABLES\n" +
                "            where\n" +
                "                TABLE_SCHEMA = '%1$s' and\n" +
                "                TABLE_TYPE = 'BASE TABLE'\n" +
                "            order by TABLE_NAME asc";
    }
}

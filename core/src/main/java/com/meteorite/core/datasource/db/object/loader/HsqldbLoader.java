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
    protected String getUserSql() {
        return "select distinct\n" +
                "  user_name as USER_NAME,\n" +
                "  'N' as IS_EXPIRED,\n" +
                "  'N' as IS_LOCKED\n" +
                "from INFORMATION_SCHEMA.SYSTEM_USERS\n" +
                "order by user_name asc";
    }

    @Override
    protected String getPrivilegesSql() {
        return "select distinct PRIVILEGE_TYPE as PRIVILEGE_NAME\n" +
                "from INFORMATION_SCHEMA.TABLE_PRIVILEGES\n" +
                "order by PRIVILEGE_TYPE asc";
    }

    @Override
    protected String getCharsetsSql() {
        return "select\n" +
                "                CHARACTER_SET_NAME as CHARSET_NAME,\n" +
                "                '' as MAX_LENGTH\n" +
                "            from INFORMATION_SCHEMA.CHARACTER_SETS\n" +
                "            order by CHARACTER_SET_NAME asc";
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

    @Override
    protected String getViewSql() {
        return "select\n" +
                "  TABLE_NAME VIEW_NAME,\n" +
                "  (select comment from INFORMATION_SCHEMA.system_comments where object_schema='%1$s' AND OBJECT_NAME=TABLE_NAME AND OBJECT_TYPE='VIEW') as VIEW_COMMENT,\n" +
                "  'N' as IS_SYSTEM_VIEW\n" +
                "from  INFORMATION_SCHEMA.TABLES\n" +
                "where\n" +
                "  TABLE_SCHEMA = '%1$s' and\n" +
                "  TABLE_TYPE = 'VIEW'\n" +
                "order by TABLE_NAME asc";
    }

    @Override
    protected String getColumnSql() {
        return "select\n" +
                "                col.COLUMN_NAME,\n" +
                "                (select comment from INFORMATION_SCHEMA.system_comments c where c.object_schema='%1$s' AND c.OBJECT_NAME='%2$s' AND c.COLUMN_NAME=col.COLUMN_NAME AND c.OBJECT_TYPE='COLUMN') AS COLUMN_COMMENT,\n" +
                "                col.ORDINAL_POSITION as POSITION,\n" +
                "                col.DATA_TYPE as DATA_TYPE_NAME,\n" +
                "                null as DATA_TYPE_OWNER,\n" +
                "                null as DATA_TYPE_PACKAGE,\n" +
                "                col.CHARACTER_MAXIMUM_LENGTH as DATA_LENGTH,\n" +
                "                col.NUMERIC_PRECISION as DATA_PRECISION,\n" +
                "                col.NUMERIC_SCALE as DATA_SCALE,\n" +
                "                left(col.IS_NULLABLE, 1) as IS_NULLABLE,\n" +
                "                'N' as IS_HIDDEN,\n" +
                "                (case when (kcu.position_in_unique_constraint is null and kcu.COLUMN_NAME is not null) then 'Y' else 'N' end) as IS_PRIMARY_KEY,\n" +
                "                (case when (kcu.position_in_unique_constraint is not null and kcu.COLUMN_NAME is not null) then 'Y' else 'N' end) as IS_FOREIGN_KEY\n" +
                "            from INFORMATION_SCHEMA.COLUMNS col\n" +
                "                    left join (\n" +
                "                        select\n" +
                "                            TABLE_SCHEMA,\n" +
                "                            TABLE_NAME,\n" +
                "                            COLUMN_NAME,\n" +
                "                            position_in_unique_constraint" +
                "                    from INFORMATION_SCHEMA.KEY_COLUMN_USAGE\n" +
                "                     ) kcu on\n" +
                "                        kcu.TABLE_SCHEMA = col.TABLE_SCHEMA and\n" +
                "                        kcu.TABLE_NAME = col.TABLE_NAME and\n" +
                "                        kcu.COLUMN_NAME = col.COLUMN_NAME\n" +
                "            where\n" +
                "                col.TABLE_SCHEMA = '%1$s' and\n" +
                "                col.TABLE_NAME = '%2$s'\n" +
                "            order by col.ORDINAL_POSITION asc";
    }
}

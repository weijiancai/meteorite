package com.meteorite.core.datasource.db.object.loader;

import com.meteorite.core.datasource.db.object.DBConnection;

/**
 * MySql加载器
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MySqlLoader extends BaseDBLoader {

    public MySqlLoader(DBConnection conn) throws Exception {
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

    @Override
    protected String getViewSql() {
        return "select\n" +
                "                TABLE_NAME as VIEW_NAME,\n" +
                "                TABLE_COMMENT VIEW_COMMENT,\n" +
                "                null as VIEW_TYPE_OWNER,\n" +
                "                null as VIEW_TYPE,\n" +
                "                if (TABLE_TYPE = 'VIEW', 'N', 'Y') as IS_SYSTEM_VIEW\n" +
                "            from INFORMATION_SCHEMA.TABLES\n" +
                "            where\n" +
                "                TABLE_SCHEMA = '%s' and\n" +
                "                TABLE_TYPE in ('VIEW', 'SYSTEM VIEW')\n" +
                "            order by TABLE_NAME asc";
    }

    @Override
    protected String getColumnSql() {
        return "select\n" +
                "                col.COLUMN_NAME,\n" +
                "                col.COLUMN_COMMENT,\n" +
                "                col.ORDINAL_POSITION as `POSITION`,\n" +
                "                col.DATA_TYPE as DATA_TYPE_NAME,\n" +
                "                null as DATA_TYPE_OWNER,\n" +
                "                null as DATA_TYPE_PACKAGE,\n" +
                "                col.CHARACTER_MAXIMUM_LENGTH as DATA_LENGTH,\n" +
                "                col.NUMERIC_PRECISION as DATA_PRECISION,\n" +
                "                col.NUMERIC_SCALE as DATA_SCALE,\n" +
                "                left(col.IS_NULLABLE, 1) as IS_NULLABLE,\n" +
                "                'N' as IS_HIDDEN,\n" +
                "                if(col.COLUMN_KEY = 'PRI', 'Y', 'N') as IS_PRIMARY_KEY,\n" +
                "                if(kcu.COLUMN_NAME is null, 'N', 'Y') as IS_FOREIGN_KEY\n" +
                "            from INFORMATION_SCHEMA.`COLUMNS` col\n" +
                "                    left join (\n" +
                "                        select\n" +
                "                            TABLE_SCHEMA,\n" +
                "                            TABLE_NAME,\n" +
                "                            COLUMN_NAME\n" +
                "                    from INFORMATION_SCHEMA.KEY_COLUMN_USAGE\n" +
                "                    where REFERENCED_COLUMN_NAME is not null) kcu on\n" +
                "                        kcu.TABLE_SCHEMA = col.TABLE_SCHEMA and\n" +
                "                        kcu.TABLE_NAME = col.TABLE_NAME and\n" +
                "                        kcu.COLUMN_NAME = col.COLUMN_NAME\n" +
                "            where\n" +
                "                col.TABLE_SCHEMA = '{0}' and\n" +
                "                col.TABLE_NAME = '{1}'\n" +
                "            order by col.COLUMN_NAME asc";
    }
}

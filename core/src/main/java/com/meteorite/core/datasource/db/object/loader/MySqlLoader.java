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
    protected String getUserSql() {
        return "select distinct\n" +
                "                GRANTEE as USER_NAME,\n" +
                "                'N' as IS_EXPIRED,\n" +
                "                'N' as IS_LOCKED\n" +
                "            from INFORMATION_SCHEMA.USER_PRIVILEGES\n" +
                "            order by GRANTEE asc";
    }

    @Override
    protected String getPrivilegesSql() {
        return "select distinct PRIVILEGE_TYPE as PRIVILEGE_NAME\n" +
                "            from INFORMATION_SCHEMA.USER_PRIVILEGES\n" +
                "            order by PRIVILEGE_TYPE asc";
    }

    @Override
    protected String getCharsetsSql() {
        return "select\n" +
                "                CHARACTER_SET_NAME as CHARSET_NAME,\n" +
                "                MAXLEN as MAX_LENGTH\n" +
                "            from INFORMATION_SCHEMA.CHARACTER_SETS\n" +
                "            order by CHARACTER_SET_NAME asc";
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
                "            order by col.ORDINAL_POSITION asc";
    }

    @Override
    protected String getConstraintsSql() {
        return "select\n" +
                "                tc.TABLE_NAME as DATASET_NAME,\n" +
                "                case\n" +
                "                    when tc.CONSTRAINT_TYPE = 'PRIMARY KEY' then concat('pk_', tc.TABLE_NAME)\n" +
                "                    when tc.CONSTRAINT_TYPE = 'UNIQUE' then concat('unq_', tc.TABLE_NAME)\n" +
                "                    else tc.CONSTRAINT_NAME\n" +
                "                end as CONSTRAINT_NAME,\n" +
                "                tc.CONSTRAINT_TYPE,\n" +
                "                rc.UNIQUE_CONSTRAINT_SCHEMA as FK_CONSTRAINT_OWNER,\n" +
                "                case\n" +
                "                    when rc.UNIQUE_CONSTRAINT_NAME = 'PRIMARY' then concat('pk_', rc.REFERENCED_TABLE_NAME)\n" +
                "                    when rc.UNIQUE_CONSTRAINT_NAME = 'name' then concat('unq_', rc.REFERENCED_TABLE_NAME)\n" +
                "                    else rc.UNIQUE_CONSTRAINT_NAME\n" +
                "                end as FK_CONSTRAINT_NAME,\n" +
                "                'Y' as IS_ENABLED,\n" +
                "                null as CHECK_CONDITION\n" +
                "            from\n" +
                "                INFORMATION_SCHEMA.TABLE_CONSTRAINTS tc left join\n" +
                "                INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS rc on\n" +
                "                    rc.CONSTRAINT_SCHEMA = tc.CONSTRAINT_SCHEMA and\n" +
                "                    rc.CONSTRAINT_NAME = tc.CONSTRAINT_NAME and\n" +
                "                    rc.TABLE_NAME = tc.TABLE_NAME\n" +
                "            where\n" +
                "                tc.TABLE_SCHEMA = '{0}' and\n" +
                "                tc.TABLE_NAME = '{1}'\n" +
                "            order by\n" +
                "                tc.TABLE_NAME,\n" +
                "                tc.CONSTRAINT_NAME asc";
    }

    @Override
    protected String getIndexesSql() {
        return "select distinct\n" +
                "                INDEX_NAME,\n" +
                "                TABLE_NAME,\n" +
                "                if (NON_UNIQUE = 'YES', 'N', 'Y') as IS_UNIQUE,\n" +
                "                'Y' as IS_VALID\n" +
                "            from INFORMATION_SCHEMA.STATISTICS\n" +
                "            where TABLE_SCHEMA = '{0}'\n" +
                "            order by\n" +
                "                TABLE_NAME,\n" +
                "                INDEX_NAME asc";
    }

    @Override
    protected String getTriggersSql() {
        return "select\n" +
                "                EVENT_OBJECT_TABLE as DATASET_NAME,\n" +
                "                TRIGGER_NAME,\n" +
                "                ACTION_TIMING as TRIGGER_TYPE,\n" +
                "                EVENT_MANIPULATION as TRIGGERING_EVENT,\n" +
                "                'Y' as IS_ENABLED,\n" +
                "                'Y' as IS_VALID,\n" +
                "                'N' as IS_DEBUG,\n" +
                "                'Y' as IS_FOR_EACH_ROW\n" +
                "            from INFORMATION_SCHEMA.TRIGGERS\n" +
                "            where EVENT_OBJECT_SCHEMA = '{0}'\n" +
                "            order by\n" +
                "                EVENT_OBJECT_TABLE,\n" +
                "                TRIGGER_NAME asc";
    }

    @Override
    protected String getProceduresSql() {
        return "select\n" +
                "  ROUTINE_NAME as PROCEDURE_NAME,\n" +
                "  'Y' as IS_VALID,\n" +
                "  'N' as IS_DEBUG,\n" +
                "  left(IS_DETERMINISTIC, 1) as IS_DETERMINISTIC\n" +
                "from INFORMATION_SCHEMA.ROUTINES\n" +
                "where\n" +
                "  ROUTINE_SCHEMA = '%1$s' and\n" +
                "  ROUTINE_TYPE = 'PROCEDURE'\n" +
                "order by ROUTINE_NAME asc";
    }

    @Override
    protected String getFunctionsSql() {
        return "select\n" +
                "                ROUTINE_NAME as FUNCTION_NAME,\n" +
                "                'Y' as IS_VALID,\n" +
                "                'N' as IS_DEBUG,\n" +
                "                left(IS_DETERMINISTIC, 1) as IS_DETERMINISTIC\n" +
                "            from INFORMATION_SCHEMA.ROUTINES\n" +
                "            where\n" +
                "                ROUTINE_SCHEMA = '%1$s' and\n" +
                "                ROUTINE_TYPE = 'FUNCTION'\n" +
                "            order by ROUTINE_NAME asc";
    }

    @Override
    protected String getParametersSql() {
        return "select\n" +
                "                PARAMETER_NAME as ARGUMENT_NAME,\n" +
                "                null as PROGRAM_NAME,\n" +
                "                SPECIFIC_NAME as METHOD_NAME,\n" +
                "                ROUTINE_TYPE as METHOD_TYPE\n" +
                "                0 as OVERLOAD,\n" +
                "                ORDINAL_POSITION as POSITION,\n" +
                "                ORDINAL_POSITION as SEQUENCE,\n" +
                "                if (PARAMETER_MODE is null, 'OUT', PARAMETER_MODE) as IN_OUT,\n" +
                "                null as DATA_TYPE_OWNER,\n" +
                "                null as DATA_TYPE_PACKAGE,\n" +
                "                DATA_TYPE as DATA_TYPE_NAME,\n" +
                "                CHARACTER_MAXIMUM_LENGTH  as DATA_LENGTH,\n" +
                "                NUMERIC_PRECISION as DATA_PRECISION,\n" +
                "                NUMERIC_SCALE as DATA_SCALE\n" +
                "            from INFORMATION_SCHEMA.PARAMETERS\n" +
                "            where\n" +
                "                SPECIFIC_SCHEMA = '%1$s'\n" +
                "            order by\n" +
                "                SPECIFIC_NAME,\n" +
                "                ORDINAL_POSITION asc";
    }

    @Override
    protected String getFKConstraintsColumnsSql() {
        return "SELECT\n" +
                "  constraint_name,\n" +
                "  table_name,\n" +
                "  column_name,\n" +
                "  referenced_table_name,\n" +
                "  referenced_column_name\n" +
                "FROM information_schema.KEY_COLUMN_USAGE\n" +
                "WHERE table_schema = '%1$s' AND referenced_table_name IS NOT NULL AND referenced_column_name IS NOT NULL";
    }
}

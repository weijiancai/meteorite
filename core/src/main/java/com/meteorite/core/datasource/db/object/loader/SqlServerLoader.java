package com.meteorite.core.datasource.db.object.loader;

import com.meteorite.core.datasource.db.object.impl.DBConnectionImpl;

/**
 * SqlServer 加载器
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class SqlServerLoader extends BaseDBLoader {
    public SqlServerLoader(DBConnectionImpl conn) throws Exception {
        super(conn);
    }

    @Override
    protected String getUserSql() {
        return "select\n" +
                "                name as USER_NAME,\n" +
                "                'N' as IS_EXPIRED,\n" +
                "                'N' as IS_LOCKED\n" +
                "            from sys.schemas\n" +
                "            order by schema_id asc";
    }

    @Override
    protected String getPrivilegesSql() {
        return "select distinct\n" +
                "                permission_name as PRIVILEGE_NAME\n" +
                "            from fn_builtin_permissions(default)\n" +
                "            order by permission_name asc";
    }

    @Override
    protected String getCharsetsSql() {
        return "select\n" +
                "                name as CHARSET_NAME,\n" +
                "                0 as MAX_LENGTH\n" +
                "            from sys.syscharsets\n" +
                "            order by name asc";
    }

    @Override
    protected String getSchemaSql() {
        return "select\n" +
                "                name as SCHEMA_NAME,\n" +
                "                'N' as IS_PUBLIC,\n" +
                "                (case when database_id > 3 then 'N' else 'Y' end) as IS_SYSTEM\n" +
                "            from sys.databases\n" +
                "            order by name asc";
    }

    @Override
    protected String getTableSql() {
        return "select\n" +
                "                name TABLE_NAME,\n" +
                "                (select top 1 convert(varchar, value) from [%1$s].sys.extended_properties where major_id = object_id) TABLE_COMMENT,\n" +
                "                'N' as IS_TEMPORARY\n" +
                "            from  [%s].sys.TABLES\n" +
                "            order by name asc";
    }

    @Override
    protected String getViewSql() {
        return "select\n" +
                "                table_name as VIEW_NAME,\n" +
                "                null as VIEW_TYPE_OWNER,\n" +
                "                null as VIEW_TYPE,\n" +
                "                'N' as IS_SYSTEM_VIEW\n" +
                "            from [%s].INFORMATION_SCHEMA.views\n" +
                "            order by table_name asc";
    }

    @Override
    protected String getColumnSql() {
        return "select\n" +
                "                '%2$s' as DATASET_NAME,\n" +
                "                col.name as COLUMN_NAME,\n" +
                "                (select top 1 convert(varchar, value) from [%1$s].sys.extended_properties where major_id = col.object_id and minor_id = col.column_id) COLUMN_COMMENT,\n" +
                "                col.column_id as POSITION,\n" +
                "                (select top 1 name from [%1$s].sys.types where system_type_id = col.system_type_id) as DATA_TYPE_NAME,\n" +
                "                null as DATA_TYPE_OWNER,\n" +
                "                null as DATA_TYPE_PACKAGE,\n" +
                "                col.max_length as DATA_LENGTH,\n" +
                "                col.PRECISION as DATA_PRECISION,\n" +
                "                col.SCALE as DATA_SCALE,\n" +
                "                (case when col.IS_NULLABLE = 0 then 'N' else 'Y' end) as IS_NULLABLE,\n" +
                "                'N' as IS_HIDDEN,\n" +
                "                'N' as IS_PRIMARY_KEY,\n" +
                "                'N' as IS_FOREIGN_KEY\n" +
                "            from [%1$s].sys.columns col\n" +
                "            where\n" +
                "                col.object_id = (select object_id from [%1$s].sys.tables where name='%2$s')\n" +
                "            order by col.name asc";
    }

    @Override
    protected String getConstraintsSql() {
        return "select\n" +
                "                a.TABLE_NAME as DATASET_NAME,\n" +
                "                a.constraint_name as CONSTRAINT_NAME,\n" +
                "                a.CONSTRAINT_TYPE,\n" +
                "                '' as FK_CONSTRAINT_OWNER,\n" +
                "                '' as FK_CONSTRAINT_NAME,\n" +
                "                'Y' as IS_ENABLED,\n" +
                "                null as CHECK_CONDITION\n" +
                "                from\n" +
                "                INFORMATION_SCHEMA.TABLE_CONSTRAINTS a,\n" +
                "                INFORMATION_SCHEMA.KEY_COLUMN_USAGE b\n" +
                "            where\n" +
                "                a.table_name = b.table_name and\n" +
                "                a.table_catalog = b.table_catalog and\n" +
                "                a.constraint_name = b.constraint_name and\n" +
                "                a.TABLE_catalog = '%1$s' and\n" +
                "                a.table_name = '%2$s'\n" +
                "            order by\n" +
                "                a.TABLE_NAME,\n" +
                "                a.CONSTRAINT_NAME asc";
    }

    @Override
    protected String getIndexesSql() {
        // TODO index columns
        return "select\n" +
                "                name as INDEX_NAME,\n" +
                "                (select name from dbo.sysobjects where id=object_id) as TABLE_NAME,\n" +
                "                '' as COLUMN_NAME,\n" +
                "                (case when is_unique = 0 then 'N' else 'Y' end) as IS_UNIQUE,\n" +
                "                'Y' as IS_ASC,\n" +
                "                (case when is_disabled = 0 then 'N' else 'Y' end) as IS_VALID\n" +
                "            from [%1$s].sys.indexes\n" +
                "            where\n" +
                "                name is not null\n" +
                "            order by name asc";
    }

    @Override
    protected String getTriggersSql() {
        return "select\n" +
                "                (select top 1 name from [%1$s].sys.tables where object_id = t.parent_id) as DATASET_NAME,\n" +
                "                t.name TRIGGER_NAME,\n" +
                "                '' as TRIGGER_TYPE,\n" +
                "                '' as TRIGGERING_EVENT,\n" +
                "                'Y' as IS_ENABLED,\n" +
                "                'Y' as IS_VALID,\n" +
                "                'N' as IS_DEBUG,\n" +
                "                'Y' as IS_FOR_EACH_ROW\n" +
                "            from [%1$s].sys.TRIGGERS t\n" +
                "            order by parent_id, name asc";
    }

    @Override
    protected String getProceduresSql() {
        return "select\n" +
                "                ROUTINE_NAME as PROCEDURE_NAME,\n" +
                "                'Y' as IS_VALID,\n" +
                "                'N' as IS_DEBUG,\n" +
                "                'N' as IS_DETERMINISTIC\n" +
                "            from [%1$s].INFORMATION_SCHEMA.ROUTINES\n" +
                "            where\n" +
                "                ROUTINE_CATALOG = '%1$s' and\n" +
                "                ROUTINE_TYPE = 'PROCEDURE'\n" +
                "            order by ROUTINE_NAME asc";
    }

    @Override
    protected String getFunctionsSql() {
        return "select\n" +
                "                ROUTINE_NAME as FUNCTION_NAME,\n" +
                "                'Y' as IS_VALID,\n" +
                "                'N' as IS_DEBUG,\n" +
                "                'N' as IS_DETERMINISTIC\n" +
                "            from [%1$s].INFORMATION_SCHEMA.ROUTINES\n" +
                "            where\n" +
                "                ROUTINE_CATALOG = '%1$s' and\n" +
                "                ROUTINE_TYPE = 'FUNCTION'\n" +
                "            order by ROUTINE_NAME asc";
    }

    @Override
    protected String getParametersSql() {
        return "select\n" +
                "                a.PARAMETER_NAME as ARGUMENT_NAME,\n" +
                "                null as PROGRAM_NAME,\n" +
                "                a.SPECIFIC_NAME as METHOD_NAME,\n" +
                "                b.ROUTINE_TYPE as METHOD_TYPE,\n" +
                "                0 as OVERLOAD,\n" +
                "                a.ORDINAL_POSITION as POSITION,\n" +
                "                a.ORDINAL_POSITION as SEQUENCE,\n" +
                "                (case when a.PARAMETER_MODE is null then 'OUT' else a.PARAMETER_MODE end) as IN_OUT,\n" +
                "                null as DATA_TYPE_OWNER,\n" +
                "                null as DATA_TYPE_PACKAGE,\n" +
                "                a.DATA_TYPE as DATA_TYPE_NAME,\n" +
                "                a.CHARACTER_MAXIMUM_LENGTH  as DATA_LENGTH,\n" +
                "                a.NUMERIC_PRECISION as DATA_PRECISION,\n" +
                "                a.NUMERIC_SCALE as DATA_SCALE\n" +
                "            from [%1$s].INFORMATION_SCHEMA.PARAMETERS a, [%1$s].INFORMATION_SCHEMA.ROUTINES b\n" +
                "            where\n" +
                "                a.SPECIFIC_CATALOG = b.ROUTINE_CATALOG and\n" +
                "                a.SPECIFIC_NAME = b.SPECIFIC_NAME and\n" +
                "                a.SPECIFIC_CATALOG = '%1$s'\n" +
                "            order by\n" +
                "                a.SPECIFIC_NAME,\n" +
                "                a.ORDINAL_POSITION asc";
    }

    @Override
    protected String getFKConstraintsColumnsSql() {
        return "SELECT\n" +
                "  (select name from dbo.sysobjects where id=constid) constraint_name,\n" +
                "  (select name from dbo.sysobjects where id=fkeyid) table_name,\n" +
                "  (select name from sys.columns where OBJECT_ID= fkeyid and column_id=fkey) column_name,\n" +
                "  (select name from dbo.sysobjects where id=rkeyid) referenced_table_name,\n" +
                "  (select name from sys.columns where OBJECT_ID= rkeyid and column_id=rkey) referenced_column_name\n" +
                "FROM SYSFOREIGNKEYS b";
    }
}

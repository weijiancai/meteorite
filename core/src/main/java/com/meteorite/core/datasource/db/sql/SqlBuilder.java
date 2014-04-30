package com.meteorite.core.datasource.db.sql;

import com.meteorite.core.datasource.db.DatabaseType;
import com.meteorite.core.util.UString;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Sql 预编译语句生成器
 *     使用方法：
 *         SqlBuilder.create()
 *             .from("table name")
 *             .join("join table_name on (....)")
 *             .query("column name")
 *             .where("where condition")
 *             .and("and condition")
 *             .and("column_name [=|>|<...] ", value)
 *             .build()
 * @author wei_jc
 * @since 1.0.0
 */
public class SqlBuilder {
    private String table;
    private static final String SELECT_WHERE_FORMAT = "SELECT %s FROM %s WHERE %s";
    private static final String WITH_SELECT_WHERE_FORMAT = "%s " + SELECT_WHERE_FORMAT;
    private static final String GROUP_FORMAT = " GROUP BY %s";
    private String columns = "";
    private String where = " 1=1";
    private String with;
    private String group;
    private boolean haveWith;
    private boolean isQuery = true;
    private boolean isBracket = false;
    private Queue<Object> paramQueue = new LinkedList<>();
    private DatabaseType dbType;

    private SqlBuilder(DatabaseType dbType) {
        this.dbType = dbType;
    }

    public static SqlBuilder create(DatabaseType dbType) {
        return new SqlBuilder(dbType);
    }

    /**
     * 查询的列，可以是单个列名，也可以是多个以逗号分隔的列名
     *
     * @param columns 列名
     * @return 返回Sql预编译语句生成器
     */
    public SqlBuilder query(String columns) {
        if (UString.isEmpty(columns)) {
            return this;
        }
        if (this.columns.trim().length() > 0) {
            this.columns += ", " + columns;
        } else {
            this.columns += columns;
        }

        this.isQuery = true;

        return this;
    }

    /**
     * 表名，可以是单个表名，也可以是多个以逗号分隔的表名
     *
     * @param table 表名
     * @return 返回Sql预编译语句生成器
     */
    public SqlBuilder from(String table) {
        this.table = table;

        return this;
    }

    /**
     * 进行连接操作的表名，如果要加限制条件，可以在表名后面加限制条件，例如 builder.join("table_name ON (...)")
     *
     * @param joinTable 连接操作的表名
     * @return 返回Sql预编译语句生成器
     */
    public SqlBuilder join(String joinTable) {
        table += " JOIN " + joinTable;

        return this;
    }

    /**
     * 添加 where条件
     *
     * @param where where条件
     * @return 返回Sql预编译语句生成器
     */
    public SqlBuilder where(String where) {
        this.where = where;

        return this;
    }

    /**
     * 添加 and 条件
     *
     * @param and and条件
     * @return 返回Sql预编译语句生成器
     */
    public SqlBuilder and(String and) {
        where += " AND " + and;

        return this;
    }

    /**
     * 添加语句 AND (
     *
     * @return 返回Sql预编译语句生成器
     */
    public SqlBuilder andBracket() {
        where += " AND (";
        isBracket = true;

        return this;
    }

    /**
     * 添加 and 条件，并且会在AND 后面加上左括号
     *
     * @param and and条件
     * @param value 参数值
     * @return 返回Sql预编译语句生成器
     */
    public SqlBuilder andBracket(String and, Object value) {
        if (value != null) {
            if (UString.isNotEmpty(value.toString())) {
                where += " AND (" + and + "?";
                paramQueue.offer(value);
                isBracket = true;
            }
        }

        return this;
    }

    /**
     * 添加 and like 条件， 并且会在AND 后面加上左括号
     *
     * @param and and条件
     * @param format like匹配样式，%%表示一个%，%s表示匹配字符串
     * @param value 参数值
     * @return 返回Sql预编译语句生成器
     */
    public SqlBuilder andBracketLike(String and, String format, String value) {
        if (UString.isNotEmpty(value)) {
            where += " AND (" + and + " LIKE ?";
            paramQueue.offer(String.format(format, value));
            isBracket = true;
        }

        return this;
    }

    /**
     * 加上左括号
     *
     * @return 返回Sql预编译语句生成器
     */
    public SqlBuilder leftBracket() {
        where += "(";
        isBracket = true;

        return this;
    }

    /**
     * 加上右括号，以匹配左括号
     *
     * @return 返回Sql预编译语句生成器
     */
    public SqlBuilder rightBracket() {
        if (isBracket) {
            if ("AND (".equals(where.substring(where.length() - 5))) {
                where = where.substring(0, where.length() - 6);
            } else {
                where += ")";
            }
            isBracket = false;
        }

        return this;
    }

    /**
     * 添加 and in 条件
     *
     * @param and   and in 条件
     * @param value 参数值
     * @return 返回Sql预编译语句生成器
     */
    public SqlBuilder andIn(String and, String value) {
        if (UString.isEmpty(value)) {
            return this;
        }

        where += " AND " + and + " IN (" + value+ ")";

        return this;
    }

    /**
     * 添加 or value 条件
     *
     * @param and   or value 条件
     * @param value 参数值
     * @return 返回Sql预编译语句生成器
     */
    public SqlBuilder orIn(String and, String value) {
        if (UString.isEmpty(value)) {
            return this;
        }

        where += " OR " + and + " IN (" + value+ ")";

        return this;
    }

    /**
     * 添加 and like 条件
     *
     * @param and   and like 条件
     * @param format like匹配样式，%%表示一个%，%s表示匹配字符串
     * @param value 参数值
     * @return 返回Sql预编译语句生成器
     */
    public SqlBuilder andLike(String and, String format, String value) {
        if (UString.isNotEmpty(value)) {
            where += " AND " + and + " LIKE ?";
            paramQueue.offer(String.format(format, value));
        }

        return this;
    }

    /**
     * 添加 and 条件
     *
     * @param and   and条件
     * @param value 参数值
     * @return 返回Sql预编译语句生成器
     */
    public SqlBuilder and(String and, Object value) {
        if (null == value) {
            return this;
        }

        if (UString.isNotEmpty(value.toString())) {
            where += " AND " + and + "?";
            paramQueue.offer(value);
        }

        return this;
    }

    /**
     * 添加 and 条件，调用to_date(?, 'yyyy-MM-dd')
     *
     * @param and   and 条件
     * @param value 参数值
     * @return 返回Sql预编译语句生成器
     */
    public SqlBuilder andDate(String and, String value) {
        if (UString.isNotEmpty(value)) {
            if (DatabaseType.HSQLDB == dbType) {
                where += " AND " + and + " to_date(?, 'YYYY-MM-DD')";
                paramQueue.offer(value);
            } else if (DatabaseType.ORACLE == dbType) {
                where += " AND " + and + " to_date(?, 'yyyy-MM-dd')";
                paramQueue.offer(value);
            } else {
                where += " AND " + and + "?";
                paramQueue.offer(value);
            }
        }

        return this;
    }

    /**
     * 添加 or 条件
     * @param or or条件
     * @return 返回Sql预编译语句生成器
     */
    public SqlBuilder or(String or) {
        where += " OR " + or;

        return this;
    }

    /**
     * 添加 or 条件
     * @param or    or 条件
     * @param value 参数值
     * @return 返回Sql预编译语句生成器
     */
    public SqlBuilder or(String or, Object value) {
        if (null == value) {
            return this;
        }
        if (UString.isNotEmpty(value.toString())) {
            where += " OR " + or + "?";
            paramQueue.offer(value);
        }

        return this;
    }

    /**
     * 添加 or like 条件
     *
     * @param or   or like 条件
     * @param format like匹配样式，%%表示一个%，%s表示匹配字符串
     * @param value 参数值
     * @return 返回Sql预编译语句生成器
     */
    public SqlBuilder orLike(String or, String format, String value) {
        if (UString.isNotEmpty(value)) {
            where += " OR " + or + " LIKE ?";
            paramQueue.offer(String.format(format, value));
        }
        return this;
    }

    /**
     * 添加 like 条件
     *
     * @param name   like 条件名称
     * @param format like匹配样式，%%表示一个%，%s表示匹配字符串
     * @param value 参数值
     * @return 返回Sql预编译语句生成器
     */
    public SqlBuilder like(String name, String format, String value) {
        if (UString.isNotEmpty(name) && UString.isNotEmpty(value)) {
            where += name + " LIKE ?";
            paramQueue.offer(String.format(format, value));
        }
        return this;
    }

    /**
     * 使用降序排序
     *
     * @param desc 排序字段
     * @return 返回Sql预编译语句生成器
     */
    public SqlBuilder desc(String desc) {
        if (UString.isNotEmpty(desc)) {
            where += " ORDER BY " + desc + " DESC";
        }

        return this;
    }

    /**
     * 在sql开头增加一条with语句
     * @param with with语句
     * @return 返回Sql预编译语句生成器
     */
    public SqlBuilder with(String with) {
        this.with = with;
        this.haveWith = true;

        return this;
    }

    /**
     * sql group by
     *
     * @param group 要进行分组的列，如：name, age
     * @return 返回Sql预编译语句生成器
     */
    public SqlBuilder group(String group) {
        this.group = group;

        return this;
    }

    /**
     * sql max column
     *
     * @param column 要计算max的列
     * @return 返回Sql预编译语句生成器
     */
    public SqlBuilder max(String column) {
        return query(String.format("MAX(%s) AS max_%1$s", column));
    }

    /**
     * 打印输出整条Sql语句
     *
     * @return 返回构造好的Sql语句
     */
    public String build() {
        if (UString.isEmpty(columns)) {
            columns = "*";
        }

        String result = "";

        if (isQuery) {
            if (haveWith) {
                result = String.format(WITH_SELECT_WHERE_FORMAT, with, columns, table, where);
            } else {
                result = String.format(SELECT_WHERE_FORMAT, columns, table, where);
            }
        }

        if (UString.isNotEmpty(group)) {
            result += String.format(GROUP_FORMAT, group);
        }

        return result;
    }

    /**
     * 获取总记录条数sql语句
     *
     * @return 返回总记录条数sql语句
     */
    public String getCountSql() {
        if (isQuery) {
            if (haveWith) {
                return String.format(WITH_SELECT_WHERE_FORMAT, with, "count(1)", table, where);
            } else {
                return String.format(SELECT_WHERE_FORMAT, "count(1)", table, where);
            }
        }
        return "";
    }

    @Override
    public String toString() {
        return build();
    }

    /**
     * 获取Oracle分页Sql
     *
     * @param page 记录开始数
     * @param rows   记录结束数
     * @return 返回Oracle分页数
     */
    public String getPageSql(int page, int rows) {
        int start = page * rows;
        int end = (page + 1) * rows;
        if(dbType == DatabaseType.ORACLE) {
            return String.format("SELECT * FROM (SELECT nowpage.*,rownum rn FROM (%s) nowpage WHERE rownum<=%d) WHERE rn>%d", build(), end, start);
        } else if (dbType == DatabaseType.HSQLDB) {
            return String.format("SELECT LIMIT %d %d * FROM (%s)", start, rows, build());
        }

        return "";
    }

    /**
     * 获取参数值数组
     *
     * @return 返回参数值数组
     */
    public Object[] getParamsValue() {
        return paramQueue.toArray();
    }

    /**
     * 将sql预编译语句代入值输出
     *
     * @return 返回sql语句
     */
    public String toLog() {
        String sql = this.build();
        if (UString.isEmpty(sql)) {
            return "";
        }
        if (!sql.contains("?")) {
            return sql;
        }

        String result = sql;
        for (Object obj : paramQueue) {
            int idx = result.indexOf("?");
            if (idx > -1) {
                result = result.replaceFirst("\\?", "'" + obj + "'");
            } else {
                result += "<" + obj + "> ";
            }
        }
        return result;
    }
}

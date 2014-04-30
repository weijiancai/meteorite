package com.meteorite.core.datasource.db.util;

import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.datasource.DataSourceManager;
import com.meteorite.core.datasource.QueryBuilder;
import com.meteorite.core.datasource.db.DBDataSource;
import com.meteorite.core.datasource.db.RowMapper;
import com.meteorite.core.datasource.db.object.DBConnection;
import com.meteorite.core.datasource.db.sql.SqlBuilder;
import com.meteorite.core.datasource.persist.IPDB;
import com.meteorite.core.util.Callback;

import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JDBC模板
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class JdbcTemplate {
    private DBDataSource dataSource;
    private Connection conn;

    public JdbcTemplate() {
        this(DataSourceManager.getSysDataSource());
    }

    public JdbcTemplate(Connection conn) {
        this.conn = conn;
        try {
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public JdbcTemplate(DBDataSource dataSource) {
        this.dataSource = dataSource;
        try {
            DBConnection dbConn = dataSource.getDbConnection();
            conn = dbConn.getConnection();
            conn.setAutoCommit(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DBDataSource getDataSource() {
        return dataSource;
    }

    public List<DataMap> queryForList(String sql) throws Exception {
        return queryForList(sql, new HashMap<String, Object>());
    }

    public List<DataMap> queryForList(String sql, Map<String, Object> conditionMap) throws Exception {
        if (conditionMap == null) {
            conditionMap = new HashMap<>();
        }
        StringBuilder sb = new StringBuilder();
        if (!sql.toLowerCase().contains("where")) {
            sb.append(" WHERE 1=1");
        }
        List<String> conditionKeyList = new ArrayList<>();
        for (String key : conditionMap.keySet()) {
            conditionKeyList.add(key);
            sb.append(" AND ").append(key).append("=?");
        }
        System.out.println(sql + sb.toString());
        PreparedStatement pstmt = conn.prepareStatement(sql + sb.toString());
        int i = 1;
        for (String key : conditionKeyList) {
            pstmt.setObject(i++, conditionMap.get(key));
        }
        ResultSet rs = pstmt.executeQuery();
        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();
//        System.out.println("------------------------------------------------");
        List<DataMap> list = new ArrayList<>();
        DataMap map;
        while (rs.next()) {
            map = new DataMap();
            for (i = 1; i <= columnCount; i++) {
                Object obj = rs.getObject(i);
                map.put(md.getColumnLabel(i), obj);
//                System.out.println(obj + "  >> " + md.getColumnLabel(i) + " = " + md.getColumnName(i) + " = " + (obj == null ? "" : obj.getClass().toString()));
            }
//            System.out.println("-------------------------------------------");
            list.add(map);
        }
        rs.close();

        return list;
    }

    public List<DataMap> queryForList(QueryBuilder builder, int page, int rows) throws SQLException {
        System.out.println(builder.toLog());
        SqlBuilder sqlBuilder = builder.sqlBuilder();
        return queryForList(sqlBuilder.getPageSql(page, rows), builder.getParamsValue());
    }

    public List<DataMap> queryForList(String sql, Object[] paramValues) throws SQLException {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        for (int i = 0; i < paramValues.length; i++) {
            pstmt.setObject(i + 1, paramValues[i]);
        }

        ResultSet rs = pstmt.executeQuery();
        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();

        List<DataMap> list = new ArrayList<>();
        DataMap map;
        while (rs.next()) {
            map = new DataMap();
            for (int i = 1; i <= columnCount; i++) {
                Object obj = rs.getObject(i);
                map.put(md.getColumnLabel(i), obj);
            }
            list.add(map);
        }
        rs.close();

        return list;
    }

    public QueryResult<Map<String, Object>> queryForList(String sql, String conditions, List values, int page, int rows) throws Exception {
        QueryResult<Map<String, Object>> result = new QueryResult<Map<String, Object>>();

        PreparedStatement pstmt;
        ResultSet rs;
        int i = 1;

        // 执行count
        conditions = (conditions == null ? "" : " where "  + conditions);
        String countSql = "SELECT count(1) " + sql.substring(sql.toLowerCase().indexOf("from")) + conditions;
        pstmt = conn.prepareStatement(countSql);
        if (values != null) {
            for (Object obj : values) {
                pstmt.setObject(i++, obj);
            }
        }
        rs  = pstmt.executeQuery();
        if (rs.next()) {
            result.setTotal(rs.getInt(1));
        }
        rs.close();
        pstmt.close();

        if(page <= 0) {
            page = 1;
        }
        sql += conditions + " limit " + (page -1) * rows + "," + rows;

        System.out.println(sql);
        pstmt = conn.prepareStatement(sql);

        i = 1;
        if (values != null) {
            for (Object obj : values) {
                pstmt.setObject(i++, obj);
            }
        }
        rs = pstmt.executeQuery();
        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();
        //        System.out.println("------------------------------------------------");
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map;
        while (rs.next()) {
            map = new HashMap<>();
            for (i = 1; i <= columnCount; i++) {
                Object obj = rs.getObject(i);
                map.put(md.getColumnLabel(i), obj);
//                map.put(md.getTableName(i) + "." + md.getColumnName(i), obj);
                //                System.out.println(obj + "  >> " + md.getColumnLabel(i) + " = " + md.getColumnName(i) + " = " + (obj == null ? "" : obj.getClass().toString()));
            }
            //            System.out.println("-------------------------------------------");
            list.add(map);
        }
        rs.close();
        pstmt.close();

        result.setRows(list);

        return result;
    }

    public Map<String, Object> queryForMap(String sql, Object... values) throws Exception {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        if (null != values && values.length > 0) {
            int i = 1;
            for (Object obj : values) {
                pstmt.setObject(i++, obj);
            }
        }
        ResultSet rs = pstmt.executeQuery();
        Map<String, Object> map = new HashMap<>();
        while (rs.next()) {
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                map.put(rs.getMetaData().getColumnLabel(i), rs.getObject(i));
            }
        }
        rs.close();
        pstmt.close();

        return map;
    }

    public void query(String sql, Callback<ResultSet> callback, Object... values) throws Exception {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        if (null != values && values.length > 0) {
            int i = 1;
            for (Object obj : values) {
                pstmt.setObject(i++, obj);
            }
        }
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            callback.call(rs);
        }
        rs.close();
        pstmt.close();
    }

    public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... values) throws Exception {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        if (null != values && values.length > 0) {
            int i = 1;
            for (Object obj : values) {
                pstmt.setObject(i++, obj);
            }
        }
        ResultSet rs = pstmt.executeQuery();
        List<T> list = new ArrayList<>();
        T t;
        while (rs.next()) {
            t = rowMapper.mapRow(rs);
            list.add(t);
        }
        rs.close();
        pstmt.close();

        return list;
    }

    public void save(IPDB po) throws Exception {
        Map<String, Map<String, Object>> map = po.getPDBMap();
        for (String table : map.keySet()) {
            save(map.get(table), table);
        }
    }

    public void save(Map<String, Object> params, String table)  throws Exception {
        try {
            StringBuilder sql = new StringBuilder("INSERT INTO " + table + " (");

            List<String> keyList = new ArrayList<>();

            String values = "";
            int i = 0;
            for(String key : params.keySet()) {
                sql.append(key);
                values += "?";
                if(++i < params.size()) {
                    sql.append(",");
                    values += ",";
                }
                keyList.add(key);
            }
            sql.append(") VALUES (").append(values).append(")");
            String outSql = sql.toString();
            Object obj;
            for (String key : params.keySet()) {
                obj = params.get(key);
                outSql = outSql.replaceFirst("\\?", obj == null ? "' '" : "'" + obj.toString() + "'");
            }
            System.out.println(outSql);
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            i = 1;
            for (String key : keyList) {
                pstmt.setObject(i++, params.get(key));
            }
            pstmt.executeUpdate();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
            if(null != conn) {
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            throw e;
        }
    }

    public void delete(Map<String, Object> params, String table)  throws Exception {
        try {
            StringBuilder sql = new StringBuilder("DELETE FROM " + table + " WHERE ");

            List<String> keyList = new ArrayList<>();

            int i = 0;
            for(String key : params.keySet()) {
                sql.append(key).append("=?");
                if(++i < params.size()) {
                    sql.append(" AND ");
                }
                keyList.add(key);
            }
            System.out.println(sql);
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            i = 1;
            for (String key : keyList) {
                pstmt.setObject(i++, params.get(key));
            }
            pstmt.executeUpdate();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
            if(null != conn) {
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            throw e;
        }
    }

    public void close() {
        if(null != conn) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void clearTable(String... tables) throws SQLException {
        for (String table : tables) {
            String sql = "DELETE FROM " + table;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
        }
    }

    public void update(Map<String, Object> valueMap, Map<String, Object> conditionMap, String tableName) throws Exception {
        try {
            StringBuilder sql = new StringBuilder("UPDATE " + tableName + " SET ");

            List<String> keyList = new ArrayList<>();
            List<String> conditionKeyList = new ArrayList<>();

            int i = 0;
            for(String key : valueMap.keySet()) {
                sql.append(key).append("=?");
                if(++i < valueMap.size()) {
                    sql.append(",");
                }
                keyList.add(key);
            }
            sql.append(" WHERE 1=1 ");
            for (String key : conditionMap.keySet()) {
                sql.append("AND ").append(key).append("=?");
                conditionKeyList.add(key);
            }
            System.out.println(sql);
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            i = 1;
            for (String key : keyList) {
                pstmt.setObject(i++, valueMap.get(key));
            }
            for (String key : conditionKeyList) {
                pstmt.setObject(i++, conditionMap.get(key));
            }
            pstmt.executeUpdate();
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
            if(null != conn) {
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            throw e;
        }
    }

    public void commit() {
        if (null != conn) {
            try {
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(String sql) throws SQLException {
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            if(null != conn) {
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            throw e;
        }
    }
}

package com.meteorite.core.datasource.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wei_jc
 * @since  1.0.0
 */
public class QueryResult<T> implements Serializable {
    private int total;
    private int pageRows = 15;
    private List<T> rows = new ArrayList<T>();

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public int getPageRows() {
        return pageRows;
    }

    public void setPageRows(int pageRows) {
        this.pageRows = pageRows <= 0 ? 15 : pageRows;
    }

    public int getPageCount() {
        int mode = total % pageRows;
        return total / pageRows + (mode > 0 ? 1 : 0);
    }
}

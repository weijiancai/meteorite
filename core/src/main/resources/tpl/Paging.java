package tpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页
 *
 * @author wei_jc
 * @since  1.0.0
 */
public class Paging<T> implements Serializable {
    private int total;
    private int pageRows = 15;
    private List<T> rows = new ArrayList<T>();

    public Paging() {
    }

    public Paging(int total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Paging(int total, int pageRows, List<T> rows) {
        this.total = total;
        this.pageRows = pageRows;
        this.rows = rows;
    }

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

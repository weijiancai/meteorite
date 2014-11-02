package com.meteorite.core.datasource.eventdata;

import com.meteorite.core.observer.EventData;

/**
 * 执行Sql语句事件数据
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class SqlExecuteEventData extends EventData {
    private String sql;
    private boolean isSuccess;
    private Exception exception;

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}

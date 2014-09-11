package com.meteorite.core.datasource.request;

import com.meteorite.core.datasource.db.DatabaseType;

/**
 * 导出数据库DDL
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class ExpDbDdlRequest extends BaseRequest {
    private String saveFilePath; // 保存ddl文件的路径
    private DatabaseType expDbType;

    public String getSaveFilePath() {
        return saveFilePath;
    }

    public void setSaveFilePath(String saveFilePath) {
        this.saveFilePath = saveFilePath;
    }

    public DatabaseType getExpDbType() {
        return expDbType;
    }

    public void setExpDbType(DatabaseType expDbType) {
        this.expDbType = expDbType;
    }
}

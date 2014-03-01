package com.meteorite.core.datasource.file;

import com.meteorite.core.datasource.DataSource;
import com.meteorite.core.datasource.DataSourceType;
import com.meteorite.core.meta.model.Meta;

/**
 * 文件系统数据源
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class FileDataSource implements DataSource {
    @Override
    public String getName() {
        return null;
    }

    @Override
    public DataSourceType getType() {
        return DataSourceType.FILE_SYSTEM;
    }

    @Override
    public Meta getProperties() {
        return null;
    }
}

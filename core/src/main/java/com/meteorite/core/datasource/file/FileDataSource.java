package com.meteorite.core.datasource.file;

import com.meteorite.core.datasource.*;
import com.meteorite.core.datasource.db.QueryResult;
import com.meteorite.core.datasource.persist.IPDB;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.model.INavTreeNode;
import com.meteorite.core.model.ITreeNode;
import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.component.form.ICanQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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

    @Override
    public QueryResult<DataMap> retrieve(Meta meta, List<ICanQuery> queryList, int page, int rows) throws SQLException {
        return null;
    }

    @Override
    public void delete(Meta meta, String... keys) throws Exception {

    }

    @Override
    public INavTreeNode getNavTree() {
        return null;
    }

    @Override
    public void load() throws Exception {

    }

    @Override
    public QueryResult<DataMap> retrieve(QueryBuilder queryBuilder, int page, int rows) throws SQLException {
        return null;
    }

    @Override
    public ResourceItem getResource(String path) {
        return null;
    }

    @Override
    public void save(Map<String, IValue> valueMap) throws Exception {

    }

    @Override
    public void save(IPDB pdb) throws Exception {

    }

    @Override
    public boolean isAvailable() {
        return false;
    }
}

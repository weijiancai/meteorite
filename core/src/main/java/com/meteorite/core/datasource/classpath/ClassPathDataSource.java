package com.meteorite.core.datasource.classpath;

import com.meteorite.core.datasource.*;
import com.meteorite.core.datasource.db.QueryResult;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.model.INavTreeNode;
import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.component.form.ICanQuery;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 类路径数据源
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class ClassPathDataSource implements DataSource {
    private static ClassPathDataSource dataSource;
    private ClassPathLoader loader;

    private ClassPathDataSource() {
        this.loader = ClassPathLoader.getLoader();
    }

    public static ClassPathDataSource getInstance() {
        if (dataSource == null) {
            dataSource = new ClassPathDataSource();
        }

        return dataSource;
    }

    @Override
    public String getName() {
        return "classpath";
    }

    @Override
    public DataSourceType getType() {
        return DataSourceType.CLASS_PATH;
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
    public INavTreeNode getNavTree() throws Exception {
        return loader.getNavTree();
    }

    @Override
    public void load() throws Exception {
        loader.load();
    }

    @Override
    public QueryResult<DataMap> retrieve(QueryBuilder queryBuilder, int page, int rows) throws SQLException {
        return null;
    }

    @Override
    public ResourceItem getResource(String path) {
        return loader.getResource(path);
    }

    @Override
    public void save(Map<String, IValue> valueMap) throws Exception {

    }

    @Override
    public String toString() {
        return loader.getBaseDir();
    }
}

package com.meteorite.core.datasource.classpath;

import com.meteorite.core.datasource.*;
import com.meteorite.core.datasource.db.QueryResult;
import com.meteorite.core.datasource.persist.IPDB;
import com.meteorite.core.datasource.request.IRequest;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.observer.Observer;
import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.component.form.ICanQuery;
import org.apache.log4j.Logger;

import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 类路径数据源
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class ClassPathDataSource extends DataSource {
    private static final Logger log = Logger.getLogger(ClassPathDataSource.class);

    private static ClassPathDataSource dataSource;
    private ClassPathLoader loader;

    private ClassPathDataSource() {
        this.loader = ClassPathLoader.getLoader();
    }

    public static ClassPathDataSource getInstance() {
        if (dataSource == null) {
            dataSource = new ClassPathDataSource();
            try {
                dataSource.load();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
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
    public VirtualResource getRootResource() {
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
    public void delete(String id) throws Exception {

    }

    @Override
    public ITreeNode getNavTree() throws Exception {
        return loader.getNavTree();
    }

    @Override
    public ITreeNode getNavTree(String parent) throws Exception {
        return loader.getResource(parent);
    }

    @Override
    public List<ITreeNode> getChildren(String parent) throws Exception {
        return null;
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
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        return loader.getResource(path);
    }

    @Override
    public void save(Map<String, IValue> valueMap) throws Exception {

    }

    @Override
    public void save(IPDB pdb) throws Exception {

    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public void write(String id, OutputStream os) throws Exception {

    }

    @Override
    public VirtualResource findResourceByPath(String path) {
        return null;
    }

    @Override
    public List<VirtualResource> findResourcesByPath(String path) {
        return null;
    }

    @Override
    public String toString() {
        return loader.getBaseDir();
    }

    @Override
    public VirtualResource get(IRequest request) {
        return null;
    }

    @Override
    public void post(IRequest request) {

    }

    @Override
    public void put(IRequest request) {

    }

    @Override
    public void delete(IRequest request) {

    }

    @Override
    public void registerObserver(Observer observer) {

    }

    @Override
    public void removeObserver(Observer observer) {

    }

    @Override
    public void notifyObserver() {

    }
}

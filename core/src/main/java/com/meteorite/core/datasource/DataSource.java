package com.meteorite.core.datasource;

import com.meteorite.core.datasource.db.QueryResult;
import com.meteorite.core.datasource.persist.IPDB;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.model.INavTreeNode;
import com.meteorite.core.model.ITreeNode;
import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.component.form.ICanQuery;

import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 数据源接口
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface DataSource {
    /**
     * 获得数据源名称
     *
     * @return 返回数据源名称
     */
    String getName();

    /**
     * 获得数据源类型
     *
     * @return 返回数据源类型
     */
    DataSourceType getType();

    /**
     * 获得数据源连接属性
     *
     * @return 返回数据源连接属性
     */
    Meta getProperties();

    /**
     * 检索数据
     *
     * @param meta 元数据
     * @param queryList 查询条件列表
     * @return 返回查询结果
     */
    QueryResult<DataMap> retrieve(Meta meta, List<ICanQuery> queryList, int page, int rows) throws SQLException;

    /**
     * 删除数据
     *
     * @param meta 元数据
     * @param keys 主键值
     * @throws Exception
     */
    void delete(Meta meta, String... keys) throws Exception;

    /**
     * 删除数据
     *
     * @param id 资源ID
     * @throws Exception
     */
    void delete(String id) throws Exception;

    /**
     * 获得导航树
     *
     * @return 返回导航树
     */
    INavTreeNode getNavTree() throws Exception;

    /**
     * 获得某个节点下的导航树
     *
     * @param parent 父节点
     * @return 返回导航树
     * @throws Exception
     */
    INavTreeNode getNavTree(String parent) throws Exception;

    /**
     * 获得某个节点下的孩子节点
     *
     * @param parent 父节点
     * @return 返回孩子节点
     * @throws Exception
     */
    List<ITreeNode> getChildren(String parent) throws Exception;

    /**
     * 加载数据源
     *
     * @throws Exception
     */
    void load() throws Exception;

    QueryResult<DataMap> retrieve(QueryBuilder queryBuilder, int page, int rows) throws SQLException;

    ResourceItem getResource(String path);

    void save(Map<String, IValue> valueMap) throws Exception;

    void save(IPDB pdb) throws Exception;

    /**
     * 是否可用
     *
     * @return 如果可以获得数据源连接，返回true，否则返回false
     */
    boolean isAvailable();

    /**
     * 将资源内容写入到输出流中
     *
     * @param id 资源id
     * @param os 输出流
     * @since 1.0.0
     */
    void write(String id, OutputStream os) throws Exception;
}

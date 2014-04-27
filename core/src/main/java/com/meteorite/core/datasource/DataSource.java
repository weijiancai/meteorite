package com.meteorite.core.datasource;

import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.model.INavTreeNode;
import com.meteorite.core.model.ITreeNode;
import com.meteorite.fxbase.ui.component.form.ICanQuery;

import java.sql.SQLException;
import java.util.List;

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
    List<DataMap> retrieve(Meta meta, List<ICanQuery> queryList) throws SQLException;

    /**
     * 删除数据
     *
     * @param meta 元数据
     * @param keys 主键值
     * @throws Exception
     */
    void delete(Meta meta, String... keys) throws Exception;

    /**
     * 获得导航树
     *
     * @return 返回导航树
     */
    INavTreeNode getNavTree() throws Exception;
}

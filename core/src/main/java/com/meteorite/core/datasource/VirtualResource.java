package com.meteorite.core.datasource;

import com.meteorite.core.datasource.db.QueryResult;
import com.meteorite.core.datasource.persist.IPDB;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.ui.model.View;
import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.component.form.ICanQuery;

import java.util.List;
import java.util.Map;

/**
 * 虚拟资源
 *
 * @author wei_jc
 * @since 1.0.0
 */
public abstract class VirtualResource {
    /**
     * 获得资源ID，格式 dsName://path, dsName【数据源名称】 path 【资源相对路径】
     *
     * @return 返回资源ID
     */
    public String getId() {
        return getDataSource().getName() + "://" + getPath();
    }
    /**
     * 获得资源的名称
     *
     * @return 返回资源的名称
     */
    public abstract String getName();

    public abstract String getPath();

    public abstract String getUrl();

    public abstract ResourceType getResourceType();

    /**
     * 获得数据类型
     *
     * @return 获得数据类型
     */
    public abstract MetaDataType getDataType();

    /**
     * 获得数据源
     *
     * @return 返回数据源
     */
    public abstract DataSource getDataSource();

    /**
     * 获得资源的视图信息
     *
     * @return 返回资源视图
     */
    public abstract View getView();

    /**
     * 获得用来显示的名称，默认为资源名称
     *
     * @return 返回显示名称
     */
    public String getDisplayName() {
        return getName();
    }

    public String getExtension() {
        String name = getName();
        int index = name.lastIndexOf('.');
        if (index < 0) return null;
        return name.substring(index + 1);
    }

    public String getNameWithoutExtension() {
        String name = getName();
        int index = name.lastIndexOf('.');
        if (index < 0) return name;
        return name.substring(0, index);
    }

    public abstract VirtualResource getParent();

    public abstract List<VirtualResource> getChildren() throws Exception;

    public VirtualResource findChild(String name) throws Exception {
        List<VirtualResource> children = getChildren();
        if (children == null) return null;
        for (VirtualResource child : children) {
            if (child.nameEquals(name)) {
                return child;
            }
        }
        return null;
    }

    public abstract void delete();

    public boolean exists() {
        return isValid();
    }

    public abstract boolean isValid();

    /**
     * 获得资源的原始对象，有些对象会包装成资源对象，如DBObject，那么返回的原始对象，就是DBObject对象的子类，如DBTable
     *
     * @return 返回资源的原始对象
     */
    public abstract Object getOriginalObject();

    protected boolean nameEquals(String name) {
        return getName().equals(name);
    }

    // ===================================== 数据内容处理 =====================================================

    /**
     * 检索数据
     *
     * @param meta 元数据
     * @param queryList 查询条件列表
     * @return 返回查询结果
     */
    public abstract QueryResult<DataMap> retrieve(Meta meta, List<ICanQuery> queryList, int page, int rows) throws Exception;

    /**
     * 检索数据
     *
     * @param queryBuilder 查询Builder
     * @param page 第几页
     * @param rows 每页行数
     * @return 返回查询结果
     * @throws Exception
     */
    public abstract QueryResult<DataMap> retrieve(QueryBuilder queryBuilder, int page, int rows) throws Exception;

    /**
     * 保存数据
     *
     * @param valueMap 值Map
     * @throws Exception
     */
    public abstract void save(Map<String, IValue> valueMap) throws Exception;

    /**
     * 保存数据
     *
     * @param pdb 持久化对象信息
     * @throws Exception
     */
    public abstract void save(IPDB pdb) throws Exception;

    /**
     * 删除数据
     *
     * @param meta 元数据
     * @param keys 主键值
     * @throws Exception
     */
    public abstract void delete(Meta meta, String... keys) throws Exception;

    /**
     * 更新数据
     *
     * @param valueMap 值Map
     * @param conditionMap 条件Map
     * @param tableName 表名
     * @throws Exception
     */
    public abstract void update(Map<String, Object> valueMap, Map<String, Object> conditionMap, String tableName) throws Exception;
}

package com.meteorite.core.datasource;

import com.meteorite.core.datasource.db.object.DBColumn;
import com.meteorite.core.util.UNumber;
import com.meteorite.core.util.UObject;
import com.meteorite.core.util.UString;
import com.meteorite.core.util.UUIDUtil;

import java.util.HashMap;

/**
 * 数据库查询结果集,key会转换成小写
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class DataMap extends HashMap<String,Object> {
    /**
     * 状态
     */
    public static enum STATUS {
        NEW, // 新增
        MODIFY, // 修改
        NOT_MODIFY // 未修改
    }

    private STATUS status = STATUS.NOT_MODIFY;
    private String uid; // 标识此DataMap的唯一值

    public DataMap() {
        uid = UUIDUtil.getUUID();
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    /**
     * 获得key对应的字符串值
     *
     * @param key Key
     * @return 返回key对应的字符串值
     * @since 1.0.0
     */
    public String getString(String key) {
        return UObject.toString(get(key));
    }

    /**
     * 将key，value添加到结果集中，key会转成小写
     *
     * @param key key
     * @param value key对应的值
     * @return 返回key对应的值
     * @since 1.0.0
     */
    @Override
    public Object put(String key, Object value) {
        if (UString.isEmpty(key)) {
            return null;
        }
        return super.put(key.toLowerCase(), value);
    }


    /**
     * 根据key查找值,key会转成小写
     *
     * @param key key
     * @return 返回key对应的值
     * @since 1.0.0
     */
    public Object get(String key) {
        return super.get(key.toLowerCase());
    }

    /**
     * 根据数据库列查找值
     *
     * @param column 数据库列
     * @return 返回key对应的值
     * @since 1.0.0
     */
    public Object get(DBColumn column) {
        return get(column.getName());
    }

    /**
     * 获得key对应的整数值
     *
     * @param key Key
     * @return 返回key对应的整数值
     * @since 1.0.0
     */
    public int getInt(String key) {
        return UNumber.toInt(getString(key));
    }

    public long getLong(String key) {
        return UNumber.toLong(getString(key));
    }

    /**
     * 获得key对应的Boolean值
     *
     * @param key Key
     * @return 返回key对应的Boolean值
     * @since 1.0.0
     */
    public boolean getBoolean(String key) {
        return UString.toBoolean(getString(key));
    }
}

package com.meteorite.core.datasource.db.object.impl;

import com.meteorite.core.datasource.db.object.DBColumn;
import com.meteorite.core.datasource.db.object.DBObject;
import com.meteorite.core.datasource.db.object.DBObjectType;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.model.ITreeNode;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 数据库列信息实现类
 *
 * @author wei_jc
 * @since 1.0.0
 */
@XmlRootElement(name = "Column")
public class DBColumnImpl extends DBObjectImpl implements DBColumn {
    private int maxLength;
    private boolean isPk;
    private boolean isFk;
    private MetaDataType dataType;

    public DBColumnImpl() {
        setObjectType(DBObjectType.COLUMN);
    }

    @Override
    public List<ITreeNode> getChildren() {
        return null;
    }

    @Override
    public MetaDataType getDataType() {
        return dataType;
    }

    public void setDataType(MetaDataType dataType) {
        this.dataType = dataType;
    }

    public boolean isPk() {
        return isPk;
    }

    public void setPk(boolean isPk) {
        this.isPk = isPk;
    }

    public boolean isFk() {
        return isFk;
    }

    public void setFk(boolean isFk) {
        this.isFk = isFk;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
}

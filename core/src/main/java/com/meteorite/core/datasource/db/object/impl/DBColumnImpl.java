package com.meteorite.core.datasource.db.object.impl;

import com.meteorite.core.datasource.db.DBIcons;
import com.meteorite.core.datasource.db.object.DBColumn;
import com.meteorite.core.datasource.db.object.enums.DBObjectType;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.util.UString;

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
    private String dbDataType;
    private int precision;
    private int scale;
    private MetaDataType dataType;
    private DBColumn fkColumn;

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

    @Override
    public DBColumn getFkColumn() {
        return fkColumn;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public String getDbDataType() {
        return dbDataType;
    }

    public void setDbDataType(String dbDataType) {
        this.dbDataType = dbDataType;
    }

    @Override
    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    @Override
    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public void setFkColumn(DBColumn fkColumn) {
        this.fkColumn = fkColumn;
    }

    @Override
    public String getIcon() {
        if (isPk && isFk) {
            return DBIcons.DBO_COLUMN_PKFK;
        } else if (isPk) {
            return DBIcons.DBO_COLUMN_PK;
        } else if (isFk) {
            return DBIcons.DBO_COLUMN_FK;
        }
        return super.getIcon();
    }

    @Override
    public String toString() {
        StringBuilder sb =  new StringBuilder();
        sb.append(getName().toLowerCase()).append(" - ").append(dbDataType.toLowerCase());
        if (maxLength > 0) {
            sb.append("(").append(maxLength).append(")");
        } else if (precision > 0) {
            sb.append("(").append(precision);
            if (scale > 0) {
                sb.append(",").append(scale);
            }
            sb.append(")");
        }
        if (UString.isNotEmpty(getComment()) && !getSchema().getName().equalsIgnoreCase("information_schema")) {
            sb.append(" ").append(getComment());
        }
        return sb.toString();
    }


}

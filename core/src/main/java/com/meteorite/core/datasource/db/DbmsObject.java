package com.meteorite.core.datasource.db;

import com.meteorite.core.datasource.DataSourceManager;
import com.meteorite.core.datasource.db.object.DBSchema;
import com.meteorite.core.datasource.db.object.enums.DBObjectType;
import com.meteorite.core.datasource.db.util.JdbcTemplate;
import com.meteorite.core.datasource.persist.MetaPDBFactory;

import java.util.Date;

/**
 * 数据库对象
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class DbmsObject {
    /** ID */
    private String id;
    /** 名称 */
    private String name;
    /** 注释 */
    private String dbComment;
    /** 对象类型 */
    private DBObjectType dbObjType;
    /** 父ID */
    private String pid;
    /** 数据类型 */
    private String dataType;
    /** 位置 */
    private int position;
    /** 默认值 */
    private String defaultValue;
    /** 可空 */
    private boolean isNullable;
    /** 最大长度 */
    private int maxLength;
    /** 数值总长度 */
    private int numericPrecision;
    /** 数值小数位数 */
    private int numericScale;
    /** 是否主键 */
    private boolean isPk;
    /** 是否外键 */
    private boolean isFk;
    /** 外键列ID */
    private String fkColumnId;
    /** 录入时间 */
    private Date inputDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDbComment() {
        return dbComment;
    }

    public void setDbComment(String dbComment) {
        this.dbComment = dbComment;
    }

    public DBObjectType getDbObjType() {
        return dbObjType;
    }

    public void setDbObjType(DBObjectType dbObjType) {
        this.dbObjType = dbObjType;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public void setNullable(boolean isNullable) {
        this.isNullable = isNullable;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public int getNumericPrecision() {
        return numericPrecision;
    }

    public void setNumericPrecision(int numericPrecision) {
        this.numericPrecision = numericPrecision;
    }

    public int getNumericScale() {
        return numericScale;
    }

    public void setNumericScale(int numericScale) {
        this.numericScale = numericScale;
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

    public String getFkColumnId() {
        return fkColumnId;
    }

    public void setFkColumnId(String fkColumnId) {
        this.fkColumnId = fkColumnId;
    }

    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    public static DbmsObject from(DBSchema schema) throws Exception {
        DbmsObject obj = new DbmsObject();
        obj.setId(schema.getId());
        obj.setName(schema.getName());
        obj.setDbObjType(schema.getObjectType());
        obj.setInputDate(new Date());
        return obj;
    }
}
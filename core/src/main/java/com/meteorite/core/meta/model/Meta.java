package com.meteorite.core.meta.model;

import com.meteorite.core.datasource.DataSource;
import com.meteorite.core.datasource.db.object.DBColumn;
import com.meteorite.core.datasource.db.object.DBObject;
import com.meteorite.core.datasource.db.object.DBTable;
import com.meteorite.core.datasource.db.object.loader.DBDataset;
import com.meteorite.core.exception.MessageException;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.meta.annotation.MetaElement;
import com.meteorite.core.meta.annotation.MetaFieldElement;
import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.ui.model.View;
import com.meteorite.fxbase.ui.component.form.ICanQuery;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 元数据信息
 *
 * @author wei_jc
 * @version 1.0.0
 */
@XmlRootElement
@XmlType(propOrder = {"id", "name", "displayName", "valid", "sortNum", "inputDate", "desc", "fields"})
@MetaElement(displayName = "元数据")
public class Meta {
    private String id;
    private String name;
    private String displayName;
    private String desc;
    private boolean isValid;
    private int sortNum;
    private Date inputDate;

    private List<MetaField> fields = new ArrayList<>();
    private DBDataset dbTable;
    private DataSource dataSource;

    private ObjectProperty<ObservableList<DataMap>> dataList = new SimpleObjectProperty<>();

    public Meta() {}

    public Meta(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "ID", dataType = MetaDataType.NUMBER, sortNum = 10)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "名称", sortNum = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "显示名", sortNum = 30)
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @MetaFieldElement(displayName = "描述", sortNum = 40)
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @XmlAttribute
    @MetaFieldElement(name = "valid", displayName = "是否有效", dataType=MetaDataType.BOOLEAN, dictId = "EnumBoolean", sortNum = 50)
    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "排序号", sortNum = 60)
    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    @XmlAttribute
    @MetaFieldElement(displayName = "录入时间", sortNum = 70)
    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    @XmlElement(name = "MetaField")
    @XmlElementWrapper(name = "fields")
    public List<MetaField> getFields() {
        return fields;
    }

    public void setFields(List<MetaField> fields) {
        this.fields = fields;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DBDataset getDbTable() {
        if (dbTable != null) {
            return dbTable;
        }
        DBColumn column = null;
        for (MetaField field : getFields()) {
            column = field.getColumn();
            if (column != null) {
                break;
            }
        }
        assert column != null;
        DBObject table = column.getParent();

        return (DBDataset) table;
    }

    public void setDbTable(DBDataset dbTable) {
        this.dbTable = dbTable;
    }

    public void setFieldValue(String fieldName, String value) {
        for (MetaField field : fields) {
            if (field.getName().equals(fieldName)) {
                field.setValue(value);
                break;
            }
        }
    }

    public String getFieldValue(String fieldName) {
        for (MetaField field : fields) {
            if (field.getName().equals(fieldName)) {
                return field.getValue();
            }
        }

        return null;
    }

    /**
     * 获得主键元数据字段列表
     *
     * @return 返回主键元字段列表
     */
    public List<MetaField> getPkFields() {
        List<MetaField> result = new ArrayList<>();
        for (MetaField field : fields) {
            if (field.getColumn().isPk()) {
                result.add(field);
            }
        }
        return result;
    }

    public ObjectProperty<ObservableList<DataMap>> dataListProperty() {
        return dataList;
    }

    public List<DataMap> getDataList() {
        return dataList.get();
    }

    public void setDataList(List<DataMap> list) {
        dataList.set(FXCollections.observableList(list));
    }

    /**
     * 获得行数据Map
     *
     * @param row 行号
     * @return 返回行数据Map
     */
    public DataMap getRowData(int row) {
        return dataList.get().get(row);
    }

    public List<DataMap> query(List<ICanQuery> queryList, int page, int rows) throws Exception {
        List<DataMap> result = dataSource.retrieve(this, queryList, page, rows);
        setDataList(result);
        return result;
    }

    /**
     * 删除行数据
     *
     * @param row 行号
     * @throws Exception
     */
    public void delete(int row) throws Exception {
        if(row < 0) {
            throw new MessageException("请选择要删除的行数据！");
        }
        DataMap rowData = getRowData(row);
        List<MetaField> pkFields = getPkFields();
        String[] values = new String[pkFields.size()];
        for (int i = 0; i < pkFields.size(); i++) {
            MetaField field = pkFields.get(i);
            values[i] = rowData.getString(field.getName());
        }
        dataSource.delete(this, values);
        dataList.get().remove(row);
    }

    public View getFormView() {
        return null;
    }
}

package com.meteorite.core.meta.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.meteorite.core.datasource.DataSource;
import com.meteorite.core.datasource.QueryBuilder;
import com.meteorite.core.datasource.VirtualResource;
import com.meteorite.core.datasource.db.QueryResult;
import com.meteorite.core.datasource.db.object.DBColumn;
import com.meteorite.core.datasource.db.object.DBDataset;
import com.meteorite.core.datasource.persist.IPDB;
import com.meteorite.core.exception.MessageException;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.meta.action.MUAction;
import com.meteorite.core.meta.annotation.MetaElement;
import com.meteorite.core.meta.annotation.MetaFieldElement;
import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.ui.ViewManager;
import com.meteorite.core.ui.model.View;
import com.meteorite.core.util.FreeMarkerConfiguration;
import com.meteorite.core.util.FreeMarkerTemplateUtils;
import com.meteorite.core.util.UObject;
import com.meteorite.fxbase.ui.IValue;
import com.meteorite.fxbase.ui.component.form.ICanQuery;
import freemarker.template.TemplateException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.annotation.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * 元数据信息
 *
 * @author wei_jc
 * @version 1.0.0
 */
@XmlRootElement
@XmlType(propOrder = {"id", "name", "displayName", "valid", "sortNum", "inputDate", "description", "fields"})
@MetaElement(displayName = "元数据")
public class Meta {
    private String id;
    private String name;
    private String displayName;
    private String description;
    private boolean isValid;
    private int sortNum;
    private Date inputDate;

    private List<MetaField> fields = new ArrayList<MetaField>();
    private List<MetaReference> references = new ArrayList<MetaReference>();
    private Set<Meta> children = new HashSet<Meta>();
    private DBDataset dbTable;
    private DataSource dataSource;
    private VirtualResource resource;

    private ObjectProperty<ObservableList<DataMap>> dataList = new SimpleObjectProperty<ObservableList<DataMap>>();
    private IntegerProperty totalRows = new SimpleIntegerProperty(0); // 总行数
    private IntegerProperty pageCount = new SimpleIntegerProperty(0); // 总页数
    private IntegerProperty pageRows = new SimpleIntegerProperty(15); // 每页行数

    private List<DataMap> insertCache = new ArrayList<DataMap>(); // 新增缓存
    private List<MUAction> actionList = new ArrayList<MUAction>(); // Action List

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
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @JSONField(serialize = false)
    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public VirtualResource getResource() {
        return resource;
    }

    public void setResource(VirtualResource resource) {
        this.resource = resource;
    }

    @JSONField(serialize = false)
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

        return column.getDataset();
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
    @JSONField(serialize = false)
    public List<MetaField> getPkFields() {
        List<MetaField> result = new ArrayList<MetaField>();
        for (MetaField field : fields) {
            if (field.getColumn().isPk()) {
                result.add(field);
            }
        }
        return result;
    }

    public MetaField getFieldByDbColumn(DBColumn column) {
        for (MetaField field : fields) {
            if (field.getColumn() == column) {
                return field;
            }
        }

        return null;
    }

    @JSONField(serialize = false)
    public ObjectProperty<ObservableList<DataMap>> dataListProperty() {
        return dataList;
    }

    public List<DataMap> getDataList() {
        return dataList.get();
    }

    public void setDataList(List<DataMap> list) {
        dataList.set(FXCollections.observableList(list));
    }

    @JSONField(serialize = false)
    public IntegerProperty totalRowsProperty() {
        return totalRows;
    }

    public int getTotalRows() {
        return totalRows.get();
    }

    public void setTotalRows(int totalRows) {
        this.totalRows.set(totalRows);
    }

    public int getPageCount() {
        return pageCount.get();
    }

    @JSONField(serialize = false)
    public IntegerProperty pageCountProperty() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount.set(pageCount);
    }

    public int getPageRows() {
        return pageRows.get();
    }

    @JSONField(serialize = false)
    public IntegerProperty pageRowsProperty() {
        return pageRows;
    }

    public void setPageRows(int pageRows) {
        this.pageRows.set(pageRows);
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

    public QueryResult<DataMap> query(List<ICanQuery> queryList, int page, int rows) throws Exception {
        QueryResult<DataMap> result = dataSource.retrieve(this, queryList, page, rows);
        setDataList(result.getRows());
        setTotalRows(result.getTotal());
        setPageCount(result.getPageCount());
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
            values[i] = rowData.getString(field.getColumn().getName());
        }
        dataSource.delete(this, values);
        dataList.get().remove(row);
    }

    /**
     * 获得引用的元数据信息
     *
     * @param fieldId 元字段ID
     * @return 返回引用的元数据信息
     */
    public Meta getRefMeta(String fieldId) {
        for (MetaField field : fields) {
            if (field.getRefField() != null && field.getId().equals(fieldId)) {
                return field.getRefField().getMeta();
            }
        }

        return null;
    }

    @JSONField(serialize = false)
    public View getFormView() {
        return ViewManager.getViewByName(getName() + "FormView");
    }

    @JSONField(serialize = false)
    public View getTableView() {
        return ViewManager.getViewByName(getName() + "TableView");
    }

    @JSONField(serialize = false)
    public List<MetaReference> getReferences() {
        if (references == null) {
            references = new ArrayList<MetaReference>();
        }
        return references;
    }

    public void setReferences(List<MetaReference> references) {
        this.references = references;
    }

    @JSONField(serialize = false)
    public Set<Meta> getChildren() {
        return children;
    }

    public void setChildren(Set<Meta> children) {
        this.children = children;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Meta meta = (Meta) o;

        return id.equals(meta.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public QueryResult<DataMap> query(QueryBuilder queryBuilder) throws Exception {
        QueryResult<DataMap> result = dataSource.retrieve(queryBuilder, -1, 0);
        setDataList(result.getRows());
        setTotalRows(result.getTotal());
        setPageCount(result.getPageCount());
        return result;
    }

    public void toTxtFile(File file, DataMap param) throws Exception {
        query(QueryBuilder.create(this));
        List<DataMap> dataList = getDataList();
        if (dataList == null) {
            return;
        }
        String[] colNames = param.getString("colNames").split(",");
        PrintWriter pw = new PrintWriter(file);
        for (DataMap map : dataList) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if(Arrays.binarySearch(colNames, entry.getKey()) < 0) {
                    continue;
                }
                pw.print(UObject.toString(entry.getValue()));
                if (colNames.length > 1) {
                    pw.print("\t");
                }
            }
            pw.println();
        }
        pw.flush();
        pw.close();
    }

    public void save(Map<String, IValue> valueMap) throws Exception {
        dataSource.save(valueMap);
    }
    
    public void insertRow(DataMap dataMap) {
        insertCache.add(dataMap);
    }
    
    public void save() throws Exception {
        for (final DataMap dataMap : insertCache) {
            dataSource.save(new IPDB() {
                @Override
                public Map<String, Map<String, Object>> getPDBMap() {
                    Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
                    map.put(getDbTable().getName(), dataMap);
                    return map;
                }
            });
        }


        insertCache.clear();
    }

    public MetaField getFieldByName(String fieldName) {
        for (MetaField field : fields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        return null;
    }

    public void addAction(MUAction action) {
        actionList.add(action);
    }

    public List<MUAction> getActionList() {
        return actionList;
    }

    public void update(Map<String, IValue> modifiedValueMap) {

    }

    public String genJavaBeanCode() {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("meta", this);
            return FreeMarkerTemplateUtils.processTemplateIntoString(FreeMarkerConfiguration.getInstance().getTemplate("JavaBean.ftl"), map);
        } catch (Exception e) {
            throw new RuntimeException("生成JavaBean代码失败！", e);
        }
    }
}

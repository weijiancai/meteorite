package com.meteorite.core.meta;

import com.meteorite.core.config.ProjectConfig;
import com.meteorite.core.config.SystemInfo;
import com.meteorite.core.config.SystemManager;
import com.meteorite.core.datasource.db.DBDataSource;
import com.meteorite.core.datasource.db.DBManager;
import com.meteorite.core.datasource.db.connection.ConnectionUtil;
import com.meteorite.core.datasource.db.object.DBColumn;
import com.meteorite.core.datasource.db.object.DBConnection;
import com.meteorite.core.datasource.db.object.DBTable;
import com.meteorite.core.datasource.db.util.JdbcTemplate;
import com.meteorite.core.datasource.persist.MetaPDBFactory;
import com.meteorite.core.datasource.persist.MetaRowMapperFactory;
import com.meteorite.core.meta.annotation.MetaElement;
import com.meteorite.core.meta.annotation.MetaFieldElement;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.meta.model.MetaForm;
import com.meteorite.core.meta.model.MetaFormField;
import com.meteorite.core.ui.ViewManager;
import com.meteorite.core.ui.layout.LayoutManager;
import com.meteorite.core.ui.model.View;
import com.meteorite.core.ui.model.ViewConfig;
import com.meteorite.core.util.UFile;
import com.meteorite.core.util.UIO;
import com.meteorite.core.util.UString;
import com.meteorite.core.util.jaxb.JAXBUtil;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.*;

import static com.meteorite.core.config.SystemConfig.DIR_SYSTEM;
import static com.meteorite.core.config.SystemConfig.FILE_NAME_META_FIELD_CONFIG;

/**
 * 元数据管理
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class MetaManager {
    private static Map<String, Meta> metaMap = new HashMap<>();
    private static Map<String, Meta> metaIdMap = new HashMap<>();
    private static Map<String, MetaField> fieldIdMap = new HashMap<>();
    private static List<MetaField> metaFieldList = new ArrayList<>();

    static {
        try {
            addMeta(ProjectConfig.class);
            addMeta(DBDataSource.class);
//            addMeta(Meta.class);
//            addMeta(MetaField.class);

            loadMetaFieldConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void load() throws Exception {
        SystemInfo sysInfo = SystemManager.getSystemInfo();
        Connection conn = DBManager.getConnection(DBManager.getSysDataSource()).getConnection();
        JdbcTemplate template = new JdbcTemplate(conn);
        try {
            if (sysInfo.isMetaInit()) { // ClassDef 已经初始化
                String sql = "SELECT * FROM sys_meta order by sort_num";
                List<Meta> metaList = template.query(sql, MetaRowMapperFactory.getMeta());
                for (final Meta meta : metaList) {
                    metaMap.put(meta.getName(), meta);
                    metaIdMap.put(meta.getId(), meta);
                    // 查询元数据字段
                    sql = "SELECT * FROM sys_meta_field WHERE meta_id=? order by sort_num";
                    List<MetaField> fieldList = template.query(sql, MetaRowMapperFactory.getMetaField(meta), meta.getId());
                    meta.setFields(fieldList);
                    for (MetaField field : fieldList) {
                        fieldIdMap.put(field.getId(), field);
                    }
                    metaFieldList.addAll(fieldList);

                    // 查询类Form
                    /*sql = "SELECT * FROM sys_class_form WHERE class_id=?";
                    List<ClassForm> formList = template.query(sql, ClassRowMapperFactory.getClassForm(meta), meta.getId());
                    meta.setFormList(formList);
                    // 查询类Form字段
                    sql = "SELECT * FROM sys_class_form_field a, sys_form_field_append b WHERE a.id=b.form_field_id and form_id=?";
                    for (ClassForm form : formList) {
                        List<FormField> formFieldList = template.query(sql, ClassRowMapperFactory.getFormField(form), form.getId());
                        form.setFieldList(formFieldList);
                        for (FormField field : formFieldList) {
                            classFormFieldIdMap.put(field.getId(), field);
                        }
                    }

                    // 查询类Query
                    sql = "SELECT * FROM sys_class_query WHERE class_id=?";
                    List<ClassQuery> queryList = template.query(sql, ClassRowMapperFactory.getClassQuery(meta), meta.getId());
                    meta.setClassQueryList(queryList);
                    // 查询类Form字段
                    sql = "SELECT * FROM sys_class_query_field WHERE query_id=?";
                    for (ClassQuery query : queryList) {
                        List<QueryField> queryFieldList = template.query(sql, ClassRowMapperFactory.getQueryField(query), query.getId());
                        query.setQueryFieldList(queryFieldList);
                    }

                    // 查询类Table
                    sql = "SELECT * FROM sys_class_table WHERE class_id=?";
                    List<ClassTable> tableList = template.query(sql, ClassRowMapperFactory.getClassTable(meta), meta.getId());
                    meta.setClassTableList(tableList);
                    // 查询类Table字段
                    sql = "SELECT * FROM sys_class_table_field WHERE class_table_id=?";
                    for (ClassTable table : tableList) {
                        List<TableField> tableFieldList = template.query(sql, ClassRowMapperFactory.getClassTableField(table), table.getId());
                        table.setTableFieldList(tableFieldList);
                    }

                    // 查询类Table Query
                    sql = "SELECT * FROM sys_class_table_query WHERE table_id=?";

                    // 查询类，DbmsTable关联表
                    sql = "SELECT * FROM sys_r_class_table WHERE class_id=?";
                    List<RClassDbmsTable> dbmsTableList = template.query(sql, ClassRowMapperFactory.getRClassDbmsTable(), meta.getId());
                    meta.setDbmsTableList(dbmsTableList);*/
                }
            } else {
                metaSortNum = 10;
                // 清空表sys_view_config, sys_view_layout, sys_view, sys_meta
                template.clearTable("sys_view_config", "sys_view_layout", "sys_view", "sys_meta");
                DBConnection dbConn = DBManager.getConnection(DBManager.getSysDataSource());
//                dbConn.getLoader().loadSchemas();
                for (DBTable table : dbConn.getSchema().getTables()) {
                    initMetaFromTable(template, table);
                    metaSortNum += 10;
                }

                sysInfo.setMetaInit(true);
                sysInfo.store();
            }

            template.commit();
            template.close();

        } catch (Exception e) {
            e.printStackTrace();
            conn.rollback();
            ConnectionUtil.closeConnection(conn);
        } finally {
            template.close();
        }
    }

    /**
     * 加载布局配置信息
     */
    private static void loadMetaFieldConfig() throws Exception {
        File file = new File(DIR_SYSTEM, FILE_NAME_META_FIELD_CONFIG);

        if(!file.exists()) {
            metaFieldList = new ArrayList<>();
        } else {
            InputStream is = UIO.getInputStream(file.getAbsolutePath(), UIO.FROM.FS);
            metaFieldList = JAXBUtil.unmarshalList(is, MetaField.class);
        }
    }

    public static void saveMetaFieldConfig() throws Exception {
        JAXBUtil.marshalListToFile(metaFieldList, UFile.createFile(DIR_SYSTEM, FILE_NAME_META_FIELD_CONFIG), MetaField.class);
    }

    public static void addMeta(Class<?> clazz) throws Exception {
        Meta meta = toMeta(clazz);
        metaMap.put(meta.getName(), meta);
    }

    public static void addMetaField(MetaField metaField) throws Exception {
        metaFieldList.add(metaField);
        saveMetaFieldConfig();
    }

    /**
     * 获得元数据信息
     *
     * @param metaName 元数据名称
     * @return 返回元数据信息
     */
    public static Meta getMeta(String metaName) {
        return metaMap.get(metaName);
    }

    /**
     * 获得元数据信息
     *
     * @param clazz 类信息
     * @return 返回元数据信息
     */
    public static Meta getMeta(Class<?> clazz) {
        return metaMap.get(clazz.getSimpleName());
    }

    /**
     * 根据元数据字段ID，获得元数据字段信息
     *
     * @param fieldId 元字段ID
     * @return 返回元字段信息
     * @since 1.0.0
     */
    public static MetaField getMetaField(String fieldId) {
        return fieldIdMap.get(fieldId);
    }

    /**
     * 根据元数据ID，获得元数据信息
     *
     * @param metaId 元数据ID
     * @return 返回元数据信息
     * @since 1.0.0
     */
    public static Meta getMetaById(String metaId) {
        return metaIdMap.get(metaId);
    }

    /**
     * 将注解了MetaElement的类转换为元数据对象
     *
     * @param clazz 注解了MetaElement的类对象
     * @return 返回类对象的元数据信息
     * @throws Exception 如果此类对象没有注解MetaElement，则抛出此异常
     * @since 1.0.0
     */
    public static Meta toMeta(Class<?> clazz) throws Exception {
        if (!clazz.isAnnotationPresent(MetaElement.class)) {
            throw new Exception(String.format("不能将非MetaElement的对象【%s】转化为Meta对象！", clazz.getName()));
        }

        MetaElement metaElement = clazz.getAnnotation(MetaElement.class);
        Meta meta = new Meta();
        meta.setName("##default".equals(metaElement.name()) ? clazz.getSimpleName() : metaElement.name());
        meta.setDisplayName(metaElement.displayName());
        meta.setValid(metaElement.isValid());
        meta.setDesc(metaElement.desc());
        meta.setInputDate(new Date());
        meta.setSortNum(metaElement.sortNum());

        List<MetaField> fieldList = new ArrayList<MetaField>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(MetaFieldElement.class)) {
                MetaFieldElement metaFieldElement = method.getAnnotation(MetaFieldElement.class);

                String name = method.getName().startsWith("get") ? method.getName().substring(3) : method.getName();
                MetaField metaField = new MetaField();
                metaField.setName("##default".equals(metaFieldElement.name()) ? name : metaFieldElement.name());
                metaField.setDisplayName(metaFieldElement.displayName());
                metaField.setValid(metaFieldElement.isValid());
                metaField.setDesc(metaFieldElement.desc());
                metaField.setInputDate(new Date());
                metaField.setSortNum(metaFieldElement.sortNum());
                metaField.setDataType(metaFieldElement.dataType());
                metaField.setDictId(metaFieldElement.dictId());
                if (UString.isEmpty(metaFieldElement.defaultValue())) {
                    /*Object obj = method.invoke(metaObj);
                    if (obj != null && UClass.isPrimitive(obj.getClass())) {
                        metaField.setDefaultValue(obj.toString());
                    }*/
                } else {
                    metaField.setDefaultValue(metaFieldElement.defaultValue());
                }

                fieldList.add(metaField);
            }
        }
        // 排序fields
        Collections.sort(fieldList, new Comparator<MetaField>() {
            @Override
            public int compare(MetaField o1, MetaField o2) {
                return o1.getSortNum() - o2.getSortNum();
            }
        });

        meta.setFields(fieldList);

        return meta;
    }

    public static MetaForm toForm(Meta meta) {
        MetaForm metaForm = new MetaForm();
        metaForm.setName(meta.getName() + "Form");
        metaForm.setCname(meta.getDisplayName() + "表单");
        metaForm.setInputDate(new Date());
        metaForm.setValid(true);
        metaForm.setColCount(1);
        metaForm.setColWidth(180);
        metaForm.setLabelGap(5);
        metaForm.setFieldGap(15);
        metaForm.setHgap(3);
        metaForm.setVgap(5);

        int sortNum = 0;
        List<MetaFormField> formFields = new ArrayList<>();
        for (MetaField field : meta.getFields()) {
            MetaFormField formField = new MetaFormField();
            formField.setInputDate(new Date());
            formField.setSortNum(sortNum += 10);
            formField.setValid(true);
            formField.setDisplayName(field.getDisplayName());
            formField.setDisplayStyle(DisplayStyle.TEXT_FIELD);
            formField.setForm(metaForm);
            formField.setMetaField(field);
            formField.setSingleLine(false);
            formField.setDisplay(true);
            formField.setWidth(180);

            formFields.add(formField);
        }

        metaForm.setFormFields(formFields);

        return metaForm;
    }

    public static List<MetaField> getMetaFieldList() {
        /*List<MetaField> result = new ArrayList<>();
        for(Meta meta : metaMap.values()) {
            result.addAll(meta.getFields());
        }*/
        return metaFieldList;
    }

    public static List<Meta> getMetaList() {
        return new ArrayList<>(metaMap.values());
    }

    private static int metaSortNum = 10;

    public static void initMetaFromTable(JdbcTemplate template, DBTable table) throws Exception {
        // 将表定义信息插入到元数据信息中
        Meta meta = new Meta();
        meta.setName(UString.tableNameToClassName(table.getName()));
        meta.setDisplayName(table.getComment());
        meta.setDesc(table.getComment());
        meta.setValid(true);
        meta.setInputDate(new Date());
        meta.setSortNum(metaSortNum);
        meta.setDbTable(table);

        // 插入元数据信息
        template.save(MetaPDBFactory.getMeta(meta));
        metaMap.put(meta.getName(), meta);
        metaIdMap.put(meta.getId(), meta);

        List<MetaField> fieldList = new ArrayList<>();

        // 将表列信息插入到类字段信息中
        MetaField field;
        int fieldSortNum = 0;
        for (DBColumn column : table.getColumns()) {
            field = new MetaField();
            field.setMeta(meta);
            field.setColumn(column);
            field.setName(UString.columnNameToFieldName(column.getName()));
            field.setDisplayName(column.getComment());
            field.setDesc(column.getComment());
            if (column.getName().toLowerCase().startsWith("is_")) {
                field.setDataType(MetaDataType.BOOLEAN);
            } else {
                field.setDataType(column.getDataType());
            }
//            field.setType(column.getDataType());
            field.setValid(true);
            field.setSortNum(fieldSortNum += 10);
//            initDzCategory(field);
            // 插入表sys_class_field
            template.save(MetaPDBFactory.getMetaField(field));
            fieldList.add(field);
//            classFieldIdMap.put(field.getId(), field);
        }
        meta.setFields(fieldList);

        // 创建视图
        ViewManager.createView(meta, template);
        // 插入sys_class_table信息
        /*ClassTable classTable = new ClassTable();
        classTable.setName("default");
        classTable.setColWidth(80);
        classTable.setSortNum(metaSortNum);
        classTable.setValid(true);
        classTable.setClassDefine(clazz);
        classTable.setJoinSql(" FROM " + table.getName());
        classTable.setSql("SELECT " + clazz.getColumnStr() + classTable.getJoinSql());
        template.save(ClassPDBFactory.getClassTable(classTable));
        List<ClassTable> classTableList = new ArrayList<ClassTable>();
        classTableList.add(classTable);
        clazz.setClassTableList(classTableList);

        // 插入sys_class_table_field
        List<TableField> tableFieldList = new ArrayList<TableField>();
        TableField tableField;
        fieldSortNum = 0;
        for (ClassField classField : fieldList) {
            tableField = new TableField();
            tableField.setClassTable(classTable);
            tableField.setClassField(classField);
            tableField.setDisplayName(classField.getFieldDesc());
            tableField.setAlign("left");
            tableField.setColWidth(getColWidth(classField));
            tableField.setValid(true);
            if (classField.getColumn().isPk() || classField.getColumn().isFk()) {
                tableField.setDisplay(false);
            } else {
                tableField.setDisplay(true);
            }
            tableField.setSortNum(fieldSortNum += 10);
            // 插入表
            template.save(ClassPDBFactory.getTableField(tableField));
            tableFieldList.add(tableField);
        }
        classTable.setTableFieldList(tableFieldList);

        // 插入sys_class_form信息
        initClassForm(template, clazz, fieldList, "0");
        initClassForm(template, clazz, fieldList, "1");

        // 插入sys_class_query信息
        ClassQuery classQuery = new ClassQuery();
        classQuery.setName("default");
        classQuery.setColCount(3);
        classQuery.setColWidth(190);
        classQuery.setClassDefine(clazz);
        classQuery.setLabelGap(5);
        classQuery.setFieldGap(15);
        classQuery.setSortNum(metaSortNum);
        classQuery.setValid(true);
        template.save(ClassPDBFactory.getClassQuery(classQuery));
        List<ClassQuery> classQueryList = new ArrayList<ClassQuery>();
        classQueryList.add(classQuery);
        clazz.setClassQueryList(classQueryList);

        // 插入sys_class_query_field
        List<QueryField> queryFieldList = new ArrayList<QueryField>();
        QueryField queryField;
        fieldSortNum = 0;
        for (ClassField classField : fieldList) {
            queryField = new QueryField();
            queryField.setClassQuery(classQuery);
            queryField.setClassField(classField);
            queryField.setDisplayName(classField.getFieldDesc());
            queryField.setOperator("=");
            queryField.setWidth(190);
            queryField.setDisplay(true);
            queryField.setSortNum(fieldSortNum += 10);
            queryField.setValid(true);
            // 插入表
            template.save(ClassPDBFactory.getQueryField(queryField));
            queryFieldList.add(queryField);
        }
        classQuery.setQueryFieldList(queryFieldList);

        // 插入类-table关联表
        List<RClassDbmsTable> tableList = new ArrayList<RClassDbmsTable>();
        RClassDbmsTable cdt = new RClassDbmsTable();
        cdt.setClassId(clazz.getId());
        cdt.setDbmsTableId(table.getId());
        cdt.setPrimary(true);
        cdt.setJoinSql("FROM " + table.getName());
        tableList.add(cdt);
        clazz.setDbmsTableList(tableList);
        List<ClassDefine> classList = new ArrayList<ClassDefine>();
        classList.add(clazz);
        table.setClassList(classList);
        template.save(ClassPDBFactory.getRClassTable(cdt));

        // 存入缓存
        cache.put(clazz.getName().toLowerCase(), clazz);
        classIdMap.put(clazz.getId(), clazz);*/
    }
}

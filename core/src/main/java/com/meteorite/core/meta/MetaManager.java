package com.meteorite.core.meta;

import com.meteorite.core.config.ProfileSetting;
import com.meteorite.core.config.SystemInfo;
import com.meteorite.core.config.SystemManager;
import com.meteorite.core.datasource.*;
import com.meteorite.core.datasource.classpath.ClassPathDataSource;
import com.meteorite.core.datasource.db.DBDataSource;
import com.meteorite.core.datasource.db.object.*;
import com.meteorite.core.datasource.db.util.JdbcTemplate;
import com.meteorite.core.datasource.persist.MetaPDBFactory;
import com.meteorite.core.datasource.persist.MetaRowMapperFactory;
import com.meteorite.core.dict.EnumBoolean;
import com.meteorite.core.meta.annotation.MetaElement;
import com.meteorite.core.meta.annotation.MetaFieldElement;
import com.meteorite.core.meta.model.*;
import com.meteorite.core.project.ProjectDefine;
import com.meteorite.core.ui.ViewManager;
import com.meteorite.core.ui.layout.property.TableFieldProperty;
import com.meteorite.core.util.UFile;
import com.meteorite.core.util.UIO;
import com.meteorite.core.util.UString;
import com.meteorite.core.util.dom.DomUtil;
import com.meteorite.core.util.jaxb.JAXBUtil;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
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
    private static Map<String, Meta> metaMap = new HashMap<String, Meta>();
    private static Map<String, Meta> metaIdMap = new HashMap<String, Meta>();
    private static Map<String, MetaField> fieldIdMap = new HashMap<String, MetaField>();
    private static Map<String, Meta> tableMeta = new HashMap<String, Meta>();
    private static List<MetaField> metaFieldList = new ArrayList<MetaField>();
    private static Map<String, Map<String, String>> metaFieldConfigs = new HashMap<String, Map<String, String>>();

    static {
        try {
//            addMeta(ProjectConfig.class, null);
//            addMeta(DBDataSource.class, null);
//            addMeta(Meta.class);
//            addMeta(MetaField.class);

            loadMetaFieldConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void load() throws Exception {
        String confSection = "metaui";
        String confKey = "metaInit";
        boolean isInit = UString.toBoolean(SystemManager.getSettingValue(confSection, confKey));
        JdbcTemplate template = new JdbcTemplate();
        try {
            if (isInit) { // ClassDef 已经初始化
                String sql = "SELECT * FROM mu_meta left join mu_meta_sql on (id=meta_id) order by sort_num";
                List<Meta> metaList = template.query(sql, MetaRowMapperFactory.getMeta());
                for (final Meta meta : metaList) {
                    metaMap.put(meta.getName(), meta);
                    metaIdMap.put(meta.getId(), meta);
                    // 查询元数据字段
                    sql = "SELECT * FROM mu_meta_field WHERE meta_id=? order by sort_num";
                    List<MetaField> fieldList = template.query(sql, MetaRowMapperFactory.getMetaField(meta), meta.getId());
                    meta.setFields(fieldList);
                    for (MetaField field : fieldList) {
                        fieldIdMap.put(field.getId(), field);
                    }
                    metaFieldList.addAll(fieldList);
                }

                // 查询元数据引用
                sql = "SELECT * FROM mu_meta_reference";
                List<MetaReference> referenceList = template.query(sql, MetaRowMapperFactory.getMetaReference());
                for (MetaReference reference : referenceList) {
                    reference.getPkMeta().getChildren().add(reference.getFkMeta());
                    reference.getFkMetaField().setRefField(reference.getPkMetaField());

                    // 外键引用
                    reference.getFkMeta().getReferences().add(reference);
                }
            } else {
                metaSortNum = 10;
                // 清空表mu_view_config, mu_view_layout, mu_view, mu_meta
                template.clearTable("mu_meta_reference", "mu_view_config", "mu_view", "mu_meta");
                DBDataSource dataSource = DataSourceManager.getSysDataSource();
                DBConnection dbConn = dataSource.getDbConnection();
                DBSchema schema = dbConn.getSchema();
//                dbConn.getLoader().loadSchemas();
                /*for (DBTable table : schema.getTables()) {
                    initMetaFromTable(template, table);
                    metaSortNum += 10;
                }

                for (DBView view : schema.getViews()) {
                    initMetaFromTable(template, view);
                    metaSortNum += 10;
                }*/

                List<VirtualResource> tables = dataSource.findResourcesByPath("/tables");
                for (VirtualResource table : tables) {
                    initMetaFromResource(template, table);
                    metaSortNum += 10;
                }

                List<VirtualResource> views = dataSource.findResourcesByPath("/views");
                for (VirtualResource view : views) {
                    initMetaFromResource(template, view);
                    metaSortNum += 10;
                }

                // 初始化元数据引用
                for (DBConstraint constraint : schema.getFkConstraints()) {
                    MetaReference metaRef = new MetaReference();
                    Meta pkMeta = tableMeta.get(dataSource.getName() + "://table/" + constraint.getPkTableName());
                    metaRef.setPkMeta(pkMeta);
                    metaRef.setPkMetaField(pkMeta.getFieldByOriginalName(constraint.getPkTableName() + "." + constraint.getPkColumnName()));
                    Meta fkMeta = tableMeta.get(dataSource.getName() + "://table/" + constraint.getFkTableName());
                    metaRef.setFkMeta(fkMeta);
                    metaRef.setFkMetaField(fkMeta.getFieldByOriginalName(constraint.getFkTableName() + "." + constraint.getFkColumnName()));

                    template.save(MetaPDBFactory.getMetaReference(metaRef));
                    metaRef.getPkMeta().getChildren().add(metaRef.getFkMeta());
                    metaRef.getFkMetaField().setRefField(metaRef.getPkMetaField());

                    // 外键引用
                    fkMeta.getReferences().add(metaRef);
                }

                // 创建元数据视图
                for (Meta meta : metaMap.values()) {
                    ViewManager.createViews(meta, template);
                }

                SystemManager.saveSetting(new ProfileSetting(confSection, confKey, "T"));
            }

            addMeta(DBDataSource.class, template);
            addMeta(TableFieldProperty.class, template);
            addMeta(ProjectDefine.class, template);

            template.commit();
        } finally {
            template.close();
        }
    }

    /**
     * 加载元字段配置信息
     */
    private static void loadMetaFieldConfig() throws Exception {
        ClassPathDataSource cpDataSource = DataSourceManager.getClassPathDataSource();
        ResourceItem configXml = cpDataSource.getResource("config/MetaFieldConfig.xml");
        if (configXml == null) {
            return;
        }
        InputStream is = configXml.getInputStream();
        List<DataMap> list = DomUtil.toListMap(is, "/config/row");
        for (DataMap map : list) {
            String metaName = map.getString("meta");
            String fieldName = map.getString("fieldName");
            String dictId = map.getString("dict");
            String metaField = metaName + "_" + fieldName;
            Map<String, String> fieldMap = metaFieldConfigs.get(metaField);
            if (fieldMap == null) {
                fieldMap = new HashMap<String, String>();
                metaFieldConfigs.put(metaField, fieldMap);
            }
            if (UString.isNotEmpty(dictId)) {
                fieldMap.put("dict", dictId);
            }
        }
    }

    public static void saveMetaFieldConfig() throws Exception {
        JAXBUtil.marshalListToFile(metaFieldList, UFile.createFile(DIR_SYSTEM, FILE_NAME_META_FIELD_CONFIG), MetaField.class);
    }

    public static void addMeta(Class<?> clazz, JdbcTemplate template) throws Exception {
        if (getMeta(clazz.getSimpleName()) != null) {
            return;
        }
        Meta meta = toMeta(clazz, template);
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
     * @param template
     * @return 返回类对象的元数据信息
     * @throws Exception 如果此类对象没有注解MetaElement，则抛出此异常
     * @since 1.0.0
     */
    public static Meta toMeta(Class<?> clazz, JdbcTemplate template) throws Exception {
        if (!clazz.isAnnotationPresent(MetaElement.class)) {
            throw new Exception(String.format("不能将非MetaElement的对象【%s】转化为Meta对象！", clazz.getName()));
        }

        MetaElement metaElement = clazz.getAnnotation(MetaElement.class);
        Meta meta = new Meta();
        meta.setName("##default".equals(metaElement.name()) ? clazz.getSimpleName() : metaElement.name());
        meta.setDisplayName(metaElement.displayName());
        meta.setValid(metaElement.isValid());
        meta.setDescription(metaElement.desc());
        meta.setInputDate(new Date());
        meta.setSortNum(metaElement.sortNum());
        meta.setDataSource(DataSourceManager.getSysDataSource());

        // 插入元数据信息
        if (template != null) {
            template.save(MetaPDBFactory.getMeta(meta));
        }
        metaMap.put(meta.getName(), meta);
        metaIdMap.put(meta.getId(), meta);

        List<MetaField> fieldList = new ArrayList<MetaField>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(MetaFieldElement.class)) {
                MetaFieldElement metaFieldElement = method.getAnnotation(MetaFieldElement.class);

                String name = method.getName().startsWith("get") ? method.getName().substring(3) : method.getName();
                MetaField metaField = new MetaField();
                metaField.setName("##default".equals(metaFieldElement.name()) ? name : metaFieldElement.name());
                metaField.setDisplayName(metaFieldElement.displayName());
                metaField.setValid(metaFieldElement.isValid());
                metaField.setDescription(metaFieldElement.desc());
                metaField.setInputDate(new Date());
                metaField.setSortNum(metaFieldElement.sortNum());
                metaField.setDataType(metaFieldElement.dataType());
                metaField.setDictId(metaFieldElement.dictId());
                metaField.setMeta(meta);
                if (UString.isEmpty(metaFieldElement.defaultValue())) {
                    /*Object obj = method.invoke(metaObj);
                    if (obj != null && UClass.isPrimitive(obj.getClass())) {
                        metaField.setDefaultValue(obj.toString());
                    }*/
                } else {
                    metaField.setDefaultValue(metaFieldElement.defaultValue());
                }

                fieldList.add(metaField);
                // 插入表sys_class_field
                if (template != null) {
                    template.save(MetaPDBFactory.getMetaField(metaField));
                }
                fieldList.add(metaField);
//            classFieldIdMap.put(field.getId(), field);
                // 加入缓存
                fieldIdMap.put(metaField.getId(), metaField);
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

        // 创建视图
        if (template != null) {
            ViewManager.createViews(meta, template);
        }

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
        List<MetaFormField> formFields = new ArrayList<MetaFormField>();
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
        return new ArrayList<Meta>(metaMap.values());
    }

    private static int metaSortNum = 10;

    public static Meta initMetaFromTable(JdbcTemplate template, DBDataset table) throws Exception {
        // 将表定义信息插入到元数据信息中
        Meta meta = new Meta();
        meta.setName(UString.tableNameToClassName(table.getName()));
        String comment = table.getComment();
        if (UString.isNotEmpty(comment) && comment.length() > 100) {
            meta.setDisplayName(table.getComment().substring(0, 100));
        } else {
            meta.setDisplayName(comment);
        }

        meta.setDescription(comment);
        meta.setValid(true);
        meta.setInputDate(new Date());
        meta.setSortNum(metaSortNum);
        meta.setDataSource(table.getSchema().getDataSource());

        // 插入元数据信息
        template.save(MetaPDBFactory.getMeta(meta));
        metaMap.put(meta.getName(), meta);
        metaIdMap.put(meta.getId(), meta);
        tableMeta.put(table.getFullName(), meta);

        List<MetaField> fieldList = new ArrayList<MetaField>();

        // 将表列信息插入到类字段信息中
        MetaField field;
        int fieldSortNum = 0;
        for (DBColumn column : table.getColumns()) {
            field = new MetaField();
            field.setMeta(meta);
//            field.setColumn(column);
            String columnComment = column.getComment();
            if (UString.isEmpty(columnComment)) {
                columnComment = column.getName().toLowerCase();
            }
            field.setDisplayName(columnComment);
            field.setName(UString.columnNameToFieldName(column.getName()));
            field.setDescription(column.getComment());
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
            // 加入缓存
            fieldIdMap.put(field.getId(), field);
        }
        meta.setFields(fieldList);

        return meta;
    }

    public static Meta initMetaFromResource(JdbcTemplate template, VirtualResource table) throws Exception {
        // 将表定义信息插入到元数据信息中
        Meta meta = new Meta();
        meta.setName(UString.tableNameToClassName(table.getName()));
        String comment = table.getDisplayName();
        if (UString.isNotEmpty(comment) && comment.length() > 100) {
            meta.setDisplayName(table.getDisplayName().substring(0, 100));
        } else {
            meta.setDisplayName(comment);
        }

        meta.setDescription(comment);
        meta.setValid(true);
        meta.setInputDate(new Date());
        meta.setSortNum(metaSortNum);
        meta.setResource(table);

        // 插入元数据信息
        template.save(MetaPDBFactory.getMeta(meta));
        metaMap.put(meta.getName(), meta);
        metaIdMap.put(meta.getId(), meta);
        tableMeta.put(table.getId(), meta);

        List<MetaField> fieldList = new ArrayList<MetaField>();

        // 将表列信息插入到类字段信息中
        MetaField field;
        int fieldSortNum = 0;
        // Sql语句 查询列
        StringBuilder queryColumns = new StringBuilder("");
        List<VirtualResource> columns = table.getDataSource().findResourcesByPath(String.format("/table/%s/columns", table.getName()));
        for (VirtualResource resource : columns) {
            DBColumn column = (DBColumn) resource.getOriginalObject();
            field = new MetaField();
            field.setMeta(meta);
            field.setOriginalName(table.getName() + "." + column.getName());
            String columnComment = resource.getDisplayName();
            if (UString.isEmpty(columnComment)) {
                columnComment = resource.getName().toLowerCase();
            }
            field.setDisplayName(columnComment);
            field.setName(UString.columnNameToFieldName(resource.getName()));
            field.setDescription(resource.getDisplayName());
            if (resource.getName().toLowerCase().startsWith("is_")) {
                field.setDataType(MetaDataType.BOOLEAN);
            } else if(column.isPk() && column.getMaxLength() == 32) {
                field.setDataType(MetaDataType.GUID);
                field.setDefaultValue("GUID()");
            }
            else {
                field.setDataType(column.getDataType());
            }
            field.setPk(column.isPk());
            field.setFk(column.isFk());
            field.setRequire(!column.isNullable());
            field.setMaxLength(column.getMaxLength());
            field.setValid(true);
            field.setSortNum(fieldSortNum += 10);

            // 默认值
            if ("isValid".equals(field.getName())) {
//                field.setDefaultValue("T");
            } else if ("inputDate".equals(field.getName())) {
                field.setDefaultValue("SYSDATE()");
            } else if ("sortNum".equals(field.getName())) {
//                field.setDefaultValue("0");
            }

            // 初始化系统默认配置信息
            // 数据字典
            Map<String, String> configMap = metaFieldConfigs.get(meta.getName() + "_" + field.getName());
            if (configMap != null) {
                String dict = configMap.get("dict");
                if (UString.isNotEmpty(dict)) {
                    field.setDictId(dict);
                    field.setDataType(MetaDataType.DICT);
                }
            }

            // 插入表sys_class_field
            template.save(MetaPDBFactory.getMetaField(field));
            fieldList.add(field);
            // 加入缓存
            fieldIdMap.put(field.getId(), field);

            // 查询列
            queryColumns.append(String.format("%s %s,", field.getOriginalName(), field.getName()));
        }

        if (queryColumns.charAt(queryColumns.length() - 1) == ',') {
            queryColumns.deleteCharAt(queryColumns.length() - 1);
        }
        String sqlText = String.format("SELECT %s FROM %s", queryColumns.toString(), table.getName());
        Map<String, Object> sqlMap = new HashMap<String, Object>();
        sqlMap.put("meta_id", meta.getId());
        sqlMap.put("sql_text", sqlText);
        template.save(sqlMap, "mu_meta_sql");

        meta.setFields(fieldList);
        meta.setSqlText(sqlText);

        return meta;
    }
}

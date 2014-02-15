package com.meteorite.core.meta;

import com.meteorite.core.config.ProjectConfig;
import com.meteorite.core.db.DataSource;
import com.meteorite.core.meta.annotation.MetaElement;
import com.meteorite.core.meta.annotation.MetaFieldElement;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.meta.model.MetaForm;
import com.meteorite.core.meta.model.MetaFormField;
import com.meteorite.core.util.jaxb.JAXBUtil;
import com.meteorite.core.util.UIO;
import com.meteorite.core.util.UString;
import com.meteorite.core.util.UtilFile;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.*;

import static com.meteorite.core.config.SystemConfig.*;

/**
 * 元数据管理
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class MetaManager {
    private static Map<String, Meta> metaMap = new HashMap<>();
    private static List<MetaField> metaFieldList;

    static {
        try {
            addMeta(ProjectConfig.class);
            addMeta(DataSource.class);
            addMeta(Meta.class);
            addMeta(MetaField.class);

            loadMetaFieldConfig();
        } catch (Exception e) {
            e.printStackTrace();
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
        JAXBUtil.marshalListToFile(metaFieldList, UtilFile.createFile(DIR_SYSTEM, FILE_NAME_META_FIELD_CONFIG), MetaField.class);
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
        return new ArrayList<>(metaMap.values());
    }
}

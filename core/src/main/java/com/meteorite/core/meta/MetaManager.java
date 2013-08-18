package com.meteorite.core.meta;

import com.meteorite.core.meta.annotation.MetaElement;
import com.meteorite.core.meta.annotation.MetaFieldElement;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.meta.model.MetaForm;
import com.meteorite.core.meta.model.MetaFormField;
import com.meteorite.core.util.ClassUtil;
import com.meteorite.core.util.UString;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 元数据管理
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class MetaManager {
    /**
     * 将注解了MetaElement的类对象转换为元数据对象
     *
     * @param metaObj 注解了MetaElement的类对象
     * @return 返回类对象的元数据信息
     * @throws Exception 如果此类对象没有注解MetaElement，则抛出此异常
     * @since 1.0.0
     */
    public static Meta toMeta(Object metaObj) throws Exception {
        Class<?> clazz = metaObj.getClass();
        if (!clazz.isAnnotationPresent(MetaElement.class)) {
            throw new Exception(String.format("不能将非MetaElement的对象【%s】转化为Meta对象！", metaObj.getClass().getName()));
        }

        MetaElement metaElement = clazz.getAnnotation(MetaElement.class);
        Meta meta = new Meta();
        meta.setName("##default".equals(metaElement.name()) ? clazz.getSimpleName() : metaElement.name());
        meta.setCname(metaElement.cname());
        meta.setValid(metaElement.isValid());
        meta.setDesc(metaElement.desc());
        meta.setInputDate(new Date());

        List<MetaField> fieldList = new ArrayList<MetaField>();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(MetaFieldElement.class)) {
                MetaFieldElement metaFieldElement = method.getAnnotation(MetaFieldElement.class);

                String name = method.getName().startsWith("get") ? method.getName().substring(3) : method.getName();
                MetaField metaField = new MetaField();
                metaField.setName("##default".equals(metaFieldElement.name()) ? name : metaFieldElement.name());
                metaField.setDisplayName(metaFieldElement.displayName());
                metaField.setValid(metaElement.isValid());
                metaField.setDesc(metaElement.desc());
                metaField.setInputDate(new Date());
                if (UString.isEmpty(metaFieldElement.defaultValue())) {
                    Object obj = method.invoke(metaObj);
                    if (obj != null && ClassUtil.isPrimitive(obj.getClass())) {
                        metaField.setDefaultValue(obj.toString());
                    }
                } else {
                    metaField.setDefaultValue(metaFieldElement.defaultValue());
                }

                fieldList.add(metaField);
            }
        }

        meta.setFileds(fieldList);

        return meta;
    }

    public static MetaForm toForm(Meta meta) {
        MetaForm metaForm = new MetaForm();
        metaForm.setName(meta.getName() + "Form");
        metaForm.setCname(meta.getCname() + "表单");
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
        for (MetaField field : meta.getFileds()) {
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
}

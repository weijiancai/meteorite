package com.meteorite.core.dict;

import com.meteorite.core.db.DatabaseType;
import com.meteorite.core.dict.annotation.DictElement;
import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.util.UObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据字典管理器
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class DictManager {
    private static Map<String, DictCategory> categoryMap = new HashMap<>();

    static {
        try {
            addDict(DatabaseType.class);
            addDict(MetaDataType.class);
            addDict(DisplayStyle.class);
            addDict(EnumBoolean.class);

            DictCategory root = new DictCategory();
            root.setId("ROOT");
            root.setName("数据字典");
            List<DictCode> codeList = new ArrayList<>();
            for (DictCategory category : categoryMap.values()) {
                DictCode code = new DictCode();
                code.setId(category.getId());
                code.setName(category.getName());
                code.setValue(category.getName());

                codeList.add(code);
            }
            root.setCodeList(codeList);
            categoryMap.put("ROOT", root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将类转化为数据字典
     *
     * @param clazz 类
     */
    public static void addDict(Class<?> clazz) throws Exception {
        if (!clazz.isAnnotationPresent(DictElement.class)) {
            throw new Exception(String.format("不能将非DictElement的对象【%s】转化为数据字典！", clazz.getName()));
        }

        if (clazz.isEnum()) { //将枚举类转化为数据字典
            DictElement dict = clazz.getAnnotation(DictElement.class);

            DictCategory category = new DictCategory();
            if ("##default".equals(dict.categoryId())) {
                category.setId(clazz.getName());
            } else {
                category.setId(dict.categoryId());
            }
            category.setName(dict.categoryName());
            category.setSystem(true);
            category.setValid(true);

            Method method = clazz.getMethod("values");
            Enum[] ems = (Enum[]) method.invoke(null); // 调用静态方法，不需要实例对象
            int sortNum = 0;
            List<DictCode> codeList = new ArrayList<>();
            for (Enum em : ems) {
                DictCode code = new DictCode();
                if ("name".equals(dict.codeNameMethod())) {
                    code.setName(em.name());
                } else if ("ordinal".equals(dict.codeNameMethod())) {
                    code.setName(em.ordinal() + "");
                }
                Method codeValueMethod = clazz.getMethod(dict.codeValueMethod());
                code.setValue(UObject.toString(codeValueMethod.invoke(em)));
                code.setSortNum(sortNum++);
                code.setValid(true);
                code.setCategory(category);

                codeList.add(code);
            }
            category.setCodeList(codeList);

            categoryMap.put(category.getId(), category);
        }
    }

    /**
     * 获得数据字典
     *
     * @param clazz 类对象
     * @return 返回数据字典
     */
    public static DictCategory getDict(Class<?> clazz) {
        return getDict(clazz.getName());
    }

    /**
     * 获得数据字典
     *
     * @param dictId 数据字典ID
     * @return 返回数据字典
     */
    public static DictCategory getDict(String dictId) {
        return categoryMap.get(dictId);
    }
}

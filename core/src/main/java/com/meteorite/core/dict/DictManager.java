package com.meteorite.core.dict;

import com.meteorite.core.config.ProfileSetting;
import com.meteorite.core.config.SystemInfo;
import com.meteorite.core.config.SystemManager;
import com.meteorite.core.datasource.DataSourceType;
import com.meteorite.core.datasource.db.DBManager;
import com.meteorite.core.datasource.db.DatabaseType;
import com.meteorite.core.datasource.db.util.JdbcTemplate;
import com.meteorite.core.datasource.persist.MetaPDBFactory;
import com.meteorite.core.datasource.persist.MetaRowMapperFactory;
import com.meteorite.core.dict.annotation.DictElement;
import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.ui.layout.PropertyType;
import com.meteorite.core.util.UObject;
import com.meteorite.core.util.UString;

import java.lang.reflect.Method;
import java.sql.Connection;
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
    private static Map<String, DictCategory> categoryMap = new HashMap<String, DictCategory>();
    private static DictCategory root = new DictCategory();

    static {
        try {
            addDict(DatabaseType.class);
            addDict(MetaDataType.class);
            addDict(DisplayStyle.class);
            addDict(EnumBoolean.class);
            addDict(PropertyType.class);
            addDict(EnumAlign.class);
            addDict(DataSourceType.class);

            root.setId("ROOT");
            root.setName("数据字典");
            List<DictCode> codeList = new ArrayList<DictCode>();
            for (DictCategory category : categoryMap.values()) {
                DictCode code = new DictCode();
                code.setCategory(category);
                code.setId(category.getId());
                code.setName(category.getName());
                code.setDisplayName(category.getName());

                codeList.add(code);
            }
            root.setCodeList(codeList);
            categoryMap.put("ROOT", root);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void load() throws Exception {
        String confSection = "metaui";
        String confKey = "dictInit";
        boolean isInit = UString.toBoolean(SystemManager.getSettingValue(confSection, confKey));
        JdbcTemplate template = new JdbcTemplate();

        if (isInit) {
            // 查询布局
            String sql = "SELECT * FROM mu_dz_category";
            List<DictCategory> categoryList = template.query(sql, MetaRowMapperFactory.getDictCategory());
            for (DictCategory category : categoryList) {
                categoryMap.put(category.getId(), category);
                // 查询布局属性
                sql = "SELECT * FROM mu_dz_code WHERE category_id=?";
                List<DictCode> codeList = template.query(sql, MetaRowMapperFactory.getDictCode(category), category.getId());
                category.setCodeList(codeList);
            }
        } else { // 初始化数据字典
            // 清空表
            template.clearTable("mu_dz_category");

            // 保存字典分类到数据库
            for (DictCategory category : getDictList()) {
                if ("ROOT".equals(category.getId())) {
                    continue;
                }
                categoryMap.put(category.getId(), category);
                template.save(MetaPDBFactory.getDictCategory(category));
                for (DictCode code : category.getCodeList()) {
                    // 保存字典代码到数据库
                    template.save(MetaPDBFactory.getDictCode(code));
                }
            }

            SystemManager.saveSetting(new ProfileSetting(confSection, confKey, "T"));
        }

        template.commit();
        template.close();
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
                category.setId(clazz.getSimpleName());
            } else {
                category.setId(dict.categoryId());
            }
            category.setName(dict.categoryName());
            category.setSystem(true);
            category.setValid(true);

            Method method = clazz.getMethod("values");
            Enum[] ems = (Enum[]) method.invoke(null); // 调用静态方法，不需要实例对象
            int sortNum = 0;
            List<DictCode> codeList = new ArrayList<DictCode>();
            for (Enum em : ems) {
                DictCode code = new DictCode();
                if ("name".equals(dict.codeNameMethod())) {
                    code.setName(em.name());
                } else if ("ordinal".equals(dict.codeNameMethod())) {
                    code.setName(em.ordinal() + "");
                }
                Method codeValueMethod = clazz.getMethod(dict.codeValueMethod());
                code.setDisplayName(UObject.toString(codeValueMethod.invoke(em)));
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
        return getDict(clazz.getSimpleName());
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

    /**
     * 获得数据字典列表
     *
     * @return 返回所有数据字典列表
     */
    public static List<DictCategory> getDictList() {
        return new ArrayList<DictCategory>(categoryMap.values());
    }

    /**
     * 获得数据字典的根节点
     *
     * @return 返回数据字典的根节点
     * @since 1.0.0
     */
    public static DictCategory getRoot() {
        return root;
    }
}

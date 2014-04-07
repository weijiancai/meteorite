package com.meteorite.core.datasource.persist;

import com.meteorite.core.datasource.db.DBManager;
import com.meteorite.core.datasource.db.RowMapper;
import com.meteorite.core.dict.DictCategory;
import com.meteorite.core.dict.DictCode;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.meta.MetaManager;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.layout.LayoutManager;
import com.meteorite.core.ui.layout.PropertyType;
import com.meteorite.core.ui.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class MetaRowMapperFactory {
    public static RowMapper<Meta> getMeta() {
        return new RowMapper<Meta>() {
            @Override
            public Meta mapRow(ResultSet rs) throws SQLException {
                Meta meta = new Meta();

                meta.setId(rs.getString("id"));
                meta.setName(rs.getString("name"));
                meta.setDisplayName(rs.getString("display_name"));
                meta.setDesc(rs.getString("desc"));
                meta.setValid("T".equals(rs.getString("is_valid")));
                meta.setInputDate(rs.getDate("input_date"));
                meta.setSortNum(rs.getInt("sort_num"));

                return meta;
            }
        };
    }

    public static RowMapper<MetaField> getMetaField(final Meta meta) {
        return new RowMapper<MetaField>() {
            @Override
            public MetaField mapRow(ResultSet rs) throws SQLException {
                MetaField field = new MetaField();

                field.setMeta(meta);
                field.setId(rs.getString("id"));
                field.setName(rs.getString("name").toLowerCase());
                field.setDisplayName(rs.getString("display_name"));
                field.setDataType(MetaDataType.getDataType(rs.getString("data_type")));
                field.setDefaultValue(rs.getString("default_value"));
                field.setDesc(rs.getString("desc"));
                field.setDictId(rs.getString("dict_id"));
                field.setValid("T".equals(rs.getString("is_valid")));
                field.setInputDate(rs.getDate("input_date"));
                field.setSortNum(rs.getInt("sort_num"));
                field.setColumn(DBManager.getCache().getColumn(rs.getString("db_column")));

                return field;
            }
        };
    }

    public static RowMapper<Layout> getLayout() {
        return new RowMapper<Layout>() {
            @Override
            public Layout mapRow(ResultSet rs) throws SQLException {
                Layout layout = new Layout();

                layout.setId(rs.getString("id"));
                layout.setPid(rs.getString("pid"));
                layout.setName(rs.getString("name"));
                layout.setDisplayName(rs.getString("display_name"));
                layout.setDesc(rs.getString("desc"));
                layout.setRefId(rs.getString("ref_id"));
                layout.setValid("T".equals(rs.getString("is_valid")));
                layout.setInputDate(rs.getDate("input_date"));
                layout.setSortNum(rs.getInt("sort_num"));

                return layout;
            }
        };
    }

    public static RowMapper<LayoutProperty> getLayoutProperty(final Layout layout) {
        return new RowMapper<LayoutProperty>() {
            @Override
            public LayoutProperty mapRow(ResultSet rs) throws SQLException {
                LayoutProperty prop = new LayoutProperty();

                prop.setLayout(layout);
                prop.setId(rs.getString("id"));
                prop.setName(rs.getString("name"));
                prop.setDisplayName(rs.getString("display_name"));
                prop.setDefaultValue(rs.getString("default_value"));
                prop.setPropType(PropertyType.getType(rs.getString("prop_type")));
                prop.setDesc(rs.getString("desc"));
                prop.setSortNum(rs.getInt("sort_num"));

                return prop;
            }
        };
    }

    public static RowMapper<DictCategory> getDictCategory() {
        return new RowMapper<DictCategory>() {
            @Override
            public DictCategory mapRow(ResultSet rs) throws SQLException {
                DictCategory category = new DictCategory();

                category.setId(rs.getString("id"));
                category.setName(rs.getString("name"));
                category.setSystem("T".equals(rs.getString("is_system")));
                category.setDesc(rs.getString("desc"));
                category.setValid("T".equals(rs.getString("is_valid")));
                category.setInputDate(rs.getDate("input_date"));
                category.setSortNum(rs.getInt("sort_num"));

                return category;
            }
        };
    }

    public static RowMapper<DictCode> getDictCode(final DictCategory category) {
        return new RowMapper<DictCode>() {
            @Override
            public DictCode mapRow(ResultSet rs) throws SQLException {
                DictCode code = new DictCode();

                code.setCategory(category);
                code.setId(rs.getString("id"));
                code.setName(rs.getString("name"));
                code.setDisplayName(rs.getString("display_name"));
                code.setDesc(rs.getString("desc"));
                code.setValid("T".equals(rs.getString("is_valid")));
                code.setInputDate(rs.getDate("input_date"));
                code.setSortNum(rs.getInt("sort_num"));

                return code;
            }
        };
    }

    public static RowMapper<View> getView() {
        return new RowMapper<View>() {
            @Override
            public View mapRow(ResultSet rs) throws SQLException {
                View view = new View();

                view.setId(rs.getString("id"));
                view.setName(rs.getString("name"));
                view.setDisplayName(rs.getString("display_name"));
                view.setDesc(rs.getString("desc"));
                view.setValid("T".equals(rs.getString("is_valid")));
                view.setInputDate(rs.getDate("input_date"));
                view.setSortNum(rs.getInt("sort_num"));

                return view;
            }
        };
    }

    public static RowMapper<ViewLayout> getViewLayout(final View view) {
        return new RowMapper<ViewLayout>() {
            @Override
            public ViewLayout mapRow(ResultSet rs) throws SQLException {
                ViewLayout viewLayout = new ViewLayout();

                viewLayout.setId(rs.getString("id"));
                viewLayout.setView(view);
                viewLayout.setLayout(LayoutManager.getLayoutById(rs.getString("layout_id")));
                viewLayout.setMeta(MetaManager.getMetaById(rs.getString("meta_id")));

                return viewLayout;
            }
        };
    }

    public static RowMapper<ViewConfig> getViewConfig(final ViewLayout viewLayout) {
        return new RowMapper<ViewConfig>() {
            @Override
            public ViewConfig mapRow(ResultSet rs) throws SQLException {
                ViewConfig config = new ViewConfig();

                config.setId(rs.getString("id"));
                config.setViewLayout(viewLayout);
                config.setProperty(LayoutManager.getLayoutPropById(rs.getString("prop_id")));
                config.setField(MetaManager.getMetaField(rs.getString("meta_field_id")));
                config.setValue(rs.getString("value"));

                return config;
            }
        };
    }
}

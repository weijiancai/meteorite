package com.meteorite.core.datasource.persist;

import com.meteorite.core.datasource.db.RowMapper;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;

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
                field.setName(rs.getString("name"));
                field.setDisplayName(rs.getString("display_name"));
                field.setDataType(MetaDataType.getDataType(rs.getString("data_type")));
                field.setDefaultValue(rs.getString("default_value"));
                field.setDesc(rs.getString("desc"));
                field.setDictId(rs.getString("dict_id"));
                field.setValid("T".equals(rs.getString("is_valid")));
                field.setInputDate(rs.getDate("input_date"));
                field.setSortNum(rs.getInt("sort_num"));

                return field;
            }
        };
    }
}

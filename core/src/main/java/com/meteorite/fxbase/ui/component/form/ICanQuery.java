package com.meteorite.fxbase.ui.component.form;

import com.meteorite.core.dict.QueryModel;
import com.meteorite.core.meta.MetaDataType;

import java.util.List;

/**
 * 可查询组件接口
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface ICanQuery {
    class Condition {
        public String colName;
        public QueryModel queryModel;
        public String value;
        public MetaDataType dataType;

        public Condition(String colName, QueryModel queryModel, String value, MetaDataType dataType) {
            this.colName = colName;
            this.queryModel = queryModel;
            this.value = value;
            this.dataType = dataType;
        }
    }

    List<Condition> getConditions();
}

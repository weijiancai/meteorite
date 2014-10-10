package com.meteorite.project.wlb;

import com.meteorite.core.config.SystemManager;
import com.meteorite.core.datasource.DataSourceManager;
import com.meteorite.core.datasource.QueryCondition;
import com.meteorite.core.datasource.db.DBDataSource;
import com.meteorite.core.datasource.request.BaseRequest;
import com.meteorite.core.datasource.request.ExpDataRequest;
import com.meteorite.core.datasource.request.IResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * 从metaui中导入数据字典到网利宝
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class DictImport {
    public static void main(String[] args) throws Exception {
        SystemManager.getInstance().init();
        DBDataSource source = DataSourceManager.getSysDataSource();
        DBDataSource target = (DBDataSource) DataSourceManager.getDataSourceByName("cms");
        BaseRequest request = new BaseRequest();
        request.setPath("/table/mu_dz_category");
        List<QueryCondition> conditions = new ArrayList<QueryCondition>();
        conditions.add(QueryCondition.create("pid", "XinJu_wlb"));
        request.setConditions(conditions);
        IResponse response = source.exp(request);

        ExpDataRequest targetRequest = new ExpDataRequest("/table/bs_dz_code_class");
        targetRequest.setResponse(response);
        targetRequest.addColMapping("id", "code_class_id");
        targetRequest.addColMapping("pid", "code_class_id_super");
        targetRequest.addColMapping("description", "class_desc");
        targetRequest.addColMapping("name", "class_name");
        targetRequest.setExcludeColumns(new String[]{"is_system"});
        target.imp(targetRequest);

    }
}

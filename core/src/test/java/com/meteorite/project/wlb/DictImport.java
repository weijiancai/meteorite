package com.meteorite.project.wlb;

import com.meteorite.core.config.SystemManager;
import com.meteorite.core.datasource.DataSourceManager;
import com.meteorite.core.datasource.db.DBDataSource;
import com.meteorite.core.datasource.request.ExpDataRequest;
import com.meteorite.core.datasource.request.IResponse;

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

        copyDzCategory(source, target);
        copyDzCode(source, target);
    }

    private static void copyDzCategory(DBDataSource source, DBDataSource target) throws Exception {
        ExpDataRequest request = new ExpDataRequest();
//        request.setPath("/table/mu_dz_category");
//        List<QueryCondition> conditions = new ArrayList<QueryCondition>();
//        conditions.add(QueryCondition.create("pid", "XinJu_wlb"));
//        request.setConditions(conditions);
        request.setSql("select * from mu_dz_category where pid='XinJu_wlb' or id='EnumBoolean'");
        IResponse response = source.exp(request);

        ExpDataRequest targetRequest = new ExpDataRequest("/table/bs_dz_code_class");
        targetRequest.setResponse(response);
        targetRequest.addColMapping("id", "code_class_id");
        targetRequest.addColMapping("pid", "code_class_id_super");
        targetRequest.addColMapping("description", "class_desc");
        targetRequest.addColMapping("name", "class_name");
        targetRequest.setExcludeColumns(new String[]{"is_system", "input_date"});
        target.imp(targetRequest);
    }

    private static void copyDzCode(DBDataSource source, DBDataSource target) throws Exception {
        ExpDataRequest request = new ExpDataRequest();
//        request.setPath("/table/mu_dz_category");
//        List<QueryCondition> conditions = new ArrayList<QueryCondition>();
//        conditions.add(QueryCondition.create("pid", "XinJu_wlb"));
//        request.setConditions(conditions);
        request.setSql("select * from mu_dz_code where category_id in (select id from mu_dz_category where pid='XinJu_wlb' or id='EnumBoolean')");
        IResponse response = source.exp(request);

        ExpDataRequest targetRequest = new ExpDataRequest("/table/bs_dz_code_global");
        targetRequest.setResponse(response);
        targetRequest.addColMapping("id", "code_id");
        targetRequest.addColMapping("category_id", "code_class_id");
        targetRequest.addColMapping("name", "code_id_display");
        targetRequest.addColMapping("description", "memo");
        targetRequest.setExcludeColumns(new String[]{"input_date"});
        target.imp(targetRequest);
    }
}

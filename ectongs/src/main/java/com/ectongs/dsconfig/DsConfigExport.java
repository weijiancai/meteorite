package com.ectongs.dsconfig;

import com.meteorite.core.datasource.DataSource;
import com.meteorite.core.datasource.DataSourceManager;
import com.meteorite.core.datasource.QueryCondition;
import com.meteorite.core.rest.Request;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class DsConfigExport {
    private final DataSource source;
    private final DataSource target;
    private String sourceDsId;
    private String targetDsId;

    public DsConfigExport(DataSource source, DataSource target) {
        this.source = source;
        this.target = target;
    }
    
    public void export(String sourceDsId, String targetDsId) throws Exception {
        this.sourceDsId = sourceDsId;
        this.targetDsId = targetDsId;

        expDataStore();
        expAnalyse();
        expAnalyseItem();
        expDsColumns();
        expDsEditColumns();
        expDsEditDetail();
        expDsListColumns();
        expDsQueryColumns();
        expDsRetrieveArgs();
        expDsReport();
    }
    
    public void expDataStore() throws Exception {
        Request request = new Request("/table/bs_sys_ds_datastore");
        List<QueryCondition> conditions = new ArrayList<QueryCondition>();
        conditions.add(QueryCondition.create("ds_id", sourceDsId));
        request.setConditions(conditions);
        Request.ExpRequest expRequest = request.getExpRequest();
        expRequest.setExcludeColumns(new String[]{"rightdestroy", "rightmodify", "rightadd", "rightquery", "rightdelete", "rightexport", "rightprint", "rightverify"});
        expRequest.addDefaultValue("ds_id", targetDsId);
        expRequest.addDefaultValue("is_show_verify_bar", "F");
        DataSourceManager.exp(source, target, request);
    }

    public void expAnalyse() throws Exception {
        Request request = new Request("/table/bs_sys_ds_analyse");
        List<QueryCondition> conditions = new ArrayList<QueryCondition>();
        conditions.add(QueryCondition.create("ds_id", sourceDsId));
        request.setConditions(conditions);
        Request.ExpRequest expRequest = request.getExpRequest();
        expRequest.addDefaultValue("ds_id", targetDsId);
        DataSourceManager.exp(source, target, request);
    }

    public void expAnalyseItem() throws Exception {
        Request request = new Request("/table/bs_sys_ds_analyse_item");
        List<QueryCondition> conditions = new ArrayList<QueryCondition>();
        conditions.add(QueryCondition.create("ds_id", sourceDsId));
        request.setConditions(conditions);
        Request.ExpRequest expRequest = request.getExpRequest();
        expRequest.addDefaultValue("ds_id", targetDsId);
        DataSourceManager.exp(source, target, request);
    }

    public void expDsColumns() throws Exception {
        Request request = new Request("/table/bs_sys_ds_columns");
        List<QueryCondition> conditions = new ArrayList<QueryCondition>();
        conditions.add(QueryCondition.create("ds_id", sourceDsId));
        request.setConditions(conditions);
        Request.ExpRequest expRequest = request.getExpRequest();
        expRequest.addDefaultValue("ds_id", targetDsId);
        DataSourceManager.exp(source, target, request);
    }

    public void expDsEditColumns() throws Exception {
        Request request = new Request("/table/bs_sys_ds_edit_columns");
        List<QueryCondition> conditions = new ArrayList<QueryCondition>();
        conditions.add(QueryCondition.create("ds_id", sourceDsId));
        request.setConditions(conditions);
        Request.ExpRequest expRequest = request.getExpRequest();
        expRequest.addDefaultValue("ds_id", targetDsId);
        expRequest.addDefaultValue("is_limit_range", "F");
        DataSourceManager.exp(source, target, request);
    }

    public void expDsEditDetail() throws Exception {
        Request request = new Request("/table/bs_sys_ds_edit_detail");
        List<QueryCondition> conditions = new ArrayList<QueryCondition>();
        conditions.add(QueryCondition.create("ds_id", sourceDsId));
        request.setConditions(conditions);
        Request.ExpRequest expRequest = request.getExpRequest();
        expRequest.addDefaultValue("ds_id", targetDsId);
        DataSourceManager.exp(source, target, request);
    }

    public void expDsListColumns() throws Exception {
        Request request = new Request("/table/bs_sys_ds_list_columns");
        List<QueryCondition> conditions = new ArrayList<QueryCondition>();
        conditions.add(QueryCondition.create("ds_id", sourceDsId));
        request.setConditions(conditions);
        Request.ExpRequest expRequest = request.getExpRequest();
        expRequest.addDefaultValue("ds_id", targetDsId);
        DataSourceManager.exp(source, target, request);
    }

    public void expDsQueryColumns() throws Exception {
        Request request = new Request("/table/bs_sys_ds_query_columns");
        List<QueryCondition> conditions = new ArrayList<QueryCondition>();
        conditions.add(QueryCondition.create("ds_id", sourceDsId));
        request.setConditions(conditions);
        Request.ExpRequest expRequest = request.getExpRequest();
        expRequest.addDefaultValue("ds_id", targetDsId);
        DataSourceManager.exp(source, target, request);
    }

    public void expDsRetrieveArgs() throws Exception {
        Request request = new Request("/table/bs_sys_ds_retrieve_args");
        List<QueryCondition> conditions = new ArrayList<QueryCondition>();
        conditions.add(QueryCondition.create("ds_id", sourceDsId));
        request.setConditions(conditions);
        Request.ExpRequest expRequest = request.getExpRequest();
        expRequest.addDefaultValue("ds_id", targetDsId);
        DataSourceManager.exp(source, target, request);
    }

    public void expDsReport() throws Exception {
        Request request = new Request("/table/bs_sys_ds_report");
        List<QueryCondition> conditions = new ArrayList<QueryCondition>();
        conditions.add(QueryCondition.create("ds_id", sourceDsId));
        request.setConditions(conditions);
        Request.ExpRequest expRequest = request.getExpRequest();
        expRequest.addDefaultValue("ds_id", targetDsId);
        DataSourceManager.exp(source, target, request);
    }
}

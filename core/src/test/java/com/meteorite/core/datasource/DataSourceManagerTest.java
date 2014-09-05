package com.meteorite.core.datasource;

import com.meteorite.core.datasource.db.DBDataSource;
import com.meteorite.core.datasource.db.JdbcDrivers;
import com.meteorite.core.rest.Request;
import org.junit.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DataSourceManagerTest {

    @Test
    public void testExp() throws Exception {
        DBDataSource target = new DBDataSource("ectb2b", JdbcDrivers.SQL_SERVER, "jdbc:sqlserver://192.168.3.3:1433;DatabaseName=ectb2b", "sa", "7758521", null);
        DBDataSource source = new DBDataSource("yhebp", JdbcDrivers.MYSQL, "jdbc:mysql://localhost:3306/yhebp", "root", "root", null);

//        expDataStore(source, target);
//        expAnalyse(source, target);
//        expAnalyseItem(source, target);
//        expDsColumns(source, target);
//        expDsEditColumns(source, target);
//        expDsEditDetail(source, target);
//        expDsListColumns(source, target);
//        expDsQueryColumns(source, target);
//        expDsRetrieveArgs(source, target);
        expDsReport(source, target);
    }

    public void expDataStore(DataSource source, DataSource target) throws Exception {
        Request request = new Request("/table/bs_sys_ds_datastore");
        List<QueryCondition> conditions = new ArrayList<QueryCondition>();
        conditions.add(QueryCondition.create("ds_id", "BAS_Send"));
        request.setConditions(conditions);
        Request.ExpRequest expRequest = request.getExpRequest();
        expRequest.setExcludeColumns(new String[]{"rightdestroy", "rightmodify", "rightadd", "rightquery", "rightdelete", "rightexport", "rightprint", "rightverify"});
        expRequest.addDefaultValue("ds_id", "YwPx_Basu");
        DataSourceManager.exp(source, target, request);
    }

    public void expAnalyse(DataSource source, DataSource target) throws Exception {
        Request request = new Request("/table/bs_sys_ds_analyse");
        List<QueryCondition> conditions = new ArrayList<QueryCondition>();
        conditions.add(QueryCondition.create("ds_id", "BAS_Send"));
        request.setConditions(conditions);
        Request.ExpRequest expRequest = request.getExpRequest();
//        expRequest.setExcludeColumns(new String[]{"rightdestroy", "rightmodify", "rightadd", "rightquery", "rightdelete", "rightexport", "rightprint", "rightverify"});
        expRequest.addDefaultValue("ds_id", "YwPx_Basu");
        DataSourceManager.exp(source, target, request);
    }

    public void expAnalyseItem(DataSource source, DataSource target) throws Exception {
        Request request = new Request("/table/bs_sys_ds_analyse_item");
        List<QueryCondition> conditions = new ArrayList<QueryCondition>();
        conditions.add(QueryCondition.create("ds_id", "BAS_Send"));
        request.setConditions(conditions);
        Request.ExpRequest expRequest = request.getExpRequest();
//        expRequest.setExcludeColumns(new String[]{"rightdestroy", "rightmodify", "rightadd", "rightquery", "rightdelete", "rightexport", "rightprint", "rightverify"});
        expRequest.addDefaultValue("ds_id", "YwPx_Basu");
        DataSourceManager.exp(source, target, request);
    }

    public void expDsColumns(DataSource source, DataSource target) throws Exception {
        Request request = new Request("/table/bs_sys_ds_columns");
        List<QueryCondition> conditions = new ArrayList<QueryCondition>();
        conditions.add(QueryCondition.create("ds_id", "BAS_Send"));
        request.setConditions(conditions);
        Request.ExpRequest expRequest = request.getExpRequest();
//        expRequest.setExcludeColumns(new String[]{"rightdestroy", "rightmodify", "rightadd", "rightquery", "rightdelete", "rightexport", "rightprint", "rightverify"});
        expRequest.addDefaultValue("ds_id", "YwPx_Basu");
        DataSourceManager.exp(source, target, request);
    }

    public void expDsEditColumns(DataSource source, DataSource target) throws Exception {
        Request request = new Request("/table/bs_sys_ds_edit_columns");
        List<QueryCondition> conditions = new ArrayList<QueryCondition>();
        conditions.add(QueryCondition.create("ds_id", "BAS_Send"));
        request.setConditions(conditions);
        Request.ExpRequest expRequest = request.getExpRequest();
//        expRequest.setExcludeColumns(new String[]{"rightdestroy", "rightmodify", "rightadd", "rightquery", "rightdelete", "rightexport", "rightprint", "rightverify"});
        expRequest.addDefaultValue("ds_id", "YwPx_Basu");
        DataSourceManager.exp(source, target, request);
    }

    public void expDsEditDetail(DataSource source, DataSource target) throws Exception {
        Request request = new Request("/table/bs_sys_ds_edit_detail");
        List<QueryCondition> conditions = new ArrayList<QueryCondition>();
        conditions.add(QueryCondition.create("ds_id", "BAS_Send"));
        request.setConditions(conditions);
        Request.ExpRequest expRequest = request.getExpRequest();
//        expRequest.setExcludeColumns(new String[]{"rightdestroy", "rightmodify", "rightadd", "rightquery", "rightdelete", "rightexport", "rightprint", "rightverify"});
        expRequest.addDefaultValue("ds_id", "YwPx_Basu");
        DataSourceManager.exp(source, target, request);
    }

    public void expDsListColumns(DataSource source, DataSource target) throws Exception {
        Request request = new Request("/table/bs_sys_ds_list_columns");
        List<QueryCondition> conditions = new ArrayList<QueryCondition>();
        conditions.add(QueryCondition.create("ds_id", "BAS_Send"));
        request.setConditions(conditions);
        Request.ExpRequest expRequest = request.getExpRequest();
//        expRequest.setExcludeColumns(new String[]{"rightdestroy", "rightmodify", "rightadd", "rightquery", "rightdelete", "rightexport", "rightprint", "rightverify"});
        expRequest.addDefaultValue("ds_id", "YwPx_Basu");
        DataSourceManager.exp(source, target, request);
    }

    public void expDsQueryColumns(DataSource source, DataSource target) throws Exception {
        Request request = new Request("/table/bs_sys_ds_query_columns");
        List<QueryCondition> conditions = new ArrayList<QueryCondition>();
        conditions.add(QueryCondition.create("ds_id", "BAS_Send"));
        request.setConditions(conditions);
        Request.ExpRequest expRequest = request.getExpRequest();
//        expRequest.setExcludeColumns(new String[]{"rightdestroy", "rightmodify", "rightadd", "rightquery", "rightdelete", "rightexport", "rightprint", "rightverify"});
        expRequest.addDefaultValue("ds_id", "YwPx_Basu");
        DataSourceManager.exp(source, target, request);
    }

    public void expDsRetrieveArgs(DataSource source, DataSource target) throws Exception {
        Request request = new Request("/table/bs_sys_ds_retrieve_args");
        List<QueryCondition> conditions = new ArrayList<QueryCondition>();
        conditions.add(QueryCondition.create("ds_id", "BAS_Send"));
        request.setConditions(conditions);
        Request.ExpRequest expRequest = request.getExpRequest();
//        expRequest.setExcludeColumns(new String[]{"rightdestroy", "rightmodify", "rightadd", "rightquery", "rightdelete", "rightexport", "rightprint", "rightverify"});
        expRequest.addDefaultValue("ds_id", "YwPx_Basu");
        DataSourceManager.exp(source, target, request);
    }

    public void expDsReport(DataSource source, DataSource target) throws Exception {
        Request request = new Request("/table/bs_sys_ds_report");
        List<QueryCondition> conditions = new ArrayList<QueryCondition>();
        conditions.add(QueryCondition.create("ds_id", "BAS_Send"));
        request.setConditions(conditions);
        Request.ExpRequest expRequest = request.getExpRequest();
//        expRequest.setExcludeColumns(new String[]{"rightdestroy", "rightmodify", "rightadd", "rightquery", "rightdelete", "rightexport", "rightprint", "rightverify"});
        expRequest.addDefaultValue("ds_id", "YwPx_Basu");
        DataSourceManager.exp(source, target, request);
    }
}
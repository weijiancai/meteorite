package com.meteorite.core.datasource.db;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.meteorite.core.config.SystemConfig;
import com.meteorite.core.config.SystemManager;
import com.meteorite.core.datasource.DataSourceManager;
import com.meteorite.core.datasource.db.object.DBConnection;
import com.meteorite.core.datasource.db.object.DBSchema;
import com.meteorite.core.datasource.db.object.impl.DBColumnImpl;
import com.meteorite.core.datasource.db.object.impl.DBTableImpl;
import com.meteorite.core.datasource.db.object.impl.DBViewImpl;
import com.meteorite.core.datasource.db.util.JdbcTemplate;
import com.meteorite.core.util.jaxb.JAXBUtil;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class DBManagerTest {
    @Test
    public void testGetConnection() throws Exception {
        SystemManager.getInstance().init(SystemManager.SystemType.DESKTOP);
        DBConnection conn = DataSourceManager.getSysDataSource().getDbConnection();
        System.out.println(conn);
        assertNotNull(conn);
        assertThat(conn.getDatabaseType(), equalTo(DatabaseType.HSQLDB));
        DBSchema schema = conn.getSchema();
//        String str = JAXBUtil.marshalToString(schema, DBTableImpl.class, DBColumnImpl.class, DBViewImpl.class);
//        System.out.println(str);

       /* JdbcTemplate template = new JdbcTemplate(conn.getConnection());
        template.update("INSERT INTO sys_layout (ref_id,is_valid,sort_num,name,pid,id,display_name,input_date,desc) VALUES (' ','F','10','FORM','L_root','L01','表单','Thu Apr 10 10:02:51 CST 2014','')");
        template.update("INSERT INTO sys_layout_prop (layout_id,sort_num,name,default_value,id,display_name,prop_type,desc) VALUES ('L01','10','form_save',' ','L01_A01','保存','AP',' ')");
        template.commit();
        template.close();*/

        System.out.println("==========================");
        System.out.println(JSON.toJSONString(schema, SerializerFeature.PrettyFormat));
    }

    @Test
    public void testMysqlConnection() throws Exception {
        DBDataSource ds = new DBDataSource();
        ds.setName("mysql");
        ds.setDatabaseType(DatabaseType.MYSQL);
        ds.setDriverClass("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/");
        ds.setUsername("root");
        ds.setPassword("7758521");

        /*DBConnection conn = ds.getDbConnection();
        System.out.println(conn);
        assertNotNull(conn);
        assertThat(conn.getDatabaseType(), equalTo(DatabaseType.MYSQL));
        DBSchema schema = conn.getSchema();*/
//        String str = JAXBUtil.marshalToString(schema, DBTableImpl.class, DBColumnImpl.class, DBViewImpl.class);
//        System.out.println(str);
        /*for (DBSchema schema1 : conn.getSchemas()) {
            System.out.println(schema1.getName());
        }*/
        System.out.println(ds.getNavTree());


        System.out.println("==========================");
//        System.out.println(JSON.toJSONString(schema, SerializerFeature.PrettyFormat));
    }
}

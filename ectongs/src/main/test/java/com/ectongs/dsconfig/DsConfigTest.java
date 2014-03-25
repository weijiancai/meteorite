package com.ectongs.dsconfig;

import cc.csdn.base.db.dataobj.dsconfig.DataStoreConfig;
import cc.csdn.base.util.UtilBase64;
import cc.csdn.base.util.UtilObject;
import com.ectongs.http.DataListOption;
import com.ectongs.http.HttpAccepter;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author wei_jc
 * @since 1.00
 */
public class DsConfigTest {
    @BeforeClass
    public static void setup() {
        /*final String path = System.getProperty("user.dir");
        System.out.println("path:" + path);
        AppManager.initSystem(new InitSystemAttribute() {
            public String getWorkPath() {
                return path;
            }
        });*/
    }

    @Test
    public void test() throws Exception {
        BaseHttpService service = new BaseHttpService("flex/DataStoreConfigAction");
        service.addParameter("dsClassName", "Product");
        service.addParameter("isConfigFromDs", "false");
        service.addParameter("isReturnObj", "true");
        HttpAccepter accepter = service.send("66540F58102FE173C81E621907A94BF8");
        String data = accepter.getData();
        DataStoreConfig dsConfig = (DataStoreConfig) UtilObject.byteToObject(UtilBase64.decode(data));
//        System.out.println(dsConfig);
        BaseGrid grid = new BaseGrid(dsConfig);
        System.out.println(grid.genForm(3, true));
    }

    @Test
    public void testLogin() {
        BaseHttpService.login("system", "1");
    }

    @Test
    public void testDataList() {
//        DataListSender sender = new DataListSender("base.datalist.codeedittypelist", null);
//        DataListSender sender = new DataListSender("base.datalist.editstylelist", null);
        DataListSender sender = new DataListSender("base.datalist.dscolumnformatlist", null);
        DataListOption[] options = sender.getOptions();
        for (DataListOption option : options) {
            System.out.println(option.getData() + " --> " + option.getLabel());
        }
    }
}

package com.meteorite.taobao.api;

import com.meteorite.core.model.ITreeNode;
import com.meteorite.taobao.AppKeyFactory;
import com.meteorite.taobao.TbItemCat;
import com.taobao.api.domain.ItemCat;
import org.junit.Test;

import java.util.List;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class TbItemFacadeTest {
    @Test
    public void testGetItemCats() throws Exception {
        TbItemFacade facade = new TbItemFacade(AppKeyFactory.getZt().getClient(), AppKeyFactory.getZt().sessionKey);
        TbItemCat root = new TbItemCat();
        root.setCid(3332L);
        facade.getItemCats(root);
        for (ITreeNode cat : root.getChildren()) {
            System.out.println(cat.getId() + "    " + cat.getName() + "    " + cat.getPid() + "    " );
        }
    }
}

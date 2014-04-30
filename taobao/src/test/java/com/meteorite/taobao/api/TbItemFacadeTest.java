package com.meteorite.taobao.api;

import com.meteorite.core.model.ITreeNode;
import com.meteorite.taobao.AppKeyFactory;
import com.meteorite.taobao.TbItemCat;
import com.taobao.api.ApiException;
import com.taobao.api.domain.ItemCat;
import com.taobao.api.domain.Product;
import com.taobao.api.response.ItemGetResponse;
import com.taobao.api.response.ProductGetResponse;
import com.taobao.api.response.ProductsSearchResponse;
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

    @Test
    public void testGetItem() throws ApiException {
        TbItemFacade facade = new TbItemFacade(AppKeyFactory.getXueFu().getClient(), AppKeyFactory.getXueFu().sessionKey);
//        ItemGetResponse response = facade.getItem(18156115336L);
        ItemGetResponse response = facade.getItem(16354718541L);
        System.out.println(response.getBody());
    }

    @Test
    public void testGetProduct() throws ApiException {
        TbItemFacade facade = new TbItemFacade(AppKeyFactory.getXueFu().getClient(), AppKeyFactory.getXueFu().sessionKey);
//        ProductGetResponse response = facade.getProduct(229292863L);
        ProductGetResponse response = facade.getProduct(235773052L);
        System.out.println(response.getBody());
    }

    @Test
    public void testSearch() throws ApiException {
        TbItemFacade facade = new TbItemFacade(AppKeyFactory.getXueFu().getClient(), AppKeyFactory.getXueFu().sessionKey);
        ProductsSearchResponse response = facade.searchProduct("9787112128211");
        System.out.println(response.getBody());
    }
}

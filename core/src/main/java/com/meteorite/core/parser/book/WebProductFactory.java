package com.meteorite.core.parser.book;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * 网上电子书商产品信息工场类
 *
 * @author weijiancai
 * @since 0.0.1
 */
public class WebProductFactory {
	private static final Logger log = Logger.getLogger(WebProductFactory.class);
	
    /**
     * 获取京东上的图书信息，如果有多条，取第一条
     *
     * @param isbn ISBN
     * @return 返回图书信息，如果没有找到返回null
     */
    public static List<IWebProduct> getJingDongProduct(String userId, String isbn) {
    	log.debug(String.format("用户【%s】开始下载京东网上商品信息【%s】......", userId, isbn));
        return new JingDongParser(userId, isbn).parse();
    }

    /**
     * 获取当当上的图书信息，如果有多条，取第一条
     *
     * @param isbn ISBN
     * @return 返回图书信息，如果没有找到返回null
     */
    public static List<IWebProduct> getDangDangProduct(String userId, String isbn) {
    	log.debug(String.format("用户【%s】开始下载当当网上商品信息【%s】......", userId, isbn));
        return new DangDangParser(userId, isbn).parse();
    }

    /**
     * 获取豆瓣上的图书信息，如果有多条，取第一条
     *
     * @param isbn ISBN
     * @return 返回图书信息，如果没有找到返回null
     */
    public static List<IWebProduct> getDouBanProduct(String userId, String isbn) {
    	log.debug(String.format("用户【%s】开始下载豆瓣网上商品信息【%s】......", userId, isbn));
        return new DouBanParser(userId, isbn).parse();
    }

    /**
     * 获取亚马逊上的图书信息，如果有多条，取第一条
     *
     * @param isbn ISBN
     * @return 返回图书信息，如果没有找到返回null
     */
    public static List<IWebProduct> getAmazonProduct(String userId, String isbn) {
    	log.debug(String.format("用户【%s】开始下载亚马逊网上商品信息【%s】......", userId, isbn));
        return new AmazonParser(userId, isbn).parse();
    }
    
    /**
     * 获取开卷新书网的图书信息，如果有多条，取第一条
     *
     * @param isbn ISBN
     * @return 返回图书信息，如果没有找到返回null
     */
    /*public static List<IWebProduct> getOpenBookDataProduct(String userId, String isbn) {
    	log.debug(String.format("用户【%s】开始下载开卷新书网网上商品信息【%s】......", userId, isbn));
        return new OpenBookDataParser(userId, isbn).parse();
    }*/

    /**
     * 获取网站上的图书信息，如果有多条，取第一条
     *
     * @param isbn ISBN
     * @return 返回图书信息，如果没有找到返回null
     */
    public static List<IWebProduct> getProduct(SiteName[] siteNames, String userId, String isbn) {
    	if(siteNames == null) {
    		siteNames = SiteName.getDefaultSort();
    	}
    	
    	List<IWebProduct> list = new ArrayList<IWebProduct>();
    	for(int i = 0; i < siteNames.length; i++) {
    		if(list.size() > 0) {
    			break;
    		}
    		switch (siteNames[i]) {
	            case JING_DONG:
	            	list.addAll(getJingDongProduct(userId, isbn));
	            	break;
	            case AMAZON:
	            	list.addAll(getAmazonProduct(userId, isbn));
	            	break;
	            case DANG_DANG:
	            	list.addAll(getDangDangProduct(userId, isbn));
	            	break;
	            case DOU_BAN:
	            	list.addAll(getDouBanProduct(userId, isbn));
	            	break;
	            case OPEN_BOOK_DATA:
//	            	list.addAll(getOpenBookDataProduct(userId, isbn));
	        }
    	}

        return list;
    }
}

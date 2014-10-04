package com.meteorite.core.parser.book;

import java.util.List;

/**
 * 图书商品解析器
 *
 * @author wei_jc
 * @since 0.0.1
 */
public interface IProductParser {
    /**
     * 解析商品信息
     *
     * @return 返回商品信息列表， 此List不为null
     */
    List<IWebProduct> parse();

    /*String getName(Element element);

    Elements search(String url);

    String getSearchUrl();*/

}

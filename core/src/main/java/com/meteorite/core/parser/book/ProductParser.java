package com.meteorite.core.parser.book;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public abstract class ProductParser implements IProductParser{
    @Override
    public List<IWebProduct> parse() {
        List<IWebProduct> list = new ArrayList<IWebProduct>();

/*
        Elements elements = search(getSearchUrl());
        for (Element element : elements) {
            WebProductImpl product = new WebProductImpl();
            product.setName(getName(element));

            list.add(product);
        }
*/

        return list;
    }
}

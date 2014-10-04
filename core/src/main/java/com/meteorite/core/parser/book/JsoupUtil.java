package com.meteorite.core.parser.book;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class JsoupUtil {
    public static String attr(Element element, String cssQuery, String attrName) {
        Elements elements = element.select(cssQuery);
        if (elements.size() > 0) {
            return elements.get(0).attr(attrName);
        }

        return "";
    }

    public static String text(Element element, String cssQuery) {
        Elements elements = element.select(cssQuery);
        if (elements.size() > 0) {
            return elements.get(0).text().trim();
        }

        return "";
    }

    public static String html(Element element, String cssQuery) {
        Elements elements = element.select(cssQuery);
        if (elements.size() > 0) {
            return elements.get(0).html();
        }

        return "";
    }
}

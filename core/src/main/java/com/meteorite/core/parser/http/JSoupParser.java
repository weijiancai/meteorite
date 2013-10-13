package com.meteorite.core.parser.http;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.Map;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class JSoupParser {
    private String url;

    public JSoupParser(String url) {
        this.url = url;
    }

    public Document parse() throws IOException {
        return parse(null);
    }

    public Document parse(Map<String, String> data) throws IOException {
        Connection conn = Jsoup.connect(url);
        Map<String, String> cookies = conn.response().cookies();
        System.out.println(cookies);
        if (data != null) {
            conn.data(data);
        }
        conn.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:24.0) Gecko/20100101 Firefox/24.0");
        return conn.get();
    }

    public Connection connect() {
        return Jsoup.connect(url);
    }
}

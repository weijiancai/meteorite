package com.meteorite.core.parser.http;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

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
        return Jsoup.connect(url).get();
    }

    public Connection connect() {
        return Jsoup.connect(url);
    }
}

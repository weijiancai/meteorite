package com.meteorite.core.parser.http;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
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
        return parse(data, null, null);
    }

    public Document parse(Map<String, String> data, Map<String, String> headers, Map<String, String> cookieMap) throws IOException {
        Connection conn = Jsoup.connect(url).timeout(50000);
        conn.userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:24.0) Gecko/20100101 Firefox/24.0");

        if (headers != null && headers.size() > 0) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                conn.header(entry.getKey(), entry.getValue());
            }
        }

        Map<String, String> cookies = conn.execute().cookies();
        if (cookieMap != null && cookieMap.size() > 0) {
            cookies.putAll(cookieMap);
        }
        System.out.println(cookies);
        if (data != null) {
            conn.data(data);
        }

        /*try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        return conn.get();
    }

    public Connection connect() {
        return Jsoup.connect(url);
    }

    public static void main(String[] args) throws IOException {
        if (args == null || args.length != 2) {
            System.out.println("参数不正确：应为 url ip");
            System.exit(0);
        }
        String url = args[0];
        String ip = args[1];

        JSoupParser parser = new JSoupParser(url);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("x-forwarded-for", ip);
        Map<String, String> cookies = new HashMap<String, String>();
        cookies.put("5QzN_2132_lastact", "1411653613%09misc.php%09so");
        Document doc = parser.parse(null, headers, null);
        System.out.println(doc.html());
    }
}

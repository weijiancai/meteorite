package com.meteorite.core.parser.http;

import com.meteorite.core.util.UtilFile;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class FetchWebSite {
    private String baseUrl;
    private File baseDir;

    public FetchWebSite(String baseUrl, File saveToDir) throws IOException {
        this.baseUrl = baseUrl;
        this.baseDir = saveToDir;

        fetch(baseUrl);
    }

    public static void main(String[] args) throws IOException {
//        Validate.isTrue(args.length == 1, "usage: supply baseUrl to fetch");
        String url = "http://www.0731hunjia.com/";
        print("Fetching %s...", url);

        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("a[href]");
        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");

        print("\nMedia: (%d)", media.size());
        for (Element src : media) {
            if (src.tagName().equals("img"))
                print(" * %s: <%s> %sx%s (%s)",
                        src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
                        trim(src.attr("alt"), 20));
            else
                print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
        }

        print("\nImports: (%d)", imports.size());
        for (Element link : imports) {
            print(" * %s <%s> (%s)", link.tagName(),link.attr("abs:href"), link.attr("rel"));
        }

        print("\nLinks: (%d)", links.size());
        for (Element link : links) {
            print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
        }
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }

    private static enum URL_TYPE {
        LINK, MEDIA, IMPORT
    }

    private void fetch(String url) throws IOException {
        if (!url.startsWith(baseUrl)) {
            return;
        }
        System.out.println("Fetch URL: " + url);
        String path = url.substring(baseUrl.length());

        Connection conn = Jsoup.connect(url);
        Document doc = conn.get();
        // 保存当前文档

        File file;
        if (url.equals(baseUrl)) {
            file = UtilFile.createFile(baseDir, "index.html");
        } else {
            file = UtilFile.createFile(baseDir, path);
        }

        path = path.toLowerCase();
        if(path.endsWith(".jpg") || path.endsWith(".jpeg") || path.endsWith(".gif") || path.endsWith(".png")) {
            byte[] bytes = conn.response().bodyAsBytes();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.close();
        } else {
            PrintWriter pw = new PrintWriter(file);
            pw.write(doc.html());
            pw.flush();
            pw.close();
        }
        /*if (!file.getParentFile().exists()) {
            file.getParentFile()
        }*/


        Elements links = doc.select("a[href]");
        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");

        for (Element src : media) {
            if (src.tagName().equals("img")) {

            } else {

            }
            fetch(src.attr("abs:src"));
        }
    }
}

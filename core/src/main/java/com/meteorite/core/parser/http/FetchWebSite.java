package com.meteorite.core.parser.http;

import com.meteorite.core.util.UFile;
import com.meteorite.core.util.UString;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class FetchWebSite {
    private String baseUrl;
    private File baseDir;
    private File errorFile;
    private PrintWriter pw;

    public FetchWebSite(File saveToDir) throws IOException {
        this.baseDir = saveToDir;
        errorFile = UFile.createFile(baseDir, "error.txt");
        pw = new PrintWriter(errorFile);
    }

    public static void main(String[] args) throws IOException {
//        Validate.isTrue(args.length == 1, "usage: supply baseUrl to fetch");
        /*String url = "http://www.0731hunjia.com/";
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
        }*/

        String baseUrl = args[0];
        File dir = new File(args[1]);
        FetchWebSite fetchWebSite = new FetchWebSite(dir);
        /*fetchWebSite.addExcludeUrl("http://www.0731hunjia.com/bbs");*/
        fetchWebSite.fetch(baseUrl, 0);
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

    private Set<String> urlList = new HashSet<>();
    private Set<String> pathList = new HashSet<>();
    private Set<String> excludeList = new HashSet<>();
    private static final int MAX_LEVEL = 1;

    public void addExcludeUrl(String url) {
        excludeList.add(url);
    }

    public void fetch(String url, int level) throws IOException {
        if(baseUrl == null) {
            int end = url.indexOf("/", 7);
            baseUrl = url.substring(0, end == -1 ? url.length() : end);
        }
        if (url.contains("../")) {
            url = url.replace("../", "");
        }

        System.out.println("Fetch URL: " + url + "    " + level);

        HttpURLConnection httpConn;
        try {
            httpConn = (HttpURLConnection) new URL(url).openConnection();
        } catch (Exception e) {
            pw.println(url);
            pw.flush();
            System.out.println(e.getMessage());
            return;
        }
        Map<String, List<String>> headerMap = httpConn.getHeaderFields();

        String path = url.substring(baseUrl.length());
        String parentDir = "";
        File file = null;
        if (url.equals(baseUrl)) {
            file = UFile.createFile(baseDir, "index.html");
        } else {
            if (path.contains("?")) {
                path = path.substring(0, path.indexOf("?"));
            } else if(path.contains("#")) {
                path = path.substring(0, path.lastIndexOf("#"));
            } else if (path.endsWith("/")) {
                path = path + "index.html";
            }
            if (!path.contains(".")) {
                path += ".html";
            }
        }

        if (file == null) {
            if (UString.isEmpty(path)) {
                return;
            }
            file = new File(baseDir, path);
            if (file.exists()) {
                return;
            }
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
        }
        if (path.contains("/")) {
            File tmpFile = file;
            while(!tmpFile.getAbsolutePath().equals(baseDir.getAbsolutePath())) {
                parentDir += "../";
                tmpFile = tmpFile.getParentFile();
            }
            parentDir = parentDir.substring(3);
        }

        List<String> contentTypeList = headerMap.get("Content-Type");
        if (contentTypeList != null && contentTypeList.size() > 0) {
            String str = contentTypeList.get(0);
            String contentType = "";
            String charset = "UTF-8";
            if(str.contains(";")) {
                contentType = str.substring(0, str.indexOf(";"));
                int charsetIdx = str.indexOf("=") ;
                if(charsetIdx != -1) {
                    charset = str.substring(charsetIdx + 1).trim();
                }
            }

            if (contentType.contains("text/html")) {
                /*if(level > MAX_LEVEL) {
                    return;
                }*/

                Document doc = Jsoup.parse(httpConn.getInputStream(), charset, baseUrl);
                Document.OutputSettings settings = new Document.OutputSettings();
                settings.indentAmount(4);
                doc.outputSettings(settings.prettyPrint(true));

                Elements links = doc.select("a[href]");
                Elements media = doc.select("[src]");
                Elements imports = doc.select("link[href]");
                Set<String> hrefList = new HashSet<>();

                for (Element link : imports) {
                    String href = link.attr("abs:href");
//                    hrefList.add(href);
                    if (link.attr("href").startsWith("/")) {
                        link.attr("href", parentDir + link.attr("href").substring(1));
                    }
                    if (!href.contains(".css")) {
                        continue;
                    }
                    fetch(href, level + 1);
                }

                for (Element src : media) {
                    String href = src.attr("abs:src");
//                    hrefList.add(href);
                    if (src.attr("src").startsWith("/")) {
                        src.attr("src", parentDir + src.attr("src").substring(1));
                    }
                    if (href.contains(".html") || href.contains(".php") || href.endsWith("/")) {
                        continue;
                    }
                    fetch(href, level + 1);
                }

                for (Element link : links) {
                    String href = link.attr("abs:href");
                    hrefList.add(href);
                    if (link.attr("href").startsWith("/")) {
                        link.attr("href", parentDir + link.attr("href").substring(1));
                    }
                    if (href.endsWith("/")) {
                        link.attr("href", parentDir + link.attr("href") + "index.html");
                    }
                }

                try {
                    PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file), charset));
                    pw.write(doc.html());
                    pw.flush();
                    pw.close();
                } catch (Exception e) {
                    pw.println(url);
                    pw.flush();
                    System.out.println(e.getMessage());
                }

                Set<String> result = new HashSet<>();
                for (String href : hrefList) {
                    boolean flag = false;

                    for (String exclude : excludeList) {
                        if (href.startsWith(exclude)) {
                            flag = true;
                            break;
                        }
                    }
                    if (!flag && !urlList.contains(href) && href.startsWith(baseUrl)) {
                        result.add(href);
                        urlList.add(href);
                    }
                }

                if(level > 0) {
                    for (String href : result) {
                        fetch(href, level + 1);
                    }
                }
            } else {
                try {
                    InputStream is = httpConn.getInputStream();
                    FileOutputStream fos = new FileOutputStream(file);
                    int i;
                    while ((i = is.read()) != -1) {
                        fos.write(i);
                    }
                    fos.close();
                } catch (Exception e) {
                    pw.println(url);
                    pw.flush();
                    System.out.println(e.getMessage());
                }
            }
        }

        /*path = path.toLowerCase();
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


        Elements links = doc.select("a[href]");
        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");*/
    }

    private Set<String> urlSet = new HashSet<>();

    public void fetchImages(String url) throws IOException {
        System.out.println(url);

        if(baseUrl == null) {
            int end = url.indexOf("/", 7);
            baseUrl = url.substring(0, end == -1 ? url.length() : end);
        }
        if (!url.startsWith(baseUrl)) {
            return;
        }

        final File imagesDir = new File(baseDir, "images");
        Document doc = Jsoup.connect(url).timeout(50000).get();

        Elements links = doc.select("a[href]");
        Elements media = doc.select("[src]");

        for (Element src : media) {
            final String href = src.attr("abs:src");
            if (href.contains(".html") || href.contains(".php") || href.endsWith("/") || href.endsWith(".js")) {
                continue;
            }
            System.out.println(href);
            /*new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        UFile.write(new URL(href), imagesDir);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();*/
            UFile.write(new URL(href), imagesDir);
        }

        for (Element link : links) {
            String href = link.attr("abs:href");
            urlSet.add(href);
            fetchImages(href);
        }
    }
}

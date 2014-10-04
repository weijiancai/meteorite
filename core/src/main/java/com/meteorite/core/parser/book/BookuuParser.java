package com.meteorite.core.parser.book;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 博库书城解析器
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class BookuuParser extends ProductParser {
    private static final Logger log = Logger.getLogger(BookuuParser.class);
    public static final String SEARCH_URL = "http://search.bookuu.com/k_%s.html";

    private String isbn;

    public BookuuParser(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public List<IWebProduct> parse() {
        List<IWebProduct> prodList = new ArrayList<IWebProduct>();

        String url = String.format(SEARCH_URL, isbn);
        try {
            Document doc = Jsoup.connect(url).get();
            Elements elements = doc.select("div.container div.main div.main-wrap div.books-list");
            for (Element element : elements) {
                IWebProduct product = parseProduct(element);
                if (product != null) {
                    prodList.add(product);
                }
            }
        } catch (IOException e) {
            log.error("连接博库网站失败！", e);
        }

        return prodList;
    }

    private IWebProduct parseProduct(Element element) {
        WebProductImpl product = new WebProductImpl();
        product.setSourceSite(SiteName.BOOKUU.name());
        List<ProductPic> picList = new ArrayList<ProductPic>();

        try {
            String detailUrl = element.select("h3.summary a").get(0).attr("href");
            Document doc = Jsoup.connect(detailUrl).get();

            // 取图片
            picList.add(new ProductPic(new URL(getPicUrl(element)), true));
            // 取书名
            product.setName(JsoupUtil.text(doc, "div#main div#rightcontent div.products-detail-info h1"));
            // 取定价
            Elements elements = doc.select("div#main div#rightcontent div.products-detail-info div.desc ul.info-li li");
            for (Element aElement : elements) {
                String text = aElement.text();
                if (text.contains("定 价：")) {
                    product.setPrice(JsoupUtil.text(aElement, "del").replace("¥", ""));
                } else if (text.contains("作者：")) {
                    product.setAuthor(text.replace("作者：", "").trim());
                } else if (text.contains("出版社：")) {
                    product.setPublishing(text.replace("出版社：", "").trim());
                } else if (text.contains("开本")) {
                    setKaiBen(product, text);
                } else if (text.contains("版")) {
                    setBanCi(product, text);
                }
            }
            // 编辑推荐
            product.setHAbstract(getOtherInfo(doc, "div#main div#rightcontent div.tabcon dl#goodsdetail div#bjtj_s"));
            // 内容提要
            product.setContent(getOtherInfo(doc, "div#main div#rightcontent div.tabcon dl#goodsdetail div#nrty_s"));
            // 作者简介
            product.setAuthorIntro(getOtherInfo(doc, "div#main div#rightcontent div.tabcon dl#goodsdetail div#zzjj_s"));
            // 目录
            product.setCatalog(getOtherInfo(doc, "div#main div#rightcontent div.tabcon dl#goodsdetail div#ml_s"));
            // 精彩页
            product.setExtract(getOtherInfo(doc, "div#main div#rightcontent div.tabcon dl#goodsdetail div#JCY_s"));
            // 前言
            product.setPrologue(getOtherInfo(doc, "div#main div#rightcontent div.tabcon dl#goodsdetail div#XY_s"));

            // 下载图片
            new DownloadPicture(picList);
            product.setProductPic(picList);

        } catch (IOException e) {
            log.error(String.format("解析博库网上图书信息【%s】失败！", isbn), e);
            return null;
        }

        return product;
    }

    private String getPicUrl(Element element) {
        String picUrl = JsoupUtil.attr(element, "div.photo span.subpic a img", "src");
        return picUrl.replace("book_m", "book").replace("-fm-m.jpg", "-fm.jpg");
    }

    private void setKaiBen(WebProductImpl product, String text) {
        try {
            int start = text.indexOf("开本：");
            int end = text.indexOf("开", start + 3);
            if (start >= 0) {
                product.setKaiben(text.substring(start + 3, end));
            }
            start = text.indexOf("页数:", end + 1);
            end = text.indexOf("页", start + 3);
            if (start >= 0) {
                product.setPageNum(text.substring(start + 3, end));
            }
        } catch (Exception e) {
            log.error(String.format("解析开本、页数【%s】失败！", text), e);
        }
    }

    private void setBanCi(WebProductImpl product, String text) {
        try {
            String[] strs = text.split("版");
            if (strs.length == 2) {
                String[] strs1 = strs[0].split("第");
                if (strs1.length == 1) {
                    product.setBanci(strs1[0]);
                } else {
                    product.setPublishDate(strs1[0].trim());
                    product.setBanci(strs1[1]);
                }
                String[] strs2 = strs[1].split("第");
                if (strs2.length == 1) {
                    product.setPrintNum(strs2[0]);
                } else {
                    product.setPrintDate(strs2[0].trim());
                    product.setPrintNum(strs2[1].replace("次印刷", ""));
                }
            }
        } catch (Exception e) {
            log.error(String.format("解析出版年月、版次、印次【%s】失败！", text), e);
        }
    }

    private String getOtherInfo(Element element, String cssQuery) {
        Elements elements = element.select(cssQuery);
        if (elements.size() > 0) {
            for (Element aElement : elements.get(0).select("a")) {
                if (aElement.text().contains("显示部分信息")) {
                    aElement.remove();
                }
            }
            return elements.get(0).html();
        }

        return "";
    }
}

package com.meteorite.core.parser.book;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.meteorite.core.util.UString;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 当当解析器
 *
 * @author wei_jc
 * @since 0.0.1
 */
public class DangDangParser implements IProductParser {
	private static final Logger log = Logger.getLogger(DangDangParser.class);
	
    public static final String SEARCH_URL = "http://searchb.dangdang.com/?key=";
    public static final String SUB_DETAIL_URL = "http://product.dangdang.com/detail/main.php?type=all&product_id=";
    private static final int TIME_OUT = 2 * 1000; // 10秒超时

    private String userId;
    private String isbn;

    public DangDangParser(String isbn) {
    	this(null, isbn);
    }
    
    public DangDangParser(String userId, String isbn) {
    	this.userId = userId;
        this.isbn = isbn;
    }

    @Override
    public List<IWebProduct> parse() {
    	List<IWebProduct> prodList = new ArrayList<IWebProduct>();
        try {
            // 打开搜索结果页面
            Document doc = Jsoup.connect(SEARCH_URL + isbn).timeout(TIME_OUT).get();
            Elements elements = doc.select("div.list_wrap div.list_main div.resultlist > ul > li");
            if (elements != null) {
            	IWebProduct prod;
                for (Element element : elements) {
                	prod = parseProduct(element);
                	if(prod != null) {
                		prodList.add(prod);
                	}
                }
            }
        } catch (Exception e) {
        	log.error("连接当当网站失败！", e);
        }

        return prodList;
    }
    
    private IWebProduct parseProduct(Element element) {
    	try {
    		WebProductImpl prod = new WebProductImpl();
            prod.setSourceSite(SiteName.DANG_DANG.name());
            List<ProductPic> picList = new ArrayList<ProductPic>();
            
            Elements mElements;
            // 打开详细页面
            String detailUrl = element.select("div.pic a").first().attr("href");

            Document detailDoc = Jsoup.connect(detailUrl)
                    .timeout(TIME_OUT)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .get();
            // 取书名
            prod.setName(detailDoc.select("div.main > div.head h1").first().ownText().replace("&nbsp;", "").trim());
            // 取详细页面图片 350 * 350
            String picUrl = detailDoc.select("div.main > div.show > div.show_pic > div.big > a").first().attr("href");
            char c = picUrl.charAt(picUrl.length() - 1);
            if(c != '='){
            	picList.add(new ProductPic(new URL(detailDoc.select("div.main > div.show > div.show_pic > div.big > a > img").first().attr("wsrc")), true));
            }
            
            // 取定价
            prod.setPrice(UString.getNumber(detailDoc.select("div.main > div.show > div.show_info > div.sale > p > .m_price").first().text()));
            
            mElements = detailDoc.select("div.main > div.show > div.show_info > ul.intro > li");
            for(Element aElement : mElements) {
                Elements c1Elements = aElement.select("> span.c1");
                Elements c3Elements = aElement.select("> span.c3");

                if(c1Elements.size() > 0) {
                    if(c1Elements.first().ownText().contains("丛 书 名")) {
                        prod.setSeriesName(c1Elements.first().ownText().replace("丛 书 名：", ""));
                    } else if(c1Elements.first().text().contains("作 者：")) {
                        String authorStr = c1Elements.first().text().replace("作 者：", "");
                        for (String str : authorStr.split("，")) {
                            String value = str.substring(0, str.length() - 1);
                            if (str.endsWith("著")) {
                                prod.setAuthor(UString.trim(value));
                            } else if (str.endsWith("图") || str.endsWith("绘")) {
                                prod.setPainter(UString.trim(value));
                            } else if (str.endsWith("译")) {
                                prod.setTranslator(UString.trim(value));
                            }
                        }
                    } else if(c1Elements.first().select("> span").size() > 0) {
                        if(c1Elements.first().select("> span").first().ownText().contains("出 版 社")) {
                            prod.setPublishing(c1Elements.select("a").text());
                        }
                    } else if(c1Elements.first().ownText().contains("出版时间")) {
                        prod.setPublishDate(c1Elements.first().ownText().replace("出版时间：", ""));
                    }
                }

                if(c3Elements.size() > 0) {
                    for (Element bElement : c3Elements.select("> span")) {
                        if(bElement.ownText().contains("版 次")) {
                            prod.setBanci(bElement.parent().ownText());
                        } else if (bElement.ownText().contains("页 数")) {
                            prod.setPageNum(bElement.parent().ownText());
                        } else if (bElement.ownText().contains("字 数")) {
                            prod.setWordCount(bElement.parent().ownText());
                        } else if (bElement.ownText().contains("开 本")) {
                            prod.setKaiben(bElement.parent().ownText());
                        } else if (bElement.ownText().contains("纸 张")) {
                            prod.setPaper(bElement.parent().ownText());
                        } else if (bElement.ownText().contains("印 次")) {
                            prod.setPrintNum(bElement.parent().ownText());
                        } else if (bElement.ownText().contains("I S B N")) {
                        	if(!isbn.equals(bElement.parent().ownText())) {
                        		return null;
                        	}
                            prod.setIsbn(isbn);
                        } else if (bElement.ownText().contains("包 装")) {
                            prod.setPack(bElement.parent().ownText());
                        }
                    }

                    for(Element cElement : c3Elements) {
                        if (cElement.ownText().contains("印刷时间")) {
                            prod.setPrintDate(cElement.ownText().replace("印刷时间：", ""));
                        }
                    }
                }
            }

            // 取淘宝product_id
            String productId = detailDoc.select("body > span#pid_span").first().attr("product_id");
            // 取编辑推荐
            Document subDetailDoc = Jsoup.connect(SUB_DETAIL_URL + productId).timeout(TIME_OUT).get();
            mElements = subDetailDoc.select("div#detail_all > div#abstract > div.descrip > textarea");
            if (mElements.size() > 0) {
                prod.setHAbstract(mElements.first().html());
            }
            // 取内容推荐
            mElements = subDetailDoc.select("div#detail_all > div#content > div.descrip > textarea");
            if (mElements.size() > 0) {
                prod.setContent(mElements.first().html());
            }
            // 取作者简介
            mElements = subDetailDoc.select("div#detail_all > div#authorintro > div.descrip > textarea");
            if (mElements.size() > 0) {
                prod.setAuthorIntro(mElements.first().html());
            }
            // 取目录
            mElements = subDetailDoc.select("div#detail_all > div#catalog > div.descrip > textarea");
            if (mElements.size() > 0) {
                prod.setCatalog(mElements.first().html());
            }
            // 取媒体评论
            mElements = subDetailDoc.select("div#detail_all > div#mediafeedback > div.descrip > textarea");
            if (mElements.size() > 0) {
                prod.setMediaFeedback(mElements.first().html());
            }
            
            // 下载图片
            new DownloadPicture(picList);
            prod.setProductPic(picList);
            prod.setUserId(userId);
            
            return prod;
    	} catch (Exception e) {
    		log.error(String.format("解析当当网上图书信息【%s】失败！", isbn), e);
    	}
    	
    	return null;
    }
}

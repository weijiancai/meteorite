package com.meteorite.core.parser.book;

import com.meteorite.core.util.UString;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author wei_jc
 * @since 0.0.1
 */
public class JingDongParser implements IProductParser {
	private static final Logger log = Logger.getLogger(JingDongParser.class);
	
	public static final String SEARCH_URL = "http://search.jd.com/Search?enc=utf-8&book=y&keyword=";
	public static final String OTHER_SEARCH_URL = "http://search.jd.com/Search?enc=utf-8&keyword=";
	public static final String JOURNAL_SEARCH_URL = "http://search.jd.com/bookadvsearch?keyword=%s&isbn=%s"; 
	
	private String userId;
	private String isbn;
	
	public JingDongParser(String isbn) {
		this(null, isbn);
	}
	
	public JingDongParser(String userId, String isbn) {
		this.userId = userId;
		this.isbn = isbn;
	}
	
	@Override
	public List<IWebProduct> parse() {
		List<IWebProduct> prodList = new ArrayList<IWebProduct>();
		try {
			// 打开搜索结果页面
			// Document doc = Jsoup.connect(SEARCH_URL + isbn).get();
			Document doc;
			if(isbn.startsWith("977")) { // 杂志处理
				String year = Calendar.getInstance().get(Calendar.YEAR) + "";
				doc = get(String.format(JOURNAL_SEARCH_URL, year, isbn));
			} else {
				doc = get(SEARCH_URL + isbn);
			}
			if (doc == null) {
				return prodList;
			}
			Elements elements = doc.select("div.main div.right-extra div#plist div.item");
			IWebProduct prod;
			if (elements != null && elements.size() > 0) {
				for (Element element : elements) {
					prod = parseProduct(element);
					if (prod != null) {
						prodList.add(prod);
					}
				}
			} else {
				doc = get(OTHER_SEARCH_URL + isbn);
		        elements = doc.select("div.main div.right-extra div#plist ul.list-h li.item-book");
		        for (Element element : elements) {
					prod = parseOtherProduct(element);
					if (prod != null) {
						prodList.add(prod);
					}
				}
		        
			}
		} catch (Exception e) {
			log.error("连接京东网站失败！", e);
		}
		
		return prodList;
	}
	
	private IWebProduct parseProduct(Element element) {
		Document detailDoc = null;
		try {
			WebProductImpl prod = new WebProductImpl();
			prod.setSourceSite(SiteName.JING_DONG.name());
			List<ProductPic> picList = new ArrayList<ProductPic>();
			
			// 取搜索结果图片 160 * 160
			picList.add(new ProductPic(new URL(element.select("div.p-img a img").first().attr("data-lazyload")), false));
            
            Elements mElements;
			// 打开详细页面
			String detailUrl = element.select("div.p-img a").first().attr("href");
			detailDoc = get(detailUrl);
			if (detailDoc == null) {
				return null;
			}
            // 取书名
            prod.setName(detailDoc.select("div#name h1").first().ownText());
            // 取详细页面图片 280 * 280
            String picUrl = detailDoc.select("div#spec-n1 img").first().attr("src");
			if (!picUrl.equals("http://img10.360buyimg.com/n11/")) {
				picList.add(new ProductPic(new URL(picUrl), true));
			}

            // 取定价
			Elements priceElement = detailDoc.select("li#summary-market div.dd");
			if(priceElement != null && priceElement.size() > 0) {
				prod.setPrice(priceElement.first().text().replace("￥", "").replace("?", "").trim());
			}
            // 取作者
			Elements authorElement = detailDoc.select("li#summary-author div.dd"); 
			if(authorElement != null && authorElement.size() > 0) {
				String authorStr = authorElement.first().text();
	            StringBuilder sb = new StringBuilder();
	            for(char c : authorStr.toCharArray()) {
	                if(c == '著') {
	                    prod.setAuthor(UString.trim(sb.toString()));
	                    sb = new StringBuilder();
	                } else if(c == '译') {
	                    prod.setTranslator(UString.trim(sb.toString()));
	                    sb = new StringBuilder();
	                } else if(c == '绘') {
	                    prod.setPainter(UString.trim(sb.toString()));
	                    sb = new StringBuilder();
	                } else {
	                    sb.append(c);
	                }
	            }
			} else {
				return null;
			}
            // 取出版社
            prod.setPublishing(detailDoc.select("div.w div.right ul#summary li#summary-ph div.dd").first().text());
            // 取出版时间
            mElements = detailDoc.select("div.w div.right ul#summary li#summary-published div.dd");
            if(mElements != null && mElements.size() > 0) {
            	prod.setPublishDate(mElements.first().text());
            }
            
            // 取ISBN
            prod.setIsbn(detailDoc.select("div.w div.right ul#summary li#summary-isbn div.dd").first().text());
            // 取商品介绍
            mElements = detailDoc.select("div.w div.right div#product-detail div#product-detail-1 > ul li");
            for (Element aElement : mElements) {
                String text = aElement.ownText();
                if (text.startsWith("版次：")) { // 取版次
                    prod.setBanci(aElement.ownText().substring(3).trim());
                } else if (text.startsWith("装帧：")) { // 取装帧
                    prod.setPack(aElement.ownText().substring(3).trim());
                } else if (text.startsWith("纸张：")) { // 取纸张
                    prod.setPaper(aElement.ownText().substring(3).trim());
                } else if (text.startsWith("印刷时间：")) { // 取印刷时间
                    prod.setPrintDate(aElement.ownText().substring(5).trim());
                } else if (text.startsWith("印次：")) { // 取印次
                    prod.setPrintNum(aElement.ownText().substring(3).trim());
                } else if (text.startsWith("正文语种：")) { // 取正文语种
                    prod.setLanguage(aElement.ownText().substring(5).trim());
                } else if (text.startsWith("开本：")) { // 取开本
                    prod.setKaiben(aElement.ownText().substring(3).trim());
                } else if (text.startsWith("页数：")) { // 取页数
                    prod.setPageNum(aElement.ownText().substring(3).trim());
                } else if (text.startsWith("作者：")) { // 作者
                    prod.setAuthor(aElement.ownText().substring(3).trim());
                } else if (text.startsWith("尺寸：")) { // 尺寸
                    String[] strs = aElement.ownText().substring(3).trim().split(";");
                    if(strs.length == 1) {
                        prod.setSize(strs[0]);
                    } else if(strs.length == 2) {
                        prod.setSize(strs[0]);
                        prod.setWeight(strs[1].replace("kg", ""));
                    }
                    if(prod.getSize() != null) {
                        strs = prod.getSize().replace("cm", "").split("x");
                        if(strs.length == 3) {
                            prod.setLength(strs[0]);
                            prod.setWidth(strs[1]);
                            prod.setDeep(strs[2]);
                        }
                    }
                }
            }

            // 取其他信息
            mElements = detailDoc.select("div.w div.right div#product-detail div#product-detail-1 > div.sub-m");
            for (Element aElement : mElements) {
                String title = aElement.select("div.sub-mt h3").text();
                String value = aElement.select("div.sub-mc").html();
                if("内容简介".equals(title)) { // 内容简介
                    prod.setContent(value);
                } else if("作者简介".equals(title)) { // 作者简介
                    prod.setAuthorIntro(value);
                } else if("精彩书摘".equals(title)) { // 取精彩书摘
                    prod.setExtract(value);
                } else if("编辑推荐".equals(title)) { // 编辑推荐
                    prod.setHAbstract(value);
                } else if("媒体评论".equals(title)) { // 媒体评论
                    prod.setMediaFeedback(value);
                } else if("目录".equals(title)) { // 目录
                    prod.setCatalog(value);
                } else if("前言".equals(title)) { // 前言
                    prod.setPrologue(value);
                }
            }

			// 下载图片
			new DownloadPicture(picList);
			prod.setProductPic(picList);
			
			prod.setUserId(userId);
			
			return prod;
		} catch (Exception e) {
			log.error(String.format("解析京东网上图书信息【%s】失败！", isbn), e);
		}
		
		return null;
	}
	
	private IWebProduct parseOtherProduct(Element element) {
		Document detailDoc = null;
        try {
            WebProductImpl prod = new WebProductImpl();
            prod.setSourceSite(SiteName.JING_DONG.name());
            List<ProductPic> picList = new ArrayList<ProductPic>();

            // 取搜索结果图片 160 * 160
            picList.add(new ProductPic(new URL(element.select("div.p-img a img").first().attr("data-lazyload")), false));
            // 打开详细页面
            String detailUrl = element.select("div.p-img a").first().attr("href");
            
            detailDoc = get(detailUrl);
            // 取书名
            prod.setName(detailDoc.select("div#name h1").first().ownText());
            // 取详细页面图片 280 * 280
            picList.add(new ProductPic(new URL(detailDoc.select("div#spec-n1 img").first().attr("src")), true));

            // 取summary
            Elements mElements = detailDoc.select("ul#summary");
            Element summaryElements = mElements.first();
            for (Element aElement : summaryElements.select("> li")) {
                String span = aElement.getElementsByTag("span").text();
                if ("演唱者/演奏者：".equals(span)) {
                    String values = aElement.text().replace("演唱者/演奏者：", "");
                    prod.setAuthor(UString.trim(values));
                } else if (span.contains("条") && span.contains("形") && span.contains("码")) {
                    prod.setIsbn(span.substring(6));
                }
            }

            // 取定价
            Elements priceElement = detailDoc.select("li#summary-market div.dd");
            if(priceElement != null && priceElement.size() > 0) {
            	prod.setPrice(priceElement.first().text().substring(1));
            }

            // 取编辑推荐
            mElements = detailDoc.select("div.w div.right div#recommend-editor div.mc div.con");
            if (mElements.size() > 0) {
                prod.setHAbstract(mElements.first().html());
            }

            // TODO 修正内容简介等信息
            mElements = detailDoc.select("div.w div.right-extra > div.m1 div.mt h2");
            for (Element aElement : mElements) {
                String text = aElement.ownText();
                if (text != null) {
                    text = text.trim();
                    String value = aElement.parent().parent().select("div.mc div.con").html();
                    if ("内容简介".equals(text)) { // 取内容简介
                        prod.setContent(value);
                    }  else if ("曲目".equals(text)) { // 取曲目
                        prod.setCatalog(value);
                    }
                }
            }

            // 下载图片
			new DownloadPicture(picList);
			prod.setProductPic(picList);
			
			prod.setUserId(userId);

            return prod;
        } catch (Exception e) {
        	log.error(String.format("解析京东网上音乐/CD信息【%s】失败！", isbn), e);
        }

        return null;
    }
    /**
     * HTTP GET请求
     * 注意：目前只用于京东【gb2312】，其他网站【utf8】这样转码会有问题
     *
     * @param url
     * @return 返回Jsoup Document对象
     */
    public static Document get(String url) {
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6");

        try {
            HttpResponse response = client.execute(httpGet);
            if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                log.debug(String.format("HTTP请求失败，状态吗【%s】！！！ ", response.getStatusLine().getStatusCode()));
                return null;
            }
            HttpEntity entity = response.getEntity();
            String content = new String(EntityUtils.toByteArray(entity), "GBK");

            return Jsoup.parse(content);
        } catch (Exception e) {
            log.debug("HTTP请求失败！！！", e);
        } finally {
            httpGet.releaseConnection();
            client.getConnectionManager().shutdown();
        }
        return null;
    }
}

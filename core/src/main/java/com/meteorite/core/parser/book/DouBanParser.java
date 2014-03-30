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
 * 豆瓣解析器
 * 
 * @author wei_jc
 * @since 0.0.1
 */
public class DouBanParser implements IProductParser {
	private static final Logger log = Logger.getLogger(DouBanParser.class);
	
	public static final String SEARCH_URL = "http://book.douban.com/subject_search?cat=1001&search_text=";
	private static final int TIME_OUT = 2 * 1000; // 2秒超时
	
	private String userId;
	private String isbn;
	
	public DouBanParser(String isbn) {
		this(null, isbn);
	}
	
	public DouBanParser(String userId, String isbn) {
		this.userId = userId;
		this.isbn = isbn;
	}
	
	@Override
	public List<IWebProduct> parse() {
		List<IWebProduct> prodList = new ArrayList<IWebProduct>();
		try {
			// 打开搜索结果页面
			Document doc = Jsoup.connect(SEARCH_URL + isbn).timeout(TIME_OUT).get();
			String searchResult = doc.select("div#content div.article > div.trr").text();
			try {
				int result = Integer.parseInt(UString.trim(searchResult.substring(searchResult.indexOf("共") + 1)));
				if (result == 0) {
					return new ArrayList<IWebProduct>();
				}
			} catch (NumberFormatException e) {
				return new ArrayList<IWebProduct>();
			}
			
			Elements elements = doc.select("div#content div.article ul.subject-list li.subject-item");
			if (elements != null) {
				IWebProduct prod;
				for (Element element : elements) {
					prod = parseProduct(element);
					if (prod != null) {
						prodList.add(prod);
					}
				}
			}
		} catch (Exception e) {
			log.error("连接豆瓣网站失败！", e);
		}
		
		return prodList;
	}
	
	private IWebProduct parseProduct(Element element) {
		try {
			WebProductImpl prod = new WebProductImpl();
			prod.setSourceSite(SiteName.DOU_BAN.name());
			List<ProductPic> picList = new ArrayList<ProductPic>();
			
			Elements mElements;
			// 打开详细页面
			String detailUrl = element.select("div.pic a").first().attr("href");
			
			Document detailDoc = Jsoup.connect(detailUrl).timeout(TIME_OUT).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6").get();
			// 取书名
			prod.setName(UString.trim(detailDoc.select("div#wrapper > h1 > span").first().ownText()));
			// 取详细页面图片 101 * 146
			String picUrl = detailDoc.select("div#content div.article div#mainpic > a > img").first().attr("src");
			if (!picUrl.equals("http://img3.douban.com/pics/book-default-medium.gif")) {
				picList.add(new ProductPic(new URL(detailDoc.select("div#content div.article div#mainpic > a").first().attr("href")), true));
			}
			
			// 取基本信息
			String infoHtml = detailDoc.select("div#content div.article div#info").first().html();
			String[] infoStrs = infoHtml.split("<br />");
			for (String infoStr : infoStrs) {
				if (infoStr.contains("作者")) {
					prod.setAuthor(Jsoup.parse(infoStr).select("a").text());
				} else if (infoStr.contains("出版社")) {
					prod.setPublishing(Jsoup.parse(infoStr).select("body").first().ownText());
				} else if (infoStr.contains("出版年")) {
					prod.setPublishDate(Jsoup.parse(infoStr).select("body").first().ownText());
				} else if (infoStr.contains("页数")) {
					prod.setPageNum(UString.trim(Jsoup.parse(infoStr).select("body").first().ownText().replace("页", "")));
				} else if (infoStr.contains("定价")) {
					prod.setPrice(Jsoup.parse(infoStr).select("body").first().ownText().replace("元", ""));
				} else if (infoStr.contains("ISBN")) {
					prod.setIsbn(isbn);
				} else if (infoStr.contains("装帧")) {
					prod.setPack(Jsoup.parse(infoStr).select("body").first().ownText());
				} else if (infoStr.contains("译者")) {
					prod.setTranslator(Jsoup.parse(infoStr).select("a").text());
				}
			}
			
			mElements = detailDoc.select("div#content div.article > div.related_info > h2");
			for (Element aElement : mElements) {
				String text = aElement.text();
				// 去掉<script>
				aElement.nextElementSibling().select("script").remove();
				String siblingText = aElement.nextElementSibling().html();
				if (text.contains("内容简介")) {
					prod.setContent(siblingText);
				} else if (text.contains("作者简介")) {
					prod.setAuthorIntro(siblingText);
				} else if (text.contains("目录")) {
					prod.setCatalog(siblingText);
				}
			}
			
			// 下载图片
			new DownloadPicture(picList);
			prod.setProductPic(picList);
			prod.setUserId(userId);
			
			return prod;
		} catch (Exception e) {
			log.error(String.format("解析豆瓣网上图书信息【%s】失败！", isbn), e);
		}
		
		return null;
	}
}

/**
 * 2013-4-11
 */
package com.meteorite.core.parser.book;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meteorite.core.util.UBase64;
import com.meteorite.core.util.UNumber;
import com.meteorite.core.util.UString;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 开卷数据解析
 *
 * @author wei_jc
 * @since 1.0
 */
public class OpenBookDataParser implements IProductParser {
	private static final Logger log = Logger.getLogger(OpenBookDataParser.class);

	private static final String SEARCH_URL = "http://www.openbookdata.com.cn/Book/%s.html";// 获取详细页面
    private static final String SEARCH_DETAIL_URL = "http://www.openbookdata.com.cn/handlers/bookhandler.ashx?Type=0&bookid="; // 获取内容页面

	private static final String USER_AGENT = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)"; // 用户浏览器信息
	
	private static final int TIME_OUT = 60 * 1000; // 1分钟超时

	private int bookId; // 开卷bookid

	public OpenBookDataParser(int bookId) {
		this.bookId = bookId;
	}
	
	@Override
	public List<IWebProduct> parse() {
		List<IWebProduct> prodList = new ArrayList<IWebProduct>();
		String id;
		try {
            id = UBase64.encode(UString.leftPadding(bookId + "", '0', 7) + "_OSGA");
            Document doc = Jsoup.connect(String.format(SEARCH_URL, id)).timeout(TIME_OUT).userAgent(USER_AGENT).get();
            prodList.add(parseProduct(doc, id));
        } catch (SocketTimeoutException e) {
			log.warn("网络连接超时！ ");
		} catch (Exception e) {
			log.error("连接开卷新书网站失败！", e);
		}
		
		return prodList;
	}

	
	public IWebProduct parseProduct(Element detailDoc, String bookId) {
		String url;
		try {
			WebProductImpl prod = new WebProductImpl();
			prod.setSourceSite(SiteName.OPEN_BOOK_DATA.name());
			List<ProductPic> picList = new ArrayList<ProductPic>();
			
			// 取书名
			prod.setName(detailDoc.select("span#__title").text().trim().replace("'", "，"));
			// 取详细页面图片 200 * 200
			String picUrl = detailDoc.select("img#Y_img").first().attr("src");
			if (!picUrl.equals("/images/no-image200X200.jpg")) { // 不是默认图片时，取图片
				picList.add(new ProductPic(new URL(picUrl), true));
			}
			
			// 取基本信息
			Elements elements = detailDoc.select("ul.scrTopUl > li");
			for (Element e : elements) {
				String key = e.select("span.textFlowLeft").html().replaceAll("&nbsp;", "");
				String value = e.select("span.scrTopRight").text().replace("-", "").replace("null", "").replace("'", "，");
				if (key.equals("作者")) {
					prod.setAuthor(value);
				} else if (key.equals("出版社")) {
					prod.setPublishing(value);
				} else if (key.equals("出版时间")) {
					if(!UString.isEmpty(value) && value.length() == 6) {
						value = value.substring(0, 4) + "-" + value.substring(4);
					}
					prod.setPublishDate(value);
				} else if (key.equals("页数")) {
					prod.setPageNum(value.replace("页", "").equals("-") ? "" : value.replace("页", ""));
				} else if (key.equals("定价")) {
					prod.setPrice(value);
				} else if (key.equals("ISBN")) {
					prod.setIsbn(value);
				} else if (key.equals("装帧")) {
					prod.setPack(value);
				} else if (key.equals("译者")) {
					prod.setTranslator(value);
				} else if (key.equals("版次")) {
					prod.setBanci(value);
				} else if (key.equals("开本")) {
					prod.setKaiben(value);
				} else if(key.equals("丛书名")) {
					prod.setSeriesName(value);
				}
			}
			
			url = SEARCH_DETAIL_URL + bookId;
            System.out.println(url);
            try {
				String detailText = Jsoup.connect(url).timeout(TIME_OUT).userAgent(USER_AGENT).get().text();
				JSONArray array = (JSONArray) JSON.parse(detailText);
				
				if (array.size() == 1) {
					JSONObject object = (JSONObject) array.get(0);
					// 内容简介
					prod.setContent(object.getString("Abstract"));
					// 作者简介
					prod.setAuthorIntro(object.getString("AuthorIntroduction"));
					// 目录
					prod.setCatalog(object.getString("Catalogue"));
					// 出版社推荐语
					prod.setMediaFeedback(object.getString("Recommendation"));
					// 字数
					prod.setWordCount(object.getString("WordNum"));
					// 语种
					prod.setLanguage(object.getString("Language"));
				}
			} catch (Exception e) {
				log.error("解析JSON对象失败！ " + url);
			}
			// 下载图片
			new DownloadPicture(picList);
			prod.setProductPic(picList);
//			prod.setUserId(userId);
			
			return prod;
		} catch (Exception e) {
			log.error(String.format("解析开卷新书网信息【%s】失败！", bookId), e);
		}
		
		return null;
	}
}

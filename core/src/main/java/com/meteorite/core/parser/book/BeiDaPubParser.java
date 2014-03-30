/**
 * 
 */
package com.meteorite.core.parser.book;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.meteorite.core.util.UNumber;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 北大出版社解析器
 * 
 * @author wei_jc
 */
public class BeiDaPubParser implements IProductParser {
	private static final Logger log = Logger.getLogger(BeiDaPubParser.class);
	
	private String userId;
	private String html; // html文本字符串
	
	private Set<String> allIsbn = new HashSet<String>();
	
	public BeiDaPubParser(String userId, String html) throws Exception {
		this.userId = userId;
		this.html = html;
		
		/*DataStore ds = DataStore.getInstance("select h_isbn from in_db_web_product", YhInfoDbi.getDbi());
		ds.retrieve();
		for(int i=0;i<ds.rowCount();i++){
			this.allIsbn.add(ds.getItemString(i, "h_isbn"));
		}*/
	}

	/* (non-Javadoc)
	 * @see com.yhsms.yhinfo.fetchbook.parser.IProductParser#parse()
	 */
	@Override
	public List<IWebProduct> parse() {
		List<IWebProduct> prodList = new ArrayList<IWebProduct>();
		
    	Document doc = Jsoup.parse(html);
    	Elements tables = doc.select("body > table");
    	// 查询body下的所有table元素
    	IWebProduct prod;
    	for(Element table : tables) {
    		prod = parseProduct(table);
    		if(prod != null && !allIsbn.contains(prod.getIsbn())) {
    			prodList.add(prod);
    		}
    	}
    	
		return prodList;
	}

	private IWebProduct parseProduct(Element element) {
    	try {
    		WebProductImpl prod = new WebProductImpl();
        	prod.setSourceSite(SiteName.PUB_BEI_DA.name());
        	prod.setUserId(userId);
        	prod.setContent("");
        	prod.setPrologue("");
        	List<ProductPic> picList = new ArrayList<ProductPic>();
        	prod.setProductPic(picList);
        	
    		// 取表格的所有行
    		Elements trs = element.select("> tbody > tr");
    		Elements elements;
    		for(Element tr : trs) {
    			elements = tr.select("> td.bookimgb");
    			if(elements.size() == 1) {
    				// 取图片URL
    	    		picList.add(new ProductPic(new URL(tr.select("> td.bookimgb > img.bookimg").first().attr("src")), true));
    	    		
    	    		// 取基本信息
    	    		elements = tr.select("> td > table > tbody > tr");
    	    		for(Element aElement : elements) {
    	    			Elements tds = aElement.select("td");
    	    			String name = tds.first().text();
    	    			String value = tds.get(1).text();
    	    			
    	    			if(name.contains("书名")) {
    	    				prod.setName(value);
    	    			} else if(name.contains("ISBN")) {
    	    				prod.setIsbn(value.replace("-", ""));
    	    			} else if(name.contains("作者")) {
    	    				prod.setAuthor(value);
    	    			} else if(name.contains("开本")) {
    	    				prod.setKaiben(value);
    	    			} else if(name.contains("装订")) {
    	    				prod.setPack(value);
    	    			} else if(name.contains("字数")) {
    	    				if(value.contains("千字")) {
    	    					prod.setWordCount(UNumber.toInt(value.replace("千字", "").replace(" ", "")) * 1000 + "");
    	    				} else {
    	    					prod.setWordCount(value);
    	    				}
    	    			} else if(name.contains("定价")) {
    	    				prod.setPrice(value);
    	    			} else if(name.contains("出版日期")) {
    	    				prod.setPublishDate(value);
    	    			}
    	    		}
    			} else {
	    			String htmlStr = tr.select("> td").first().html().trim();
	    			if(htmlStr.startsWith("前言：")) { // 取前言
	    				prod.setPrologue(htmlStr.substring(3));
	    			} else if(htmlStr.startsWith("出版说明：")) {
	    				prod.setPrologue(prod.getPrologue() + "<br/>" + htmlStr);
	    			} else if(htmlStr.startsWith("内容简介：")) {
	    				prod.setContent(htmlStr.substring(5));
	    			} else if(htmlStr.startsWith("精彩片段：")) {
	    				prod.setExtract(htmlStr.substring(5));
	    			} else if(htmlStr.startsWith("章节目录：")) {
	    				prod.setCatalog(htmlStr.substring(5));
	    			} else if(htmlStr.startsWith("作者简介：")) {
	    				prod.setAuthorIntro(htmlStr.substring(5));
	    			} else if(htmlStr.startsWith("书　　评：")) {
	    				prod.setMediaFeedback(htmlStr.substring(5));
	    			} else if(htmlStr.startsWith("后　　记：")) {
	    				prod.setContent(prod.getContent() + "<br/>" + htmlStr);
	    			} else if(htmlStr.startsWith("参考资料：")) {
	    				prod.setContent(prod.getContent() + "<br/>" + htmlStr);
	    			} else if(htmlStr.startsWith("其　　它：")) {
	    				prod.setContent(prod.getContent() + "<br/>" + htmlStr);
	    			}
	    		}
    		}
    		
    		return prod;
    	} catch (Exception e) {
    		log.error(String.format("解析北大出版社图书信息失败！"), e);
    	}
    	
		return null;
	}
}

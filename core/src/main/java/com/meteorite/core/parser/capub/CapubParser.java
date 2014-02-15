/**
 * 2013-4-11
 */
package com.meteorite.core.parser.capub;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.meteorite.core.util.UBase64;
import com.meteorite.core.util.UDate;
import com.meteorite.core.util.UNumber;
import com.meteorite.core.util.UString;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 新闻出版总署CIP数据解析
 * 
 * @author wei_jc
 * @since 1.0
 */
public class CapubParser {
	private static final String DETAIL_URL = "http://www.capub.cn/pdm/business/queryBookAction.do?method=queryDetail&id=";// 获取详细页面
	private static final String CHECK_APPROVE_NO_URL = "http://www.capub.cn/pdm/business/CipInfoAction.do?method=checkApproveNo&approveNo="; // 检查CIP核字号
	
	private static final String MAIN_URL = "http://www.capub.cn/pdm"; // 主页
	private static final String LOGIN_URL = "http://www.capub.cn/pdm/business/MemberAction.do?method=check"; // 登录
	private static final String CAPTCHA_IMG_URL = "http://www.capub.cn/pdm/business/site/util/image.jsp?isDecorator=false"; // 获得验证码图片
	private static final String USER_AGENT = "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)"; //  用户浏览器信息
	
	private static final int TIME_OUT = 60 * 1000; // 15秒超时
	
	private static Map<String, String> cookieMap = new HashMap<String, String>();// 记录session
	
	private CapubBean bean;
	private boolean isStop;
	private PrintWriter pw;
	
	public CapubParser() {}
	
	public CapubParser(CapubBean bean) throws IOException {
		this.bean = bean;
		File file = new File(CapubBO.capubDir, "cip_not_found.txt");
		if(!file.exists()) {
			file.createNewFile();
		}
		pw = new PrintWriter(new FileWriter(file, true));
	}
	
	public boolean isStop() {
		return isStop;
	}

	public void setStop(boolean isStop) {
		this.isStop = isStop;
	}

	private boolean parse(String id, String pici) throws Exception {
        System.out.println("cip_detail_id = " + id);

		// 打开详细结果页面
		Document doc = Jsoup.connect(DETAIL_URL + id).timeout(TIME_OUT).cookies(cookieMap).userAgent(USER_AGENT).get();
		Element element = doc.select("body").get(0);
		String title = doc.select("head title").get(0).text();
		if(!"书目详细".equals(title)) {
			cookieMap.clear();
			throw new Exception("请登录CIP！");
		}
		ProductUploadInfo info = parseProduct(element);
		if(info != null) {
			if(!UString.isEmpty(info.getCipId())) {
				ProductUploadInfo newInfo = checkApproveNo(info.getCipId());
				if(newInfo != null) {
					info = newInfo;
				}
			}
			info.setCipDetailId(id);
		}
		return addProductUpload(info, pici);
	}
	
	public String parseByStart(String startId) throws Exception {
		if(cookieMap.isEmpty()) {
			throw new Exception("请登录CIP！");
		}
		int start = UNumber.toInt(startId.substring(0, startId.length() - 5));
		String startStr;
		String pici = "CIP数据 " + UDate.dateToString(new Date(), "yyyy-MM-dd HH:ss:mm");
		String str;
		
		while(true) {
			startStr = UString.leftPadding(start + "", '0', 9);
			str = startStr + "CPP02";
			boolean isSuccess = parse(str, pici);
			str = startStr + "CPP01";
			isSuccess = isSuccess || parse(str, pici);
			
			if(!isSuccess) {
				break;
			}
			start++;
		}
		
		return str;
	}
	
	public void parseByEndStart() throws Exception {
		if(bean.getCurrentId() <= bean.getStartId()) {
			return;
		}
		if(cookieMap.isEmpty()) {
			throw new Exception("请登录CIP！");
		}
		
		String endStr;
		String str = "";
		String pici = "CIP数据 " + UDate.dateToString(new Date(), "yyyy-MM-dd HH:ss:mm");
		
		do {
			if(isStop) {
				return;
			}
			endStr = UString.leftPadding(bean.getCurrentId() + "", '0', 9);
			try {
				str = endStr + "CPP02";
				boolean isSuccess = parse(str, pici);
				str = endStr + "CPP01";
				isSuccess = isSuccess || parse(str, pici);
				if(!isSuccess) {
					pw.println(endStr);
					pw.flush();
					
					bean.addNotFound(str);
				}
				
				bean.save();
			} catch(Exception e) {
				if("请登录CIP！".equals(e.getMessage())) {
					throw e;
				}
				e.printStackTrace();
				pw.println(endStr + " --> error");
				pw.flush();
				bean.addNotFound(str);
			}
			
			bean.setCurrentId(bean.getCurrentId() - 1);
		} while(bean.getCurrentId() >= bean.getStartId());
	}
	
	public ProductUploadInfo parseProduct(Element element) {
		try {
			ProductUploadInfo prod = new ProductUploadInfo();
			List<Element> trs = element.select("table.bizform tr");
			List<Element> tds;
			
			if(trs == null || trs.size() == 0) {
				return null;
			}
			
			tds = trs.get(1).select("td");
			// CIP核准号
			prod.setCipId(tds.get(1).text());
			if(UString.isEmpty(prod.getCipId())) {
				return null;
			}
			// ISBN
			prod.sethMytm(tds.get(3).text());
			prod.sethIsbn(prod.gethMytm().replace("-", ""));
			// 正书名
			prod.sethName(trs.get(2).select("td").get(1).text());
			// 出版单位
			prod.setPubName(trs.get(3).select("td").get(1).text());
			
			tds = trs.get(4).select("td");
			// 出版地
			prod.setPubLocation(tds.get(1).text());
			// 出版时间
			String pubDate = tds.get(3).text();
			if(!UString.isEmpty(pubDate)) {
				Date date = UDate.toDate(pubDate);
				if(date == null && pubDate.length() == 4) {
					date = UDate.toDate(pubDate, "yyyy");
				}
				if(date.after(UDate.toDate("1900-01-01"))) {
					prod.setThisPubTime(date);
					prod.setInputDate(prod.getThisPubTime());
				}
			}
			// 第一责任者
			prod.sethWriter(subString(trs.get(5).select("td").get(1).text(), 100));
			
			tds = trs.get(6).select("td");
			// 版次
			prod.setThisEdition(tds.get(1).text());
			// 印次
			prod.setPrintTimes(tds.get(3).text());
			
			tds = trs.get(7).select("td");
			// 定价(元)
			prod.sethPrice(UNumber.toDouble(tds.get(1).text().replace("CNY", "")));
			// 正文语种
			prod.setLanguage(subString(tds.get(3).text(), 40));
			
			tds = trs.get(8).select("td");
			// 开本或尺寸
			prod.setKbSize(tds.get(1).text());
//			prod.setKbId(tds.get(1).text());
			// 装祯方式
			prod.setZzId(tds.get(3).text());
			
			tds = trs.get(9).select("td");
			// 页数
			prod.setPageCount(UNumber.toInt(tds.get(1).text()));
			// 印数（册）
			prod.setPrintAmount(UNumber.toInt(tds.get(3).text()));
			
			// 中图法分类
			prod.setIsbnClass(getIsbnClass(trs.get(10).select("td").get(1).text()));
			
			// 主题词
			prod.setKeywords(trs.get(11).select("td").get(1).text());
			
			// 内容提要
			prod.setContent(trs.get(12).select("td").get(1).text());
			
			return prod;
		} catch (Exception e) {
            System.out.println("解析中国新闻出版信息网信息失败！\\r\\n" + element.html());
            e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 取中图分类，如果有多个，取第一个
	 * 
	 * @param isbnClass
	 * @return
	 */
	private String getIsbnClass(String isbnClass) {
		String[] strs = isbnClass.split("；");
		return strs[0];
	}
	
	/**
	 * 截取字符串
	 * 
	 * @param str
	 * @return
	 */
	private String subString(String str, int length) {
		return UString.isEmpty(str) ? str : UString.substringByByte(str, 0, length);
	}
	
	/**
	 * 根据cip核准号查询结果，解析商品信息
	 * 
	 * @param element
	 * @return
	 */
	public ProductUploadInfo parseProductByCip(Element element) {
		try {
			ProductUploadInfo prod = new ProductUploadInfo();
			
			Document doc = Jsoup.parse(element.html().replace("<!--", "").replace("-->", ""));
			List<Element> trs = doc.select("table.bizform tr");
			if(trs == null || trs.size() == 0) {
				return null;
			}
			
			List<Element> tds;
			
			tds = trs.get(1).select("td");
			// 1. CIP核准号
			prod.setCipId(tds.get(1).text());
			// 1. ISBN
			prod.sethMytm(tds.get(3).text());
			prod.sethIsbn(prod.gethMytm().replace("-", ""));
			
			// 2. 正书名
			prod.sethName(trs.get(2).select("td").get(1).text());
			
			// 3. 丛书名
			prod.setSeriesName(trs.get(3).select("td").get(1).text());
			
			// 4. 出版单位
			prod.setPubName(trs.get(4).select("td").get(1).text());
			
			tds = trs.get(5).select("td");
			// 5. 出版地
			prod.setPubLocation(tds.get(1).text());
			// 5. 出版时间
			String pubDate = tds.get(3).text();
			if(!UString.isEmpty(pubDate)) {
				Date date = UDate.toDate(pubDate);
				if(date == null && pubDate.length() == 4) {
					date = UDate.toDate(pubDate, "yyyy");
				}
				if(date.after(UDate.toDate("1900-01-01"))) {
					prod.setThisPubTime(date);
					prod.setInputDate(prod.getThisPubTime());
				}
			}
			
			// 6. 第一责任者
			prod.sethWriter(subString(trs.get(6).select("td").get(1).text(), 100));
			
			tds = trs.get(7).select("td");
			// 7. 版次
			prod.setThisEdition(tds.get(1).text());
			// 7. 印次
			prod.setPrintTimes(tds.get(3).text());
			
			tds = trs.get(8).select("td");
			// 8. 定价(元)
			prod.sethPrice(UNumber.toDouble(tds.get(1).text()));
			// 8. 正文语种
			prod.setLanguage(subString(tds.get(3).text(), 40));
			
			tds = trs.get(9).select("td");
			// 9. 开本或尺寸
			prod.setKbSize(tds.get(1).text());
//			prod.setKbId(tds.get(1).text());
			// 装祯方式
			prod.setZzId(tds.get(3).text());
			
			tds = trs.get(10).select("td");
			// 10. 页数
			prod.setPageCount(UNumber.toInt(tds.get(1).text()));
			// 10. 印数（册）
			prod.setPrintAmount(UNumber.toInt(tds.get(3).text()));
			
			// 11. 中图法分类
			prod.setIsbnClass(getIsbnClass(trs.get(11).select("td").get(1).text()));
			
			// 12. 主题词
			prod.setKeywords(trs.get(12).select("td").get(1).text());
			
			// 13. 内容提要
			prod.setContent(trs.get(13).select("td").get(1).text());
			
			return prod;
		} catch (Exception e) {
            System.out.println("解析中国新闻出版信息网信息失败！\\r\\n" + element.html());
            e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 登录中国新闻出版信息网
	 * 
	 * @param userId
	 * @param password
	 * @param captcha
	 * @throws IOException
	 */
	public synchronized static void login(String userId, String password, String captcha) throws Exception {
        System.out.println("登录中国新闻出版信息网............");
		// 登录
		Map<String, String> params = new HashMap<String, String>();
		params.put("userid", userId);
		params.put("password", password);
		params.put("captcha", captcha);
		Connection conn = Jsoup.connect(LOGIN_URL).timeout(TIME_OUT);
		Document doc = conn.data(params).userAgent(USER_AGENT).cookies(cookieMap).post();
		System.out.println(doc.html());
		if(doc.text().contains("errorCode")) {
			throw new Exception("验证码无效！");
		}
	}
	
	/**
	 * 打开登录界面，获得session
	 * 
	 * @throws IOException
	 */
	public static void goMain() throws IOException {
		Connection conn = Jsoup.connect(MAIN_URL);
		conn.get();
		cookieMap = conn.response().cookies();
	}
	
	public static String getCaptchaImg() throws Exception {
		if(cookieMap.isEmpty()) {
			goMain();
		}
		Connection conn = Jsoup.connect(CAPTCHA_IMG_URL).cookies(cookieMap);
		conn.get();
		
		return UBase64.encode(conn.response().bodyAsBytes());
	}

	public ProductUploadInfo checkApproveNo(String approveNo) throws Exception {
		Document doc = Jsoup.connect(CHECK_APPROVE_NO_URL + approveNo).timeout(TIME_OUT).userAgent(USER_AGENT).get();
		Elements elements = doc.select("div#info");
		if(elements == null || elements.size() ==0) {
			throw new CipNotFoundException();
		}
		
		Element element = elements.get(0);
		
		return parseProductByCip(element);
	}
	
	public boolean addProductUpload(ProductUploadInfo prod, String pici) throws Exception {
		if(prod == null) {
			return false;
		}
		
		/*ProductUploadBO bo = new ProductUploadBO(null);
		
		String memberId = MemberBO.CAPUB_MEMBER_ID;
		int num = ReceiptUtil.createSerialNumber(ProductBO.PROD_RECEIPT_STATION, YhbisServerReceiptClass.PRODUCT, true, YhbisServerDbi.getDbi());
		String hid = memberId + YhbisServerReceiptClass.PRODUCT + UtilString.leftPadding(num+"", '0', 9);
		prod.sethId(hid);
		prod.setMemberIdUpload(memberId);
		prod.setPubId(PubDictMappingBO.PUB_CAPUB_ID);
		prod.setPubHId(prod.getCipId());
		prod.setPubPici(pici);
		prod.setInputStat(InputStatList.FINISH);
		prod.setVerifyStat(VerifyStatList.NOT_VERIFY);
		
		bo.addRecord(prod);*/
		return true;
	}
}

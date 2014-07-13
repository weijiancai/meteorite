package com.meteorite.core.parser.mobile;

import com.meteorite.core.util.UString;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 从吉号吧网站（http://www.jihaoba.com/tools/?com=haoduan）抓取手机号码
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class FetchMobileNumber {
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.102 Safari/537.36"; // 用户浏览器信息
    private static final int TIME_OUT = 2 * 60 * 1000; // 2分钟超时
    public static final String BASE_URL = "http://www.jihaoba.com/tools/?com=haoduan";

    private Map<String, String> cookies;

    public List<MobileNumber> fetch() throws IOException {
        List<MobileNumber> result = new ArrayList<>();
        getProvinceUrl();
        return result;
    }

    private void getProvinceUrl() throws IOException {
        Connection conn = Jsoup.connect("http://www.jihaoba.com/").userAgent(USER_AGENT);
        Connection.Response response = conn.execute();
        cookies = response.cookies();
        System.out.println(response.cookies());
        Document doc = Jsoup.connect(BASE_URL).userAgent(USER_AGENT).cookies(cookies).timeout(TIME_OUT).get();
        Elements elements = doc.select("div#right div.right1 div.nr div.nr1 a");
        for (Element element : elements) {
            String href = element.attr("href");
            String province = element.text();
            System.out.println(province + " > " + href);
            if ("北京".equals(province) || "上海".equals(province) || "天津".equals(province) || "重庆".equals(province)) {
                getCardTypeUrl(href, province, province);
            } else {
                getCityUrl(href, province);
            }
        }
    }

    private void getCityUrl(String url, String province) throws IOException {
        Document doc = Jsoup.connect(url).userAgent(USER_AGENT).cookies(cookies).timeout(TIME_OUT).get();
        Elements elements = doc.select("div#right div.right1 div.nr div.nr1 a");
        for (Element element : elements) {
            String href = element.attr("href");
            String city = element.text();
            if (UString.isNotEmpty(city)) {
                System.out.println("    " + city + " > " + href);
                getCardTypeUrl(href, province, city);
            }
        }
    }

    private void getCardTypeUrl(String url, String province, String city) throws IOException {
        Document doc = Jsoup.connect(url).userAgent(USER_AGENT).cookies(cookies).timeout(TIME_OUT).get();
        Elements elements = doc.select("div#right div.right1 div.nr div.nr1 a");
        for (Element element : elements) {
            String href = element.attr("href");
            String cardType = element.text();
            if (UString.isNotEmpty(cardType)) {
                System.out.println("    " + cardType + " > " + href);
                getCodeSegmentUrl(href, province, city);
            }
        }
    }

    private void getCodeSegmentUrl(String url, String province, String city) throws IOException {
        Document doc = Jsoup.connect(url).userAgent(USER_AGENT).cookies(cookies).timeout(TIME_OUT).get();
        Elements elements = doc.select("div#right div.right3 div.nr1 a");
        for (Element element : elements) {
            String href = element.attr("href");
            String codeSegment = element.text().replace("*", "");
            System.out.println("    " + codeSegment + " > " + href);
            getCodeUrl(href, province, city, codeSegment);
        }
    }

    private void getCodeUrl(String url, String province, String city, String codeSegment) throws IOException {
        Document doc = Jsoup.connect(url).userAgent(USER_AGENT).cookies(cookies).timeout(TIME_OUT).get();
        Elements liElement = doc.select("div#right div.right22 div.nr3 div.left li");
        String cardType = liElement.get(2).select("span").get(0).text();
        String operator = liElement.get(3).select("span").get(0).text();

        String codes = doc.select("div#right div.right22 div.nr1 div.fleft textarea").get(0).text();
        /*System.out.println(cardType + " --> " + operator);
        System.out.println(codes);*/
        for (String code : codes.split("\n")) {
            MobileNumber mobileNumber = new MobileNumber(code, province, city, cardType, operator, codeSegment);
            System.out.println(mobileNumber);
        }

    }
}

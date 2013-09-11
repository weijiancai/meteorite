package com.meteorite.core.parser.http;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class JSoupParserTest {
    @Test
    public void testParse() throws Exception {
        String url = "http://category.dangdang.com/?ref=www-0-C#ref=www-0-C";
        JSoupParser parser = new JSoupParser(url);
        Document doc = parser.parse();
        Elements elements = doc.select("div#bd div.col_1 div.cfied-list");

//        Map<String, List<String>> map = new HashMap<String, List<String>>();
        List<BookCat> catList = new ArrayList<BookCat>();
        int sortNum = 0;
        int num = 0;
        for (Element element : elements) {
            String key = element.select("h4 a").get(0).text();
            BookCat parent = new BookCat(sortNum++ + "", key, "root", num += 10);
            catList.add(parent);
//            List<String> list = new ArrayList<String>();
            int n = 0;
            for(Element e : element.select("div.list a")) {
//                list.add(e.text());
                catList.add(new BookCat(sortNum++ + "", e.text(), parent.id, n += 10));
            }
//            map.put(key, list);
        }
        /*for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
            System.out.println("------------------------------------------------");
        }*/
        for (BookCat cat : catList) {
            String sql = String.format("INSERT INTO ss_dz_sale_class ( ss_dz_sale_class.sale_class_id,ss_dz_sale_class.sale_class_name,ss_dz_sale_class.sale_class_super,ss_dz_sale_class.sort_num,ss_dz_sale_class.is_valid,ss_dz_sale_class.input_date,ss_dz_sale_class.o_id_input,ss_dz_sale_class.memo) " +
                    "values ('%s','%s','%s',%d,'T',convert(datetime,'2013-09-11 16:11:17'),'28CAFCFFEEF54F54A4F8B5598571F5F5',null);", cat.id, cat.name, cat.pid, cat.sortNum);
            System.out.println(sql);
        }
    }

    class BookCat {
        String id;
        String pid;
        String name;
        int sortNum;

        BookCat(String id, String name, String pid, int sortNum) {
            this.id = id;
            this.name = name;
            this.pid = pid;
            this.sortNum = sortNum;
        }

        @Override
        public String toString() {
            return "BookCat{" +
                    "id='" + id + '\'' +
                    ", pid='" + pid + '\'' +
                    ", name='" + name + '\'' +
                    ", sortNum=" + sortNum +
                    '}';
        }
    }
}

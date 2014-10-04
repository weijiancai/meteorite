package com.meteorite.core.parser.book;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class BookuuParserTest {

    @Test
    public void testParse() throws Exception {
        String isbn = "9787010129860";
        BookuuParser parser = new BookuuParser(isbn);
        List<IWebProduct> list = parser.parse();
        System.out.println(list);
        assertThat(list.size(), equalTo(1));
        IWebProduct product = list.get(0);
        assertThat(product.getName(), equalTo("开窍的日子(舒乙散文和绘画作品自选集)"));
        assertThat(product.getPrice(), equalTo("46"));
        assertThat(product.getAuthor(), equalTo("舒乙"));
        assertThat(product.getPublishing(), equalTo("人民"));
        assertThat(product.getKaiben(), equalTo("16"));
        assertThat(product.getPageNum(), equalTo("256"));
        assertThat(product.getPublishDate(), equalTo("2014-04-01"));
        assertThat(product.getBanci(), equalTo("1"));
        assertThat(product.getPrintDate(), equalTo("2014-04-01"));
        assertThat(product.getPrintNum(), equalTo("1"));
    }

    @Test
    public void test9787550232204() throws Exception {
        String isbn = "9787550232204";
        BookuuParser parser = new BookuuParser(isbn);
        List<IWebProduct> list = parser.parse();
        System.out.println(list);
        assertThat(list.size(), equalTo(2));
        IWebProduct product = list.get(0);
        assertThat(product.getName(), equalTo("告白与告别"));
        assertThat(product.getPrice(), equalTo("32.8"));
        assertThat(product.getAuthor(), equalTo("韩寒"));
        assertThat(product.getPublishing(), equalTo("北京联合"));
        assertThat(product.getKaiben(), equalTo("32"));
        assertThat(product.getPageNum(), equalTo("200"));
        assertThat(product.getPublishDate(), equalTo("2014-08-01"));
        assertThat(product.getBanci(), equalTo("1"));
        assertThat(product.getPrintDate(), equalTo("2014-08-01"));
        assertThat(product.getPrintNum(), equalTo("1"));
    }
}
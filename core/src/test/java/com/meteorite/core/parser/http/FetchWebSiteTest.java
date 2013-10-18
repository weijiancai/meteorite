package com.meteorite.core.parser.http;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class FetchWebSiteTest {
    @Test
    public void testFetchWebSite() throws IOException {
        String baseUrl = "http://www.0731hunjia.com/";
        File dir = new File("D:\\fetch\\0731hunjia");
        FetchWebSite fetchWebSite = new FetchWebSite(dir);
        fetchWebSite.addExcludeUrl("http://www.0731hunjia.com/bbs");
        fetchWebSite.fetch(baseUrl, 0);
    }
}
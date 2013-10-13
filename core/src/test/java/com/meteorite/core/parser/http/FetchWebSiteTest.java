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
        new FetchWebSite(baseUrl, dir);
    }
}

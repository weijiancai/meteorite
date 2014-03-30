package com.meteorite.core.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class UFileTest {
    @Test
    public void testGetFileExt() {
        String expect = "file.txt";
        String actual = UFile.getFileExt(expect);
        assertThat(actual, equalTo("txt"));
    }
}

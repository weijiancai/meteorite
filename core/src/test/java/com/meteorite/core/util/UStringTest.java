package com.meteorite.core.util;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class UStringTest {
    @Test
    public void testTableNameToClassName() throws Exception {

    }

    @Test public void testLeftPadding() {
        String expect = "0000001";
        String actual = UString.leftPadding("1", '0', 7);
        assertThat(actual, equalTo(expect));

        expect = "1234567";
        actual = UString.leftPadding("1234567", '0', 7);
        assertThat(actual, equalTo(expect));
    }
}

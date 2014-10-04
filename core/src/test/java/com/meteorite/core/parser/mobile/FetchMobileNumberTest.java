package com.meteorite.core.parser.mobile;

import org.junit.Test;

public class FetchMobileNumberTest {

    @Test
    public void testFetch() throws Exception {
        FetchMobileNumber fetchMobileNumber = new FetchMobileNumber(null);
        fetchMobileNumber.fetch();
    }
}
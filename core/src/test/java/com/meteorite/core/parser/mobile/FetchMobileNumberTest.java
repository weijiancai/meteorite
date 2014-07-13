package com.meteorite.core.parser.mobile;

import org.junit.Test;

import static org.junit.Assert.*;

public class FetchMobileNumberTest {

    @Test
    public void testFetch() throws Exception {
        FetchMobileNumber fetchMobileNumber = new FetchMobileNumber();
        fetchMobileNumber.fetch();
    }
}
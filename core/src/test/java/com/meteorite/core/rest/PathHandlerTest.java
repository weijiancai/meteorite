package com.meteorite.core.rest;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class PathHandlerTest {

    @Test
    public void testParseForDb() throws Exception {
        String path = "/table/sys_meta_field/column/default_value";
        PathHandler handler = new PathHandler(path);
        Map<String, String> map = handler.parseForDb();
        System.out.println(map);
    }
}
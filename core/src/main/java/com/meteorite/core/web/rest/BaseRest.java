package com.meteorite.core.web.rest;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class BaseRest extends HttpServlet {
    protected void writeJsonObject(HttpServletResponse res, Object obj) throws IOException {
        res.setContentType("application/json;charset=UTF-8");
        res.getWriter().write(JSON.toJSONString(obj));
    }
}

package com.meteorite.core.web.rest;

import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Base Rest服务
 *
 * @author wei_jc
 * @since 1.0.0
 */
public abstract class BaseRest extends HttpServlet {
    public enum BrowserType {
        MSIE, FIREFOX, CHROME
    }

    protected BrowserType browserType;

    protected void writeJsonObject(HttpServletResponse res, Object obj) throws IOException {
        res.setContentType("application/json;charset=UTF-8");
        res.getWriter().write(JSON.toJSONString(obj));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String userAgent = req.getHeader("User-Agent");
        if (userAgent.contains("MSIE")) {
            browserType = BrowserType.MSIE;
        } else if (userAgent.contains("Firefox")) {
            browserType = BrowserType.FIREFOX;
        } else if (userAgent.contains("Chrome")) {
            browserType = BrowserType.CHROME;
        }
        doPost(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        handle(req, res);

    }

    protected abstract void handle(HttpServletRequest req, HttpServletResponse res);
}

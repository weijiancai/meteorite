package com.meteorite.core.web.action;

import com.alibaba.fastjson.JSON;
import com.meteorite.core.util.UFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Base Action
 *
 * @author wei_jc
 * @since 1.0.0
 */
public abstract class BaseAction extends HttpServlet {
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

    protected Map<String, Object> getData(HttpServletRequest req) throws IOException {
        String jsonStr = UFile.readString(req.getInputStream(), "UTF-8");
        return JSON.parseObject(jsonStr);
    }
}

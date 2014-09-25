package com.meteorite.core.web.filter;

import com.meteorite.core.util.UHttp;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author weijiancai
 */
public class SystemFilter implements javax.servlet.Filter {
    private static Logger log = Logger.getLogger(SystemFilter.class);

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpReq = ((HttpServletRequest)req);
        String ipAddr = UHttp.getIpAddr(httpReq);
        log.debug("Request URL: " + ipAddr + " == " + httpReq.getRequestURL());
        req.setCharacterEncoding("UTF-8");
//        resp.setCharacterEncoding("UTF-8");
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws javax.servlet.ServletException {

    }
}

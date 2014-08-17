package com.meteorite.core.web.filter;

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
        log.debug("Request URL: " + ((HttpServletRequest)req).getRequestURL());
        req.setCharacterEncoding("UTF-8");
//        resp.setCharacterEncoding("UTF-8");
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws javax.servlet.ServletException {

    }
}

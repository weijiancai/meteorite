package com.meteorite.core.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author weijiancai
 */
public class SystemFilter implements javax.servlet.Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("Request URL: " + ((HttpServletRequest)req).getRequestURL());
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws javax.servlet.ServletException {

    }
}

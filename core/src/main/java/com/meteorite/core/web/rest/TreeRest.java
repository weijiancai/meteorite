package com.meteorite.core.web.rest;

import com.meteorite.core.datasource.ftp.FtpDataSource;
import com.meteorite.core.util.UString;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Tree Rest
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class TreeRest extends BaseRest {
    private static final Logger log = Logger.getLogger(TreeRest.class);

    @Override
    protected void handle(HttpServletRequest req, HttpServletResponse res) {
        try {
            FtpDataSource ds = new FtpDataSource("115.29.163.55", "wei_jc", "wjcectong2013#");
            ds.load();

            if (req.getRequestURI().endsWith("/tree")) {
                String path = req.getParameter("path");
                if (UString.isEmpty(path)) {
                    path = "/";
                }
                writeJsonObject(res, ds.getNavTree(path));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}

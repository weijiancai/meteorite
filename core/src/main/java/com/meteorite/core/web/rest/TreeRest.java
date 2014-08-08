package com.meteorite.core.web.rest;

import com.meteorite.core.datasource.ftp.FtpDataSource;
import com.meteorite.core.util.UString;
import org.apache.log4j.Logger;
import org.hsqldb.lib.StringInputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;

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
            FtpDataSource ds = FtpDataSource.getInstance("115.29.163.55", "wei_jc", "wjcectong2013#");
            ds.load();

            if (req.getRequestURI().endsWith("/tree")) {
                String path = req.getParameter("path");
                if (UString.isEmpty(path)) {
                    path = "/";
                }
                String down = req.getParameter("down");
                String store = req.getParameter("store");
                String delete = req.getParameter("delete");
                String refresh = req.getParameter("refresh");
                if (UString.isNotEmpty(down)) {
                    res.setContentType("application/octet-stream");
                    res.setHeader("Content-disposition", "attachment;filename=" + UString.getLastName(down, "/"));
                    ServletOutputStream os = res.getOutputStream();
                    ds.write(down, os);
                    os.flush();
                    os.close();
                } else if (UString.isNotEmpty(store)) {
                    String text = req.getParameter("text");
                    ds.store(store, new ByteArrayInputStream(text.getBytes("UTF-8")));
                } else if (UString.isNotEmpty(delete)) {
                    ds.delete(delete);
                } else if (UString.isNotEmpty(refresh)) {
                    writeJsonObject(res, ds.getChildren(path));
                } else {
                    writeJsonObject(res, ds.getChildren(path));
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}

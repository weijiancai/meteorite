package com.meteorite.core.web.rest;

import com.meteorite.core.dict.DictManager;
import com.meteorite.core.util.UString;
import com.meteorite.core.web.action.BaseAction;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class DictRest extends BaseAction {
    private static final Logger log = Logger.getLogger(DictRest.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (UString.isNotEmpty(id)) {
            writeJsonObject(res, DictManager.getDict(id));
            return;
        }
        if (req.getRequestURI().endsWith("/dict")) {
            writeJsonObject(res, DictManager.getDictList());
        } else {
            String dictId = req.getRequestURI().substring(6);
            writeJsonObject(res, DictManager.getDict(dictId));
        }
    }
}

package com.meteorite.core.web.rest;

import com.meteorite.core.dict.DictManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class DictRest extends BaseRest {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        if (req.getRequestURI().endsWith("/dict")) {
            writeJsonObject(res, DictManager.getDictList());
        } else {
            String dictName = req.getRequestURI().substring(6);
            writeJsonObject(res, DictManager.getDict(dictName));
        }
    }
}

package com.meteorite.core.web.rest;

import com.alibaba.fastjson.JSON;
import com.meteorite.core.meta.MetaManager;
import com.meteorite.core.meta.model.Meta;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class MetaRest extends BaseRest {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//        this.doPost(req, res);
        if (req.getRequestURI().endsWith("/meta")) {
            writeJsonObject(res, MetaManager.getMetaList());
        } else {
            String metaName = req.getRequestURI().substring(6);
            writeJsonObject(res, MetaManager.getMeta(metaName));
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        res.setCharacterEncoding("UTF-8");


        System.out.println("PUT : " + req.getParameter("name"));

        BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line = null;
        System.out.println("now is the inputstream content : ");
        while((line = reader.readLine()) != null){
            System.out.println(new String(line.getBytes(), "UTF-8"));
            sb.append(line);
        }
        System.out.println("input stream is end ...");
        reader.close();
//        Meta meta = JSON.parseObject(sb.toString(), Meta.class);
//        String metaName = req.getParameter("name");
//        writeJsonObject(res, MetaManager.getMeta(metaName));
        res.getWriter().write(new String(sb.toString().getBytes(), "UTF-8"));
    }

    @Override
    protected void handle(HttpServletRequest req, HttpServletResponse res) {

    }
}

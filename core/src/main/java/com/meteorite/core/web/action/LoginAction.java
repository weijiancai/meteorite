package com.meteorite.core.web.action;

import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.datasource.QueryBuilder;
import com.meteorite.core.datasource.db.QueryResult;
import com.meteorite.core.meta.MetaManager;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.datasource.request.BaseResponse;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆Action
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class LoginAction extends BaseAction {
    private static final Logger log = Logger.getLogger(LoginAction.class);

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String userName = req.getParameter("name");
        String pwd = req.getParameter("pwd");

        BaseResponse response = new BaseResponse();
        Meta meta = MetaManager.getMeta("User");
        QueryBuilder builder = QueryBuilder.create(meta);
        try {
            builder.add("name", userName);
            builder.add("pwd", pwd);
            QueryResult<DataMap> result = meta.query(builder);
            if (result.getTotal() == 1) {
                response.setSuccess(true);
            } else {
                response.setSuccess(false);
                response.setErrorMsg("用户名或密码不正确！");
            }
        } catch (Exception e) {
            response.setSuccess(false);
            response.setErrorMsg(e.getMessage());
            log.error(e.getMessage(), e);
        }

        this.writeJsonObject(res, response);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req,res);
    }
}

/**
 * 
 */
package com.meteorite.core.parser.capub;

import com.meteorite.core.util.UNumber;
import com.meteorite.core.util.UString;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class CapubAction extends HttpServlet {

	/* (non-Javadoc)
	 * @see cc.csdn.base.flex.web.FlexAction#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String method = req.getParameter("method");
		
		if("getCaptchaImg".equals(method)) {
			CapubParser.getCaptchaImg();

		} else if("start".equals(method)) {
			String startId = req.getParameter("startId");
			String endId = req.getParameter("endId");
			String base = req.getParameter("base");
			String approveNo = req.getParameter("approveNo");
			if(UString.isEmpty(approveNo)) {
				CapubBO.initCipData(UNumber.toInt(endId), UNumber.toInt(startId), UNumber.toInt(base));
			} else {
				String endNo = CapubBO.initCipData(approveNo);
                System.out.println("下载CIP数据结束，结束核字号是：" + endNo);
//				this.outputData(res, endNo);
			}
			
		} else if("stop".equals(method)) {
			CapubBO.stop();
		} else if("login".equals(method)) {
			String userid = req.getParameter("userid");
			String password = req.getParameter("password");
			String captcha = req.getParameter("captcha");
			
			CapubParser.login(userid, password, captcha);
//			this.outputMessage(res, "登录成功！");
		}
	}

}

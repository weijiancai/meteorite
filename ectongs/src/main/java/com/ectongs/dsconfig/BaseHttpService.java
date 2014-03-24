/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ectongs.dsconfig;

import cc.csdn.base.app.exception.AppErrorException;
import cc.csdn.base.app.receipt.ReceiptUtil;
import cc.csdn.base.util.Debug;
import cc.csdn.base.util.UtilBase64;
import cc.csdn.base.util.UtilString;
import cc.csdn.base.web.right.AuthorityChecker;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.util.*;

/**
 *
 * @author wei_jc
 */
public class BaseHttpService {
    private static final Logger log = Logger.getLogger(BaseHttpService.class);
    // 入口url
    public static String ENTRY_URL = "";
    // 应用url
    public static String APP_URL = "http://115.29.163.55:11031/ectmss-server";
    private HttpClient client = new DefaultHttpClient();

    protected String serviceName;
    protected Map<String, String> parameters = new HashMap<String,String>();
    

    public BaseHttpService(String serviceName) {
        this.serviceName = serviceName;
        // 超时
        this.client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);
    }
    
    public void addParameter(String key,String value){
        this.parameters.put(key, value);
    }

    public String send() {
        return send(null);
    }

    public String send(String jsessionid) {
        String baseUrl = APP_URL;
//        String jsessionid = "";

        String url = baseUrl + ("/" + this.serviceName) + ".do;jsessionid=" + jsessionid;
        System.out.println("Request URL: " + url);

        HttpPost postMethod = new HttpPost(url);
        String reqid = ReceiptUtil.getReceiptGUID();
        postMethod.addHeader(AuthorityChecker.REQID, reqid);
        postMethod.addHeader(AuthorityChecker.VERID, Double.toString(AuthorityChecker.reqidToVerifyId(reqid)));
        Debug debug =new Debug();
        debug.setStartTime(new Date());

        try {
            if(parameters != null) {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                nvps.add(new BasicNameValuePair("flex_http_req_id", "true"));

                for (Map.Entry<String, String> entry : parameters.entrySet()) {
                    String value;
                    if (UtilString.isEmpty(entry.getValue())) {
                        value = "";
                    } else {
                        value = UtilBase64.zlibEncode(entry.getValue());
                    }
                    nvps.add(new BasicNameValuePair(entry.getKey(), value));
                }

                postMethod.setEntity(new UrlEncodedFormEntity(nvps));
            }

            HttpResponse response = client.execute(postMethod);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new Exception("http请求失败！" +response.getStatusLine().getStatusCode());
            }

            HttpEntity resEntity = response.getEntity();
            byte[] bytes = EntityUtils.toByteArray(resEntity);
            if (bytes.length == 0) {
                return "";
            }

            String result = UtilBase64.zlibDecode(bytes);
            int dataStart = result.indexOf("<data><![CDATA[") + 15;
            int dataEnd = result.indexOf("]]></data>");
            System.out.println(result);
            String data = result.substring(dataStart, dataEnd);
            System.out.println(data);

            return data;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            postMethod.releaseConnection();
            client.getConnectionManager().shutdown();
            debug.printUseTime("httpService[" + this.serviceName + "]use time:");
        }

        return null;
    }
    


    /**
     * http初始化. 初始化之后HttpService实例方可执行.
     */
    public static boolean initHttp() throws Exception {
        HttpPost postMethod = null;
        try {
            log.info("入口URL：" + ENTRY_URL);
            System.out.println("入口URL：" + ENTRY_URL);

            postMethod = new HttpPost(ENTRY_URL);
            String reqid = ReceiptUtil.getReceiptGUID();
            postMethod.addHeader(AuthorityChecker.REQID, reqid);
            postMethod.addHeader(AuthorityChecker.VERID, Double.toString(AuthorityChecker.reqidToVerifyId(reqid)));

            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("flex_http_req_id", "true"));
            postMethod.setEntity(new UrlEncodedFormEntity(nvps));
            
            HttpClient httpclient = new DefaultHttpClient();
            // 超时
            httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);            
            HttpResponse response = httpclient.execute(postMethod);
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new AppErrorException("http请求失败！ " + ENTRY_URL + "\n" + response.getStatusLine().toString());
            }

            HttpEntity entity = response.getEntity();

            APP_URL = UtilBase64.zlibDecode(EntityUtils.toString(entity));
            log.info("应用URL：" + APP_URL);
            System.out.println("应用URL：" + APP_URL);
            if (UtilString.isEmpty(APP_URL)) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != postMethod) {
                postMethod.releaseConnection();
            }
        }

        return true;
    }

    public static void login(String userName, String passwd) {
        BaseHttpService service = new BaseHttpService("flex/LoginAction");
        service.addParameter("userName", userName);
        service.addParameter("passwd", passwd);
        String data = service.send();
        System.out.println(data);
    }
}

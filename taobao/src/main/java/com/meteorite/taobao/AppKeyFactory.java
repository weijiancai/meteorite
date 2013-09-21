package com.meteorite.taobao;

import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;

/**
 * @author weijiancai
 * @version 0.0.1
 */
public class AppKeyFactory {
    /**
     * 取wlhb的正式环境应用
     *
     * @return
     */
    public static AppKey getWlhb() {
        AppKey key = new AppKey();
        key.url = "http://gw.api.taobao.com/router/rest";
        key.appkey = "21474926";
        key.secret = "4a1cfaaf68e5de59558533504b7a0e95";
        key.sessionKey = "61001120c6d9adcf5293153622473205aae085aeaea71f4714117733";
        return key;
    }
    
    public static AppKey getQy() {
    	AppKey key = new AppKey();
    	key.url = "http://gw.api.taobao.com/router/rest";
    	key.appkey = "21155284";
    	key.secret = "0207b12e9eb9d5c46d034159198f12fb";
    	key.sessionKey = "61007237e074911117dbfa82437ae2010e515ff66b5e978651953975";
    	
    	return key;
    }
    
    // http://container.api.taobao.com/container?appkey=21499544
    public static AppKey getZt() {
    	AppKey key = new AppKey();
    	key.url = "http://gw.api.taobao.com/router/rest";
    	key.appkey = "21499544";
    	key.secret = "51fd2ae7d1ecfc8f4775fba746b866b2";
    	key.sessionKey = "6100124cff500b43b84358d53179eaac1515417a4295024737854810";
    	
    	return key;
    }
    
    // http://container.api.taobao.com/container?appkey=21493839
    public static AppKey getZt1() {
    	AppKey key = new AppKey();
    	key.url = "http://gw.api.taobao.com/router/rest";
    	key.appkey = "21493839";
    	key.secret = "093bd24950fe10ce43c069c2ea7304a5";
    	key.sessionKey = "6100a296cc5c0e8f21205350b10eeaa4beac119399ed672737854810";
    	
    	return key;
    }
    
    // http://container.api.tbsandbox.com/container?appkey=1021499544
    // sandbox_b_01  sandbox_seller_0 taobao1234
    public static AppKey getZtSandbox() {
    	AppKey key = new AppKey();
    	key.url = "http://gw.api.tbsandbox.com/router/rest";
    	key.appkey = "1021499544";
    	key.secret = "sandbox7d1ecfc8f4775fba746b866b2";
    	key.sessionKey = "6100529fc43e2293ecb146022acddcee873291424a0ab242054718218";
    	
    	return key;
    }

    public static class AppKey {
        public String url;
        public String appkey;
        public String secret;
        public String sessionKey;

        public TaobaoClient getClient(String format) {
            return new DefaultTaobaoClient(url, appkey, secret, format);
        }
        
        public TaobaoClient getClient() {
            return getClient("xml");
        }
    }
}

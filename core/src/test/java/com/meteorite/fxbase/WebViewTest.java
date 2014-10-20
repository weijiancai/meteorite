package com.meteorite.fxbase;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class WebViewTest extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        WebView webView = new WebView();
        webView.getEngine().setUserDataDirectory(new File("D:/temp"));
        URI uri = URI.create("http://www.taobao.com");
        Map<String, List<String>> headers = new LinkedHashMap<String, List<String>>();
        headers.put("Set-Cookie", Arrays.asList("name=value"));
        java.net.CookieHandler.getDefault().put(uri, headers);
        webView.getEngine().load("https://login.taobao.com");
        System.out.println(headers);

        primaryStage.setScene(new Scene(webView));
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}

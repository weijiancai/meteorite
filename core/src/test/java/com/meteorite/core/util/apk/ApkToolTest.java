package com.meteorite.core.util.apk;

import java.io.File;

public class ApkToolTest {

    @org.junit.Test
    public void testParse() throws Exception {
        File apkFile = new File("D:\\JavaSoft\\apktool-install-windows-r05-ibot\\veryzhun.apk");
        ApkTool.parse(apkFile);
    }
}
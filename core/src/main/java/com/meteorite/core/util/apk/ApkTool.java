package com.meteorite.core.util.apk;

import com.meteorite.core.util.UFile;

import java.io.File;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class ApkTool {
    public static void parse(File apkFile) {
        File outDir = new File(apkFile.getParentFile(), UFile.getFileNameNoExt(apkFile));

    }
}

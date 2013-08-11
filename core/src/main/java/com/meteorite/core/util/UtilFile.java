package com.meteorite.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author wei_jc
 * @version 1.0
 */
public class UtilFile {
    public static String readString(File file) throws IOException {
        StringBuilder result = new StringBuilder();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            result.append(line);
        }
        br.close();
        return  result.toString();
    }
}

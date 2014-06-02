package com.meteorite.core.datasource.classpath;

import org.junit.Test;

import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static org.junit.Assert.*;

public class ClassPathLoaderTest {

    @Test
    public void testLoad() throws Exception {

    }

    @Test
    public void testJarLoad() throws IOException {
        JarFile jarFile = new JarFile("D:\\workspace\\meteorite\\out\\artifacts\\core_jar\\core.jar");
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            System.out.println(entry);
        }
        jarFile.close();
    }
}
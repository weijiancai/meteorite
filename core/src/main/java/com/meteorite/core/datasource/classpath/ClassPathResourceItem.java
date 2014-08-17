package com.meteorite.core.datasource.classpath;

import com.meteorite.core.datasource.ResourceItem;
import com.meteorite.core.util.UFile;
import com.meteorite.core.util.UIO;

import java.io.File;
import java.io.InputStream;

/**
 * 类路径资源
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class ClassPathResourceItem extends ResourceItem {
    private String type;
    private String baseDir;

    public ClassPathResourceItem(String type, String baseDir) {
        this.type = type;
        this.baseDir = baseDir;
    }

    @Override
    public String getContent() throws Exception {
        if ("jar".equals(type)) {
            InputStream is = UIO.getInputStream("/" + getId(), UIO.FROM.CP);
            return UFile.readString(is, "UTF-8");
        } else if ("file".equals(type)) {
            File file = new File(baseDir, getId());
            return UFile.readString(file);
        }

        return null;
    }

    @Override
    public InputStream getInputStream() throws Exception {
        return UIO.getInputStream("/" + getId(), UIO.FROM.CP);
    }
}

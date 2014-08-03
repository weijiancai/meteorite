package com.meteorite.core.datasource.ftp;

import com.meteorite.core.datasource.ResourceItem;

import java.util.Date;

/**
 * Ftp资源项
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class FtpResourceItem extends ResourceItem {
    private Date lastModified;

    @Override
    public String getContent() throws Exception {
        return null;
    }
}

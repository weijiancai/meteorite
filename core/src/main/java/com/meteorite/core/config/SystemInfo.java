package com.meteorite.core.config;

import com.meteorite.core.util.jaxb.AbstractXmlSerialization;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;

import static com.meteorite.core.config.SystemConfig.FILE_NAME_SYSTEM_INFO;

/**
 * 系统基本信息
 *
 * @author wei_jc
 * @since 1.0.0
 */
@XmlRootElement
public class SystemInfo extends AbstractXmlSerialization {
    /** 数据字典是否已初始化*/
    private boolean isDictInit;
    /** 元数据是否已初始化 */
    private boolean isMetaInit;
    /** 布局是否已初始化 */
    private boolean isLayoutInit;
    /** 视图是否已初始化 */
    private boolean isViewInit = true;

    public boolean isDictInit() {
        return isDictInit;
    }

    public void setDictInit(boolean isDictInit) {
        this.isDictInit = isDictInit;
    }

    public boolean isLayoutInit() {
        return isLayoutInit;
    }

    public void setLayoutInit(boolean isLayoutInit) {
        this.isLayoutInit = isLayoutInit;
    }

    public boolean isMetaInit() {
        return isMetaInit;
    }

    public void setMetaInit(boolean isMetaInit) {
        this.isMetaInit = isMetaInit;
    }

    public boolean isViewInit() {
        return isViewInit;
    }

    public void setViewInit(boolean isViewLoad) {
        this.isViewInit = isViewLoad;
    }

    @Override
    protected File getXmlFile() {
        return new File(SystemConfig.DIR_SYSTEM, FILE_NAME_SYSTEM_INFO);
    }

    public void reset() {
        isDictInit = false;
        isMetaInit = false;
        isLayoutInit = false;
        isViewInit = false;
    }
}

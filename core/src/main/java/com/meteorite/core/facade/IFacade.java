package com.meteorite.core.facade;

import com.meteorite.core.config.ProjectConfig;

/**
 *
 * @author wei_jc
 * @version 1.0.0
 */
public interface IFacade {
    /**
     * 获得项目配置信息
     *
     * @return 返回项目配置信息
     * @version 1.0.0
     */
    ProjectConfig getProjectConfig();

    /**
     * 初始化完成
     */
    void initAfter() throws Exception;
}

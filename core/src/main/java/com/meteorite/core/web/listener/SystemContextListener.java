package com.meteorite.core.web.listener;

import com.meteorite.core.config.SystemManager;
import com.meteorite.core.meta.MetaManager;
import com.meteorite.core.util.HSqlDBServer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 系统ServletContext监听器
 *
 * @author weijiancai
 * @since 1.0.0
 */
public class SystemContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try { // 初始化配置信息
            SystemManager.getInstance().init();
            //  启动数据库
//            HSqlDBServer.getInstance().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*UFile.WEB_INF = new File(servletContextEvent.getServletContext().getRealPath("/WEB-INF"));*/
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        HSqlDBServer.getInstance().stop();
    }
}

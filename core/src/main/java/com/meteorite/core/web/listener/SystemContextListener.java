package com.meteorite.core.web.listener;

import com.meteorite.core.config.PathManager;
import com.meteorite.core.config.SystemConfig;
import com.meteorite.core.config.SystemManager;
import com.meteorite.core.config.SystemType;
import com.meteorite.core.util.HSqlDBServer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;

/**
 * 系统ServletContext监听器
 *
 * @author weijiancai
 * @since 1.0.0
 */
public class SystemContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        // 设置日志目录属性
        System.setProperty("logs_dir", event.getServletContext().getRealPath("/log"));
        // 设置web项目根目录
        PathManager.WEB_ROOT_DIR = new File(event.getServletContext().getRealPath("/"));
        // 初始化系统类型
        SystemConfig.SYSTEM_TYPE = SystemType.WEB;
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

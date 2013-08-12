package com.meteorite.core;

/**
 * 日志记录
 *
 * @author wei_jc
 * @version 0.0.1
 */
public interface ILogger {
    /**
     * 开始写日志
     */
    void logBegin();

    /**
     * 结束写日志
     */
    void logEnd();

    /**
     * 记录日志内容
     */
    void log();

    /**
     * 记录错误日志
     */
    void logError();
}

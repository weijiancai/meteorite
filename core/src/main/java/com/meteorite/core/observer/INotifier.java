package com.meteorite.core.observer;

/**
 * 通知者接口
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface INotifier {
    /**
     * 发送一个通知
     *
     * @param notificationName 通知名称
     * @param body 通知内容
     * @param type 通知类型
     */
    void sendNotification(String notificationName, Object body, String type);

    /**
     * 发送一个通知
     *
     * @param notificationName 通知名称
     * @param body 通知内容
     */
    void sendNotification(String notificationName, Object body);

    /**
     * 发送一个通知
     *
     * @param notificationName 通知名称
     */
    void sendNotification(String notificationName);
}

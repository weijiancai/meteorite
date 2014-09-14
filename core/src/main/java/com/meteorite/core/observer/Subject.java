package com.meteorite.core.observer;

/**
 * 主题接口
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface Subject {
    void registerObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObserver();
}

package com.meteorite.core.observer;

/**
 * 主题接口
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface Subject<D extends EventData> {
    void registerObserver(Observer<D> observer);

    void removeObserver(Observer<D> observer);

    void notifyObserver(D data);
}

package com.meteorite.core.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * 主题默认实现
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class BaseSubject<D extends EventData> implements Subject<D> {
    private List<Observer<D>> list = new ArrayList<Observer<D>>();


    @Override
    public void registerObserver(Observer<D> observer) {
        list.add(observer);
    }

    @Override
    public void removeObserver(Observer<D> observer) {
        list.remove(observer);
    }

    @Override
    public void notifyObserver(D data) {
        for (Observer<D> observer : list) {
            observer.update(data);
        }
    }
}

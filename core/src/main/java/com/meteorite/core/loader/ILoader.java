package com.meteorite.core.loader;

import com.meteorite.core.observer.Subject;

/**
 * 加载器
 *
 * @author wei_jc
 * @version 0.0.1
 */
public interface ILoader extends Subject {
    void load() throws Exception;
}

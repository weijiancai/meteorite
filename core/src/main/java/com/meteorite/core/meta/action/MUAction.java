package com.meteorite.core.meta.action;

import com.meteorite.core.meta.model.Meta;

/**
 * Meta UI Action
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUAction {
    private Meta meta;
    private String name;
    private String displayName;
    private Class<?> actionClass;
    private String methodName;

    public MUAction() {
    }

    public MUAction(Meta meta, String name, String displayName, Class<?> actionClass, String methodName) {
        this.meta = meta;
        this.name = name;
        this.displayName = displayName;
        this.actionClass = actionClass;
        this.methodName = methodName;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Class<?> getActionClass() {
        return actionClass;
    }

    public void setActionClass(Class<?> actionClass) {
        this.actionClass = actionClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}

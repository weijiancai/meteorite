package com.meteorite.core.rest;

import java.util.Map;

/**
 * Rest Request
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class Request {
    private String path;
    private Map<String, String> params;
    private PathHandler pathHandler;

    public Request(String path) {
        this.path = path;
        pathHandler = new PathHandler(path);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public PathHandler getPathHandler() {
        return pathHandler;
    }
}

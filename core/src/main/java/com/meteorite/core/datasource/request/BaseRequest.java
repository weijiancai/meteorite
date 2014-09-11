package com.meteorite.core.datasource.request;

import com.meteorite.core.datasource.QueryCondition;
import com.meteorite.core.rest.PathHandler;

import java.util.List;
import java.util.Map;

/**
 * Rest Request
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class BaseRequest implements IRequest{
    private String path;
    private Map<String, String> params;
    private PathHandler pathHandler;
    private List<QueryCondition> conditions;

    private IResponse response = new BaseResponse();

    public BaseRequest() {
    }

    public BaseRequest(String path) {
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

    public List<QueryCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<QueryCondition> conditions) {
        this.conditions = conditions;
    }

    public IResponse getResponse() {
        return response;
    }

    public void setResponse(IResponse response) {
        this.response = response;
    }
}

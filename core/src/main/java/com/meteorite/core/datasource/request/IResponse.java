package com.meteorite.core.datasource.request;

import com.meteorite.core.datasource.DataMap;

import java.util.List;

/**
 * 资源响应接口
 *
 * @author wei_jc
 * @since 1.0.0
 */
public interface IResponse {
    List<DataMap> getListData();

    boolean isSuccess();

    String getErrorMsg();
}

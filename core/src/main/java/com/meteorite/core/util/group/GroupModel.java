package com.meteorite.core.util.group;

import java.util.HashMap;
import java.util.Map;

/**
 * 分组模型
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class GroupModel {
    private String[] groupCols;
    private Map<String, GroupFunction> computeColMap = new HashMap<String, GroupFunction>();

    public void addComputeCol(String colName, GroupFunction function) {
        computeColMap.put(colName, function);
    }

    public String[] getGroupCols() {
        return groupCols;
    }

    public Map<String, GroupFunction> getComputeColMap() {
        return computeColMap;
    }

    public void setGroupCols(String[] groupCols) {
        this.groupCols = groupCols;
    }

    public void setComputeColMap(Map<String, GroupFunction> computeColMap) {
        this.computeColMap = computeColMap;
    }
}

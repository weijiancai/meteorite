package com.meteorite.core.datasource;

import java.util.ArrayList;
import java.util.List;

/**
 * Map Data元数据信息
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class DataMapMetaData {
    private int columnCount;
    private List<Integer> displaySizeList = new ArrayList<Integer>();
    private List<String> labelList = new ArrayList<String>();
    private List<String> nameList = new ArrayList<String>();
    private List<Integer> typeList = new ArrayList<Integer>();
    private List<String> typeNameList = new ArrayList<String>();

    public void add(String name, String label, int displaySize, int type, String typeName) {
        nameList.add(name);
        labelList.add(label);
        displaySizeList.add(displaySize);
        typeList.add(type);
        typeNameList.add(typeName);
    }

    public int getColumnDisplaySize(int column) {
        return displaySizeList.get(column);
    }

    public String getColumnLabel(int column) {
        return labelList.get(column);
    }

    public String getColumnName(int column) {
        return nameList.get(column);
    }

    public int getColumnType(int column) {
        return typeList.get(column);
    }

    public String getColumnTypeName(int column) {
        return typeNameList.get(column);
    }

    public int getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(int columnCount) {
        this.columnCount = columnCount;
    }
}

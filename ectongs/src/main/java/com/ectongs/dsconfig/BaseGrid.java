package com.ectongs.dsconfig;

import cc.csdn.base.basedata.dsconfig.info.DsColumnsInfo;
import cc.csdn.base.basedata.dsconfig.info.DsEditColumnsInfo;
import cc.csdn.base.basedata.dsconfig.list.DsColumnFormatList;
import cc.csdn.base.db.dataobj.dsconfig.DataStoreConfig;

import java.util.*;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class BaseGrid {

    private Map<String, Object> nameCtlMap = new HashMap<String, Object>();
    private Map<String, Object> colNameCtlMap = new HashMap<String, Object>();
    private DataStoreConfig dsConfig;
    private boolean isReadOnly;

    public BaseGrid(DataStoreConfig dsConfig) {
        this.dsConfig = dsConfig;
    }

    public String genForm(int columnCount, boolean isTable) {
        StringBuilder sb = new StringBuilder();

        int rowIdx = 0;
        int colIdx = 0;

        String wrapperTag = "table";
        String rowTag = "tr";
        String colTag = "td";
        if (!isTable) {
            wrapperTag = "ul";
            rowTag = "li";
            colTag = "span";
        }

        List<DsEditColumnsInfo> cols = dsConfig.getEditColumns().getColumnList();
        Collections.sort(cols, new Comparator<DsEditColumnsInfo>() {
            @Override
            public int compare(DsEditColumnsInfo o1, DsEditColumnsInfo o2) {
                int n1 = o1.getOrdernum() == null ? 0 : o1.getOrdernum();
                int n2 = o2.getOrdernum() == null ? 0 : o2.getOrdernum();
                return n1 - n2;
            }
        });

        sb.append(String.format("<%s class=\"ds_form\"><%s>", wrapperTag, rowTag));
        for (DsEditColumnsInfo column : dsConfig.getEditColumns().getColumnList()) {
            if (!column.isShow()) {
                continue;
            }
            DsColumnsInfo columnInfo = dsConfig.getColumns().getColumn(column.getName());
            String cName = columnInfo.getCname();

            if (column.isSingleRow() != null && column.isSingleRow()) {
                rowIdx++;
                sb.append(String.format("</%s><%1$s>", rowTag));
                sb.append(String.format("<%s><label>%s</label></%1$s>", colTag, cName));
                sb.append(String.format("<%s colspan=\"%d\">", colTag, columnCount * 2 - 1)).append(getInputControl(columnInfo, column, true)).append(String.format("</%s>", colTag));
                sb.append(String.format("</%s><%1$s>", rowTag));
                colIdx = 0;
                rowIdx++;
                continue;
            }

            sb.append(String.format("<%s><label>%s</label></%1$s>", colTag, cName));
            sb.append(String.format("<%s>", colTag)).append(getInputControl(columnInfo, column, false)).append(String.format("</%s>", colTag));

            if (columnCount == 1) {
                colIdx = 0;
                rowIdx++;
                sb.append(String.format("</%s><%1$s>", rowTag));
            } else {
                if (colIdx == columnCount - 1) {
                    colIdx = 0;
                    rowIdx++;
                    sb.append(String.format("</%s><%1$s>", rowTag));
                } else {
                    colIdx++;
                }
            }
        }

        sb.append(String.format("</%s></%s>", rowTag, wrapperTag));
        return sb.toString().replace("<tr></tr>", "");
    }

    public String getInputControl(DsColumnsInfo columnInfo, DsEditColumnsInfo editColInfo, boolean isSingleLine) {
        StringBuilder sb = new StringBuilder();

        String colName = columnInfo.getColname().toLowerCase();
        String editStyle = columnInfo.getEditstyle();
        String format = editColInfo.getFormat();
        if (DsColumnFormatList.FORMAT_DATALIST.equals(format)) {
            sb.append(String.format("<select name=\"%s\" style=\"width:150px;\" data-ects='{\"type\":\"dl\",\"dlName\":\"%s\"}'></select>",
                    colName, editStyle));
        } else {
            if (isSingleLine) {
                sb.append(String.format("<input type=\"text\" name=\"%s\" class='txt_600'>", colName));
            } else {
                sb.append(String.format("<input name=\"%s\"  type=\"text\" class='txt_150'>", colName));
            }
        }

        return sb.toString();
    }

    public boolean isReadOnly() {
        return isReadOnly;
    }

    public void setReadOnly(boolean readOnly) {
        isReadOnly = readOnly;
    }
}

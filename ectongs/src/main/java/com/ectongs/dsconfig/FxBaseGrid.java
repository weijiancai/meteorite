package com.ectongs.dsconfig;

import cc.csdn.base.basedata.dsconfig.info.DsColumnsInfo;
import cc.csdn.base.basedata.dsconfig.info.DsEditColumnsInfo;
import cc.csdn.base.db.dataobj.dsconfig.DataStoreConfig;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.util.*;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class FxBaseGrid extends GridPane {
//    private List<DataStoreColumn> items;

    private Map<String, Object> nameCtlMap = new HashMap<String, Object>();
    private Map<String, Object> colNameCtlMap = new HashMap<String, Object>();
    private Map<String, DsEditColumnsInfo> columnNameMap = new HashMap<String, DsEditColumnsInfo>();
    private DataStoreConfig dsConfig;
    private boolean isReadOnly;

    public FxBaseGrid(DataStoreConfig dsConfig) {
        this.dsConfig = dsConfig;
        setVgap(5);
        setHgap(4);
    }

    /**
     *
     * @param columnCount
     */
    public void addItems(int columnCount) {
        List<DsEditColumnsInfo> cols = dsConfig.getEditColumns().getColumnList();
        Collections.sort(cols, new Comparator<DsEditColumnsInfo>() {
            @Override
            public int compare(DsEditColumnsInfo o1, DsEditColumnsInfo o2) {
                int n1 = o1.getOrdernum() == null ? 0 : o1.getOrdernum();
                int n2 = o2.getOrdernum() == null ? 0 : o2.getOrdernum();
                return n1 - n2;
            }
        });

        Label label;
        Region labelGap;
        TextField textField;
        Region fieldGap;
        int rowIdx = 0;
        int colIdx = 0;

        for (DsEditColumnsInfo column : dsConfig.getEditColumns().getColumnList()) {
            columnNameMap.put(column.getColname(), column);
//            colNameCtlMap.put(column.getColName().toLowerCase(), column);

            if (!column.isShow()) {
                continue;
            }

            DsColumnsInfo columnInfo = dsConfig.getColumns().getColumn(column.getName());
            String cName = columnInfo.getCname();

            if (column.isSingleRow()) {
                rowIdx++;
                add(new Label(cName), 0, rowIdx);
                Region labelRegion = new Region();
                labelRegion.setPrefWidth(5);
                add(labelRegion, 1, rowIdx);
                TextArea ta = new TextArea();
                if (column.getHeight() != null) {
                    ta.setPrefHeight(column.getHeight());
                }
                ta.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {

                    }
                });
//                nameCtlMap.put(column.getName().toLowerCase(), ta);
//                colNameCtlMap.put(column.getColName().replace("_", "").toLowerCase(), ta);
                //tf.setText(map.get(colName).toString());
                if (isReadOnly) {
                    ta.setDisable(true);
                }
                add(ta, 2, rowIdx, 9, 1);
                colIdx = 0;
                rowIdx++;
                continue;
            }

            label = new Label(cName);
            this.add(label, colIdx++, rowIdx);

            labelGap = new Region();
            int iLabelGap = 10;
            labelGap.setPrefWidth(iLabelGap);
            this.add(labelGap, colIdx++, rowIdx);

            int gridCellMinHeight = 20;
            int tfPrefWidth = 185;
            /*if ("base.datestyle.generaldate".equals(columnInfo.getEditStyleName())) {
                FXCalendar startTf = new FXCalendar();
                startTf.setPrefWidth(87);
                startTf.setId("startDate");
                label = new Label("��  ");
                FXCalendar endTf = new FXCalendar();
                endTf.setPrefWidth(87);
                endTf.setId("endDate");
                HBox box = new HBox();
                box.setMinHeight(gridCellMinHeight);
                box.getChildren().addAll(startTf, label, endTf);
                if (isReadOnly) {
                    box.setDisable(true);
                }
                this.add(box, colIdx++, rowIdx);
                nameCtlMap.put(column.getName().toLowerCase(), box);
                colNameCtlMap.put(column.getColName().toLowerCase(), box);
            } else if ("base.datalist.truefalselist".equals(column.getEditStyleName())) {
                ChoiceBox<String> choiceBox = new ChoiceBox<String>();
                choiceBox.getItems().addAll("ȫ��", "��", "��");
                choiceBox.setPrefWidth(tfPrefWidth);
                choiceBox.setMinHeight(gridCellMinHeight);
                choiceBox.getSelectionModel().select(StringUtil.toInt(column.getDefaultValue(), 0));
                if (isReadOnly) {
                    choiceBox.setDisable(true);
                }
                this.add(choiceBox, colIdx++, rowIdx);
                nameCtlMap.put(column.getName().toLowerCase(), choiceBox);
                colNameCtlMap.put(column.getColName().toLowerCase(), choiceBox);
            } else if ("bb_db_product.station_supplier".equals(column.getDbName())) {
                StationSupplierDataList stationSupplierDataList = new StationSupplierDataList();
                if (isReadOnly) {
                    stationSupplierDataList.setDisable(true);
                }
                this.add(stationSupplierDataList, colIdx++, rowIdx);
                nameCtlMap.put(column.getName().toLowerCase(), stationSupplierDataList);
                colNameCtlMap.put(column.getColName().toLowerCase(), stationSupplierDataList);
            } else if (DsColumnFormatList.FORMAT_DATALIST.endsWith(column.getFormat())) {
                DataListView dataList = new DataListView();
                try {
                    dataList.setClassNameAndArgs(column.getEditStyleName(), LoginModel.getInstance().getStationId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dataList.setPrefWidth(tfPrefWidth);
                dataList.setMinHeight(gridCellMinHeight);
                dataList.selectOption(column.getDefaultValue());
                if (isReadOnly) {
                    dataList.setDisable(true);
                }
                this.add(dataList, colIdx++, rowIdx);
                nameCtlMap.put(column.getName().toLowerCase(), dataList);
                colNameCtlMap.put(column.getColName().toLowerCase(), dataList);
            } else {*/
                textField = new TextField();
                textField.setPrefWidth(tfPrefWidth);
                textField.setMinHeight(gridCellMinHeight);
                if (isReadOnly) {
                    textField.setDisable(true);
                }
                this.add(textField, colIdx++, rowIdx);
//                nameCtlMap.put(column.getName().toLowerCase(), textField);
//                colNameCtlMap.put(column.getColName().toLowerCase(), textField);
//            }


            if (columnCount == 1) {
                colIdx = 0;
                rowIdx++;
            } else {
                if (colIdx == columnCount * 4 - 1) {
                    colIdx = 0;
                    rowIdx++;
                } else {
                    fieldGap = new Region();
                    int iFieldGap = 15;
                    fieldGap.setPrefWidth(iFieldGap);
                    this.add(fieldGap, colIdx++, rowIdx);
                }
            }
        }
    }

    public Map<String, Object> getNameControlMap() {
        return nameCtlMap;
    }

    public Map<String, Object> getColNameControlMap() {
        return colNameCtlMap;
    }

    public boolean isReadOnly() {
        return isReadOnly;
    }

    public void setReadOnly(boolean readOnly) {
        isReadOnly = readOnly;
    }
}

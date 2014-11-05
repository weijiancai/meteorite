package com.meteorite.fxbase.ui.win;

import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.datasource.DataSourceManager;
import com.meteorite.core.datasource.QueryBuilder;
import com.meteorite.core.datasource.db.DBDataSource;
import com.meteorite.core.datasource.db.QueryResult;
import com.meteorite.core.datasource.db.object.DBConnection;
import com.meteorite.core.datasource.db.sql.SqlFormat;
import com.meteorite.core.datasource.db.util.JdbcTemplate;
import com.meteorite.core.datasource.eventdata.SqlExecuteEventData;
import com.meteorite.core.dict.DictCode;
import com.meteorite.core.dict.DictManager;
import com.meteorite.core.dict.EnumAlign;
import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.model.impl.BaseTreeNode;
import com.meteorite.core.observer.Observer;
import com.meteorite.core.ui.layout.property.TableFieldProperty;
import com.meteorite.core.ui.layout.property.TableProperty;
import com.meteorite.core.ui.model.View;
import com.meteorite.core.util.UFile;
import com.meteorite.core.util.UIO;
import com.meteorite.core.util.UString;
import com.meteorite.fxbase.MuEventHandler;
import com.meteorite.fxbase.ui.component.form.MuComboBox;
import com.meteorite.fxbase.ui.view.MUDialog;
import com.meteorite.fxbase.ui.view.MUTable;
import com.sun.javafx.webkit.Accessor;
import com.sun.webkit.WebPage;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import org.glassfish.jersey.server.internal.BackgroundScheduler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * SQL控制台
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUSqlConsoleWin extends BorderPane {
    private TreeItem<ITreeNode> sqlConsoleTreeItem;
    private TabPane tabPane;
    private MuComboBox dataSource;
    private WebPage webPage;
    private TextArea messageTA;
    private MUTable formatTable;
    private MUTable resultTable;

    private String codeMirrorJs;
    private String codeMirrorCss;
    private String sqlModeJs;
    private String fullScreenJs;
    private String fullScreenCss;

    public MUSqlConsoleWin() {
        initUI();
    }

    private void initUI() {
        BaseTreeNode node = new BaseTreeNode("SQL控制台");
        node.setId("SqlConsoleView");
        node.setView(View.createNodeView(this));
        sqlConsoleTreeItem = new TreeItem<ITreeNode>(node);

        try {
            codeMirrorJs = UFile.readStringFromCP("/codemirror/lib/codemirror.js");
            codeMirrorCss = UFile.readStringFromCP("/codemirror/lib/codemirror.css");
            sqlModeJs = UFile.readStringFromCP("/codemirror/mode/sql/sql.js");
            fullScreenJs = UFile.readStringFromCP("/codemirror/addon/display/fullscreen.js");
            fullScreenCss = UFile.readStringFromCP("/codemirror/addon/display/fullscreen.css");
        } catch (IOException e) {
            MUDialog.showExceptionDialog(e);
        }

        createTopBar();
        createCenter();
        createBottom();
    }

    private void createTopBar() {
        ToolBar toolBar = new ToolBar();
        // 数据源
        dataSource = new MuComboBox(DictManager.getDict(DictManager.DICT_DB_DATA_SOURCE));
        dataSource.setPrefWidth(120);
        // 执行Sql
        Button btnExec = new Button("执行");
        btnExec.setOnAction(new ExecSqlEventHandler());
        // 格式化
        Button btnFormat = new Button("格式化");
        btnFormat.setOnAction(new SqlFormatEventHandler());

        toolBar.getItems().addAll(new Label("数据源"), dataSource, btnExec, btnFormat);
        this.setTop(toolBar);
    }

    private void createCenter() {
        final WebView webView = new WebView();
        webView.setStyle("-fx-border-color: #e02222");
        webPage = Accessor.getPageFor(webView.getEngine());
        setSql("");
        this.setCenter(webView);
    }

    private void createBottom() {
        tabPane = new TabPane();
        // 消息Tab
        Tab messageTab = new Tab("消息");
        messageTA = new TextArea();
        messageTab.setClosable(false);
        messageTab.setContent(messageTA);
        // 查询结果
        Tab resultTab = new Tab("查询结果");
        resultTab.setClosable(false);
        resultTable = new MUTable();
        resultTable.setShowToolBar(false);
        resultTab.setContent(resultTable);
        // 格式化
        Tab formatTab = new Tab("格式化");
        formatTab.setClosable(false);
        formatTable = new MUTable();
        formatTable.setShowToolBar(false);
        formatTable.setShowPaging(false);
        formatTable.initUI(getSqlFormatTableProperty());
        formatTab.setContent(formatTable);

        tabPane.getTabs().addAll(messageTab, resultTab, formatTab);
        this.setBottom(tabPane);
    }

    private void setSql(String sql) {
        webPage.load(webPage.getMainFrame(), convertToHtml(sql), "text/html");
    }

    private String getSql() {
        return webPage.getInnerText(webPage.getMainFrame());
    }

    private String convertToHtml(String source) {
        StringBuilder html = new StringBuilder();
        html.append("<html>\n");
        html.append("    <head>\n");
        html.append("    <script type=\"text/javascript\">\n");
        html.append(codeMirrorJs);
        html.append('\n');
        html.append(sqlModeJs);
        html.append("\n");
        html.append(fullScreenJs);
        html.append("    </script>\n");
        html.append("    <style>\n");
        html.append(codeMirrorCss);
        html.append('\n');
        html.append(fullScreenCss);
        html.append('\n');

        html.append("        body {background-color: #f4f4f4;}\n");
        html.append("    </style>\n");
        html.append("    </head>\n");
        html.append("<body contenteditable=\"true\">\n");
        html.append("\n");
        html.append("    <script type=\"text/javascript\"> " +
                "var myCodeMirror = CodeMirror(document.body, {mode:  'text/x-sql',fullScreen:true}); " +
                (UString.isEmpty(source) ? "" : String.format("myCodeMirror.setValue(\"%s\");", source.replace("\n", "\\n"))) +
                "</script>\n");
        html.append("</body>\n");
        html.append("</html>\n");

        try {
            UFile.write(html.toString(), "d:/test/1.html");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return html.toString();
    }

    public TreeItem<ITreeNode> getSqlConsoleTreeItem() {
        return sqlConsoleTreeItem;
    }

    private TableProperty getSqlFormatTableProperty() {
        TableProperty tableProperty = new TableProperty();
        TableFieldProperty colNameProp = new TableFieldProperty(SqlFormat.COL_NAME, "列名", 170);
        colNameProp.setSortNum(10);
        TableFieldProperty valueProp = new TableFieldProperty(SqlFormat.VALUE, "值", 400);
        valueProp.setSortNum(20);
        TableFieldProperty valueLengthProp = new TableFieldProperty(SqlFormat.VALUE_LENGTH, "长度", 30);
        valueLengthProp.setSortNum(30);
        valueLengthProp.setAlign(EnumAlign.CENTER);
        TableFieldProperty dataTypeProp = new TableFieldProperty(SqlFormat.DB_DATA_TYPE, "数据类型", 120);
        dataTypeProp.setSortNum(40);
        TableFieldProperty maxLengthProp = new TableFieldProperty(SqlFormat.MAX_LENGTH, "最大长度", 50);
        maxLengthProp.setSortNum(50);
        maxLengthProp.setAlign(EnumAlign.CENTER);
        tableProperty.getFieldProperties().add(colNameProp);
        tableProperty.getFieldProperties().add(valueProp);
        tableProperty.getFieldProperties().add(valueLengthProp);
        tableProperty.getFieldProperties().add(dataTypeProp);
        tableProperty.getFieldProperties().add(maxLengthProp);
        return tableProperty;
    }

    class ExecSqlEventHandler extends MuEventHandler<ActionEvent> {

        @Override
        public void doHandler(final ActionEvent event) throws Exception {
            messageTA.setText(""); // 清空消息

            String sql = getSql();
            if (UString.isEmpty(sql)) {
                return;
            }
            sql = sql.trim().replace('\u200B', ' ').replace('\n', ' ');
            DictCode code = dataSource.getSelectedItem();
            if (code == null) {
                MUDialog.showInformation("请选择数据源！");
                return;
            }

            DBDataSource dbDataSource = (DBDataSource) DataSourceManager.getDataSource(code.getId());

            if (sql.toLowerCase().startsWith("select")) { // 查询
                // 选中查询结果Tab
                tabPane.getSelectionModel().select(1);

                QueryBuilder builder = QueryBuilder.create(sql, dbDataSource.getDatabaseType());
                QueryResult<DataMap> list = dbDataSource.retrieve(builder, 0, resultTable.getPageRows());
                resultTable.initUI(list);
            } else {
                DBConnection connection = dbDataSource.getDbConnection();
                connection.getSqlExecuteSubject().registerObserver(new Observer<SqlExecuteEventData>() {
                    @Override
                    public void update(SqlExecuteEventData data) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("==============================================================================================\r\n");
                        sb.append(data.getSql()).append("\r\n");
                        sb.append(data.isSuccess() ? "执行成功！" : "执行失败");
                        if (!data.isSuccess() && data.getException() != null) {
                            for(StackTraceElement element : data.getException().getStackTrace()) {
                                sb.append(element.toString());
                            }
                        }
                        messageTA.setText(messageTA.getText() + "\r\n" + sb.toString());
                    }
                });
                connection.execSqlScript(sql, ";");
            }
        }
    }

    class SqlFormatEventHandler extends MuEventHandler<ActionEvent> {

        @Override
        public void doHandler(ActionEvent event) throws Exception {
            messageTA.setText(""); // 清空消息

            String sql = getSql();
            if (UString.isEmpty(sql)) {
                return;
            }
            sql = sql.trim().replace('\u200B', ' ').replace('\n', ' ');
            DictCode code = dataSource.getSelectedItem();
            if (code == null) {
                MUDialog.showInformation("请选择数据源！");
                return;
            }
            // 选中格式化Tab
            tabPane.getSelectionModel().select(2);

            try {
                DBDataSource dbDataSource = (DBDataSource) DataSourceManager.getDataSource(code.getId());
                SqlFormat format = new SqlFormat(sql, dbDataSource);
                setSql(format.format());
                formatTable.getItems().addAll(format.getDataList());
            } catch (Exception e) {
                // 选中消息Tab
                tabPane.getSelectionModel().select(0);

                StringBuilder sb = new StringBuilder();
                sb.append("格式化代码失败！ ").append(e.getMessage()).append("\n");
                for(StackTraceElement element : e.getStackTrace()) {
                    sb.append(element.toString()).append("\r\n");
                }
                messageTA.setText(sb.toString());
            }
        }
    }
}

package com.ectongs.ideaplugin.sql;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.*;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.sql.visitor.SQLASTOutputVisitor;
import com.alibaba.druid.util.JdbcUtils;
import com.intellij.ui.table.JBTable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqlFormatDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea taSql;
    private JTable resultTable;
    private JButton btnFormat;

    private DefaultTableModel tableModel;

    public SqlFormatDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // 格式化
        btnFormat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                format();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        initUI();
    }

    private void initUI() {
        /*TableColumn nameCol = new TableColumn();
        nameCol.setHeaderValue("列名");
        resultTable.addColumn(nameCol);*/
        taSql.setSize(650, 90);
    }

    private void onOK() {
// add your code here
        dispose();
    }

    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    private void format() {
        // 清除原有行
        tableModel.setRowCount(0);
        String sql = taSql.getText().toLowerCase();
// parser得到AST
        SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(sql, JdbcUtils.SQL_SERVER);
        List<SQLStatement> stmtList = parser.parseStatementList(); //
        // 将AST通过visitor输出
        for (SQLStatement stmt : stmtList) {
            //stmt.accept(visitor);
            if (stmt instanceof SQLSelectStatement) {
//                taSql.setText(SQLUtils.format(taSql.getText(), JdbcUtils.SQL_SERVER));
                resultTable.setVisible(false);
            } else if (stmt instanceof SQLInsertStatement) {
                resultTable.setVisible(true);
                SQLInsertStatement insertStatement = (SQLInsertStatement) stmt;
                for (int i = 0; i < insertStatement.getColumns().size(); i++) {
                    String[] array = new String[3];
                    array[0] = insertStatement.getColumns().get(i).toString();
                    if (i < insertStatement.getValues().getValues().size()) {
                        array[1] = insertStatement.getValues().getValues().get(i).toString();
                        array[2] = array[1].length() + "";
                    } else {
                        array[1] = "";
                        array[2] = "0";
                    }
                    tableModel.addRow(array);
                }
            } else if (stmt instanceof SQLUpdateStatement) {
                resultTable.setVisible(true);
                SQLUpdateStatement updateStatement = (SQLUpdateStatement) stmt;
                for (SQLUpdateSetItem item : updateStatement.getItems()) {
                    String[] array = new String[3];
                    array[0] = item.getColumn().toString();
                    array[1] = item.getValue().toString();
                    array[2] = array[1].length() + "";
                    tableModel.addRow(array);
                }
            }
        }
        taSql.setText(SQLUtils.format(taSql.getText(), JdbcUtils.SQL_SERVER));

        // 更新表格
        resultTable.invalidate();
    }

    private void selectFormat(String sql) {

    }

    private void updateFormat(String sql) {
        sql = sql.toLowerCase();
        // 找第一个括号
        int end = sql.indexOf("values");
        String first = sql.substring(sql.indexOf("(") + 1, sql.indexOf(")"));
        String[] firstArray = first.split(",");
        sql = sql.substring(end + 1);
        String second = sql.substring(sql.indexOf("(") + 1, sql.lastIndexOf(")"));
        String[] secondArray = second.split(",");

        // 清除原有行
        tableModel.setRowCount(0);
        // 填充数据
//        String[] colNames = {"列名", "值", "长度"};
//        String[][] cellData = new String[firstArray.length][];
        for (int i = 0; i < firstArray.length; i++) {
            String[] array = new String[3];
            array[0] = firstArray[i];
            if(i < secondArray.length) {
                array[1] = secondArray[i].trim();
                array[2] = array[1].length() + "";
            } else {
                array[1] = "";
                array[2] = "0";
            }
            tableModel.addRow(array);
        }
    }

    public static void main(String[] args) {
        SqlFormatDialog dialog = new SqlFormatDialog();
        dialog.setTitle("Sql格式化");
        int width = 800, height = 600;
        dialog.setSize(width, height);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        int screenWidth = dimension.width;                     //获取屏幕的宽
        int screenHeight = dimension.height;                   //获取屏幕的高
        dialog.setLocation(screenWidth/2-width/2, screenHeight/2-height/2);

        dialog.setVisible(true);
        System.exit(0);
    }

    private void createUIComponents() {
        tableModel = new DefaultTableModel();
        resultTable = new JBTable(tableModel);
        resultTable.getTableHeader().setVisible(true);
        tableModel.addColumn("列名");
        tableModel.addColumn("值");
        tableModel.addColumn("长度");
    }
}

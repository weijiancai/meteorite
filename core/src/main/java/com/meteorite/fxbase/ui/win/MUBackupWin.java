package com.meteorite.fxbase.ui.win;

import com.meteorite.core.backup.BackupManager;
import com.meteorite.core.backup.BackupSetting;
import com.meteorite.core.config.PathManager;
import com.meteorite.core.config.SystemManager;
import com.meteorite.core.model.ITreeNode;
import com.meteorite.core.model.impl.BaseTreeNode;
import com.meteorite.core.ui.model.View;
import com.meteorite.core.util.UDate;
import com.meteorite.core.util.UFile;
import com.meteorite.fxbase.MuEventHandler;
import com.meteorite.fxbase.ui.component.form.MUCheckListView;
import com.meteorite.fxbase.ui.component.form.MUListView;
import com.meteorite.fxbase.ui.view.MUDialog;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.util.Callback;

import java.io.File;
import java.util.*;

/**
 * 备份、恢复管理窗口
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUBackupWin extends BorderPane {
    private TreeItem<ITreeNode> backupTreeItem;
    private MUCheckListView<String> checkListView;
    private MUListView<BackupFile> listView;

    public MUBackupWin() {
        initUI();
    }

    private void initUI() {
        BaseTreeNode backupTreeNode = new BaseTreeNode("备份恢复");
        backupTreeNode.setId("Backup");
        backupTreeNode.setView(View.createNodeView(this));
        backupTreeItem = new TreeItem<ITreeNode>(backupTreeNode);

        initToolbar();
        initLeft();
        initCenter();
    }

    private void initCenter() {
        listView = new MUListView<BackupFile>();
        listView.setCellFactory(new Callback<ListView<BackupFile>, ListCell<BackupFile>>() {
            @Override
            public ListCell<BackupFile> call(ListView<BackupFile> param) {
                return new ListCell<BackupFile>() {
                    @Override
                    protected void updateItem(BackupFile item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty) {
                            HBox box = new HBox();
                            box.getChildren().add(new Label(item.getFileName()));
                            Region region = new Region();
                            HBox.setHgrow(region, Priority.ALWAYS);
                            box.getChildren().add(region);
                            Label sizeLabel = new Label(item.getSize());
                            sizeLabel.setPrefWidth(300);

                            Label dateLabel = new Label(item.getDate());
                            dateLabel.setPrefWidth(160);
                            box.getChildren().addAll(sizeLabel, dateLabel);
                            setGraphic(box);
                        }
                    }
                };
            }
        });
        loadBackupFile();

        this.setCenter(listView);
    }

    private void initLeft() {
        List<String> list = new ArrayList<String>();
        list.add("系统参数设置");
        list.add("数据字典");
        list.add("项目信息");
        list.add("元数据项");
        list.add("元数据");
        list.add("元数据引用");
        list.add("视图");
        list.add("数据源");

        checkListView = new MUCheckListView<String>(list);
        checkListView.selectAll();
        this.setLeft(checkListView);
    }

    private void initToolbar() {
        ToolBar toolBar = new ToolBar();
        Button btnBackup = new Button("备份");
        btnBackup.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                BackupManager.getInstance().backup(getBackupSetting());
                // 刷新备份
                loadBackupFile();
                MUDialog.showInformation("备份成功！");
            }
        });

        Button btnRestore = new Button("恢复");
        btnRestore.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                BackupFile file = listView.getSelectionModel().getSelectedItem();
                if (file == null) {
                    MUDialog.showInformation("请选择要恢复的备份文件！");
                    return;
                }
                BackupManager.getInstance().restore(getBackupSetting(), file.getFile());
                MUDialog.showInformation("恢复成功！");
            }
        });

        // 恢复到出厂设置
        Button btnRestoreBorn = new Button("恢复到出厂设置");
        btnRestoreBorn.setOnAction(new MuEventHandler<ActionEvent>() {
            @Override
            public void doHandler(ActionEvent event) throws Exception {
                SystemManager.resetToDefaultSetting();
                MUDialog.showInformation("恢复成功！");
            }
        });

        toolBar.getItems().addAll(btnBackup, btnRestore, btnRestoreBorn);
        this.setTop(toolBar);
    }

    private void loadBackupFile() {
        List<BackupFile> list = new ArrayList<BackupFile>();
        File backupDir = PathManager.getBackupPath();
        File[] files = backupDir.listFiles();
        if (files != null) {
            // 排序 按时间逆序
            Arrays.sort(files, new Comparator<File>() {
                @Override
                public int compare(File o1, File o2) {
                    return (int) (o2.lastModified() - o1.lastModified());
                }
            });
            for (File file : files) {
                list.add(new BackupFile(file));
            }
        }
        listView.getItems().clear();
        listView.getItems().addAll(list);
    }

    public TreeItem<ITreeNode> getBackupTreeItem() {
        return backupTreeItem;
    }

    private BackupSetting getBackupSetting() {
        BackupSetting setting = new BackupSetting();
        for (String str : checkListView.getSelectionModel().getSelectedItems()) {
            if ("系统参数设置".equals(str)) {
                setting.setSetting(true);
            } else if ("数据字典".equals(str)) {
                setting.setDict(true);
            } else if ("项目信息".equals(str)) {
                setting.setProject(true);
            } else if ("元数据项".equals(str)) {
                setting.setMetaItem(true);
            } else if ("元数据".equals(str)) {
                setting.setMeta(true);
            } else if ("元数据引用".equals(str)) {
                setting.setMetaReference(true);
            } else if ("视图".equals(str)) {
                setting.setView(true);
            } else if ("数据源".equals(str)) {
                setting.setDataSource(true);
            }
        }

        return setting;
    }

    class BackupFile {
        private File file;
        private SimpleStringProperty fileName = new SimpleStringProperty();
        private SimpleStringProperty date = new SimpleStringProperty();
        private SimpleStringProperty size = new SimpleStringProperty();

        public BackupFile(File file) {
            this.file = file;
            setFileName(file.getName());
            setDate(UDate.dateToString(new Date(file.lastModified()), "yyyy-MM-dd HH:ss:mm"));
            setSize(UFile.getSize(file));
        }

        public File getFile() {
            return file;
        }

        public void setFile(File file) {
            this.file = file;
        }

        public String getFileName() {
            return fileName.get();
        }

        public SimpleStringProperty fileNameProperty() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName.set(fileName);
        }

        public String getDate() {
            return date.get();
        }

        public SimpleStringProperty dateProperty() {
            return date;
        }

        public void setDate(String date) {
            this.date.set(date);
        }

        public String getSize() {
            return size.get();
        }

        public SimpleStringProperty sizeProperty() {
            return size;
        }

        public void setSize(String size) {
            this.size.set(size);
        }

        @Override
        public String toString() {
            return fileName.get();
        }
    }
}

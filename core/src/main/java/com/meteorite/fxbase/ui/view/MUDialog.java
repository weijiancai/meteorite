package com.meteorite.fxbase.ui.view;

import com.meteorite.fxbase.BaseApp;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.util.Callback;
import org.apache.log4j.Logger;
import org.controlsfx.dialog.AbstractDialogAction;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

/**
 * JavaFx 对话框
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MUDialog extends Dialog {
    private static final Logger log = Logger.getLogger(MUDialog.class);

    public MUDialog(Object owner, String title) {
        super(owner, title);
    }

    /**
     * 显示自定义对话框
     *
     * @param owner 父容器
     * @param title 对话框标题
     * @param content 对话框内容节点
     */
    public static void showCustomDialog(Object owner, String title, Node content, final Callback<Void, Void> callback) {
        if (owner == null) {
            owner = BaseApp.getInstance().getStage();
        }
        final MUDialog dialog = new MUDialog(owner, title);
        dialog.setContent(content);
        dialog.getActions().add(new AbstractDialogAction("确定", ActionTrait.CLOSING, ActionTrait.CANCEL) {
            @Override
            public void execute(ActionEvent event) {
                if (callback != null) {
                    callback.call(null);
                }
                dialog.hide();
            }
        });
        dialog.show();
    }

    /**
     * 显示消息对话框
     *
     * @param msg 消息内容
     */
    public static void showInformation(String msg) {
        Dialogs.create().title("信息").masthead(null).message(msg).showInformation();
    }

    /**
     * 显示以藏信息对话框
     *
     * @param e 异常信息
     */
    public static void showExceptionDialog(Throwable e) {
        log.error(e.getMessage(), e);
        Dialogs.create().title("异常信息").masthead(null).message(e.getMessage()).showException(e);
    }
}

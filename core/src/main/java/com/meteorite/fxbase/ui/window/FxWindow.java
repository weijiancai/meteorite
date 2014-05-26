package com.meteorite.fxbase.ui.window;

import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * JavaFx窗口BaseWindow的实现
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class FxWindow extends BaseWindow {
    private Stage stage;
    private Window owner;
//    private BasePane basePane;
    private WindowType windowType;

    @Override
    public WindowType getWindowType() {
        return windowType;
    }

    @Override
    public void closeWin() {
        if (WindowType.WINDOW == windowType || WindowType.DIALOG == windowType) {
            stage.close();
        } else if (WindowType.PANE == windowType) {
//            basePane.closeWin();
        }
    }

    @Override
    public void showWin() {
        if (WindowType.WINDOW == windowType || WindowType.DIALOG == windowType) {
            stage.centerOnScreen();
            stage.showAndWait();
        } else if (WindowType.PANE == windowType) {
//            basePane.showWin();
        }
    }

    @Override
    public void hideWin() {
        if (WindowType.WINDOW == windowType || WindowType.DIALOG == windowType) {
            stage.hide();
        } else if (WindowType.PANE == windowType) {
//            basePane.hideWin();
        }
    }
}

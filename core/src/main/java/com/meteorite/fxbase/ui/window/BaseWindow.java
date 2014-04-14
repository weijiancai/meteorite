package com.meteorite.fxbase.ui.window;

/**
 * 窗口基本信息
 *
 * @author wei_jc
 * @version 1.0.0
 */
public abstract class BaseWindow {
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获得窗口类型
     *
     * @return 返回窗口类型
     */
    public abstract WindowType getWindowType();

    /**
     * 关闭窗口
     */
    public abstract void closeWin();

    /**
     * 显示窗口
     */
    public abstract void showWin();

    /**
     * 隐藏窗口
     */
    public abstract void hideWin();
}

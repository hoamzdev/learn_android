package com.hwa.excelllll.excelsoui;

/**
 * Coordinates document-level UI state used by page views.
 * The host can replace the current instance when attaching its document UI.
 */
public class NUIDocView {
    public static final int OVERSIZE_MARGIN = 20;

    private static NUIDocView currentInstance = new NUIDocView();
    private boolean composing;
    private boolean pageListVisible;

    public static NUIDocView currentNUIDocView() {
        return currentInstance;
    }

    public static void setCurrentNUIDocView(NUIDocView docView) {
        currentInstance = docView != null ? docView : new NUIDocView();
    }

    public boolean getIsComposing() {
        return this.composing;
    }

    public void setIsComposing(boolean composing) {
        this.composing = composing;
    }

    public boolean isPageListVisible() {
        return this.pageListVisible;
    }

    public void setPageListVisible(boolean visible) {
        this.pageListVisible = visible;
    }

    public void onReflowScale() {
    }

    public void showUI(boolean visible) {
    }

    public void triggerRender() {
    }
}

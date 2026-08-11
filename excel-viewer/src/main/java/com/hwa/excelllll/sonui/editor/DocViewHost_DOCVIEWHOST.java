package com.hwa.excelllll.sonui.editor;

/* JADX INFO: loaded from: classes.dex */
public interface DocViewHost {
    void clickSheetButton(int i, boolean z);

    int getBorderColor();

    DocView getDocView();

    int getKeyboardHeight();

    void layoutNow();

    void onShowKeyboard(boolean z);

    void prefinish();

    void selectionupdated();

    void setCurrentPage(int i);

    boolean showKeyboard();
}

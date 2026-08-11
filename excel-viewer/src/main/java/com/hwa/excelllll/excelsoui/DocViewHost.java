package com.hwa.excelllll.excelsoui;
public interface DocViewHost {
    void clickSheetButton(int sheetIndex, boolean isSelected);

    int getBorderColor();

    DocView getDocView();

    int getKeyboardHeight();

    void layoutNow();

    void onShowKeyboard(boolean isVisible);

    void prefinish();

    void selectionupdated();

    void setCurrentPage(int pageIndex);

    boolean showKeyboard();
}

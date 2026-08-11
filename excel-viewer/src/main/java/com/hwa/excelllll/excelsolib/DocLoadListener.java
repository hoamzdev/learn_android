package com.hwa.excelllll.excelsolib;
public interface DocLoadListener {
    void onLoadComplete();

    void onLoadProgress(int progressPercent);

    void onLoadError(int errorId, int errorDetail);

    void onLoadCancelled();

    void onSelectionChanged(int startPage, int endPage);
}

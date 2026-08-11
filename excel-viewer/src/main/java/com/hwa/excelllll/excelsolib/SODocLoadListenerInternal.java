package com.hwa.excelllll.excelsolib;
public interface SODocLoadListenerInternal {
    void onDocumentLoaded(SODoc document);

    void error(int i, int i2);

    void onLayoutCompleted();

    void onSelectionChanged(int i, int i2);

    void progress(int i, boolean z);
}

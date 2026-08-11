package com.hwa.excelllll.solib;

/* JADX INFO: loaded from: classes.dex */
public interface SODocLoadListenerInternal {
    void a(SODoc sODoc);

    void error(int i, int i2);

    void onLayoutCompleted();

    void onSelectionChanged(int i, int i2);

    void progress(int i, boolean z);
}

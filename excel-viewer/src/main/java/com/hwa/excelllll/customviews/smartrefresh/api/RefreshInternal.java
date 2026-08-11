package com.hwa.excelllll.customviews.smartrefresh.api;

import android.view.View;
import com.xlsxreader.excelreader.customviews.smartrefresh.SmartRefreshLayout;
import com.xlsxreader.excelreader.customviews.smartrefresh.constant.SpinnerStyle;
import com.xlsxreader.excelreader.customviews.smartrefresh.listener.OnStateChangedListener;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public interface RefreshInternal extends OnStateChangedListener {
    /* JADX INFO: renamed from: a */
    void mo21900a(int i, int i2, float f);

    /* JADX INFO: renamed from: d */
    void mo21901d(RefreshLayout refreshLayout, int i, int i2);

    /* JADX INFO: renamed from: e */
    void mo21902e(RefreshKernel refreshKernel, int i, int i2);

    /* JADX INFO: renamed from: f */
    void mo21903f(SmartRefreshLayout smartRefreshLayout, int i, int i2);

    SpinnerStyle getSpinnerStyle();

    View getView();

    /* JADX INFO: renamed from: h */
    int mo21904h(RefreshLayout refreshLayout, boolean z);

    /* JADX INFO: renamed from: i */
    boolean mo21905i();

    /* JADX INFO: renamed from: j */
    void mo21906j(boolean z, float f, int i, int i2, int i3);

    void setPrimaryColors(int... iArr);
}

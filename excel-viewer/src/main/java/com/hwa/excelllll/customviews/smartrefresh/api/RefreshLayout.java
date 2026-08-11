package com.hwa.excelllll.customviews.smartrefresh.api;

import android.view.ViewGroup;
import com.xlsxreader.excelreader.customviews.smartrefresh.SmartRefreshLayout;
import com.xlsxreader.excelreader.customviews.smartrefresh.constant.RefreshState;
import com.xlsxreader.excelreader.customviews.smartrefresh.listener.OnRefreshListener;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public interface RefreshLayout {
    /* JADX INFO: renamed from: a */
    SmartRefreshLayout mo21876a(RefreshHeader refreshHeader);

    /* JADX INFO: renamed from: b */
    SmartRefreshLayout mo21877b(int i);

    /* JADX INFO: renamed from: c */
    SmartRefreshLayout mo21878c(boolean z);

    /* JADX INFO: renamed from: d */
    SmartRefreshLayout mo21879d(OnRefreshListener onRefreshListener);

    ViewGroup getLayout();

    RefreshState getState();
}

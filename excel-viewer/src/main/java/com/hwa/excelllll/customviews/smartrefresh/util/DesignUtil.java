package com.hwa.excelllll.customviews.smartrefresh.util;

import android.view.View;
import android.view.ViewGroup;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshKernel;
import com.xlsxreader.excelreader.customviews.smartrefresh.impl.RefreshContentWrapper;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class DesignUtil {
    /* JADX INFO: renamed from: a */
    public static void m21917a(View view, RefreshKernel refreshKernel, final RefreshContentWrapper refreshContentWrapper) {
        try {
            if (view instanceof CoordinatorLayout) {
                refreshKernel.mo21894e().setNestedScrollingEnabled(false);
                ViewGroup viewGroup = (ViewGroup) view;
                for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                    View childAt = viewGroup.getChildAt(childCount);
                    if (childAt instanceof AppBarLayout) {
                        ((AppBarLayout) childAt).m18006a(new AppBarLayout.OnOffsetChangedListener() { // from class: com.xlsxreader.excelreader.customviews.smartrefresh.util.DesignUtil.1
                            @Override // com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
                            /* JADX INFO: renamed from: a */
                            public final void mo18026a(AppBarLayout appBarLayout, int i) {
                                boolean z = i >= 0;
                                boolean z2 = appBarLayout.getTotalScrollRange() + i <= 0;
                                RefreshContentWrapper refreshContentWrapper2 = refreshContentWrapper;
                                refreshContentWrapper2.f48570g = z;
                                refreshContentWrapper2.f48571h = z2;
                            }
                        });
                    }
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}

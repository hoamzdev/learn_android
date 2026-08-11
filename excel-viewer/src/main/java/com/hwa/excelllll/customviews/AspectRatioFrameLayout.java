package com.hwa.excelllll.customviews;

import android.widget.FrameLayout;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class AspectRatioFrameLayout extends FrameLayout {
    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        int[] iArr = {i, i2};
        super.onMeasure(iArr[0], iArr[1]);
    }
}

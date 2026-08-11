package com.hwa.excelllll.sonui.editor;

import android.content.Context;
import android.graphics.PointF;
import com.artifex.solib.SODoc;

/* JADX INFO: loaded from: classes.dex */
public class SlideShowPageView extends DocPageView {
    public SlideShowPageView(Context context, SODoc sODoc) {
        super(context, sODoc);
    }

    @Override // com.artifex.sonui.editor.DocPageView
    public void resize(int i, int i2) {
        PointF pointFZoomToFitRect = this.mPage.zoomToFitRect(i, i2);
        double dMin = Math.min(pointFZoomToFitRect.x, pointFZoomToFitRect.y);
        this.mZoom = dMin;
        this.mSize = this.mPage.sizeAtZoom(dMin);
    }

    @Override // com.artifex.sonui.editor.DocPageView
    public void setupPage(int i, int i2, int i3) {
        changePage(i);
        resize(i2, i3);
    }
}

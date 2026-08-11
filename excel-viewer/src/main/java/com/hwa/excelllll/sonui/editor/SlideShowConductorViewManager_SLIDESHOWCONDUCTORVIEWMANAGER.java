package com.hwa.excelllll.sonui.editor;

import android.graphics.PointF;
import android.graphics.RectF;
import com.artifex.solib.SODoc;
import com.artifex.solib.SOPage;

/* JADX INFO: loaded from: classes.dex */
public interface SlideShowConductorViewManager {
    void add(SlideShowConductorView slideShowConductorView);

    void animationsCompleted();

    void animationsRunning();

    void animationsStarted();

    void animationsWaiting();

    void destroyLayer(SlideShowConductorView slideShowConductorView);

    SlideShowConductorView newLayer(SODoc sODoc, SOPage sOPage, int i, PointF pointF, RectF rectF);

    void remove(SlideShowConductorView slideShowConductorView);
}

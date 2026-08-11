package com.hwa.excelllll.solib;

import android.graphics.PointF;
import android.graphics.RectF;

/* JADX INFO: loaded from: classes.dex */
public class SOSelectionLimits {
    private long internal;

    public SOSelectionLimits(long j) {
        this.internal = j;
    }

    private native void destroy();

    public native void combineWith(SOSelectionLimits sOSelectionLimits);

    public void finalize() throws Throwable {
        try {
            destroy();
        } finally {
            super.finalize();
        }
    }

    public native RectF getBox();

    public native PointF getEnd();

    public native boolean getHasSelectionEnd();

    public native boolean getHasSelectionStart();

    public native boolean getIsActive();

    public native boolean getIsCaret();

    public native PointF getStart();

    public SOSelectionLimits() {
    }
}

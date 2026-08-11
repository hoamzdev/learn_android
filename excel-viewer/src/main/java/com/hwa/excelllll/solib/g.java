package com.hwa.excelllll.solib;

import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

/* JADX INFO: loaded from: classes.dex */
public class g extends SOSelectionLimits {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private Rect f4234a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private boolean f4235b;

    public g(PointF pointF, PointF pointF2, boolean z) {
        this.f4234a = null;
        this.f4234a = new Rect((int) pointF.x, (int) pointF.y, (int) pointF2.x, (int) pointF2.y);
        this.f4235b = z;
    }

    @Override // com.artifex.solib.SOSelectionLimits
    public RectF getBox() {
        Rect rect = this.f4234a;
        return new RectF(rect.left, rect.top, rect.right, rect.bottom);
    }

    @Override // com.artifex.solib.SOSelectionLimits
    public PointF getEnd() {
        Rect rect = this.f4234a;
        return new PointF(rect.right, rect.bottom);
    }

    @Override // com.artifex.solib.SOSelectionLimits
    public boolean getHasSelectionEnd() {
        return this.f4235b && this.f4234a != null;
    }

    @Override // com.artifex.solib.SOSelectionLimits
    public boolean getHasSelectionStart() {
        return this.f4235b && this.f4234a != null;
    }

    @Override // com.artifex.solib.SOSelectionLimits
    public boolean getIsActive() {
        return this.f4235b;
    }

    @Override // com.artifex.solib.SOSelectionLimits
    public boolean getIsCaret() {
        return false;
    }

    @Override // com.artifex.solib.SOSelectionLimits
    public PointF getStart() {
        Rect rect = this.f4234a;
        return new PointF(rect.left, rect.top);
    }

    @Override // com.artifex.solib.SOSelectionLimits
    public void finalize() throws Throwable {
    }

    @Override // com.artifex.solib.SOSelectionLimits
    public void combineWith(SOSelectionLimits sOSelectionLimits) {
    }

    public g(Rect rect, boolean z) {
        this.f4234a = null;
        this.f4234a = new Rect(rect);
        this.f4235b = z;
    }
}

package com.hwa.excelllll.solib.animation;

import android.graphics.PointF;

/* JADX INFO: loaded from: classes.dex */
public class SOAnimationScaleCommand extends SOAnimationRunningCommand {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public float f4196a;
    public float c;
    public float d;
    public float e;
    public PointF f;
    public int l;

    public SOAnimationScaleCommand(int i, int i2, boolean z, boolean z2, float f, float f2, float f3, float f4, float f5, float f6, PointF pointF, int i3) {
        super(i, i2, z, z2, f, f2);
        this.f4196a = f3;
        this.c = f4;
        this.d = f5;
        this.e = f6;
        this.f = pointF;
        this.l = i3;
    }

    @Override // com.artifex.solib.animation.SOAnimationRunningCommand, com.artifex.solib.animation.SOAnimationCommand
    public String toString() {
        return String.format("SOAnimationScaleCommand(%s (%.2f, %.2f) (%.2f, %.2f) (%.2f, %.2f) %d)", super.toString(), Float.valueOf(this.f4196a), Float.valueOf(this.c), Float.valueOf(this.d), Float.valueOf(this.e), Float.valueOf(this.f.x), Float.valueOf(this.f.y), Integer.valueOf(this.l));
    }
}

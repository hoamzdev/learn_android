package com.hwa.excelllll.solib.animation;

import android.graphics.PointF;

/* JADX INFO: loaded from: classes.dex */
public class SOAnimationRotateCommand extends SOAnimationRunningCommand {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public PointF f4195a;
    public float c;
    public float d;
    public int e;

    public SOAnimationRotateCommand(int i, int i2, boolean z, boolean z2, float f, float f2, PointF pointF, float f3, float f4, int i3) {
        super(i, i2, z, z2, f, f2);
        this.f4195a = pointF;
        this.c = f3;
        this.d = f4;
        this.e = i3;
    }

    @Override // com.artifex.solib.animation.SOAnimationRunningCommand, com.artifex.solib.animation.SOAnimationCommand
    public String toString() {
        return String.format("SOAnimationRotateCommand(%s (%.2f, %.2f), %.2f, %.2f, %d)", super.toString(), Float.valueOf(this.f4195a.x), Float.valueOf(this.f4195a.y), Float.valueOf(this.c), Float.valueOf(this.d), Integer.valueOf(this.e));
    }
}

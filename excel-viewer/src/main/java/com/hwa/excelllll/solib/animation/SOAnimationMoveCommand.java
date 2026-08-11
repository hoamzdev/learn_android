package com.hwa.excelllll.solib.animation;

import android.graphics.PointF;

/* JADX INFO: loaded from: classes.dex */
public class SOAnimationMoveCommand extends SOAnimationRunningCommand {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public PointF f4193a;
    public PointF c;
    public int d;

    public SOAnimationMoveCommand(int i, int i2, boolean z, boolean z2, float f, float f2, PointF pointF, PointF pointF2, int i3) {
        super(i, i2, z, z2, f, f2);
        this.f4193a = pointF;
        this.c = pointF2;
        this.d = i3;
    }

    @Override // com.artifex.solib.animation.SOAnimationRunningCommand, com.artifex.solib.animation.SOAnimationCommand
    public String toString() {
        return String.format("SOAnimationMoveCommand(%s (%.2f, %.2f) (%.2f, %.2f) %d)", super.toString(), Float.valueOf(this.f4193a.x), Float.valueOf(this.f4193a.y), Float.valueOf(this.c.x), Float.valueOf(this.c.y), Integer.valueOf(this.d));
    }
}

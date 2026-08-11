package com.hwa.excelllll.solib.animation;

import android.graphics.PointF;

/* JADX INFO: loaded from: classes.dex */
public class SOAnimationPlotCommand extends SOAnimationImmediateCommand {
    public PointF c;
    public int d;

    public SOAnimationPlotCommand(int i, float f, PointF pointF, int i2) {
        super(i, f);
        this.c = pointF;
        this.d = i2;
    }

    @Override // com.artifex.solib.animation.SOAnimationImmediateCommand, com.artifex.solib.animation.SOAnimationCommand
    public String toString() {
        return String.format("SOAnimationPlotCommand(%s (%.2f, %.2f) %d)", super.toString(), Float.valueOf(this.c.x), Float.valueOf(this.c.y), Integer.valueOf(this.d));
    }
}

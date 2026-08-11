package com.hwa.excelllll.solib.animation;

import android.graphics.PointF;

/* JADX INFO: loaded from: classes.dex */
public class SOAnimationSetPositionCommand extends SOAnimationImmediateCommand {
    public PointF c;

    public SOAnimationSetPositionCommand(int i, float f, PointF pointF) {
        super(i, f);
        this.c = pointF;
    }

    @Override // com.artifex.solib.animation.SOAnimationImmediateCommand, com.artifex.solib.animation.SOAnimationCommand
    public String toString() {
        return String.format("SOAnimationSetPositionCommand(%s (%.2f %.2f))", super.toString(), Float.valueOf(this.c.x), Float.valueOf(this.c.y));
    }
}

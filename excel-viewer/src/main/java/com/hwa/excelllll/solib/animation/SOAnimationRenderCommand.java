package com.hwa.excelllll.solib.animation;

import android.graphics.PointF;

/* JADX INFO: loaded from: classes.dex */
public class SOAnimationRenderCommand extends SOAnimationCommand {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f4194a;
    public float c;
    public PointF d;
    public float e;
    public float f;
    public float g;
    public float h;

    public SOAnimationRenderCommand(int i, int i2, float f, PointF pointF, float f2, float f3, float f4, float f5) {
        super(i);
        this.f4194a = i2;
        this.c = f;
        this.d = pointF;
        this.e = f2;
        this.f = f3;
        this.g = f4;
        this.h = f5;
    }

    @Override // com.artifex.solib.animation.SOAnimationCommand
    public String toString() {
        return String.format("SOAnimationRenderCommand(%s, %d, %.2f, (%.2f, %.2f), (%.2f, %.2f, %.2f, %.2f))", super.toString(), Integer.valueOf(this.f4194a), Float.valueOf(this.c), Float.valueOf(this.d.x), Float.valueOf(this.d.y), Float.valueOf(this.e), Float.valueOf(this.f), Float.valueOf(this.g), Float.valueOf(this.h));
    }
}

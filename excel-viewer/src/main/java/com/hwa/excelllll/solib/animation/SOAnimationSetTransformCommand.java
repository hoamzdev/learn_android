package com.hwa.excelllll.solib.animation;

/* JADX INFO: loaded from: classes.dex */
public class SOAnimationSetTransformCommand extends SOAnimationImmediateCommand {
    public float c;
    public float d;
    public float e;
    public float f;
    public float g;
    public float h;

    public SOAnimationSetTransformCommand(int i, float f, float f2, float f3, float f4, float f5, float f6, float f7) {
        super(i, f);
        this.c = f2;
        this.d = f3;
        this.e = f4;
        this.f = f5;
        this.g = f6;
        this.h = f7;
    }

    @Override // com.artifex.solib.animation.SOAnimationImmediateCommand, com.artifex.solib.animation.SOAnimationCommand
    public String toString() {
        return String.format("SOAnimationSetTransformCommand(%s (%.2f %.2f %.2f %.2f %.2f %.2f)", super.toString(), Float.valueOf(this.c), Float.valueOf(this.d), Float.valueOf(this.e), Float.valueOf(this.f), Float.valueOf(this.g), Float.valueOf(this.h));
    }
}

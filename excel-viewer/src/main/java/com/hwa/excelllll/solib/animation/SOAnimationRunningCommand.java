package com.hwa.excelllll.solib.animation;

/* JADX INFO: loaded from: classes.dex */
public abstract class SOAnimationRunningCommand extends SOAnimationCommand {
    public int g;
    public boolean h;
    public boolean i;
    public float j;
    public float k;

    public SOAnimationRunningCommand(int i, int i2, boolean z, boolean z2, float f, float f2) {
        super(i);
        this.g = i2;
        this.h = z;
        this.i = z2;
        this.j = f;
        this.k = f2;
    }

    @Override // com.artifex.solib.animation.SOAnimationCommand
    public String toString() {
        return String.format("SOAnimationRunningCommand(%d %d %b %b %.2f %.2f)", Integer.valueOf(this.f4190b), Integer.valueOf(this.g), Boolean.valueOf(this.h), Boolean.valueOf(this.i), Float.valueOf(this.j), Float.valueOf(this.k));
    }
}

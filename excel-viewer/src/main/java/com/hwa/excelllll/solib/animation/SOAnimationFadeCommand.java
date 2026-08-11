package com.hwa.excelllll.solib.animation;

/* JADX INFO: loaded from: classes.dex */
public class SOAnimationFadeCommand extends SOAnimationRunningCommand {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f4191a;
    public int c;
    public float d;
    public float e;
    public int f;

    public SOAnimationFadeCommand(int i, int i2, boolean z, boolean z2, float f, float f2, int i3, int i4, float f3, float f4, int i5) {
        super(i, i2, z, z2, f, f2);
        this.f4191a = i3;
        this.c = i4;
        this.d = f3;
        this.e = f4;
        this.f = i5;
    }

    @Override // com.artifex.solib.animation.SOAnimationRunningCommand, com.artifex.solib.animation.SOAnimationCommand
    public String toString() {
        return String.format("SOAnimationFadeCommand(%s %d %.2f %.2f %d)", super.toString(), Integer.valueOf(this.f4191a), Float.valueOf(this.d), Float.valueOf(this.e), Integer.valueOf(this.f));
    }
}

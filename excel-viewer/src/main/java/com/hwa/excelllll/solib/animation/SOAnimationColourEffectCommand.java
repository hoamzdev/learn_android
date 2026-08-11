package com.hwa.excelllll.solib.animation;

/* JADX INFO: loaded from: classes.dex */
public class SOAnimationColourEffectCommand extends SOAnimationRunningCommand {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f4189a;

    public SOAnimationColourEffectCommand(int i, int i2, boolean z, boolean z2, float f, float f2, int i3) {
        super(i, i2, z, z2, f, f2);
        this.f4189a = i3;
    }

    @Override // com.artifex.solib.animation.SOAnimationRunningCommand, com.artifex.solib.animation.SOAnimationCommand
    public String toString() {
        return String.format("SOAnimationColourEffectCommand(%s %d)", super.toString(), Integer.valueOf(this.f4189a));
    }
}

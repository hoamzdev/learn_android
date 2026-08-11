package com.hwa.excelllll.solib.animation;

/* JADX INFO: loaded from: classes.dex */
public class SOAnimationWaitForLayerCommand extends SOAnimationCommand {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f4198a;
    public int c;

    public SOAnimationWaitForLayerCommand(int i, int i2, int i3) {
        super(i);
        this.f4198a = i2;
        this.c = i3;
    }

    @Override // com.artifex.solib.animation.SOAnimationCommand
    public String toString() {
        return String.format("SOAnimationWaitForLayerCommand(%s %d %d)", super.toString(), Integer.valueOf(this.f4198a), Integer.valueOf(this.c));
    }
}

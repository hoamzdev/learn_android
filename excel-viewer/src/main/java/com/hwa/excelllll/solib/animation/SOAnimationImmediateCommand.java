package com.hwa.excelllll.solib.animation;

/* JADX INFO: loaded from: classes.dex */
public abstract class SOAnimationImmediateCommand extends SOAnimationCommand {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public float f4192a;

    public SOAnimationImmediateCommand(int i, float f) {
        super(i);
        this.f4192a = f;
    }

    @Override // com.artifex.solib.animation.SOAnimationCommand
    public String toString() {
        return String.format("SOAnimationImmediateCommand(%s %.2f)", super.toString(), Float.valueOf(this.f4192a));
    }
}

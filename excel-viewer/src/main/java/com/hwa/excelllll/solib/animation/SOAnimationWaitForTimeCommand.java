package com.hwa.excelllll.solib.animation;

/* JADX INFO: loaded from: classes.dex */
public class SOAnimationWaitForTimeCommand extends SOAnimationCommand {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public float f4199a;

    public SOAnimationWaitForTimeCommand(int i, float f) {
        super(i);
        this.f4199a = f;
    }

    @Override // com.artifex.solib.animation.SOAnimationCommand
    public String toString() {
        return String.format("SOAnimationWaitForTimeCommand(%s %.2f)", super.toString(), Float.valueOf(this.f4199a));
    }
}

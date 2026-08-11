package com.hwa.excelllll.solib.animation;

/* JADX INFO: loaded from: classes.dex */
public class SOAnimationWaitForEventCommand extends SOAnimationCommand {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public int f4197a;

    public SOAnimationWaitForEventCommand(int i, int i2) {
        super(i);
        this.f4197a = i2;
    }

    @Override // com.artifex.solib.animation.SOAnimationCommand
    public String toString() {
        return String.format("SOAnimationWaitForEventCommand(%s %d)", super.toString(), Integer.valueOf(this.f4197a));
    }
}

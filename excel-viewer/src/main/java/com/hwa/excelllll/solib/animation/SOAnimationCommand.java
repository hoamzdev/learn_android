package com.hwa.excelllll.solib.animation;

/* JADX INFO: loaded from: classes.dex */
public abstract class SOAnimationCommand {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public int f4190b;

    public SOAnimationCommand(int i) {
        this.f4190b = i;
    }

    public String toString() {
        return String.format("SOAnimationCommand(%d)", Integer.valueOf(this.f4190b));
    }
}

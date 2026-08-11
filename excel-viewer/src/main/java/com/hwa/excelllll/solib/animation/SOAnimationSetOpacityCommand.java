package com.hwa.excelllll.solib.animation;

/* JADX INFO: loaded from: classes.dex */
public class SOAnimationSetOpacityCommand extends SOAnimationImmediateCommand {
    public float c;

    public SOAnimationSetOpacityCommand(int i, float f, float f2) {
        super(i, f);
        this.c = f2;
    }

    @Override // com.artifex.solib.animation.SOAnimationImmediateCommand, com.artifex.solib.animation.SOAnimationCommand
    public String toString() {
        return String.format("SOAnimationSetOpacityCommand(%s %.2f)", super.toString(), Float.valueOf(this.c));
    }
}

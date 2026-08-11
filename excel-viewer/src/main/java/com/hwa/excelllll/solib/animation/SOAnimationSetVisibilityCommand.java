package com.hwa.excelllll.solib.animation;

/* JADX INFO: loaded from: classes.dex */
public class SOAnimationSetVisibilityCommand extends SOAnimationImmediateCommand {
    public boolean c;

    public SOAnimationSetVisibilityCommand(int i, float f, boolean z) {
        super(i, f);
        this.c = z;
    }

    @Override // com.artifex.solib.animation.SOAnimationImmediateCommand, com.artifex.solib.animation.SOAnimationCommand
    public String toString() {
        return "SOAnimationSetVisibilityCommand(" + super.toString() + " " + this.c + ")";
    }
}

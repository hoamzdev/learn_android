package com.hwa.excelllll.sonui.editor.a;

import android.view.animation.Animation;
import com.artifex.sonui.editor.SlideShowConductorView;

/* JADX INFO: loaded from: classes.dex */
public abstract class h extends Animation implements Animation.AnimationListener {
    SlideShowConductorView e;
    protected int c = 0;
    protected int d = 0;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private a f4930a = null;

    public interface a {
        void a();
    }

    public h(int i, boolean z, int i2, SlideShowConductorView slideShowConductorView) {
        setDuration(i2);
        setAnimationListener(this);
        setRepeatCount(i - 1);
        setRepeatMode(z ? 2 : 1);
        this.e = slideShowConductorView;
    }

    public void a() {
        cancel();
        this.e.setOpacity(this.c == 0 ? 1.0f : 0.0f);
    }

    public void b(int i) {
        this.c = i;
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationEnd(Animation animation) {
        a aVar = this.f4930a;
        if (aVar != null) {
            aVar.a();
            this.f4930a = null;
        }
        setAnimationListener(null);
    }

    public void a(int i) {
        this.d = i;
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationRepeat(Animation animation) {
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationStart(Animation animation) {
    }
}

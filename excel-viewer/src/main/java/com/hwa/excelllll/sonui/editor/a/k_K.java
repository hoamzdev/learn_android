package com.hwa.excelllll.sonui.editor.a;

import android.view.animation.Animation;
import android.view.animation.Transformation;
import com.artifex.sonui.editor.SlideShowConductorView;
import defpackage.m5;

/* JADX INFO: loaded from: classes.dex */
public abstract class k extends h {
    static final /* synthetic */ boolean h = true;
    protected int f;
    protected int g;

    public k(int i, boolean z, int i2, SlideShowConductorView slideShowConductorView) {
        super(i, z, i2, slideShowConductorView);
        this.f = 0;
        this.g = 0;
        SlideShowConductorView slideShowConductorView2 = this.e;
        if (slideShowConductorView2 != null) {
            this.f = slideShowConductorView2.getSize().y;
            this.g = this.e.getSize().x;
        }
    }

    public void a(float f) {
        if (h) {
            return;
        }
        m5.b();
    }

    @Override // android.view.animation.Animation
    public void applyTransformation(float f, Transformation transformation) {
        super.applyTransformation(f, transformation);
        a(f);
    }

    @Override // com.artifex.sonui.editor.a.h, android.view.animation.Animation.AnimationListener
    public void onAnimationEnd(Animation animation) {
        super.onAnimationEnd(animation);
        this.e.setClipPath(null);
        this.e = null;
    }
}

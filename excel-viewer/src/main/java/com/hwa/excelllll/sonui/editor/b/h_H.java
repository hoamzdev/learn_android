package com.hwa.excelllll.sonui.editor.b;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import com.artifex.sonui.editor.AnimatableView;
import defpackage.m5;

/* JADX INFO: loaded from: classes.dex */
public class h extends k {
    static final /* synthetic */ boolean h = true;
    protected AnimatableView c;
    protected AnimatableView d;
    protected String e;
    protected int f;
    protected int g;

    /* JADX WARN: Multi-variable type inference failed */
    public h(String str, View view, View view2, int i) {
        super(i);
        this.c = (AnimatableView) view;
        AnimatableView animatableView = (AnimatableView) view2;
        this.d = animatableView;
        this.e = str;
        this.f = animatableView.getMeasuredHeight();
        this.g = this.d.getMeasuredWidth();
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

    @Override // com.artifex.sonui.editor.b.k, android.view.animation.Animation.AnimationListener
    public void onAnimationEnd(Animation animation) {
        super.onAnimationEnd(animation);
        this.c.setClipPath(null);
        this.d.setClipPath(null);
        this.c = null;
        this.d = null;
    }
}

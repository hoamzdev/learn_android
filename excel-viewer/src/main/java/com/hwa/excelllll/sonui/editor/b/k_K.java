package com.hwa.excelllll.sonui.editor.b;

import android.view.animation.Animation;

/* JADX INFO: loaded from: classes.dex */
public class k extends Animation implements Animation.AnimationListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private a f4937a = null;

    public interface a {
        void a();
    }

    public k(int i) {
        setDuration(i);
        setAnimationListener(this);
    }

    public void a(a aVar) {
        this.f4937a = aVar;
    }

    public void onAnimationEnd(Animation animation) {
        a aVar = this.f4937a;
        if (aVar != null) {
            aVar.a();
            this.f4937a = null;
        }
        setAnimationListener(null);
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationRepeat(Animation animation) {
    }

    @Override // android.view.animation.Animation.AnimationListener
    public void onAnimationStart(Animation animation) {
    }
}

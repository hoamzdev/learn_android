package com.hwa.excelllll.sonui.editor.a;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.ColorMatrix;
import com.artifex.sonui.editor.SlideShowConductorView;

/* JADX INFO: loaded from: classes.dex */
public class e implements Animator.AnimatorListener, ValueAnimator.AnimatorUpdateListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private int f4926a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private final ValueAnimator f4927b;
    private SlideShowConductorView c;
    private ColorMatrix d = new ColorMatrix();
    private ColorMatrix e = new ColorMatrix();
    private ColorMatrix f = new ColorMatrix();
    private ColorMatrix g = new ColorMatrix();

    public e(int i, boolean z, int i2, int i3, SlideShowConductorView slideShowConductorView) {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.f4927b = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setDuration(i2);
        valueAnimatorOfFloat.setRepeatCount(i - 1);
        valueAnimatorOfFloat.setRepeatMode(z ? 2 : 1);
        this.f4926a = i3;
        this.c = slideShowConductorView;
        valueAnimatorOfFloat.addUpdateListener(this);
        valueAnimatorOfFloat.addListener(this);
    }

    public void a() {
        this.f4927b.end();
    }

    public boolean b() {
        return this.f4927b.isStarted();
    }

    public boolean c() {
        return this.f4927b.isRunning();
    }

    public void d() {
        this.f4927b.start();
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animator) {
        this.c.concatColorAdjustmentMatrix(this.d);
        this.f4927b.removeUpdateListener(this);
        this.f4927b.removeListener(this);
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        float animatedFraction;
        this.f.reset();
        this.e.reset();
        this.g.reset();
        this.d.reset();
        int i = this.f4926a;
        if (i == 0) {
            float animatedFraction2 = valueAnimator.getAnimatedFraction();
            float f = 1.0f - animatedFraction2;
            this.f.set(new float[]{f, 0.0f, animatedFraction2, 0.0f, 0.0f, animatedFraction2, f, 0.0f, 0.0f, 0.0f, 0.0f, animatedFraction2, f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
        } else if (i == 1) {
            float animatedFraction3 = valueAnimator.getAnimatedFraction();
            float f2 = 1.0f - animatedFraction3;
            this.f.set(new float[]{f2, animatedFraction3, 0.0f, 0.0f, 0.0f, 0.0f, f2, animatedFraction3, 0.0f, 0.0f, animatedFraction3, 0.0f, f2, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
        } else if (i != 2) {
            if (i == 3) {
                this.e.setSaturation(1.0f - (valueAnimator.getAnimatedFraction() * 0.125f));
                animatedFraction = 1.0f - (valueAnimator.getAnimatedFraction() * 0.25f);
            } else if (i == 4) {
                this.e.setSaturation(1.0f - (valueAnimator.getAnimatedFraction() * 0.7f));
            } else if (i == 5) {
                this.e.setSaturation((valueAnimator.getAnimatedFraction() * 0.125f) + 1.0f);
                animatedFraction = (valueAnimator.getAnimatedFraction() * 0.25f) + 1.0f;
            }
            this.g.setScale(animatedFraction, animatedFraction, animatedFraction, 1.0f);
        } else {
            float animatedFraction4 = valueAnimator.getAnimatedFraction();
            float f3 = 1.0f - animatedFraction4;
            float f4 = animatedFraction4 * 0.5f;
            this.e.setSaturation(2.0f);
            this.f.set(new float[]{f3, f4, f4, 0.0f, 0.0f, f4, f3, f4, 0.0f, 0.0f, f4, f4, f3, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
        }
        this.d.postConcat(this.f);
        this.d.postConcat(this.e);
        this.d.postConcat(this.g);
        this.c.applyColorAdjustmentMatrix(this.d);
        this.c.invalidate();
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animator) {
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationRepeat(Animator animator) {
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationStart(Animator animator) {
    }
}

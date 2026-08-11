package com.hwa.excelllll.customviews.smartrefresh.internal;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class ProgressDrawable extends PaintDrawable implements Animatable, ValueAnimator.AnimatorUpdateListener {

    /* JADX INFO: renamed from: b */
    public int f48595b;

    /* JADX INFO: renamed from: c */
    public int f48596c;

    /* JADX INFO: renamed from: d */
    public int f48597d;

    /* JADX INFO: renamed from: e */
    public ValueAnimator f48598e;

    /* JADX INFO: renamed from: f */
    public Path f48599f;

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        Path path = this.f48599f;
        Rect bounds = getBounds();
        int iWidth = bounds.width();
        int iHeight = bounds.height();
        float f = iWidth;
        float fMax = Math.max(1.0f, f / 22.0f);
        if (this.f48595b != iWidth || this.f48596c != iHeight) {
            path.reset();
            float f2 = f - fMax;
            float f3 = iHeight / 2.0f;
            Path.Direction direction = Path.Direction.CW;
            path.addCircle(f2, f3, fMax, direction);
            float f4 = f - (5.0f * fMax);
            path.addRect(f4, f3 - fMax, f2, f3 + fMax, direction);
            path.addCircle(f4, f3, fMax, direction);
            this.f48595b = iWidth;
            this.f48596c = iHeight;
        }
        canvas.save();
        float f5 = f / 2.0f;
        float f6 = iHeight / 2.0f;
        canvas.rotate(this.f48597d, f5, f6);
        for (int i = 0; i < 12; i++) {
            Paint paint = this.f48594a;
            paint.setAlpha((i + 5) * 17);
            canvas.rotate(30.0f, f5, f6);
            canvas.drawPath(path, paint);
        }
        canvas.restore();
    }

    @Override // android.graphics.drawable.Animatable
    public final boolean isRunning() {
        return this.f48598e.isRunning();
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.f48597d = (((Integer) valueAnimator.getAnimatedValue()).intValue() / 30) * 30;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Animatable
    public final void start() {
        ValueAnimator valueAnimator = this.f48598e;
        if (valueAnimator.isRunning()) {
            return;
        }
        valueAnimator.addUpdateListener(this);
        valueAnimator.start();
    }

    @Override // android.graphics.drawable.Animatable
    public final void stop() {
        ValueAnimator valueAnimator = this.f48598e;
        if (valueAnimator.isRunning()) {
            valueAnimator.removeAllListeners();
            valueAnimator.removeAllUpdateListeners();
            valueAnimator.cancel();
        }
    }
}

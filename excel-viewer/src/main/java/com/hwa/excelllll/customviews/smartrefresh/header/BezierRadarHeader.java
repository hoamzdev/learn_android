package com.hwa.excelllll.customviews.smartrefresh.header;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.xlsxreader.excelreader.customviews.smartrefresh.SmartRefreshLayout;
import com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshHeader;
import com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshLayout;
import com.xlsxreader.excelreader.customviews.smartrefresh.constant.RefreshState;
import com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalAbstract;
import com.xlsxreader.excelreader.customviews.smartrefresh.util.SmartUtil;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class BezierRadarHeader extends InternalAbstract implements RefreshHeader {

    /* JADX INFO: renamed from: d */
    public int f48517d;

    /* JADX INFO: renamed from: e */
    public int f48518e;

    /* JADX INFO: renamed from: f */
    public boolean f48519f;

    /* JADX INFO: renamed from: g */
    public boolean f48520g;

    /* JADX INFO: renamed from: h */
    public boolean f48521h;

    /* JADX INFO: renamed from: i */
    public boolean f48522i;

    /* JADX INFO: renamed from: j */
    public Path f48523j;

    /* JADX INFO: renamed from: k */
    public Paint f48524k;

    /* JADX INFO: renamed from: l */
    public int f48525l;

    /* JADX INFO: renamed from: m */
    public int f48526m;

    /* JADX INFO: renamed from: n */
    public int f48527n;

    /* JADX INFO: renamed from: o */
    public int f48528o;

    /* JADX INFO: renamed from: p */
    public float f48529p;

    /* JADX INFO: renamed from: q */
    public float f48530q;

    /* JADX INFO: renamed from: r */
    public float f48531r;

    /* JADX INFO: renamed from: s */
    public float f48532s;

    /* JADX INFO: renamed from: t */
    public int f48533t;

    /* JADX INFO: renamed from: u */
    public float f48534u;

    /* JADX INFO: renamed from: v */
    public float f48535v;

    /* JADX INFO: renamed from: w */
    public float f48536w;

    /* JADX INFO: renamed from: x */
    public AnimatorSet f48537x;

    /* JADX INFO: renamed from: y */
    public RectF f48538y;

    /* JADX INFO: renamed from: com.xlsxreader.excelreader.customviews.smartrefresh.header.BezierRadarHeader$1 */
    /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
    public static /* synthetic */ class C74381 {

        /* JADX INFO: renamed from: a */
        public static final /* synthetic */ int[] f48539a;

        static {
            int[] iArr = new int[RefreshState.values().length];
            f48539a = iArr;
            try {
                RefreshState refreshState = RefreshState.None;
                iArr[0] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                int[] iArr2 = f48539a;
                RefreshState refreshState2 = RefreshState.None;
                iArr2[1] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    /* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
    public class AnimatorUpdater implements ValueAnimator.AnimatorUpdateListener {

        /* JADX INFO: renamed from: a */
        public final byte f48540a;

        public AnimatorUpdater(byte b2) {
            this.f48540a = b2;
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            BezierRadarHeader bezierRadarHeader = BezierRadarHeader.this;
            byte b2 = this.f48540a;
            if (b2 == 0) {
                bezierRadarHeader.f48536w = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            } else if (1 == b2) {
                if (bezierRadarHeader.f48521h) {
                    valueAnimator.cancel();
                    return;
                }
                bezierRadarHeader.f48526m = ((Integer) valueAnimator.getAnimatedValue()).intValue() / 2;
            } else if (2 == b2) {
                bezierRadarHeader.f48529p = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            } else if (3 == b2) {
                bezierRadarHeader.f48532s = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            } else if (4 == b2) {
                bezierRadarHeader.f48533t = ((Integer) valueAnimator.getAnimatedValue()).intValue();
            }
            bezierRadarHeader.invalidate();
        }
    }

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalAbstract, com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshInternal
    /* JADX INFO: renamed from: a */
    public final void mo21900a(int i, int i2, float f) {
        this.f48527n = i;
        invalidate();
    }

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalAbstract, com.xlsxreader.excelreader.customviews.smartrefresh.listener.OnStateChangedListener
    /* JADX INFO: renamed from: b */
    public final void mo21908b(RefreshLayout refreshLayout, RefreshState refreshState, RefreshState refreshState2) {
        int iOrdinal = refreshState2.ordinal();
        if (iOrdinal == 0 || iOrdinal == 1) {
            this.f48529p = 1.0f;
            this.f48536w = 0.0f;
            this.f48532s = 0.0f;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        float f;
        float f2;
        Canvas canvas2 = canvas;
        Paint paint = this.f48524k;
        int width = getWidth();
        int height = isInEditMode() ? getHeight() : this.f48528o;
        Path path = this.f48523j;
        path.reset();
        float f3 = 0.0f;
        path.lineTo(0.0f, this.f48525l);
        int i = this.f48527n;
        float f4 = 2.0f;
        float f5 = i >= 0 ? i : width / 2.0f;
        int i2 = this.f48525l;
        float f6 = width;
        path.quadTo(f5, this.f48526m + i2, f6, i2);
        path.lineTo(f6, 0.0f);
        paint.setColor(this.f48518e);
        canvas2.drawPath(path, paint);
        if (this.f48529p > 0.0f) {
            paint.setColor(this.f48517d);
            float f7 = height;
            float f8 = f7 / SmartUtil.f48601b;
            float f9 = 1.0f;
            float f10 = (f6 * 1.0f) / 7.0f;
            float f11 = this.f48530q;
            float f12 = (f10 * f11) - (f11 > 1.0f ? ((f11 - 1.0f) * f10) / f11 : 0.0f);
            float f13 = f7 - (f11 > 1.0f ? (((f11 - 1.0f) * f7) / 2.0f) / f11 : 0.0f);
            int i3 = 0;
            while (i3 < 7) {
                int i4 = i3 + 1;
                int i5 = i3 - 3;
                float f14 = f4;
                float f15 = f9;
                paint.setAlpha((int) ((1.0d - (1.0d / Math.pow((((double) f8) / 800.0d) + 1.0d, 15.0d))) * ((double) (this.f48529p * 255.0f))));
                float f16 = (f15 - (f15 / ((f8 / 10.0f) + f15))) * this.f48531r;
                canvas2.drawCircle((i5 * f12) + ((f6 / f14) - (f16 / f14)), f13 / f14, f16, paint);
                i3 = i4;
                f3 = f3;
                f4 = f14;
                f9 = f15;
            }
            f = f3;
            f2 = f4;
            paint.setAlpha(255);
        } else {
            f = 0.0f;
            f2 = 2.0f;
        }
        RectF rectF = this.f48538y;
        if (this.f48537x != null || isInEditMode()) {
            float f17 = this.f48534u;
            float f18 = this.f48536w;
            float f19 = f17 * f18;
            float f20 = this.f48535v * f18;
            paint.setColor(this.f48517d);
            Paint.Style style = Paint.Style.FILL;
            paint.setStyle(style);
            float f21 = f6 / f2;
            float f22 = height / f2;
            canvas2.drawCircle(f21, f22, f19, paint);
            Paint.Style style2 = Paint.Style.STROKE;
            paint.setStyle(style2);
            float f23 = f19 + f20;
            canvas2.drawCircle(f21, f22, f23, paint);
            paint.setColor((this.f48518e & 16777215) | 1426063360);
            paint.setStyle(style);
            rectF.set(f21 - f19, f22 - f19, f21 + f19, f19 + f22);
            canvas2.drawArc(rectF, 270.0f, this.f48533t, true, paint);
            paint.setStyle(style2);
            rectF.set(f21 - f23, f22 - f23, f21 + f23, f22 + f23);
            canvas2 = canvas;
            canvas2.drawArc(rectF, 270.0f, this.f48533t, false, paint);
            paint.setStyle(style);
        }
        if (this.f48532s > f) {
            paint.setColor(this.f48517d);
            canvas2.drawCircle(f6 / f2, height / f2, this.f48532s, paint);
        }
        super.dispatchDraw(canvas);
    }

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalAbstract, com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshInternal
    /* JADX INFO: renamed from: f */
    public final void mo21903f(SmartRefreshLayout smartRefreshLayout, int i, int i2) {
        this.f48525l = i - 1;
        this.f48521h = false;
        int i3 = SmartUtil.f48604e;
        SmartUtil smartUtil = new SmartUtil(i3);
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        valueAnimatorOfFloat.setInterpolator(smartUtil);
        valueAnimatorOfFloat.addUpdateListener(new AnimatorUpdater((byte) 2));
        ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setInterpolator(smartUtil);
        valueAnimatorOfFloat2.addUpdateListener(new AnimatorUpdater((byte) 0));
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, 360);
        valueAnimatorOfInt.setDuration(720L);
        valueAnimatorOfInt.setRepeatCount(-1);
        valueAnimatorOfInt.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimatorOfInt.addUpdateListener(new AnimatorUpdater((byte) 4));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(valueAnimatorOfFloat, valueAnimatorOfFloat2, valueAnimatorOfInt);
        animatorSet.start();
        int i4 = this.f48526m;
        float f = i4;
        ValueAnimator valueAnimatorOfInt2 = ValueAnimator.ofInt(i4, 0, -((int) (0.8f * f)), 0, -((int) (f * 0.4f)), 0);
        valueAnimatorOfInt2.addUpdateListener(new AnimatorUpdater((byte) 1));
        valueAnimatorOfInt2.setInterpolator(new SmartUtil(i3));
        valueAnimatorOfInt2.setDuration(800L);
        valueAnimatorOfInt2.start();
        this.f48537x = animatorSet;
    }

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalAbstract, com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshInternal
    /* JADX INFO: renamed from: h */
    public final int mo21904h(RefreshLayout refreshLayout, boolean z) {
        AnimatorSet animatorSet = this.f48537x;
        if (animatorSet != null) {
            animatorSet.removeAllListeners();
            this.f48537x.end();
            this.f48537x = null;
        }
        int width = getWidth();
        int i = this.f48528o;
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.f48534u, (float) Math.sqrt((i * i) + (width * width)));
        valueAnimatorOfFloat.setDuration(400L);
        valueAnimatorOfFloat.addUpdateListener(new AnimatorUpdater((byte) 3));
        valueAnimatorOfFloat.start();
        return 400;
    }

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalAbstract, com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshInternal
    /* JADX INFO: renamed from: i */
    public final boolean mo21905i() {
        return this.f48522i;
    }

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalAbstract, com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshInternal
    /* JADX INFO: renamed from: j */
    public final void mo21906j(boolean z, float f, int i, int i2, int i3) {
        this.f48528o = i;
        if (z || this.f48521h) {
            this.f48521h = true;
            this.f48525l = Math.min(i2, i);
            this.f48526m = (int) (Math.max(0, i - i2) * 1.9f);
            this.f48530q = f;
            invalidate();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        AnimatorSet animatorSet = this.f48537x;
        if (animatorSet != null) {
            animatorSet.removeAllListeners();
            this.f48537x.end();
            this.f48537x = null;
        }
    }

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalAbstract, com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshInternal
    @Deprecated
    public void setPrimaryColors(int... iArr) {
        if (iArr.length > 0 && !this.f48519f) {
            this.f48518e = iArr[0];
            this.f48519f = false;
        }
        if (iArr.length <= 1 || this.f48520g) {
            return;
        }
        this.f48517d = iArr[1];
        this.f48520g = false;
    }
}

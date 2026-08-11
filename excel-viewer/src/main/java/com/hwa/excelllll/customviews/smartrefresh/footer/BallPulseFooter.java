package com.hwa.excelllll.customviews.smartrefresh.footer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.animation.AccelerateDecelerateInterpolator;
import androidx.core.graphics.ColorUtils;
import com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshFooter;
import com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshLayout;
import com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalAbstract;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class BallPulseFooter extends InternalAbstract implements RefreshFooter {

    /* JADX INFO: renamed from: d */
    public boolean f48505d;

    /* JADX INFO: renamed from: e */
    public boolean f48506e;

    /* JADX INFO: renamed from: f */
    public Paint f48507f;

    /* JADX INFO: renamed from: g */
    public int f48508g;

    /* JADX INFO: renamed from: h */
    public int f48509h;

    /* JADX INFO: renamed from: i */
    public float f48510i;

    /* JADX INFO: renamed from: j */
    public long f48511j;

    /* JADX INFO: renamed from: k */
    public boolean f48512k;

    /* JADX INFO: renamed from: l */
    public AccelerateDecelerateInterpolator f48513l;

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalAbstract, com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshInternal
    /* JADX INFO: renamed from: d */
    public final void mo21901d(RefreshLayout refreshLayout, int i, int i2) {
        if (this.f48512k) {
            return;
        }
        invalidate();
        this.f48512k = true;
        this.f48511j = System.currentTimeMillis();
        this.f48507f.setColor(this.f48509h);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        float fMin = Math.min(width, height);
        float f = this.f48510i;
        float f2 = 2.0f;
        float f3 = (fMin - (f * 2.0f)) / 6.0f;
        float f4 = f3 * 2.0f;
        float f5 = (width / 2.0f) - (f4 + f);
        float f6 = height / 2.0f;
        long jCurrentTimeMillis = System.currentTimeMillis();
        int i = 0;
        while (i < 3) {
            int i2 = i + 1;
            long j = (jCurrentTimeMillis - this.f48511j) - ((long) (i2 * 120));
            float interpolation = this.f48513l.getInterpolation(j > 0 ? (j % 750) / 750.0f : 0.0f);
            canvas.save();
            float f7 = i;
            canvas.translate((f7 * f) + (f4 * f7) + f5, f6);
            float f8 = f2;
            float f9 = f4;
            if (interpolation < 0.5d) {
                float f10 = 1.0f - ((interpolation * f8) * 0.7f);
                canvas.scale(f10, f10);
            } else {
                float f11 = ((interpolation * f8) * 0.7f) - 0.4f;
                canvas.scale(f11, f11);
            }
            canvas.drawCircle(0.0f, 0.0f, f3, this.f48507f);
            canvas.restore();
            f2 = f8;
            f4 = f9;
            i = i2;
        }
        super.dispatchDraw(canvas);
        if (this.f48512k) {
            invalidate();
        }
    }

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalAbstract, com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshInternal
    /* JADX INFO: renamed from: h */
    public final int mo21904h(RefreshLayout refreshLayout, boolean z) {
        this.f48512k = false;
        this.f48511j = 0L;
        this.f48507f.setColor(this.f48508g);
        return 0;
    }

    @Override // com.xlsxreader.excelreader.customviews.smartrefresh.internal.InternalAbstract, com.xlsxreader.excelreader.customviews.smartrefresh.api.RefreshInternal
    @Deprecated
    public void setPrimaryColors(int... iArr) {
        Paint paint = this.f48507f;
        if (!this.f48506e && iArr.length > 1) {
            int i = iArr[0];
            this.f48509h = i;
            this.f48506e = true;
            if (this.f48512k) {
                paint.setColor(i);
            }
            this.f48506e = false;
        }
        if (this.f48505d) {
            return;
        }
        if (iArr.length > 1) {
            int i2 = iArr[1];
            this.f48508g = i2;
            this.f48505d = true;
            if (!this.f48512k) {
                paint.setColor(i2);
            }
        } else if (iArr.length > 0) {
            int iM2300c = ColorUtils.m2300c(-1711276033, iArr[0]);
            this.f48508g = iM2300c;
            this.f48505d = true;
            if (!this.f48512k) {
                paint.setColor(iM2300c);
            }
        }
        this.f48505d = false;
    }
}

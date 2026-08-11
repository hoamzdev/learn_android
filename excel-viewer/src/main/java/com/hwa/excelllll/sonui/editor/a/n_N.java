package com.hwa.excelllll.sonui.editor.a;

import android.graphics.Path;
import android.graphics.RectF;
import com.artifex.sonui.editor.SlideShowConductorView;

/* JADX INFO: loaded from: classes.dex */
public class n extends k {
    public n(int i, boolean z, int i2, SlideShowConductorView slideShowConductorView) {
        super(i, z, i2, slideShowConductorView);
    }

    @Override // com.artifex.sonui.editor.a.k
    public void a(float f) {
        if (this.c == 1) {
            f = 1.0f - f;
        }
        float f2 = f * 180.0f;
        int i = this.f;
        int i2 = this.g;
        int iSqrt = (int) Math.sqrt((i2 * i2) + (i * i));
        int i3 = this.g;
        int i4 = this.f;
        RectF rectF = new RectF((i3 / 2) - iSqrt, (i4 / 2) - iSqrt, (i3 / 2) + iSqrt, (i4 / 2) + iSqrt);
        Path path = new Path();
        path.moveTo(rectF.centerX(), rectF.centerY());
        path.arcTo(rectF, ((-90.0f) - f2) + 0.01f, (f2 * 2.0f) - 0.1f);
        path.lineTo(rectF.centerX(), rectF.centerY());
        path.close();
        SlideShowConductorView slideShowConductorView = this.e;
        if (slideShowConductorView != null) {
            slideShowConductorView.setClipPath(path);
            this.e.invalidate();
        }
    }
}

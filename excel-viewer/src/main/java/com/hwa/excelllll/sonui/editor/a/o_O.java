package com.hwa.excelllll.sonui.editor.a;

import android.graphics.Path;
import android.graphics.RectF;
import com.artifex.sonui.editor.SlideShowConductorView;

/* JADX INFO: loaded from: classes.dex */
public class o extends k {
    public o(int i, boolean z, int i2, SlideShowConductorView slideShowConductorView) {
        super(i, z, i2, slideShowConductorView);
    }

    /* JADX WARN: Code duplicated, block: B:14:0x004d A[PHI: r3
  0x004d: PHI (r3v5 int) = (r3v4 int), (r3v6 int), (r3v7 int) binds: [B:6:0x0040, B:10:0x0047, B:12:0x004a] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.artifex.sonui.editor.a.k
    public void a(float f) {
        Path path = new Path();
        if (this.c == 1) {
            path.addRect(0.0f, 0.0f, this.g, this.f, Path.Direction.CCW);
        }
        int i = this.f;
        int i2 = this.g;
        int iSqrt = (int) Math.sqrt((i2 * i2) + (i * i));
        int i3 = this.g;
        int i4 = this.f;
        RectF rectF = new RectF((i3 / 2) - iSqrt, (i4 / 2) - iSqrt, (i3 / 2) + iSqrt, (i4 / 2) + iSqrt);
        int i5 = this.d;
        int i6 = 8;
        if (i5 == 8) {
            a(i6, path, rectF, f);
        } else if (i5 == 1) {
            a(1, path, rectF, f);
        } else if (i5 != 2) {
            i6 = 3;
            if (i5 != 3) {
                i6 = 4;
                if (i5 == 4) {
                    a(i6, path, rectF, f);
                }
            } else {
                a(i6, path, rectF, f);
            }
        } else {
            a(2, path, rectF, f);
        }
        SlideShowConductorView slideShowConductorView = this.e;
        if (slideShowConductorView != null) {
            slideShowConductorView.setClipPath(path);
            this.e.invalidate();
        }
    }

    private void a(int i, Path path, RectF rectF, float f) {
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = 360 / i;
            path.moveTo(rectF.centerX(), rectF.centerY());
            path.arcTo(rectF, (i2 * i3) - 90, (i3 * f) - 0.05f);
            path.lineTo(rectF.centerX(), rectF.centerY());
        }
    }
}

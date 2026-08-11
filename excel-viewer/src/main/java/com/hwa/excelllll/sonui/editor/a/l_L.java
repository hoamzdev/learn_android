package com.hwa.excelllll.sonui.editor.a;

import android.graphics.Path;
import com.artifex.sonui.editor.SlideShowConductorView;

/* JADX INFO: loaded from: classes.dex */
public class l extends k {
    public l(int i, boolean z, int i2, SlideShowConductorView slideShowConductorView) {
        super(i, z, i2, slideShowConductorView);
        this.d = 21;
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0085  */
    /* JADX WARN: Code duplicated, block: B:25:? A[RETURN, SYNTHETIC] */
    @Override // com.artifex.sonui.editor.a.k
    public void a(float f) {
        float f2;
        float f3;
        float f4;
        float f5;
        int i;
        SlideShowConductorView slideShowConductorView;
        Path.Direction direction = Path.Direction.CW;
        Path path = new Path();
        if (this.c == 0) {
            path.addRect(0.0f, 0.0f, this.g, this.f, Path.Direction.CCW);
        }
        int i2 = this.f;
        int i3 = (int) (i2 * f);
        int i4 = this.g;
        int i5 = (int) (i4 * f);
        int i6 = this.d;
        float f6 = 0.0f;
        if (i6 != 21) {
            if (i6 != 26) {
                if (i6 == 37) {
                    int i7 = i5 / 2;
                    path.addRect(0.0f, 0.0f, (i4 / 2) - i7, i2, direction);
                    int i8 = this.g;
                    f3 = (i8 / 2) + i7;
                    f2 = i8;
                } else if (i6 == 42) {
                    int i9 = i3 / 2;
                    path.addRect(0.0f, 0.0f, i4, (i2 / 2) - i9, direction);
                    i = this.f;
                    f4 = (i / 2) + i9;
                    f5 = this.g;
                }
                slideShowConductorView = this.e;
                if (slideShowConductorView != null) {
                    slideShowConductorView.setClipPath(path);
                    this.e.postInvalidate();
                }
            }
            int i10 = i3 / 2;
            f4 = i10;
            i = i2 - i10;
            f5 = i4;
            path.addRect(f6, f4, f5, i, direction);
            slideShowConductorView = this.e;
            if (slideShowConductorView != null) {
                slideShowConductorView.setClipPath(path);
                this.e.postInvalidate();
            }
        }
        int i11 = i5 / 2;
        f2 = i4 - i11;
        f3 = i11;
        f6 = f3;
        f4 = 0.0f;
        f5 = f2;
        i = this.f;
        path.addRect(f6, f4, f5, i, direction);
        slideShowConductorView = this.e;
        if (slideShowConductorView != null) {
            slideShowConductorView.setClipPath(path);
            this.e.postInvalidate();
        }
    }
}

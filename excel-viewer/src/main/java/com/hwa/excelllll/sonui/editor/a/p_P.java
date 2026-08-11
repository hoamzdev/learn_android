package com.hwa.excelllll.sonui.editor.a;

import android.graphics.Path;
import com.artifex.sonui.editor.SlideShowConductorView;

/* JADX INFO: loaded from: classes.dex */
public class p extends k {
    public p(int i, boolean z, int i2, SlideShowConductorView slideShowConductorView) {
        super(i, z, i2, slideShowConductorView);
        this.d = 1;
    }

    /* JADX WARN: Code duplicated, block: B:23:0x005e  */
    /* JADX WARN: Code duplicated, block: B:25:? A[RETURN, SYNTHETIC] */
    @Override // com.artifex.sonui.editor.a.k
    public void a(float f) {
        float f2;
        SlideShowConductorView slideShowConductorView;
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        int i = (int) (this.f * f);
        int i2 = (int) (this.g * f);
        Path path = new Path();
        if (this.c == 1) {
            path.addRect(0.0f, 0.0f, this.g, this.f, Path.Direction.CCW);
        }
        int i3 = this.d;
        float f8 = 0.0f;
        if (i3 != 4) {
            if (i3 == 8) {
                f7 = 0.0f;
                f3 = i2;
                f4 = this.f;
                f5 = f8;
                f6 = f7;
                path.addRect(f6, f5, f3, f4, Path.Direction.CW);
            } else if (i3 == 1) {
                f3 = this.g;
                f4 = i;
                f6 = 0.0f;
                f5 = 0.0f;
                path.addRect(f6, f5, f3, f4, Path.Direction.CW);
            } else if (i3 == 2) {
                f2 = this.g - i2;
            }
            slideShowConductorView = this.e;
            if (slideShowConductorView != null) {
                slideShowConductorView.setClipPath(path);
                this.e.postInvalidate();
            }
        }
        f8 = this.f - i;
        f2 = 0.0f;
        f7 = f2;
        i2 = this.g;
        f3 = i2;
        f4 = this.f;
        f5 = f8;
        f6 = f7;
        path.addRect(f6, f5, f3, f4, Path.Direction.CW);
        slideShowConductorView = this.e;
        if (slideShowConductorView != null) {
            slideShowConductorView.setClipPath(path);
            this.e.postInvalidate();
        }
    }
}

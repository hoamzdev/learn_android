package com.hwa.excelllll.sonui.editor.a;

import android.graphics.Path;
import com.artifex.sonui.editor.SlideShowConductorView;

/* JADX INFO: loaded from: classes.dex */
public class c extends k {
    public c(int i, boolean z, int i2, SlideShowConductorView slideShowConductorView) {
        super(i, z, i2, slideShowConductorView);
        this.d = 10;
    }

    @Override // com.artifex.sonui.editor.a.k
    public void a(float f) {
        int i;
        int i2;
        int i3;
        int i4;
        Path path = new Path();
        if (this.c == 1) {
            f = 1.0f - f;
        }
        int i5 = this.g / 20;
        int i6 = this.f / 20;
        for (int i7 = 0; i7 < 21; i7++) {
            for (int i8 = 0; i8 < 21; i8++) {
                int i9 = this.d;
                if (i9 == 10) {
                    i = i8 * i6;
                    i2 = i7 * 2 * i5;
                    if (i8 % 2 != 0) {
                        i2 -= i5;
                    }
                    i3 = (int) ((i5 * f * 2.0f) + i2);
                    i4 = i6 + i;
                } else if (i9 == 5) {
                    i2 = i8 * i5;
                    i = i7 * 2 * i6;
                    if (i8 % 2 != 0) {
                        i -= i6;
                    }
                    i4 = (int) ((i6 * f * 2.0f) + i);
                    i3 = i2 + i5;
                } else {
                    i = 0;
                    i2 = 0;
                    i3 = 0;
                    i4 = 0;
                }
                path.addRect(i2, i, i3, i4, Path.Direction.CW);
            }
        }
        SlideShowConductorView slideShowConductorView = this.e;
        if (slideShowConductorView != null) {
            slideShowConductorView.setClipPath(path);
            this.e.invalidate();
        }
    }
}

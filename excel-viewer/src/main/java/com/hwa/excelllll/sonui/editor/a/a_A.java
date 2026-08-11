package com.hwa.excelllll.sonui.editor.a;

import android.graphics.Path;
import com.artifex.sonui.editor.SlideShowConductorView;

/* JADX INFO: loaded from: classes.dex */
public class a extends k {
    public a(int i, boolean z, int i2, SlideShowConductorView slideShowConductorView) {
        super(i, z, i2, slideShowConductorView);
        this.d = 5;
    }

    @Override // com.artifex.sonui.editor.a.k
    public void a(float f) {
        float f2;
        float f3;
        float f4;
        float f5;
        Path path = new Path();
        int i = this.g / 10;
        int i2 = this.f / 10;
        if (this.c == 1) {
            f = 1.0f - f;
        }
        for (int i3 = 0; i3 < 10; i3++) {
            int i4 = this.d;
            if (i4 == 5) {
                f4 = i3 * i;
                f2 = this.f;
                f5 = (i * f) + f4;
                f3 = 0.0f;
            } else {
                if (i4 == 10) {
                    float f6 = i3 * i2;
                    f2 = (i2 * f) + f6;
                    f3 = f6;
                    f4 = 0.0f;
                    f5 = this.g;
                }
            }
            path.addRect(f4, f3, f5, f2, Path.Direction.CW);
        }
        SlideShowConductorView slideShowConductorView = this.e;
        if (slideShowConductorView != null) {
            slideShowConductorView.setClipPath(path);
            this.e.invalidate();
        }
    }
}

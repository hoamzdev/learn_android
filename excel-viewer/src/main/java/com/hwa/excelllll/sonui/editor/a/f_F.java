package com.hwa.excelllll.sonui.editor.a;

import android.graphics.Path;
import com.artifex.sonui.editor.SlideShowConductorView;

/* JADX INFO: loaded from: classes.dex */
public class f extends k {
    public f(int i, boolean z, int i2, SlideShowConductorView slideShowConductorView) {
        super(i, z, i2, slideShowConductorView);
        this.d = 16;
    }

    @Override // com.artifex.sonui.editor.a.k
    public void a(float f) {
        Path path = new Path();
        int i = this.d;
        if (i == 16) {
            f = 1.0f - f;
        }
        int i2 = this.c;
        if ((i2 == 1 && i == 32) || (i2 == 0 && i == 16)) {
            path.addRect(0.0f, 0.0f, this.g, this.f, Path.Direction.CCW);
        }
        int i3 = this.g;
        path.moveTo((i3 / 2) - (i3 * f), this.f / 2);
        float f2 = this.g / 2;
        int i4 = this.f;
        path.lineTo(f2, (i4 / 2) - (i4 * f));
        int i5 = this.g;
        path.lineTo((i5 * f) + (i5 / 2), this.f / 2);
        float f3 = this.g / 2;
        int i6 = this.f;
        path.lineTo(f3, (i6 * f) + (i6 / 2));
        path.close();
        SlideShowConductorView slideShowConductorView = this.e;
        if (slideShowConductorView != null) {
            slideShowConductorView.setClipPath(path);
            this.e.invalidate();
        }
    }
}

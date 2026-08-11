package com.hwa.excelllll.sonui.editor.a;

import android.graphics.Path;
import com.artifex.sonui.editor.SlideShowConductorView;

/* JADX INFO: loaded from: classes.dex */
public class m extends k {
    public m(int i, boolean z, int i2, SlideShowConductorView slideShowConductorView) {
        super(i, z, i2, slideShowConductorView);
        this.d = 3;
    }

    /* JADX WARN: Code duplicated, block: B:21:0x00b7  */
    /* JADX WARN: Code duplicated, block: B:23:? A[RETURN, SYNTHETIC] */
    @Override // com.artifex.sonui.editor.a.k
    public void a(float f) {
        SlideShowConductorView slideShowConductorView;
        Path path = new Path();
        if (this.c == 1) {
            f = 1.0f - f;
        }
        int i = this.d;
        if (i == 3) {
            path.moveTo(0.0f, this.f);
            int i2 = this.f;
            path.lineTo(0.0f, i2 - ((i2 * 2) * f));
            path.lineTo(this.g * 2 * f, this.f);
            path.lineTo(0.0f, this.f);
        } else if (i == 6) {
            path.moveTo(0.0f, 0.0f);
            path.lineTo(0.0f, this.f * 2 * f);
            path.lineTo(this.g * 2 * f, 0.0f);
            path.lineTo(0.0f, 0.0f);
        } else {
            if (i != 9) {
                if (i == 12) {
                    path.moveTo(this.g, 0.0f);
                    path.lineTo(this.g, this.f * 2 * f);
                    int i3 = this.g;
                    path.lineTo(i3 - ((i3 * 2) * f), 0.0f);
                    path.lineTo(this.g, 0.0f);
                }
                slideShowConductorView = this.e;
                if (slideShowConductorView != null) {
                    slideShowConductorView.setClipPath(path);
                    this.e.postInvalidate();
                }
            }
            path.moveTo(this.g, this.f);
            float f2 = this.g;
            int i4 = this.f;
            path.lineTo(f2, i4 - ((i4 * 2) * f));
            int i5 = this.g;
            path.lineTo(i5 - ((i5 * 2) * f), this.f);
            path.lineTo(this.g, this.f);
        }
        path.close();
        slideShowConductorView = this.e;
        if (slideShowConductorView != null) {
            slideShowConductorView.setClipPath(path);
            this.e.postInvalidate();
        }
    }
}

package com.hwa.excelllll.sonui.editor.a;

import android.graphics.Path;
import com.artifex.sonui.editor.SlideShowConductorView;

/* JADX INFO: loaded from: classes.dex */
public class b extends k {
    public b(int i, boolean z, int i2, SlideShowConductorView slideShowConductorView) {
        super(i, z, i2, slideShowConductorView);
        this.d = 16;
    }

    @Override // com.artifex.sonui.editor.a.k
    public void a(float f) {
        if (this.d == 16) {
            f = 1.0f - f;
        }
        Path path = new Path();
        int i = this.c;
        if ((i == 1 && this.d == 32) || (i == 0 && this.d == 16)) {
            path.addRect(0.0f, 0.0f, this.g, this.f, Path.Direction.CCW);
        }
        int i2 = this.g;
        int i3 = this.f;
        path.addRect((i2 / 2) - ((i2 / 2) * f), (i3 / 2) - ((i3 / 2) * f), (i2 / 2) + ((i2 / 2) * f), ((i3 / 2) * f) + (i3 / 2), Path.Direction.CW);
        SlideShowConductorView slideShowConductorView = this.e;
        if (slideShowConductorView != null) {
            slideShowConductorView.setClipPath(path);
            this.e.invalidate();
        }
    }
}

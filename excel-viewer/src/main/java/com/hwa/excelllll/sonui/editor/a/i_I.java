package com.hwa.excelllll.sonui.editor.a;

import android.graphics.Path;
import com.artifex.sonui.editor.SlideShowConductorView;

/* JADX INFO: loaded from: classes.dex */
public class i extends k {
    public i(int i, boolean z, int i2, SlideShowConductorView slideShowConductorView) {
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
        int i2 = this.f;
        Path.Direction direction = Path.Direction.CW;
        path.addRect(0.0f, (i2 / 2) - ((i2 / 2) * f), this.g, ((i2 / 2) * f) + (i2 / 2), direction);
        int i3 = this.g;
        path.addRect((i3 / 2) - ((i3 / 2) * f), 0.0f, ((i3 / 2) * f) + (i3 / 2), this.f, direction);
        int i4 = this.g;
        int i5 = this.f;
        path.addRect((i4 / 2) - ((i4 / 2) * f), (i5 / 2) - ((i5 / 2) * f), (i4 / 2) + ((i4 / 2) * f), ((i5 / 2) * f) + (i5 / 2), Path.Direction.CCW);
        SlideShowConductorView slideShowConductorView = this.e;
        if (slideShowConductorView != null) {
            slideShowConductorView.setClipPath(path);
            this.e.invalidate();
        }
    }
}

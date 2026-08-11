package com.hwa.excelllll.sonui.editor.a;

import android.graphics.Path;
import com.artifex.sonui.editor.SlideShowConductorView;
import java.util.Random;

/* JADX INFO: loaded from: classes.dex */
public class g extends k {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    Random f4928a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    Path f4929b;

    public g(int i, boolean z, int i2, SlideShowConductorView slideShowConductorView) {
        super(i, z, i2, slideShowConductorView);
        this.f4928a = new Random();
        this.f4929b = null;
    }

    private void b() {
        Path path = new Path();
        this.f4929b = path;
        if (this.c == 1) {
            path.addRect(0.0f, 0.0f, this.g, this.f, Path.Direction.CW);
        }
    }

    @Override // com.artifex.sonui.editor.a.k
    public void a(float f) {
        if (this.f4929b == null) {
            b();
        }
        int duration = (int) (f * getDuration());
        for (int i = 0; i < 40; i++) {
            for (int i2 = 0; i2 < 40; i2++) {
                if (this.f4928a.nextInt((int) getDuration()) <= duration) {
                    int i3 = this.g;
                    float f2 = (i3 * i2) / 40;
                    float f3 = ((i2 + 1) * i3) / 40;
                    int i4 = this.f;
                    this.f4929b.addRect(f2, (i4 * i) / 40, f3, ((i + 1) * i4) / 40, this.c == 1 ? Path.Direction.CCW : Path.Direction.CW);
                }
            }
        }
        SlideShowConductorView slideShowConductorView = this.e;
        if (slideShowConductorView != null) {
            slideShowConductorView.setClipPath(this.f4929b);
            this.e.invalidate();
        }
    }
}

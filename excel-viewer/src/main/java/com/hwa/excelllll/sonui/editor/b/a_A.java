package com.hwa.excelllll.sonui.editor.b;

import android.graphics.Path;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class a extends h {
    public a(String str, View view, View view2, int i) {
        super(str, view, view2, i);
    }

    @Override // com.artifex.sonui.editor.b.h
    public void a(float f) {
        float f2;
        float f3;
        float f4;
        Path path = new Path();
        int i = this.g / 10;
        int i2 = this.f / 10;
        for (int i3 = 0; i3 < 10; i3++) {
            float f5 = 0.0f;
            if (this.e.equals("horz")) {
                f4 = i3 * i;
                f3 = this.f;
                f2 = (i * f) + f4;
            } else {
                if (this.e.equals("vert")) {
                    float f6 = i3 * i2;
                    f2 = this.g;
                    f3 = (i2 * f) + f6;
                    f5 = f6;
                    f4 = 0.0f;
                }
            }
            path.addRect(f4, f5, f2, f3, Path.Direction.CW);
        }
        this.d.setClipPath(path);
        this.d.invalidate();
    }
}

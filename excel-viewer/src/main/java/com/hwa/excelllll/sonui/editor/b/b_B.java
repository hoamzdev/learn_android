package com.hwa.excelllll.sonui.editor.b;

import android.graphics.Path;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class b extends h {
    public b(String str, View view, View view2, int i) {
        super(str, view, view2, i);
    }

    @Override // com.artifex.sonui.editor.b.h
    public void a(float f) {
        int i;
        int i2;
        int i3;
        int i4;
        Path path = new Path();
        int i5 = this.g / 20;
        int i6 = this.f / 20;
        for (int i7 = 0; i7 < 21; i7++) {
            for (int i8 = 0; i8 < 21; i8++) {
                if (this.e.equals("horz")) {
                    i = i8 * i6;
                    i3 = i7 * 2 * i5;
                    if (i8 % 2 != 0) {
                        i3 -= i5;
                    }
                    i2 = (int) ((i5 * f * 2.0f) + i3);
                    i4 = i6 + i;
                } else if (this.e.equals("vert")) {
                    i3 = i8 * i5;
                    i = i7 * 2 * i6;
                    if (i8 % 2 != 0) {
                        i -= i6;
                    }
                    i4 = (int) ((i6 * f * 2.0f) + i);
                    i2 = i3 + i5;
                } else {
                    i = 0;
                    i2 = 0;
                    i3 = 0;
                    i4 = 0;
                }
                path.addRect(i3, i, i2, i4, Path.Direction.CW);
            }
        }
        this.d.setClipPath(path);
        this.d.invalidate();
    }
}

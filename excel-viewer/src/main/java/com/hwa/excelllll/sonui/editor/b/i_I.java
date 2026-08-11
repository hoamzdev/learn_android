package com.hwa.excelllll.sonui.editor.b;

import android.graphics.Path;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class i extends h {
    public i(String str, View view, View view2, int i) {
        super(str, view, view2, i);
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0058  */
    @Override // com.artifex.sonui.editor.b.h
    public void a(float f) {
        byte b2;
        float f2;
        int i;
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        Path.Direction direction = Path.Direction.CW;
        int i2 = (int) (this.f * f);
        int i3 = (int) (this.g * f);
        Path path = new Path();
        String str = this.e;
        int iHashCode = str.hashCode();
        if (iHashCode != -1211507980) {
            if (iHashCode != -819940842) {
                if (iHashCode != 351643773) {
                    if (iHashCode == 1097964383 && str.equals("horzout")) {
                        b2 = 1;
                    } else {
                        b2 = -1;
                    }
                } else if (str.equals("vertout")) {
                    b2 = 3;
                } else {
                    b2 = -1;
                }
            } else if (str.equals("vertin")) {
                b2 = 2;
            } else {
                b2 = -1;
            }
        } else if (str.equals("horzin")) {
            b2 = 0;
        } else {
            b2 = -1;
        }
        float f8 = 0.0f;
        if (b2 != 0) {
            if (b2 != 1) {
                if (b2 == 2) {
                    int i4 = i3 / 2;
                    float f9 = i4;
                    f3 = this.g - i4;
                    i = this.f;
                    f8 = f9;
                    f2 = 0.0f;
                } else if (b2 == 3) {
                    int i5 = i3 / 2;
                    path.addRect(0.0f, 0.0f, (this.g / 2) - i5, this.f, direction);
                    f8 = (this.g / 2) + i5;
                    f7 = 0.0f;
                }
                this.c.setClipPath(path);
                this.c.postInvalidate();
            }
            int i6 = i2 / 2;
            path.addRect(0.0f, 0.0f, this.g, (this.f / 2) - i6, direction);
            f7 = (this.f / 2) + i6;
            f4 = this.g;
            f5 = this.f;
            f6 = f7;
            path.addRect(f8, f6, f4, f5, direction);
            this.c.setClipPath(path);
            this.c.postInvalidate();
        }
        int i7 = i2 / 2;
        f2 = i7;
        float f10 = this.g;
        i = this.f - i7;
        f3 = f10;
        f4 = f3;
        f5 = i;
        f6 = f2;
        path.addRect(f8, f6, f4, f5, direction);
        this.c.setClipPath(path);
        this.c.postInvalidate();
    }
}

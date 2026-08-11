package com.hwa.excelllll.sonui.editor.b;

import android.graphics.Path;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class n extends h {
    public n(String str, View view, View view2, int i) {
        super(str, view, view2, i);
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0058  */
    @Override // com.artifex.sonui.editor.b.h
    public void a(float f) {
        byte b2;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        Path path;
        float f8;
        Path.Direction direction = Path.Direction.CW;
        int i = (int) (this.f * f);
        int i2 = (int) (this.g * f);
        Path path2 = new Path();
        Path path3 = new Path();
        String str = this.e;
        int iHashCode = str.hashCode();
        if (iHashCode != 100) {
            if (iHashCode != 108) {
                if (iHashCode != 114) {
                    if (iHashCode == 117 && str.equals("u")) {
                        b2 = 1;
                    } else {
                        b2 = -1;
                    }
                } else if (str.equals("r")) {
                    b2 = 3;
                } else {
                    b2 = -1;
                }
            } else if (str.equals("l")) {
                b2 = 2;
            } else {
                b2 = -1;
            }
        } else if (str.equals("d")) {
            b2 = 0;
        } else {
            b2 = -1;
        }
        float f9 = 0.0f;
        if (b2 != 0) {
            if (b2 != 1) {
                if (b2 == 2) {
                    path2.addRect(0.0f, 0.0f, this.g - i2, this.f, direction);
                    f9 = this.g - i2;
                    f8 = 0.0f;
                } else if (b2 != 3) {
                    path2 = path2;
                    path = path3;
                } else {
                    f3 = i2;
                    path2.addRect(f3, 0.0f, this.g, this.f, direction);
                    f2 = this.f;
                }
                this.c.setClipPath(path2);
                this.c.postInvalidate();
                this.d.setClipPath(path);
                this.d.postInvalidate();
            }
            path2.addRect(0.0f, 0.0f, this.g, this.f - i, direction);
            f8 = this.f - i;
            f5 = this.g;
            f4 = this.f;
            f7 = f9;
            f6 = f8;
            path2 = path2;
            path = path3;
            path.addRect(f7, f6, f5, f4, direction);
            this.c.setClipPath(path2);
            this.c.postInvalidate();
            this.d.setClipPath(path);
            this.d.postInvalidate();
        }
        f2 = i;
        path2.addRect(0.0f, f2, this.g, this.f, direction);
        f3 = this.g;
        f5 = f3;
        f4 = f2;
        f7 = 0.0f;
        f6 = 0.0f;
        path = path3;
        path.addRect(f7, f6, f5, f4, direction);
        this.c.setClipPath(path2);
        this.c.postInvalidate();
        this.d.setClipPath(path);
        this.d.postInvalidate();
    }
}

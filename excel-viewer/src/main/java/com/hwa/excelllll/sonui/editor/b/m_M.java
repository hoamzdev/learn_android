package com.hwa.excelllll.sonui.editor.b;

import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class m extends h {
    public m(String str, View view, View view2, int i) {
        super(str, view, view2, i);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @Override // com.artifex.sonui.editor.b.h
    public void a(float f) {
        int i;
        Path path = new Path();
        int i2 = this.f;
        int i3 = this.g;
        int iSqrt = (int) Math.sqrt((i3 * i3) + (i2 * i2));
        int i4 = this.g;
        int i5 = this.f;
        RectF rectF = new RectF((i4 / 2) - iSqrt, (i5 / 2) - iSqrt, (i4 / 2) + iSqrt, (i5 / 2) + iSqrt);
        String str = this.e;
        switch (str.hashCode()) {
            case -1055763538:
                if (str.equals("threespoke")) {
                    i = 3;
                    a(i, path, rectF, f);
                }
                break;
            case 338780454:
                if (str.equals("fourspoke")) {
                    i = 4;
                    a(i, path, rectF, f);
                }
                break;
            case 1185388381:
                if (str.equals("eightspoke")) {
                    i = 8;
                    a(i, path, rectF, f);
                }
                break;
            case 1828778944:
                if (str.equals("twospoke")) {
                    a(2, path, rectF, f);
                }
                break;
            case 2020771814:
                if (str.equals("onespoke")) {
                    i = 1;
                    a(i, path, rectF, f);
                }
                break;
        }
        this.d.setClipPath(path);
        this.d.invalidate();
    }

    private void a(int i, Path path, RectF rectF, float f) {
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = 360 / i;
            path.moveTo(rectF.centerX(), rectF.centerY());
            path.arcTo(rectF, (i2 * i3) - 90, (i3 * f) - 0.05f);
            path.lineTo(rectF.centerX(), rectF.centerY());
        }
    }
}

package com.hwa.excelllll.sonui.editor.b;

import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class l extends h {
    public l(String str, View view, View view2, int i) {
        super(str, view, view2, i);
    }

    @Override // com.artifex.sonui.editor.b.h
    public void a(float f) {
        float f2 = f * 180.0f;
        int i = this.f;
        int i2 = this.g;
        int iSqrt = (int) Math.sqrt((i2 * i2) + (i * i));
        int i3 = this.g;
        int i4 = this.f;
        RectF rectF = new RectF((i3 / 2) - iSqrt, (i4 / 2) - iSqrt, (i3 / 2) + iSqrt, (i4 / 2) + iSqrt);
        Path path = new Path();
        path.moveTo(rectF.centerX(), rectF.centerY());
        path.arcTo(rectF, ((-90.0f) - f2) + 0.01f, (f2 * 2.0f) - 0.1f);
        path.lineTo(rectF.centerX(), rectF.centerY());
        path.close();
        this.d.setClipPath(path);
        this.d.invalidate();
    }
}

package com.hwa.excelllll.sonui.editor.b;

import android.graphics.Path;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class d extends h {
    public d(String str, View view, View view2, int i) {
        super(str, view, view2, i);
    }

    @Override // com.artifex.sonui.editor.b.h
    public void a(float f) {
        Path path = new Path();
        int i = this.g;
        path.moveTo((i / 2) - (i * f), this.f / 2);
        float f2 = this.g / 2;
        int i2 = this.f;
        path.lineTo(f2, (i2 / 2) - (i2 * f));
        int i3 = this.g;
        path.lineTo((i3 * f) + (i3 / 2), this.f / 2);
        float f3 = this.g / 2;
        int i4 = this.f;
        path.lineTo(f3, (i4 * f) + (i4 / 2));
        path.close();
        this.d.setClipPath(path);
        this.d.invalidate();
    }
}

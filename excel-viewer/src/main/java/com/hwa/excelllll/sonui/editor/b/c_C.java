package com.hwa.excelllll.sonui.editor.b;

import android.graphics.Path;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class c extends h {
    public c(String str, View view, View view2, int i) {
        super(str, view, view2, i);
    }

    @Override // com.artifex.sonui.editor.b.h
    public void a(float f) {
        Path path = new Path();
        int i = this.g;
        int i2 = this.f;
        path.addCircle(this.g / 2, this.f / 2, ((float) Math.sqrt(((i2 * i2) / 4) + ((i * i) / 4))) * f, Path.Direction.CW);
        this.d.setClipPath(path);
        this.d.invalidate();
    }
}

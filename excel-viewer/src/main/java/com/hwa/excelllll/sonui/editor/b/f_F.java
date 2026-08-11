package com.hwa.excelllll.sonui.editor.b;

import android.graphics.Path;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class f extends h {
    public f(String str, View view, View view2, int i) {
        super(str, view, view2, i);
    }

    @Override // com.artifex.sonui.editor.b.h
    public void a(float f) {
        Path path = new Path();
        int i = this.f;
        Path.Direction direction = Path.Direction.CW;
        path.addRect(0.0f, (i / 2) - ((i / 2) * f), this.g, (i / 2) + ((i / 2) * f), direction);
        int i2 = this.g;
        path.addRect((i2 / 2) - ((i2 / 2) * f), 0.0f, (i2 / 2) + ((i2 / 2) * f), this.f, direction);
        this.d.setClipPath(path);
        this.d.invalidate();
    }
}

package com.hwa.excelllll.sonui.editor.b;

import android.graphics.Path;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class j extends h {
    public j(String str, View view, View view2, int i) {
        super(str, view, view2, i);
    }

    @Override // com.artifex.sonui.editor.b.h
    public void a(float f) {
        Path path = new Path();
        String str = this.e;
        int iHashCode = str.hashCode();
        if (iHashCode != 3448) {
            if (iHashCode != 3465) {
                if (iHashCode != 3634) {
                    if (iHashCode == 3651 && str.equals("ru")) {
                        path.moveTo(0.0f, this.f);
                        int i = this.f;
                        path.lineTo(0.0f, i - ((i * 2) * f));
                        path.lineTo(this.g * 2 * f, this.f);
                        path.lineTo(0.0f, this.f);
                        path.close();
                    }
                } else if (str.equals("rd")) {
                    path.moveTo(0.0f, 0.0f);
                    path.lineTo(0.0f, this.f * 2 * f);
                    path.lineTo(this.g * 2 * f, 0.0f);
                    path.lineTo(0.0f, 0.0f);
                    path.close();
                }
            } else if (str.equals("lu")) {
                path.moveTo(this.g, this.f);
                float f2 = this.g;
                int i2 = this.f;
                path.lineTo(f2, i2 - ((i2 * 2) * f));
                int i3 = this.g;
                path.lineTo(i3 - ((i3 * 2) * f), this.f);
                path.lineTo(this.g, this.f);
                path.close();
            }
        } else if (str.equals("ld")) {
            path.moveTo(this.g, 0.0f);
            path.lineTo(this.g, this.f * 2 * f);
            int i4 = this.g;
            path.lineTo(i4 - ((i4 * 2) * f), 0.0f);
            path.lineTo(this.g, 0.0f);
            path.close();
        }
        this.d.setClipPath(path);
        this.d.postInvalidate();
    }
}

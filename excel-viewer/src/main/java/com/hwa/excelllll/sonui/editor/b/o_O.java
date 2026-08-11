package com.hwa.excelllll.sonui.editor.b;

import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class o extends h {
    public o(String str, View view, View view2, int i) {
        super(str, view, view2, i);
    }

    @Override // com.artifex.sonui.editor.b.h
    public void a(float f) {
        Path path = new Path();
        String str = this.e;
        int iHashCode = str.hashCode();
        if (iHashCode != 3365) {
            if (iHashCode == 110414 && str.equals("out")) {
                RectF rectF = new RectF(0.0f, 0.0f, this.g, this.f);
                Path.Direction direction = Path.Direction.CW;
                path.addRect(rectF, direction);
                float f2 = 1.0f - f;
                rectF.inset((this.g * f2) / 2.0f, (this.f * f2) / 2.0f);
                path.addRect(rectF, direction);
                path.setFillType(Path.FillType.EVEN_ODD);
            }
        } else if (str.equals("in")) {
            RectF rectF2 = new RectF(0.0f, 0.0f, this.g, this.f);
            rectF2.inset((this.g * f) / 2.0f, (this.f * f) / 2.0f);
            path.addRect(rectF2, Path.Direction.CW);
        }
        this.c.setClipPath(path);
        this.c.postInvalidate();
    }
}

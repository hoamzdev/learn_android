package com.hwa.excelllll.customviews.smartrefresh.internal;

import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class ArrowDrawable extends PaintDrawable {

    /* JADX INFO: renamed from: b */
    public final Path f48575b = new Path();

    /* JADX INFO: renamed from: c */
    public int f48576c = 0;

    /* JADX INFO: renamed from: d */
    public int f48577d = 0;

    @Override // android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        Rect bounds = getBounds();
        int iWidth = bounds.width();
        int iHeight = bounds.height();
        int i = this.f48576c;
        Path path = this.f48575b;
        if (i != iWidth || this.f48577d != iHeight) {
            path.reset();
            float f = (iWidth * 30) / 225;
            float f2 = f * 0.70710677f;
            float f3 = f / 0.70710677f;
            float f4 = iWidth;
            float f5 = f4 / 2.0f;
            float f6 = iHeight;
            path.moveTo(f5, f6);
            float f7 = f6 / 2.0f;
            path.lineTo(0.0f, f7);
            float f8 = f7 - f2;
            path.lineTo(f2, f8);
            float f9 = f / 2.0f;
            float f10 = f5 - f9;
            float f11 = (f6 - f3) - f9;
            path.lineTo(f10, f11);
            path.lineTo(f10, 0.0f);
            float f12 = f5 + f9;
            path.lineTo(f12, 0.0f);
            path.lineTo(f12, f11);
            path.lineTo(f4 - f2, f8);
            path.lineTo(f4, f7);
            path.close();
            this.f48576c = iWidth;
            this.f48577d = iHeight;
        }
        canvas.drawPath(path, this.f48594a);
    }
}

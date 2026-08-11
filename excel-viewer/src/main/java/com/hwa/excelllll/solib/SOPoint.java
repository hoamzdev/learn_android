package com.hwa.excelllll.solib;

import android.graphics.PointF;

/* JADX INFO: loaded from: classes.dex */
public class SOPoint extends PointF {
    public int type;

    public SOPoint(PointF pointF, int i) {
        this.type = 0;
        ((PointF) this).x = pointF.x;
        ((PointF) this).y = pointF.y;
        if (i < 0 || i > 1) {
            return;
        }
        this.type = i;
    }

    public SOPoint(int i, int i2, int i3) {
        this.type = 0;
        ((PointF) this).x = i;
        ((PointF) this).y = i2;
        if (i3 < 0 || i3 > 1) {
            return;
        }
        this.type = i3;
    }

    public SOPoint(float f, float f2, int i) {
        this.type = 0;
        ((PointF) this).x = f;
        ((PointF) this).y = f2;
        if (i < 0 || i > 1) {
            return;
        }
        this.type = i;
    }
}

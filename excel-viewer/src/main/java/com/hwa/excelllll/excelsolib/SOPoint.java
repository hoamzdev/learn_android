package com.hwa.excelllll.excelsolib;

import android.graphics.PointF;
public class SOPoint extends PointF {
    public int type;

    public SOPoint(PointF point, int pointType) {
        this.type = 0;
        ((PointF) this).x = point.x;
        ((PointF) this).y = point.y;
        if (pointType < 0 || pointType > 1) {
            return;
        }
        this.type = pointType;
    }

    public SOPoint(int xVal, int yVal, int pointType) {
        this.type = 0;
        ((PointF) this).x = xVal;
        ((PointF) this).y = yVal;
        if (pointType < 0 || pointType > 1) {
            return;
        }
        this.type = pointType;
    }

    public SOPoint(float xVal, float yVal, int pointType) {
        this.type = 0;
        ((PointF) this).x = xVal;
        ((PointF) this).y = yVal;
        if (pointType < 0 || pointType > 1) {
            return;
        }
        this.type = pointType;
    }
}

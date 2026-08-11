package com.hwa.excelllll.mupdf.fitz;

/* JADX INFO: loaded from: classes.dex */
public class Rect {
    public float x0;
    public float x1;
    public float y0;
    public float y1;

    static {
        Context.init();
    }

    public Rect(Quad quad) {
        this.x0 = quad.ll_x;
        this.y0 = quad.ll_y;
        this.x1 = quad.ur_x;
        this.y1 = quad.ur_y;
    }

    public native void adjustForStroke(StrokeState strokeState, Matrix matrix);

    public boolean contains(Rect rect) {
        return !isEmpty() && !rect.isEmpty() && rect.x0 >= this.x0 && rect.x1 <= this.x1 && rect.y0 >= this.y0 && rect.y1 <= this.y1;
    }

    public boolean isEmpty() {
        return this.x0 == this.x1 || this.y0 == this.y1;
    }

    public String toString() {
        return "[" + this.x0 + " " + this.y0 + " " + this.x1 + " " + this.y1 + "]";
    }

    public Rect transform(Matrix matrix) {
        float f = this.x0;
        float f2 = matrix.f4155a;
        float f3 = f * f2;
        float f4 = this.x1;
        float f5 = f2 * f4;
        if (f3 > f5) {
            f3 = f5;
            f5 = f3;
        }
        float f6 = this.y0;
        float f7 = matrix.c;
        float f8 = f6 * f7;
        float f9 = this.y1;
        float f10 = f7 * f9;
        if (f8 > f10) {
            f8 = f10;
            f10 = f8;
        }
        float f11 = matrix.e;
        float f12 = matrix.f4156b;
        float f13 = f * f12;
        float f14 = f4 * f12;
        if (f13 > f14) {
            f14 = f13;
            f13 = f14;
        }
        float f15 = matrix.d;
        float f16 = f6 * f15;
        float f17 = f9 * f15;
        if (f16 > f17) {
            f17 = f16;
            f16 = f17;
        }
        float f18 = matrix.f;
        this.x0 = f8 + f11 + f3;
        this.x1 = f10 + f11 + f5;
        this.y0 = f16 + f18 + f13;
        this.y1 = f17 + f18 + f14;
        return this;
    }

    public void union(Rect rect) {
        float f;
        if (isEmpty()) {
            this.x0 = rect.x0;
            this.y0 = rect.y0;
            this.x1 = rect.x1;
            f = rect.y1;
        } else {
            float f2 = rect.x0;
            if (f2 < this.x0) {
                this.x0 = f2;
            }
            float f3 = rect.y0;
            if (f3 < this.y0) {
                this.y0 = f3;
            }
            float f4 = rect.x1;
            if (f4 > this.x1) {
                this.x1 = f4;
            }
            f = rect.y1;
            if (f <= this.y1) {
                return;
            }
        }
        this.y1 = f;
    }

    public Rect(float f, float f2, float f3, float f4) {
        this.x0 = f;
        this.y0 = f2;
        this.x1 = f3;
        this.y1 = f4;
    }

    public Rect() {
        this.y1 = 0.0f;
        this.x1 = 0.0f;
        this.y0 = 0.0f;
        this.x0 = 0.0f;
    }

    public Rect(Rect rect) {
        this(rect.x0, rect.y0, rect.x1, rect.y1);
    }

    public Rect(RectI rectI) {
        this(rectI.x0, rectI.y0, rectI.x1, rectI.y1);
    }

    public boolean contains(float f, float f2) {
        return !isEmpty() && f >= this.x0 && f < this.x1 && f2 >= this.y0 && f2 < this.y1;
    }
}

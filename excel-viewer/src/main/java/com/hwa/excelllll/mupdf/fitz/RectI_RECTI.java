package com.hwa.excelllll.mupdf.fitz;

import defpackage.e6;

/* JADX INFO: loaded from: classes.dex */
public class RectI {
    public int x0;
    public int x1;
    public int y0;
    public int y1;

    public RectI(Rect rect) {
        this.x0 = (int) Math.floor(rect.x0);
        this.y0 = (int) Math.ceil(rect.y0);
        this.x1 = (int) Math.floor(rect.x1);
        this.y1 = (int) Math.ceil(rect.y1);
    }

    public boolean contains(Rect rect) {
        return !isEmpty() && !rect.isEmpty() && rect.x0 >= ((float) this.x0) && rect.x1 <= ((float) this.x1) && rect.y0 >= ((float) this.y0) && rect.y1 <= ((float) this.y1);
    }

    public boolean isEmpty() {
        return this.x0 == this.x1 || this.y0 == this.y1;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        sb.append(this.x0);
        sb.append(" ");
        sb.append(this.y0);
        sb.append(" ");
        sb.append(this.x1);
        sb.append(" ");
        return e6.m(sb, this.y1, "]");
    }

    public RectI transform(Matrix matrix) {
        int i = this.x0;
        float f = matrix.f4155a;
        float f2 = i * f;
        int i2 = this.x1;
        float f3 = i2 * f;
        if (f2 > f3) {
            f3 = f2;
            f2 = f3;
        }
        int i3 = this.y0;
        float f4 = matrix.c;
        float f5 = i3 * f4;
        int i4 = this.y1;
        float f6 = i4 * f4;
        if (f5 > f6) {
            f6 = f5;
            f5 = f6;
        }
        float f7 = matrix.e;
        float f8 = matrix.f4156b;
        float f9 = i * f8;
        float f10 = i2 * f8;
        if (f9 > f10) {
            f10 = f9;
            f9 = f10;
        }
        float f11 = matrix.d;
        float f12 = i3 * f11;
        float f13 = i4 * f11;
        if (f12 > f13) {
            f13 = f12;
            f12 = f13;
        }
        float f14 = matrix.f;
        this.x0 = (int) Math.floor(f5 + f7 + f2);
        this.x1 = (int) Math.ceil(f6 + f7 + f3);
        this.y0 = (int) Math.floor(f12 + f14 + f9);
        this.y1 = (int) Math.ceil(f13 + f14 + f10);
        return this;
    }

    public void union(RectI rectI) {
        int i;
        if (isEmpty()) {
            this.x0 = rectI.x0;
            this.y0 = rectI.y0;
            this.x1 = rectI.x1;
            i = rectI.y1;
        } else {
            int i2 = rectI.x0;
            if (i2 < this.x0) {
                this.x0 = i2;
            }
            int i3 = rectI.y0;
            if (i3 < this.y0) {
                this.y0 = i3;
            }
            int i4 = rectI.x1;
            if (i4 > this.x1) {
                this.x1 = i4;
            }
            i = rectI.y1;
            if (i <= this.y1) {
                return;
            }
        }
        this.y1 = i;
    }

    public RectI(int i, int i2, int i3, int i4) {
        this.x0 = i;
        this.y0 = i2;
        this.x1 = i3;
        this.y1 = i4;
    }

    public RectI() {
        this.y1 = 0;
        this.x1 = 0;
        this.y0 = 0;
        this.x0 = 0;
    }

    public RectI(RectI rectI) {
        this(rectI.x0, rectI.y0, rectI.x1, rectI.y1);
    }

    public boolean contains(int i, int i2) {
        return !isEmpty() && i >= this.x0 && i < this.x1 && i2 >= this.y0 && i2 < this.y1;
    }
}

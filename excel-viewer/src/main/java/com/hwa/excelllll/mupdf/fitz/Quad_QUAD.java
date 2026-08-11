package com.hwa.excelllll.mupdf.fitz;

import defpackage.e6;

/* JADX INFO: loaded from: classes.dex */
public class Quad {
    public float ll_x;
    public float ll_y;
    public float lr_x;
    public float lr_y;
    public float ul_x;
    public float ul_y;
    public float ur_x;
    public float ur_y;

    public Quad(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        this.ul_x = f;
        this.ul_y = f2;
        this.ur_x = f3;
        this.ur_y = f4;
        this.ll_x = f5;
        this.ll_y = f6;
        this.lr_x = f7;
        this.lr_y = f8;
    }

    public Rect toRect() {
        return new Rect(Math.min(Math.min(this.ul_x, this.ur_x), Math.min(this.ll_x, this.lr_x)), Math.min(Math.min(this.ul_y, this.ur_y), Math.min(this.ll_y, this.lr_y)), Math.max(Math.max(this.ul_x, this.ur_x), Math.max(this.ll_x, this.lr_x)), Math.max(Math.max(this.ul_y, this.ur_y), Math.max(this.ll_y, this.lr_y)));
    }

    public String toString() {
        return "[" + this.ul_x + " " + this.ul_y + " " + this.ur_x + " " + this.ur_y + " " + this.ll_x + " " + this.ll_y + " " + this.lr_x + " " + this.lr_y + "]";
    }

    public Quad transform(Matrix matrix) {
        float f = this.ul_x;
        float f2 = matrix.f4155a;
        float f3 = this.ul_y;
        float f4 = matrix.c;
        float f5 = matrix.e;
        float f6 = matrix.f4156b;
        float f7 = matrix.d;
        float f8 = matrix.f;
        float f9 = this.ur_x;
        float f10 = this.ur_y;
        float f11 = this.ll_x;
        float f12 = this.ll_y;
        float f13 = this.lr_x;
        float f14 = this.lr_y;
        this.ul_x = e6.b(f3, f4, f * f2, f5);
        this.ul_y = e6.b(f3, f7, f * f6, f8);
        this.ur_x = e6.b(f10, f4, f9 * f2, f5);
        this.ur_y = e6.b(f10, f7, f9 * f6, f8);
        this.ll_x = e6.b(f12, f4, f11 * f2, f5);
        this.ll_y = e6.b(f12, f7, f11 * f6, f8);
        this.lr_x = e6.b(f14, f4, f2 * f13, f5);
        this.lr_y = e6.b(f7, f14, f13 * f6, f8);
        return this;
    }

    public Quad transformed(Matrix matrix) {
        float f = this.ul_x;
        float f2 = matrix.f4155a;
        float f3 = this.ul_y;
        float f4 = matrix.c;
        float f5 = matrix.e;
        float f6 = matrix.f4156b;
        float f7 = matrix.d;
        float f8 = matrix.f;
        float f9 = this.ur_x;
        float f10 = this.ur_y;
        float f11 = this.ll_x;
        float f12 = this.ll_y;
        float f13 = this.lr_x;
        float f14 = this.lr_y;
        return new Quad(e6.b(f3, f4, f * f2, f5), e6.b(f3, f7, f * f6, f8), e6.b(f10, f4, f9 * f2, f5), e6.b(f10, f7, f9 * f6, f8), e6.b(f12, f4, f11 * f2, f5), e6.b(f12, f7, f11 * f6, f8), e6.b(f14, f4, f2 * f13, f5), e6.b(f14, f7, f13 * f6, f8));
    }
}

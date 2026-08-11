package com.hwa.excelllll.mupdf.fitz;

/* JADX INFO: loaded from: classes.dex */
public class Point {
    public float x;
    public float y;

    public Point(Point point) {
        this.x = point.x;
        this.y = point.y;
    }

    public String toString() {
        return "[" + this.x + " " + this.y + "]";
    }

    public Point transform(Matrix matrix) {
        float f = this.x;
        float f2 = matrix.f4155a * f;
        float f3 = this.y;
        this.x = (matrix.c * f3) + f2 + matrix.e;
        this.y = (f3 * matrix.d) + (f * matrix.f4156b) + matrix.f;
        return this;
    }

    public Point(float f, float f2) {
        this.x = f;
        this.y = f2;
    }
}

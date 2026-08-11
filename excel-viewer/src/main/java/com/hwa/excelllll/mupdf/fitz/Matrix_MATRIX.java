package com.hwa.excelllll.mupdf.fitz;

import defpackage.e6;

/* JADX INFO: loaded from: classes.dex */
public class Matrix {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public float f4155a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public float f4156b;
    public float c;
    public float d;
    public float e;
    public float f;

    public Matrix(Matrix matrix, Matrix matrix2) {
        float f = matrix.f4155a;
        float f2 = matrix2.f4155a;
        float f3 = matrix.f4156b;
        float f4 = matrix2.c;
        this.f4155a = (f3 * f4) + (f * f2);
        float f5 = matrix.f4155a;
        float f6 = matrix2.f4156b;
        float f7 = matrix2.d;
        this.f4156b = (f3 * f7) + (f5 * f6);
        float f8 = matrix.c;
        float f9 = matrix2.f4155a;
        float f10 = matrix.d;
        this.c = (f4 * f10) + (f8 * f9);
        float f11 = matrix.c;
        float f12 = matrix2.f4156b;
        this.d = (f10 * f7) + (f11 * f12);
        float f13 = matrix.e;
        float f14 = matrix.f;
        this.e = (matrix2.c * f14) + (f13 * f9) + matrix2.e;
        this.f = (f14 * matrix2.d) + (matrix.e * f12) + matrix2.f;
    }

    public static Matrix Identity() {
        return new Matrix(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f);
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0021 A[PHI: r2
  0x0021: PHI (r2v9 float) = (r2v1 float), (r2v4 float) binds: [B:9:0x001f, B:15:0x003c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:19:0x004b A[PHI: r2
  0x004b: PHI (r2v2 float) = (r2v1 float), (r2v4 float) binds: [B:12:0x002d, B:18:0x0049] A[DONT_GENERATE, DONT_INLINE]] */
    public static Matrix Rotate(float f) {
        float fCos;
        float f2;
        while (true) {
            fCos = 0.0f;
            if (f >= 0.0f) {
                break;
            }
            f += 360.0f;
        }
        while (f >= 360.0f) {
            f -= 360.0f;
        }
        float f3 = 1.0f;
        if (Math.abs(0.0f - f) >= 1.0E-4d) {
            if (Math.abs(90.0f - f) < 1.0E-4d) {
                f2 = f3;
            } else {
                f3 = -1.0f;
                if (Math.abs(180.0f - f) < 1.0E-4d) {
                    f2 = 0.0f;
                } else if (Math.abs(270.0f - f) < 1.0E-4d) {
                    f2 = f3;
                } else {
                    double d = f;
                    Double.isNaN(d);
                    Double.isNaN(d);
                    Double.isNaN(d);
                    double d2 = (d * 3.141592653589793d) / 180.0d;
                    float fSin = (float) Math.sin(d2);
                    fCos = (float) Math.cos(d2);
                    f2 = fSin;
                }
            }
            f3 = fCos;
        } else {
            f2 = 0.0f;
        }
        return new Matrix(f3, f2, -f2, f3, 0.0f, 0.0f);
    }

    public static Matrix Scale(float f) {
        return new Matrix(f, 0.0f, 0.0f, f, 0.0f, 0.0f);
    }

    public static Matrix Translate(float f, float f2) {
        return new Matrix(1.0f, 0.0f, 0.0f, 1.0f, f, f2);
    }

    public Matrix concat(Matrix matrix) {
        float f = this.f4155a;
        float f2 = matrix.f4155a;
        float f3 = this.f4156b;
        float f4 = matrix.c;
        float f5 = matrix.f4156b;
        float f6 = matrix.d;
        float f7 = this.c;
        float f8 = this.d;
        float f9 = this.e;
        float f10 = this.f;
        float f11 = matrix.e;
        this.f = (f10 * f6) + (f9 * f5) + matrix.f;
        this.f4155a = (f3 * f4) + (f * f2);
        this.f4156b = (f3 * f6) + (f * f5);
        this.c = (f8 * f4) + (f7 * f2);
        this.d = (f8 * f6) + (f7 * f5);
        this.e = e6.b(f4, f10, f2 * f9, f11);
        return this;
    }

    public Matrix rotate(float f) {
        float f2;
        while (f < 0.0f) {
            f += 360.0f;
        }
        while (f >= 360.0f) {
            f -= 360.0f;
        }
        if (Math.abs(0.0f - f) < 1.0E-4d) {
            return this;
        }
        if (Math.abs(90.0f - f) < 1.0E-4d) {
            float f3 = this.f4155a;
            float f4 = this.f4156b;
            this.f4155a = this.c;
            this.f4156b = this.d;
            this.c = -f3;
            f2 = -f4;
        } else if (Math.abs(180.0f - f) < 1.0E-4d) {
            this.f4155a = -this.f4155a;
            this.f4156b = -this.f4156b;
            this.c = -this.c;
            f2 = -this.d;
        } else {
            if (Math.abs(270.0f - f) < 1.0E-4d) {
                float f5 = this.f4155a;
                float f6 = this.f4156b;
                this.f4155a = -this.c;
                this.f4156b = -this.d;
                this.c = f5;
                this.d = f6;
                return this;
            }
            double d = f;
            Double.isNaN(d);
            Double.isNaN(d);
            Double.isNaN(d);
            double d2 = (d * 3.141592653589793d) / 180.0d;
            float fSin = (float) Math.sin(d2);
            float fCos = (float) Math.cos(d2);
            float f7 = this.f4155a;
            float f8 = this.f4156b;
            float f9 = this.c;
            this.f4155a = (fSin * f9) + (fCos * f7);
            float f10 = this.d;
            this.f4156b = (fSin * f10) + (fCos * f8);
            float f11 = -fSin;
            this.c = (f9 * fCos) + (f7 * f11);
            f2 = (f11 * f8) + (fCos * f10);
        }
        this.d = f2;
        return this;
    }

    public Matrix scale(float f, float f2) {
        this.f4155a *= f;
        this.f4156b *= f;
        this.c *= f2;
        this.d *= f2;
        return this;
    }

    public String toString() {
        return "[" + this.f4155a + " " + this.f4156b + " " + this.c + " " + this.d + " " + this.e + " " + this.f + "]";
    }

    public Matrix translate(float f, float f2) {
        float f3 = this.e;
        this.e = e6.b(this.c, f2, this.f4155a * f, f3);
        float f4 = this.f;
        this.f = e6.b(f2, this.d, f * this.f4156b, f4);
        return this;
    }

    public static Matrix Scale(float f, float f2) {
        return new Matrix(f, 0.0f, 0.0f, f2, 0.0f, 0.0f);
    }

    public Matrix scale(float f) {
        return scale(f, f);
    }

    public Matrix(float f) {
        this(f, 0.0f, 0.0f, f, 0.0f, 0.0f);
    }

    public Matrix(float f, float f2) {
        this(f, 0.0f, 0.0f, f2, 0.0f, 0.0f);
    }

    public Matrix(float f, float f2, float f3, float f4) {
        this(f, f2, f3, f4, 0.0f, 0.0f);
    }

    public Matrix(float f, float f2, float f3, float f4, float f5, float f6) {
        this.f4155a = f;
        this.f4156b = f2;
        this.c = f3;
        this.d = f4;
        this.e = f5;
        this.f = f6;
    }

    public Matrix(Matrix matrix) {
        this(matrix.f4155a, matrix.f4156b, matrix.c, matrix.d, matrix.e, matrix.f);
    }

    public Matrix() {
        this(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f);
    }
}

package com.hwa.excelllll.solib;

import android.graphics.Matrix;

/* JADX INFO: loaded from: classes.dex */
public class i {
    public float d = 1.0f;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public float f4238a = 1.0f;
    public float c = 0.0f;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    public float f4239b = 0.0f;
    public float f = 0.0f;
    public float e = 0.0f;

    public Matrix a() {
        Matrix matrix = new Matrix();
        matrix.setScale(this.f4238a, this.d);
        matrix.setSkew(this.f4239b, this.c);
        matrix.setTranslate(this.e, this.f);
        return matrix;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        i iVar = (i) obj;
        return Float.compare(iVar.f4238a, this.f4238a) == 0 && Float.compare(iVar.f4239b, this.f4239b) == 0 && Float.compare(iVar.c, this.c) == 0 && Float.compare(iVar.d, this.d) == 0 && Float.compare(iVar.e, this.e) == 0 && Float.compare(iVar.f, this.f) == 0;
    }

    public int hashCode() {
        float f = this.f4238a;
        int iFloatToIntBits = f != 0.0f ? Float.floatToIntBits(f) : 0;
        float f2 = this.f4239b;
        int iFloatToIntBits2 = f2 != 0.0f ? Float.floatToIntBits(f2) : 0;
        float f3 = this.c;
        int iFloatToIntBits3 = f3 != 0.0f ? Float.floatToIntBits(f3) : 0;
        float f4 = this.d;
        int iFloatToIntBits4 = f4 != 0.0f ? Float.floatToIntBits(f4) : 0;
        float f5 = this.e;
        int iFloatToIntBits5 = f5 != 0.0f ? Float.floatToIntBits(f5) : 0;
        float f6 = this.f;
        return (((((((((iFloatToIntBits * 31) + iFloatToIntBits2) * 31) + iFloatToIntBits3) * 31) + iFloatToIntBits4) * 31) + iFloatToIntBits5) * 31) + (f6 != 0.0f ? Float.floatToIntBits(f6) : 0);
    }
}

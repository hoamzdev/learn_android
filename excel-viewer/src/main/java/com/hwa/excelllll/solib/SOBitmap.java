package com.hwa.excelllll.solib;

import android.graphics.Bitmap;
import android.graphics.Rect;
import defpackage.a7;

/* JADX INFO: loaded from: classes.dex */
public class SOBitmap implements Comparable<SOBitmap> {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    protected static int f4164a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    protected int f4165b;
    protected Bitmap bitmap;
    protected Rect rect;

    /* JADX INFO: renamed from: com.artifex.solib.SOBitmap$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f4166a;

        static {
            int[] iArr = new int[Type.values().length];
            f4166a = iArr;
            try {
                iArr[Type.RGB555.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f4166a[Type.RGB565.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f4166a[Type.A8.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f4166a[Type.RGBA8888.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public enum Type {
        A8,
        RGB555,
        RGB565,
        RGBA8888
    }

    public SOBitmap(int i, int i2, Type type) {
        int i3 = f4164a + 1;
        f4164a = i3;
        this.f4165b = i3;
        int i4 = AnonymousClass1.f4166a[type.ordinal()];
        this.bitmap = Bitmap.createBitmap(i, i2, i4 != 3 ? i4 != 4 ? Bitmap.Config.RGB_565 : Bitmap.Config.ARGB_8888 : Bitmap.Config.ALPHA_8);
        this.rect = new Rect(0, 0, i, i2);
    }

    private native void nativeMergePixels(Bitmap bitmap, int i, int i2, int i3, int i4, Bitmap bitmap2, Bitmap bitmap3, int i5, int i6, int i7, int i8);

    public void a(SOBitmap sOBitmap, SOBitmap sOBitmap2) {
        if (sOBitmap == null || sOBitmap2 == null) {
            throw null;
        }
        if (!this.rect.equals(sOBitmap2.rect)) {
            a7.z("alphaCombine's source bitmaps must have identical dimensions");
            return;
        }
        if (a().getConfig() != Bitmap.Config.RGB_565) {
            a7.z("alphaCombine's source bitmap must be RGB_565 format");
            return;
        }
        if (sOBitmap2.a().getConfig() != Bitmap.Config.ALPHA_8) {
            a7.z("alphaCombine's source alpha mask must be ALPHA_8 format");
            return;
        }
        if (sOBitmap.a().getConfig() != Bitmap.Config.ARGB_8888) {
            a7.z("alphaCombine's destination bitmap must be ARGB_8888 format");
            return;
        }
        Rect rectB = sOBitmap.b();
        Bitmap bitmap = sOBitmap.bitmap;
        int i = rectB.left;
        int i2 = rectB.top;
        int iWidth = rectB.width();
        int iHeight = rectB.height();
        Bitmap bitmap2 = this.bitmap;
        Bitmap bitmap3 = sOBitmap2.bitmap;
        Rect rect = this.rect;
        nativeMergePixels(bitmap, i, i2, iWidth, iHeight, bitmap2, bitmap3, rect.left, rect.top, rect.width(), this.rect.height());
    }

    public Rect b() {
        return this.rect;
    }

    public int c() {
        Rect rect = this.rect;
        return rect.right - rect.left;
    }

    public int d() {
        Rect rect = this.rect;
        return rect.bottom - rect.top;
    }

    public void e() {
        if (this.bitmap.isRecycled()) {
            return;
        }
        this.bitmap.recycle();
    }

    public SOBitmap(int i, int i2) {
        this(i, i2, Type.RGB565);
    }

    public SOBitmap() {
    }

    public SOBitmap(SOBitmap sOBitmap, int i, int i2, int i3, int i4) {
        this.f4165b = sOBitmap.f4165b;
        this.bitmap = sOBitmap.bitmap;
        this.rect = new Rect(i, i2, i3, i4);
    }

    public Bitmap a() {
        return this.bitmap;
    }

    @Override // java.lang.Comparable
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public int compareTo(SOBitmap sOBitmap) {
        Bitmap bitmap = this.bitmap;
        int byteCount = bitmap == null ? 0 : bitmap.getByteCount();
        Bitmap bitmap2 = sOBitmap.bitmap;
        int byteCount2 = bitmap2 == null ? 0 : bitmap2.getByteCount();
        if (byteCount > byteCount2) {
            return 1;
        }
        if (byteCount < byteCount2) {
            return -1;
        }
        int i = this.f4165b;
        int i2 = sOBitmap.f4165b;
        if (i > i2) {
            return 1;
        }
        return i < i2 ? -1 : 0;
    }
}

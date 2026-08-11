package com.hwa.excelllll.mupdf.fitz;

/* JADX INFO: loaded from: classes.dex */
public final class ColorParams {
    public static final int BP = 32;
    public static final int OP = 64;
    public static final int OPM = 128;

    /* JADX INFO: renamed from: com.artifex.mupdf.fitz.ColorParams$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f4154a;

        static {
            int[] iArr = new int[RenderingIntent.values().length];
            f4154a = iArr;
            try {
                iArr[RenderingIntent.PERCEPTUAL.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f4154a[RenderingIntent.RELATIVE_COLORIMETRIC.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f4154a[RenderingIntent.SATURATION.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f4154a[RenderingIntent.ABSOLUTE_COLORIMETRIC.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public enum RenderingIntent {
        PERCEPTUAL,
        RELATIVE_COLORIMETRIC,
        SATURATION,
        ABSOLUTE_COLORIMETRIC
    }

    public static boolean BP(int i) {
        return (i & 32) != 0;
    }

    public static boolean OP(int i) {
        return (i & 64) != 0;
    }

    public static boolean OPM(int i) {
        return (i & 128) != 0;
    }

    public static RenderingIntent RI(int i) {
        int i2 = i & 3;
        if (i2 == 1) {
            return RenderingIntent.RELATIVE_COLORIMETRIC;
        }
        if (i2 != 2) {
            return i2 != 3 ? RenderingIntent.PERCEPTUAL : RenderingIntent.ABSOLUTE_COLORIMETRIC;
        }
        return RenderingIntent.SATURATION;
    }

    public static int pack(RenderingIntent renderingIntent, boolean z, boolean z2, boolean z3) {
        int i = AnonymousClass1.f4154a[renderingIntent.ordinal()];
        int i2 = 2;
        if (i == 2) {
            i2 = 1;
        } else if (i != 3) {
            i2 = i != 4 ? 0 : 3;
        }
        if (z) {
            i2 |= 32;
        }
        if (z2) {
            i2 |= 64;
        }
        return z3 ? i2 | 128 : i2;
    }
}

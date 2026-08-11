package com.hwa.excelllll.solib;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import com.artifex.solib.animation.SOAnimationCommand;
import defpackage.a7;

/* JADX INFO: loaded from: classes.dex */
public class SOPage {
    private long internal;

    public SOPage(long j) {
        this.internal = j;
    }

    private native String getSlideTransitionInternal();

    private native SORender nativeRenderAtZoom(int i, double d, double d2, double d3, Bitmap bitmap, int i2, Bitmap bitmap2, int i3, int i4, int i5, int i6, SORenderListenerInternal sORenderListenerInternal);

    public SORender a(int i, double d, double d2, double d3, SOBitmap sOBitmap, SOBitmap sOBitmap2, final o oVar, final boolean z) {
        int i2;
        int i3;
        int i4;
        Rect rectB = sOBitmap.b();
        Bitmap bitmapA = sOBitmap.a();
        int width = bitmapA.getWidth();
        int height = bitmapA.getHeight();
        Bitmap bitmapA2 = sOBitmap2 != null ? sOBitmap2.a() : null;
        int i5 = rectB.left;
        if (i5 >= 0 && (i2 = rectB.top) >= 0 && (i3 = rectB.right) <= width && (i4 = rectB.bottom) <= height) {
            return nativeRenderAtZoom(i, d, d2, d3, bitmapA, width, bitmapA2, i5, i2, i3 - i5, i4 - i2, new SORenderListenerInternal() { // from class: com.artifex.solib.SOPage.1
                @Override // com.artifex.solib.SORenderListenerInternal
                public void progress(final int i6) {
                    if (z) {
                        SOLib.a(new Runnable() { // from class: com.artifex.solib.SOPage.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                oVar.a(i6);
                            }
                        });
                    } else {
                        oVar.a(i6);
                    }
                }
            });
        }
        a7.z("Render rectangle out of range");
        return null;
    }

    public native void discard();

    public void finalize() throws Throwable {
        try {
            discard();
        } finally {
            super.finalize();
        }
    }

    public native SOAnimationCommand[] getAnimations();

    public native float[] getHorizontalRuler();

    public native String getPageTitle();

    public native float[] getVerticalRuler();

    public void m() {
        discard();
    }

    public native SOHyperlink objectAtPoint(float f, float f2);

    public q p() {
        return new q(getSlideTransitionInternal());
    }

    public native void select(int i, double d, double d2);

    public native SOSelectionLimits selectionLimits();

    public native void setSelectionLimitsBox(float f, float f2, float f3, float f4);

    public native Point sizeAtZoom(double d);

    public native PointF zoomToFitRect(int i, int i2);

    public void n() {
    }

    public SORender a(double d, PointF pointF, SOBitmap sOBitmap, o oVar, boolean z) {
        return a(-2, d, pointF.x, pointF.y, sOBitmap, null, oVar, z);
    }

    public SORender a(double d, PointF pointF, SOBitmap sOBitmap, o oVar) {
        return a(-2, d, pointF.x, pointF.y, sOBitmap, null, oVar, true);
    }

    public void a(int i, PointF pointF) {
        select(i, pointF.x, pointF.y);
    }

    public void a(RectF rectF) {
        setSelectionLimitsBox(rectF.left, rectF.top, rectF.right, rectF.bottom);
    }
}

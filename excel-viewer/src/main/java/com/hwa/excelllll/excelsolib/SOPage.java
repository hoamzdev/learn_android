package com.hwa.excelllll.excelsolib;

import android.util.Log;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

import com.hwa.excelllll.SOLib;
import com.hwa.excelllll.excelsolib.animation.SOAnimationCommand;

public class SOPage {
    private long internalHandle;

    public SOPage(long handle) {
        this.internalHandle = handle;
    }

    private native String getSlideTransitionInternal();

    private native SORender nativeRenderAtZoom(int renderMode, double zoom, double originX, double originY, Bitmap bitmap, int bitmapWidth, Bitmap alphaBitmap, int rectLeft, int rectTop, int rectWidth, int rectHeight, SORenderListenerInternal listener);

    public SORender render(int renderMode, double zoom, double originX, double originY, SOBitmap targetBitmap, SOBitmap alphaBitmap, final PageRenderListener listener, final boolean runOnMainThread) {
        int rectTop;
        int rectRight;
        int rectBottom;
        Rect renderRect = targetBitmap.getRect();
        Bitmap destNativeBitmap = targetBitmap.getBitmap();
        int bitmapWidth = destNativeBitmap.getWidth();
        int bitmapHeight = destNativeBitmap.getHeight();
        Bitmap alphaNativeBitmap = alphaBitmap != null ? alphaBitmap.getBitmap() : null;
        int rectLeft = renderRect.left;
        if (rectLeft >= 0 && (rectTop = renderRect.top) >= 0 && (rectRight = renderRect.right) <= bitmapWidth && (rectBottom = renderRect.bottom) <= bitmapHeight) {
            return nativeRenderAtZoom(renderMode, zoom, originX, originY, destNativeBitmap, bitmapWidth, alphaNativeBitmap, rectLeft, rectTop, rectRight - rectLeft, rectBottom - rectTop, new SORenderListenerInternal() { // from class: com.hwa.excel.excelsolib.SOPage.1
                @Override // com.hwa.excelllll.excelsolib.SORenderListenerInternal
                public void progress(final int progressPercent) {
                    if (runOnMainThread) {
                        SOLib.a(new Runnable() { // from class: com.hwa.excel.excelsolib.SOPage.1.1
                            @Override // java.lang.Runnable
                            public void run() {
                                listener.onRenderProgress(progressPercent);
                            }
                        });
                    } else {
                        listener.onRenderProgress(progressPercent);
                    }
                }
            });
        }
        Log.e("ExcelCore", "Render rectangle out of range");
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

    public void release() {
        discard();
    }

    public native SOHyperlink objectAtPoint(float x, float y);

    public SlideTransition getSlideTransition() {
        return new SlideTransition(getSlideTransitionInternal());
    }

    public native void select(int selectMode, double x, double y);

    public native SOSelectionLimits selectionLimits();

    public native void setSelectionLimitsBox(float left, float top, float right, float bottom);

    public native Point sizeAtZoom(double zoom);

    public native PointF zoomToFitRect(int width, int height);

    public void onPause() {
    }

    public SORender render(double zoom, PointF point, SOBitmap targetBitmap, PageRenderListener listener, boolean runOnMainThread) {
        return render(-2, zoom, point.x, point.y, targetBitmap, null, listener, runOnMainThread);
    }

    public SORender render(double zoom, PointF point, SOBitmap targetBitmap, PageRenderListener listener) {
        return render(-2, zoom, point.x, point.y, targetBitmap, null, listener, true);
    }

    public void select(int selectMode, PointF point) {
        select(selectMode, point.x, point.y);
    }

    public void setSelectionLimitsBox(RectF box) {
        setSelectionLimitsBox(box.left, box.top, box.right, box.bottom);
    }
}

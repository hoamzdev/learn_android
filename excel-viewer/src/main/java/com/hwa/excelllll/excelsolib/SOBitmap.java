package com.hwa.excelllll.excelsolib;
import android.util.Log;

import android.graphics.Bitmap;
import android.graphics.Rect;
public class SOBitmap implements Comparable<SOBitmap> {
    protected static int instanceCounter;
    protected int instanceId;
    protected Bitmap bitmap;
    protected Rect rect;
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] typeSwitchMap;

        static {
            int[] iArr = new int[Type.values().length];
            typeSwitchMap = iArr;
            try {
                iArr[Type.RGB555.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                typeSwitchMap[Type.RGB565.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                typeSwitchMap[Type.A8.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                typeSwitchMap[Type.RGBA8888.ordinal()] = 4;
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

    public SOBitmap(int width, int height, Type type) {
        int nextId = instanceCounter + 1;
        instanceCounter = nextId;
        this.instanceId = nextId;
        int configType = AnonymousClass1.typeSwitchMap[type.ordinal()];
        this.bitmap = Bitmap.createBitmap(width, height, configType != 3 ? configType != 4 ? Bitmap.Config.RGB_565 : Bitmap.Config.ARGB_8888 : Bitmap.Config.ALPHA_8);
        this.rect = new Rect(0, 0, width, height);
    }

    private native void nativeMergePixels(Bitmap destBitmap, int destX, int destY, int destW, int destH, Bitmap srcBitmap, Bitmap alphaMaskBitmap, int srcX, int srcY, int srcW, int srcH);

    public void combineWithAlpha(SOBitmap destBitmap, SOBitmap alphaMask) {
        if (destBitmap == null || alphaMask == null) {
            throw null;
        }
        if (!this.rect.equals(alphaMask.rect)) {
            Log.e("ExcelCore", "alphaCombine's source bitmaps must have identical dimensions");
            return;
        }
        if (getBitmap().getConfig() != Bitmap.Config.RGB_565) {
            Log.e("ExcelCore", "alphaCombine's source bitmap must be RGB_565 format");
            return;
        }
        if (alphaMask.getBitmap().getConfig() != Bitmap.Config.ALPHA_8) {
            Log.e("ExcelCore", "alphaCombine's source alpha mask must be ALPHA_8 format");
            return;
        }
        if (destBitmap.getBitmap().getConfig() != Bitmap.Config.ARGB_8888) {
            Log.e("ExcelCore", "alphaCombine's destination bitmap must be ARGB_8888 format");
            return;
        }
        Rect destRect = destBitmap.getRect();
        Bitmap destNativeBitmap = destBitmap.bitmap;
        int destLeft = destRect.left;
        int destTop = destRect.top;
        int destWidth = destRect.width();
        int destHeight = destRect.height();
        Bitmap srcBitmap = this.bitmap;
        Bitmap alphaMaskBitmap = alphaMask.bitmap;
        Rect srcRect = this.rect;
        nativeMergePixels(destNativeBitmap, destLeft, destTop, destWidth, destHeight, srcBitmap, alphaMaskBitmap, srcRect.left, srcRect.top, srcRect.width(), this.rect.height());
    }


    public Rect getRect() {
        return this.rect;
    }

    public int getWidth() {
        Rect currentRect = this.rect;
        return currentRect.right - currentRect.left;
    }

    public int getHeight() {
        Rect currentRect = this.rect;
        return currentRect.bottom - currentRect.top;
    }

    public void recycle() {
        if (this.bitmap.isRecycled()) {
            return;
        }
        this.bitmap.recycle();
    }

    public SOBitmap(int width, int height) {
        this(width, height, Type.RGB565);
    }

    public SOBitmap() {
    }

    public SOBitmap(SOBitmap sourceBitmap, int left, int top, int right, int bottom) {
        this.instanceId = sourceBitmap.instanceId;
        this.bitmap = sourceBitmap.bitmap;
        this.rect = new Rect(left, top, right, bottom);
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    @Override // java.lang.Comparable
    public int compareTo(SOBitmap other) {
        Bitmap thisNativeBitmap = this.bitmap;
        int thisByteCount = thisNativeBitmap == null ? 0 : thisNativeBitmap.getByteCount();
        Bitmap otherNativeBitmap = other.bitmap;
        int otherByteCount = otherNativeBitmap == null ? 0 : otherNativeBitmap.getByteCount();
        if (thisByteCount > otherByteCount) {
            return 1;
        }
        if (thisByteCount < otherByteCount) {
            return -1;
        }
        int thisId = this.instanceId;
        int otherId = other.instanceId;
        if (thisId > otherId) {
            return 1;
        }
        return thisId < otherId ? -1 : 0;
    }
}

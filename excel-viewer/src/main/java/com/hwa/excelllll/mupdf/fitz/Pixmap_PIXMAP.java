package com.hwa.excelllll.mupdf.fitz;

/* JADX INFO: loaded from: classes.dex */
public class Pixmap {
    private long pointer;

    static {
        Context.init();
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public Pixmap(ColorSpace colorSpace, Rect rect, boolean z) {
        float f = rect.x0;
        float f2 = rect.y0;
        this(colorSpace, (int) f, (int) f2, (int) (rect.x1 - f), (int) (rect.y1 - f2), z);
    }

    private native void clearWithValue(int i);

    private native long newNative(ColorSpace colorSpace, int i, int i2, int i3, int i4, boolean z);

    public native void clear();

    public void clear(int i) {
        clearWithValue(i);
    }

    public void destroy() {
        finalize();
        this.pointer = 0L;
    }

    public native void finalize();

    public native void gamma(float f);

    public native boolean getAlpha();

    public Rect getBounds() {
        int x = getX();
        int y = getY();
        return new Rect(x, y, getWidth() + x, getHeight() + y);
    }

    public native ColorSpace getColorSpace();

    public native int getHeight();

    public native int getNumberOfComponents();

    public native int[] getPixels();

    public native byte getSample(int i, int i2, int i3);

    public native byte[] getSamples();

    public native int getStride();

    public native int getWidth();

    public native int getX();

    public native int getXResolution();

    public native int getY();

    public native int getYResolution();

    public native void invert();

    public native void saveAsPNG(String str);

    public String toString() {
        return "Pixmap(w=" + getWidth() + " h=" + getHeight() + " x=" + getX() + " y=" + getY() + " n=" + getNumberOfComponents() + " alpha=" + getAlpha() + " cs=" + getColorSpace() + ")";
    }

    public Pixmap(ColorSpace colorSpace, int i, int i2) {
        this(colorSpace, 0, 0, i, i2, false);
    }

    public Pixmap(ColorSpace colorSpace, int i, int i2, int i3, int i4) {
        this(colorSpace, i, i2, i3, i4, false);
    }

    public Pixmap(ColorSpace colorSpace, int i, int i2, int i3, int i4, boolean z) {
        this.pointer = newNative(colorSpace, i, i2, i3, i4, z);
    }

    public Pixmap(ColorSpace colorSpace, int i, int i2, boolean z) {
        this(colorSpace, 0, 0, i, i2, z);
    }

    public Pixmap(ColorSpace colorSpace, Rect rect) {
        this(colorSpace, rect, false);
    }

    private Pixmap(long j) {
        this.pointer = j;
    }
}

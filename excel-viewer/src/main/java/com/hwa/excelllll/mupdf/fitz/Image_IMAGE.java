package com.hwa.excelllll.mupdf.fitz;

/* JADX INFO: loaded from: classes.dex */
public class Image {
    protected long pointer;

    static {
        Context.init();
    }

    public Image(Pixmap pixmap) {
        this.pointer = newNativeFromPixmap(pixmap);
    }

    private native long newNativeFromFile(String str);

    private native long newNativeFromPixmap(Pixmap pixmap);

    public void destroy() {
        finalize();
        this.pointer = 0L;
    }

    public native void finalize();

    public native int getBitsPerComponent();

    public native ColorSpace getColorSpace();

    public native int getHeight();

    public native boolean getImageMask();

    public native boolean getInterpolate();

    public native Image getMask();

    public native int getNumberOfComponents();

    public native int getWidth();

    public native int getXResolution();

    public native int getYResolution();

    public native Pixmap toPixmap();

    public Image(long j) {
        this.pointer = j;
    }

    public Image(String str) {
        this.pointer = newNativeFromFile(str);
    }
}

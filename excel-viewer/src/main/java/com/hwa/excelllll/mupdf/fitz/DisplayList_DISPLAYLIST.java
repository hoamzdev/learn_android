package com.hwa.excelllll.mupdf.fitz;

/* JADX INFO: loaded from: classes.dex */
public class DisplayList {
    private long pointer;

    static {
        Context.init();
    }

    public DisplayList(Rect rect) {
        this.pointer = newNative(rect);
    }

    private native long newNative(Rect rect);

    public void destroy() {
        finalize();
        this.pointer = 0L;
    }

    public native void finalize();

    public void run(Device device, Matrix matrix, Cookie cookie) {
        run(device, matrix, null, cookie);
    }

    public native void run(Device device, Matrix matrix, Rect rect, Cookie cookie);

    public native Quad[] search(String str);

    public native Pixmap toPixmap(Matrix matrix, ColorSpace colorSpace, boolean z);

    public StructuredText toStructuredText() {
        return toStructuredText(null);
    }

    public native StructuredText toStructuredText(String str);

    private DisplayList(long j) {
        this.pointer = j;
    }
}

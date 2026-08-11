package com.hwa.excelllll.mupdf.fitz;

/* JADX INFO: loaded from: classes.dex */
public class Page {
    private long pointer;

    static {
        Context.init();
    }

    public Page(long j) {
        this.pointer = j;
    }

    public void destroy() {
        finalize();
        this.pointer = 0L;
    }

    public native void finalize();

    public native Rect getBounds();

    public native Link[] getLinks();

    public native Separations getSeparations();

    public void run(Device device, Matrix matrix) {
        run(device, matrix, null);
    }

    public native void run(Device device, Matrix matrix, Cookie cookie);

    public native void runPageAnnots(Device device, Matrix matrix, Cookie cookie);

    public native void runPageContents(Device device, Matrix matrix, Cookie cookie);

    public native void runPageWidgets(Device device, Matrix matrix, Cookie cookie);

    public native Quad[] search(String str);

    public native byte[] textAsHtml();

    public DisplayList toDisplayList() {
        return toDisplayList(true);
    }

    public native DisplayList toDisplayList(boolean z);

    public Pixmap toPixmap(Matrix matrix, ColorSpace colorSpace, boolean z) {
        return toPixmap(matrix, colorSpace, z, true);
    }

    public native Pixmap toPixmap(Matrix matrix, ColorSpace colorSpace, boolean z, boolean z2);

    public StructuredText toStructuredText() {
        return toStructuredText(null);
    }

    public native StructuredText toStructuredText(String str);
}

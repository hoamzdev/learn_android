package com.hwa.excelllll.mupdf.fitz;

/* JADX INFO: loaded from: classes.dex */
public class DocumentWriter {
    private long pointer;

    static {
        Context.init();
    }

    public DocumentWriter(String str, String str2, String str3) {
        this.pointer = newNativeDocumentWriter(str, str2, str3);
    }

    private native long newNativeDocumentWriter(String str, String str2, String str3);

    public native Device beginPage(Rect rect);

    public native void close();

    public void destroy() {
        finalize();
        this.pointer = 0L;
    }

    public native void endPage();

    public native void finalize();
}

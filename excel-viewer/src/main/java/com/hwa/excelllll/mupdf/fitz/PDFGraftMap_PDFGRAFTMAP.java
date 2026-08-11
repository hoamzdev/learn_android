package com.hwa.excelllll.mupdf.fitz;

/* JADX INFO: loaded from: classes.dex */
public class PDFGraftMap {
    private long pointer;

    static {
        Context.init();
    }

    private PDFGraftMap(long j) {
        this.pointer = j;
    }

    public void destroy() {
        finalize();
        this.pointer = 0L;
    }

    public native void finalize();

    public native PDFObject graftObject(PDFObject pDFObject);
}

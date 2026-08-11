package com.hwa.excelllll.mupdf.fitz;

/* JADX INFO: loaded from: classes.dex */
public class Shade {
    private long pointer;

    static {
        Context.init();
    }

    private Shade(long j) {
        this.pointer = j;
    }

    public void destroy() {
        finalize();
        this.pointer = 0L;
    }

    public native void finalize();
}

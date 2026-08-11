package com.hwa.excelllll.mupdf.fitz;

/* JADX INFO: loaded from: classes.dex */
public class Cookie {
    private long pointer = newNative();

    static {
        Context.init();
    }

    private native long newNative();

    public native void abort();

    public void destroy() {
        finalize();
        this.pointer = 0L;
    }

    public native void finalize();
}

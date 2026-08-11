package com.hwa.excelllll.mupdf.fitz;

/* JADX INFO: loaded from: classes.dex */
public final class DrawDevice extends NativeDevice {
    static {
        Context.init();
    }

    public DrawDevice(Pixmap pixmap) {
        super(newNative(pixmap));
    }

    private static native long newNative(Pixmap pixmap);
}

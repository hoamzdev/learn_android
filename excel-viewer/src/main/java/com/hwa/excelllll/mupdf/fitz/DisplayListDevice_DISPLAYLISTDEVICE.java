package com.hwa.excelllll.mupdf.fitz;

/* JADX INFO: loaded from: classes.dex */
public final class DisplayListDevice extends NativeDevice {
    public DisplayListDevice(DisplayList displayList) {
        super(newNative(displayList));
    }

    private static native long newNative(DisplayList displayList);
}

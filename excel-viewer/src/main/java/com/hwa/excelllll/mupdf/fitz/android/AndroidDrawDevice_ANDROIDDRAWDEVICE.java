package com.hwa.excelllll.mupdf.fitz.android;

import android.graphics.Bitmap;
import com.artifex.mupdf.fitz.Context;
import com.artifex.mupdf.fitz.NativeDevice;

/* JADX INFO: loaded from: classes.dex */
public final class AndroidDrawDevice extends NativeDevice {
    static {
        Context.init();
    }

    public AndroidDrawDevice(Bitmap bitmap, int i, int i2, int i3, int i4, int i5, int i6) {
        super(0L);
        this.pointer = newNative(bitmap, i, i2, i3, i4, i5, i6);
    }

    private native long newNative(Bitmap bitmap, int i, int i2, int i3, int i4, int i5, int i6);
}

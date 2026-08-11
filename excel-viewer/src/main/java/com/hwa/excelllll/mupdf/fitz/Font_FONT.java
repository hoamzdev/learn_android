package com.hwa.excelllll.mupdf.fitz;

/* JADX INFO: loaded from: classes.dex */
public class Font {
    public static final int ADOBE_CNS = 0;
    public static final int ADOBE_GB = 1;
    public static final int ADOBE_JAPAN = 2;
    public static final int ADOBE_KOREA = 3;
    public static final int CYRILLIC = 2;
    public static final int GREEK = 1;
    public static final int LATIN = 0;
    private long pointer;

    static {
        Context.init();
    }

    public Font(String str) {
        this.pointer = newNative(str, 0);
    }

    private native long newNative(String str, int i);

    public float advanceGlyph(int i) {
        return advanceGlyph(i, false);
    }

    public native float advanceGlyph(int i, boolean z);

    public void destroy() {
        finalize();
        this.pointer = 0L;
    }

    public native int encodeCharacter(int i);

    public native void finalize();

    public native String getName();

    public String toString() {
        return "Font(" + getName() + ")";
    }

    private Font(long j) {
        this.pointer = j;
    }

    public Font(String str, int i) {
        this.pointer = newNative(str, i);
    }
}

package com.hwa.excelllll.mupdf.fitz;

/* JADX INFO: loaded from: classes.dex */
public class Text implements TextWalker {
    private long pointer;

    static {
        Context.init();
    }

    public Text() {
        this.pointer = newNative();
    }

    private native long newNative();

    public void destroy() {
        finalize();
        this.pointer = 0L;
    }

    public native void finalize();

    public native Rect getBounds(StrokeState strokeState, Matrix matrix);

    public void showGlyph(Font font, Matrix matrix, int i, int i2) {
        showGlyph(font, matrix, i, i2, false);
    }

    @Override // com.artifex.mupdf.fitz.TextWalker
    public native void showGlyph(Font font, Matrix matrix, int i, int i2, boolean z);

    public void showString(Font font, Matrix matrix, String str) {
        showString(font, matrix, str, false);
    }

    public native void showString(Font font, Matrix matrix, String str, boolean z);

    public native void walk(TextWalker textWalker);

    private Text(long j) {
        this.pointer = j;
    }
}

package com.hwa.excelllll.mupdf.fitz;

/* JADX INFO: loaded from: classes.dex */
public interface StructuredTextWalker {
    void beginLine(Rect rect, int i);

    void beginTextBlock(Rect rect);

    void endLine();

    void endTextBlock();

    void onChar(int i, Point point, Font font, float f, Quad quad);

    void onImageBlock(Rect rect, Matrix matrix, Image image);
}

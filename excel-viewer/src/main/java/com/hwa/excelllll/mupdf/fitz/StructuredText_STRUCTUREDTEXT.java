package com.hwa.excelllll.mupdf.fitz;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class StructuredText {
    public static final int SELECT_CHARS = 0;
    public static final int SELECT_LINES = 2;
    public static final int SELECT_WORDS = 1;
    private long pointer;

    public class TextBlock {
        public Rect bbox;
        public TextLine[] lines;

        public TextBlock() {
        }
    }

    public class TextChar {
        public int c;
        public Quad quad;

        public TextChar() {
        }

        public boolean isWhitespace() {
            return Character.isWhitespace(this.c);
        }
    }

    public class TextLine {
        public Rect bbox;
        public TextChar[] chars;

        public TextLine() {
        }
    }

    static {
        Context.init();
    }

    private StructuredText(long j) {
        this.pointer = j;
    }

    public native String copy(Point point, Point point2);

    public void destroy() {
        finalize();
        this.pointer = 0L;
    }

    public native void finalize();

    public TextBlock[] getBlocks() {
        a aVar = new a();
        walk(aVar);
        return aVar.a();
    }

    public native Quad[] highlight(Point point, Point point2);

    public native Quad[] search(String str);

    public native Quad snapSelection(Point point, Point point2, int i);

    public native void walk(StructuredTextWalker structuredTextWalker);

    public class a implements StructuredTextWalker {

        /* JADX INFO: renamed from: a, reason: collision with root package name */
        ArrayList<TextBlock> f4160a = new ArrayList<>();

        /* JADX INFO: renamed from: b, reason: collision with root package name */
        ArrayList<TextLine> f4161b;
        ArrayList<TextChar> c;
        Rect d;
        Rect e;

        public a() {
        }

        public TextBlock[] a() {
            return (TextBlock[]) this.f4160a.toArray(new TextBlock[0]);
        }

        @Override // com.artifex.mupdf.fitz.StructuredTextWalker
        public void beginLine(Rect rect, int i) {
            this.c = new ArrayList<>();
            this.d = rect;
        }

        @Override // com.artifex.mupdf.fitz.StructuredTextWalker
        public void beginTextBlock(Rect rect) {
            this.f4161b = new ArrayList<>();
            this.e = rect;
        }

        @Override // com.artifex.mupdf.fitz.StructuredTextWalker
        public void endLine() {
            TextLine textLine = StructuredText.this.new TextLine();
            textLine.bbox = this.d;
            textLine.chars = (TextChar[]) this.c.toArray(new TextChar[0]);
            this.f4161b.add(textLine);
        }

        @Override // com.artifex.mupdf.fitz.StructuredTextWalker
        public void endTextBlock() {
            TextBlock textBlock = StructuredText.this.new TextBlock();
            textBlock.bbox = this.e;
            textBlock.lines = (TextLine[]) this.f4161b.toArray(new TextLine[0]);
            this.f4160a.add(textBlock);
        }

        @Override // com.artifex.mupdf.fitz.StructuredTextWalker
        public void onChar(int i, Point point, Font font, float f, Quad quad) {
            TextChar textChar = StructuredText.this.new TextChar();
            textChar.c = i;
            textChar.quad = quad;
            this.c.add(textChar);
        }

        @Override // com.artifex.mupdf.fitz.StructuredTextWalker
        public void onImageBlock(Rect rect, Matrix matrix, Image image) {
        }
    }
}

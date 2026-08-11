package com.hwa.excelllll.mupdf.fitz;

import java.util.Date;

/* JADX INFO: loaded from: classes.dex */
public class PDFAnnotation {
    public static final int IS_HIDDEN = 2;
    public static final int IS_INVISIBLE = 1;
    public static final int IS_LOCKED = 128;
    public static final int IS_LOCKED_CONTENTS = 512;
    public static final int IS_NO_ROTATE = 16;
    public static final int IS_NO_VIEW = 32;
    public static final int IS_NO_ZOOM = 8;
    public static final int IS_PRINT = 4;
    public static final int IS_READ_ONLY = 64;
    public static final int IS_TOGGLE_NO_VIEW = 256;
    public static final int LINE_ENDING_BUTT = 6;
    public static final int LINE_ENDING_CIRCLE = 2;
    public static final int LINE_ENDING_CLOSED_ARROW = 5;
    public static final int LINE_ENDING_DIAMOND = 3;
    public static final int LINE_ENDING_NONE = 0;
    public static final int LINE_ENDING_OPEN_ARROW = 4;
    public static final int LINE_ENDING_R_CLOSED_ARROW = 8;
    public static final int LINE_ENDING_R_OPEN_ARROW = 7;
    public static final int LINE_ENDING_SLASH = 9;
    public static final int LINE_ENDING_SQUARE = 1;
    public static final int TYPE_3D = 25;
    public static final int TYPE_CARET = 14;
    public static final int TYPE_CIRCLE = 5;
    public static final int TYPE_FILE_ATTACHMENT = 17;
    public static final int TYPE_FREE_TEXT = 2;
    public static final int TYPE_HIGHLIGHT = 8;
    public static final int TYPE_INK = 15;
    public static final int TYPE_LINE = 3;
    public static final int TYPE_LINK = 1;
    public static final int TYPE_MOVIE = 19;
    public static final int TYPE_POLYGON = 6;
    public static final int TYPE_POLY_LINE = 7;
    public static final int TYPE_POPUP = 16;
    public static final int TYPE_PRINTER_MARK = 22;
    public static final int TYPE_REDACT = 12;
    public static final int TYPE_SCREEN = 21;
    public static final int TYPE_SOUND = 18;
    public static final int TYPE_SQUARE = 4;
    public static final int TYPE_SQUIGGLY = 10;
    public static final int TYPE_STAMP = 13;
    public static final int TYPE_STRIKE_OUT = 11;
    public static final int TYPE_TEXT = 0;
    public static final int TYPE_TRAP_NET = 23;
    public static final int TYPE_UNDERLINE = 9;
    public static final int TYPE_UNKNOWN = -1;
    public static final int TYPE_WATERMARK = 24;
    public static final int TYPE_WIDGET = 20;
    private long pointer;

    static {
        Context.init();
    }

    public PDFAnnotation(long j) {
        this.pointer = j;
    }

    public void destroy() {
        finalize();
        this.pointer = 0L;
    }

    public boolean equals(PDFAnnotation pDFAnnotation) {
        return this.pointer == pDFAnnotation.pointer;
    }

    public native void eventBlur();

    public native void eventDown();

    public native void eventEnter();

    public native void eventExit();

    public native void eventFocus();

    public native void eventUp();

    public native void finalize();

    public native String getAuthor();

    public native float getBorder();

    public native Rect getBounds();

    public native float[] getColor();

    public native String getContents();

    public native int getFlags();

    public native String getIcon();

    public native float[][] getInkList();

    public native float[] getInteriorColor();

    public native int[] getLineEndingStyles();

    public Date getModificationDate() {
        return new Date(getModificationDateNative());
    }

    public native long getModificationDateNative();

    public native float[][] getQuadPoints();

    public native Rect getRect();

    public native int getType();

    public native float[] getVertices();

    public native boolean isOpen();

    public native void run(Device device, Matrix matrix, Cookie cookie);

    public native void setAuthor(String str);

    public native void setBorder(float f);

    public native void setColor(float[] fArr);

    public native void setContents(String str);

    public native void setFlags(int i);

    public native void setIcon(String str);

    public native void setInkList(float[][] fArr);

    public native void setInteriorColor(float[] fArr);

    public native void setIsOpen(boolean z);

    public native void setLineEndingStyles(int i, int i2);

    public void setLineEndingStyles(int[] iArr) {
        setLineEndingStyles(iArr[0], iArr[1]);
    }

    public native void setModificationDate(long j);

    public void setModificationDate(Date date) {
        setModificationDate(date.getTime());
    }

    public native void setQuadPoints(float[][] fArr);

    public native void setRect(Rect rect);

    public native void setVertices(float[] fArr);

    public native DisplayList toDisplayList();

    public native Pixmap toPixmap(Matrix matrix, ColorSpace colorSpace, boolean z);

    public native boolean update();

    public boolean equals(long j) {
        return this.pointer == j;
    }
}

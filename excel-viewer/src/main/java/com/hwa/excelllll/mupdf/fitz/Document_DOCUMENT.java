package com.hwa.excelllll.mupdf.fitz;

/* JADX INFO: loaded from: classes.dex */
public class Document {
    public static final String META_ENCRYPTION = "encryption";
    public static final String META_FORMAT = "format";
    public static final String META_INFO_AUTHOR = "info:Author";
    public static final String META_INFO_TITLE = "info:Title";
    protected long pointer;

    static {
        Context.init();
    }

    public Document(long j) {
        this.pointer = j;
    }

    public static Document openDocument(SeekableInputStream seekableInputStream, String str) {
        return openNativeWithStream(seekableInputStream, str);
    }

    public static native Document openNativeWithBuffer(byte[] bArr, String str);

    public static native Document openNativeWithPath(String str);

    public static native Document openNativeWithStream(SeekableInputStream seekableInputStream, String str);

    public static native boolean recognize(String str);

    public native boolean authenticatePassword(String str);

    public native int countPages();

    public void destroy() {
        finalize();
        this.pointer = 0L;
    }

    public native void finalize();

    public native int findBookmark(long j);

    public native String getMetaData(String str);

    public boolean isPDF() {
        return false;
    }

    public native boolean isReflowable();

    public native boolean isUnencryptedPDF();

    public native void layout(float f, float f2, float f3);

    public native Outline[] loadOutline();

    public native Page loadPage(int i);

    public native long makeBookmark(int i);

    public native boolean needsPassword();

    public static Document openDocument(String str) {
        return openNativeWithPath(str);
    }

    public static Document openDocument(byte[] bArr, String str) {
        return openNativeWithBuffer(bArr, str);
    }
}

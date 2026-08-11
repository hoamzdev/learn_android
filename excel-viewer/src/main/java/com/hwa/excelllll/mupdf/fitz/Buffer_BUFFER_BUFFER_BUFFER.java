package com.hwa.excelllll.mupdf.fitz;

/* JADX INFO: loaded from: classes.dex */
public class Buffer {
    private long pointer;

    static {
        Context.init();
    }

    public Buffer() {
        this.pointer = newNativeBuffer(0);
    }

    private native long newNativeBuffer(int i);

    public void destroy() {
        finalize();
        this.pointer = 0L;
    }

    public native void finalize();

    public native int getLength();

    public native int readByte(int i);

    public native int readBytes(int i, byte[] bArr);

    public native int readBytesInto(int i, byte[] bArr, int i2, int i3);

    public native void save(String str);

    public native void writeBuffer(Buffer buffer);

    public native void writeByte(byte b2);

    public native void writeBytes(byte[] bArr);

    public native void writeBytesFrom(byte[] bArr, int i, int i2);

    public native void writeLine(String str);

    public native void writeLines(String... strArr);

    public native void writeRune(int i);

    public Buffer(int i) {
        this.pointer = newNativeBuffer(i);
    }
}

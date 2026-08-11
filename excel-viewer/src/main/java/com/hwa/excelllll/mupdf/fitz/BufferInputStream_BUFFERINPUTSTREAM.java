package com.hwa.excelllll.mupdf.fitz;

import defpackage.d8;
import java.io.IOException;
import java.io.InputStream;

/* JADX INFO: loaded from: classes.dex */
public class BufferInputStream extends InputStream {
    protected Buffer buffer;
    protected int position = 0;
    protected int resetPosition = -1;

    public BufferInputStream(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override // java.io.InputStream
    public int available() {
        return this.buffer.getLength();
    }

    @Override // java.io.InputStream
    public void mark(int i) {
        this.resetPosition = this.position;
    }

    @Override // java.io.InputStream
    public boolean markSupported() {
        return true;
    }

    @Override // java.io.InputStream
    public int read() {
        int i = this.buffer.readByte(this.position);
        if (i >= 0) {
            this.position++;
        }
        return i;
    }

    @Override // java.io.InputStream
    public void reset() throws IOException {
        int i = this.resetPosition;
        if (i < 0) {
            d8.h("cannot reset because mark never set");
        } else if (i < this.buffer.getLength()) {
            this.position = this.resetPosition;
        } else {
            d8.h("cannot reset because mark set outside of buffer");
        }
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) {
        int bytes = this.buffer.readBytes(this.position, bArr);
        if (bytes >= 0) {
            this.position += bytes;
        }
        return bytes;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) {
        int bytesInto = this.buffer.readBytesInto(this.position, bArr, i, i2);
        if (bytesInto >= 0) {
            this.position += bytesInto;
        }
        return bytesInto;
    }
}

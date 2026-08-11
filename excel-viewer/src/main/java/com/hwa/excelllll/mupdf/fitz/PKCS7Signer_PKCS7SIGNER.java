package com.hwa.excelllll.mupdf.fitz;

import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public abstract class PKCS7Signer {
    private ByteBuffer buffer;
    private long pointer;

    static {
        Context.init();
    }

    private native boolean create(ByteBuffer byteBuffer);

    public abstract void begin();

    public boolean createSigner(ByteBuffer byteBuffer) {
        this.buffer = byteBuffer;
        return create(byteBuffer);
    }

    public abstract void data(ByteBuffer byteBuffer, int i);

    public abstract int maxDigest();

    public abstract PKCS7DesignatedName name();

    public abstract ByteBuffer sign();
}

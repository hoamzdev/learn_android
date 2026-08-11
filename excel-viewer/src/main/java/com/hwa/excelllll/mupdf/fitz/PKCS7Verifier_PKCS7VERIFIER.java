package com.hwa.excelllll.mupdf.fitz;

import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes.dex */
public abstract class PKCS7Verifier {
    public static final int PKCS7VerifierDigestFailure = 3;
    public static final int PKCS7VerifierNoCertificate = 2;
    public static final int PKCS7VerifierNoSignature = 1;
    public static final int PKCS7VerifierNotTrusted = 6;
    public static final int PKCS7VerifierOK = 0;
    public static final int PKCS7VerifierSelfSigned = 4;
    public static final int PKCS7VerifierSelfSignedInChain = 5;
    public static final int PKCS7VerifierUnknown = -1;
    private ByteBuffer buffer;
    private long pointer;

    static {
        Context.init();
    }

    private native boolean create(ByteBuffer byteBuffer);

    public abstract void begin();

    public boolean createVerifier(ByteBuffer byteBuffer) {
        this.buffer = byteBuffer;
        return create(byteBuffer);
    }

    public abstract void data(ByteBuffer byteBuffer, int i);

    public abstract PKCS7DesignatedName name(ByteBuffer byteBuffer);

    public abstract void verify(ByteBuffer byteBuffer, int i, int i2);
}

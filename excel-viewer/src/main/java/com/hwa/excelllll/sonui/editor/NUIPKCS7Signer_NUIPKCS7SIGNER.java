package com.hwa.excelllll.sonui.editor;

import com.artifex.mupdf.fitz.PKCS7Signer;

/* JADX INFO: loaded from: classes.dex */
public abstract class NUIPKCS7Signer extends PKCS7Signer {

    public interface NUIPKCS7SignerListener {
        void onCancel();

        void onSignatureReady();
    }

    public abstract void doSign(NUIPKCS7SignerListener nUIPKCS7SignerListener);
}

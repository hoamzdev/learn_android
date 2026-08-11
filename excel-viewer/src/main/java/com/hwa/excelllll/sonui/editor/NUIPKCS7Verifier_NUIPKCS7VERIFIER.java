package com.hwa.excelllll.sonui.editor;

import com.artifex.mupdf.fitz.PKCS7Verifier;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public abstract class NUIPKCS7Verifier extends PKCS7Verifier {

    public interface NUIPKCS7VerifierListener {
        void onInitComplete();

        void onVerifyResult(Map<String, String> map, int i, int i2);
    }

    public abstract void certificateUpdated();

    public abstract void doVerify(NUIPKCS7VerifierListener nUIPKCS7VerifierListener);

    public abstract void presentResults(HashMap<String, String> map, int i, int i2);
}

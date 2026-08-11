package com.hwa.excelllll.mupdf.fitz;

import defpackage.a7;

/* JADX INFO: loaded from: classes.dex */
public class Context {
    private static boolean inited;

    static {
        init();
    }

    public static void init() {
        if (inited) {
            return;
        }
        inited = true;
        try {
            try {
                System.loadLibrary("mupdf_java");
            } catch (UnsatisfiedLinkError unused) {
                System.loadLibrary("mupdf_java64");
            }
        } catch (UnsatisfiedLinkError unused2) {
            System.loadLibrary("mupdf_java32");
        }
        if (initNative() >= 0) {
            return;
        }
        a7.t("cannot initialize mupdf library");
    }

    private static native int initNative();
}

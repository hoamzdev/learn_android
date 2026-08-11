package com.hwa.excelllll.solib;

import com.artifex.mupdf.fitz.Cookie;

/* JADX INFO: loaded from: classes.dex */
public class f extends SORender {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private Cookie f4232a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private boolean f4233b;

    public f(Cookie cookie) {
        super(0L);
        this.f4233b = false;
        this.f4232a = cookie;
    }

    @Override // com.artifex.solib.SORender
    public void abort() {
        Cookie cookie = this.f4232a;
        if (cookie != null) {
            cookie.abort();
            this.f4233b = true;
        }
    }

    @Override // com.artifex.solib.SORender
    public void destroy() {
    }

    public void finalize() throws Throwable {
    }
}

package com.hwa.excelllll.mupdf.fitz;

import defpackage.h;

/* JADX INFO: loaded from: classes.dex */
public class Link {
    public Rect bounds;
    public int page;
    public String uri;

    public Link(Rect rect, int i, String str) {
        this.bounds = rect;
        this.page = i;
        this.uri = str;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Link(b=");
        sb.append(this.bounds);
        sb.append(",page=");
        sb.append(this.page);
        sb.append(",uri=");
        return h.p(sb, this.uri, ")");
    }
}

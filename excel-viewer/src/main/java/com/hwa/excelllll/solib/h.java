package com.hwa.excelllll.solib;

import android.graphics.Rect;
import com.artifex.mupdf.fitz.PDFAnnotation;
import com.artifex.mupdf.fitz.PDFWidget;
import com.artifex.mupdf.fitz.PKCS7Signer;
import com.artifex.mupdf.fitz.PKCS7Verifier;
import com.artifex.mupdf.fitz.Quad;

/* JADX INFO: loaded from: classes.dex */
public class h {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    protected PDFWidget f4236a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    protected long f4237b = -1;

    public h(PDFWidget pDFWidget) {
        this.f4236a = pDFWidget;
    }

    public boolean a(String str) {
        if (!this.f4236a.isEditing() && str != null && str.equals("")) {
            this.f4236a.setEditing(true);
            this.f4236a.setTextValue("");
            this.f4236a.setEditing(false);
            return true;
        }
        PDFWidget pDFWidget = this.f4236a;
        if (pDFWidget == null) {
            return false;
        }
        int textFormat = pDFWidget.getTextFormat();
        PDFWidget pDFWidget2 = this.f4236a;
        boolean value = textFormat == 1 ? pDFWidget2.setValue(str) : pDFWidget2.setTextValue(str);
        this.f4236a.update();
        return value;
    }

    public String b() {
        PDFWidget pDFWidget = this.f4236a;
        if (pDFWidget != null) {
            return pDFWidget.getValue();
        }
        return null;
    }

    public Rect c() {
        com.artifex.mupdf.fitz.Rect bounds = this.f4236a.getBounds();
        if (bounds != null) {
            return new Rect((int) bounds.x0, (int) bounds.y0, (int) bounds.x1, (int) bounds.y1);
        }
        return null;
    }

    public String[] d() {
        PDFWidget pDFWidget = this.f4236a;
        if (pDFWidget != null) {
            return pDFWidget.getOptions();
        }
        return null;
    }

    public Rect[] e() {
        PDFWidget pDFWidget = this.f4236a;
        if (pDFWidget == null) {
            return null;
        }
        Quad[] quadArrTextQuads = pDFWidget.textQuads();
        Rect[] rectArr = new Rect[quadArrTextQuads.length];
        for (int i = 0; i < quadArrTextQuads.length; i++) {
            com.artifex.mupdf.fitz.Rect rect = quadArrTextQuads[i].toRect();
            rectArr[i] = new Rect((int) rect.x0, (int) rect.y0, (int) rect.x1, (int) rect.y1);
        }
        return rectArr;
    }

    public int f() {
        PDFWidget pDFWidget = this.f4236a;
        if (pDFWidget != null) {
            return pDFWidget.getMaxLen();
        }
        return 0;
    }

    public int g() {
        PDFWidget pDFWidget = this.f4236a;
        if (pDFWidget != null) {
            return pDFWidget.getTextFormat();
        }
        return 0;
    }

    public boolean h() {
        PDFWidget pDFWidget = this.f4236a;
        return (pDFWidget == null || (pDFWidget.getFieldFlags() & PDFWidget.PDF_TX_FIELD_IS_MULTILINE) == 0) ? false : true;
    }

    public boolean i() {
        PDFWidget pDFWidget = this.f4236a;
        if (pDFWidget != null) {
            return pDFWidget.toggle();
        }
        return false;
    }

    public boolean j() {
        PDFWidget pDFWidget = this.f4236a;
        if (pDFWidget != null) {
            return pDFWidget.isSigned();
        }
        return false;
    }

    public long k() {
        return this.f4237b;
    }

    public void a(boolean z) {
        PDFWidget pDFWidget = this.f4236a;
        if (pDFWidget != null) {
            pDFWidget.setEditing(z);
        }
    }

    public boolean a(PKCS7Signer pKCS7Signer) {
        PDFWidget pDFWidget = this.f4236a;
        boolean zSign = pDFWidget != null ? pDFWidget.sign(pKCS7Signer) : false;
        if (zSign) {
            this.f4237b = System.currentTimeMillis();
        }
        return zSign;
    }

    public boolean a(PKCS7Verifier pKCS7Verifier) {
        PDFWidget pDFWidget = this.f4236a;
        if (pDFWidget != null) {
            return pDFWidget.verify(pKCS7Verifier);
        }
        return false;
    }

    public boolean a(h hVar) {
        if (hVar != null) {
            return this.f4236a.equals((PDFAnnotation) hVar.f4236a);
        }
        return false;
    }

    public int a() {
        PDFWidget pDFWidget = this.f4236a;
        if (pDFWidget != null) {
            return pDFWidget.getFieldType();
        }
        return 0;
    }
}

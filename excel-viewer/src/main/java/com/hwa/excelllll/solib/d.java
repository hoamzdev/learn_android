package com.hwa.excelllll.solib;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.util.Log;
import com.artifex.mupdf.fitz.Document;
import com.artifex.mupdf.fitz.PDFWidget;

/* JADX INFO: loaded from: classes.dex */
public class d extends k {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private static d f4225b;

    private d() {
    }

    public static d a(Activity activity) {
        if (f4225b == null) {
            Log.w("SOLib", "creating new SOLib");
            f4225b = new d();
            if (k.f4240a == null) {
                k.a(activity);
            }
        }
        return f4225b;
    }

    public void finalize() throws Throwable {
        super.finalize();
    }

    @Override // com.artifex.solib.k
    public SODoc a(final String str, final l lVar, Context context) {
        final c cVar = new c(Looper.myLooper(), lVar, context);
        cVar.c();
        cVar.d().a(new r.a() { // from class: com.artifex.solib.d.1
            private boolean e = false;
            private boolean f = false;

            @Override // com.artifex.solib.r.a
            public void a() {
                Document documentC = c.c(str);
                if (documentC == null) {
                    return;
                }
                this.e = true;
                cVar.a(documentC);
                cVar.a(str);
                if (documentC.needsPassword()) {
                    this.f = true;
                } else {
                    cVar.v();
                }
            }

            @Override // com.artifex.solib.r.a, java.lang.Runnable
            public void run() {
                if (!this.e) {
                    l lVar2 = lVar;
                    if (lVar2 != null) {
                        lVar2.a(4, 0);
                        return;
                    }
                    return;
                }
                if (!this.f) {
                    cVar.w();
                    return;
                }
                l lVar3 = lVar;
                if (lVar3 != null) {
                    lVar3.a(PDFWidget.PDF_TX_FIELD_IS_MULTILINE, 0);
                }
            }
        });
        return cVar;
    }

    @Override // com.artifex.solib.k
    public SOBitmap a(int i, int i2) {
        return new b(i, i2);
    }
}

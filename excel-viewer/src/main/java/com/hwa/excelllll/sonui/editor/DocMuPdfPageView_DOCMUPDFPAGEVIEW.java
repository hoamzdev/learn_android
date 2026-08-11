package com.hwa.excelllll.sonui.editor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;
import com.artifex.solib.ConfigOptions;
import com.artifex.solib.SODoc;
import com.artifex.solib.c;
import com.artifex.solib.e;
import com.artifex.solib.h;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class DocMuPdfPageView extends DocPdfPageView {

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private final String f4544b;
    private Rect c;
    private Paint d;
    private Paint e;
    private h[] f;
    private Rect[] g;
    private PDFFormEditor h;
    private h i;
    private int j;
    protected boolean mFullscreen;

    public DocMuPdfPageView(Context context, SODoc sODoc) {
        super(context, sODoc);
        this.f4544b = "DocPdfPageView";
        this.c = new Rect();
        this.d = null;
        this.e = null;
        this.h = null;
        this.i = null;
        this.j = -1;
        this.mFullscreen = false;
        Paint paint = new Paint();
        this.d = paint;
        paint.setColor(context.getColor(R.color.sodk_editor_text_highlight_color));
        Paint paint2 = this.d;
        Paint.Style style = Paint.Style.FILL;
        paint2.setStyle(style);
        this.d.setAlpha(getContext().getResources().getInteger(R.integer.sodk_editor_text_highlight_alpha));
        Paint paint3 = new Paint();
        this.e = paint3;
        paint3.setColor(context.getColor(R.color.sodk_editor_form_field_color));
        this.e.setStyle(style);
        this.e.setAlpha(getContext().getResources().getInteger(R.integer.sodk_editor_form_field_alpha));
    }

    private void a(final h hVar, boolean z) {
        if (a()) {
            if (z && ConfigOptions.a().y()) {
                if (hVar.j()) {
                    final NUIPKCS7Verifier verifier = Utilities.getVerifier((Activity) getContext());
                    if (verifier != null) {
                        NUIPKCS7Verifier.NUIPKCS7VerifierListener nUIPKCS7VerifierListener = new NUIPKCS7Verifier.NUIPKCS7VerifierListener() { // from class: com.artifex.sonui.editor.DocMuPdfPageView.4
                            @Override // com.artifex.sonui.editor.NUIPKCS7Verifier.NUIPKCS7VerifierListener
                            public void onInitComplete() {
                                if (hVar.a(verifier)) {
                                    return;
                                }
                                onVerifyResult(new HashMap(), -1, 0);
                            }

                            @Override // com.artifex.sonui.editor.NUIPKCS7Verifier.NUIPKCS7VerifierListener
                            public void onVerifyResult(Map<String, String> map, int i, int i2) {
                            }
                        };
                        if (hVar.k() > ((c) getDoc()).g()) {
                            Toast.makeText(getContext(), R.string.sodk_editor_unsaved_signatures, 1).show();
                        } else {
                            verifier.doVerify(nUIPKCS7VerifierListener);
                        }
                    }
                } else {
                    final NUIPKCS7Signer signer = Utilities.getSigner((Activity) getContext());
                    if (signer != null) {
                        signer.doSign(new NUIPKCS7Signer.NUIPKCS7SignerListener() { // from class: com.artifex.sonui.editor.DocMuPdfPageView.3
                            @Override // com.artifex.sonui.editor.NUIPKCS7Signer.NUIPKCS7SignerListener
                            public void onSignatureReady() {
                                boolean zA = hVar.a(signer);
                                ((c) DocMuPdfPageView.this.getDoc()).a(true);
                                if (zA) {
                                    return;
                                }
                                onCancel();
                            }

                            @Override // com.artifex.sonui.editor.NUIPKCS7Signer.NUIPKCS7SignerListener
                            public void onCancel() {
                            }
                        });
                    }
                }
            }
            c();
            invalidate();
        }
    }

    private void b(h hVar, boolean z) {
        if (a()) {
            final DocView docView = getDocView();
            docView.setShowKeyboardListener(new DocView.a() { // from class: com.artifex.sonui.editor.DocMuPdfPageView.5
                @Override // com.artifex.sonui.editor.DocView.a
                public void a(boolean z2) {
                    if (z2) {
                        DocMuPdfPageView.this.c();
                    }
                }
            });
            PDFFormEditor pDFFormEditor = (PDFFormEditor) ((Activity) getContext()).findViewById(R.id.pdf_form_checkbox_editor_layout);
            this.h = pDFFormEditor;
            pDFFormEditor.start(this, getPageNumber(), (c) getDoc(), docView, hVar, this.g[this.j], new PDFFormEditor.EditorListener() { // from class: com.artifex.sonui.editor.DocMuPdfPageView.6
                @Override // com.artifex.sonui.editor.PDFFormEditor.EditorListener
                public void onStopped() {
                    docView.setShowKeyboardListener(null);
                    if (DocMuPdfPageView.this.a()) {
                        DocMuPdfPageView.this.b();
                    }
                }
            });
            if (z) {
                ((PDFFormCheckboxEditor) this.h).toggle();
            }
            c();
            invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        PDFFormEditor pDFFormEditor = this.h;
        if (pDFFormEditor != null) {
            pDFFormEditor.scrollIntoView();
        }
    }

    private boolean d() {
        return NUIDocView.currentNUIDocView().isKeyboardVisible();
    }

    @Override // com.artifex.sonui.editor.DocPageView
    public boolean canDoubleTap(int i, int i2) {
        return ((e) this.mPage).a(screenToPage(new Point(i, i2)), 12) == -1;
    }

    @Override // com.artifex.sonui.editor.DocPageView
    public void changePage(int i) {
        super.changePage(i);
        collectFormFields();
    }

    public void collectFormFields() {
        e eVar = (e) getPage();
        c cVar = (c) getDoc();
        if (eVar == null || cVar == null) {
            return;
        }
        h[] hVarArrO = eVar.o();
        this.f = hVarArrO;
        if (hVarArrO == null || hVarArrO.length <= 0) {
            return;
        }
        this.g = new Rect[hVarArrO.length];
        int i = 0;
        for (h hVar : hVarArrO) {
            this.g[i] = hVar.c();
            i++;
        }
    }

    @Override // android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        PDFFormEditor pDFFormEditor = this.h;
        if (pDFFormEditor != null && pDFFormEditor.dispatchTouchEvent(motionEvent)) {
            return true;
        }
        a();
        this.i = null;
        this.j = -1;
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // com.artifex.sonui.editor.DocPdfPageView
    public void drawSearchHighlight(Canvas canvas) {
        Rect rectA;
        e eVar = (e) getPage();
        if (eVar == null || (rectA = eVar.a()) == null) {
            return;
        }
        Rect rectPageToView = pageToView(new RectF(rectA.left, rectA.top, rectA.right, rectA.bottom));
        this.c.set(rectPageToView.left, rectPageToView.top, rectPageToView.right, rectPageToView.bottom);
        canvas.drawRect(this.c, this.d);
    }

    @Override // com.artifex.sonui.editor.DocPdfPageView
    public void drawSelection(Canvas canvas) {
        e eVar;
        h[] hVarArr;
        if (this.mPage == null || (eVar = (e) getPage()) == null) {
            return;
        }
        Rect rectE = eVar.e();
        if (rectE != null) {
            Rect rectPageToView = pageToView(new RectF(rectE.left, rectE.top, rectE.right, rectE.bottom));
            this.c.set(rectPageToView.left, rectPageToView.top, rectPageToView.right, rectPageToView.bottom);
            canvas.drawRect(this.c, this.d);
        }
        Rect[] rectArrF = eVar.f();
        int i = 0;
        if (rectArrF != null && rectArrF.length > 0) {
            for (Rect rect : rectArrF) {
                this.c.set(rect);
                Rect rect2 = this.c;
                pageToView(rect2, rect2);
                canvas.drawRect(this.c, this.d);
            }
        }
        if (!ConfigOptions.a().x() || this.mFullscreen || (hVarArr = this.f) == null || hVarArr.length <= 0) {
            return;
        }
        while (true) {
            h[] hVarArr2 = this.f;
            if (i >= hVarArr2.length) {
                return;
            }
            if (!hVarArr2[i].a(this.i)) {
                this.c.set(this.g[i]);
                Rect rect3 = this.c;
                pageToView(rect3, rect3);
                canvas.drawRect(this.c, this.e);
            }
            i++;
        }
    }

    @Override // com.artifex.sonui.editor.DocPageView
    public void endRenderPass() {
        super.endRenderPass();
        PDFFormEditor pDFFormEditor = this.h;
        if (pDFFormEditor != null) {
            pDFFormEditor.onRenderComplete();
        }
    }

    @Override // com.artifex.sonui.editor.DocPageView
    public void onFullscreen(boolean z) {
        this.mFullscreen = z;
        if (z) {
            ConfigOptions configOptionsA = ConfigOptions.a();
            if (configOptionsA.x() && configOptionsA.c()) {
                a();
                invalidate();
            }
        }
    }

    public void onReloadFile() {
        dropPage();
        changePage(getPageNumber());
    }

    @Override // com.artifex.sonui.editor.DocPageView
    public boolean onSingleTap(int i, int i2, boolean z, DocPageView.ExternalLinkListener externalLinkListener) {
        boolean zA;
        int iA;
        ConfigOptions configOptionsA = ConfigOptions.a();
        ((c) getDoc()).a(-1, -1);
        Point pointScreenToPage = screenToPage(i, i2);
        if (configOptionsA.x() && configOptionsA.c()) {
            zA = a();
            invalidate();
        } else {
            zA = true;
        }
        if (tryHyperlink(pointScreenToPage, externalLinkListener)) {
            return true;
        }
        if (configOptionsA.x() && configOptionsA.c() && zA) {
            h hVarA = a(pointScreenToPage.x, pointScreenToPage.y);
            if (hVarA != null) {
                a(hVarA, true, pointScreenToPage);
                return true;
            }
            Utilities.hideKeyboard(getContext());
        }
        if (configOptionsA.c()) {
            e eVar = (e) this.mPage;
            if (NUIDocView.currentNUIDocView().isRedactionMode() && configOptionsA.z()) {
                iA = eVar.a(pointScreenToPage, 12);
            } else {
                int iA2 = eVar.a(pointScreenToPage, 0);
                if (iA2 < 0) {
                    iA2 = eVar.a(pointScreenToPage, 8);
                }
                iA = iA2 < 0 ? eVar.a(pointScreenToPage, -1) : iA2;
            }
            if (iA >= 0) {
                eVar.b(iA);
                return true;
            }
        }
        return false;
    }

    @Override // com.artifex.sonui.editor.DocPageView
    public void setOrigin() {
        Rect rect = new Rect();
        rect.set(this.mPageRect);
        int[] iArr = new int[2];
        ((Activity) getContext()).getWindow().getDecorView().getLocationOnScreen(iArr);
        rect.offset(-iArr[0], -iArr[1]);
        this.mRenderOrigin.set(rect.left, rect.top);
    }

    @Override // com.artifex.sonui.editor.DocPageView, com.artifex.solib.SOPageListener
    public void update(RectF rectF) {
        if (!isFinished() && isShown()) {
            invalidate();
        }
    }

    private void b(h hVar) {
        if (a()) {
            final DocView docView = getDocView();
            docView.setShowKeyboardListener(new DocView.a() { // from class: com.artifex.sonui.editor.DocMuPdfPageView.7
                @Override // com.artifex.sonui.editor.DocView.a
                public void a(boolean z) {
                    if (z) {
                        DocMuPdfPageView.this.c();
                    }
                }
            });
            PDFFormEditor pDFFormEditor = (PDFFormEditor) ((Activity) getContext()).findViewById(R.id.pdf_form_text_editor_layout);
            this.h = pDFFormEditor;
            pDFFormEditor.start(this, getPageNumber(), (c) getDoc(), docView, hVar, this.g[this.j], new PDFFormEditor.EditorListener() { // from class: com.artifex.sonui.editor.DocMuPdfPageView.8
                @Override // com.artifex.sonui.editor.PDFFormEditor.EditorListener
                public void onStopped() {
                    docView.setShowKeyboardListener(null);
                    if (DocMuPdfPageView.this.a()) {
                        DocMuPdfPageView.this.b();
                    }
                }
            });
            if (d()) {
                c();
            }
            invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        h[] hVarArr;
        int i;
        int i2 = this.j;
        if (i2 >= 0) {
            do {
                int i3 = this.j + 1;
                this.j = i3;
                hVarArr = this.f;
                if (i3 >= hVarArr.length) {
                    this.j = 0;
                }
                i = this.j;
                if (i == i2) {
                    return;
                }
            } while (hVarArr[i].a() == 1);
            this.i = this.f[this.j];
            new Handler().post(new Runnable() { // from class: com.artifex.sonui.editor.DocMuPdfPageView.1
                @Override // java.lang.Runnable
                public void run() {
                    DocMuPdfPageView docMuPdfPageView = DocMuPdfPageView.this;
                    docMuPdfPageView.a(docMuPdfPageView.i, false, null);
                }
            });
        }
    }

    private void a(final h hVar) {
        String[] strArrD = hVar.d();
        String strB = hVar.b();
        if (strArrD == null || strArrD.length <= 0) {
            return;
        }
        ListWheelDialog.show(getContext(), strArrD, strB, new ListWheelDialog.a() { // from class: com.artifex.sonui.editor.DocMuPdfPageView.2
            @Override // com.artifex.sonui.editor.ListWheelDialog.a
            public void a(String str) {
                hVar.a(str);
                ((c) DocMuPdfPageView.this.getDoc()).a(DocMuPdfPageView.this.getPageNumber());
                DocMuPdfPageView.this.invalidate();
                if (ListWheelDialog.wasDismissedWithButton() || !DocMuPdfPageView.this.a()) {
                    return;
                }
                DocMuPdfPageView.this.b();
            }

            @Override // com.artifex.sonui.editor.ListWheelDialog.a
            public void a() {
                DocMuPdfPageView.this.invalidate();
                if (ListWheelDialog.wasDismissedWithButton() || !DocMuPdfPageView.this.a()) {
                    return;
                }
                DocMuPdfPageView.this.b();
            }
        });
        if (d()) {
            c();
        }
        invalidate();
    }

    private h a(int i, int i2) {
        this.i = null;
        this.j = -1;
        h[] hVarArr = this.f;
        if (hVarArr != null && hVarArr.length > 0) {
            int i3 = 0;
            while (true) {
                h[] hVarArr2 = this.f;
                if (i3 >= hVarArr2.length) {
                    break;
                }
                h hVar = hVarArr2[i3];
                if (this.g[i3].contains(i, i2)) {
                    if (hVar.a() != 1) {
                        this.i = hVar;
                        this.j = i3;
                    }
                    return hVar;
                }
                i3++;
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(h hVar, boolean z, Point point) {
        c cVar = (c) getDoc();
        int iA = hVar.a();
        switch (iA) {
            case 1:
                if (a() && z && point != null) {
                    ((e) this.mPage).a(point.x, point.y);
                }
                break;
            case 2:
            case 5:
                b(hVar, z);
                break;
            case 3:
            case 4:
                a(hVar);
                break;
            case 6:
                a(hVar, z);
                break;
            case 7:
                b(hVar);
                break;
            default:
                a();
                Log.i("DocPdfPageView", "editWidget() unsupported widget type: " + iA);
                break;
        }
        cVar.a(getPageNumber());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean a() {
        PDFFormEditor pDFFormEditor = this.h;
        if (pDFFormEditor == null) {
            return true;
        }
        boolean zStop = pDFFormEditor.stop();
        if (zStop) {
            this.h = null;
            invalidate();
        }
        return zStop;
    }
}

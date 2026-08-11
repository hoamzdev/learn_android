package com.hwa.excelllll.solib;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.RectF;
import defpackage.a7;
import java.util.ArrayList;

/* JADX INFO: compiled from: r8-map-id-9d97077224f63450f1940cb5ac6b1fe0f8959659f3401832f4dcadbeb673f8df */
/* JADX INFO: loaded from: classes.dex */
public class SODoc {
    private boolean i;
    private long internal;
    private String j;
    private boolean k;
    private boolean l;
    private p m;
    public final int DocType_DOC = 4;
    public final int DocType_DOCX = 5;
    public final int DocType_Error = -1;
    public final int DocType_Other = 7;
    public final int DocType_PDF = 6;
    public final int DocType_PPT = 2;
    public final int DocType_PPTX = 3;
    public final int DocType_XLS = 0;
    public final int DocType_XLSX = 1;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final int f4169a = 0;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private final int f4170b = 1;
    protected int c = 0;
    private final int d = 2;
    private final int e = 3;
    private final int f = 4;
    private final int g = 5;
    private final int h = 6;
    private int n = 0;
    private int o = 1;
    private ArrayList<SOPage> p = new ArrayList<>();
    private int q = 0;
    private int r = 0;

    public SODoc(long j) {
        this.internal = j;
    }

    private native void destroy();

    private native String getSelectionNaturalDimensionsInternal();

    private native void insertImageAtSelection(String str);

    private native void insertImageCenterPage(int i, String str);

    private native void nativeCloseSearch();

    private native void nativeInsertAutoshapeCenterPage(int i, String str, String str2, boolean z, float f, float f2);

    private native int nativeSearch(String str, boolean z, boolean z2);

    private native void nativeSetSearchStart(int i, float f, float f2);

    private native int saveToInternal(String str, SODocSaveListener sODocSaveListener);

    private native int saveToPDFInternal(String str, boolean z, SODocSaveListener sODocSaveListener);

    private void searchProgress(final int i, final int i2, final float f, final float f2, final float f3, final float f4) {
        SOLib.a(new Runnable() { // from class: com.artifex.solib.SODoc.1

            /* JADX INFO: renamed from: com.artifex.solib.SODoc$1$1, reason: invalid class name and collision with other inner class name */
            class RunnableC00251 implements Runnable {

                /* JADX INFO: renamed from: a, reason: collision with root package name */
                final /* synthetic */ int f4171a;

                /* JADX INFO: renamed from: b, reason: collision with root package name */
                final /* synthetic */ int f4172b;

                public RunnableC00251(int i, int i2) {
                    this.f4171a = i;
                    this.f4172b = i2;
                }

                @Override // java.lang.Runnable
                public void run() {
                    AnonymousClass1.this.a.onComplete(this.f4171a, this.f4172b);
                }
            }

            @Override // java.lang.Runnable
            public void run() {
                if (i != 5) {
                    SODoc.this.i = false;
                }
                int i3 = i;
                if (i3 == 0) {
                    if (SODoc.this.m != null) {
                        SODoc.this.m.a(i2, new RectF(f, f2, f3, f4));
                        return;
                    }
                    return;
                }
                if (i3 == 1) {
                    if (SODoc.this.m != null) {
                        SODoc.this.m.a();
                        return;
                    }
                    return;
                }
                if (i3 == 2) {
                    if (SODoc.this.m != null) {
                        SODoc.this.m.b();
                        return;
                    }
                    return;
                }
                if (i3 == 3) {
                    if (SODoc.this.m != null) {
                        SODoc.this.m.c();
                        return;
                    }
                    return;
                }
                SODoc sODoc = SODoc.this;
                if (i3 == 5) {
                    if (sODoc.m != null) {
                        SODoc.this.m.a(i2);
                    }
                } else if (i3 != 6) {
                    if (sODoc.m != null) {
                        SODoc.this.m.d();
                    }
                } else if (sODoc.m != null) {
                    SODoc.this.m.e();
                }
            }
        });
    }

    private native void setSelectionListStyle(int i);

    public void A() {
        setSelectionListStyle(0);
    }

    public void B() {
        setSelectionListStyle(1);
    }

    public void C() {
        setSelectionListStyle(2);
    }

    public void D() {
        setSelectionAlignment(0);
    }

    public void E() {
        setSelectionAlignment(1);
    }

    public void F() {
        setSelectionAlignment(2);
    }

    public void G() {
        setSelectionAlignment(3);
    }

    public void H() {
        if (getSelectionCanBeCopied() && getSelectionCanBeDeleted()) {
            selectionCutToClip();
            this.n = k.b() + 1;
            k.a(getClipboardAsText());
        }
    }

    public void I() {
        if (getSelectionCanBeCopied()) {
            selectionCopyToClip();
            this.n = k.b() + 1;
            k.a(getClipboardAsText());
        }
    }

    public boolean J() {
        return clipboardHasData() || k.c();
    }

    public int K() {
        return this.o;
    }

    public PointF L() {
        PointF pointF = new PointF(0.0f, 0.0f);
        String selectionNaturalDimensionsInternal = getSelectionNaturalDimensionsInternal();
        if (selectionNaturalDimensionsInternal != null) {
            String[] strArrSplit = selectionNaturalDimensionsInternal.split(",");
            if (strArrSplit.length == 2) {
                try {
                    pointF.set(Float.parseFloat(strArrSplit[0]), Float.parseFloat(strArrSplit[1]));
                    return pointF;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return pointF;
    }

    public void M() {
        ArrayList<SOPage> arrayList = this.p;
        if (arrayList == null || arrayList.isEmpty()) {
            return;
        }
        int i = 0;
        while (true) {
            int size = this.p.size();
            ArrayList<SOPage> arrayList2 = this.p;
            if (i >= size) {
                arrayList2.clear();
                return;
            }
            SOPage sOPage = arrayList2.get(i);
            if (sOPage != null) {
                sOPage.m();
            }
            i++;
        }
    }

    public void N() {
        if (getSelectionCanBeDeleted()) {
            selectionDelete();
        } else {
            deleteChar();
        }
    }

    public int O() {
        return this.q;
    }

    public int P() {
        return this.r;
    }

    public void a(Context context, int i) {
        if (!getSelectionCanBePasteTarget() || selectionIsAutoshapeOrImage()) {
            return;
        }
        boolean zClipboardHasData = clipboardHasData();
        if (!k.c() || (zClipboardHasData && (!zClipboardHasData || k.b() <= this.n))) {
            selectionPaste(i);
        } else {
            setSelectionText(k.b(context), 0, true);
        }
    }

    public native void abortLoad();

    public native void acceptTrackedChange();

    public native void addBlankPage(int i);

    public native void addColumnsLeft();

    public native void addColumnsRight();

    public native void addHighlightAnnotation();

    public native void addRowsAbove();

    public native void addRowsBelow();

    public void b(String str) {
        if (this.i) {
            a7.z("Search already in progess");
        } else {
            this.j = new String(str);
        }
    }

    public void c(boolean z) {
        if (this.i) {
            a7.z("Search already in progess");
        } else {
            this.k = z;
        }
    }

    public native void cancelSearch();

    public native void clearSelection();

    public native boolean clipboardHasData();

    public native void createInkAnnotation(int i, SOPoint[] sOPointArr, float f, int i2);

    public native void createTextAnnotationAt(PointF pointF, int i);

    public void d(boolean z) {
        if (this.i) {
            a7.z("Search already in progess");
        } else {
            this.l = z;
        }
    }

    public native void deleteChar();

    public native void deleteColumns();

    public native void deleteHighlightAnnotation();

    public native void deletePage(int i);

    public native void deleteRows();

    public native boolean docSupportsReview();

    public native void duplicatePage(int i);

    public void e(int i) {
        this.r = i;
    }

    public native int enumerateToc(SOEnumerateTocListener sOEnumerateTocListener);

    public void finalize() throws Throwable {
        try {
            destroy();
        } finally {
            super.finalize();
        }
    }

    public native String getAuthor();

    public native String[] getBgColorList();

    public native String getClipboardAsText();

    public native int getCurrentEdit();

    public native String getFontList();

    public native boolean getHasBeenModified();

    public native int[] getIndentationLevel();

    public native int getNumEdits();

    public native SOPage getPage(int i, SOPageListener sOPageListener);

    public native String getSelectedCellFormat();

    public native float getSelectedColumnWidth();

    public native float getSelectedRowHeight();

    public native String getSelectedTrackedChangeAuthor();

    public native String getSelectedTrackedChangeComment();

    public native String getSelectedTrackedChangeDate();

    public native int getSelectedTrackedChangeType();

    public native int getSelectionAlignment();

    public native int getSelectionAlignmentV();

    public native String getSelectionAnnotationAuthor();

    public native String getSelectionAnnotationComment();

    public native String getSelectionAnnotationDate();

    public native SOBitmap getSelectionAsBitmap();

    public native String getSelectionAsText();

    public native boolean getSelectionCanBeAbsolutelyPositioned();

    public native boolean getSelectionCanBeCopied();

    public native boolean getSelectionCanBeDeleted();

    public native boolean getSelectionCanBePasteTarget();

    public native boolean getSelectionCanBeResized();

    public native boolean getSelectionCanBeRotated();

    public native boolean getSelectionCanHaveTextAltered();

    public native String getSelectionFontName();

    public native double getSelectionFontSize();

    public native boolean getSelectionHasAssociatedPopup();

    public native boolean getSelectionIsAlignCenter();

    public native boolean getSelectionIsAlignJustify();

    public native boolean getSelectionIsAlignLeft();

    public native boolean getSelectionIsAlignRight();

    public native boolean getSelectionIsAlterableTextSelection();

    public native boolean getSelectionIsBold();

    public native boolean getSelectionIsItalic();

    public native boolean getSelectionIsLinethrough();

    public native boolean getSelectionIsUnderlined();

    public native int getSelectionLineType();

    public native float getSelectionLineWidth();

    public native boolean getSelectionListStyleIsDecimal();

    public native boolean getSelectionListStyleIsDisc();

    public native float getSelectionRotation();

    public native boolean getShowingTrackedChanges();

    public native boolean getTableCellsMerged();

    public native boolean getTrackingChanges();

    public boolean i() {
        return true;
    }

    public boolean j() {
        return true;
    }

    public void k() {
        destroy();
    }

    public native void movePage(int i, int i2);

    public native boolean nextTrackedChange();

    public void o() {
        nativeCloseSearch();
        this.i = false;
    }

    public boolean p() {
        return this.i;
    }

    public native boolean previousTrackedChange();

    public native void processKeyCommand(int i);

    public native boolean providePassword(String str);

    public int q() {
        String str = this.j;
        if (str == null) {
            a7.z("No Search Text specified");
            return 0;
        }
        if (this.i) {
            a7.z("Search already in progess");
            return 0;
        }
        this.i = true;
        try {
            int iNativeSearch = nativeSearch(str, this.k, this.l);
            if (iNativeSearch != 0) {
                this.i = false;
            }
            return iNativeSearch;
        } catch (Throwable th) {
            this.i = false;
            throw th;
        }
    }

    public int r() {
        return this.c;
    }

    public native void rejectTrackedChange();

    public native void selectionCopyToClip();

    public native void selectionCutToClip();

    public native void selectionDelete();

    public native boolean selectionIsAutoshapeOrImage();

    public native boolean selectionIsReviewable();

    public native void selectionPaste(int i);

    public native SOSelectionTableRange selectionTableRange();

    public native boolean setAuthor(String str);

    public native void setCurrentEdit(int i);

    public native void setFlowModeInternal(int i, float f);

    public native void setIndentationLevel(int i);

    public native void setSelectedCellFormat(String str);

    public native void setSelectedColumnWidth(float f);

    public native void setSelectedRowHeight(float f);

    public native void setSelectionAlignment(int i);

    public native void setSelectionAlignmentV(int i);

    public native void setSelectionAnnotationComment(String str);

    public native void setSelectionArrangeBack();

    public native void setSelectionArrangeBackwards();

    public native void setSelectionArrangeForwards();

    public native void setSelectionArrangeFront();

    public native void setSelectionBackgroundColor(String str);

    public native void setSelectionBackgroundTransparent();

    public native void setSelectionFillColor(String str);

    public native void setSelectionFontColor(String str);

    public native void setSelectionFontName(String str);

    public native void setSelectionFontSize(double d);

    public native void setSelectionIsBold(boolean z);

    public native void setSelectionIsItalic(boolean z);

    public native void setSelectionIsLinethrough(boolean z);

    public native void setSelectionIsUnderlined(boolean z);

    public native void setSelectionLineColor(String str);

    public native void setSelectionLineType(int i);

    public native void setSelectionLineWidth(float f);

    public native void setSelectionRotation(float f);

    public native void setSelectionText(String str, int i, boolean z);

    public native void setShowingTrackedChanges(boolean z);

    public native void setTableCellsMerged(boolean z);

    public native void setTrackingChanges(boolean z);

    public boolean x() {
        return false;
    }

    public boolean y() {
        return false;
    }

    public String z() {
        return "dd/MM/yyyy HH:mm:ss";
    }

    public void c(int i) {
        this.c = i;
    }

    public void d(String str) {
        insertImageAtSelection(str);
    }

    public void d(int i) {
        this.q = i;
    }

    public void b(SOPage sOPage) {
        this.p.remove(sOPage);
    }

    public void b(String str, boolean z, final SODocSaveListener sODocSaveListener) {
        if (saveToPDFInternal(str, z, new SODocSaveListener(this) { // from class: com.artifex.solib.SODoc.3
            @Override // com.artifex.solib.SODocSaveListener
            public void onComplete(final int i, final int i2) {
                SOLib.a(new Runnable() { // from class: com.artifex.solib.SODoc.3.1
                    @Override // java.lang.Runnable
                    public void run() {
                        sODocSaveListener.onComplete(i, i2);
                    }
                });
            }
        }) == 0) {
            return;
        }
        a7.c();
    }

    public void a(String str) {
    }

    public void a(boolean z) {
    }

    public void a(int i, float f) {
        this.o = i;
        setFlowModeInternal(i, f);
    }

    public void a(int i, float f, float f2) {
        if (!this.i) {
            nativeSetSearchStart(i, f, f2);
        } else {
            a7.z("Search already in progess");
        }
    }

    public void a(int i, String str) {
        insertImageCenterPage(i, str);
    }

    public void a(int i, String str, String str2) {
        nativeInsertAutoshapeCenterPage(i, str, str2, true, 0.0f, 0.0f);
    }

    public void a(SOPage sOPage) {
        this.p.add(sOPage);
    }

    public void a(p pVar) {
        if (!this.i) {
            this.m = pVar;
        } else {
            a7.z("Search already in progess");
        }
    }

    public void a(String str, final SODocSaveListener sODocSaveListener) {
        if (saveToInternal(str, new SODocSaveListener(this) { // from class: com.artifex.solib.SODoc.2
            @Override // com.artifex.solib.SODocSaveListener
            public void onComplete(final int i, final int i2) {
                SOLib.a(new Runnable() { // from class: com.artifex.solib.SODoc.2.1
                    @Override // java.lang.Runnable
                    public void run() {
                        sODocSaveListener.onComplete(i, i2);
                    }
                });
            }
        }) == 0) {
            return;
        }
        a7.c();
    }
}

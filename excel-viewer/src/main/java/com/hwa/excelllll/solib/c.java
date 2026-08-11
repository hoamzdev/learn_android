package com.hwa.excelllll.solib;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import com.artifex.mupdf.fitz.Document;
import com.artifex.mupdf.fitz.Outline;
import com.artifex.mupdf.fitz.PDFAnnotation;
import com.artifex.mupdf.fitz.PDFDocument;
import com.artifex.mupdf.fitz.PDFObject;
import com.artifex.mupdf.fitz.PDFPage;
import com.artifex.mupdf.fitz.PDFWidget;
import com.artifex.mupdf.fitz.Page;
import com.artifex.mupdf.fitz.SeekableInputOutputStream;
import com.artifex.mupdf.fitz.SeekableInputStream;
import defpackage.a7;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class c extends SODoc {
    private static String G = "yyyy-MM-dd HH:mm";
    private int A;
    private boolean B;
    private boolean C;
    private boolean D;
    private List<Integer> E;
    private String F;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public PDFDocument.JsEventListener f4200a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    int f4201b;
    private Document d;
    private ArrayList<e> e;
    private r f;
    private int g;
    private int h;
    private boolean i;
    private l j;
    private Context k;
    private a l;
    private long m;
    private String n;
    private boolean o;
    private long p;
    private boolean q;
    private boolean r;
    private int s;
    private int t;
    private boolean u;
    private String v;
    private boolean w;
    private boolean x;
    private p y;
    private int z;

    public interface a {
        void a(String str);
    }

    public interface b {
        void a(int i, int i2, int i3, String str, String str2, float f, float f2);
    }

    /* JADX INFO: renamed from: com.artifex.solib.c$c, reason: collision with other inner class name */
    public interface InterfaceC0029c {
        void a();
    }

    public c(Looper looper, l lVar, Context context) {
        super(0L);
        this.d = null;
        this.e = new ArrayList<>();
        this.f = null;
        this.g = 0;
        this.h = 0;
        this.i = false;
        this.j = null;
        this.l = null;
        this.f4200a = new PDFDocument.JsEventListener() { // from class: com.artifex.solib.c.1
            @Override // com.artifex.mupdf.fitz.PDFDocument.JsEventListener
            public void onAlert(String str) {
                if (c.this.l != null) {
                    c.this.l.a(str);
                }
            }
        };
        this.n = null;
        this.p = 0L;
        this.r = false;
        this.s = -1;
        this.t = -1;
        this.z = 0;
        this.A = 0;
        this.B = true;
        this.E = new ArrayList();
        this.F = null;
        this.f4201b = 0;
        this.j = lVar;
        this.k = context;
        this.g = 0;
        this.h = 0;
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.m = jCurrentTimeMillis;
        this.p = jCurrentTimeMillis;
        this.f = new r(looper);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void Q() {
        this.e.get(this.z).a(-1);
        boolean z = this.x;
        int i = this.z;
        int i2 = z ? i - 1 : i + 1;
        this.z = i2;
        if (i2 < 0) {
            this.z = r() - 1;
        }
        if (this.z >= r()) {
            this.z = 0;
        }
        this.B = true;
        ((Activity) this.k).runOnUiThread(new Runnable() { // from class: com.artifex.solib.c.11
            @Override // java.lang.Runnable
            public void run() {
                if (c.this.y != null) {
                    c.this.y.a(c.this.z);
                }
            }
        });
    }

    private void a(Outline[] outlineArr, int i, b bVar) {
        if (outlineArr == null || outlineArr.length <= 0) {
            return;
        }
        int i2 = 0;
        while (i2 < outlineArr.length) {
            Outline outline = outlineArr[i2];
            float fMin = outline.x;
            float fMin2 = outline.y;
            int i3 = outline.page;
            if (i3 >= 0) {
                Point pointSizeAtZoom = this.e.get(i3).sizeAtZoom(1.0d);
                fMin = Math.min(Math.max(fMin, 0.0f), pointSizeAtZoom.x);
                fMin2 = Math.min(Math.max(fMin2, 0.0f), pointSizeAtZoom.y);
            }
            int i4 = this.f4201b + 1;
            this.f4201b = i4;
            int i5 = i;
            b bVar2 = bVar;
            bVar2.a(i4, i5, outline.page, outline.title, outline.uri, fMin, fMin2);
            a(outline.down, this.f4201b, bVar2);
            i2++;
            bVar = bVar2;
            i = i5;
        }
    }

    public static Document c(String str) {
        try {
            SOSecureFS sOSecureFSA = k.a();
            return (sOSecureFSA == null || !sOSecureFSA.isSecurePath(str)) ? Document.openDocument(str) : a(str, sOSecureFSA);
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void f(int i) {
        Integer num = new Integer(i);
        if (this.E.contains(num)) {
            return;
        }
        this.E.add(num);
    }

    private void g(int i) {
        Integer num = new Integer(i);
        if (this.E.contains(num)) {
            this.E.remove(num);
        }
    }

    public static /* synthetic */ int l(c cVar) {
        int i = cVar.A;
        cVar.A = i + 1;
        return i;
    }

    @Override // com.artifex.solib.SODoc
    public void abortLoad() {
        k();
    }

    @Override // com.artifex.solib.SODoc
    public void addHighlightAnnotation() {
        int iK = e.k();
        if (iK != -1) {
            this.e.get(iK).i();
            a(iK);
        }
    }

    @Override // com.artifex.solib.SODoc
    public void b(String str) {
        if (this.u) {
            a7.z("Search already in progess");
            return;
        }
        if (!str.equalsIgnoreCase(this.v)) {
            this.A = 0;
            this.z = 0;
            this.B = true;
        }
        this.v = str;
    }

    @Override // com.artifex.solib.SODoc
    public void cancelSearch() {
        this.u = false;
    }

    @Override // com.artifex.solib.SODoc
    public void clearSelection() {
        int i = this.t;
        this.t = -1;
        this.s = -1;
        if (i != -1) {
            this.e.get(i).j();
        }
        int iK = e.k();
        if (iK != -1) {
            this.e.get(iK).l();
        }
    }

    @Override // com.artifex.solib.SODoc
    public void createInkAnnotation(int i, SOPoint[] sOPointArr, float f, int i2) {
        this.e.get(i).a(sOPointArr, f, i2);
        a(i);
    }

    @Override // com.artifex.solib.SODoc
    public void createTextAnnotationAt(PointF pointF, int i) {
        this.e.get(i).a(pointF, getAuthor());
        a(i);
    }

    @Override // com.artifex.solib.SODoc
    public void d(boolean z) {
        if (this.u) {
            a7.z("Search already in progess");
        } else {
            this.x = z;
        }
    }

    public Document e() {
        return this.d;
    }

    @Override // com.artifex.solib.SODoc
    public String getAuthor() {
        return this.F;
    }

    @Override // com.artifex.solib.SODoc
    public boolean getHasBeenModified() {
        return this.r;
    }

    @Override // com.artifex.solib.SODoc
    public SOPage getPage(int i, SOPageListener sOPageListener) {
        e eVar = this.e.get(i);
        eVar.a(sOPageListener);
        return eVar;
    }

    @Override // com.artifex.solib.SODoc
    public String getSelectionAnnotationAuthor() {
        PDFAnnotation pDFAnnotationM = m();
        if (pDFAnnotationM != null) {
            return pDFAnnotationM.getAuthor();
        }
        return null;
    }

    @Override // com.artifex.solib.SODoc
    public String getSelectionAnnotationComment() {
        PDFAnnotation pDFAnnotationM = m();
        if (pDFAnnotationM != null) {
            return pDFAnnotationM.getContents();
        }
        return null;
    }

    @Override // com.artifex.solib.SODoc
    public String getSelectionAnnotationDate() {
        PDFAnnotation pDFAnnotationM = m();
        if (pDFAnnotationM == null) {
            return null;
        }
        try {
            return new SimpleDateFormat(G).format(pDFAnnotationM.getModificationDate());
        } catch (Exception unused) {
            return null;
        }
    }

    @Override // com.artifex.solib.SODoc
    public boolean getSelectionCanBeAbsolutelyPositioned() {
        return false;
    }

    @Override // com.artifex.solib.SODoc
    public boolean getSelectionCanBeDeleted() {
        return (this.t == -1 || this.s == -1) ? false : true;
    }

    @Override // com.artifex.solib.SODoc
    public boolean getSelectionCanBeResized() {
        return false;
    }

    @Override // com.artifex.solib.SODoc
    public boolean getSelectionCanBeRotated() {
        return false;
    }

    @Override // com.artifex.solib.SODoc
    public boolean getSelectionHasAssociatedPopup() {
        PDFAnnotation pDFAnnotationM = m();
        if (pDFAnnotationM == null || pDFAnnotationM.getType() != 0) {
            return pDFAnnotationM != null && pDFAnnotationM.getType() == 8;
        }
        return true;
    }

    @Override // com.artifex.solib.SODoc
    public boolean getSelectionIsAlterableTextSelection() {
        return e.k() != -1;
    }

    @Override // com.artifex.solib.SODoc
    public boolean i() {
        return b(this.d) != null;
    }

    @Override // com.artifex.solib.SODoc
    public boolean j() {
        return i();
    }

    @Override // com.artifex.solib.SODoc
    public void k() {
        this.i = true;
        r rVar = this.f;
        if (rVar != null) {
            rVar.c();
            this.f = null;
        }
        ArrayList<e> arrayList = this.e;
        if (arrayList != null) {
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                e eVar = arrayList.get(i);
                i++;
                eVar.m();
            }
            this.e.clear();
            this.e = null;
        }
        Document document = this.d;
        if (document != null) {
            PDFDocument pDFDocumentB = b(document);
            if (pDFDocumentB != null) {
                pDFDocumentB.disableJs();
            }
            this.d.destroy();
        }
        this.g = 0;
        this.h = 0;
        this.d = null;
    }

    public PDFAnnotation m() {
        int i = this.t;
        if (i == -1 || this.s == -1 || i >= this.e.size()) {
            return null;
        }
        return this.e.get(this.t).c(this.s);
    }

    public boolean n() {
        int i = this.t;
        return i != -1 && this.e.get(i).c(this.s).getType() == 12;
    }

    @Override // com.artifex.solib.SODoc
    public void o() {
        this.u = false;
    }

    @Override // com.artifex.solib.SODoc
    public boolean providePassword(String str) {
        if (this.d.authenticatePassword(str)) {
            v();
            w();
            return true;
        }
        l lVar = this.j;
        if (lVar != null) {
            lVar.a(PDFWidget.PDF_TX_FIELD_IS_MULTILINE, 0);
        }
        return false;
    }

    @Override // com.artifex.solib.SODoc
    public int q() {
        this.u = true;
        this.C = false;
        this.D = false;
        d().a(new r.a() { // from class: com.artifex.solib.c.12
            @Override // com.artifex.solib.r.a
            public void a() {
                int i = 0;
                while (true) {
                    boolean z = c.this.u;
                    c cVar = c.this;
                    if (!z) {
                        cVar.C = true;
                        return;
                    }
                    int iA = ((e) cVar.e.get(c.this.z)).a(c.this.v);
                    c cVar2 = c.this;
                    if (iA != 0) {
                        boolean z2 = cVar2.B;
                        c cVar3 = c.this;
                        if (z2) {
                            boolean z3 = cVar3.x;
                            c cVar4 = c.this;
                            if (z3) {
                                cVar4.A = iA - 1;
                            } else {
                                cVar4.A = 0;
                            }
                            c.this.B = false;
                        } else {
                            boolean z4 = cVar3.x;
                            c cVar5 = c.this;
                            if (z4) {
                                c.k(cVar5);
                            } else {
                                c.l(cVar5);
                            }
                            if (c.this.A < 0 || c.this.A >= iA) {
                                boolean z5 = c.this.x;
                                c cVar6 = c.this;
                                if (z5) {
                                    if (cVar6.z == 0) {
                                        c.this.Q();
                                        break;
                                    }
                                    c.this.Q();
                                } else {
                                    if (cVar6.z == c.this.r() - 1) {
                                        c.this.Q();
                                        break;
                                    }
                                    c.this.Q();
                                }
                            }
                        }
                        c.this.D = true;
                        return;
                    }
                    i++;
                    if (i == cVar2.r()) {
                        break;
                    } else {
                        c.this.Q();
                    }
                }
                c.this.u = false;
            }

            @Override // com.artifex.solib.r.a, java.lang.Runnable
            public void run() {
                boolean z = c.this.C;
                c cVar = c.this;
                if (z) {
                    if (cVar.y != null) {
                        c.this.y.e();
                        return;
                    }
                    return;
                }
                boolean z2 = cVar.D;
                c cVar2 = c.this;
                if (!z2) {
                    if (cVar2.y != null) {
                        c.this.y.a();
                    }
                } else {
                    ((e) cVar2.e.get(c.this.z)).a(c.this.A);
                    Rect rectA = ((e) c.this.e.get(c.this.z)).a();
                    if (c.this.y != null) {
                        c.this.y.a(c.this.z, ((e) c.this.e.get(c.this.z)).a(rectA));
                    }
                    c.this.u = false;
                }
            }
        });
        return 0;
    }

    @Override // com.artifex.solib.SODoc
    public int r() {
        return this.c;
    }

    public void s() {
        int iK = e.k();
        if (iK != -1) {
            this.e.get(iK).g();
            a(iK);
            f(iK);
        }
    }

    @Override // com.artifex.solib.SODoc
    public void selectionDelete() {
        int i = this.t;
        if (i == -1 || this.s == -1) {
            return;
        }
        e eVar = this.e.get(i);
        eVar.a(eVar.c(this.s));
        a(this.t);
        if (this.e.get(this.t).e(12) == 0) {
            g(this.t);
        }
        clearSelection();
    }

    @Override // com.artifex.solib.SODoc
    public boolean setAuthor(String str) {
        this.F = str;
        return true;
    }

    @Override // com.artifex.solib.SODoc
    public void setSelectionAnnotationComment(String str) {
        PDFAnnotation pDFAnnotationM = m();
        if (pDFAnnotationM == null || str == null) {
            return;
        }
        String contents = pDFAnnotationM.getContents();
        if (contents != null && contents.compareTo(str) != 0) {
            pDFAnnotationM.setContents(str);
            pDFAnnotationM.setModificationDate(new Date());
        }
        a(this.s);
    }

    public boolean t() {
        return !this.E.isEmpty();
    }

    public void u() {
        int size = this.e.size();
        for (int i = 0; i < size; i++) {
            if (this.e.get(i).e(12) > 0) {
                this.e.get(i).h();
                a(i);
            }
        }
        this.E.clear();
    }

    public void v() {
        PDFDocument pDFDocumentB;
        this.g = this.d.countPages();
        if (!ConfigOptions.a().x() || (pDFDocumentB = b(this.d)) == null) {
            return;
        }
        pDFDocumentB.enableJs();
        pDFDocumentB.setJsEventListener(this.f4200a);
    }

    public void w() {
        if (!this.i) {
            d().a(new r.a() { // from class: com.artifex.solib.c.3

                /* JADX INFO: renamed from: b, reason: collision with root package name */
                private boolean f4212b = false;

                @Override // com.artifex.solib.r.a
                public void a() {
                    if (c.this.h >= c.this.g) {
                        this.f4212b = true;
                        return;
                    }
                    c.this.a(c.this.d.loadPage(c.this.h));
                    if (((e) c.this.e.get(c.this.h)).e(12) > 0) {
                        c cVar = c.this;
                        cVar.f(cVar.h);
                    }
                }

                @Override // com.artifex.solib.r.a, java.lang.Runnable
                public void run() {
                    boolean z = this.f4212b;
                    c cVar = c.this;
                    if (z) {
                        if (cVar.j != null) {
                            c.this.j.a();
                        }
                    } else {
                        c.t(cVar);
                        if (c.this.j != null) {
                            c.this.j.a(c.this.h);
                        }
                        c.this.w();
                    }
                }
            });
            return;
        }
        l lVar = this.j;
        if (lVar != null) {
            lVar.a();
        }
    }

    @Override // com.artifex.solib.SODoc
    public boolean x() {
        if (!(e() instanceof PDFDocument)) {
            return false;
        }
        PDFObject trailer = ((PDFDocument) e()).getTrailer();
        if (!a(trailer)) {
            trailer = trailer.get("Root");
        }
        if (!a(trailer)) {
            trailer = trailer.get("AcroForm");
        }
        if (!a(trailer)) {
            trailer = trailer.get("XFA");
        }
        return !a(trailer);
    }

    @Override // com.artifex.solib.SODoc
    public boolean y() {
        if (!(e() instanceof PDFDocument)) {
            return false;
        }
        PDFObject trailer = ((PDFDocument) e()).getTrailer();
        if (!a(trailer)) {
            trailer = trailer.get("Root");
        }
        if (!a(trailer)) {
            trailer = trailer.get("AcroForm");
        }
        if (!a(trailer)) {
            trailer = trailer.get("Fields");
        }
        return !a(trailer) && trailer.size() > 0;
    }

    @Override // com.artifex.solib.SODoc
    public String z() {
        return "yyyy-MM-dd HH:mm";
    }

    @Override // com.artifex.solib.SODoc
    public boolean p() {
        return this.u;
    }

    public boolean h() {
        return this.q;
    }

    public int l() {
        return this.t;
    }

    public static /* synthetic */ int t(c cVar) {
        int i = cVar.h;
        cVar.h = i + 1;
        return i;
    }

    public r d() {
        return this.f;
    }

    @Override // com.artifex.solib.SODoc
    public void deleteHighlightAnnotation() {
    }

    @Override // com.artifex.solib.SODoc
    public void finalize() throws Throwable {
    }

    public boolean f() {
        return this.o;
    }

    public long g() {
        return this.p;
    }

    @Override // com.artifex.solib.SODoc
    public void processKeyCommand(int i) {
    }

    public void c() {
        this.f.a();
    }

    @Override // com.artifex.solib.SODoc
    public void c(boolean z) {
        if (this.u) {
            a7.z("Search already in progess");
        } else {
            this.w = z;
        }
    }

    public static PDFDocument b(Document document) {
        try {
            return (PDFDocument) document;
        } catch (Exception unused) {
            return null;
        }
    }

    public String b() {
        return this.n;
    }

    public void b(final int i) {
        new Handler().post(new Runnable() { // from class: com.artifex.solib.c.2
            @Override // java.lang.Runnable
            public void run() {
                c.this.d(i);
                c.this.e(i);
                if (c.this.j != null) {
                    l lVar = c.this.j;
                    int i2 = i;
                    lVar.b(i2, i2);
                }
            }
        });
    }

    @Override // com.artifex.solib.SODoc
    public void b(String str, boolean z, SODocSaveListener sODocSaveListener) {
        a(str, false, sODocSaveListener);
    }

    public void b(boolean z) {
        this.r = z;
    }

    public static /* synthetic */ int k(c cVar) {
        int i = cVar.A;
        cVar.A = i - 1;
        return i;
    }

    public int a(b bVar) {
        if (bVar != null) {
            try {
                this.f4201b = 0;
                a(this.d.loadOutline(), this.f4201b, bVar);
            } catch (Exception unused) {
            }
        }
        return 0;
    }

    public long a() {
        return this.m;
    }

    private static Document a(String str, final SOSecureFS sOSecureFS) {
        try {
            final Object fileHandleForReading = sOSecureFS.getFileHandleForReading(str);
            return Document.openDocument(new SeekableInputStream() { // from class: com.artifex.solib.c.5
                @Override // com.artifex.mupdf.fitz.SeekableStream
                public long position() throws IOException {
                    return sOSecureFS.getFileOffset(fileHandleForReading);
                }

                @Override // com.artifex.mupdf.fitz.SeekableInputStream
                public int read(byte[] bArr) throws IOException {
                    return sOSecureFS.readFromFile(fileHandleForReading, bArr);
                }

                @Override // com.artifex.mupdf.fitz.SeekableStream
                public long seek(long j, int i) throws IOException {
                    long fileOffset = sOSecureFS.getFileOffset(fileHandleForReading);
                    long fileLength = sOSecureFS.getFileLength(fileHandleForReading);
                    if (i != 0) {
                        if (i != 1) {
                            j = i != 2 ? 0L : j + fileLength;
                        } else {
                            j += fileOffset;
                        }
                    }
                    sOSecureFS.seekToFileOffset(fileHandleForReading, j);
                    return j;
                }
            }, com.artifex.solib.a.g(str));
        } catch (Exception unused) {
            return null;
        }
    }

    public void a(final int i) {
        d().a(new r.a() { // from class: com.artifex.solib.c.13
            @Override // com.artifex.solib.r.a
            public void a() {
                e eVar = (e) c.this.e.get(i);
                if (eVar != null) {
                    eVar.b();
                }
            }

            @Override // com.artifex.solib.r.a, java.lang.Runnable
            public void run() {
                if (c.this.j != null) {
                    c.this.j.a();
                    l lVar = c.this.j;
                    int i2 = i;
                    lVar.b(i2, i2);
                }
            }
        });
    }

    @Override // com.artifex.solib.SODoc
    public void a(int i, float f, float f2) {
        if (this.u) {
            a7.z("Search already in progess");
        }
    }

    public void a(int i, int i2) {
        this.t = i;
        this.s = i2;
    }

    public void a(Document document) {
        this.d = document;
    }

    public void a(Page page) {
        this.e.add(new e(this, page, this.c));
        this.c = this.e.size();
    }

    public void a(a aVar) {
        this.l = aVar;
    }

    public void a(final InterfaceC0029c interfaceC0029c) {
        d().a(new r.a() { // from class: com.artifex.solib.c.4
            @Override // com.artifex.solib.r.a
            public void a() {
                c cVar;
                String strB = c.this.b();
                c.this.d.destroy();
                int i = 0;
                while (true) {
                    int size = c.this.e.size();
                    cVar = c.this;
                    if (i >= size) {
                        break;
                    }
                    e eVar = (e) cVar.e.get(i);
                    if (eVar != null) {
                        eVar.m();
                    }
                    i++;
                }
                cVar.e.clear();
                c.this.d = c.c(strB);
                int iCountPages = c.this.d.countPages();
                for (int i2 = 0; i2 < iCountPages; i2++) {
                    c.this.e.add(new e(this, (PDFPage) c.this.d.loadPage(i2), c.this.c));
                }
            }

            @Override // com.artifex.solib.r.a, java.lang.Runnable
            public void run() {
                interfaceC0029c.a();
            }
        });
    }

    @Override // com.artifex.solib.SODoc
    public void a(p pVar) {
        if (pVar == null) {
            cancelSearch();
            this.y = pVar;
        } else if (this.u) {
            a7.z("Search already in progess");
        } else {
            this.y = pVar;
        }
    }

    @Override // com.artifex.solib.SODoc
    public void a(String str) {
        this.n = str;
    }

    @Override // com.artifex.solib.SODoc
    public void a(String str, final SODocSaveListener sODocSaveListener) {
        Runnable runnable;
        if (i()) {
            a(str, true, sODocSaveListener);
            return;
        }
        if (str.compareToIgnoreCase(b()) == 0) {
            runnable = new Runnable() { // from class: com.artifex.solib.c.6
                @Override // java.lang.Runnable
                public void run() {
                    sODocSaveListener.onComplete(0, 0);
                    c.this.p = System.currentTimeMillis();
                }
            };
        } else {
            runnable = com.artifex.solib.a.a(b(), str, true) ? new Runnable() { // from class: com.artifex.solib.c.7
                @Override // java.lang.Runnable
                public void run() {
                    sODocSaveListener.onComplete(0, 0);
                    c.this.p = System.currentTimeMillis();
                }
            } : new Runnable() { // from class: com.artifex.solib.c.8
                @Override // java.lang.Runnable
                public void run() {
                    sODocSaveListener.onComplete(1, 795);
                }
            };
        }
        SOLib.a(runnable);
    }

    public void a(final String str, final boolean z, final SODocSaveListener sODocSaveListener) {
        final PDFDocument pDFDocumentB = b(this.d);
        if (pDFDocumentB == null) {
            sODocSaveListener.onComplete(1, 0);
        } else {
            d().a(new r.a() { // from class: com.artifex.solib.c.9
                private int f;

                @Override // com.artifex.solib.r.a
                public void a() {
                    String str2;
                    c.this.o = false;
                    if (z && pDFDocumentB.canBeSavedIncrementally()) {
                        c.this.o = true;
                        str2 = "incremental";
                    } else {
                        str2 = "";
                    }
                    String str3 = com.artifex.solib.a.c(c.this.k) + File.separator + UUID.randomUUID() + ".pdf";
                    if (str2.equals("incremental")) {
                        com.artifex.solib.a.a(c.this.b(), str3, true);
                    }
                    SOSecureFS sOSecureFSA = k.a();
                    if (sOSecureFSA == null || !sOSecureFSA.isSecurePath(str)) {
                        try {
                            pDFDocumentB.save(str3, str2);
                            this.f = 0;
                        } catch (Exception unused) {
                            this.f = 1;
                        }
                    } else {
                        this.f = c.a(pDFDocumentB, str3, str2, sOSecureFSA);
                    }
                    if (this.f == 0) {
                        c.this.b(false);
                        if (com.artifex.solib.a.a(str3, str, true)) {
                            com.artifex.solib.a.e(str3);
                        }
                    }
                }

                @Override // com.artifex.solib.r.a, java.lang.Runnable
                public void run() {
                    SODocSaveListener sODocSaveListener2 = sODocSaveListener;
                    if (sODocSaveListener2 != null) {
                        int i = this.f;
                        if (i != 0) {
                            sODocSaveListener2.onComplete(1, i);
                            return;
                        }
                        sODocSaveListener2.onComplete(0, i);
                        c.this.p = System.currentTimeMillis();
                    }
                }
            });
        }
    }

    @Override // com.artifex.solib.SODoc
    public void a(boolean z) {
        this.q = z;
    }

    public static int a(PDFDocument pDFDocument, String str, String str2, final SOSecureFS sOSecureFS) {
        try {
            final Object fileHandleForWriting = sOSecureFS.getFileHandleForWriting(str);
            pDFDocument.save(new SeekableInputOutputStream() { // from class: com.artifex.solib.c.10
                @Override // com.artifex.mupdf.fitz.SeekableStream
                public long position() throws IOException {
                    return sOSecureFS.getFileOffset(fileHandleForWriting);
                }

                @Override // com.artifex.mupdf.fitz.SeekableInputStream
                public int read(byte[] bArr) throws IOException {
                    return sOSecureFS.readFromFile(fileHandleForWriting, bArr);
                }

                @Override // com.artifex.mupdf.fitz.SeekableStream
                public long seek(long j, int i) throws IOException {
                    long fileOffset = sOSecureFS.getFileOffset(fileHandleForWriting);
                    long fileLength = sOSecureFS.getFileLength(fileHandleForWriting);
                    if (i != 0) {
                        if (i != 1) {
                            j = i != 2 ? 0L : j + fileLength;
                        } else {
                            j += fileOffset;
                        }
                    }
                    sOSecureFS.seekToFileOffset(fileHandleForWriting, j);
                    return j;
                }

                @Override // com.artifex.mupdf.fitz.SeekableOutputStream
                public void write(byte[] bArr, int i, int i2) throws IOException {
                    if (i == 0 && i2 == bArr.length) {
                        sOSecureFS.writeToFile(fileHandleForWriting, bArr);
                    } else {
                        sOSecureFS.writeToFile(fileHandleForWriting, Arrays.copyOfRange(bArr, i, i2));
                    }
                }
            }, str2);
            sOSecureFS.closeFile(fileHandleForWriting);
        } catch (Exception unused) {
        }
        return 0;
    }

    private boolean a(PDFObject pDFObject) {
        return pDFObject == null || pDFObject.equals(PDFObject.Null);
    }
}

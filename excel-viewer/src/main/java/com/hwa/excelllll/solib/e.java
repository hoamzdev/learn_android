package com.hwa.excelllll.solib;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import com.artifex.mupdf.fitz.Cookie;
import com.artifex.mupdf.fitz.DisplayList;
import com.artifex.mupdf.fitz.DisplayListDevice;
import com.artifex.mupdf.fitz.Link;
import com.artifex.mupdf.fitz.Matrix;
import com.artifex.mupdf.fitz.PDFAnnotation;
import com.artifex.mupdf.fitz.PDFPage;
import com.artifex.mupdf.fitz.PDFWidget;
import com.artifex.mupdf.fitz.Page;
import com.artifex.mupdf.fitz.Quad;
import com.artifex.mupdf.fitz.Rect;
import com.artifex.mupdf.fitz.StructuredText;
import com.artifex.mupdf.fitz.android.AndroidDrawDevice;
import defpackage.e6;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;

/* JADX INFO: loaded from: classes.dex */
public class e extends SOPage {
    private static int p = -1;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    Rect[] f4228a;

    /* JADX INFO: renamed from: b, reason: collision with root package name */
    private Page f4229b;
    private int c;
    private c d;
    private Rect e;
    private r f;
    private DisplayList g;
    private DisplayList h;
    private DisplayList i;
    private Quad[] j;
    private int k;
    private String l;
    private ArrayList<SOPageListener> m;
    private Point n;
    private Point o;
    private boolean q;
    private boolean r;

    public e(c cVar, Page page, int i) {
        super(0L);
        this.e = new Rect();
        this.g = null;
        this.h = null;
        this.i = null;
        this.l = "";
        this.m = new ArrayList<>();
        this.f4228a = null;
        this.n = null;
        this.o = null;
        this.q = false;
        this.r = false;
        this.f4229b = page;
        this.c = i;
        this.d = cVar;
        this.e = page.getBounds();
        this.f = this.d.d();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void a(Cookie cookie) {
        PDFPage pDFPageA = a(this.f4229b);
        if (this.g == null) {
            this.g = new DisplayList((Rect) null);
            DisplayListDevice displayListDevice = new DisplayListDevice(this.g);
            try {
                try {
                    this.f4229b.runPageContents(displayListDevice, new Matrix(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f), cookie);
                } catch (RuntimeException unused) {
                    this.g.destroy();
                    this.g = null;
                }
                displayListDevice.destroy();
            } catch (Throwable th) {
                displayListDevice.destroy();
                throw th;
            }
        }
        if (this.h == null && pDFPageA != null) {
            this.h = new DisplayList((Rect) null);
            DisplayListDevice displayListDevice2 = new DisplayListDevice(this.h);
            try {
                try {
                    PDFAnnotation[] annotations = pDFPageA.getAnnotations();
                    if (annotations != null) {
                        for (PDFAnnotation pDFAnnotation : annotations) {
                            pDFAnnotation.run(displayListDevice2, new Matrix(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f), cookie);
                        }
                    }
                } catch (Throwable th2) {
                    displayListDevice2.destroy();
                    throw th2;
                }
            } catch (RuntimeException unused2) {
                this.h.destroy();
                this.h = null;
            }
            displayListDevice2.destroy();
        }
        if (this.i != null || pDFPageA == null) {
            return;
        }
        this.i = new DisplayList((Rect) null);
        DisplayListDevice displayListDevice3 = new DisplayListDevice(this.i);
        try {
            try {
                PDFWidget[] widgets = pDFPageA.getWidgets();
                if (widgets != null) {
                    for (PDFWidget pDFWidget : widgets) {
                        pDFWidget.run(displayListDevice3, new Matrix(1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f), cookie);
                    }
                }
            } catch (RuntimeException unused3) {
                this.i.destroy();
                this.i = null;
            }
        } finally {
            displayListDevice3.destroy();
        }
    }

    private void f(int i) {
        PDFPage pDFPageA = a(this.f4229b);
        if (pDFPageA != null && p == this.c) {
            PDFAnnotation pDFAnnotationCreateAnnotation = pDFPageA.createAnnotation(i);
            if (i == 8) {
                pDFAnnotationCreateAnnotation.setColor(d(16776960));
            }
            pDFAnnotationCreateAnnotation.setQuadPoints(a(this.f4228a));
            pDFAnnotationCreateAnnotation.setAuthor("SmartOffice");
            pDFAnnotationCreateAnnotation.setModificationDate(new Date());
            pDFAnnotationCreateAnnotation.update();
        }
    }

    public static int k() {
        return p;
    }

    private void q() {
        this.f4228a = null;
        p = -1;
        Point point = this.n;
        if (point == null || this.o == null) {
            return;
        }
        float f = point.x;
        float f2 = this.n.y;
        Point point2 = this.o;
        Rect rect = new Rect(f, f2, point2.x, point2.y);
        Quad[] quadArrHighlight = this.f4229b.toStructuredText().highlight(new com.artifex.mupdf.fitz.Point((int) rect.x0, (int) rect.y0), new com.artifex.mupdf.fitz.Point((int) rect.x1, (int) rect.y1));
        this.f4228a = null;
        if (quadArrHighlight != null && quadArrHighlight.length > 0) {
            this.f4228a = new Rect[quadArrHighlight.length];
            for (int i = 0; i < quadArrHighlight.length; i++) {
                this.f4228a[i] = quadArrHighlight[i].toRect();
            }
        }
        p = this.c;
    }

    private void r() {
        this.q = true;
        if (this.r) {
            return;
        }
        this.f4229b.destroy();
        this.f4229b = null;
        this.r = true;
    }

    public void b(int i) {
        PDFAnnotation[] annotations;
        PDFAnnotation pDFAnnotation;
        PDFPage pDFPageA = a(this.f4229b);
        if (pDFPageA == null || (annotations = pDFPageA.getAnnotations()) == null || annotations.length == 0 || (pDFAnnotation = annotations[i]) == null) {
            return;
        }
        this.d.a(this.c, i);
        Rect rect = pDFAnnotation.getRect();
        ArrayList<SOPageListener> arrayList = this.m;
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size) {
            SOPageListener sOPageListener = arrayList.get(i2);
            i2++;
            sOPageListener.update(d(rect));
        }
        b(rect);
        this.d.b(this.c);
    }

    public void c() {
        PDFWidget[] widgets;
        PDFPage pDFPageA = a(this.f4229b);
        if (pDFPageA == null || (widgets = pDFPageA.getWidgets()) == null || widgets.length <= 0) {
            return;
        }
        for (PDFWidget pDFWidget : widgets) {
            if (pDFWidget != null) {
                pDFWidget.update();
            }
        }
    }

    public float[] d(int i) {
        return new float[]{Color.red(i) / 255.0f, Color.green(i) / 255.0f, Color.blue(i) / 255.0f};
    }

    public int e(int i) {
        PDFAnnotation[] annotations;
        PDFPage pDFPageA = a(this.f4229b);
        if (pDFPageA == null || (annotations = pDFPageA.getAnnotations()) == null) {
            return 0;
        }
        int i2 = 0;
        for (PDFAnnotation pDFAnnotation : annotations) {
            if (pDFAnnotation.getType() == i) {
                i2++;
            }
        }
        return i2;
    }

    @Override // com.artifex.solib.SOPage
    public void finalize() throws Throwable {
        this.m.clear();
        this.d = null;
        r();
    }

    public void g() {
        f(12);
    }

    public void h() {
        PDFPage pDFPageA = a(this.f4229b);
        if (pDFPageA == null) {
            return;
        }
        pDFPageA.applyRedactions();
    }

    public void i() {
        f(8);
    }

    public void j() {
        this.f4228a = null;
        this.n = null;
        this.o = null;
        p = -1;
        b((Rect) null);
        this.d.b(this.c);
    }

    public void l() {
        if (this.c == p) {
            j();
        }
    }

    @Override // com.artifex.solib.SOPage
    public void n() {
        this.q = true;
        this.m.clear();
        this.d = null;
        r();
    }

    public h[] o() {
        PDFWidget[] widgets;
        PDFPage pDFPageA = a(this.f4229b);
        if (pDFPageA == null || (widgets = pDFPageA.getWidgets()) == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < widgets.length; i++) {
            int flags = widgets[i].getFlags();
            int fieldFlags = widgets[i].getFieldFlags();
            if ((flags & 1) == 0 && (flags & 2) == 0 && (flags & 32) == 0 && (flags & 64) == 0 && (fieldFlags & 1) == 0) {
                arrayList.add(new h(widgets[i]));
            }
        }
        return (h[]) arrayList.toArray(new h[arrayList.size()]);
    }

    @Override // com.artifex.solib.SOPage
    public SOHyperlink objectAtPoint(float f, float f2) {
        Link[] links = this.f4229b.getLinks();
        if (links == null || links.length == 0) {
            return null;
        }
        for (Link link : links) {
            if (link.bounds.contains(f, f2)) {
                SOHyperlink sOHyperlink = new SOHyperlink();
                sOHyperlink.url = link.uri;
                sOHyperlink.pageNum = link.page;
                sOHyperlink.bbox = c(link.bounds);
                return sOHyperlink;
            }
        }
        return null;
    }

    @Override // com.artifex.solib.SOPage
    public void select(int i, double d, double d2) {
        Point point = new Point((int) d, (int) d2);
        this.d.a(-1, -1);
        if (i == 0) {
            this.n = point;
        } else {
            if (i != 1) {
                android.graphics.Rect rectA = a(point);
                if (rectA == null) {
                    this.f4228a = null;
                    b(this.e);
                    this.d.b(this.c);
                    p = -1;
                    return;
                }
                this.n = new Point(rectA.left, rectA.top);
                this.o = new Point(rectA.right, rectA.bottom);
                this.f4228a = new Rect[]{b(rectA)};
                p = this.c;
                b(b(rectA));
                this.d.b(this.c);
                return;
            }
            this.o = point;
        }
        q();
        this.d.b(this.c);
        b(this.e);
    }

    @Override // com.artifex.solib.SOPage
    public SOSelectionLimits selectionLimits() {
        PDFAnnotation pDFAnnotationM = this.d.m();
        if (pDFAnnotationM != null && this.d.l() == this.c) {
            return new g(c(pDFAnnotationM.getRect()), true);
        }
        Rect[] rectArr = this.f4228a;
        if (rectArr == null || rectArr.length <= 0) {
            return null;
        }
        PointF pointF = new PointF(rectArr[0].x0, this.f4228a[0].y0);
        Rect[] rectArr2 = this.f4228a;
        return new g(pointF, new PointF(rectArr2[rectArr2.length - 1].x1, rectArr2[rectArr2.length - 1].y1), true);
    }

    @Override // com.artifex.solib.SOPage
    public Point sizeAtZoom(double d) {
        Rect rect = this.e;
        double d2 = rect.x1 - rect.x0;
        double d3 = rect.y1 - rect.y0;
        Double.isNaN(d2);
        int iA = (int) e6.a(d2, d2, d2, d);
        Double.isNaN(d3);
        Double.isNaN(d3);
        Double.isNaN(d3);
        return new Point(iA, (int) (d * d3));
    }

    @Override // com.artifex.solib.SOPage
    public PointF zoomToFitRect(int i, int i2) {
        Rect rect = this.e;
        return new PointF(i / (rect.x1 - rect.x0), i2 / (rect.y1 - rect.y0));
    }

    @Override // com.artifex.solib.SOPage
    public void m() {
    }

    public PDFAnnotation c(int i) {
        PDFAnnotation[] annotations;
        PDFPage pDFPageA = a(this.f4229b);
        if (pDFPageA == null || (annotations = pDFPageA.getAnnotations()) == null || annotations.length <= i) {
            return null;
        }
        return annotations[i];
    }

    private android.graphics.Rect c(Rect rect) {
        return new android.graphics.Rect((int) rect.x0, (int) rect.y0, (int) rect.x1, (int) rect.y1);
    }

    public void d() {
        PDFAnnotation[] annotations;
        PDFPage pDFPageA = a(this.f4229b);
        if (pDFPageA == null || (annotations = pDFPageA.getAnnotations()) == null || annotations.length <= 0) {
            return;
        }
        for (PDFAnnotation pDFAnnotation : annotations) {
            if (pDFAnnotation != null) {
                pDFAnnotation.update();
            }
        }
    }

    public android.graphics.Rect e() {
        PDFAnnotation pDFAnnotationM = this.d.m();
        if (pDFAnnotationM == null || this.d.l() != this.c) {
            return null;
        }
        return c(pDFAnnotationM.getRect());
    }

    private RectF d(Rect rect) {
        return new RectF(rect.x0, rect.y0, rect.x1, rect.y1);
    }

    public android.graphics.Rect[] f() {
        Rect[] rectArr = this.f4228a;
        if (rectArr == null) {
            return null;
        }
        android.graphics.Rect[] rectArr2 = new android.graphics.Rect[rectArr.length];
        int i = 0;
        while (true) {
            Rect[] rectArr3 = this.f4228a;
            if (i >= rectArr3.length) {
                return rectArr2;
            }
            rectArr2[i] = c(rectArr3[i]);
            i++;
        }
    }

    public void b() {
        c cVar = this.d;
        if (cVar == null || cVar.e() == null) {
            return;
        }
        this.g = null;
        this.h = null;
        this.i = null;
        this.d.b(true);
        PDFPage pDFPageA = a(this.f4229b);
        if (pDFPageA != null) {
            pDFPageA.update();
            d();
            c();
        }
    }

    private Rect b(android.graphics.Rect rect) {
        return new Rect(rect.left, rect.top, rect.right, rect.bottom);
    }

    private void b(Rect rect) {
        ArrayList<SOPageListener> arrayList = this.m;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            SOPageListener sOPageListener = arrayList.get(i);
            i++;
            sOPageListener.update(rect != null ? d(rect) : null);
        }
    }

    private boolean b(PDFAnnotation pDFAnnotation) {
        return pDFAnnotation == null || pDFAnnotation.getType() != 20;
    }

    public int a(String str) {
        try {
            if (!str.equalsIgnoreCase(this.l)) {
                this.j = this.f4229b.search(str);
            }
            this.l = str;
            Quad[] quadArr = this.j;
            if (quadArr != null) {
                return quadArr.length;
            }
            return 0;
        } catch (Exception unused) {
            return 0;
        }
    }

    public android.graphics.Rect a() {
        int i;
        Quad[] quadArr = this.j;
        if (quadArr == null || quadArr.length <= 0 || (i = this.k) < 0 || i >= quadArr.length) {
            return null;
        }
        return c(quadArr[i].toRect());
    }

    private android.graphics.Rect a(Point point) {
        StructuredText.TextLine textLineA;
        StructuredText.TextBlock textBlockA = a(this.f4229b.toStructuredText().getBlocks(), point);
        if (textBlockA == null || (textLineA = a(textBlockA.lines, point)) == null) {
            return null;
        }
        int i = 0;
        while (true) {
            StructuredText.TextChar[] textCharArr = textLineA.chars;
            if (i >= textCharArr.length) {
                i = -1;
                break;
            }
            if (textCharArr[i].quad.toRect().contains(point.x, point.y)) {
                break;
            }
            i++;
        }
        if (i == -1 || textLineA.chars[i].isWhitespace()) {
            return null;
        }
        int i2 = i;
        while (true) {
            int i3 = i2 + 1;
            StructuredText.TextChar[] textCharArr2 = textLineA.chars;
            if (i3 >= textCharArr2.length || textCharArr2[i3].isWhitespace()) {
                break;
            }
            i2 = i3;
        }
        while (true) {
            int i4 = i - 1;
            if (i4 < 0 || textLineA.chars[i4].isWhitespace()) {
                break;
            }
            i--;
        }
        ArrayList arrayList = new ArrayList();
        Rect rect = new Rect();
        while (i <= i2) {
            arrayList.add(textLineA.chars[i]);
            rect.union(textLineA.chars[i].quad.toRect());
            i++;
        }
        return new android.graphics.Rect((int) rect.x0, (int) rect.y0, (int) rect.x1, (int) rect.y1);
    }

    public RectF a(android.graphics.Rect rect) {
        return new RectF(rect.left, rect.top, rect.right, rect.bottom);
    }

    public static PDFPage a(Page page) {
        try {
            return (PDFPage) page;
        } catch (Exception unused) {
            return null;
        }
    }

    private StructuredText.TextBlock a(StructuredText.TextBlock[] textBlockArr, Point point) {
        if (textBlockArr == null) {
            return null;
        }
        for (StructuredText.TextBlock textBlock : textBlockArr) {
            if (textBlock != null && textBlock.bbox.contains(point.x, point.y)) {
                return textBlock;
            }
        }
        return null;
    }

    private StructuredText.TextLine a(StructuredText.TextLine[] textLineArr, Point point) {
        if (textLineArr == null) {
            return null;
        }
        for (StructuredText.TextLine textLine : textLineArr) {
            if (textLine != null && textLine.bbox.contains(point.x, point.y)) {
                return textLine;
            }
        }
        return null;
    }

    @Override // com.artifex.solib.SOPage
    public SORender a(int i, double d, double d2, double d3, final SOBitmap sOBitmap, SOBitmap sOBitmap2, final o oVar, boolean z) {
        final int i2 = (int) d2;
        final int i3 = (int) d3;
        sOBitmap.c();
        sOBitmap.d();
        final int i4 = sOBitmap.b().left;
        final int i5 = sOBitmap.b().top;
        final int i6 = sOBitmap.b().right;
        final int i7 = sOBitmap.b().bottom;
        final Matrix matrixIdentity = Matrix.Identity();
        matrixIdentity.scale((float) d);
        final Cookie cookie = new Cookie();
        this.f = this.d.d();
        f fVar = new f(cookie);
        this.f.b(new r.a() { // from class: com.artifex.solib.e.1
            private boolean l = false;

            @Override // com.artifex.solib.r.a
            public void a() throws Throwable {
                if (e.this.q || !e.this.f.b()) {
                    return;
                }
                e.this.a(cookie);
                AndroidDrawDevice androidDrawDevice = null;
                try {
                    try {
                        try {
                            if (!sOBitmap.a().isRecycled()) {
                                AndroidDrawDevice androidDrawDevice2 = new AndroidDrawDevice(sOBitmap.a(), -i2, -i3, i4, i5, i6, i7);
                                try {
                                    if (e.this.g != null) {
                                        e.this.g.run(androidDrawDevice2, matrixIdentity, cookie);
                                    }
                                    if (e.this.h != null) {
                                        e.this.h.run(androidDrawDevice2, matrixIdentity, cookie);
                                    }
                                    if (e.this.i != null) {
                                        e.this.i.run(androidDrawDevice2, matrixIdentity, cookie);
                                    }
                                    androidDrawDevice = androidDrawDevice2;
                                } catch (Exception e) {
                                    e = e;
                                    androidDrawDevice = androidDrawDevice2;
                                    this.l = true;
                                    Log.e("mupdf", e.getMessage());
                                    if (androidDrawDevice == null || e.this.q || sOBitmap.a().isRecycled()) {
                                        return;
                                    }
                                    androidDrawDevice.close();
                                    androidDrawDevice.destroy();
                                    return;
                                } catch (Throwable th) {
                                    th = th;
                                    androidDrawDevice = androidDrawDevice2;
                                    AndroidDrawDevice androidDrawDevice3 = androidDrawDevice;
                                    Throwable th2 = th;
                                    if (androidDrawDevice3 == null || e.this.q || sOBitmap.a().isRecycled()) {
                                        throw th2;
                                    }
                                    try {
                                        androidDrawDevice3.close();
                                        androidDrawDevice3.destroy();
                                        throw th2;
                                    } catch (Exception e2) {
                                        this.l = true;
                                        Log.e("mupdf", e2.getMessage());
                                        throw th2;
                                    }
                                }
                            }
                            if (androidDrawDevice == null || e.this.q || sOBitmap.a().isRecycled()) {
                                return;
                            }
                            androidDrawDevice.close();
                            androidDrawDevice.destroy();
                        } catch (Exception e3) {
                            e = e3;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                    }
                } catch (Exception e4) {
                    this.l = true;
                    Log.e("mupdf", e4.getMessage());
                }
            }

            @Override // com.artifex.solib.r.a, java.lang.Runnable
            public void run() {
                if (oVar != null) {
                    oVar.a((e.this.q || sOBitmap.a().isRecycled() || this.l) ? 1 : 0);
                }
            }
        });
        return fVar;
    }

    public h a(int i, int i2) {
        PDFWidget pDFWidgetActivateWidgetAt;
        PDFPage pDFPageA = a(this.f4229b);
        if (pDFPageA == null || (pDFWidgetActivateWidgetAt = pDFPageA.activateWidgetAt(i, i2)) == null) {
            return null;
        }
        return new h(pDFWidgetActivateWidgetAt);
    }

    public void a(int i) {
        this.k = i;
        b(this.e);
    }

    public void a(PointF pointF, String str) {
        PDFPage pDFPageA = a(this.f4229b);
        if (pDFPageA == null) {
            return;
        }
        PDFAnnotation pDFAnnotationCreateAnnotation = pDFPageA.createAnnotation(0);
        float f = pointF.x;
        float f2 = pointF.y;
        pDFAnnotationCreateAnnotation.setRect(new Rect(f, f2 - 24.0f, 24.0f + f, f2));
        pDFAnnotationCreateAnnotation.setAuthor(str);
        pDFAnnotationCreateAnnotation.setModificationDate(new Date());
        pDFAnnotationCreateAnnotation.update();
    }

    public int a(Point point, int i) {
        PDFAnnotation[] annotations;
        PDFPage pDFPageA = a(this.f4229b);
        if (pDFPageA != null && (annotations = pDFPageA.getAnnotations()) != null && annotations.length != 0) {
            for (int i2 = 0; i2 < annotations.length; i2++) {
                PDFAnnotation pDFAnnotation = annotations[i2];
                if (pDFAnnotation != null && b(pDFAnnotation) && ((pDFAnnotation.getType() == i || i == -1) && pDFAnnotation.getRect().contains(point.x, point.y))) {
                    return i2;
                }
            }
        }
        return -1;
    }

    public void a(PDFAnnotation pDFAnnotation) {
        PDFPage pDFPageA = a(this.f4229b);
        if (pDFPageA != null) {
            pDFPageA.deleteAnnotation(pDFAnnotation);
        }
    }

    public void a(SOPageListener sOPageListener) {
        if (this.m.contains(sOPageListener)) {
            return;
        }
        this.m.add(sOPageListener);
    }

    public void a(SOPoint[] sOPointArr, float f, int i) {
        PDFPage pDFPageA = a(this.f4229b);
        if (pDFPageA == null) {
            return;
        }
        PDFAnnotation pDFAnnotationCreateAnnotation = pDFPageA.createAnnotation(15);
        pDFAnnotationCreateAnnotation.setBorder(f);
        pDFAnnotationCreateAnnotation.setColor(d(i));
        int length = sOPointArr.length;
        float[][] fArr = (float[][]) Array.newInstance((Class<?>) Float.TYPE, 1, length * 2);
        for (int i2 = 0; i2 < length; i2++) {
            float[] fArr2 = fArr[0];
            int i3 = i2 * 2;
            SOPoint sOPoint = sOPointArr[i2];
            fArr2[i3] = ((PointF) sOPoint).x;
            fArr2[i3 + 1] = ((PointF) sOPoint).y;
        }
        pDFAnnotationCreateAnnotation.setInkList(fArr);
        pDFAnnotationCreateAnnotation.update();
        this.d.b(true);
    }

    public float[] a(Rect rect) {
        float f = rect.x0;
        float f2 = rect.y0;
        float f3 = rect.x1;
        float f4 = rect.y1;
        return new float[]{f, f2, f3, f2, f, f4, f3, f4};
    }

    public float[][] a(Rect[] rectArr) {
        float[][] fArr = (float[][]) Array.newInstance((Class<?>) Float.TYPE, rectArr.length, 8);
        int i = 0;
        for (Rect rect : rectArr) {
            fArr[i] = a(rect);
            i++;
        }
        return fArr;
    }
}
